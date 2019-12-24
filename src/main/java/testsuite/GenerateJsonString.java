package testsuite;

import Constant.Constant;



import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Description:
 * @auther phantom
 * @create 2019-12-09 下午4:28
 */
public class GenerateJsonString {

    private List<String> nullList = new ArrayList<>(Arrays.asList("1-1", "2-1", "3-1", "4-1", "5-1",
            "6-1", "7-1", "8-1", "9-1", "10-1", "11-1", "12-1", "13-1", "14-1"));

    /**
     * generate json string according to the index of specific test case
     * @param index index
     * @return json string
     */
    public String generateJsonString(int index){

        // all test frames
        List<String> testFrames = AllFields.readTestFramesFile();

        // specific test frame
        String atestFrame = testFrames.get(index);
        String[] choices = atestFrame.split(";");

        List<String> fieldNames = AllFields.getAllFieldName();
        List<String> fieldValues = AllFields.getAllFieldValue();
        String str = "{";

        for (int i = 0; i < choices.length; i++) {
            String tempIndex = choices[i];
            if (nullList.contains(tempIndex)){
                continue;
            }else {
                str += "\"" + fieldNames.get(i) + "\":" + fieldValues.get(i) + ",";
            }
        }

        str = str.substring(0, str.length() - 1);

        str += "}";

        return str;
    }

    /**
     * 为MR1生成衍生测试用例，为每一个属性后面添加注释
     * @param index
     * @return
     */
    public String generateFollowJsonString4MR1(int index){
        // all test frames
        List<String> testFrames = AllFields.readTestFramesFile();
        // specific test frame
        String atestFrame = testFrames.get(index);
        String[] choices = atestFrame.split(";");
        List<String> fieldNames = AllFields.getAllFieldName();
        List<String> fieldValues = AllFields.getAllFieldValue();
        String str = "{";
        for (int i = 0; i < choices.length; i++) {
            String tempIndex = choices[i];
            if (nullList.contains(tempIndex)){
                continue;
            }else {
                str += "\"" + fieldNames.get(i) + "\":" + fieldValues.get(i);
            }
            if (i != choices.length - 1){
                str += "," + Constant.COMMENTS;
            }
        }
        int comma_position = str.lastIndexOf(",");
        StringBuffer stringBuffer = new StringBuffer(str);
        stringBuffer.replace(comma_position, comma_position + 1, "");
        str = stringBuffer.toString();
        str += "}";
        return str;
    }

    /**
     * 为MR2生成衍生测试用例，将字符串转化为字节数组
     * @param index 测试用例的编号
     * @return 字节数组
     */
    public byte[] generateFollowJsonString4MR2(int index){
        // 原始测试用例的json字符串
        String sourceJosn = generateJsonString(index);

        //将json字符串转化为字符数组
        byte[] followData = sourceJosn.getBytes();

        return followData;
    }

    /**
     * 为MR3产生测试用例，将json字符串转化为普通字符串
     * @param index 测试用例的编号
     * @return 衍生测试用例的字符串
     */
    public String generateFollowJsonString4MR3(int index){
        // all test frames
        List<String> testFrames = AllFields.readTestFramesFile();
        // specific test frame
        String atestFrame = testFrames.get(index);
        String[] choices = atestFrame.split(";");
        List<String> fieldNames = AllFields.getAllFieldName();
        List<String> fieldValues = AllFields.getAllFollowFieldValue();
        String str = "{";
        for (int i = 0; i < choices.length; i++) {
            String tempIndex = choices[i];
            if (nullList.contains(tempIndex)){
                continue;
            }else {
                str += "\"" + fieldNames.get(i) + "\":" + fieldValues.get(i);
            }
            if (i != choices.length - 1){
                str += ",";
            }
        }
        str += "}";
        return str;
    }

    public String generateFollowJsonString4MR4(int index){
        // all test frames
        List<String> testFrames = AllFields.readTestFramesFile();
        // specific test frame
        String atestFrame = testFrames.get(index);
        String[] choices = atestFrame.split(";");
        List<String> fieldNames = AllFields.getAllFieldName();
        List<String> fieldValues = AllFields.getAllFieldValue();
        fieldValues.set(0, "1.781234567");
        String str = "{";
        for (int i = 0; i < choices.length; i++) {
            String tempIndex = choices[i];
            if (nullList.contains(tempIndex)){
                continue;
            }else {
                str += "\"" + fieldNames.get(i) + "\":" + fieldValues.get(i);
            }
            if (i != choices.length - 1){
                str += ",";
            }
        }
        str += "}";
        return str;
    }

    public String generateFollowJsonString4MR5(int index){
        // all test frames
        List<String> testFrames = AllFields.readTestFramesFile();
        // specific test frame
        String atestFrame = testFrames.get(index);
        String[] choices = atestFrame.split(";");
        List<String> fieldNames = AllFields.getAllFieldName();
        List<String> fieldValues = AllFields.getAllFieldValue();
        fieldValues.set(1, "800.001234567891234567");
        String str = "{";
        for (int i = 0; i < choices.length; i++) {
            String tempIndex = choices[i];
            if (nullList.contains(tempIndex)){
                continue;
            }else {
                str += "\"" + fieldNames.get(i) + "\":" + fieldValues.get(i);
            }
            if (i != choices.length - 1){
                str += ",";
            }
        }
        str += "}";
        return str;
    }

    public String generateFollowJsonString4MR6(int index){
        // all test frames
        List<String> testFrames = AllFields.readTestFramesFile();
        // specific test frame
        String atestFrame = testFrames.get(index);
        String[] choices = atestFrame.split(";");
        List<String> fieldNames = AllFields.getAllFieldName();
        List<String> fieldValues = AllFields.getAllFieldValue();
        fieldValues.set(10, "others");
        String str = "{";
        for (int i = 0; i < choices.length; i++) {
            String tempIndex = choices[i];
            if (nullList.contains(tempIndex)){
                continue;
            }else {
                str += "\"" + fieldNames.get(i) + "\":" + fieldValues.get(i);
            }
            if (i != choices.length - 1){
                str += ",";
            }
        }
        str += "}";
        return str;
    }

    public String generateFollowJsonString4MR7(int index){
        // all test frames
        List<String> testFrames = AllFields.readTestFramesFile();
        // specific test frame
        String atestFrame = testFrames.get(index);
        String[] choices = atestFrame.split(";");
        List<String> fieldNames = AllFields.getAllFieldName();
        List<String> fieldValues = AllFields.getAllFieldValue();
        fieldValues.set(8, "\"1993年03月01日 00时00分00秒\"");
        String str = "{";
        for (int i = 0; i < choices.length; i++) {
            String tempIndex = choices[i];
            if (nullList.contains(tempIndex)){
                continue;
            }else {
                str += "\"" + fieldNames.get(i) + "\":" + fieldValues.get(i);
            }
            if (i != choices.length - 1){
                str += ",";
            }
        }
        str += "}";
        return str;
    }

    public String generateFollowJsonString4MR8(int index){
        // all test frames
        List<String> testFrames = AllFields.readTestFramesFile();
        // specific test frame
        String atestFrame = testFrames.get(index);
        String[] choices = atestFrame.split(";");
        List<String> fieldNames = AllFields.getAllFieldName();
        List<String> fieldValues = AllFields.getAllFieldValue();
        fieldValues.set(12, "[\"badminton\", \"footbal\", \"tennis\"]");
        String str = "{";
        for (int i = 0; i < choices.length; i++) {
            String tempIndex = choices[i];
            if (nullList.contains(tempIndex)){
                continue;
            }else {
                str += "\"" + fieldNames.get(i) + "\":" + fieldValues.get(i);
            }
            if (i != choices.length - 1){
                str += ",";
            }
        }
        str += "}";
        return str;
    }

    public String generateFollowJsonString4MR9(int index){
        // all test frames
        List<String> testFrames = AllFields.readTestFramesFile();
        // specific test frame
        String atestFrame = testFrames.get(index);
        String[] choices = atestFrame.split(";");
        List<String> fieldNames = AllFields.getAllFieldName();
        List<String> fieldValues = AllFields.getAllFieldValue();
        fieldValues.set(12, "[\"footbal\", \"tennis\", \"badminton\", \"volleyball\"]");
        String str = "{";
        for (int i = 0; i < choices.length; i++) {
            String tempIndex = choices[i];
            if (nullList.contains(tempIndex)){
                continue;
            }else {
                str += "\"" + fieldNames.get(i) + "\":" + fieldValues.get(i);
            }
            if (i != choices.length - 1){
                str += ",";
            }
        }
        str += "}";
        return str;
    }

    public String generateFollowJsonString4MR10(int index){
        // all test frames
        List<String> testFrames = AllFields.readTestFramesFile();
        // specific test frame
        String atestFrame = testFrames.get(index);
        String[] choices = atestFrame.split(";");
        List<String> fieldNames = AllFields.getAllFieldName();
        List<String> fieldValues = AllFields.getAllFieldValue();
        fieldValues.set(12, "[\"footbal\", \"tennis\"]");
        String str = "{";
        for (int i = 0; i < choices.length; i++) {
            String tempIndex = choices[i];
            if (nullList.contains(tempIndex)){
                continue;
            }else {
                str += "\"" + fieldNames.get(i) + "\":" + fieldValues.get(i);
            }
            if (i != choices.length - 1){
                str += ",";
            }
        }
        str += "}";
        return str;
    }

    public String generateFollowJsonString4MR11(int index){
        // all test frames
        List<String> testFrames = AllFields.readTestFramesFile();
        // specific test frame
        String atestFrame = testFrames.get(index);
        String[] choices = atestFrame.split(";");
        List<String> fieldNames = AllFields.getAllFieldName4MR11();
        List<String> fieldValues = AllFields.getAllFieldValue4MR11();
        String str = "{";
        for (int i = 0; i < choices.length; i++) {
            String tempIndex = choices[i];
            if (nullList.contains(tempIndex)){
                continue;
            }else {
                str += "\"" + fieldNames.get(i) + "\":" + fieldValues.get(i);
            }
            if (i != choices.length - 1){
                str += ",";
            }
        }
        str += "\"favoriteSports2\":[\"badminton\"]";
        str += "}";
        return str;
    }

    public String generateFollowJsonString4MR12(int index){
        // all test frames
        List<String> testFrames = AllFields.readTestFramesFile();
        // specific test frame
        String atestFrame = testFrames.get(index);
        String[] choices = atestFrame.split(";");
        List<String> fieldNames = AllFields.getAllFieldName();
        List<String> fieldValues = AllFields.getAllFieldValue();
        fieldValues.set(11, "{\"father\":\"Xinping Dai\", \"mother\":\"Aixiang Jia\", \"sister\":\"Yujing Dai\"}");
        String str = "{";
        for (int i = 0; i < choices.length; i++) {
            String tempIndex = choices[i];
            if (nullList.contains(tempIndex)){
                continue;
            }else {
                str += "\"" + fieldNames.get(i) + "\":" + fieldValues.get(i);
            }
            if (i != choices.length - 1){
                str += ",";
            }
        }
        int comma_position = str.lastIndexOf(",");
        StringBuffer stringBuffer = new StringBuffer(str);
        stringBuffer.replace(comma_position, comma_position + 1, "");
        str = stringBuffer.toString();
        str += "}";
        return str;
    }

    public String generateFollowJsonString4MR13(int index){
        // all test frames
        List<String> testFrames = AllFields.readTestFramesFile();
        // specific test frame
        String atestFrame = testFrames.get(index);
        String[] choices = atestFrame.split(";");
        List<String> fieldNames = AllFields.getAllFieldName();
        List<String> fieldValues = AllFields.getAllFieldValue();
        fieldValues.set(11, "{\"father\":\"Xinping Dai\"}");
        String str = "{";
        for (int i = 0; i < choices.length; i++) {
            String tempIndex = choices[i];
            if (nullList.contains(tempIndex)){
                continue;
            }else {
                str += "\"" + fieldNames.get(i) + "\":" + fieldValues.get(i);
            }
            if (i != choices.length - 1){
                str += ",";
            }
        }

        str += "}";
        return str;
    }

    public String generateFollowJsonString4MR14(int index){
        // all test frames
        List<String> testFrames = AllFields.readTestFramesFile();
        // specific test frame
        String atestFrame = testFrames.get(index);
        String[] choices = atestFrame.split(";");
        List<String> fieldNames = AllFields.getAllFieldName4MR14();
        List<String> fieldValues = AllFields.getAllFieldValue4MR14();
        String str = "{";
        for (int i = 0; i < choices.length; i++) {
            String tempIndex = choices[i];
            if (nullList.contains(tempIndex)){
                continue;
            }else {
                str += "\"" + fieldNames.get(i) + "\":" + fieldValues.get(i);
            }
            if (i != choices.length - 1){
                str += ",";
            }
        }
        str += "\"family2\":{\"mother\":\"Aixiang Jia\"}";
        str += "}";
        return str;
    }

    public String generateFollowJsonString4MR15(int index){
        // all test frames
        List<String> testFrames = AllFields.readTestFramesFile();
        // specific test frame
        String atestFrame = testFrames.get(index);
        String[] choices = atestFrame.split(";");
        List<String> fieldNames = AllFields.getAllFieldName();
        List<String> fieldValues = AllFields.getAllFieldValue();
        fieldValues.set(13, "[\"caffe\", \"cake\", \"ice cream\", \"fruits\"]");
        String str = "{";
        for (int i = 0; i < choices.length; i++) {
            String tempIndex = choices[i];
            if (nullList.contains(tempIndex)){
                continue;
            }else {
                str += "\"" + fieldNames.get(i) + "\":" + fieldValues.get(i);
            }
            if (i != choices.length - 1){
                str += ",";
            }
        }
        str += "}";
        return str;
    }

    public String generateFollowJsonString4MR16(int index){
        // all test frames
        List<String> testFrames = AllFields.readTestFramesFile();
        // specific test frame
        String atestFrame = testFrames.get(index);
        String[] choices = atestFrame.split(";");
        List<String> fieldNames = AllFields.getAllFieldName();
        List<String> fieldValues = AllFields.getAllFieldValue();
        fieldValues.set(13, "[\"caffe\", \"cake\"]");
        String str = "{";
        for (int i = 0; i < choices.length; i++) {
            String tempIndex = choices[i];
            if (nullList.contains(tempIndex)){
                continue;
            }else {
                str += "\"" + fieldNames.get(i) + "\":" + fieldValues.get(i);
            }
            if (i != choices.length - 1){
                str += ",";
            }
        }
        str += "}";
        return str;
    }

    public String generateFollowJsonString4MR17(int index){
        // all test frames
        List<String> testFrames = AllFields.readTestFramesFile();
        // specific test frame
        String atestFrame = testFrames.get(index);
        String[] choices = atestFrame.split(";");
        List<String> fieldNames = AllFields.getAllFieldName4MR17();
        List<String> fieldValues = AllFields.getAllFieldValue4MR17();
        String str = "{";
        for (int i = 0; i < choices.length; i++) {
            String tempIndex = choices[i];
            if (nullList.contains(tempIndex)){
                continue;
            }else {
                str += "\"" + fieldNames.get(i) + "\":" + fieldValues.get(i);
            }
            if (i != choices.length - 1){
                str += ",";
            }
        }
        str += "\"favoriteFoods2\":[\"ice cream\"]";
        str += "}";
        return str;
    }

    public static void main(String[] args) {
//        System.out.println(new GenerateJsonString().ge);
    }

}
