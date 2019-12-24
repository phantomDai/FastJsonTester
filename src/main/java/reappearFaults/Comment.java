package reappearFaults;
import java.lang.reflect.Type;
/**
 * describe:
 *
 * @author phantom
 * @date 2019/12/23
 */
public class Comment {
    private int age;
    private boolean flag;

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public static void main(String[] args) throws ClassNotFoundException {
        String str = "{\"age\":25,/**comments**/\"falg\":true/**comments**/}";
        Comment comment = mutants.v31.com.alibaba.fastjson.JSONObject.parseObject(str,
                (Type) Class.forName("reappearFaults.Comment"));
        System.out.println(comment.getAge());
    }
}
