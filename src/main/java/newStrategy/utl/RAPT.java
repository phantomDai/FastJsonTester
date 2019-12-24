package newStrategy.utl;

import Constant.Constant;

import java.util.Random;

/**
 * @Description:
 * @auther phantom
 * @create 2019-09-23 下午5:19
 */
public class RAPT {


    /**the test profile of RAPT*/
    private double[] RAPT;

    private double RAPT_epsilon = 0.05;

    private double RAPT_delta = 0.002770083102493075;

    /**the factor of reward*/
    private int[] rew;


    private int[] bou;

    /**the factor of punishment*/
    private int[] pun;


    public void adjustRewParameter(int sourcePartition, int followPartition){
        if (sourcePartition == followPartition){
            rew[sourcePartition] += 1;
            pun[sourcePartition] =0 ;
        }else {
            rew[sourcePartition] += 1;
            rew[followPartition] += 1;
            pun[sourcePartition] =0 ;
            pun[followPartition] =0 ;
        }
    }

    public void adjustPunParameter(int sourcePartition, int followPartition){
        if (sourcePartition == followPartition){
            pun[sourcePartition] += 1;
        }else {
            pun[sourcePartition] += 1;
            pun[followPartition] += 1;
        }

    }


    /**
     * initialize the test profile of RAPT
     */
    public void initializeRAPT(){
        bou = Constant.getRAPTBoundary();
        RAPT = new double[Constant.numberOfPartition];
        pun = new int[Constant.numberOfPartition];
        rew = new int[Constant.numberOfPartition];
        for (int i = 0; i < Constant.numberOfPartition; i++) {
            RAPT[i] = 1.0 / Constant.numberOfPartition;
            pun[i] = 0;
        }
    }

    /**
     * get a index of partition
     * Note that the first number of partitions is 0
     * @return the index
     */
    public int nextPartition4RAPT(){
        boolean flag = false;
        int partitionindex = 0;
        for (int i = 0; i < rew.length; i++) {
            if (rew[i] > 0){
                flag = true;
                partitionindex = i;
                break;
            }
        }
        if (flag){
            return partitionindex;
        }else {
            int index = -1;
            double randomNumber = new Random().nextDouble();
            double sum = 0;
            do {
                index++;
                sum += RAPT[index];
            } while (randomNumber >= sum && index < RAPT.length - 1);
            return index;
        }
    }


    /**
     * adjust the test profile for RAPT testing
     * @param
     * @param isKilledMutant
     */
    public void adjustRAPT(int formersourcePartitionIndex,
                            int formerfollowUpPartitionIndex,
                            boolean isKilledMutant){
        double old_i = RAPT[formersourcePartitionIndex];
        double old_f = RAPT[formerfollowUpPartitionIndex];

        if (formersourcePartitionIndex == formerfollowUpPartitionIndex){
            if (isKilledMutant){
                double sum = 0;
                for (int i = 0; i < RAPT.length; i++) {
                    if (i != formersourcePartitionIndex){
                        RAPT[i] -= (1 + Math.log(rew[formersourcePartitionIndex]))
                                * RAPT_epsilon / (RAPT.length - 1);
                        if (RAPT[i] < 0){
                            RAPT[i] = 0;
                        }
                    }
                    sum += RAPT[i];
                }
                RAPT[formersourcePartitionIndex] = 1 - sum;
                rew[formersourcePartitionIndex] = 0;
            }else {
                for (int i = 0; i < RAPT.length; i++) {
                    if (i == formersourcePartitionIndex){
                        if (old_i >= RAPT_delta){
                            RAPT[i] -= RAPT_delta;
                        }
                        if (old_i < RAPT_delta || bou[i] == pun[i]){
                            RAPT[i] = 0;
                        }
                        if (bou[i] == pun[i]){
                            pun[i] = 0;
                        }
                    }else {
                        if (old_i >= RAPT_delta){
                            RAPT[i] += RAPT_delta / (RAPT.length - 1);
                        }
                        if (old_i < RAPT_delta || bou[i] == pun[i]){
                            RAPT[i] += old_i / (RAPT.length - 1);
                        }
                        if (bou[i] == pun[i]){
                            pun[i] = 0;
                        }
                    }
                }
            }
        }else { // not belong to the same partition
            if (isKilledMutant){
                double sum = 0;
                for (int i = 0; i < RAPT.length; i++) {
                    if (i != formersourcePartitionIndex &&i != formerfollowUpPartitionIndex){
                        if (RAPT[i] > (RAPT_epsilon *(1 + Math.log(rew[i])) / (RAPT.length - 2))){
                            RAPT[i] -= RAPT_epsilon *(1 + Math.log(rew[i])) / (RAPT.length - 2);
                        }else {
                            RAPT[i] = 0;
                        }
                    }
                    sum += RAPT[i];
                }
                RAPT[formersourcePartitionIndex] = old_i + ((1 - sum) - old_i - old_f) / 2;
                RAPT[formerfollowUpPartitionIndex] = old_f + ((1 - sum) - old_f - old_i) / 2;
                rew[formersourcePartitionIndex] = 0;
                rew[formerfollowUpPartitionIndex] =0;
            }else { // not reveal a mutant
                if (old_i > RAPT_delta){
                    RAPT[formersourcePartitionIndex] = old_i - RAPT_delta;
                }
                if (old_i <= RAPT_delta || pun[formersourcePartitionIndex] == bou[formersourcePartitionIndex]){
                    RAPT[formersourcePartitionIndex] = 0;
                    if(pun[formersourcePartitionIndex] == bou[formersourcePartitionIndex]){
                        pun[formersourcePartitionIndex] = 0;
                    }
                }
                if (old_f > RAPT_delta){
                    RAPT[formerfollowUpPartitionIndex] = old_f - RAPT_delta;
                }
                if (old_f <= RAPT_delta || pun[formerfollowUpPartitionIndex] == bou[formerfollowUpPartitionIndex]){
                    RAPT[formerfollowUpPartitionIndex] = 0;
                    if(pun[formersourcePartitionIndex] == bou[formersourcePartitionIndex]){
                        pun[formersourcePartitionIndex] = 0;
                    }
                }

                for (int i = 0; i < RAPT.length; i++) {
                    if (i != formersourcePartitionIndex && i != formerfollowUpPartitionIndex){
                        RAPT[i] += (( old_i - RAPT[formersourcePartitionIndex]) +
                                (old_f - RAPT[formerfollowUpPartitionIndex])) / (RAPT.length - 2);
                    }
                }
            }
        }

    }


}
