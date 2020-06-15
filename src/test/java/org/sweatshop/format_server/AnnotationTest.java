package org.sweatshop.format_server;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class AnnotationTest {

    @BeforeClass
    @AfterClass
    public void bummy2() {
        System.out.println("this is dummy2");
    }

    @BeforeClass
    @AfterClass
    public void dummy() {
        System.out.println("this is dummy");
    }

    @Test
    public void test() {
        System.out.println("this is the test");
    }
}
