package machinelearning;
import java.io.*;
import java.util.*;

/**
 * @author Sandeep,Snehal,Kushagra,Tanmay
 * AIM : To implement and find the efficiency of ID3 algorithm on a particular Dataset.
 */

public class ID3 {
	public static ArrayList<DataSet> data = new ArrayList<DataSet>();
  	public static void main(String[] args) {
        try{
        	inputHandle();
        }catch(Exception e){
        	System.out.println(e);
        }
        DataCount firstLevel = new DataCount();
        firstLevel.updateValues(calcSplit(data,"age"),calcSplit(data,"fnlwgt"),calcSplit(data,"educationNum"),calcSplit(data,"capitalGain"),calcSplit(data,"capitalLoss"),calcSplit(data,"hoursPerWeek"),data);
        TreeBuilder tree = new TreeBuilder();
        List<String> attributes = Arrays.asList("age","workClass","fnlwgt","education","educationNum","maritalStatus","occupation","relationship","race","sex","capitalGain","capitalLoss","hoursPerWeek","nativeCountry");
        System.out.println(tree.findRoot(firstLevel,attributes));
  	}
  	/**
  	 * @throws IOException
  	 * Reads the data from the text file and stores it in the object.
  	 */
  	public static void inputHandle()throws IOException {
  		 BufferedReader br = new BufferedReader(new FileReader("adult.txt"));
         String line=null;
         int flag = 1;
         while( (line=br.readLine()) != null) {
        	flag = 1;
			StringTokenizer st = new StringTokenizer(line,", ");
			int age=0;
			String workClass=null;
			int fnlwgt=0;
			String education=null;
			int educationNum=0;
			String maritalStatus=null;
			String occupation=null;
			String relationship = null;
			String race=null;
			String sex=null;
			int capitalGain=0;
			int capitalLoss=0;
			int hoursPerWeek=0;
			String nativeCountry=null;
			String income = null;
             while(st.hasMoreTokens()){
            	 age = Integer.parseInt(st.nextToken());
            	 workClass = st.nextToken();
            	 if(workClass.equals("?"))
            		 flag = 0;
            	 fnlwgt = Integer.parseInt(st.nextToken());
            	 education = st.nextToken();
            	 educationNum = Integer.parseInt(st.nextToken());
            	 maritalStatus = st.nextToken();
            	 occupation = st.nextToken();
            	 if(occupation.equals("?"))
            		 flag = 0;
            	 relationship = st.nextToken();
            	 race = st.nextToken();
            	 sex = st.nextToken();
            	 capitalGain = Integer.parseInt(st.nextToken());
            	 capitalLoss = Integer.parseInt(st.nextToken());
            	 hoursPerWeek = Integer.parseInt(st.nextToken());
            	 nativeCountry = st.nextToken();
            	 if(nativeCountry.equals("?"))
            		 flag = 0;
            	 income = st.nextToken();
             }
             if(flag==1)
            	 data.add(new DataSet(age,workClass,fnlwgt,education,educationNum,maritalStatus,occupation,relationship,race,sex,capitalGain,capitalLoss,hoursPerWeek,nativeCountry,income));
         }
         br.close();
  	}
  	
  	
  	/**
  	 * @param data : Data passed as a list of DataSet Objects 
  	 * @param param : parameter for which split is supposed to be calculated 
  	 * @return split : returns the value on which split should happen 
  	 * Calculates where split should happen in continuous variables based on the gini index
  	 */
  	public static int calcSplit(ArrayList<DataSet> data, String param){
  		Map<Integer, Integer> _cnt = new HashMap<Integer, Integer>();	//dictionary to store count of particular feature with result of instance <=50K
  		Map<Integer, Integer> cnt_ = new HashMap<Integer, Integer>();	//dictionary to store count of particular feature with result of instance >50K
  		ArrayList<Integer> list = new ArrayList<Integer>();		//store unique values the feature can take
  		DataSet obj;
  		int N = data.size();
  		for(int i=0;i<N;i++){
  			obj = data.get(i);
  			int value;
  			if(param.equals("age")){
  				value = obj.age;
  			}
  			else if(param.equals("fnlwgt")){
  				value = obj.fnlwgt;
  			}
  			else if(param.equals("educationNum")){
  				value = obj.educationNum;
  			}
  			else if(param.equals("capitalGain")){
  				value = obj.capitalGain;
  			}
  			else if(param.equals("capitalLoss")){
  				value = obj.capitalLoss;
  			}
  			else{
  				value = obj.hoursPerWeek;
  			}
  			if(!list.contains(value)){
  				list.add(value);
  				if(obj.result == 0){
  					_cnt.put(value,1);
  					cnt_.put(value, 0);
  				}
  				else{
  					_cnt.put(value,0);
  					cnt_.put(value,1);
  				}
  			}
  			else{
  				if(obj.result==0){
  					_cnt.put(value, _cnt.get(value)+1);
  				}
  				else
  					cnt_.put(value, cnt_.get(value)+1);
  			}
  		}
  		Collections.sort(list);

  		for(int i=1;i<list.size();i++){
  			_cnt.put(list.get(i),_cnt.get(list.get(i))+_cnt.get(list.get(i-1)));
  			cnt_.put(list.get(i),cnt_.get(list.get(i))+cnt_.get(list.get(i-1)));
  		}
  		//count matrix - count[0][i] and count[0][i+i] has the split value 
  		//count matrix - count[1][i] has no of instances <= split value and result = <=50K; count[1][i+1] has no of instances <=split value and result = >50K
  		//count matrix - count[2][i] has no of instances > split value and result = <=50K; count[2][i+1] has no of instances > split value and result = > 50K
  		int count[][] = new int[3][(list.size()-1)*2];
  		
  		for(int i=0;i<list.size()-1;i++){
  			count[0][2*i] = count[0][2*i+1] = (list.get(i)+list.get(i+1))/2;
  			count[1][2*i] = _cnt.get(list.get(i));
  			count[2][2*i] = cnt_.get(list.get(i));
  			count[1][2*i+1] = _cnt.get(list.get(list.size()-1))-count[1][2*i];
  			count[2][2*i+1] = cnt_.get(list.get(list.size()-1))-count[2][2*i];
  		}
  		int split = -1;
  		double minGini = 1;
  		for(int i=0;i<(list.size()-1)*2;i+=2){
  			double total1 = count[1][i] + count[2][i];
  			double total2 = count[1][i+1] + count[2][i+1];
  			double gini1 = 1 - Math.pow(count[1][i]/total1,2) - Math.pow(count[2][i]/total1,2);
  			double gini2 = 1 - Math.pow(count[1][i+1]/total2, 2) - Math.pow(count[2][i+1]/total2,2);
  			double gini = (total1/N)*gini1 + (total2/N)*gini2;
  			if(gini<minGini){
  				minGini = gini;
  				split = count[0][i];
  			}
  		}
  		return split;
  	}  	
}