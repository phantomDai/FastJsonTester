package mr.utl;

import java.util.ArrayList;
import java.util.List;

/**
 * describe:
 * 根据测试结果返回每一个MR中的verifyResults接口的结果
 * @author phantom
 * @date 2019/12/18
 */
public class GetMRReturnResult {

    public static List<Object> getResult(boolean flag, String name){
        List<Object> results = new ArrayList<>();
        if (flag){
            results.add(flag);
            results.add(name);
            return results;
        }else {
            results.add(false);
            results.add("null");
            return results;
        }

    }


}
