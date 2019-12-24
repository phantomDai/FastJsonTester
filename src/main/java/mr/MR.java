package mr;

import java.util.ArrayList;
import java.util.List;

/**
 * describe:
 *
 * @author phantom
 * @date 2019/12/18
 */
public interface MR<T> {


    public T obtainFollowJsonString(int testCaseIndex);

    public boolean obtainFollowJBFile(int testCaseIndex);

    public boolean verifyResults(int testCaseIndex, String mutantName);


}
