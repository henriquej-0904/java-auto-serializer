package serializer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.tuple.Pair;

import pt.unl.fct.di.novasys.network.ISerializer;


/**
 * Usage:
 * {@code Autor}
 */
public class AutoSerializer {
    
    private final Map<String, ISerializer<?>> serializers;

    protected AutoSerializer(Map<String, ISerializer<?>> customSerializers)
    {
        this.serializers = Collections.synchronizedMap(customSerializers);
    }


    public <T> ISerializer<T> getSerializer(Class<T> type)
    {
        return (ISerializer<T>) this.serializers.get(type.getName());
    }

    /**
     * A builder for the AutoSerializer class
     * where a user can configure to its needs.
     */
    public static class Builder {

        private final Map<String, ISerializer<?>> customSerializers;

        /**
         * Create a new default Builder
         * @param customSerializers
         */
        public Builder() {
            // this.registeredClasses = new ArrayList<>(100);
            this.customSerializers = new HashMap<>();
        }

        /**
         * Add a custom serializer for a specific class.
         * This method can be used for users that need a more efficient
         * serializer for a specific case.
         * The serializer for this class will be used for classes that
         * need to serialize objects of this type.
         * @param <T> The generic type of the class
         * @param type The class object
         * @param serializer The serializer for this type
         * @return The updated builder
         */
        public <T> Builder addCustomSerializer(Class<T> type, ISerializer<T> serializer) {
            this.customSerializers.put(type.getName(), serializer);
            return this;
        }

        /**
         * Register a class for serialization.
         * This is an optional operation that modifies how
         * a serializer is obtained for a particular class. <p>
         * 
         * When {@link #build()} is called, all required serializers for
         * all registered classes through this method are implemented.
         * This means that all serializers for that classes are simply
         * returned when calling {@code AutoSerializer.getSerializer(type)}. <p>
         * 
         * If a class is not registered, you can still get a serializer for it
         * but every time the {@code AutoSerializer.getSerializer(type)} is called
         * a new one will be created.
         * 
         * @param types The classes to register for serialization
         * @return The updated builder
         */
        /* public Builder registerClasses(Class<?>... types)
        {
            for (var type : types) {
                this.registeredClasses.add(type);
            }

            return this;
        } */

        public AutoSerializer build() {
            return new AutoSerializer(this.customSerializers);
        }
    }

}
