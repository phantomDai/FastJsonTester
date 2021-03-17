package newStrategy;

import testsuite.AllTestFrame;

import java.util.ArrayList;
import java.util.List;

/**
 * ARTSum：
 * 实现了ARTsum算法的核心逻辑
 * @description:
 * @author: phantom
 * @date: 2021/3/16 下午9:46
 */
public class ARTSum {

    /**include the candidate test cases*/
    private List<String> candidatesTestCases = new ArrayList<>();

    /**include the test cases that has been executed*/
    private List<String> executeTestCases = new ArrayList<>();

    /**执行的过程中记录S值*/
    private int[] S;

    /**
     * 每次重复执行测试需要调用该函数，来初始化S
     * @param tc 第一次选择的原始测试帧
     */
    public void initializeS(String tc){
        executeTestCases.add(tc);
        S = new int[42];
        if (tc.contains("1-1")){
            S[1] = 1;
        }else if (tc.contains("1-2")){
            S[2] = 1;
        }if (tc.contains("2-1")){
            S[4] = 1;
        }else if (tc.contains("2-2")){
            S[5] = 1;
        }if (tc.contains("3-1")){
            S[7] = 1;
        }else if (tc.contains("3-2")){
            S[8] = 1;
        }if (tc.contains("4-1")){
            S[10] = 1;
        }else if (tc.contains("4-2")){
            S[11] = 1;
        }if (tc.contains("5-1")){
            S[13] = 1;
        }else if (tc.contains("5-2")){
            S[14] = 1;
        }if (tc.contains("6-1")){
            S[16] = 1;
        }else if (tc.contains("6-2")){
            S[17] = 1;
        }if (tc.contains("7-1")){
            S[19] = 1;
        }else if (tc.contains("7-2")){
            S[20] = 1;
        }if (tc.contains("8-1")){
            S[22] = 1;
        }else if (tc.contains("8-2")){
            S[23] = 1;
        }if (tc.contains("9-1")){
            S[25] = 1;
        }else if (tc.contains("9-2")){
            S[26] = 1;
        }if (tc.contains("10-1")){
            S[28] = 1;
        }else if (tc.contains("10-2")){
            S[29] = 1;
        }if (tc.contains("11-1")){
            S[31] = 1;
        }else if (tc.contains("11-2")){
            S[32] = 1;
        }if (tc.contains("12-1")){
            S[34] = 1;
        }else if (tc.contains("12-2")){
            S[35] = 1;
        }if (tc.contains("13-1")){
            S[37] = 1;
        }else if (tc.contains("13-2")){
            S[38] = 1;
        }if (tc.contains("14-1")){
            S[40] = 1;
        }else if (tc.contains("14-2")){
            S[41] = 1;
        }
    }


    /**
     * 每次执行ART算法更新一次候选测试用例序列
     * @param candidatesTestCases
     */
    public void updateCandidatesTestCases(List<String> candidatesTestCases){
        this.candidatesTestCases = candidatesTestCases;
    }

    /**
     * 获取原始测试用例
     * @return 原始测试用例
     */
    public int getSourceTestCase(){
        String sourceTestCase = null;
        int maxValue = 0;
        int[] sourceChoiceArray;
        int[] maxSourceChoiceArray = new int[42];
        for(String tc : candidatesTestCases){
            sourceChoiceArray = new int[42];
            int tempValue = 0;
            if (tc.contains("1-1")){
                sourceChoiceArray[1] = 1;
                tempValue += (executeTestCases.size() - S[1]);
            }else if (tc.contains("1-2")){
                tempValue += (executeTestCases.size() - S[2]);
                sourceChoiceArray[2] = 1;
            }if (tc.contains("2-1")){
                tempValue += (executeTestCases.size() - S[4]);
                sourceChoiceArray[4] = 1;
            }else if (tc.contains("2-2")){
                tempValue += (executeTestCases.size() - S[5]);
                sourceChoiceArray[5] = 1;
            }if (tc.contains("3-1")){
                tempValue += (executeTestCases.size() - S[7]);
                sourceChoiceArray[7] = 1;
            }else if (tc.contains("3-2")){
                tempValue += (executeTestCases.size() - S[8]);
                sourceChoiceArray[8] = 1;
            }if (tc.contains("4-1")){
                tempValue += (executeTestCases.size() - S[10]);
                sourceChoiceArray[10] = 1;
            }else if (tc.contains("4-2")){
                tempValue += (executeTestCases.size() - S[11]);
                sourceChoiceArray[11] = 1;
            }if (tc.contains("5-1")){
                tempValue += (executeTestCases.size() - S[13]);
                sourceChoiceArray[13] = 1;
            }else if (tc.contains("5-2")){
                tempValue += (executeTestCases.size() - S[14]);
                sourceChoiceArray[14] = 1;
            }if (tc.contains("6-1")){
                tempValue += (executeTestCases.size() - S[16]);
                sourceChoiceArray[16] = 1;
            }else if (tc.contains("6-2")){
                tempValue += (executeTestCases.size() - S[17]);
                sourceChoiceArray[17] = 1;
            }if (tc.contains("7-1")){
                tempValue += (executeTestCases.size() - S[19]);
                sourceChoiceArray[19] = 1;
            }else if (tc.contains("7-2")){
                tempValue += (executeTestCases.size() - S[20]);
                sourceChoiceArray[20] = 1;
            }if (tc.contains("8-1")){
                tempValue += (executeTestCases.size() - S[22]);
                sourceChoiceArray[22] = 1;
            }else if (tc.contains("8-2")){
                tempValue += (executeTestCases.size() - S[23]);
                sourceChoiceArray[23] = 1;
            }if (tc.contains("9-1")){
                tempValue += (executeTestCases.size() - S[25]);
                sourceChoiceArray[25] = 1;
            }else if (tc.contains("9-2")){
                tempValue += (executeTestCases.size() - S[26]);
                sourceChoiceArray[26] = 1;
            }if (tc.contains("10-1")){
                tempValue += (executeTestCases.size() - S[28]);
                sourceChoiceArray[28] = 1;
            }else if (tc.contains("10-2")){
                tempValue += (executeTestCases.size() - S[29]);
                sourceChoiceArray[29] = 1;
            }if (tc.contains("11-1")){
                tempValue += (executeTestCases.size() - S[31]);
                sourceChoiceArray[31] = 1;
            }else if (tc.contains("11-2")){
                tempValue += (executeTestCases.size() - S[32]);
                sourceChoiceArray[32] = 1;
            }if (tc.contains("12-1")){
                tempValue += (executeTestCases.size() - S[34]);
                sourceChoiceArray[34] = 1;
            }else if (tc.contains("12-2")){
                tempValue += (executeTestCases.size() - S[35]);
                sourceChoiceArray[35] = 1;
            }if (tc.contains("13-1")){
                tempValue += (executeTestCases.size() - S[37]);
                sourceChoiceArray[37] = 1;
            }else if (tc.contains("13-2")){
                tempValue += (executeTestCases.size() - S[38]);
                sourceChoiceArray[38] = 1;
            }if (tc.contains("14-1")){
                tempValue += (executeTestCases.size() - S[40]);
                sourceChoiceArray[40] = 1;
            }else if (tc.contains("14-2")){
                tempValue += (executeTestCases.size() - S[41]);
                sourceChoiceArray[41] = 1;
            }
            if (tempValue > maxValue){
                maxValue = tempValue;
                sourceTestCase = tc;
                maxSourceChoiceArray = sourceChoiceArray;
            }
        }
        for (int i = 0; i < S.length; i++) {
            S[i] = S[i] + maxSourceChoiceArray[i];
        }
        executeTestCases.add(sourceTestCase);
        return new AllTestFrame().getIndex(sourceTestCase);
    }



}
