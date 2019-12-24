package testsuite;

import Constant.Constant;
import testsuite.generateString4testcase.ProductionFactory;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import static java.io.File.separator;

/**
 * @author phantom
 * @description Generate source java file for all test cases.
 * @date 2019/11/6
 */
public class GenerateTestcases {


    public void generateSourceJavaFile(){
        List<String> testFrames = AllFields.readTestFramesFile();
        String parentDir = Constant.TESTPOOLDIR;
        ProductionFactory factory = new ProductionFactory();
        for (int i = 0; i < Constant.numberOfTestFrames; i++) {
//        for (int i = 16382; i < 16383; i++) {
            String aTestFrame = testFrames.get(i);
            String[] choices = aTestFrame.split(";");
            String tempStr = "package testsuite.pool.testcase" + String.valueOf(i) + ";\nimport java.util.*;\n" +
                    "import testsuite.TestCase;\npublic class Person extends TestCase { \n";
            for (int j = 0; j < choices.length; j++) {
                tempStr += handleString(factory.getString(choices[j]));
            }
            tempStr += "}";
            String javaFilePath = parentDir + separator + "testcase" +String.valueOf(i) + separator + "Person.java";
            writeJavaFile(javaFilePath, tempStr);
        }
    }


    public void generateFollowJavaFile4MR11(){
        List<String> testFrames = AllFields.readTestFramesFile();
        String parentDir = Constant.TESTSUITEDIR + separator + "MR11";
        ProductionFactory factory = new ProductionFactory();
        for (int i = 0; i < Constant.numberOfTestFrames; i++) {
//        for (int i = 0; i < 16383; i++) {
            String aTestFrame = testFrames.get(i);
            if (!aTestFrame.contains("13-2")){
                continue;
            }
            String testCaseDir = parentDir + separator + "testcase" + String.valueOf(i);
            File tempDir = new File(testCaseDir);
            if (!tempDir.exists()){
                tempDir.mkdir();
            }

            String[] choices = aTestFrame.split(";");
            String tempStr = "package testsuite.mr11.testcase" + String.valueOf(i) + ";\nimport java.util.*;\n" +
                    "import testsuite.TestCase;\npublic class Person extends TestCase { \n";
            for (int j = 0; j < choices.length; j++) {
                tempStr += handleString(factory.getFollowString(choices[j]));
            }
            tempStr += "}";
            String javaFilePath = parentDir + separator + "testcase" +String.valueOf(i) + separator + "Person.java";
            writeJavaFile(javaFilePath, tempStr);
        }
    }


    public void generateFollowJavaFile4MR14(){
        List<String> testFrames = AllFields.readTestFramesFile();
        String parentDir = Constant.TESTSUITEDIR + separator + "MR14";
        ProductionFactory factory = new ProductionFactory();
        for (int i = 0; i < Constant.numberOfTestFrames; i++) {
//        for (int i = 0; i < 16383; i++) {
            String aTestFrame = testFrames.get(i);
            if (!aTestFrame.contains("12-2")){
                continue;
            }
            String testCaseDir = parentDir + separator + "testcase" + String.valueOf(i);
            File tempDir = new File(testCaseDir);
            if (!tempDir.exists()){
                tempDir.mkdir();
            }


            String[] choices = aTestFrame.split(";");
            String tempStr = "package testsuite.mr14.testcase" + String.valueOf(i) + ";\nimport java.util.*;\n" +
                    "import testsuite.TestCase;\npublic class Person extends TestCase { \n";
            for (int j = 0; j < choices.length; j++) {
                tempStr += handleString(factory.getFollowString(choices[j]));
            }
            tempStr += "}";
            String javaFilePath = parentDir + separator + "testcase" +String.valueOf(i) + separator + "Person.java";
            writeJavaFile(javaFilePath, tempStr);
        }
    }

    public void generateFollowJavaFile4MR17(){
        List<String> testFrames = AllFields.readTestFramesFile();
        String parentDir = Constant.TESTSUITEDIR + separator + "MR17";
        ProductionFactory factory = new ProductionFactory();
        for (int i = 0; i < Constant.numberOfTestFrames; i++) {
//        for (int i = 0; i < 16383; i++) {
            String aTestFrame = testFrames.get(i);
            if (!aTestFrame.contains("14-2")){
                continue;
            }
            String testCaseDir = parentDir + separator + "testcase" + String.valueOf(i);
            File tempDir = new File(testCaseDir);
            if (!tempDir.exists()){
                tempDir.mkdir();
            }

            String[] choices = aTestFrame.split(";");
            String tempStr = "package testsuite.mr17.testcase" + String.valueOf(i) + ";\nimport java.util.*;\n" +
                    "import testsuite.TestCase;\npublic class Person extends TestCase { \n";
            for (int j = 0; j < choices.length; j++) {
                tempStr += handleString(factory.getFollowString(choices[j]));
            }
            tempStr += "}";
            String javaFilePath = parentDir + separator + "testcase" +String.valueOf(i) + separator + "Person.java";
            writeJavaFile(javaFilePath, tempStr);
        }
    }


    private String handleString(String str){
        if (str.equals("null")){
            return "";
        }else {
            return str;
        }
    }



    private void writeJavaFile(String path, String content){
        File f = new File(path);
        try (PrintWriter printWriter = new PrintWriter(new FileWriter(f))) {
            printWriter.write(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
//        new GenerateTestcases().generateFollowJavaFile4MR11();
//        new GenerateTestcases().generateFollowJavaFile4MR14();
//        new GenerateTestcases().generateFollowJavaFile4MR17();
        new GenerateTestcases().generateSourceJavaFile();
    }


}
