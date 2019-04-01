import java.util.*;


public class MarkovModel {
    private int modelOrder;
    private Map<String, List<String>> model;
    private Set<String> names;

    protected MarkovModel(int modelOrder){
        this.modelOrder = modelOrder;
        model = new HashMap<>();
        names = new HashSet<>();
    }

    
    // uses the given name to build the markov model 
    protected void buildModel(String name) {
        names.add(name);
        name = "_" + name + "_";
        for (int m = 1; m < modelOrder; m++) {
            for (int i = 0; i < (name.length() - m); i++) {
                String temp = name.substring(i, i + m);
                String temp1 = name.substring(i + m, i + m + 1);
                if (!model.containsKey(temp)) {
                    List<String> list = new ArrayList();
                    list.add(temp1);
                    model.put(temp, list);
                } else {
                    List<String> list = model.get(temp);
                    list.add(temp1);
                    model.put(temp, list);
                }
            }
        }
    }

    
    // returns the List in the model map associated with the given value.
    protected List getValues(String key) {
        if (!model.containsKey(key))
            return null;
        else
            return model.get(key);
    }

    
    // checks to see if a name already exists    
    protected boolean checkNameNovelty(String name) {
        return names.contains(name);
    }
}