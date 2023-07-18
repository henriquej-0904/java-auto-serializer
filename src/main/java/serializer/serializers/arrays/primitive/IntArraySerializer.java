package serializer.serializers.arrays.primitive;

import java.io.IOException;

import io.netty.buffer.ByteBuf;
import pt.unl.fct.di.novasys.network.ISerializer;

public class IntArraySerializer implements ISerializer<int[]> {

    public static final int[] EMPTY_ARRAY = new int[0];

    @Override
    public void serialize(int[] t, ByteBuf out) throws IOException {
        out.writeInt(t.length);

        for (int v : t) {
            out.writeInt(v);
        }
    }

    @Override
    public int[] deserialize(ByteBuf in) throws IOException {
        int size = in.readInt();

        if (size < 0)
            throw new IOException("Invalid size for array: " + size);

        if (size == 0)
            return EMPTY_ARRAY;

        int[] array = new int[size];

        for (int i = 0; i < size; i++)
            array[i] = in.readInt();

        return array;
    }
    
}
