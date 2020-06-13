package org.sweatshop.format_server;

import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;

import org.sweatshop.format_server.config.FilesConfig;
import org.sweatshop.format_server.resources.FormatServerResources;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.fasterxml.jackson.core.JsonProcessingException;

import io.dropwizard.client.JerseyClientBuilder;
import io.dropwizard.testing.DropwizardTestSupport;
import io.dropwizard.testing.ResourceHelpers;

public class FormatServerApplicationTest {

    @Test
    public void getNameTest() {
        //        FilesConfig fc = new FilesConfig(Paths.get("src/test/resources/header.html"),
        //                Paths.get("src/test/resources/footer.html"),
        //                Paths.get("src/test/resources/error404.html"),
        //                Paths.get("src/test/resources/files.html"));
        FormatServerApplication fsa = new FormatServerApplication();
        assertEquals(fsa.getName(), "hello-world");
    }

    public static final DropwizardTestSupport<FormatServerConfiguration> SUPPORT =
            new DropwizardTestSupport<FormatServerConfiguration>(FormatServerApplication.class,
                    ResourceHelpers.resourceFilePath("config.yml"));

    public static Client client;
    
    @BeforeClass
    public void beforeClass() throws Exception {
        SUPPORT.before();
        client = new JerseyClientBuilder(SUPPORT.getEnvironment()).build("test client");
    }

    @AfterClass
    public void afterClass() {
        SUPPORT.after();
    }

    @Test
    public void helloWorldTest() {
        FormatServerApplication fsa = new FormatServerApplication();

        Response response = client.target(
                String.format("http://localhost:%d/hello-world/?name=fred", SUPPORT.getLocalPort()))
                .request()
                .get();

        assertEquals(response.getStatus(), 200);
    }

    @Test
    public void helloWorld2Test() {
        Response response = client.target(
                String.format("http://localhost:%d/hello-world2/?name=fred", SUPPORT.getLocalPort()))
                .request()
                .get();

        assertEquals(response.getStatus(), 200);
    }

    @Test(dataProvider="helloWorld3TestDP")
    public void helloWorld3Test(String in, String expected, int expectedStatus) throws IOException {
        Response response = client.target(
                String.format("http://localhost:%d/files/%s", SUPPORT.getLocalPort(), in))
                .request()
                .get();

        Object entity = response.getEntity();
        InputStream is = (InputStream) entity;
        byte[] bytes = is.readAllBytes();
        String entityString = new String(bytes);

        assertEquals(entityString, expected);
        assertEquals(response.getStatus(), expectedStatus);
    }

    @DataProvider
    Object[][] helloWorld3TestDP() {
        return new Object[][]{
            {null, "Header from a file\nline2 from the file Error 404:\n    File not found footer from a file", 200},
            {"", "{\"code\":404,\"message\":\"HTTP 404 Not Found\"}", 404},

            {"a", "Header from a file\nline2 from the file Error 404:\n    File not found footer from a file", 200},

            {"george", "Header from a file\nline2 from the file WATCH OUT FOR THAT TREE footer from a file", 200},
            {"George", "Header from a file\nline2 from the file WATCH OUT FOR THAT TREE footer from a file", 200},
        };
    }

}