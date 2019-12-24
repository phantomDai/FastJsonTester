package mutants;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * describe:
 *
 * @author phantom
 * @date 2019/12/18
 */
public class MutantSet {

    private List<String> mutants = new ArrayList<>(Arrays.asList("v31", "v36", "v40", "v48"));

    public List<String> getMutants() {
        return mutants;
    }
}
