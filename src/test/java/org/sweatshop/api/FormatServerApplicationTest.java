package org.sweatshop.api;

import static org.testng.Assert.assertEquals;

import org.sweatshop.format_server.FormatServerApplication;
import org.testng.annotations.Test;

public class FormatServerApplicationTest {

    @Test
    public void getNameTest() {
        FormatServerApplication fsa = new FormatServerApplication();
        assertEquals(fsa.getName(), "hello-world");
    }
}
