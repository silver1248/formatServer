package org.sweatshop.format_server;

import static org.testng.Assert.assertEquals;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.dropwizard.testing.ConfigOverride;
import io.dropwizard.testing.DropwizardTestSupport;
import io.dropwizard.testing.ResourceHelpers;
import io.dropwizard.client.JerseyClientBuilder;

public class LoginAcceptanceTest {

//    public static final DropwizardTestSupport<FormatServerConfiguration> SUPPORT =
//            new DropwizardTestSupport<FormatServerConfiguration>(
//                    FormatServerApplication.class,
//                    ResourceHelpers.resourceFilePath("config.yml")
//            );
//
//    @BeforeClass
//    public void beforeClass() throws Exception {
//        SUPPORT.before();
//    }
//
//    @AfterClass
//    public void afterClass() {
//        SUPPORT.after();
//    }
//
//    @Test
//    public void loginHandlerRedirectsAfterPost() {
//        Client client = new JerseyClientBuilder(SUPPORT.getEnvironment()).build("test client");
//
//        Response response = client.target(
//                 String.format("http://localhost:%d/hello-world/?name=%s", SUPPORT.getLocalPort(), "George"))
//                .request()
//                .get();
//
//        assertEquals(response.getStatus(), 200);//
//        assertEquals(Entity.json(response.getEntity()), "{\"id\":2,\"content\":\"Hello, George!\"}");
//        String jsonString = mapper.writeValueAsString(object);
//    }
}
