package mutants.v36.com.alibaba.fastjson.spi;

import mutants.v36.com.alibaba.fastjson.parser.ParserConfig;
import mutants.v36.com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import mutants.v36.com.alibaba.fastjson.serializer.ObjectSerializer;
import mutants.v36.com.alibaba.fastjson.serializer.SerializeConfig;

public interface Module {
    ObjectDeserializer createDeserializer(ParserConfig config, Class type);
    ObjectSerializer createSerializer(SerializeConfig config, Class type);
}
