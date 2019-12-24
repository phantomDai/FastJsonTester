package reappearFaults;


import java.lang.reflect.Type;
import java.util.Map;

/**
 * describe: this fault can be detected by test case when this test case has two Map object.
 * When a test case detect this fault, console throw-out a exception.
 * @author phantom
 * @date 2019/12/17
 */
public class V31 {

    private Map<String, String> body;

    private Map<String, String> header;

    public Map<String, String> getBody() {
        return body;
    }

    public void setBody(Map<String, String> body) {
        this.body = body;
    }

    public Map<String, String> getHeader() {
        return header;
    }

    public void setHeader(Map<String, String> header) {
        this.header = header;
    }

    public static void main(String[] args) {
        String str = new String("{\"header\": {\"target\": \"all\",\"targetvalue\": \"all\"},\"body\": {\"title\": \"预约超时\",\"body\": \"您的预约已经超时\"}}");

        Class v = null;
        try{
            v = Class.forName("reappearFaults.V31");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        V31 v31 = mutants.v31.com.alibaba.fastjson.JSONObject.parseObject(str, (Type) v);
        Map<String, String> list = v31.getBody();
        System.out.println(list.get("body"));
    }
}
