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
import com.spring.app.rest.pojo.FormRegistration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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

        User user = userRepository.findByNameUser(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));
        return UserDetailsImpl.buildUserDetails(user);
    }


    @Override
    public String newUser(FormRegistration formRegistration) {

        Set<Role>rolesNewUser = new HashSet<>();

        Set<Role> roles = roleRepository.findAll();

        for (Role role : roles) {
            for (String roleForm : formRegistration.getRolesNewUser()) {
                if (Objects.equals(role.getNameRole(),roleForm)){
                    rolesNewUser.add(role);
                }
            }
        }
        if (rolesNewUser.isEmpty()){
            throw new CreateException();                                    //Роль обязательно
        }

        User newUser = new User(
                formRegistration.getNameNewUser(),
                bCryptPasswordEncoder.encode(formRegistration.getPasswordNewUser()),
                formRegistration.getEmailNewUser(),
                formRegistration.getDateOfEmploymentNewUser(),
                rolesNewUser);

        userRepository.save(newUser);

        return "User successfully created";
    }

    @Override
    public Set<RoleDto> allRoles() {
        return roleRepository
                .findAll()
                .stream()
                .map(RoleDto::toDto)
                .collect(Collectors.toSet());
    }

    @Override
    public List<UserDto> allUsers() {
        return userRepository
                .findAll()
                .stream()
                .map(UserDto::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public UserDto userByName(String nameUser) {
        User userByName = userRepository.findByNameUser(nameUser).orElseThrow(RepositoryException::new);
        return UserDto.toDto(userByName);
    }

    @Override
    public String deleteUser(String nameDeleteUser) {
        User deleteUser = userRepository.findByNameUser(nameDeleteUser).orElseThrow(RepositoryException::new);
        userRepository.delete(deleteUser);
        return "User: " + nameDeleteUser + " deleted successfully";
    }

    @Override
    public String addRoleUser(String newNameRole, String nameUser) {

        boolean isSuccessfully1 = true;
        boolean isSuccessfully2 = false;

        Set<Role> roles = roleRepository.findAll();

        User user = userRepository.findByNameUser(nameUser).orElseThrow(RepositoryException::new);

        Set<Role> allRolesUser = user.getRoles();

        for (Role role : roles) {
            if(Objects.equals(role.getNameRole(), newNameRole)){
                for (Role roleUser : allRolesUser) {
                    if(Objects.equals(roleUser.getNameRole(),newNameRole)){
                        isSuccessfully1 = false;
                    }
                }
                allRolesUser.add(role);
                user.setRoles(allRolesUser);
                isSuccessfully2 = true;
            }
        }

        if (!isSuccessfully1){
            throw new ServiceException();
        }

        if (!isSuccessfully2){
            throw new ServiceException();
        }

        userRepository.save(user);

        return nameUser + " given role " + newNameRole;
    }

    @Override
    public String removeRoleUser(String deleteRole, String nameUser) {

        boolean isSuccessfully = false;

        Set<Role> roles = roleRepository.findAll();
        User user = userRepository.findByNameUser(nameUser).orElseThrow(RepositoryException::new);

        Set<Role> allRolesUser = user.getRoles();

        for (Role role : roles) {
            if(Objects.equals(role.getNameRole(), deleteRole)){
                for (Role userRole : allRolesUser) {
                    if(Objects.equals(userRole.getNameRole(),deleteRole)){
                        allRolesUser.remove(userRole);
                        user.setRoles(allRolesUser);
                        System.out.println(allRolesUser);
                        isSuccessfully = true;
                    }
                }
            }
        }

        if (!isSuccessfully){
            throw new ServiceException();
        }

        userRepository.save(user);

        return nameUser + " delete role " + deleteRole;
    }

}
