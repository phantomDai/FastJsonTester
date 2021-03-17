package testsuite;

import Constant.Constant;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * AllTestFrame：
 * (1) 返回所有的测试帧
 * (2) 根据编号返回一个测试帧
 * (3) 返回候选测试帧
 * (4) 根据测试帧返回改测试帧的编号
 * @description:
 * @author: phantom
 * @date: 2021/3/17 上午8:49
 */
public class AllTestFrame {

    /**
     * 返回所有的测试帧
     * @return 所有的测试帧列表
     */
    public List<String> getAllTestFrame(){
        List<String> allTestFrames = new ArrayList<>();
        File file = new File(Constant.TESTFRAMEFILE);
        try(BufferedReader br = new BufferedReader(new FileReader(file))){
            String tempStr = null;
            while((tempStr = br.readLine()) != null){
                allTestFrames.add(tempStr);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return allTestFrames;
    }

    /**
     * 根据测试用例的编号返回测试镇
     * @param index 测试用例的饿编号
     * @return 返回的测试帧
     */
    public String getOneTestFrame(int index){
        List<String> allTestFrames = getAllTestFrame();
        return allTestFrames.get(index);
    }

    /**
     * 随机获取10个候选的测试用例
     * @return
     */
    public List<String> getTenTestFrames(){
        List<String> allTestFrames = getAllTestFrame();
        List<String> candidatesTestCases = new ArrayList<>();
        for (int i = 0; i < Constant.K; i++) {
            candidatesTestCases.add(allTestFrames.get(new Random().nextInt(Constant.numberOfTestFrames)));
        }
        return candidatesTestCases;
    }

    /**
     * 根据具体的测试帧，返回改测试帧的编号
     * @param testFrame 具体的测试帧
     * @return 编号
     */
    public int getIndex(String testFrame){
        return getAllTestFrame().indexOf(testFrame);
    }

}
