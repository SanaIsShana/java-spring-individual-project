package me.code.javaspringinlamningsuppgiftshana.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/** JWTLoginFilter implements UsernamePasswordAuthenticationFilter which
 * processes form submission of an authentication,
 * it tries to find username and password request in body and authenticate the values.
 */

@RequiredArgsConstructor
public class JWTLoginFilter extends UsernamePasswordAuthenticationFilter {

    /* Injects AuthenticationManager */
    private final AuthenticationManager authenticationManager;


    /* Customize attemptAuthentication method
     * it returns a populated authentication token for user
     * when the authentication is successful,
     * it returns null if is not successful.
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException
    {
        var username = request.getHeader("username");
        var password = request.getHeader("password");

        var authentication = new UsernamePasswordAuthenticationToken(username, password);

        return authenticationManager.authenticate(authentication);
    }

    /** when the autentication is successful is comes to this method
     * and create a token in the response header.
     */
    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult)
            throws IOException, ServletException
    {
        try {
            var algorithm = Algorithm.HMAC256("test");
            var token = JWT.create()
                    .withIssuer("auth0")
                    .withSubject(authResult.getName())
                    .sign(algorithm);

            response.addHeader("Access-Control-Expose-Headers", "Authorization");
            response.addHeader("Authorization", "Bearer " + token);
        } catch (JWTCreationException exception){
            exception.printStackTrace();
        }
    }


}
