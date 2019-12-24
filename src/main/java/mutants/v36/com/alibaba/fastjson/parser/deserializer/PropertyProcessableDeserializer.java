package mutants.v36.com.alibaba.fastjson.parser.deserializer;

import mutants.v36.com.alibaba.fastjson.JSONException;
import mutants.v36.com.alibaba.fastjson.parser.DefaultJSONParser;
import mutants.v36.com.alibaba.fastjson.parser.JSONToken;
import mutants.v36.com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import mutants.v36.com.alibaba.fastjson.parser.deserializer.PropertyProcessable;
import mutants.v36.com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;

import java.lang.reflect.Type;

/**
 * Created by wenshao on 15/07/2017.
 */
public class PropertyProcessableDeserializer implements ObjectDeserializer {
    public final Class<mutants.v36.com.alibaba.fastjson.parser.deserializer.PropertyProcessable> type;

    public PropertyProcessableDeserializer(Class<mutants.v36.com.alibaba.fastjson.parser.deserializer.PropertyProcessable> type) {
        this.type = type;
    }

    public <T> T deserialze(DefaultJSONParser parser, Type type, Object fieldName) {
        PropertyProcessable processable;
        try {
            processable = this.type.newInstance();
        } catch (Exception e) {
            throw new JSONException("craete instance error");
        }

        Object object =parser.parse(processable, fieldName);

        return (T) object;
    }

    public int getFastMatchToken() {
        return JSONToken.LBRACE;
    }
}
