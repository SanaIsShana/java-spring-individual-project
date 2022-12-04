package me.code.javaspringinlamningsuppgiftshana.services;

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

/**
 * UserService implements UserDetailService,
 * this class includes the business functionalities for user object,
 * and it has two methods.
 * Method loadUserByUsername is overridden in this class which locates the user based on the username.
 * The other method does the registration of a new user.
 */
@Service
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

    /* When user register a new user account
     * the service will wrap it to UserDetail object
     * and the password will be hashed.
     */
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
        return userRepository.save(user);

    }
}
