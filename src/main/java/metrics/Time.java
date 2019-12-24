package metrics;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @author GN
 * @description Template method:calculate average and variance
 * @date 2019/11/13
 */
public abstract class Time {

    public List<Long> selectTestCaseArray = new ArrayList<Long>();

    public List<Long> generateTestcaseArray = new ArrayList<Long>();

    public List<Long> executeTestcaseArray = new ArrayList<Long>();



    public void addSelectTime(long selectTime){
        selectTestCaseArray.add(selectTime);
    }

    public void addGenerateTime(long generateTime){
        generateTestcaseArray.add(generateTime);
    }

    public void addExecuteTime(long executeTime){
        executeTestcaseArray.add(executeTime);
    }

    public double getAverageSelectTime(){
        return calculateAverage(selectTestCaseArray);
    }

    public double getAverageGenerateTime(){
        return calculateAverage(generateTestcaseArray);
    }

    public double getAverageExecuteTime(){
        return calculateAverage(executeTestcaseArray);
    }

    public double getVarianceSelectTime(){
        return calculateVariance(selectTestCaseArray);
    }

    public double getVarianceGenerateTime(){
        return calculateVariance(generateTestcaseArray);
    }

    public double getVarianceExecuteTime(){
        return calculateVariance(executeTestcaseArray);
    }

    /**
     * get average
     * @param list
     * @return average
     */
    public double calculateAverage(List<Long> list){

        int sum = 0;

        DecimalFormat decimalFormat = new DecimalFormat("0.00");

        for (int i = 0; i < list.size(); i++) {

            sum += list.get(i);

        }

        return Double.parseDouble(decimalFormat.format(sum / list.size()));
    }



    /**
     * get variance
     * @param list
     * @return variance
     */
    public double calculateVariance(List<Long> list){

        double average = calculateAverage(list);

        double sum = 0;

        DecimalFormat decimalFormat = new DecimalFormat("0.00");

        for (int i = 0 ;i < list.size(); i++){

            sum += Math.pow(list.get(i) - average, 2);

        }

        return Double.parseDouble(decimalFormat.format(sum / (list.size() - 1)));
    }


    public List<Long> getSelectTestCaseArray() {
        return selectTestCaseArray;
    }

    public List<Long> getGenerateTestcaseArray() {
        return generateTestcaseArray;
    }

    public List<Long> getExecuteTestcaseArray() {
        return executeTestcaseArray;
    }
}
