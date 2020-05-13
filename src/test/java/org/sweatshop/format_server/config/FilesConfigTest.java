package org.sweatshop.format_server.config;

import static org.testng.Assert.assertEquals;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.testng.annotations.Test;

public class FilesConfigTest {

    @Test
    public void instantiationTest() {
        Path headerFilePath = Paths.get("src/main/java/header.html");

        FilesConfig fc = new FilesConfig(headerFilePath, Paths.get("src/main/java/footer.html")
                , Paths.get("src/main/java/files"), Paths.get("src/main/java/error404.html"));

        assertEquals(fc.getHeaderFile(), headerFilePath);
    }

    @Test
    public void triedSendingNullTest() {
        FilesConfig fc = new FilesConfig(null, null, null, null);
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<FilesConfig>> constraintViolations = validator.validate(fc);
        assertEquals(constraintViolations, new HashSet<>());
    }

}
