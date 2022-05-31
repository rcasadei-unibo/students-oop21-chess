package io;


import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.Objects;
import static io.JsonUtils.getMapper;
/**
 * 
 *

 */
public class JsonDeserializerImpl implements JsonDeserializer {
    private final Class<?> className;
    /**
     * 
     * @param className the name of the class to be deserialized
     */
    public JsonDeserializerImpl(final Class<?> className) {
            this.className = Objects.requireNonNull(className);
    }
    @Override
    public Object deserialize(final String str) throws JsonProcessingException {
            return getMapper().readValue(str, className);
    }
}
