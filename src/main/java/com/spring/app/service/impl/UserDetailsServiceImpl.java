package com.spring.app.service.impl;

import com.spring.app.dao.model.Role;
import com.spring.app.dao.model.User;
import com.spring.app.dao.repository.RoleRepository;
import com.spring.app.dao.repository.UserRepository;
import com.spring.app.exception.EntityNotCreatedException;
import com.spring.app.exception.EntityNotUpdateException;
import com.spring.app.exception.NotFoundEntityException;
import com.spring.app.rest.dto.PlainUserDto;
import com.spring.app.rest.dto.RoleDto;
import com.spring.app.rest.dto.UserRegistrationDto;
import com.spring.app.rest.mapper.RoleMapper;
import com.spring.app.rest.mapper.UserMapper;
import com.spring.app.service.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserDetailsServiceImpl implements UserDetailsService, UserService {

    PasswordEncoder bCryptPasswordEncoder;
    UserRepository userRepository;
    RoleRepository roleRepository;
    // Mapper'ы объявляются бинами (настройка в pom.xml) и внедряются в другие бины
    UserMapper userMapper;
    RoleMapper roleMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = userRepository.findByName(username)
                                 .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));
        return UserDetailsImpl.buildUserDetails(user);
    }

    @Override
    public Long create(UserRegistrationDto registrationDto) {
        var roleFromDB = roleRepository.findByName("USER")
                                       .orElseThrow(() -> new NotFoundEntityException("Role USER not found in database"));

        // Если нужно только проверить, то лучше использовать для этого соответствующий метод
        // И такой вопрос: почему внутри filter() была переменная organization для класса User ? Почему не user, например ?
        if (userRepository.existsByName(registrationDto.getName())) {
            throw new EntityNotCreatedException("This user already exists");
        }
        String encodedPassword = bCryptPasswordEncoder.encode(registrationDto.getPassword());
        var roles = Set.of(roleFromDB);
        // mapper - это про сопоставление полей, НЕ про билдинг объектов
        var newUser = userMapper.toEntity(registrationDto);
        newUser.setPassword(encodedPassword);
        newUser.setRoles(roles);
        newUser.setRegistrationDate(LocalDate.now());
        var user = userRepository.save(newUser);

        // Какие-либо пользовательские сообщения должны формироваться фронтом, не бэком. Максимум это исключения
        return user.getId();
    }

    @Override
    public Set<RoleDto> getAllRoles() {
        return roleRepository
                .findAll()
                .stream()
                .map(roleMapper::toDto)
                .collect(Collectors.toSet());
    }

    @Override
    public List<PlainUserDto> getAllUsers() {
        return userRepository
                .findAll()
                .stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public PlainUserDto getUserByName(String name) {
        var userFromDB = userRepository.findByName(name)
                                       .orElseThrow(() -> new NotFoundEntityException("User not found in database"));

        return userMapper.toDto(userFromDB);
    }

    @Override
    public void remove(String name) {
        var userFromDB = userRepository.findByName(name)
                                       .orElseThrow(() -> new NotFoundEntityException("User not found in database"));
        userRepository.delete(userFromDB);
    }

    @Override
    public List<RoleDto> getUserRoles(String name) {
        return userRepository.findByName(name).orElseThrow(() -> new NotFoundEntityException("User not found in database")).getRoles()
                .stream().map(roleMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public void addUserRole(List<RoleDto> roles, String userName) {

        var userFromDB = userRepository.findByName(userName)
                                       .orElseThrow(() -> new NotFoundEntityException("User not found in database"));
        // В идеале, сделать один запрос в БД, по которому можно будет понять есть ли у пользователя указанная роль. Подумать, что для
        // этого нужно изменить

        var roleEntities = roleMapper.toEntityList(roles);

        var roleEntitiesCopy = new ArrayList<>(roleEntities);
        roleEntitiesCopy.retainAll(userFromDB.getRoles());
        if (!roleEntitiesCopy.isEmpty()) {
            throw new EntityNotUpdateException("User already have some roles: " + collectRolesToString(roleEntitiesCopy));
        }
        excludeNonexistentRoles(roleEntities, roleEntitiesCopy);

        var actualRoles = roleEntities.stream()
                .map(Role::getName)
                .map(roleRepository::findByName)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toSet());

        userFromDB.getRoles().addAll(actualRoles);
        userRepository.save(userFromDB);
    }

    private void excludeNonexistentRoles(List<Role> roleEntities, ArrayList<Role> roleEntitiesCopy) {
        roleEntities.removeAll(roleEntitiesCopy);
        var nonexistentRoles =
                roleEntities.stream()
                        .map(role -> roleRepository.findByName(role.getName()))
                        .filter(Optional::isEmpty)
                        .map(Optional::get)
                        .collect(Collectors.toList());

        if (!nonexistentRoles.isEmpty()) {
            throw new NotFoundEntityException("Roles not found in database: " + collectRolesToString(nonexistentRoles));
        }

        roleEntities.removeAll(nonexistentRoles);
    }

    private String collectRolesToString(List<Role> roles) {
        return roles.stream().map(Role::toString).collect(Collectors.joining(", "));
    }

    @Override
    public void removeUserRole(List<RoleDto> roles, String userName) {
        User userFromDB = userRepository.findByName(userName)
                                        .orElseThrow(() -> new NotFoundEntityException("User not found in database"));

        var roleEntities = roleMapper.toEntityList(roles);
        var roleEntitiesCopy = new ArrayList<>(roleEntities);

        roleEntitiesCopy.removeAll(userFromDB.getRoles());
        if (!roleEntitiesCopy.isEmpty()) {
            throw new EntityNotUpdateException("The user does not have this roles: " + collectRolesToString(roleEntitiesCopy));
        }
        excludeNonexistentRoles(roleEntities, roleEntitiesCopy);
        roleEntities.forEach(userFromDB.getRoles()::remove);

        if (userFromDB.getRoles().isEmpty()) {
            // Странное решение. Зачем удалять пользователя через процесс удаления ролей? Не лучше ли было
            // недопустить ситуации когда у пользователя не может быть ролей, не удаляя такого пользователя, а просто запретить иметь
            // пользователям меньше одной роли?
            userRepository.delete(userFromDB);
        } else {
            userRepository.save(userFromDB);
        }
    }
}
