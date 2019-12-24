package mr;

import Constant.Constant;
import mr.utl.GetMRReturnResult;

import java.util.List;

/**
 * describe:
 *
 * @author phantom
 * @date 2019/12/18
 */
public class MR4 implements MR {

    @Override
    public String obtainFollowJsonString(int testCaseIndex) {
        return Constant.getGeJsonObject().generateFollowJsonString4MR4(testCaseIndex);
    }

    @Override
    public boolean obtainFollowJBFile(int testCaseIndex) {
        return false;
    }

    @Override
    public boolean verifyResults(int testCaseIndex, String mutantName) {
        if (mutantName.equals("v40") || mutantName.equals("v48")){
            return false;
        }else {
            return false;
        }
    }
}
