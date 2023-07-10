package pingpong.messages;

import io.netty.buffer.ByteBuf;
import java.util.List;
import java.util.ArrayList;
import java.lang.Class;
import java.lang.reflect.*;
import java.lang.reflect.Field;
import java.lang.annotation.*;

import pt.unl.fct.di.novasys.network.ISerializer; //TODO: put this elsewhere
import pt.unl.fct.di.novasys.babel.generic.ProtoMessage;

/* Classes who import this class must have access to @Serialize*/
@Retention(RetentionPolicy.RUNTIME)
@interface Serialize { }

public class AutoSerializer {

    private static List<Field> serialize_fields = null;
    private static Constructor<?> ctor = null;

    public static <T> void init(Class<T> c) {
        if(serialize_fields != null) {return;}

        synchronized(AutoSerializer.class) {
            if(serialize_fields != null) {return;}

            List<Field> temp_serialize_fields = new ArrayList<>();
            Field[] fields = c.getDeclaredFields();

            for(Field f : fields) {
                if(f.isAnnotationPresent(Serialize.class)) {
                    temp_serialize_fields.add(f);
                    f.setAccessible(true);
                }
            }


            try {
                Class<?>[] ctorTypes = temp_serialize_fields.stream()
                    .map(Field::getType).toArray(Class<?>[]::new);

                ctor = c.getConstructor(ctorTypes);

            } catch (Exception e) {
                e.printStackTrace();
            }

            //TODO this is concurrently confusing -> simplify
            serialize_fields = temp_serialize_fields;
        }
    }

    public static <T> void serialize(T msg, ByteBuf out) {
        AutoSerializer.init(msg.getClass()); //TODO: where can this go?

        //Automatic serialization:
        for(Field f : serialize_fields) {
            if(f.getType().equals(int.class)) {
                try {
                    int x = f.getInt(msg);
                    out.writeInt(x);
            
                } catch(Exception e) { 
                    e.printStackTrace();
                }
            }
            
            if(f.getType().equals(java.lang.String.class)) {
                try {
                    String s = (String) f.get(msg);
                    Utils.encodeUTF8(s, out);
            
                } catch(Exception e) { 
                    e.printStackTrace();
                }
            }
        }
    }
    
    public static <T> T deserialize(ByteBuf in, Class<T> c) {
        AutoSerializer.init(c); //TODO: where can this go?
    
        Object [] objs = new Object[serialize_fields.size()];
        int i = 0;

        //Automatic serialization:
        for(Field f : serialize_fields) {
            if(f.getType().equals(int.class)) {
                try {
                    objs[i] = in.readInt();
           
                } catch(Exception e) { 
                    e.printStackTrace();
                }
            }
           
            if(f.getType().equals(java.lang.String.class)) {
                try {
                    objs[i] = Utils.decodeUTF8(in);
                    
                } catch(Exception e) { 
                    e.printStackTrace();
                }
            }
            
            i++;
        }

        T msg = null; 
        try {
            msg = (T) ctor.newInstance(objs);
        } catch(Exception e) {
            e.printStackTrace();
        }

        return msg;
    }

	public static <T extends ProtoMessage> ISerializer<T> getSerializer(Class<T> c) {
		
		ISerializer<T> s = new ISerializer<T>() {
			public void serialize(T msg, ByteBuf out) {
	            AutoSerializer.serialize(msg, out);
	        }
	
	        public T deserialize(ByteBuf in) {
	            return AutoSerializer.deserialize(in, c);
	        }
		};
		
		return s;
	}
}
