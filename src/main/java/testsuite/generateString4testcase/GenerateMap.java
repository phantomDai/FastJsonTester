package testsuite.generateString4testcase;

import testsuite.generateString4testcase.utl.GetSetGeneration;

/**
 * @Description:
 * @auther phantom
 * @create 2019-12-09 下午3:06
 */
public class GenerateMap implements Production {
    @Override
    public String produce() {
        return GetSetGeneration.generateGetSetMethod("family");
    }


    public String produceFollow(){
        return GetSetGeneration.generateGetSetMethod4Follow("family");
    }
}
