package serializer;

import org.junit.Test;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import pt.unl.fct.di.novasys.network.ISerializer;

public class TestAutoSerializer {
    
    @Test
    public void testAutoSerializer()
    {
        AutoSerializer s = new AutoSerializer.Builder().build();

        var buff = Unpooled.buffer(1000);

        ISerializer<Integer> s1 = s.getSerializer(int.class);

        ISerializer<int[]> s2 = s.getSerializer(int[].class);

        ISerializer<int[]> s3 = s.getArraySerializerInt();

        ISerializer<Integer[]> s4 = s.getArraySerializerGenericFromComponentType(Integer.class);
    
        try {

            var v1 = 10;
            var v2 = new int[] {0, 1, 2};
            var v3 = new int[] {3, 4, 5};
            var v4 = new Integer[] {6, 7, 8};

            s1.serialize(v1, buff);
            s2.serialize(v2, buff);
            s3.serialize(v3, buff);
            s4.serialize(v4, buff);

            buff.readerIndex(0);

            assertTrue(v1 == s1.deserialize(buff));
            assertTrue(Arrays.equals(v2, s2.deserialize(buff)));
            assertTrue(Arrays.equals(v3, s3.deserialize(buff)));
            assertTrue(Arrays.equals(v4, s4.deserialize(buff)));
        } catch (Exception e) {
            e.printStackTrace();
            assertTrue(false);
        }
        
    }

}
