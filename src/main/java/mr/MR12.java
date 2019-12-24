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
public class MR12 implements MR {
    @Override
    public Object obtainFollowJsonString(int testCaseIndex) {
        return Constant.getGeJsonObject().generateFollowJsonString4MR12(testCaseIndex);
    }

    @Override
    public boolean obtainFollowJBFile(int testCaseIndex) {
        return false;
    }

    @Override
    public boolean verifyResults(int testCaseIndex, String mutantName) {
        return false;
    }
}
