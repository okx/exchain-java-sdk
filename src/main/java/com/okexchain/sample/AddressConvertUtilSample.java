package com.okexchain.sample;

import com.okexchain.utils.crypto.AddressConvertUtil;
import com.okexchain.utils.crypto.PrivateKey;

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
        System.out.println(AddressConvertUtil.convertFromBech32ToHex("okexchain1vnatqxr67z70lpyeq7gkrk9q668w3qn6sufu56").equals("0x64fAB0187AF0BCfF8499079161d8a0D68Ee8827a"));
        System.out.println(AddressConvertUtil.convertFromBech32ToHex("ex1vnatqxr67z70lpyeq7gkrk9q668w3qn6hhzuhk").equals("0x64fAB0187AF0BCfF8499079161d8a0D68Ee8827a"));
        System.out.println(AddressConvertUtil.convertFromHexToOkexchainBech32("0x64fAB0187AF0BCfF8499079161d8a0D68Ee8827a").equals("okexchain1vnatqxr67z70lpyeq7gkrk9q668w3qn6sufu56"));
        System.out.println(AddressConvertUtil.convertFromHexToExBech32("0x64fAB0187AF0BCfF8499079161d8a0D68Ee8827a").equals("ex1vnatqxr67z70lpyeq7gkrk9q668w3qn6hhzuhk"));
        System.out.println(AddressConvertUtil.convertFromExBech32ToOkexchainBech32("ex1vnatqxr67z70lpyeq7gkrk9q668w3qn6hhzuhk").equals("okexchain1vnatqxr67z70lpyeq7gkrk9q668w3qn6sufu56"));
        System.out.println(AddressConvertUtil.convertFromOkexchainBech32ToExBech32("okexchain1vnatqxr67z70lpyeq7gkrk9q668w3qn6sufu56").equals("ex1vnatqxr67z70lpyeq7gkrk9q668w3qn6hhzuhk"));

    }
}
