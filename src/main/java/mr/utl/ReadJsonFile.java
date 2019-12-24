package mr.utl;

import java.io.*;

/**
 * @Description:
 * @auther phantom
 * @create 2019-12-02 下午7:55
 */
public class ReadJsonFile {

    public static String readFile(String path){
        File file = new File(path);
        BufferedReader bufferedReader = null;
        String content = "";
        try{
           bufferedReader = new BufferedReader(new FileReader(file));
           String temp = "";
           while((temp = bufferedReader.readLine()) != null){
               content += temp + "/*this is conmments*/\n";
           }
           bufferedReader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content;
    }
}
