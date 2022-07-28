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

        List<User> usersList = userRepository.findAll();

        for (User user : usersList) {
            if (Objects.equals(user.getName(), userRegistrationDto.getName())){
                throw new CreateException();
            }
        }

        User newUser = UserRegistrationDto.toDomainObject(userRegistrationDto, rolesNewUser,bCryptPasswordEncoder.encode(userRegistrationDto.getPassword()));
        userRepository.save(newUser);

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

        boolean isSuccessfully1 = true;
        boolean isSuccessfully2 = false;

        Set<Role> roles = roleRepository.findAll();

        User user = userRepository.findByName(nameUser).orElseThrow(RepositoryException::new);

        Set<Role> allRolesUser = user.getRoles();

        for (Role role : roles) {
            if(Objects.equals(role.getName(), newNameRole)){
                for (Role roleUser : allRolesUser) {
                    if(Objects.equals(roleUser.getName(),newNameRole)){
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
    public String removeUserRole(String deleteRole, String nameUser) {

        boolean isSuccessfully = false;

        Set<Role> roles = roleRepository.findAll();
        User user = userRepository.findByName(nameUser).orElseThrow(RepositoryException::new);

        Set<Role> allRolesUser = user.getRoles();

        for (Role role : roles) {
            if(Objects.equals(role.getName(), deleteRole)){
                for (Role userRole : allRolesUser) {
                    if(Objects.equals(userRole.getName(),deleteRole)){
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
