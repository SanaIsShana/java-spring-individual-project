package me.code.javaspringinlamningsuppgiftshana.controllers;

import lombok.Getter;
import lombok.Setter;
import me.code.javaspringinlamningsuppgiftshana.data.User;
import me.code.javaspringinlamningsuppgiftshana.dtos.UserDTO;
import me.code.javaspringinlamningsuppgiftshana.exceptions.UserAlreadyExistsException;
import me.code.javaspringinlamningsuppgiftshana.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

/**
 * UserController class includes all request handling methods
 */
@RestController
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }

    /* Endpoint for register a new user,
     * if user already exists it throws an UserAlreadyExistsException
     * it also return a UserDTO object.
     */
    @PostMapping("/register")
    public ResponseEntity<UserDTO> registerUser(
            @RequestBody
            UserInput userInput
    ) throws UserAlreadyExistsException {
        var user = userService.register(userInput.getUsername(), userInput.getPassword());
        return ResponseEntity.ok(UserDTO.fromUser(user));
    }

    @Getter
    @Setter
    public static class UserInput{
        private String username;
        private String password;
    }

    /* Endpoint for user detail,
     * sends a UserDTO (includes username and userId) as response.
     */

    @GetMapping("/info")
    public ResponseEntity<UserDTO> info(@AuthenticationPrincipal User user){
        return ResponseEntity.ok(UserDTO.fromUser(user));
    }

}
