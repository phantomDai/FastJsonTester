package reappearFaults;



/**
 * describe: 反序列化枚举错误：当枚举值不存在是不抛出异常，解决的方法是错误的值输出“NULL”。
 * If the case has the type of enum, and this object has a wrong value,
 * this fault can be detected.
 * @author phantom
 * @date 2019/12/17
 */
public class V36 {
    public enum Code{
        SUCCESS, FAILURE
    }
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private Code code;

    public Code getCode() {
        return code;
    }

    public void setCode(Code code) {
        this.code = code;
    }


    public static void main(String[] args) {
        String json = "{\"code\":\"ERROR\", \"id\":3}";

        System.out.println(mutants.v36.com.alibaba.fastjson.JSON.
                toJSONString(mutants.v36.com.alibaba.fastjson.
                        JSONObject.parseObject(json, V36.class)));


    }
}
