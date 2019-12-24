package testsuite.generateString4testcase;

/**
 * @Description:
 * @auther phantom
 * @create 2019-12-09 下午3:08
 */
public class ProductionFactory {

    public String getString(String index){
        switch (index){
            case "1-2":
                return new GenerateFloat().produce();
            case "2-2":
                return new GenerateDouble().produce();
            case "3-2":
                return new GenerateShort().produce();
            case "4-2":
                return new GenerateByte().produce();
            case "5-2":
                return new GenerateInt().produce();
            case "6-2":
                return new GenerateLong().produce();
            case "7-2":
                return new GenerateBoolean().produce();
            case "8-2":
                return new GenerateChar().produce();
            case "9-2":
                return new GenerateDate().produce();
            case "10-2":
                return new GenerateString().produce();
            case "11-2":
                return new GenerateVehicle().produce();
            case "12-2":
                return new GenerateMap().produce();
            case "13-2":
                return new GenerateList().produce();
            case "14-2":
                return new GenerateSet().produce();
            default:
                System.out.println("error");
                return "null";
        }
    }


    public String getFollowString(String index){
        switch (index){
            case "1-2":
                return new GenerateFloat().produce();
            case "2-2":
                return new GenerateDouble().produce();
            case "3-2":
                return new GenerateShort().produce();
            case "4-2":
                return new GenerateByte().produce();
            case "5-2":
                return new GenerateInt().produce();
            case "6-2":
                return new GenerateLong().produce();
            case "7-2":
                return new GenerateBoolean().produce();
            case "8-2":
                return new GenerateChar().produce();
            case "9-2":
                return new GenerateDate().produce();
            case "10-2":
                return new GenerateString().produce();
            case "11-2":
                return new GenerateVehicle().produce();
            case "12-2":
                return new GenerateMap().produceFollow();
            case "13-2":
                return new GenerateList().produceFollow();
            case "14-2":
                return new GenerateSet().produceFollow();
            default:
                System.out.println("error");
                return "null";
        }
    }
}
