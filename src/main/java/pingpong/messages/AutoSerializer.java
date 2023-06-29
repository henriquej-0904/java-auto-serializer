package pingpong.messages;

import io.netty.buffer.ByteBuf;
import java.util.List;
import java.util.ArrayList;
import java.lang.Class;
import java.lang.reflect.*;
import java.lang.reflect.Field;
import java.lang.annotation.*;

/* Classes who import this class must have access to @Serialize*/
@Retention(RetentionPolicy.RUNTIME)
@interface Serialize { }

public class AutoSerializer {

    private static List<Field> serialize_fields = null;

    public static void init(PingMessage pm) {
        if(serialize_fields != null) {return;}

        synchronized(AutoSerializer.class) {
            if(serialize_fields != null) {return;}

            List<Field> temp_serialize_fields = new ArrayList<>();
            Field[] fields = pm.getClass().getDeclaredFields();

            for(Field f : fields) {
                if(f.isAnnotationPresent(Serialize.class)) {
                    temp_serialize_fields.add(f);
                    f.setAccessible(true);
                }
            }

            serialize_fields = temp_serialize_fields;
        }
    }

    public static void serialize(PingMessage msg, ByteBuf out) {
        AutoSerializer.init(msg); //TODO: where can this go?

        //Automatic serialization:
        for(Field f : serialize_fields) {
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
    
    public static PingMessage deserialize(ByteBuf in) {
        PingMessage msg = new PingMessage(0, null);
        AutoSerializer.init(msg); //TODO: where can this go?
    
        //Automatic serialization:
         for(Field f : serialize_fields) {
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
}
