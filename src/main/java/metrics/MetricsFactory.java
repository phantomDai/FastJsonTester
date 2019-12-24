package metrics;

/**
 * @author GN
 * @description The factory class of metric, return the corresponding object according to metric's name
 * @date 2019/11/13
 */
public class MetricsFactory {

    /**
     * calculate measure method
     * @param method
     * @return
     */
    public Measure chooseCalculateMeasureMethod(String method){

        if (method.equalsIgnoreCase("FMeasure")){

            return new FMeasure();

        }else if (method.equalsIgnoreCase("F2Measure")){

            return new F2Measure();

        }
        return null;
    }



    /**
     * calculate time method
     * @param method
     * @return
     */
    public Time chooseCalculateTimeMethod(String method){

        if (method.equalsIgnoreCase("FTime")){

            return new FTime();

        }else if (method.equalsIgnoreCase("F2Time")){

            return new F2Time();

        }
        return null;
    }
}
