package pingpong.messages;

import io.netty.buffer.ByteBuf;
import pt.unl.fct.di.novasys.babel.generic.ProtoMessage;
import pt.unl.fct.di.novasys.network.ISerializer;
import serializer.Serializer;
import serializer.Serialize;

public class PingMessage extends ProtoMessage {

    public static final short MSG_ID = 101;

    
    private int pingId;

    private String message;

    public PingMessage(int pingId, String message) {
        super(MSG_ID);
        this.pingId = pingId;
        this.message = message;
    }

    public int getPingId() {
        return pingId;
    }

    public String getMessage() {
        return message;
    }

    public static ISerializer<? extends ProtoMessage> serializer = Serializer.getSerializer(PingMessage.class);
}
