package me.code.javaspringinlamningsuppgiftshana.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception message when the file is not found
 */
@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "A file with the specified id could not be found.")
public class FileNotFoundException extends Exception {
}
