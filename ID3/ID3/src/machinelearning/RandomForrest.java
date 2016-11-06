package machinelearning;

import java.util.*;

/**
 * A class to implement Random Forest Algorithm on our generated ID3 trees.
 * It stores all the root nodes of the generated trees and then traverses through each of the tree and stores the result of each tree.
 * The we find the majority and give that as the result
 */
public class RandomForrest {
	/** resultRandom[i][j] = 1 if the jth tree classified the ith row of the dataset correctly. 0 otherwise */
	int resultRandom[][] = new int[15060][200];
	/** An arrayList storing the root nodes of all the generated trees.*/
	ArrayList<Tree> genTrees = new ArrayList<Tree>();
	/**
	 * @return An array of randomly chosen 10 numbers within the range 0-13 (both inclusive). The values will not repeat
	 */
	public int[] generateRandomAttr(){
		int attr[] = new int[10];
		ArrayList<Integer> list = new ArrayList<Integer>();
        for (int i=0; i<14; i++) {
            list.add(new Integer(i));
        }
        Collections.shuffle(list);
        for (int i=0;i<10; i++) {
            attr[i] = list.get(i);
        }
        return attr;
	}
	/**
	 * @param matrix :  The Dataset in the form of a numeric matrix which is returned from the formMatrix() method
	 * @return A matrix which stores whether a tree has properly classified the given example or not.
	 */
	public int[] populateMatrix(int matrix[][]){
		int values[] = new int[matrix.length];
		for(int i=0;i<genTrees.size();i++)
		{
			for(int j=0;j<matrix.length;j++)
				resultRandom[j][i] = genTrees.get(i).traversal(matrix[j]);
		}
		System.out.println();
		for(int i=0;i<matrix.length;i++)
		{
			int positive=0,negative=0;
			for(int j = 0;j<resultRandom[i].length;j++)
			{
				if(resultRandom[i][j]==1)
					positive++;
				else
					negative++;
			}
			values[i] = (positive>negative)?matrix[i][14]:1-matrix[i][14];
		}
		
		findAccuracy(values,matrix);
		return values;
	}
	/**
	 * @param values : The values is an array returned by the populateMatrix function 
	 * @param matrix : The Dataset in the form of a numeric matrix which is returned from the formMatrix() method
	 * Prints the accuracy of the Random Forest implementation
	 */
	public void findAccuracy(int values[],int matrix[][]){
		double accuracy = 0.0;
  		int result[] = {0,0};
  		for(int i=0;i<values.length;i++){
  			if(values[i]==matrix[i][14])
  				result[1]++;
  			else
  				result[0]++;
  		}
  		accuracy = ((double)result[1]/(result[0]+result[1]))*100;
  		accuracy = Math.round(accuracy*100) / 100.0;
		System.out.println("Accuracy of the Random Forrest is : " + accuracy+"%");
  		System.out.println("It has correctly classified "+result[1]+" instances out of "+(result[0]+result[1])+" instances" );
	}
}
