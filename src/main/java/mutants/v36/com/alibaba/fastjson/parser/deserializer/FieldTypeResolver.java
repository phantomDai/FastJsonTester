package mutants.v36.com.alibaba.fastjson.parser.deserializer;

import mutants.v36.com.alibaba.fastjson.parser.deserializer.ParseProcess;
import mutants.v36.com.alibaba.fastjson.parser.deserializer.ParseProcess;

import java.lang.reflect.Type;

public interface FieldTypeResolver extends ParseProcess {
    Type resolve(Object object, String fieldName);
}
