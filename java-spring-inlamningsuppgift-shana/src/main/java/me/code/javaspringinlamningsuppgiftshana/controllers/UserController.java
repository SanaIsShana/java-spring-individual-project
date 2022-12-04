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

@RestController
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }

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

    @GetMapping("/info")
    public ResponseEntity<UserDTO> info(@AuthenticationPrincipal User user){
        return ResponseEntity.ok(UserDTO.fromUser(user));
    }

}
