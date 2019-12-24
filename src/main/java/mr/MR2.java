package mr;

import Constant.Constant;
import mr.MR;
import mr.utl.GetMRReturnResult;

import java.util.List;

/**
 * describe:
 *
 * @author phantom
 * @date 2019/12/18
 */
public class MR2 implements MR {

    /**
     * 获得衍生测用例的Json文件
     * @param testCaseIndex 原始测试用例的编号
     * @return 衍生测试用例的JOSN字符串
     *
     */
    @Override
    public byte[] obtainFollowJsonString(int testCaseIndex) {
        return Constant.getGeJsonObject().generateFollowJsonString4MR2(testCaseIndex);
    }

    /**
     * 获得衍生测试用例的JB文件
     * @param testCaseIndex 测试用例的编号
     * @return
     */
    @Override
    public boolean obtainFollowJBFile(int testCaseIndex) {
        return false;
    }

    /***
     * 验证原始测试用例与衍生测试用例的执行结果
     * @param testCaseIndex 测试用例的编号
     * @return 是否揭示故障
     */
    @Override
    public boolean verifyResults(int testCaseIndex, String mutantName) {
        return false;
    }
}
