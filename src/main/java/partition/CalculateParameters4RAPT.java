package partition;

/**
 * describe: 根据分区的失效率计算RAPT的参数值
 *
 * @author phantom
 * @date 2019/12/23
 */
public class CalculateParameters4RAPT {

    public void getParameterOfRAPT(){
        double max = 0.057971014492753624;
        double lessMax = 0.05333333333333334;
        double delta = 0.25 / (4 * (1 / lessMax) - 1 + (1 / max) - 1);
        System.out.println(delta);
    }

    public static void main(String[] args) {
        new CalculateParameters4RAPT().getParameterOfRAPT();
    }

}
