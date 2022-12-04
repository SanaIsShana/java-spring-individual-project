package me.code.javaspringinlamningsuppgiftshana.security;

import me.code.javaspringinlamningsuppgiftshana.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * This class manages the authentication of the program
 * which implements AuthenticationManager and this will authenticate the passed Authentication object
 * and return a fully populated Authentication object if authentication is successful.
 */
@Component
public class CustomAuthenticationManager implements AuthenticationManager {

    //here injects PasswordEncoder and UserService
    private final PasswordEncoder encoder;
    private final UserService userService;

    @Autowired
    public CustomAuthenticationManager(PasswordEncoder encoder, UserService userService) {
        this.encoder = encoder;
        this.userService = userService;
    }

    @Override //Customize the Authentication
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        final UserDetails userDetail = userService.loadUserByUsername(authentication.getName());
        if (!encoder.matches(authentication.getCredentials().toString(), userDetail.getPassword())) {
            throw new BadCredentialsException("Wrong password"); //it throws BadCredentialException when
            // there is incorrect credentials are presented.
        }
        return new UsernamePasswordAuthenticationToken(userDetail.getUsername(), userDetail.getPassword(), userDetail.getAuthorities());
    }
}
