package mr;

/**
 * @Description: the factory of MRs
 * @auther phantom
 * @create 2019-12-02 下午3:31
 */
public class MRFactory {


    public MR productionMR(String MRName){
        if (MRName.equals("MR1")){
            return new MR1();
        }else if (MRName.equals("MR2")){
            return new MR2();
        }else if (MRName.equals("MR3")){
            return new MR3();
        }else if (MRName.equals("MR4")){
            return new MR4();
        }else if (MRName.equals("MR5")){
            return new MR5();
        }else if (MRName.equals("MR6")){
            return new MR6();
        }else if (MRName.equals("MR7")){
            return new MR7();
        }else if (MRName.equals("MR8")){
            return new MR8();
        }else if (MRName.equals("MR9")){
            return new MR9();
        }else if (MRName.equals("MR10")){
            return new MR10();
        }else if (MRName.equals("MR11")){
            return new MR11();
        }else if (MRName.equals("MR12")){
            return new MR12();
        }else if (MRName.equals("MR13")){
            return new MR13();
        }else if (MRName.equals("MR14")){
            return new MR14();
        }else if (MRName.equals("MR15")){
            return new MR15();
        }else if (MRName.equals("MR16")){
            return new MR16();
        }else{
            return new MR17();
        }
    }






}
