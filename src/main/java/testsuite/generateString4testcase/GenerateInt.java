package testsuite.generateString4testcase;

import testsuite.generateString4testcase.utl.GetSetGeneration;

/**
 * @Description:
 * @auther phantom
 * @create 2019-12-09 下午2:59
 */
public class GenerateInt implements Production{
    @Override
    public String produce() {
        return GetSetGeneration.generateGetSetMethod("maxExpense");
    }
}
