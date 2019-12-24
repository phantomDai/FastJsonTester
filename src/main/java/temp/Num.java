package temp;

//import com.alibaba.fastjson.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * @Description:
 * @auther phantom
 * @create 2019-11-29 下午8:33
 */
public class Num {
    private Map<String, String> header;

    private Map<String,String> body;

    public Map<String, String> getHeader() {
        return header;
    }

    public void setHeader(Map<String, String> header) {
        this.header = header;
    }

    public Map<String, String> getBody() {
        return body;
    }

    public void setBody(Map<String, String> body) {
        this.body = body;
    }

    public static void main(String[] args) {
        SimpleDateFormat df =
                new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒");

        System.out.println(df.format(new Date()));
    }
}
