package com.spring.app.service.security;


import com.spring.app.dao.model.Role;
import com.spring.app.dao.model.User;
import com.spring.app.exception.EntityNotCreatedException;
import com.spring.app.exception.EntityNotUpdateException;
import com.spring.app.exception.NotFoundEntityException;
import com.spring.app.dao.repository.security.RoleRepository;
import com.spring.app.dao.repository.security.UserRepository;
import com.spring.app.rest.dto.security.RoleDto;
import com.spring.app.rest.dto.security.UserDto;
import com.spring.app.rest.dto.security.UserRegistrationDto;
import com.spring.app.rest.mapper.RoleMapper;
import com.spring.app.rest.mapper.UserMapper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserDetailsServiceImpl implements UserDetailsService, UserService {


    PasswordEncoder bCryptPasswordEncoder;

    UserRepository userRepository;

    RoleRepository roleRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findByName(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));
        return UserDetailsImpl.buildUserDetails(user);
    }


    @Override
    public String create(UserRegistrationDto dto) {


        Role roleFromDB = roleRepository.findByName("USER")
                .orElseThrow(() -> new NotFoundEntityException("Role USER not found in database"));

        Set <Role> userRoles = Set.of(roleFromDB);

        Optional<User> userName = userRepository.findAll()
                .stream()
                .filter(organization -> Objects.equals(organization.getName(),dto.getName()))
                .findFirst();

        if(userName.isPresent()){
            throw new EntityNotCreatedException("This user already exists");
        }

        userRepository.save(UserMapper.INSTANCE.toDomainObject(
                dto,(bCryptPasswordEncoder.encode(dto.getPassword())), userRoles));

        return "User successfully created";
    }

    @Override
    public Set<RoleDto> getAllRoles () {
        return roleRepository
                .findAll()
                .stream()
                .map(RoleMapper.INSTANCE::toDto)
                .collect(Collectors.toSet());
    }

    @Override
    public List<UserDto> getAllUsers () {


        return userRepository
                .findAll()
                .stream()
                .map(user -> UserMapper.INSTANCE.toDto(user , user.getRoles().
                        stream().map(RoleMapper.INSTANCE::toDto).collect(Collectors.toSet())))
                .collect(Collectors.toList());

    }

    @Override
    public UserDto getUserByName (String name) {
        User userFromDB = userRepository.findByName(name)
                .orElseThrow(() -> new NotFoundEntityException("User not found in database"));

        return UserMapper.INSTANCE.toDto(userFromDB, userFromDB.getRoles()
                .stream().map(RoleMapper.INSTANCE::toDto).collect(Collectors.toSet()));
    }

    @Override
    public String remove (String name) {
        User userFromDB = userRepository.findByName(name)
                .orElseThrow(() -> new NotFoundEntityException("User not found in database"));
        userRepository.delete(userFromDB);
        return "User: " + name + " deleted successfully";
    }

    @Override
    public String addUserRole (String roleName, String userName) {

        User userFromDB = userRepository.findByName(userName)
                .orElseThrow(() -> new NotFoundEntityException("User not found in database"));
        Role roleFromDB = roleRepository.findByName(roleName)
                .orElseThrow(() -> new NotFoundEntityException("Role not found in database"));

        Set <Role> rolesSame = userFromDB.getRoles()
                .stream()
                .filter(role -> Objects.equals(role.getName(), roleFromDB.getName()))
                .collect(Collectors.toSet());

        if(!rolesSame.isEmpty()){
            throw new EntityNotUpdateException("User already has this role");
        }

        userFromDB.getRoles().add(roleFromDB);
        userRepository.save(userFromDB);

        return userName + " given role " + roleName;
    }

    @Override
    public String removeUserRole (String roleName, String userName) {

        User userFromDB = userRepository.findByName(userName)
                .orElseThrow(() -> new NotFoundEntityException("User not found in database"));
        Role roleFromDB = roleRepository.findByName(roleName)
                .orElseThrow(() -> new NotFoundEntityException("Role not found in database"));

        Set <Role> rolesRemote = userFromDB.getRoles()
                .stream()
                .filter(role -> Objects.equals(role.getName(), roleFromDB.getName()))
                .collect(Collectors.toSet());

        if(rolesRemote.isEmpty()){
            throw new EntityNotUpdateException("The user does not have this role");
        }

        Set <Role> rolesUser = userFromDB.getRoles()
                .stream()
                .filter(role -> !Objects.equals(role.getName(),roleFromDB.getName()))
                .collect(Collectors.toSet());

        if(rolesUser.isEmpty()){
            userRepository.delete(userFromDB);
        } else {
            userFromDB.getRoles().remove(roleFromDB);
            userRepository.save(userFromDB);
        }

        return userName + " delete role " + roleName;
    }

}
