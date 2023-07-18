package serializer.serializers.common;

import java.util.Map;

import pt.unl.fct.di.novasys.network.ISerializer;

public class CommonSerializers {
    
    public static final Map<Class<?>, ISerializer<?>> COMMON_SERIALIZERS =
        Map.of(
            String.class, StringSerializer.INSTANCE
        );

}
