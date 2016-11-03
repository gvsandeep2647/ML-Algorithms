package machinelearning;
import java.util.*;

/**
 * A class which used for calculating the entropy of a given attribute in the passed dataset.
 *
 */
public class AttributeEntropy {
	public int attribute;
	public Map<Integer,int[]> diversity;
	public boolean flag;
	double entropy;
	public AttributeEntropy(int attribute) {
		flag = true;
		this.attribute = attribute;
		diversity = new HashMap<Integer,int[]>();
		entropy = 0.0;
	}
	
	/**
	 * @param matrix : Dataset
	 * Updates the values of the maps which contain the attribute value as a key and the corresponding array of positive and negative count as value
	 */
	public void updateFields(int matrix[][]){
		for(int i=0;i<matrix.length;i++){
			if(!diversity.containsKey(ID3.matrix[i][attribute]))
			{
				diversity.put(ID3.matrix[i][attribute], new int[2]);
				if(ID3.matrix[i][14]==1)
					diversity.get(ID3.matrix[i][attribute])[1]++;
				else
					diversity.get(ID3.matrix[i][attribute])[0]++;
			}
			else
			{
				if(ID3.matrix[i][14]==1)
					diversity.get(ID3.matrix[i][attribute])[1]++;
				else
					diversity.get(ID3.matrix[i][attribute])[0]++;
			}		
		}
	}
	
	/**
	 * Updates the class variable entropy by calculating the entropy of the attribute
	 */
	public void calcEntropy() {
		double indiEntropy[] =  new double[diversity.size()];
		int sum_value=0;
		int i = 0;
		entropy = 0.0;
		for (Map.Entry<Integer, int[]> entry : diversity.entrySet()) {
		    int[] value = entry.getValue();
		    double temp1 = (double)value[0]/(value[0]+value[1]);
		    double temp2 = (double)value[1]/(value[0]+value[1]);
		    indiEntropy[i] = (-1)*temp1*(Math.log(temp1)/Math.log(2))+ (-1)*temp2*(Math.log(temp2)/Math.log(2));
		    if(Double.isNaN(indiEntropy[i]))
		    	indiEntropy[i] = 0.0;
		    entropy += (value[0]+value[1])*indiEntropy[i];
		    sum_value += value[0]+value[1];
		    i++;
		}
		
		entropy = entropy/sum_value;
	}
	
	/**
	 * utility function
	 * Used for printing the Map
	 */
	public void printMap(){
		for(Map.Entry<Integer, int[]> entry : diversity.entrySet()){
			int []value = entry.getValue();
			System.out.println(entry.getKey()+" "+value[0]+" "+value[1]);
		}
	}
}
