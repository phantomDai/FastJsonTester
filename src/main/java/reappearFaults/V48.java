package reappearFaults;


/**
 * describe:
 *
 * @author phantom
 * @date 2019/12/17
 */
public class V48 {
    private float num;

    public float getNum() {
        return num;
    }

    public void setNum(float num) {
        this.num = num;
    }


    public static void main(String[] args) {
        String json="{\"num\":90.82195113}";
        V48 num= mutants.v48.com.alibaba.fastjson.JSONObject.parseObject(json,V48.class);
        System.out.println(num.getNum());
    }

}
