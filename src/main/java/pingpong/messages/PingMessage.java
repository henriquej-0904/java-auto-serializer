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

public class PingMessage extends ProtoMessage {

    @Retention(RetentionPolicy.RUNTIME)
    @interface Serialize { }

    public static final short MSG_ID = 101;

    @Serialize
    private final int pingId;

    @Serialize
    private final String message;

    private static List<Field> serialize_fields = null;
    private final List<Function_void_2> serialize_functions = new ArrayList<>();
    private final List<Function_2> deserialize_functions = new ArrayList<>();

    public PingMessage(int pingId, String message) {
        super(MSG_ID);
        this.pingId = pingId;
        this.message = message;

        if(serialize_fields != null) {return;}

        serialize_fields = new ArrayList<>();
        Field[] fields = this.getClass().getDeclaredFields();

        for(Field f : fields) {
            if(f.isAnnotationPresent(Serialize.class)) {
                //System.out.print("Teste ");
                //System.out.print(f.getName() + " ");
                //System.out.print(f.isAnnotationPresent(Serialize.class) + " ");
                //System.out.print(f.getType() + " ");
                //System.out.print("\n");

                serialize_fields.add(f);
                f.setAccessible(true);

                //Probably not needed
                //if(f.getType().equals(int.class)) {
                //    Function_void_2<Integer, ByteBuf> serialize = (i, out) -> {
                //       out.writeInt(i);
                //    };
                //    serialize_functions.add(serialize);

                //    Function_2<ByteBuf, Integer> deserialize = (in) -> {
                //        return in.readInt();
                //    };
                //    deserialize_functions.add(deserialize);
                //}

                //if(f.getType().equals(java.lang.String.class)) {
                //    Function_void_2<String, ByteBuf> serialize = (s, out) -> {
                //        Utils.encodeUTF8(s, out);
                //    };
                //    serialize_functions.add(serialize);

                //    Function_2<ByteBuf, String> deserialize = (in) -> {
                //        return Utils.decodeUTF8(in);
                //    };
                //    deserialize_functions.add(deserialize);

                //}//else class serializer not implemented

            }
        }


    }

    @FunctionalInterface
    public interface Function_void_2<T, U> {
        public void apply(T t, U u);
    }

    @FunctionalInterface
    public interface Function_2<T, R> {
        public R apply(T t);
    }

    public int getPingId() {
        return pingId;
    }

    public String getMessage() {
        return message;
    }

    public static ISerializer<? extends ProtoMessage> serializer = new ISerializer<PingMessage>() {
        public void serialize(PingMessage msg, ByteBuf out) {
            //Manually serialization:
            //out.writeInt(msg.pingId);
            //Utils.encodeUTF8(msg.message, out);

            //Automatic serialization:
            for(Field f : msg.serialize_fields) {
                if(f.getType().equals(int.class)) {
                    try {
                        int x = f.getInt(msg);
                        out.writeInt(x);
                
                    } catch(Exception e) { /*TODO*/ }
                }
                
                if(f.getType().equals(java.lang.String.class)) {
                    try {
                        String s = (String) f.get(msg);
                        Utils.encodeUTF8(s, out);
                
                    } catch(Exception e) { /*TODO*/ }
                }
            }
            
        }

        public PingMessage deserialize(ByteBuf in) {
            //Manually serialization:
            //int pingId = in.readInt();
            //String message = Utils.decodeUTF8(in);

            PingMessage msg = new PingMessage(0, null);

            //Automatic serialization:
             for(Field f : msg.serialize_fields) {
                if(f.getType().equals(int.class)) {
                    try {
                        f.setInt(msg, in.readInt());
                
                    } catch(Exception e) { /*TODO*/ }
                }
                
                if(f.getType().equals(java.lang.String.class)) {
                    try {
                        f.set(msg, Utils.decodeUTF8(in));
                        
                    } catch(Exception e) { /*TODO*/ }
                }
            }

            return msg;
        }
    };

}
