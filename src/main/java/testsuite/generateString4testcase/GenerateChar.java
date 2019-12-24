package testsuite.generateString4testcase;

import testsuite.generateString4testcase.utl.GetSetGeneration;

/**
 * @Description:
 * @auther phantom
 * @create 2019-12-09 下午3:02
 */
public class GenerateChar implements Production {
    @Override
    public String produce() {
        return GetSetGeneration.generateGetSetMethod("sex");
    }
}
