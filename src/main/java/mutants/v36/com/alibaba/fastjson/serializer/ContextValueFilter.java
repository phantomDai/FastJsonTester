package mutants.v36.com.alibaba.fastjson.serializer;

import mutants.v36.com.alibaba.fastjson.serializer.BeanContext;
import mutants.v36.com.alibaba.fastjson.serializer.SerializeFilter;
import mutants.v36.com.alibaba.fastjson.serializer.BeanContext;
import mutants.v36.com.alibaba.fastjson.serializer.SerializeFilter;

/**
 * @since 1.2.9
 *
 */
public interface ContextValueFilter extends SerializeFilter {
    Object process(BeanContext context, Object object, String name, Object value);
}
