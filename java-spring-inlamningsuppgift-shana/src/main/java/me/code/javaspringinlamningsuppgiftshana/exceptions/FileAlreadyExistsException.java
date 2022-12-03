package me.code.javaspringinlamningsuppgiftshana.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT, reason = "A file with the specified name already exists.")
public class FileAlreadyExistsException extends Exception {
}
