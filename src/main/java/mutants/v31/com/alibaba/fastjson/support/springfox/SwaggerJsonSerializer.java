package mutants.v31.com.alibaba.fastjson.support.springfox;

import java.io.IOException;
import java.lang.reflect.Type;

import mutants.v31.com.alibaba.fastjson.serializer.JSONSerializer;
import mutants.v31.com.alibaba.fastjson.serializer.ObjectSerializer;
import mutants.v31.com.alibaba.fastjson.serializer.SerializeWriter;

import springfox.documentation.spring.web.json.Json;

/**
 *
 * @author zhaiyongchao [http://blog.didispace.com]
 * @since 1.2.15
 */
public class SwaggerJsonSerializer implements ObjectSerializer {

    public final static SwaggerJsonSerializer instance = new SwaggerJsonSerializer();

    public void write(JSONSerializer serializer, //
                      Object object, Object fieldName, Type fieldType, int features) throws IOException {
        SerializeWriter out = serializer.getWriter();
        Json json = (Json) object;
        String value = json.value();
        out.write(value);
    }

}
