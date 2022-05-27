package com.okexchain.test;

import com.alibaba.fastjson.JSON;
import com.okexchain.msg.common.BlockMethods;
import com.okexchain.msg.common.ContractAddresses;
import com.okexchain.msg.common.Message;
import com.okexchain.msg.gov.MsgContractMethodBlockedListProposal;
import com.okexchain.msg.gov.MsgContractMethodBlockedListProposalValue;
import com.okexchain.msg.tx.UnsignedTx;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class MsgContractMethodBlockedListProposalTest {

    @Test
    public void testMsgContractMethodBlockedListProposal(){

        MsgContractMethodBlockedListProposalValue mb=new MsgContractMethodBlockedListProposalValue();





        BlockMethods blm=new BlockMethods();
        blm.setExtra("inc()");
        blm.setSign("0x371303c0");

        BlockMethods blm1=new BlockMethods();
        blm1.setExtra("vote()");
        blm1.setSign("0x37130345");

        BlockMethods blm2=new BlockMethods();
        blm2.setExtra("sedEHT()");
        blm2.setSign("0x37000345");


        List<BlockMethods> bm=new ArrayList<>();
        bm.add(blm);
        bm.add(blm1);
        bm.add(blm2);



        BlockMethods blm3=new BlockMethods();
        blm3.setExtra("query()");
        blm3.setSign("0x9830345");

        BlockMethods blm4=new BlockMethods();
        blm4.setExtra("sedOKT()");
        blm4.setSign("0x37000345");

        List<BlockMethods> bm1=new ArrayList<>();
        bm1.add(blm3);
        bm1.add(blm4);


        ContractAddresses ca=new ContractAddresses();
        ca.setAddress("0x6825415e61F95965d80C556709C0f32B4092fc25");
        ca.setBlockMethods(bm);

        ContractAddresses ca1=new ContractAddresses();
        ca1.setAddress("0x90dAE493DAEE9B8A16EAf1D87dc553A48644Ccfe");
        ca1.setBlockMethods(bm1);


        List<ContractAddresses> contract_addresses_list=new ArrayList();
        contract_addresses_list.add(ca);
        contract_addresses_list.add(ca1);

        mb.setTitle("title");
        mb.setDescription("desc");
        mb.setContractAddresses(contract_addresses_list);
        mb.setAdded(true);


        MsgContractMethodBlockedListProposal msg = new MsgContractMethodBlockedListProposal();

        Message messages = msg.produceContractMethodBlockedListProposal(mb,"","");

        UnsignedTx unsignedTx = msg.getUnsignedTx(messages,"0.05000000","500000","");


        System.out.println(JSON.toJSONString(mb));
    }
}
