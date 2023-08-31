package ru.practicum;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class MainServiceAppTest extends TestCase {
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public MainServiceAppTest(String testName) {
        super(testName);
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite() {
        return new TestSuite(MainServiceAppTest.class);
    }

    /**
     * Rigourous Test :-)
     */
    public void testApp() {
        assertTrue(true);
    }
}
