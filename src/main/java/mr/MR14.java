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
public class MR14 implements MR {
    @Override
    public String obtainFollowJsonString(int testCaseIndex) {
        return Constant.getGeJsonObject().generateFollowJsonString4MR14(testCaseIndex);
    }

    @Override
    public boolean obtainFollowJBFile(int testCaseIndex) {
        return true;
    }

    @Override
    public boolean verifyResults(int testCaseIndex, String mutantName) {
        if (mutantName.equals("v31")){
            return true;
        }else {
            return false;
        }
    }
}
