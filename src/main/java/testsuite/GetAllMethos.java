package testsuite;

import Constant.Constant;

import java.util.*;

/**
 * @Description: get all method names for all test cases
 * @auther phantom
 * @create 2019-12-10 下午9:29
 */
public class GetAllMethos {


    public List<List<String>> getAllSourceMethod(){
        List<String> allTestFrames = AllFields.readTestFramesFile();
        List<List<String>> allMethods = new ArrayList<>();

        for (int i = 0; i < allTestFrames.size(); i++) {
            List<String> tempmethods = new ArrayList<>();
            String[] choices = allTestFrames.get(i).split(";");
            for (int j = 0; j < choices.length; j++) {
                if (Constant.nullList.contains(choices[j])){
                    continue;
                }else {
                    tempmethods.add(Constant.sourceMethodNames.get(j));
                }
            }
            allMethods.add(tempmethods);
        }
        return allMethods;
    }

    public static void main(String[] args) {
        System.out.println(new GetAllMethos().getAllSourceMethod().get(16382).toString());
    }

}
