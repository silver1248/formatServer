package org.sweatshop.format_server;

import static org.testng.Assert.assertEquals;

import java.nio.file.Paths;

import org.sweatshop.format_server.config.FilesConfig;
import org.sweatshop.format_server.resources.FormatServerResources;
import org.testng.annotations.Test;

public class FormatServerApplicationTest {

    @Test
    public void getNameTest() {
        FilesConfig fc = new FilesConfig(Paths.get("src/test/resources/header.html"),
                Paths.get("src/test/resources/footer.html"),
                Paths.get("src/test/resources/error404.html"),
                Paths.get("src/test/resources/files.html"));
        FormatServerApplication fsa = new FormatServerApplication();
        assertEquals(fsa.getName(), "hello-world");;
    }

    @Test
    public void runTest() {
        throw new RuntimeException("Test not implemented");
    }
}
