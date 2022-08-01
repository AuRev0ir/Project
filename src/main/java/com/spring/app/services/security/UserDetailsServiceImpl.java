package com.spring.app.services.security;


import com.spring.app.domain.Role;
import com.spring.app.domain.User;
import com.spring.app.exception.CreateEntityException;
import com.spring.app.exception.UpdateEntityException;
import com.spring.app.exception.NotFoundEntityException;
import com.spring.app.repository.security.JpaRoleRepository;
import com.spring.app.repository.security.JpaUserRepository;
import com.spring.app.rest.dto.security.RoleDto;
import com.spring.app.rest.dto.security.UserDto;
import com.spring.app.rest.dto.security.UserRegistrationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;


@Service
public class UserDetailsServiceImpl implements UserDetailsService, UserService {


    @Autowired
    private PasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private JpaUserRepository jpaUserRepository;

    @Autowired
    private JpaRoleRepository jpaRoleRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = jpaUserRepository.findByName(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));
        return UserDetailsImpl.buildUserDetails(user);
    }


    @Override
    public String addUser(UserRegistrationDto dto) {


        Role roleFromDB = jpaRoleRepository.findByName("USER").orElseThrow(
                () -> new NotFoundEntityException("Role USER not found in database")
        );
        Set<Role>userRoles = Set.of(roleFromDB);

        Optional<User> userName = jpaUserRepository.findAll()
                .stream()
                .filter(organization -> Objects.equals(organization.getName(),dto.getName()))
                .findFirst();

        // Если имя User уже существует
        if(userName.isPresent()){
            throw new CreateEntityException("This user already exists");
        }

        jpaUserRepository.save(UserRegistrationDto.toDomainObject(
                dto, userRoles, bCryptPasswordEncoder.encode(dto.getPassword())));

        return "User successfully created";
    }

    @Override
    public Set<RoleDto> getRoles() {
        return jpaRoleRepository
                .findAll()
                .stream()
                .map(RoleDto::toDto)
                .collect(Collectors.toSet());
    }

    @Override
    public List<UserDto> getUsers() {
        return jpaUserRepository
                .findAll()
                .stream()
                .map(UserDto::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public UserDto getUserByName(String name) {
        User userFromDB = jpaUserRepository.findByName(name).orElseThrow(() -> new NotFoundEntityException(
                "User not found in database"));
        return UserDto.toDto(userFromDB);
    }

    @Override
    public String removeUser(String name) {
        User userFromDB = jpaUserRepository.findByName(name).orElseThrow(() -> new NotFoundEntityException(
                "User not found in database"));
        jpaUserRepository.delete(userFromDB);
        return "User: " + name + " deleted successfully";
    }

    @Override
    public String addUserRole(String roleName, String userName) {

        User userFromDB = jpaUserRepository.findByName(userName).orElseThrow(() -> new NotFoundEntityException(
                "User not found in database"));
        Role roleFromDB = jpaRoleRepository.findByName(roleName).orElseThrow(() -> new NotFoundEntityException(
                "Role not found in database"));


        // Проверка на наличие одинаковых ролей у User
        Set<Role> rolesSame = userFromDB.getRoles()
                .stream()
                .filter(role -> Objects.equals(role.getName(), roleFromDB.getName()))
                .collect(Collectors.toSet());

        if(!rolesSame.isEmpty()){
            throw new UpdateEntityException("User already has this role");
        }

        // Если ошибок нет добавляем Role
        userFromDB.getRoles().add(roleFromDB);
        jpaUserRepository.save(userFromDB);

        return userName + " given role " + roleName;
    }

    @Override
    public String removeUserRole(String roleName, String userName) {

        User userFromDB = jpaUserRepository.findByName(userName).orElseThrow(() -> new NotFoundEntityException(
                "User not found in database"));
        Role roleFromDB = jpaRoleRepository.findByName(roleName).orElseThrow(() -> new NotFoundEntityException(
                "Role not found in database"));

        // Проверка на наличие роли, которую стоит удалить
        Set<Role> rolesRemote = userFromDB.getRoles()
                .stream()
                .filter(role -> Objects.equals(role.getName(), roleFromDB.getName()))
                .collect(Collectors.toSet());

        if(rolesRemote.isEmpty()){
            throw new UpdateEntityException("User already has this role removed");
        }

        // Обновляем Roles У User
        Set<Role> rolesUser = userFromDB.getRoles()
                .stream()
                .filter(role -> !Objects.equals(role.getName(),roleFromDB.getName()))
                .collect(Collectors.toSet());


        // Если у User нету Roles, то очищаем БД иначе сохраняем
        if(rolesUser.isEmpty()){
            jpaUserRepository.delete(userFromDB);
        } else {
            userFromDB.setRoles(rolesUser);
            jpaUserRepository.save(userFromDB);
        }

        return userName + " delete role " + roleName;
    }

}
