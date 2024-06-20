package ProgrammeringsEksamenAPI.models;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.util.Date;

@Getter @Setter
public class ErrorDetails {
    private Date timestamp;
    private String message;
    private String details;
    private HttpStatus status;

    public ErrorDetails(HttpStatus status, String message, String details) {
        super();
        this.timestamp = new Date();
        this.status = status;
        this.message = message;
        this.details = details;
    }

    // Getters and setters
}