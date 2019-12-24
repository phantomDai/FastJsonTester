package mutants.v36.com.alibaba.fastjson.serializer;

import mutants.v36.com.alibaba.fastjson.serializer.BeanContext;
import mutants.v36.com.alibaba.fastjson.serializer.JSONSerializer;
import mutants.v36.com.alibaba.fastjson.serializer.ObjectSerializer;
import mutants.v36.com.alibaba.fastjson.serializer.BeanContext;
import mutants.v36.com.alibaba.fastjson.serializer.JSONSerializer;
import mutants.v36.com.alibaba.fastjson.serializer.ObjectSerializer;

import java.io.IOException;

public interface ContextObjectSerializer extends ObjectSerializer {
    void write(JSONSerializer serializer, //
               Object object, //
               BeanContext context) throws IOException;
}
