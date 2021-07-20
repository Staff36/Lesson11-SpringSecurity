package com.example.lesson11springsecurity.services;

import com.example.lesson11springsecurity.entities.Role;
import com.example.lesson11springsecurity.entities.User;
import com.example.lesson11springsecurity.repositories.UserRepository;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@Data
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    public User findByLogin(String login){
        return userRepository.findUserByLogin(login).orElseThrow(()-> new EntityNotFoundException(String.format("User with login %s not found", login)));
    }

    public void incrementUsersScore(Long id){
        updateUsersScores(id, 1L);
    }

    public void decrementUsersScore(Long id){
        updateUsersScores(id, -1L);
    }

    public User getUserById(Long id){
        User user = userRepository.findUserById(id).orElseThrow(()-> new EntityNotFoundException(String.format("User with id %d not found", id)));
        return user;
    }

    public void updateUsersScores(Long id, Long value){
        User user = getUserById(id);
        user.setScore(user.getScore() + value);
        userRepository.save(user);
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = findByLogin(username);
        return new org.springframework.security.core.userdetails.User(user.getLogin(), user.getPassword(), mapRolesToAuthorities(user.getRoles()));
    }

    public Collection<? extends GrantedAuthority> mapRolesToAuthorities(List<Role> roles){
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }
}


