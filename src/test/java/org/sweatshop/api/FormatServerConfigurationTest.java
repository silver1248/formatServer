package org.sweatshop.api;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import org.sweatshop.format_server.FormatServerConfiguration;
import org.testng.annotations.Test;

public class FormatServerConfigurationTest {

    @Test
    public void getDefaultNameTest() {
        FormatServerConfiguration fsc = null;
        assertEquals(fsc.getDefaultName(), "Stranger");
        assertEquals(fsc.getTemplate(), "Hello, %s!");
        assertEquals(fsc.getTemplate(), "Hello, %s!");
    }
}
