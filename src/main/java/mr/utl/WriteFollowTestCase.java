package mr.utl;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @Description: generate the follow-up test case according to source test case and mr
 * @auther phantom
 * @create 2019-12-02 下午7:32
 */
public class WriteFollowTestCase {

    /**
     * write string into file
     * @param content
     * @param path
     */
    public static void writeFollow(String content, String path){
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
