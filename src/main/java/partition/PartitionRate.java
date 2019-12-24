package partition;

import Constant.Constant;
import mr.MR;
import mr.MRFactory;
import mrAndPartitionRelation.MRAndTestCaseMapping;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * describe:
 * 主要获得分区的失效率
 * @author phantom
 * @date 2019/12/20
 */
public class PartitionRate {

    /**
     * 获取每一个分区的失效率
     * @return 每一个分区的失效率
     */
    public List<Double> getPartitionRate(){
        MRFactory mrFactory = new MRFactory();
        // 得到分区以及分区中的测试用例的信息
        List<List<Integer>> partition = new Partition().getPartition();
        // 得到测试用例与蜕变关系的映射关系
        List<List<String>> mapping= new MRAndTestCaseMapping().getMapping();
        // 最终的分区失效率
        List<Double> partitionRates = new ArrayList<>();

        for (int i = 0; i < partition.size(); i++) {
            List<Integer> testCases = partition.get(i);
            //记录能杀死的变异体的测试用例的数目
            int numberOfKill = 0;
            //记录总的执行次数
            int totalNumber = 0;
            for (int j = 0; j < testCases.size(); j++) {
                int tempTestCaseIndex = testCases.get(j);
                for (int k = 0; k < Constant.mutantNames.size(); k++) {
                    String tempMutantName = Constant.mutantNames.get(k);
                    List<String> tempMRs = mapping.get(tempTestCaseIndex);
                    for (int l = 0; l < tempMRs.size(); l++) {
                        String tempMRName = tempMRs.get(l);
                        MR tempMR = mrFactory.productionMR(tempMRName);
                        if (tempMR.verifyResults(tempTestCaseIndex,
                                tempMutantName)){
                            numberOfKill++;
                        }
                        totalNumber++;
                    }
                }
            }
            double partitionRate = (double)numberOfKill / totalNumber;
            partitionRates.add(partitionRate);
        }
        Collections.sort(partitionRates);
        writePartitionRate(partitionRates);
        return partitionRates;
    }

    private void writePartitionRate(List<Double> partitionRates){
        File file = new File(Constant.PartitionRatesFilePath);
        try(PrintWriter printWriter = new PrintWriter(new FileWriter(file))) {
            for (int i = 0; i < partitionRates.size(); i++) {
                printWriter.write(String.valueOf(partitionRates.get(i)) + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        new PartitionRate().getPartitionRate();
    }

}
