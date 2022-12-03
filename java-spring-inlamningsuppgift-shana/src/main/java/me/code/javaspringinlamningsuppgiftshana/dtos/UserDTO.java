package me.code.javaspringinlamningsuppgiftshana.dtos;

import lombok.Getter;
import lombok.Setter;
import me.code.javaspringinlamningsuppgiftshana.data.User;
import org.springframework.security.core.userdetails.UserDetails;

@Getter
@Setter
public class UserDTO {

    private String username, password;
    private boolean admin;

}
