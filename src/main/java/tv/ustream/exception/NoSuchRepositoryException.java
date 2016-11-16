package tv.ustream.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Repository not found with these parameters.")
public class NoSuchRepositoryException extends Exception {
}
