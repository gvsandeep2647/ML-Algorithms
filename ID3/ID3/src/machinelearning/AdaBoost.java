package machinelearning;
import java.util.*;

class AdaBoost {
	double[] alphas;
	ArrayList<Tree> trees = new ArrayList<Tree>();
	double values[] = new double[15060];
	int num_trees;
	AdaBoost(int num){
		num_trees= num;
		alphas = new double[num];
	}
	 public void adaBoost(int matrix[][]){
		 double[] weights = new double[matrix.length];
		 
		 for(int i=0;i<matrix.length;i++)
		 {
			 weights[i]=1.0/(double)matrix.length;
		 }
		 Tree root = new Tree(0, 0);
		 ArrayList<AttributeEntropy> attEnt = new ArrayList<AttributeEntropy>();
		 for(int i = 0 ; i<num_trees;i++)
		 {
			 attEnt = new ArrayList<AttributeEntropy>();
			 for(int k = 0;k<14;k++){
				 attEnt.add(new AttributeEntropy(k));
			 }
			 int attrToConsider[] = {0,1,2,3,4,5,6,7,8,9,10,11,12,13};

			 int firstAttribute = ID3.findA(matrix,attEnt,attrToConsider);
			 root = new Tree(firstAttribute,-1);
			 DistributedRandomNumberGenerator drng = new DistributedRandomNumberGenerator();
			 for(int s=0;s<30162;s++)
			 {
				 drng.addNumber(s, weights[s]);
			 }
			 int tempArr[] = new int[20108];
			 for(int p=0;p<20108;p++)
			 {
				 tempArr[p] = drng.getDistributedRandomNumber();
			 }
             int tempMatrix[][] = new int[tempArr.length][15];
             for(int k = 0; k<tempMatrix.length;k++)
             {
        		 tempMatrix[k] = matrix[tempArr[k]];
             }
             root.children = ID3.runID3(tempMatrix,firstAttribute,attEnt,true);
             trees.add(root);
             int posClass[] = new int[30162];
             for(int l=0;l<matrix.length;l++)
             {
            	 posClass[l] = root.traversal(matrix[l]);
             }
			 int pos=0 ,neg=0;
			 for(int l =0;l<posClass.length;l++)
			 {
				 if(posClass[l]==0)
					 pos++;
				 else
					 neg++;
			 }
			 double error = (double)pos/neg;
			 alphas[i] = 0.5*Math.log((1-error)/error);
			 double sum_W = 0.0;
			 for(int l =0;l<posClass.length;l++)
			 {
				 if(posClass[l]==1)
	                {
	                    weights[l]=weights[l]*Math.exp(alphas[i]);
	                }
	                else
	                {
	                    weights[l]=weights[l]*Math.exp(-alphas[i]);
	                }
	                sum_W+=weights[l];
			 }
			 for(int j=0;j<weights.length;j++)
			 {
                weights[j]=weights[j]/sum_W;
			 }
		 }
	 }
	 
	 public void calcAccuracy(int testMatrix[][]){
		 double accuracy = 0.0;
	  		int result[] = {0,0};
	  		for(int i=0;i<testMatrix.length;i++){
	  			for(int j =0;j<num_trees;j++){
	  				int temporary;
	  				if(trees.get(j).traversal(testMatrix[i])==1)
	  					temporary = (testMatrix[i][14]==0)?-1:1;
	  				else
	  					temporary = (1-testMatrix[i][14]==0)?-1:1;
	  				values[i] += alphas[j] * temporary;
	  			}
	  		}
	  		for(int i=0;i<values.length;i++){
	  			if(values[i]>=0)
	  			{
	  				if(testMatrix[i][14]==1)
	  					result[1]++;
	  				else
	  					result[0]++;
	  			}
  				else{
  					if(testMatrix[i][14]==0)
	  					result[1]++;
	  				else
	  					result[0]++;
  				}
	  		}
	  		accuracy = ((double)result[1]/(result[0]+result[1]))*100;
	  		accuracy = Math.round(accuracy*100) / 100.0;
			System.out.println("Accuracy of the Adaboost is : " + accuracy+"%");
	  		System.out.println("It has correctly classified "+result[1]+" instances out of "+(result[0]+result[1])+" instances" );
	 }
}
