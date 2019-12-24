package Log;

import Constant.Constant;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import static java.io.File.separator;

/**
 * @author JHH
 */

public class LogRecorder {
    /**
     *
     * @param objectName object
     * @param seed seed
     * @param index  the index of executing test cases
     * @param sourceResult the result of source test cases
     * @param followResult ;the results of follow-up test cases
     * @param MR the name of selected mr
     * @param sourceTestCase source test case
     * @param followUpTestCase follow-up test case
     * @param mutantName the name of selected mutant
     */
    public static void recordLog(String objectName, int seed, int index, Object sourceResult, Object followResult,
                                 String MR, String sourceTestCase, String followUpTestCase, List mutantName){
        String content = "第" + String.valueOf(seed) + "次实验；的第" +String.valueOf(index) + "个测试用例；"
                + mutantName.toString() + "被杀死；" + "原始测试用例为：" + sourceTestCase + "；衍生测试用例为："
                + followUpTestCase + "；它们的结果分别为：" + String.valueOf(sourceResult) + "和"
                + String.valueOf(followResult) + "；违反了MR:" + MR;

        String path = Constant.logPath  + separator + "logRecord";
        File file = new File(path);
        PrintWriter printWriter = null;
        try {
            printWriter = new PrintWriter(new FileWriter(file,true));
            printWriter.write(content + "\n");
            printWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
