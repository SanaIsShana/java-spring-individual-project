package me.code.javaspringinlamningsuppgiftshana.services;

import lombok.extern.slf4j.Slf4j;
import me.code.javaspringinlamningsuppgiftshana.data.User;
import me.code.javaspringinlamningsuppgiftshana.exceptions.UserAlreadyExistsException;
import me.code.javaspringinlamningsuppgiftshana.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public Optional<User> getByUsername(String username){
        return userRepository.findByUsername(username);
    }

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = userRepository
                .findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Can't find the user with username: " + username + ",please double check the name!"));

        return user;
    }

    public User register(String username, String password)
            throws UserAlreadyExistsException
    {
        var existing = userRepository.findByUsername(username);
        if (existing.isPresent()){
            throw new UserAlreadyExistsException();
        }
        var user = new User(
            UUID.randomUUID().toString(),
            username,
            passwordEncoder.encode(password)

    );
        log.info("Successfully registered user with id '" + user.getId() + "'.");
        return userRepository.save(user);

    }
}
