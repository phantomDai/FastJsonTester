package mutants.v36.com.alibaba.fastjson.parser.deserializer;

import java.lang.reflect.Type;

import mutants.v36.com.alibaba.fastjson.parser.DefaultJSONParser;
import mutants.v36.com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import mutants.v36.com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;

public abstract class ContextObjectDeserializer implements ObjectDeserializer {
    public <T> T deserialze(DefaultJSONParser parser, Type type, Object fieldName) {
        return deserialze(parser, type, fieldName, null, 0);
    }
    
    public abstract <T> T deserialze(DefaultJSONParser parser, Type type, Object fieldName, String format, int features); 
}
