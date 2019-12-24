package testsuite;

import Constant.Constant;

import java.io.*;
import java.util.*;

import static java.io.File.separator;

/**
 * @Description: this class is responsible for generating the following materials:
 * (1) all fields;
 * (2) all test frames;
 * (3) create dirs for test cases
 * @auther phantom
 * @create 2019-12-09 上午10:33
 */
public class AllFields {

    private static final List<String> allFieldValue = new ArrayList<>(Arrays.asList("1.78",
            "800.00", "3000", "25", "10000", "10000000", "true", "\"m\"", "\"1993-03-01 00:00:00\"",
            "\"phantom\"", "\"Car\"", "{\"father\":\"Xinping Dai\", \"mother\":\"Aixiang Jia\"}", "[\"footbal\", \"tennis\", \"badminton\"]",
            "[\"caffe\", \"cake\", \"ice cream\"]"));

    private static final List<String> allFieldName = new ArrayList<>(Arrays.asList("height",
            "property","salary","age","maxExpense","id","isMarried","sex","birthday","name",
            "vehicle","family","favoriteSports","favoriteFoods"));

    private static final List<String> allFollowFieldValue = new ArrayList<>(Arrays.asList("\"1.78\"",
            "\"800.00\"", "\"3000\"", "\"25\"", "\"10000\"", "\"10000000\"", "\"true\"", "\"m\"", "\"1993-03-01 00:00:00\"",
            "\"phantom\"", "\"Car\"", "{\"father\":\"Xinping Dai\", \"mother\":\"Aixiang Jia\"}", "[\"footbal\", \"tennis\", \"badminton\"]",
            "[\"caffe\", \"cake\", \"ice cream\"]"));

    private static final List<String> allFieldValue4MR11 = new ArrayList<>(Arrays.asList("1.78",
            "800.00", "3000", "25", "10000", "10000000", "true", "\"m\"", "\"1993-03-01 00:00:00\"",
            "\"phantom\"", "\"Car\"", "{\"father\":\"Xinping Dai\", \"mother\":\"Aixiang Jia\"}", "[\"footbal\", \"tennis\"]",
            "[\"caffe\", \"cake\", \"ice cream\"]"));

    private static final List<String> allFieldName4MR11 = new ArrayList<>(Arrays.asList("height",
            "property","salary","age","maxExpense","id","isMarried","sex","birthday","name",
            "vehicle","family","favoriteSports","favoriteFoods"));

    private static final List<String> allFieldValue4MR14 = new ArrayList<>(Arrays.asList("1.78",
            "800.00", "3000", "25", "10000", "10000000", "true", "\"m\"", "\"1993-03-01 00:00:00\"",
            "\"phantom\"", "\"Car\"", "{\"father\":\"Xinping Dai\"}", "[\"footbal\", \"tennis\", \"badminton\"]",
            "[\"caffe\", \"cake\", \"ice cream\"]"));

    private static final List<String> allFieldName4MR14 = new ArrayList<>(Arrays.asList("height",
            "property","salary","age","maxExpense","id","isMarried","sex","birthday","name",
            "vehicle","family1","favoriteSports","favoriteFoods"));

    private static final List<String> allFieldValue4MR17 = new ArrayList<>(Arrays.asList("1.78",
            "800.00", "3000", "25", "10000", "10000000", "true", "\"m\"", "\"1993-03-01 00:00:00\"",
            "\"phantom\"", "\"Car\"", "{\"father\":\"Xinping Dai\", \"mother\":\"Aixiang Jia\"}", "[\"footbal\", \"tennis\", \"badminton\"]",
            "[\"caffe\", \"cake\"]"));

    private static final List<String> allFieldName4MR17 = new ArrayList<>(Arrays.asList("height",
            "property","salary","age","maxExpense","id","isMarried","sex","birthday","name",
            "vehicle","family","favoriteSports","favoriteFoods1"));

    public static List<String> getAllFieldValue4MR17() {
        return allFieldValue4MR17;
    }

    public static List<String> getAllFieldName4MR17() {
        return allFieldName4MR17;
    }

    public static List<String> getAllFieldValue4MR14() {
        return allFieldValue4MR14;
    }

    public static List<String> getAllFieldName4MR14() {
        return allFieldName4MR14;
    }

    public static List<String> getAllFieldValue4MR11() {
        return allFieldValue4MR11;
    }

    public static List<String> getAllFieldName4MR11() {
        return allFieldName4MR11;
    }

    public static List<String> getAllFieldValue() {
        return allFieldValue;
    }

    public static List<String> getAllFollowFieldValue(){
        return allFollowFieldValue;
    }

    public static List<String> getAllFieldName() {
        return allFieldName;
    }

    /**
     * get all test frames
     * @return a map: key is the index of tets frame; value is the specific tets frame
     */
    public static void getAllTestFrames(){
        List<String> content = new ArrayList<>();

        for (int a = 1; a < 3; a++) {
            for (int b = 1; b < 3; b++) {
                for (int c = 1; c < 3; c++) {
                    for (int d = 1; d < 3; d++) {
                        for (int e = 1; e < 3; e++) {
                            for (int f = 1; f < 3; f++) {
                                for (int g = 1; g < 3; g++) {
                                    for (int h = 1; h < 3; h++) {
                                        for (int i = 1; i < 3; i++) {
                                            for (int j = 1; j < 3; j++) {
                                                for (int k = 1; k < 3; k++) {
                                                    for (int l = 1; l < 3; l++) {
                                                        for (int m = 1; m < 3; m++) {
                                                            for (int n = 1; n < 3; n++) {
                                                                String temp = "";
                                                                if (a == 1){
                                                                    temp += "1-1;";
                                                                }else {
                                                                    temp += "1-2;";
                                                                }

                                                                if (b == 1){
                                                                    temp += "2-1;";
                                                                }else {
                                                                    temp += "2-2;";
                                                                }

                                                                if (c == 1){
                                                                    temp += "3-1;";
                                                                }else {
                                                                    temp += "3-2;";
                                                                }

                                                                if (d == 1){
                                                                    temp += "4-1;";
                                                                }else {
                                                                    temp += "4-2;";
                                                                }

                                                                if (e == 1){
                                                                    temp += "5-1;";
                                                                }else {
                                                                    temp += "5-2;";
                                                                }

                                                                if (f == 1){
                                                                    temp += "6-1;";
                                                                }else {
                                                                    temp += "6-2;";
                                                                }

                                                                if (g == 1){
                                                                    temp += "7-1;";
                                                                }else {
                                                                    temp += "7-2;";
                                                                }

                                                                if (h == 1){
                                                                    temp += "8-1;";
                                                                }else {
                                                                    temp += "8-2;";
                                                                }

                                                                if (i == 1){
                                                                    temp += "9-1;";
                                                                }else {
                                                                    temp += "9-2;";
                                                                }

                                                                if (j == 1){
                                                                    temp += "10-1;";
                                                                }else {
                                                                    temp += "10-2;";
                                                                }

                                                                if (k == 1){
                                                                    temp += "11-1;";
                                                                }else {
                                                                    temp += "11-2;";
                                                                }

                                                                if (l == 1){
                                                                    temp += "12-1;";
                                                                }else {
                                                                    temp += "12-2;";
                                                                }

                                                                if (m == 1){
                                                                    temp += "13-1;";
                                                                }else {
                                                                    temp += "13-2;";
                                                                }

                                                                if (n == 1){
                                                                    temp += "14-1;";
                                                                }else {
                                                                    temp += "14-2;";
                                                                }
                                                                content.add(temp + "\n");
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        File file = new File(Constant.TESTSUITEDIR + separator + "TestFrames.txt");

        try{
            PrintWriter printWriter = new PrintWriter(new FileWriter(file));
            for (String aline : content){
                printWriter.write(aline);
            }
            printWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /***
     * create dirs for all test cases
     */
    public static void createDirsForTestCases(){
        String poolPath = Constant.TESTPOOLDIR;


        for (int i = 0; i < Constant.numberOfTestFrames; i++) {
            new File(poolPath + separator + "testcase" +String.valueOf(i)).mkdir();
        }

    }

    public static void createTestCaseDirsForMR11(){
        List<String> testFrames = readTestFramesFile();
        for (int i = 0; i < Constant.numberOfTestFrames; i++) {
            String aTestFrame = testFrames.get(i);
            if (aTestFrame.equals("13-2")){
                new File(Constant.TESTSUITEDIR + separator + "mr11" +
                        separator + String.valueOf(i)).mkdir();
            }else {
                continue;
            }
        }
    }

    public static void createTestCaseDirsForMR14(){
        List<String> testFrames = readTestFramesFile();
        for (int i = 0; i < Constant.numberOfTestFrames; i++) {
            String aTestFrame = testFrames.get(i);
            if (aTestFrame.equals("12-2")){
                new File(Constant.TESTSUITEDIR + separator + "mr14" +
                        separator + String.valueOf(i)).mkdir();
            }else {
                continue;
            }
        }
    }

    public static void createTestCaseDirsForMR17(){
        List<String> testFrames = readTestFramesFile();
        for (int i = 0; i < Constant.numberOfTestFrames; i++) {
            String aTestFrame = testFrames.get(i);
            if (aTestFrame.equals("14-2")){
                new File(Constant.TESTSUITEDIR + separator + "mr17" +
                        separator + String.valueOf(i)).mkdir();
            }else {
                continue;
            }
        }
    }







    /***
     * read all test frames from the file included all test frames
     * @return a list includes all test frames
     */
    public static List<String> readTestFramesFile(){
        List<String> testFrames = new ArrayList<>();
        File file = new File(Constant.TESTFRAMEFILE);
        try (BufferedReader br = new BufferedReader(new FileReader(file))){
            String temp = "";
            while ((temp = br.readLine()) != null){
                testFrames.add(temp);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return testFrames;
    }

    public static void main(String[] args) {
//        AllFields.getAllTestFrames();
        AllFields.createDirsForTestCases();

    }
}
