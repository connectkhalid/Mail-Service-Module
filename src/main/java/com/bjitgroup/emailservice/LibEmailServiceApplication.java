package com.bjitgroup.emailservice;

import jakarta.mail.MessagingException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @hidden
 */
@SpringBootApplication
public class LibEmailServiceApplication {
    public static void main(String[] args) throws MessagingException {
        SpringApplication.run(LibEmailServiceApplication.class, args);
    }
}