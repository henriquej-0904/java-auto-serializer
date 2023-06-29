package pingpong.messages;

import io.netty.buffer.ByteBuf;
import pt.unl.fct.di.novasys.babel.generic.ProtoMessage;
import pt.unl.fct.di.novasys.network.ISerializer;

import java.util.List;
import java.util.ArrayList;
import java.util.function.Function;
import java.lang.Class;
import java.lang.reflect.*;
import java.lang.reflect.Field;
import java.lang.annotation.*;

import pingpong.messages.AutoSerializer;

public class PingMessage extends ProtoMessage {

    public static final short MSG_ID = 101;

    @Serialize
    private int pingId;

    @Serialize
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

    public static ISerializer<? extends ProtoMessage> serializer = new ISerializer<PingMessage>() {
        public void serialize(PingMessage msg, ByteBuf out) {
            AutoSerializer.serialize(msg, out);
        }

        public PingMessage deserialize(ByteBuf in) {
            return AutoSerializer.deserialize(in, PingMessage.class);
        }
    };

}
