package testsuite;

/**
 * @Description: preparation for testing includes:
 *  (1) create source and follow-up test cases dirs
 *  (2) generating source and follow-up JavaBeans
 *  (3) generating source and follow-up Json String (this job is assigined to mr)
 * @auther phantom
 * @create 2019-12-10 下午5:05
 */
public class Preparation4Testing {
    public Preparation4Testing(){
        // create test cases dirs
        AllFields.createDirsForTestCases();
        AllFields.createTestCaseDirsForMR11();
        AllFields.createTestCaseDirsForMR14();
        AllFields.createTestCaseDirsForMR17();

        // create source and follow-up java bean
        new GenerateTestcases().generateSourceJavaFile();
        new GenerateTestcases().generateFollowJavaFile4MR11();
        new GenerateTestcases().generateFollowJavaFile4MR14();
        new GenerateTestcases().generateFollowJavaFile4MR17();
    }
}
