package mr.utl;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @Description:
 * @auther phantom
 * @create 2019-12-02 下午8:02
 */
public class WriteFollowJsonFile {
    public static void writeJsonFile(String content, String path){
        File file = new File(path);
        PrintWriter printWriter = null;
        try{
            printWriter = new PrintWriter(new FileWriter(file));
            printWriter.write(content);
            printWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
