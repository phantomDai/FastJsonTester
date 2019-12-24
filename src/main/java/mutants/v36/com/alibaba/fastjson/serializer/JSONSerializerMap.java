package mutants.v36.com.alibaba.fastjson.serializer;


import mutants.v36.com.alibaba.fastjson.serializer.ObjectSerializer;
import mutants.v36.com.alibaba.fastjson.serializer.SerializeConfig;
import mutants.v36.com.alibaba.fastjson.serializer.ObjectSerializer;
import mutants.v36.com.alibaba.fastjson.serializer.SerializeConfig;

@Deprecated
public class JSONSerializerMap extends SerializeConfig {
    public final boolean put(Class<?> clazz, ObjectSerializer serializer) {
        return super.put(clazz, serializer);
    }
}
