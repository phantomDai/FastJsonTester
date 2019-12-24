package mrAndPartitionRelation;

import Constant.Constant;
import mutants.v31.com.alibaba.fastjson.parser.deserializer.FieldDeserializer;
import testsuite.AllFields;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * describe:
 *  获得每一个测试用例可以作用的蜕变关系,
 *  映射信息存在放在一个list中，index是测试用例的序号，对应的lsit是可以作用的蜕变关系
 *
 * @author phantom
 * @date 2019/12/20
 */
public class MRAndTestCaseMapping {

    private String[] allUsedMR = {"MR1", "MR2", "MR3"};


    public List<List<String>> getMapping(){
        List<List<String>> mappingRelation = new ArrayList<>();
        List<String> allTestFrames = AllFields.readTestFramesFile();
        for (int i = 0; i < Constant.numberOfTestFrames; i++) {
            List<String> tempList = new ArrayList<>(Arrays.asList("MR1", "MR2", "MR3"));
            String aTestFrame = allTestFrames.get(i);
            if (aTestFrame.contains("1-2")){
                tempList.add("MR4");
            }
            if (aTestFrame.contains("2-2")){
                tempList.add("MR5");
            }
            if (aTestFrame.contains("11-2")){
                tempList.add("MR6");
            }

            if (aTestFrame.contains("9-2")){
                tempList.add("MR7");
            }

            if (aTestFrame.contains("13-2")){
                tempList.add("MR8");
                tempList.add("MR9");
                tempList.add("MR10");
                tempList.add("MR11");
            }

            if (aTestFrame.contains("12-2")){
                tempList.add("MR12");
                tempList.add("MR13");
                tempList.add("MR14");
            }
            if (aTestFrame.contains("14-2")){
                tempList.add("MR15");
                tempList.add("MR16");
                tempList.add("MR17");
            }
            mappingRelation.add(tempList);
        }
        writeMappingRelation(mappingRelation);
        return mappingRelation;
    }

    private void writeMappingRelation(List<List<String>> mapping){
        File file = new File(Constant.mrAndPartitionRelationPath);
        try(PrintWriter printWriter = new PrintWriter(new FileWriter(file))){
            for (int i = 0; i < mapping.size(); i++) {
                printWriter.write(mapping.get(i).toString() + "\n");
            }
            printWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }



    public static void main(String[] args) {
        new MRAndTestCaseMapping().getMapping();
    }
}
