package Log.utl;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @author JHH
 */

public class TimeRecorder {

    /**the time recorders*/
    private List<Long> firstSelectTestCaseArray;
    private List<Long> secondSelectTestCaseArray;

    private List<Long> firstGenerateTestCaseArray;
    private List<Long> secondGenerateTestCaseArray;

    private List<Long> firstExecuteTestCaseArray;
    private List<Long> secondExecuteTestCaseArray;

    private DecimalFormat decimalFormat;

    public TimeRecorder() {
        initial();
    }

    private void initial(){
        firstSelectTestCaseArray = new ArrayList<Long>();
        firstGenerateTestCaseArray = new ArrayList<Long>();
        firstExecuteTestCaseArray = new ArrayList<Long>();

        secondSelectTestCaseArray = new ArrayList<Long>();
        secondGenerateTestCaseArray = new ArrayList<Long>();
        secondExecuteTestCaseArray = new ArrayList<Long>();

        decimalFormat = new DecimalFormat("0.00");
    }

    public void addFirstSelectTestCase(Long time){
        firstSelectTestCaseArray.add(time);
    }

    public void addSecondSelectTestCase(Long time){
        secondSelectTestCaseArray.add(time);
    }


    public void addFirstGenerateTestCase(Long time){
        firstGenerateTestCaseArray.add(time);
    }

    public void addSecondGenerateTestCase(Long time){
        secondGenerateTestCaseArray.add(time);
    }


    public void addFirstExecuteTestCase(Long time){
        firstExecuteTestCaseArray.add(time);
    }

    public void addSecondExecuteTestCase(Long time){
        secondExecuteTestCaseArray.add(time);
    }


    /**get average value**/

    public double getAverageSelectFirstTestCaseTime(){
        return getAverageValue(firstSelectTestCaseArray);
    }

    public double getAverageSelectSecondTestCaseTime(){
        return getAverageValue(secondSelectTestCaseArray);
    }


    public double getAverageGenerateFirstTestCaseTime(){
        return getAverageValue(firstGenerateTestCaseArray);
    }

    public double getAverageGenerateSecondTestCaseTime(){
        return getAverageValue(secondGenerateTestCaseArray);
    }


    public double getAverageExecuteFirstTestCaseTime(){
        return getAverageValue(firstExecuteTestCaseArray);
    }

    public double getAverageExecuteSecondTestCaseTime(){
        return getAverageValue(secondExecuteTestCaseArray);
    }


    /**get variance value**/

    public double getVarianceSelectFirstTestCaseTime(){
        return getVarianceValue(firstSelectTestCaseArray);
    }

    public double getVarianceSelectSecondTestCaseTime(){
        return getVarianceValue(secondSelectTestCaseArray);
    }


    public double getVarianceGenerateFirstTestCaseTime(){
        return getVarianceValue(firstGenerateTestCaseArray);
    }

    public double getVarianceGenerateSecondTestCaseTime(){
        return getVarianceValue(secondGenerateTestCaseArray);
    }


    public double getVarianceExecuteFirstTestCaseTime(){
        return getVarianceValue(firstExecuteTestCaseArray);
    }

    public double getVarianceExecuteSecondTestCaseTime(){
        return getVarianceValue(secondExecuteTestCaseArray);
    }




    private double getAverageValue(List<Long> timeArray){
        double sum = 0;
        for (int i = 0; i < timeArray.size(); i++) {
            sum += timeArray.get(i);
        }
        return Double.parseDouble(decimalFormat.format(sum / timeArray.size()));
    }


    private double getVarianceValue(List<Long> list){
        double average = getAverageValue(list);

        double sum = 0;

        for (int i = 0; i < list.size(); i++) {
            sum += Math.pow(list.get(i) - average, 2);
        }
        return Double.parseDouble(decimalFormat.format(sum / (list.size() - 1)));
    }





    /***get lists**/

    public List<Long> getFirstSelectTestCaseArray() {
        return firstSelectTestCaseArray;
    }

    public List<Long> getSecondSelectTestCaseArray() {
        return secondSelectTestCaseArray;
    }


    public List<Long> getFirstGenerateTestCaseArray() {
        return firstGenerateTestCaseArray;
    }

    public List<Long> getSecondGenerateTestCaseArray() {
        return secondGenerateTestCaseArray;
    }


    public List<Long> getFirstExecuteTestCaseArray() {
        return firstExecuteTestCaseArray;
    }

    public List<Long> getSecondExecuteTestCaseArray() {
        return secondExecuteTestCaseArray;
    }

}
