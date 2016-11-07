package machinelearning;
import java.util.*;
/**
 *  A class which returns random numbers based on a given probability distribution
 *
 */
public class DistributedRandomNumberGenerator {

    private HashMap<Integer, Double> distribution;
    private double distSum;

    /**
     * Initializes the class with a private variable distribution
     */
    public DistributedRandomNumberGenerator() {
        distribution = new HashMap<>();
    }

    /**
     * @param value : The number to be inserted
     * @param distribution : The probabilty of that number being selected
     */
    public void addNumber(int value, double distribution) {
        if (this.distribution.get(value) != null) {
            distSum -= this.distribution.get(value);
        }
        this.distribution.put(value, distribution);
        distSum += distribution;
    }

    /**
     * @return : A random number based on the probability distribution
     */
    public int getDistributedRandomNumber() {
        double rand = Math.random();
        /*double ratio = 1.0f / distSum;
        double tempDist = 0;
        for (Integer i : distribution.keySet()) {
            tempDist += distribution.get(i);
            if (rand / ratio <= tempDist) {
                return i;
            }
        }*/
        double[] cumul = new double[distribution.size()];
        cumul[0] = distribution.get(0);
        for(int i=1;i<cumul.length;i++){
        	cumul[i] = cumul[i-1] + distribution.get(i);
        }
        for(int j=0;j<cumul.length;j++)
        {
            if(rand<=cumul[j])
            {
                return j;
            }
        }
        return 0;
    }

}
