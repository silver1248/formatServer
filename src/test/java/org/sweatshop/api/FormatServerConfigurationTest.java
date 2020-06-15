package org.sweatshop.api;

import static org.testng.Assert.assertEquals;
import java.nio.file.Paths;

import org.sweatshop.format_server.FormatServerConfiguration;
import org.sweatshop.format_server.config.FilesConfig;
import org.testng.annotations.Test;

public class FormatServerConfigurationTest {

    @Test
    public void getVariableTest() {
        FilesConfig fc = new FilesConfig(Paths.get("src/test/resources/header.html"),
                Paths.get("src/test/resources/footer.html"),
                Paths.get("src/test/resources/error404.html"),
                Paths.get("src/test/resources/files.html"));
        FormatServerConfiguration fsc = new FormatServerConfiguration("Hello, %s!", "Stranger", fc);
        assertEquals(fsc.getDefaultName(), "Stranger");
        assertEquals(fsc.getTemplate(), "Hello, %s!");
        assertEquals(fsc.getTemplate(), "Hello, %s!");
    }
}
