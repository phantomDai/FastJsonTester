package testsuite.generateString4testcase;

import testsuite.generateString4testcase.utl.GetSetGeneration;

/**
 * @Description:
 * @auther phantom
 * @create 2019-12-09 下午3:06
 */
public class GenerateList implements Production {
    @Override
    public String produce() {
        return GetSetGeneration.generateGetSetMethod("favoriteSports");
    }

    public String produceFollow() {
        return GetSetGeneration.generateGetSetMethod4Follow("favoriteSports");
    }
}
