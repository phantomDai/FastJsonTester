package mr.utl;

import java.io.*;

/**
 * @Description:
 * @auther phantom
 * @create 2019-12-02 下午7:43
 */
public class ReadSourceTestCase {



    public static String readSource(String sourcePath){
        // the file of source test case
        File sourceTestCase = new File(sourcePath);

        //get content
        BufferedReader bufferedReader = null;
        String content = "";
        try{
            bufferedReader = new BufferedReader(new FileReader(sourceTestCase));
            String temp = "";
            while((temp = bufferedReader.readLine()) != null) {
                content += temp + "\n";
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content;
    }
}
