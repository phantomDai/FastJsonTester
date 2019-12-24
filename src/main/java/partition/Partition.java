package partition;

import Constant.Constant;
import testsuite.AllFields;

import java.io.*;
import java.util.*;

import static java.io.File.separator;

/**
 * describe:获得分区方式
 *  随机选取7个测试帧，并将它们的选项进行组合得到分区模式:
 *  1, 3, 4, 5, 7, 10, 12
 * @author phantom
 * @date 2019/12/18
 */
public class Partition {

    /**
     * 随机选择7个测试帧
     * @return 选中的测试帧的编号
     */
    public List<Integer> choosePartitionIndex(){
        List<Integer> partitionIndexs = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            partitionIndexs.add(new Random().nextInt(14));
        }
        return partitionIndexs;
    }

    /**
     * 获取分区模式存在map中：key：分区编号；value：是一个list，存放选项
     * @return 分区模式
     */
    private Map<Integer, List<String>> getPartitionScheme(){
        Map<Integer, List<String>> partitionScheme = new IdentityHashMap<>();
        int partitionIndex = -1;
        for (int a = 1; a < 3; a++) { // 1
            for (int b = 1; b < 3; b++) {// 3
                for (int c = 1; c < 3; c++) { //4
                    for (int d = 1; d < 3; d++) {//5
                        for (int e = 1; e < 3; e++) { //7
                            for (int f = 1; f < 3; f++) { //10
                                for (int g = 1; g < 3; g++) { //12
                                    partitionIndex++;
                                    List<String> tempList = new ArrayList<>();
                                    if (a == 1){
                                        tempList.add("1-1");
                                    }else {
                                        tempList.add("1-2");
                                    }
                                    if (b == 1){
                                        tempList.add("3-1");
                                    }else {
                                        tempList.add("3-2");
                                    }

                                    if (c == 1){
                                        tempList.add("4-1");
                                    }else {
                                        tempList.add("4-2");
                                    }

                                    if (d == 1){
                                        tempList.add("5-1");
                                    }else {
                                        tempList.add("5-2");
                                    }

                                    if (e == 1){
                                        tempList.add("7-1");
                                    }else {
                                        tempList.add("7-2");
                                    }

                                    if (f ==1 ){
                                        tempList.add("10-1");
                                    }else {
                                        tempList.add("10-2");
                                    }

                                    if (g == 1){
                                        tempList.add("12-1");
                                    }else {
                                        tempList.add("12-2");
                                    }
                                    partitionScheme.put(partitionIndex, tempList);
                                }
                            }
                        }
                    }
                }
            }
        }
        return partitionScheme;
    }

    /**
     * 获取分区，键是分区编号，值是该分区中的测试用例的编号
     * @return map
     */
    public Map<Integer, List<Integer>> generatePartition(){
        // 获得分区模式
        Map<Integer, List<String>> partitionScheme = getPartitionScheme();
        writePartitionScheme2Text(partitionScheme);

        // 获取所有的测试帧
        List<String> allTestFrames = AllFields.readTestFramesFile();
        Map<Integer, List<Integer>> partition = new IdentityHashMap<>();

        //遍历分区模式得到分区以及该分区下的测试用例
        for (int i = 0; i < partitionScheme.size(); i++) {
            List<Integer> testCaseIndexs = new ArrayList<>();
            List<String> aPartitionScheme = partitionScheme.get(i);
            for (int j = 0; j < allTestFrames.size(); j++) {
                boolean flag = true;
                String aTestFrame = allTestFrames.get(j);
                for (int k = 0; k < aPartitionScheme.size(); k++) {
                    String aChoice = aPartitionScheme.get(k);
                    if (!aTestFrame.contains(aChoice)){
                        flag = false;
                        break;
                    }
                }
                if (flag){
                    testCaseIndexs.add(j);
                }
            }
            partition.put(i, testCaseIndexs);
        }
        return partition;
    }

    private void writePartitionScheme2Text(Map<Integer, List<String>> partitionScheme){
        String path = System.getProperty("user.dir") + separator + "src" + separator +
                "main" + separator + "java" + separator + "partition" +
                separator + "partitionScheme.txt";

        File file = new File(path);

        if (file.exists()){
            return;
        }

        StringBuffer stringBuffer = new StringBuffer();

        for (int i = 0; i < partitionScheme.size(); i++) {
            stringBuffer.append(String.valueOf(i) + ":" +
                    stringArray2String(partitionScheme.get(i)) + "\n");
        }

        try(PrintWriter printWriter = new PrintWriter(new FileWriter(file))){
            printWriter.write(stringBuffer.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        stringBuffer.delete(0, stringBuffer.length());
    }

    private void writePartition2Text(Map<Integer, List<Integer>> partition){
        String path = System.getProperty("user.dir") + separator + "src" + separator +
                "main" + separator + "java" + separator + "partition" + separator + "partition.txt";

        File file = new File(path);

        if (file.exists()){
            return;
        }

        StringBuffer stringBuffer = new StringBuffer();

        for (int i = 0; i < partition.size(); i++) {
            stringBuffer.append(String.valueOf(i) + ":" +
                    integerArray2String(partition.get(i)) + "\n");
        }

        try(PrintWriter printWriter = new PrintWriter(new FileWriter(file))){
            printWriter.write(stringBuffer.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        stringBuffer.delete(0, stringBuffer.length());
    }

    private String integerArray2String(List<Integer> list){
        String s = "";
        for (int i = 0; i < list.size(); i++) {
            s += String.valueOf(i) + ";";
        }
        return s;
    }

    private String stringArray2String(List<String> list){
        String s = "";
        for (int i = 0; i < list.size(); i++) {
            s += list.get(i) + ";";
        }
        return s;
    }

    /**
     * read partition.txt文件获得分区
     * @return a list, the index of it is the partiion index,
     * and the corresponding list is the indexs of test case
     */
    public List<List<Integer>> getPartition(){
       List<List<Integer>> partition = new ArrayList<>();
        File file = new File(Constant.PartitionFilePath);
        // 分区编号
        int partitionIndex = -1;
       try(BufferedReader br = new BufferedReader(new FileReader(file))){
           String tempStr = "";
           while ((tempStr = br.readLine()) != null){
                 tempStr = tempStr.substring(1, tempStr.length() - 1);
                 String[] testCasesIndexs = tempStr.split(", ");
                 partition.add(trunStringTestCasesIndexsToString(testCasesIndexs));
           }
       } catch (FileNotFoundException e) {
           e.printStackTrace();
       } catch (IOException e) {
           e.printStackTrace();
       }
       return partition;
    }

    private List<Integer> trunStringTestCasesIndexsToString(String [] array){
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < array.length; i++) {
            list.add(Integer.parseInt(array[i]));
        }
        return list;
    }

    public static void main(String[] args) {
        System.out.println(new Partition().getPartition().get(0));
    }



}
