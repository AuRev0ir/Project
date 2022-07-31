package com.spring.app.services.serviceSecurity;


import com.spring.app.domain.Role;
import com.spring.app.domain.User;
import com.spring.app.exception.CreateException;
import com.spring.app.exception.RepositoryException;
import com.spring.app.exception.ServiceException;
import com.spring.app.repository.security.JpaRoleRepository;
import com.spring.app.repository.security.JpaUserRepository;
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
    public String addUser(UserRegistrationDto userRegistrationDto) {

        Set<Role>rolesNewUser = new HashSet<>();
        Role roleNewUser = jpaRoleRepository.findByName("USER").orElseThrow(RepositoryException::new);
        rolesNewUser.add(roleNewUser);

        Optional<User> userName = jpaUserRepository.findAll()
                .stream()
                .filter(organization -> Objects.equals(organization.getName(),userRegistrationDto.getName()))
                .findFirst();

        // Если имя User уже существует
        if(userName.isPresent()){
            throw new CreateException();
        }

        jpaUserRepository.save(UserRegistrationDto.toDomainObject(
                userRegistrationDto, rolesNewUser,bCryptPasswordEncoder.encode(userRegistrationDto.getPassword())));

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
    public UserDto getUserByName(String nameUser) {
        User userByName = jpaUserRepository.findByName(nameUser).orElseThrow(RepositoryException::new);
        return UserDto.toDto(userByName);
    }

    @Override
    public String removeUser(String nameDeleteUser) {
        User deleteUser = jpaUserRepository.findByName(nameDeleteUser).orElseThrow(RepositoryException::new);
        jpaUserRepository.delete(deleteUser);
        return "User: " + nameDeleteUser + " deleted successfully";
    }

    @Override
    public String addUserRole(String newNameRole, String nameUser) {

        User user = jpaUserRepository.findByName(nameUser).orElseThrow(RepositoryException::new);
        Role roleByName = jpaRoleRepository.findByName(newNameRole).orElseThrow(RepositoryException::new);


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
        jpaUserRepository.save(user);

        return nameUser + " given role " + newNameRole;
    }

    @Override
    public String removeUserRole(String deleteRole, String nameUser) {

        User user = jpaUserRepository.findByName(nameUser).orElseThrow(RepositoryException::new);
        Role roleByName = jpaRoleRepository.findByName(deleteRole).orElseThrow(RepositoryException::new);

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


        // Если у User нету Roles, то очищаем БД иначе сохраняем
        if(allRoles.isEmpty()){
            jpaUserRepository.delete(user);
        } else {
            user.setRoles(allRoles);
            jpaUserRepository.save(user);
        }

        return nameUser + " delete role " + deleteRole;
    }

}
