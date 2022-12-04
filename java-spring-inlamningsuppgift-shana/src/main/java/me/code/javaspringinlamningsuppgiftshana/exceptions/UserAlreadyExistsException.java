package me.code.javaspringinlamningsuppgiftshana.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception for registration of user, if the user already exist it shows the message.
 */
@ResponseStatus(code = HttpStatus.CONFLICT, reason = "A user with the specified name already exists.")
public class UserAlreadyExistsException extends Exception {
}
