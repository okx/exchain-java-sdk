package com.okexchain.legacy.common;

import org.junit.Assert;
import org.junit.Test;
import com.okexchain.utils.Utils;

public class StrUtilsTest {

    @Test
    public void isDecimalTest() {
        Assert.assertTrue(StrUtils.isDecimal("1.00000000", 8));
        Assert.assertTrue(StrUtils.isDecimal("99.00000000", 8));
        Assert.assertFalse(StrUtils.isDecimal("1.0000000", 8));
        Assert.assertFalse(StrUtils.isDecimal("1.000000000", 8));
        Assert.assertFalse(StrUtils.isDecimal("1.", 8));
        Assert.assertFalse(StrUtils.isDecimal(".000000000", 8));
        Assert.assertFalse(StrUtils.isDecimal("+1.00000000", 8));
        Assert.assertFalse(StrUtils.isDecimal("-1.00000000", 8));
        Assert.assertFalse(StrUtils.isDecimal("123", 8));
    }

    @Test
    public void isIntegerTest() {
        Assert.assertFalse(StrUtils.isInteger("1.0000000"));
        Assert.assertTrue(StrUtils.isInteger("199"));
    }

    @Test
    public void isNumericTest() {
        Assert.assertFalse(StrUtils.isNumeric("1.0000000"));
        Assert.assertTrue(StrUtils.isNumeric("10000000"));
        Assert.assertFalse(StrUtils.isNumeric("T00"));
        Assert.assertTrue(StrUtils.isNumeric("99"));
    }

    @Test
    public void isProductTest() {
        Assert.assertTrue(StrUtils.isProduct("okt_tusdk"));
        Assert.assertTrue(StrUtils.isProduct("1_2"));
        Assert.assertFalse(StrUtils.isProduct("xxb"));
        Assert.assertFalse(StrUtils.isProduct("xxb_"));
        Assert.assertFalse(StrUtils.isProduct("_xxb"));
    }

    @Test
    public void isProductSideTest() {
        Assert.assertFalse(StrUtils.isProductSide(""));
        Assert.assertFalse(StrUtils.isProductSide(null));
        Assert.assertFalse(StrUtils.isProductSide("999"));
        Assert.assertTrue(StrUtils.isProductSide("BUY"));
        Assert.assertTrue(StrUtils.isProductSide("SELL"));
    }

    @Test
    public void strToDecStrTest() {
        Assert.assertEquals("1.000000000000000000", Utils.NewDecString("1.000"));
        Assert.assertEquals("1.000000000000000000", Utils.NewDecString("1.0"));
        Assert.assertEquals("1.000000000000000000", Utils.NewDecString("1.0"));
        Assert.assertEquals("1.000000000000000000", Utils.NewDecString("1"));
        Assert.assertEquals("-1.000000000000000000", Utils.NewDecString("-1"));
        Assert.assertEquals("0.002000000000000000", Utils.NewDecString("0.00200000"));
    }
}