package mutants.v36.com.alibaba.fastjson.parser.deserializer;

import mutants.v36.com.alibaba.fastjson.parser.deserializer.ParseProcess;
import mutants.v36.com.alibaba.fastjson.parser.deserializer.ParseProcess;

import java.lang.reflect.Type;

/**
 * @author wenshao[szujobs@hotmail.com]
 * @since 1.1.34
 */
public interface ExtraTypeProvider extends ParseProcess {

    Type getExtraType(Object object, String key);
}
