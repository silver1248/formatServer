package org.sweatshop.format_server.config;

import static org.testng.Assert.assertEquals;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import org.hibernate.validator.internal.engine.ConstraintViolationImpl;
import org.hibernate.validator.internal.engine.path.PathImpl;
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
        HashSet<ConstraintViolation<FilesConfig>> set = new HashSet<>();

        set.add(makeCvi("filesDir"));
        set.add(makeCvi("headerFile"));
        set.add(makeCvi("error404"));
        set.add(makeCvi("footerFile"));


        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<FilesConfig>> violations = validator.validate(fc);

        List<ConstraintViolation<FilesConfig>> actualList = violations.stream().sorted((x, y) -> x.getPropertyPath().toString().compareTo(y.getPropertyPath().toString())).collect(Collectors.toList());
        List<ConstraintViolation<FilesConfig>> expectedList = set.stream().sorted((x, y) -> x.getPropertyPath().toString().compareTo(y.getPropertyPath().toString())).collect(Collectors.toList());
        assertCVIEqualsIsh(actualList, expectedList);
    }

    public static ConstraintViolation<FilesConfig> makeCvi(String path) {
        javax.validation.Path fdPath = PathImpl.createPathFromString(path);

        ConstraintViolation<FilesConfig> cvi = ConstraintViolationImpl.forBeanValidation("{javax.validation.constraints.NotNull.message}", null, null,
                "must not be null", FilesConfig.class, null, null, null, fdPath, null, null);
        return cvi;
    }

    public <T extends Object> void assertCVIEqualsIsh(List<ConstraintViolation<T>> actual, List<ConstraintViolation<T>> expected) {
        assertEquals(actual.size(), expected.size());
        for (int i = 0; i < actual.size(); i++) {
            assertCVIEqualsIsh(actual.get(i), expected.get(i));
        }
    }

    public void assertCVIEqualsIsh(ConstraintViolation<?> actual, ConstraintViolation<?> expected) {
        assertEquals(actual.getMessage(), expected.getMessage());
        assertEquals(actual.getPropertyPath(), expected.getPropertyPath());
        assertEquals(actual.getRootBeanClass(), expected.getRootBeanClass());
        assertEquals(actual.getMessageTemplate(), expected.getMessageTemplate());
    }
}
