package org.sweatshop.format_server.config;

import static org.testng.Assert.assertEquals;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.metadata.ConstraintDescriptor;

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
        //java.lang.AssertionError: Sets differ: expected [] but got [
//        ConstraintViolationImpl{interpolatedMessage='must not be null', propertyPath=filesDir, rootBeanClass=class org.sweatshop.format_server.config.FilesConfig, messageTemplate='{javax.validation.constraints.NotNull.message}'}, 
//        ConstraintViolationImpl{interpolatedMessage='must not be null', propertyPath=headerFile, rootBeanClass=class org.sweatshop.format_server.config.FilesConfig, messageTemplate='{javax.validation.constraints.NotNull.message}'}, 
//        ConstraintViolationImpl{interpolatedMessage='must not be null', propertyPath=footerFile, rootBeanClass=class org.sweatshop.format_server.config.FilesConfig, messageTemplate='{javax.validation.constraints.NotNull.message}'}, 
//        ConstraintViolationImpl{interpolatedMessage='must not be null', propertyPath=error404, rootBeanClass=class org.sweatshop.format_server.config.FilesConfig, messageTemplate='{javax.validation.constraints.NotNull.message}'}]
        javax.validation.Path fdPath = PathImpl.createPathFromString("filesDir");

        ConstraintViolation<FilesConfig> cvi = ConstraintViolationImpl.forBeanValidation("{javax.validation.constraints.NotNull.message}", null, null,
                "must not be null", FilesConfig.class, null, null, null, fdPath, null, null);
        set.add(cvi);

                ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
                Validator validator = factory.getValidator();
                Set<ConstraintViolation<FilesConfig>> violations = validator.validate(fc);
                ConstraintViolation<FilesConfig> cvi2 = violations.stream().findAny().get();
//                assertCVIEqualsIsh(cvi2, cvi);

                List<ConstraintViolation<FilesConfig>> actualList = violations.stream().sorted((x, y) -> x.getMessage().compareTo(y.getMessage())).collect(Collectors.toList());
                List<ConstraintViolation<FilesConfig>> expectedList = set.stream().sorted((x, y) -> x.getMessage().compareTo(y.getMessage())).collect(Collectors.toList());
                //TODO
    }

    public void assertCVIEqualsIsh(List<ConstraintViolation<?>> actual, List<ConstraintViolation<?>> expected) {
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
