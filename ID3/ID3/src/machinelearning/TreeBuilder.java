package machinelearning;
import java.util.*;

public class TreeBuilder {
	
	public Map<String,int[]> findRoot(DataCount tempData,List<String> attributes){
		printMap(tempData.nativeCountry);
		return tempData.age;
	}
	public double printMap(Map<String,int[]> mp) {
		double entropy = 0.0;
		double indiEntropy[] =  new double[mp.size()];
		int i = 0;
		for (Map.Entry<String, int[]> entry : mp.entrySet()) {
		    int[] value = entry.getValue();
		    double temp1 = value[0]/(value[0]+value[1]); 
		    double temp2 = value[1]/(value[0]+value[1]);
		    indiEntropy[i] = -temp1*Math.log(temp1)-temp2*Math.log(temp2);
		    i++;
		}
		double sum = 0.0;
		for(int j = 0;j<indiEntropy.length;j++ )
		{
			sum += indiEntropy[j];
		}
		return entropy;
	}
}
