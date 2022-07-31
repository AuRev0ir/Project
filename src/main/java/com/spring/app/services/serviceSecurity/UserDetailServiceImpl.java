package com.spring.app.services.serviceSecurity;


import com.spring.app.domain.Role;
import com.spring.app.domain.User;
import com.spring.app.exception.CreateException;
import com.spring.app.exception.RepositoryException;
import com.spring.app.exception.ServiceException;
import com.spring.app.repository.security.RoleRepository;
import com.spring.app.repository.security.UserRepository;
import com.spring.app.rest.dto.registrationDto.RoleDto;
import com.spring.app.rest.dto.registrationDto.UserDto;
import com.spring.app.rest.dto.registrationDto.UserRegistrationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;


@Service
public class UserDetailServiceImpl implements UserDetailsService, UserService {


    @Autowired
    private PasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findByName(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));
        return UserDetailsImpl.buildUserDetails(user);
    }


    @Override
    public String addUser(UserRegistrationDto userRegistrationDto) {

        Set<Role>rolesNewUser = new HashSet<>();
        Role roleNewUser = roleRepository.findByName("USER").orElseThrow(RepositoryException::new);
        rolesNewUser.add(roleNewUser);

        Optional<User> userName = userRepository.findAll()
                .stream()
                .filter(organization -> Objects.equals(organization.getName(),userRegistrationDto.getName()))
                .findFirst();

        // Если имя User уже существует
        if(userName.isPresent()){
            throw new CreateException();
        }

        userRepository.save(UserRegistrationDto.toDomainObject(
                userRegistrationDto, rolesNewUser,bCryptPasswordEncoder.encode(userRegistrationDto.getPassword())));

        return "User successfully created";
    }

    @Override
    public Set<RoleDto> getRoles() {
        return roleRepository
                .findAll()
                .stream()
                .map(RoleDto::toDto)
                .collect(Collectors.toSet());
    }

    @Override
    public List<UserDto> getUsers() {
        return userRepository
                .findAll()
                .stream()
                .map(UserDto::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public UserDto getUserByName(String nameUser) {
        User userByName = userRepository.findByName(nameUser).orElseThrow(RepositoryException::new);
        return UserDto.toDto(userByName);
    }

    @Override
    public String removeUser(String nameDeleteUser) {
        User deleteUser = userRepository.findByName(nameDeleteUser).orElseThrow(RepositoryException::new);
        userRepository.delete(deleteUser);
        return "User: " + nameDeleteUser + " deleted successfully";
    }

    @Override
    public String addUserRole(String newNameRole, String nameUser) {

        User user = userRepository.findByName(nameUser).orElseThrow(RepositoryException::new);
        Role roleByName = roleRepository.findByName(newNameRole).orElseThrow(RepositoryException::new);


        // Проверка на наличие одинаковых ролей у User
        Set<Role> sameRoles = user.getRoles()
                .stream()
                .filter(role -> Objects.equals(role.getName(), roleByName.getName()))
                .collect(Collectors.toSet());

        if(!sameRoles.isEmpty()){
            throw new ServiceException();
        }

        // Если ошибок нет добавляем Role
        user.getRoles().add(roleByName);
        userRepository.save(user);

        return nameUser + " given role " + newNameRole;
    }

    @Override
    public String removeUserRole(String deleteRole, String nameUser) {

        User user = userRepository.findByName(nameUser).orElseThrow(RepositoryException::new);
        Role roleByName = roleRepository.findByName(deleteRole).orElseThrow(RepositoryException::new);

        // Проверка на наличие роли, которую стоит удалить
        Set<Role> roles = user.getRoles()
                .stream()
                .filter(role -> Objects.equals(role.getName(), roleByName.getName()))
                .collect(Collectors.toSet());

        if(roles.isEmpty()){
            throw new ServiceException();
        }

        // Обновляем Roles У User
        Set<Role> allRoles = user.getRoles()
                .stream()
                .filter(role -> !Objects.equals(role.getName(),roleByName.getName()))
                .collect(Collectors.toSet());


        // Если у User нету Roles, то очищаем БД
        if(allRoles.isEmpty()){
            userRepository.delete(user);
        } else {
            user.setRoles(allRoles);
            userRepository.save(user);
        }

        return nameUser + " delete role " + deleteRole;
    }

}
