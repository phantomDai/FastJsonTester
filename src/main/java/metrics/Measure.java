package metrics;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @author GN
 * @description Template method:calculate average and variance
 * @date 2019/11/13
 */
public abstract class Measure {

    public List<Integer> measureArray = new ArrayList<Integer>();


    public void addMeasure(int measure){
        measureArray.add(measure);
    }


    /**
     * get average
     * @return average
     */
    public double getAverageMeasure(){

        int sum = 0;

        DecimalFormat decimalFormat = new DecimalFormat("0.00");

        for (int i = 0; i < measureArray.size(); i++) {

            sum += measureArray.get(i);

        }

        return Double.parseDouble(decimalFormat.format(sum / measureArray.size()));
    }



    /**
     * get variance
     * @return variance
     */
    public double getVarianceMeasure(){

        double sum = 0;

        double average = getAverageMeasure();

        DecimalFormat decimalFormat = new DecimalFormat("0.00");

        for (int i = 0 ;i < measureArray.size(); i++){

            sum += Math.pow(measureArray.get(i) - average, 2);

        }

        return Double.parseDouble(decimalFormat.format(sum / (measureArray.size() - 1)));
    }

    public List<Integer> getMeasureArray() {
        return measureArray;
    }
}
