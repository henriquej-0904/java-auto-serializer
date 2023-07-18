package serializer.serializers.arrays;

import java.util.Map;

import pt.unl.fct.di.novasys.network.ISerializer;
import serializer.AutoSerializer;
import serializer.serializers.arrays.primitive.BooleanArraySerializer;
import serializer.serializers.arrays.primitive.ByteArraySerializer;
import serializer.serializers.arrays.primitive.CharArraySerializer;
import serializer.serializers.arrays.primitive.DoubleArraySerializer;
import serializer.serializers.arrays.primitive.FloatArraySerializer;
import serializer.serializers.arrays.primitive.IntArraySerializer;
import serializer.serializers.arrays.primitive.LongArraySerializer;
import serializer.serializers.arrays.primitive.ShortArraySerializer;

public class ArraysSerializer {
    
    public static final Map<Class<?>, ISerializer<?>> ARRAY_PRIMITIVE_SERIALIZERS =
        Map.of(
            boolean.class, new BooleanArraySerializer(),
            byte.class, new ByteArraySerializer(),
            char.class, new CharArraySerializer(),
            short.class, new ShortArraySerializer(),
            int.class, new IntArraySerializer(),
            long.class, new LongArraySerializer(),
            float.class, new FloatArraySerializer(),
            double.class, new DoubleArraySerializer()
        );

    private final AutoSerializer autoSerializer;

    public ArraysSerializer(AutoSerializer autoSerializer)
    {
        this.autoSerializer = autoSerializer;
    }

    

    public <T> ISerializer<T[]> getArraySerializer(Class<T> componentType)
    {
        var componentSerializer = this.autoSerializer.getSerializer(componentType);
        return new ArraySerializer<T>(componentType, componentSerializer);
    }

    public static <T> ISerializer<T[]> getArraySerializer(Class<? extends T> componentType,
        ISerializer<T> componentSerializer)
    {
        return new ArraySerializer<T>(componentType, componentSerializer);
    }

}
