package mutants.v36.com.alibaba.fastjson.parser.deserializer;

import mutants.v36.com.alibaba.fastjson.parser.deserializer.ParseProcess;
import mutants.v36.com.alibaba.fastjson.parser.deserializer.ParseProcess;

/**
 * 
 * @author wenshao[szujobs@hotmail.com]
 * @since 1.1.34
 */
public interface ExtraProcessor extends ParseProcess {

    void processExtra(Object object, String key, Object value);
}
