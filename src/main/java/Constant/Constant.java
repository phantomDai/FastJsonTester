package Constant;

import partition.Partition;
import testsuite.GenerateJsonString;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.io.File.separator;

public class Constant {

      /**record the info of testing process*/
      public static final String logPath = System.getProperty("user.dir") + separator + "log";

      /**record the testing results*/
      public static final String resultPath = System.getProperty("user.dir") + separator + "result";

      public static final String resultDir = System.getProperty("user.dir") + separator + "src" +
              separator + "main" + separator + "java" + separator + "results";

      /**the root direction of test suite*/
      public static final String TESTSUITEDIR = System.getProperty("user.dir") + separator + "src" +
              separator + "main" + separator + "java" + separator + "testsuite";

      public static final int numberOfTestFrames = 16383;

      public static final int numberOfPartition = 128;

      public static final String TESTPOOLDIR = TESTSUITEDIR + separator + "pool";

      public static final String TESTFRAMEFILE = TESTSUITEDIR + separator + "TestFrames.txt";


      public static final String COMMENTS = "/*comments*/";


      public static List<String> nullList = new ArrayList<>(Arrays.asList("1-1", "2-1", "3-1", "4-1", "5-1",
              "6-1", "7-1", "8-1", "9-1", "10-1", "11-1", "12-1", "13-1", "14-1"));


      public static List<String> sourceMethodNames = new ArrayList<>(Arrays.asList("getHeight", "getProperty",
              "getSalary", "getAge", "getMaxExpense", "getId", "getIsMarried", "getSex", "getBirthday",
              "getName", "getVehicle", "getFamily", "getFavoriteSports", "getFavoriteFoods"));

      public static List<String> mutantNames = new ArrayList<>(Arrays.asList("v31", "v36", "v40", "48"));

      public static GenerateJsonString getGeJsonObject(){
            return new GenerateJsonString();
      }

      public static String PartitionFilePath = System.getProperty("user.dir") + separator + "src" + separator +
              "main" + separator + "java" + separator + "partition" + separator + "partition.txt";

      public static String PartitionRatesFilePath = System.getProperty("user.dir") + separator + "src" + separator +
              "main" + separator + "java" + separator + "partition" + separator + "partitionRates.txt";

      public static String mrAndPartitionRelationPath = System.getProperty("user.dir") + separator + "src" + separator +
              "main" + separator + "java" + separator + "mrAndPartitionRelation" + separator + "mappingRelation.txt";

      public static String sourcePath = "testsuite.pool.";

      public static final int K = 10;


      public static int[] getRAPTBoundary(){
            List<List<Integer>> partition = new Partition().getPartition();
            int[] boundary = new int[partition.size()];
            for (int i = 0; i < partition.size(); i++) {
                  boundary[i] = (int) (partition.get(i).size() * 0.7);
            }
            return boundary;
      }


}
