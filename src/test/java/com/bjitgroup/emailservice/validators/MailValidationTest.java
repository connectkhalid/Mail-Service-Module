package com.bjitgroup.emailservice.validators;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @author Mallika Dey
 */
@SpringBootTest
@TestPropertySource(locations = "/application-test.properties")
public class MailValidationTest {
    private MailValidation mailValidation;
    @Value("${file.invalid.location}")
    private String invalidFile;

    @Value("${file.invalid.large.location}")
    private String largeFile;

    @BeforeEach
    void setUp() {
        mailValidation = new MailValidation() {
        };
    }

    @Test
    public void checkFileCompatibilityTest_FileNotExist_ShouldThrowException() {
        File file = new File(invalidFile);

        assertThrows(IOException.class, () -> mailValidation.checkFileCompatibility(file));
    }

    @Test
    public void checkFileCompatibilityTest_InvalidFileType_ShouldThrowException() {
        File file = new File(largeFile);

        assertThrows(RuntimeException.class, () -> mailValidation.checkFileCompatibility(file));
    }
}
