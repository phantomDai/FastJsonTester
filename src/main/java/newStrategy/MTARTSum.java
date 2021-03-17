package newStrategy;

import Constant.Constant;
import Log.WriteResult2Excel;
import Log.WriteResult2text;
import metrics.Measure;
import metrics.MetricsFactory;
import metrics.Time;
import mr.MR;
import mr.MRFactory;
import mrAndPartitionRelation.MRAndTestCaseMapping;
import testsuite.AllTestFrame;
import testsuite.GenerateJsonString;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * MTARTSum：
 * 利用ARTsum算法为MT选择原始测试用例，利用随机的方法选择蜕变关系
 * @description:
 * @author: phantom
 * @date: 2021/3/16 下午8:17
 */
public class MTARTSum implements Strategy{

    /**产生度量标准的工厂*/
    private MetricsFactory metricsFactory = new MetricsFactory();

    /**产生蜕变关系的工厂*/
    private MRFactory mrFactory = new MRFactory();

    // 得到测试用例与蜕变关系的映射关系
    private List<List<String>> mapping= new MRAndTestCaseMapping().getMapping();

    private GenerateJsonString generateJsonString = new GenerateJsonString();

    /**include the candidate test cases*/
    private List<String> candidatesTestCases;

    /**include the test cases that has been executed*/
    private List<String> executeTestCases;

    //记录结果
    WriteResult2Excel writeResult2Excel = new WriteResult2Excel();


    private int[] S;


    /**
     * 利用ARTSum算法为蜕变测试选择测试用例
     * @param repeat 重复的次数
     */
    @Override
    public void testing(int repeat) {
        Time FTime = metricsFactory.chooseCalculateTimeMethod("FTime");
        Time F2Time = metricsFactory.chooseCalculateTimeMethod("F2Time");
        Measure FMeasure = metricsFactory.chooseCalculateMeasureMethod("FMeasure");
        Measure F2Measure = metricsFactory.chooseCalculateMeasureMethod("F2Measure");

        for (int o = 0; o < 30; o++) {
            Random random = new Random(o);

            List<String> mutantNames = new ArrayList<>(Arrays.asList("v31", "v36", "v40","v48"));

            boolean executeFlag = true;

            //记录检测变异体的个数
            int numberOfkilledMutants = 0;

            //记录执行的测试用例数目
            int totalNumberOfTestCases = 0;

            //ARTSum算法对象
            ARTSum artSum = new ARTSum();

            //测试帧信息对象
            AllTestFrame allTestFrame = new AllTestFrame();

            int tempFMeasure = 0;

            long FSelect = 0;
            long FGenerate = 0;
            long FExecute = 0;

            long F2Select = 0;
            long F2Generate = 0;
            long F2Execute = 0;

            while(executeFlag){
                //选择测试用例
                totalNumberOfTestCases++; //记录测试用例的执行数目
                long startSelectTestCaseTime = System.nanoTime();
                int testCaseIndex = 0;
                String selectedMRName = null;
                if (totalNumberOfTestCases == 1){
                    testCaseIndex = random.nextInt(Constant.numberOfTestFrames);
                    selectedMRName = mapping.get(testCaseIndex).get(new Random().
                            nextInt(mapping.get(testCaseIndex).size()));
                    //初始化S
                    artSum.initializeS(allTestFrame.getOneTestFrame(testCaseIndex));
                }else {
                    //生成候选测试用例
                    artSum.updateCandidatesTestCases(allTestFrame.getTenTestFrames());
                    testCaseIndex = artSum.getSourceTestCase();
                    selectedMRName = mapping.get(testCaseIndex).get(new Random().
                            nextInt(mapping.get(testCaseIndex).size()));
                }
                long endSelectTestCaseTime = System.nanoTime();

                //记录选择测试用例的时间
                if (numberOfkilledMutants == 0){
                    FSelect += (endSelectTestCaseTime - startSelectTestCaseTime);
                }

                if (numberOfkilledMutants == 1){
                    F2Select += (endSelectTestCaseTime - startSelectTestCaseTime);
                }

                for (int i = 0; i < mutantNames.size(); i++) {
                    String mutantName = mutantNames.get(i);
                    // 产生衍生测试用例
                    long startGenerateTestCaseTime = System.nanoTime();
                    String sourceJsonString = generateJsonString.generateJsonString(testCaseIndex);
                    MR mr = mrFactory.productionMR(selectedMRName);
                    Object followJsonString = mr.obtainFollowJsonString(testCaseIndex);
                    long endGenerateTestCaseTime = System.nanoTime();
                    if (numberOfkilledMutants == 0){
                        FGenerate += (endGenerateTestCaseTime - startGenerateTestCaseTime);
                    }
                    if (numberOfkilledMutants == 1){
                        F2Generate += (endGenerateTestCaseTime - startGenerateTestCaseTime);
                    }
                    //执行原始、衍生测试用例
                    long startExecuteTestCase = System.nanoTime();
                    boolean isGenerateFollowJB = mr.obtainFollowJBFile(testCaseIndex);
                    String sourceTestCase = Constant.sourcePath + "testcase" + String.valueOf(testCaseIndex) +
                            ".Person";
                    String followTestCase = Constant.sourcePath + "testcase" + String.valueOf(testCaseIndex) +
                            ".Person";
                    try{
                        if (mutantName.equals("v31")){
                            mutants.v31.com.alibaba.fastjson.JSONObject.parseObject("{\"header\": {\"target\": \"all\",\"targetvalue\": \"all\"},\"body\": {\"title\": \"预约超时\",\"body\": \"您的预约已经超时\"}}",
                                    Class.forName("reappearFaults.V31"));
                            mutants.v31.com.alibaba.fastjson.JSONObject.parseObject("{\"header\": {\"target\": \"all\",\"targetvalue\": \"all\"},\"body\": {\"title\": \"预约超时\",\"body\": \"您的预约已经超时\"}}",
                                    Class.forName("reappearFaults.V31"));
                        }else if (mutantName.equals("v36")){
                            mutants.v36.com.alibaba.fastjson.JSONObject.parseObject("{\"header\": {\"target\": \"all\",\"targetvalue\": \"all\"},\"body\": {\"title\": \"预约超时\",\"body\": \"您的预约已经超时\"}}",
                                    Class.forName("reappearFaults.V31"));
                            mutants.v36.com.alibaba.fastjson.JSONObject.parseObject("{\"header\": {\"target\": \"all\",\"targetvalue\": \"all\"},\"body\": {\"title\": \"预约超时\",\"body\": \"您的预约已经超时\"}}",
                                    Class.forName("reappearFaults.V31"));
                        }else if (mutantName.equals("v40")){
                            mutants.v40.com.alibaba.fastjson.JSONObject.parseObject("{\"header\": {\"target\": \"all\",\"targetvalue\": \"all\"},\"body\": {\"title\": \"预约超时\",\"body\": \"您的预约已经超时\"}}",
                                    Class.forName("reappearFaults.V31"));
                            mutants.v40.com.alibaba.fastjson.JSONObject.parseObject("{\"header\": {\"target\": \"all\",\"targetvalue\": \"all\"},\"body\": {\"title\": \"预约超时\",\"body\": \"您的预约已经超时\"}}",
                                    Class.forName("reappearFaults.V31"));
                        }else {
                            mutants.v48.com.alibaba.fastjson.JSONObject.parseObject("{\"header\": {\"target\": \"all\",\"targetvalue\": \"all\"},\"body\": {\"title\": \"预约超时\",\"body\": \"您的预约已经超时\"}}",
                                    Class.forName("reappearFaults.V31"));
                            mutants.v48.com.alibaba.fastjson.JSONObject.parseObject("{\"header\": {\"target\": \"all\",\"targetvalue\": \"all\"},\"body\": {\"title\": \"预约超时\",\"body\": \"您的预约已经超时\"}}",
                                    Class.forName("reappearFaults.V31"));
                        }
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }

                    long endExecuteTestCase = System.nanoTime();
                    if (numberOfkilledMutants == 0){
                        FExecute += (endExecuteTestCase - startExecuteTestCase);
                    }
                    if (numberOfkilledMutants == 1){
                        F2Execute += (endExecuteTestCase - startExecuteTestCase);
                    }

                    //验证测试结果
                    if (mr.verifyResults(testCaseIndex, mutantName)){
                        mutantNames.remove(mutantName);

                        if (numberOfkilledMutants == 0){
                            FTime.addSelectTime(FSelect);
                            FTime.addGenerateTime(FGenerate);
                            FTime.addExecuteTime(FExecute);

                            FMeasure.addMeasure(totalNumberOfTestCases);
                            tempFMeasure = totalNumberOfTestCases;
                        }
                        if (numberOfkilledMutants == 1){
                            F2Time.addSelectTime(F2Select);
                            F2Time.addGenerateTime(F2Generate);
                            F2Time.addExecuteTime(F2Execute);

                            F2Measure.addMeasure(totalNumberOfTestCases - tempFMeasure);
                        }
                        numberOfkilledMutants++;
                    }

                }
                if (numberOfkilledMutants == 2){
                    executeFlag = false;
                }
            }
        }
        //记录结果
        new WriteResult2text().recordResult("MTARTSumPlusRandom", repeat,
                FMeasure.getAverageMeasure(), F2Measure.getAverageMeasure());

        writeResult2Excel.initializeMeasureArray(FMeasure.getMeasureArray(),
                F2Measure.getMeasureArray());

        writeResult2Excel.initializeMeasureAverageAndVariance(FMeasure.getAverageMeasure(),
                F2Measure.getAverageMeasure(),FMeasure.getVarianceMeasure(),F2Measure.getVarianceMeasure());

        writeResult2Excel.getTimeArray(FTime.getSelectTestCaseArray(), FTime.getGenerateTestcaseArray(),
                FTime.getExecuteTestcaseArray(),F2Time.getSelectTestCaseArray(),F2Time.getGenerateTestcaseArray(),
                F2Time.getExecuteTestcaseArray());

        writeResult2Excel.getTimeAverage(FTime.getAverageSelectTime(), FTime.getAverageGenerateTime(),
                FTime.getAverageExecuteTime(),F2Time.getAverageSelectTime(),F2Time.getAverageGenerateTime(),
                F2Time.getAverageExecuteTime());

        writeResult2Excel.getTimeVariance(FTime.getVarianceSelectTime(),FTime.getVarianceGenerateTime(),
                FTime.getVarianceExecuteTime(),F2Time.getVarianceSelectTime(),F2Time.getVarianceGenerateTime(),
                F2Time.getVarianceExecuteTime());

        writeResult2Excel.writeResult("MTARTSumPlusRandom", repeat);
    }

    public static void main(String[] args) {
        MTARTSum mt = new MTARTSum();
        for (int i = 0; i < 30; i++) {
            mt.testing(i);
        }
    }
}
