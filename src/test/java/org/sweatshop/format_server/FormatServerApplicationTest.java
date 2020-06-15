package org.sweatshop.format_server;

import static org.testng.Assert.assertEquals;
import java.io.IOException;
import java.io.InputStream;
import javax.ws.rs.client.Client;
import javax.ws.rs.core.Response;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import io.dropwizard.client.JerseyClientBuilder;
import io.dropwizard.testing.DropwizardTestSupport;
import io.dropwizard.testing.ResourceHelpers;

public class FormatServerApplicationTest {

    @Test
    public void getNameTest() {
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
        try (InputStream is = (InputStream) entity) {
            byte[] bytes = is.readAllBytes();
            String entityString = new String(bytes);

            assertEquals(entityString, expected);
            assertEquals(response.getStatus(), expectedStatus);
        }
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