package com.okchain.common;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class StrUtilsTest {

  @Test
  public void isDecimal() {
    Assert.assertTrue(StrUtils.isDecimal("1.00000000",8));
    Assert.assertFalse(StrUtils.isDecimal("1.0000000",8));
    Assert.assertFalse(StrUtils.isDecimal("1.000000000",8));
    Assert.assertFalse(StrUtils.isDecimal("1.",8));
    Assert.assertFalse(StrUtils.isDecimal(".000000000",8));
    Assert.assertFalse(StrUtils.isDecimal("+1.00000000",8));
    Assert.assertFalse(StrUtils.isDecimal("-1.00000000",8));
    Assert.assertFalse(StrUtils.isDecimal("123",8));
  }

  @Test
  public void isInteger() {

  }

  @Test
  public void isNumeric() {}

  @Test
  public void isProduct() {
    Assert.assertTrue(StrUtils.isProduct("xxb_okb"));
    Assert.assertTrue(StrUtils.isProduct("1_2"));
    Assert.assertFalse(StrUtils.isProduct("xxb"));
    Assert.assertFalse(StrUtils.isProduct("xxb_"));
    Assert.assertFalse(StrUtils.isProduct("_xxb"));

  }

  @Test
  public void isProductSide() {}
}