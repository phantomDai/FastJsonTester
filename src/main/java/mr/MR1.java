package mr;

import Constant.Constant;
import mr.utl.GetMRReturnResult;
import testsuite.GenerateJsonString;

import java.util.List;


/**
 * @Description: 该 MR 在每一个原始测试用例的JSON文件的属性后面添加注释
 * the following functions:
 * (1) get Json String of ftc (衍生测试用例的JSON字符串）
 * (2) get JB file (衍生测试用例的java文件)
 * (5) verify the testing results
 * (6) return a list that include: time of generation test case and execution test case,
 * and testing result (true or false)
 *
 * @auther phantom
 * @create 2019-12-09 下午10:15
 */
public class MR1 implements MR{

    // 为原始和衍生测试用例产生Json字符串
    private GenerateJsonString generateJsonString;


    /**
     * 根据测试用例以及蜕变关系生成衍生测试用例
     * @param testCaseIndex 测试用例的编号
     * @return Json字符串
     */
    public String obtainFollowJsonString(int testCaseIndex){
        return Constant.getGeJsonObject().generateFollowJsonString4MR1(testCaseIndex);
    }

    /**
     * 获得衍生测试用例的JB文件
     * @param testCaseIndex 测试用例的编号
     * @return false：表示原始与衍生测试用例的JB文件一致；true: 表示衍生测试用例与原始测试用例不一致
     */
    public boolean obtainFollowJBFile(int testCaseIndex){
        return false;
    }

    /**
     *includes two things:(1)true or false;(2)the name of fault.
     *true means this test case detected a fault
     *false means this tets case did not detecte a fault
     *if this test case did not detecte a fault the name is set to "null"
     *
     * @param testCaseIndex 测试用例的编号
     * @return false：没有揭示故障；true：揭示故障
     */
    public boolean verifyResults(int testCaseIndex, String mutantName){
        return false;
    }

}
