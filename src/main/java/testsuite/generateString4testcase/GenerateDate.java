package testsuite.generateString4testcase;

import testsuite.generateString4testcase.utl.GetSetGeneration;

/**
 * @Description:
 * @auther phantom
 * @create 2019-12-09 下午3:03
 */
public class GenerateDate implements Production {
    @Override
    public String produce() {
        return GetSetGeneration.generateGetSetMethod("birthday");
    }
}
