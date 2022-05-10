package com.okc.ibc;

import com.okexchain.msg.ibc.channels.pojo.PacketCommitmentsResponse;
import com.okexchain.utils.Utils;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

@Slf4j
public class HeightTest {

    @Test
    public void height(){
        String json="{\"commitments\":[{\"port_id\":\"transfer\",\"channel_id\":\"channel-0\",\"sequence\":\"1\",\"data\":\"P48OX2zrDqI5qt/BCRk5oLAajUTGOhBkEzRB5+IL8bc=\"},{\"port_id\":\"transfer\",\"channel_id\":\"channel-0\",\"sequence\":\"2\",\"data\":\"P48OX2zrDqI5qt/BCRk5oLAajUTGOhBkEzRB5+IL8bc=\"}],\"pagination\":{\"next_key\":null,\"total\":\"6\"},\"height\":{\"revision_number\":\"4\",\"revision_height\":\"10418827\"}}";
        PacketCommitmentsResponse commitmentsResponse=Utils.serializer.fromJson(json,PacketCommitmentsResponse.class);
        System.out.println(commitmentsResponse.toString());

    }
}
