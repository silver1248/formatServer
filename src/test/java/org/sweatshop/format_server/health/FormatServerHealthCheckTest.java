package org.sweatshop.format_server.health;

import static org.testng.Assert.assertEquals;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.codahale.metrics.health.HealthCheck.Result;

public class FormatServerHealthCheckTest {

    @Test
    public void instantiationTest() {
        FormatServerHealthCheck fsh = new FormatServerHealthCheck("Hello, %s!");
        assertEquals(fsh.toString(), "FormatServerHealthCheck(template=Hello, %s!)");
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void checkNullTest() throws Exception {
        FormatServerHealthCheck fsh = new FormatServerHealthCheck(null);
        fsh.check();
    }

    @Test(dataProvider = "checkTestDP")
    public void checkTest(String template, Result expected) throws Exception {
        FormatServerHealthCheck fsh = new FormatServerHealthCheck(template);
        Result actual = fsh.check();
        assertEquals(actual.isHealthy(), expected.isHealthy());
        assertEquals(actual.getDuration(), expected.getDuration());
        assertEquals(actual.getMessage(), expected.getMessage());
        assertEquals(actual.getError(), expected.getError());
    }

    @DataProvider
    Object[][] checkTestDP() {
        Result unhealthy = Result.unhealthy("template doesn't include a name");
        return new Object[][] {
            //null is tested separately above because it throws
            {"", unhealthy},

            {"Hello, %s!", Result.healthy()},
            {"%s", Result.healthy()},
            {"Hello, TEST!", Result.healthy()},


            {"Hello, George!", unhealthy},
            {"Hello,!", unhealthy},
        };
    }
}
