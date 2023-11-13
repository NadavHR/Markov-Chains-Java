import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;


public class MarkovChain<T> {
    private Dictionary<T, Dictionary<T, Integer>> _data; // contains the processed training data
    // each value in _data is a node and for each node we save a dict with every possibility it preceded and a counter for the ammount of times it preceded it

    public MarkovChain(){
        _data = new Hashtable<>();
    }

    public void fit(T[] arr){
        for (int i = 0; i < arr.length - 1; i++) { // goes through every pair
            if (_data.get(arr[i]) == null) { // checks if a new node needs to be created
                Dictionary<T, Integer> probabilities = new Hashtable<>(); // creates a new node
                probabilities.put(arr[i+1], 1); // its only probability rn is preceding arr[i+1] 100% of the time
                _data.put(arr[i], probabilities); 
            }
            else{
                // if the node already exists
                Dictionary<T, Integer> probabilities = _data.get(arr[i]);
                if(probabilities.get(arr[i+1]) == null){ // checks if it ever preceded arr[i+1]
                    probabilities.put(arr[i+1], 1); // if it never preceded arr[i+1] start the counter with 1
                }
                else {
                    probabilities.put(arr[i+1], probabilities.get(arr[i+1]) + 1);  // if it has preceded add 1 to existing counter
                }
            }
            
        }
    }

    public List<T> forward(T[] kernel, int length){
        List<T> generated = new ArrayList<>(Arrays.asList(kernel)); // this is the generated list, starts with the kernel
        for (int i = 0; i < length; i++) { // each iteration generates the next value
            Dictionary<T, Integer> probabilities = _data.get(generated.get(generated.size()-1)); // this dict rperesents the probability of having each key in it as the next value
            if (probabilities != null) // make sure we actually have training data on it
            {
                
                Enumeration<T> keys = probabilities.keys();
                List<Pair<T, Integer>> dictAsList = new ArrayList<>(); // list of all keys and their appearences
                int countPossibilities = 0; // sum of all possibilities so we can calculate probability of each value
                while (keys.hasMoreElements()) { // check every key
                    T key = keys.nextElement();
                    dictAsList.add(new Pair<T,Integer>(key, probabilities.get(key))); // go from dict to a list of keys and appearences
                    countPossibilities += probabilities.get(key); 
                }

                int randomChoice = (int)Math.floor(Math.random() * (countPossibilities  + 1)); // chooses random integer from 0 to countPossibilities including
                int indexChosen = 0; // the index of the chosen value
                int sum = dictAsList.get(0).getValue(); // sum of all probabilities, initiates with the first value so the loop wont start with j>1 as it skips the 0th index
                // makes random choice be the index of the key in dictAsList
                for (int j = 0; j <= randomChoice; j++) { 
                    if (j > sum){ // to make sure we dont get below 0 too often
                        indexChosen++; 
                        sum += dictAsList.get(indexChosen).getValue();
                        
                    }
                }

                generated.add(dictAsList.get(indexChosen).getKey()); // adds the generated value to the generated list
            }
           else { // if we dont have any training data on the current key, choose the first value arbitrarily as we dont know
                generated.add(_data.keys().nextElement());
            }
            
        }
        return generated;
    }
}
