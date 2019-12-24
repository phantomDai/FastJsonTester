package reappearFaults;


/**
 * describe: 超出float的精度范围结果出现负数
 *
 * @author phantom
 * @date 2019/12/17
 */
public class V40 {
    private float age;

    public float getAge() {
        return age;
    }

    public void setAge(float age) {
        this.age = age;
    }


    public static void main(String[] args) {
        V40 v40 = mutants.v40.com.alibaba.fastjson.JSONObject.parseObject("{\"age\":\"0.9390308260917664\"}", V40.class);
        System.out.println(v40.getAge());
    }
}
