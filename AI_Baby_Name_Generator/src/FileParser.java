import java.io.BufferedReader;
import java.io.FileReader;


public class FileParser {
    
    // reads the names from the given file and runs them through the markov model.
     
    protected MarkovModel readFile(String filename, int modelOrder) {
        MarkovModel markovModel = new MarkovModel(modelOrder);
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filename));
            String line;
            int totalCount = 0;
            while ((line = reader.readLine()) != null) {
                String temp = line;
                temp = temp.toLowerCase();
                markovModel.buildModel(temp);
            }
            reader.close();

            return markovModel;
        } catch (Exception e) {
            System.err.format("Exception occurred trying to read '%s'.", filename);
            e.printStackTrace();
            return null;
        }
    }
}