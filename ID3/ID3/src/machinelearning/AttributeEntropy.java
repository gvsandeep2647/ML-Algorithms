package machinelearning;
import java.util.*;

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
	
	public void updateFields(int attribute,int matrix[][]){
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
	
	public void printMap(){
		int global= 0;
		for(Map.Entry<Integer, int[]> entry : diversity.entrySet()){
			int []value = entry.getValue();
			global += value[0]+value[1];
			System.out.println(entry.getKey()+" "+value[0]+" "+value[1]);
		}
		System.out.println(global);
	}
}
