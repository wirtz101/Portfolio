import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Set;


public class NameGenerator {
    private int order;
    private MarkovModel model;

    protected NameGenerator(MarkovModel model, int order) {
        this.order = order;
        this.model = model;
    }

    
    // returns a name generated by the given MarkovModel 
     
    protected String getName() {
        String name = "_";
        List values;
        String key = " ";
        String nextChar = " ";
        try{
            while(!nextChar.equals("_")) {

                if (name.length() < order)
                    key = name;
                else
                    key = key.substring(1);
                values = model.getValues(key);
                Collections.shuffle(values);
                if (values.size() > 0) {
                    nextChar = (String) values.remove(0);
                    name += nextChar;
                    key += nextChar;
                }
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        if (!name.equals("_"))
            return name.substring(1, name.length()-1);
        return null;
    }

}