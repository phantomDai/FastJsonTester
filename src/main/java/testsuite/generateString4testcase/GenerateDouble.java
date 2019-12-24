package testsuite.generateString4testcase;

import testsuite.generateString4testcase.Production;
import testsuite.generateString4testcase.utl.GetSetGeneration;

/**
 * @Description:
 * @auther phantom
 * @create 2019-12-09 下午2:57
 */
public class GenerateDouble implements Production {
    @Override
    public String produce() {
        return GetSetGeneration.generateGetSetMethod("property");
    }
}
