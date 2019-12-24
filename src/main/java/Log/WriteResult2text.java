package Log;

import Constant.Constant;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import static java.io.File.separator;

/**
 * describe:
 *
 * @author phantom
 * @date 2019/12/23
 */
public class WriteResult2text {
    public static void recordResult(String metric, int idVersion, double FAverage, double F2Average){
        String path = Constant.resultDir + separator + metric;
        File file = new File(path);
        if (!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            PrintWriter printWriter = new PrintWriter(new FileWriter(file,true));
            printWriter.write("id-version: " + String.valueOf(idVersion) + "\n");
            printWriter.write("Fmeasure: " + String.valueOf(FAverage) + "\n");
            printWriter.write("F2measure: " + String.valueOf(F2Average) + "\n");
            printWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
