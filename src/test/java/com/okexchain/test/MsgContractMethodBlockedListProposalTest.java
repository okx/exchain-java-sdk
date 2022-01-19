package com.okexchain.test;

import com.okexchain.msg.common.Block_methods;
import com.okexchain.msg.common.Contract_addresses;
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





        Block_methods blm=new Block_methods();
        blm.setExtra("inc()");
        blm.setSign("0x371303c0");

        Block_methods blm1=new Block_methods();
        blm1.setExtra("vote()");
        blm1.setSign("0x37130345");

        Block_methods blm2=new Block_methods();
        blm2.setExtra("sedEHT()");
        blm2.setSign("0x37000345");


        List<Block_methods> bm=new ArrayList<>();
        bm.add(blm);
        bm.add(blm1);
        bm.add(blm2);



        Block_methods blm3=new Block_methods();
        blm3.setExtra("query()");
        blm3.setSign("0x9830345");

        Block_methods blm4=new Block_methods();
        blm4.setExtra("sedOKT()");
        blm4.setSign("0x37000345");

        List<Block_methods> bm1=new ArrayList<>();
        bm1.add(blm3);
        bm1.add(blm4);


        Contract_addresses ca=new Contract_addresses();
        ca.setAddress("1");
        ca.setBlock_methods(bm);

        Contract_addresses ca1=new Contract_addresses();
        ca1.setAddress("2");
        ca1.setBlock_methods(bm1);


        List<Contract_addresses> contract_addresses_list=new ArrayList();
        contract_addresses_list.add(ca);
        contract_addresses_list.add(ca1);

        mb.setTitle("title");
        mb.setDescription("desc");
        mb.setContractAddresses(contract_addresses_list);
        mb.setIsAdded(true);


        MsgContractMethodBlockedListProposal msg = new MsgContractMethodBlockedListProposal();

        Message messages = msg.produceContractMethodBlockedListProposal(mb,"","");

        UnsignedTx unsignedTx = msg.getUnsignedTx(messages,"0.05000000","500000","");
        System.out.println("mb=========="+mb);
        //System.out.println("unsignedTx.toString()"+unsignedTx.toString());
    }
}
