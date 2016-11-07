package machinelearning;

import java.util.*;

public class AdaBoost {
	public List<int[]> getRandom(double[] prob, List<int[]> l, int n)
    {
        assert l.size()==prob.length;
        double c_prob[]=new double[prob.length];
        c_prob[0]=prob[0];
        for(int i=1;i<prob.length;i++)
        	c_prob[i]=prob[i]+c_prob[i-1];
        List<int[]> list=new ArrayList<int []>();
        for(int i=0;i<n;i++)
        {
            double x=Math.random();
            for(int j=0;j<l.size();j++)
            {
                if(x<=c_prob[j])
                {
                    list.add(l.get(j));
                    break;
                }
            }
        }
        return list;
    }
	 public void adaBoost(int num_trees,int matrix[][]){
		 double[] weights = new double[matrix.length];
		 double[] alphas = new double[num_trees];
		 for(int i=0;i<matrix.length;i++)
		 {
			 weights[i]=1.0/(double)matrix.length;
		 }
		 for(int i = 0 ; i<num_trees;i++)
		 {
			 double error = 0.0;
			 
		 }
	 }
}

