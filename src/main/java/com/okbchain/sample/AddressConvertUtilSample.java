package com.okbchain.sample;

import com.okbchain.utils.crypto.AddressConvertUtil;
import com.okbchain.utils.crypto.PrivateKey;

/**
 * @author shaoyun.zhan
 * @date 2021/3/31
 * <p>
 * 描述：
 */
public class AddressConvertUtilSample {
    public static void main(String[] args) {
        //"eth_address": "0x64fAB0187AF0BCfF8499079161d8a0D68Ee8827a"
        //"privateKey": "2843de7dec93946f1022ec9355678fdec3dc49d3140d2314b452a3a4afe78191"
        System.out.println(new PrivateKey("2843de7dec93946f1022ec9355678fdec3dc49d3140d2314b452a3a4afe78191").getAddress());
        System.out.println(AddressConvertUtil.convertFromBech32ToHex("okbchain1vnatqxr67z70lpyeq7gkrk9q668w3qn6sufu56").equals("0x64fAB0187AF0BCfF8499079161d8a0D68Ee8827a"));
        System.out.println(AddressConvertUtil.convertFromBech32ToHex("ex1vnatqxr67z70lpyeq7gkrk9q668w3qn6hhzuhk").equals("0x64fAB0187AF0BCfF8499079161d8a0D68Ee8827a"));
        System.out.println(AddressConvertUtil.convertFromHexTookbchainBech32("0x64fAB0187AF0BCfF8499079161d8a0D68Ee8827a").equals("okbchain1vnatqxr67z70lpyeq7gkrk9q668w3qn6sufu56"));
        System.out.println(AddressConvertUtil.convertFromHexToExBech32("0x64fAB0187AF0BCfF8499079161d8a0D68Ee8827a").equals("ex1vnatqxr67z70lpyeq7gkrk9q668w3qn6hhzuhk"));
        System.out.println(AddressConvertUtil.convertFromExBech32TookbchainBech32("ex1vnatqxr67z70lpyeq7gkrk9q668w3qn6hhzuhk").equals("okbchain1vnatqxr67z70lpyeq7gkrk9q668w3qn6sufu56"));
        System.out.println(AddressConvertUtil.convertFromokbchainBech32ToExBech32("okbchain1vnatqxr67z70lpyeq7gkrk9q668w3qn6sufu56").equals("ex1vnatqxr67z70lpyeq7gkrk9q668w3qn6hhzuhk"));
        System.out.println(AddressConvertUtil.convertFromokbchainValToExVal("okbchainvaloper18au05qx485u2qcw2gvqsrfh29evq77lm45mf4h").equals("exvaloper18au05qx485u2qcw2gvqsrfh29evq77lm9u2jwr"));

    }
}
