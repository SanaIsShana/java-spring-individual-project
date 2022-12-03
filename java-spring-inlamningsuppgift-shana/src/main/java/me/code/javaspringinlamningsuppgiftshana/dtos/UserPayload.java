package me.code.javaspringinlamningsuppgiftshana.dtos;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import me.code.javaspringinlamningsuppgiftshana.data.User;

@Getter
@Setter
@RequiredArgsConstructor
public class UserPayload {

    private final String id;

    private String username;
    private boolean admin;

    public static UserPayload fromUser(User user) {
        var payload = new UserPayload(user.getId());
        payload.setUsername(user.getUsername());

        return payload;
    }
}
