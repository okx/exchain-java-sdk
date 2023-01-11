package com.okexchain.utils;

import junit.framework.TestCase;
import org.junit.Assert;

public class UtilsTest extends TestCase {

    public void testIsValidHexAddress() {
        String hexAddress = "0x45dD91b0289E60D89Cec94dF0Aac3a2f539c514a";
        Assert.assertTrue(Utils.isValidHexAddress(hexAddress));

        hexAddress = "45dD91b0289E60D89Cec94dF0Aac3a2f539c514a";
        Assert.assertFalse(Utils.isValidHexAddress(hexAddress));

        hexAddress = "0x45dD91b09E60D89Cec94dF0Aac3a2f539c514a";
        Assert.assertFalse(Utils.isValidHexAddress(hexAddress));
    }
}