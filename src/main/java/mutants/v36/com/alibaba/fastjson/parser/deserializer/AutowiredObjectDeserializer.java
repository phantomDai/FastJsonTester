package mutants.v36.com.alibaba.fastjson.parser.deserializer;

import mutants.v36.com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import mutants.v36.com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;

import java.lang.reflect.Type;
import java.util.Set;


public interface AutowiredObjectDeserializer extends ObjectDeserializer {
	Set<Type> getAutowiredFor();
}
