package me.code.javaspringinlamningsuppgiftshana.dtos;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import me.code.javaspringinlamningsuppgiftshana.data.User;

@Getter
@Setter
@RequiredArgsConstructor
public class UserDTO {

    private final String id;

    private String username;

    public static UserDTO fromUser(User user) {
        var payload = new UserDTO(user.getId());
        payload.setUsername(user.getUsername());

        return payload;
    }
}
