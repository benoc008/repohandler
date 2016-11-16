package tv.ustream.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT, reason = "Repository already exists with this name.")
public class NameAlreadyInUseException extends Exception {
}
