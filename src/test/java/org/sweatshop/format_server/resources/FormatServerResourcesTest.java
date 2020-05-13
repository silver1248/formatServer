package org.sweatshop.format_server.resources;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNull;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import org.sweatshop.format_server.config.FilesConfig;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

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
        try {assertEquals(FormatServerResources.readFile(in), expectedString);
            assertNull(expectedE);
        } catch (Exception e) {
            assertEquals(e.getClass(), expectedE.getClass());
            assertEquals(e.getMessage(), expectedE.getMessage());
        }
    }

    @DataProvider
    Object[][] readFileTestDP() {
        return new Object[][] {
            {Paths.get("src/main/resources/files/george"), "WATCH OUT FOR THAT TREE", null},
            {Paths.get("src/main/resources/files/john"), "", new NoSuchFileException(Paths.get("src/main/resources/files/john").toString())},
            {Paths.get("src/main/resources/header.html"),"Header from a file\nline2 from the file", null},
            {Paths.get("src/main/resources/footer.html"), "footer from a file", null},
            {Paths.get("src/main/resources/foot"), "", new NoSuchFileException(Paths.get("src/main/resources/foot").toString())},
        };
    }

//    @Test(dataProvider = "sayHelloTestDP")
//    public void sayHelloTest() {
//        throw new RuntimeException("Test not implemented");
//    }

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

    //    @Test
    //    public void sayHello3Test() {
    //        throw new RuntimeException("Test not implemented");
    //    }
}
