package org.sweatshop.format_server.resources;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNull;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.sweatshop.format_server.config.FilesConfig;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import io.vavr.collection.List;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FormatServerResourcesTest {

    @Test
    public void getDefaultNameTest() {
        FilesConfig fc = new FilesConfig(Paths.get("src/test/resources/header.html"),
                Paths.get("src/test/resources/footer.html"),
                Paths.get("src/test/resources/error404.html"),
                Paths.get("src/test/resources/files.html"));
        FormatServerResources fsr = new FormatServerResources("Hello, %s!", "Stranger", fc);
        assertEquals(fsr.getDefaultName(), "Stranger");
    }

    @Test
    public void getFilesConfigTest() {
        FilesConfig fc = new FilesConfig(Paths.get("src/test/resources/header.html"),
                Paths.get("src/test/resources/footer.html"),
                Paths.get("src/test/resources/error404.html"),
                Paths.get("src/test/resources/files.html"));
        FormatServerResources fsr = new FormatServerResources("Hello, %s!", "Stranger", fc);
        assertEquals(fsr.getFilesConfig(), fc);
    }

    @Test
    public void getTemplateTest() {
        FilesConfig fc = new FilesConfig(Paths.get("src/test/resources/header.html"),
                Paths.get("src/test/resources/footer.html"),
                Paths.get("src/test/resources/error404.html"),
                Paths.get("src/test/resources/files.html"));
        FormatServerResources fsr = new FormatServerResources("Hello, %s!", "Stranger", fc);
        assertEquals(fsr.getTemplate(), "Hello, %s!");
    }

    @Test(dataProvider= "readFileTestDP")
    public void readFileTest(Path in, String expectedString, Exception expectedE) {
        try {
            assertEquals(FormatServerResources.readFile(in), expectedString);
            assertNull(expectedE);
        } catch (Exception e) {
            assertEquals(e.getClass(), expectedE.getClass());
            assertEquals(e.getMessage(), expectedE.getMessage());
        }
    }

    @DataProvider
    Object[][] readFileTestDP() {
        return new Object[][] {
            {Paths.get("src/test/resources/files/george"), "WATCH OUT FOR THAT TREE", null},
            {Paths.get("src/test/resources/files/john"), "", new NoSuchFileException(Paths.get("src/test/resources/files/john").toString())},
            {Paths.get("src/test/resources/header.html"),"Header from a file\nline2 from the file", null},
            {Paths.get("src/test/resources/footer.html"), "footer from a file", null},
            {Paths.get("src/test/resources/foot"), "", new NoSuchFileException(Paths.get("src/test/resources/foot").toString())},
        };
    }

    @Test(dataProvider = "sayHelloTestDP")
    public void sayHelloTest(Optional<String> in, String expected, String template, String defaultName) throws FileNotFoundException, IOException {
        FilesConfig fc = new FilesConfig(Paths.get("src/test/resources/header.html"),
                Paths.get("src/test/resources/footer.html"),
                Paths.get("src/test/resources/error404.html"),
                Paths.get("src/test/resources/files.html"));
        FormatServerResources fsr = new FormatServerResources("Hello, %s!", "Stranger", fc);
        assertEquals(fsr.sayHello(in).getContent(), expected);
    }

    @DataProvider
    Object[][] sayHelloTestDP() {
        return new Object[][] {
            {Optional.empty(), "Hello, Stranger!", "Hello, %s!", "Stranger"},
            {Optional.of("Hello"), "Hello, Hello!", "Hello, %s!", "Stranger"},
            {Optional.of("George"), "Hello, George!", "Hello, %s!", "Stranger"},
            {Optional.of("src/test/resources/footer.html"), "Hello, src/test/resources/footer.html!", "Hello, %s!", "Stranger"},
        };
    }

    @Test(dataProvider = "sayHello2TestDP")
    public void sayHello2Test(Optional<String> in, String expected, String template, String defaultName) throws FileNotFoundException, IOException {
        FilesConfig fc = new FilesConfig(Paths.get("src/test/resources/header.html"),
                Paths.get("src/test/resources/footer.html"),
                Paths.get("src/test/resources/error404.html"),
                Paths.get("src/test/resources/files.html"));
        FormatServerResources fsr = new FormatServerResources("Hello, %s!", "Stranger", fc);
        assertEquals(fsr.sayHello2(in), expected);
    }

    @DataProvider
    Object[][] sayHello2TestDP() {
        return new Object[][] {
            {Optional.empty(), "Header from a file\nline2 from the file Stranger footer from a file", "Hello, %s!", "Stranger"},
            {Optional.of("Hello"), "Header from a file\nline2 from the file Hello footer from a file", "Hello, %s!", "Stranger"},
            {Optional.of("George"), "Header from a file\nline2 from the file George footer from a file", "Hello, %s!", "Stranger"},
            {Optional.of("src/test/resources/footer.html"), "Header from a file\nline2 from the file src/test/resources/footer.html footer from a file", "Hello, %s!", "Stranger"},
        };
    }

    @Test(dataProvider = "sayHello3TestDP")
    public void sayHello3Test(Optional<String> in, String expectedString, String template, String defaultName, Exception expectedE) throws FileNotFoundException, IOException {
        FilesConfig fc = FilesConfig.builder()
                .headerFile(Paths.get("src/test/resources/header.html"))
                .footerFile(Paths.get("src/test/resources/footer.html"))
                .error404(Paths.get("src/test/resources/error404.html"))
                .filesDir(Paths.get("src/test/resources/files"))
                .build();
        FormatServerResources fsr = new FormatServerResources("Hello, %s!", "Stranger", fc);
        try {
            assertEquals(fsr.sayHello3(in), expectedString);
            assertNull(expectedE);
            return;
        } catch (Exception e) {
            log.error("problem ", e);
            assertEquals(e.getClass(), expectedE.getClass());
            assertEquals(e.getMessage(), expectedE.getMessage());
        }
    }

    @DataProvider
    Object[][] sayHello3TestDP() {
        return new Object[][] {
            {Optional.empty(), "Header from a file\nline2 from the file Error 404:\n    File not found footer from a file", "Hello, %s!", "Stranger", new NoSuchElementException("No value present")},
            {Optional.of("Hello"), "Header from a file\nline2 from the file Error 404:\n    File not found footer from a file", "Hello, %s!", "Stranger", null},
            {Optional.of("george"), "Header from a file\nline2 from the file WATCH OUT FOR THAT TREE footer from a file", "Hello, %s!", "Stranger", null},
            {Optional.of("src/test/resources/footer.html"), "Header from a file\nline2 from the file Error 404:\n    File not found footer from a file", "Hello, %s!", "Stranger", null},
        };
    }

    @Test
    public void filesTest() {
        FilesConfig fc = new FilesConfig(Paths.get("src/test/resources/header.html"),
                Paths.get("src/test/resources/footer.html"),
                Paths.get("src/test/resources/empty"),
                Paths.get("src/test/resources/error404.html"));
        FormatServerResources fsr = new FormatServerResources("Hello, %s!", "Stranger", fc);
        assertEquals(fsr.filesInFiles(), List.empty());
    }
}