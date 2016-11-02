package machinelearning;
import java.io.*;
import java.util.*;

/**
 * @author Sandeep,Snehal,Kushagra,Tanmay
 * AIM : To implement and find the efficiency of ID3 algorithm on a particular Dataset.
 */

public class ID3 {
	static int ageSplit;
	static int fnlSplit;
	static int eduNumSplit;
	static int capitalGainSplit;
	static int capitalLossSplit;
	static int hoursPerWeekSplit;
	public static ArrayList<DataSet> data = new ArrayList<DataSet>();
	public static int matrix[][] = new int[30162][15];
  	public static void main(String[] args) {
        try{
        	inputHandle();
        }catch(Exception e){
        	System.out.println(e);
        }
        ageSplit = calcSplit(data,"age");
        fnlSplit = calcSplit(data,"fnlwgt");
        eduNumSplit = calcSplit(data,"educationNum");
        capitalGainSplit = calcSplit(data,"capitalGain");
        capitalLossSplit = calcSplit(data,"capitalLoss");
        hoursPerWeekSplit = calcSplit(data,"hoursPerWeek");
        
        formMatrix();
        
        
        for(int i=0;i<14;i++)
    	{
        	AttributeEntropy attEnt = new AttributeEntropy(i);
        	attEnt.updateFields(i, matrix);
        	attEnt.printMap();
    	}
  	}
  	public static void formMatrix(){
  		int i = 0 ;
  		for (Iterator<DataSet> iterator = data.iterator(); iterator.hasNext();) {
  			DataSet dataItem = (DataSet) iterator.next();
  			
  			if(dataItem.age<=ageSplit)
  				matrix[i][0] = 0;
  			else
  				matrix[i][0] = 1;
  			
  			
  			if(dataItem.workClass.equals("Private"))
  				matrix[i][1] = 0;
  			else if(dataItem.workClass.equals("Self-emp-not-inc"))
  				matrix[i][1] = 1;
			else if(dataItem.workClass.equals("Self-emp-inc"))
				matrix[i][1] = 2;
			else if(dataItem.workClass.equals("Federal-gov"))
				matrix[i][1] = 3;
			else if(dataItem.workClass.equals("Local-gov"))
				matrix[i][1] = 4;
			else if(dataItem.workClass.equals("State-gov"))
				matrix[i][1] = 5;
			else if(dataItem.workClass.equals("Without-pay"))
				matrix[i][1] = 6;
			else if(dataItem.workClass.equals("Never-worked"))
				matrix[i][1] = 7;
  			
  			
  			if(dataItem.fnlwgt<=fnlSplit)
  				matrix[i][2] = 0;
  			else
  				matrix[i][2] = 1;
  			
  			
  			if(dataItem.education.equals("Bachelors"))
  				matrix[i][3]=0;
  			else if(dataItem.education.equals("Some-college"))
  				matrix[i][3]=1;
			else if(dataItem.education.equals("11th"))
				matrix[i][3]=2;
			else if(dataItem.education.equals("HS-grad"))
				matrix[i][3]=3;
			else if(dataItem.education.equals("Prof-school"))
				matrix[i][3]=4;
			else if(dataItem.education.equals("Assoc-acdm"))
				matrix[i][3]=5;
			else if(dataItem.education.equals("Assoc-voc"))
				matrix[i][3]=6;
			else if(dataItem.education.equals("9th"))
				matrix[i][3]=7;
			else if(dataItem.education.equals("7th-8th"))
				matrix[i][3]=8;
			else if(dataItem.education.equals("12th"))
				matrix[i][3]=9;
			else if(dataItem.education.equals("Masters"))
				matrix[i][3]=10;
			else if(dataItem.education.equals("1st-4th"))
				matrix[i][3]=11;
			else if(dataItem.education.equals("10th"))
				matrix[i][3]=12;
			else if(dataItem.education.equals("Doctorate"))
				matrix[i][3]=13;
			else if(dataItem.education.equals("5th-6th"))
				matrix[i][3]=14;
			else if(dataItem.education.equals("Preschool"))
				matrix[i][3]=15;
  			
  			
  			if(dataItem.educationNum<=eduNumSplit)
  				matrix[i][4] = 0;
  			else
  				matrix[i][4] = 1;
  			
  			
  			if(dataItem.maritalStatus.equals("Married-civ-spouse"))
  				matrix[i][5]=0;
  			else if(dataItem.maritalStatus.equals("Divorced"))
  				matrix[i][5]=1;
			else if(dataItem.maritalStatus.equals("Never-married"))
				matrix[i][5]=2;
			else if(dataItem.maritalStatus.equals("Separated"))
				matrix[i][5]=3;
			else if(dataItem.maritalStatus.equals("Widowed"))
				matrix[i][5]=4;
			else if(dataItem.maritalStatus.equals("Married-spouse-absent"))
				matrix[i][5]=5;
			else if(dataItem.maritalStatus.equals("Married-AF-spouse"))
				matrix[i][5]=6;
  			
  			
  			if(dataItem.occupation.equals("Tech-support"))
  				matrix[i][6]=0;
  			else if(dataItem.occupation.equals("Craft-repair"))
  				matrix[i][6]=1;
			else if(dataItem.occupation.equals("Other-service"))
				matrix[i][6]=2;
			else if(dataItem.occupation.equals("Sales"))
				matrix[i][6]=3;
			else if(dataItem.occupation.equals("Exec-managerial"))
				matrix[i][6]=4;
			else if(dataItem.occupation.equals("Prof-specialty"))
				matrix[i][6]=5;
			else if(dataItem.occupation.equals("Handlers-cleaners"))
				matrix[i][6]=6;
			else if(dataItem.occupation.equals("Machine-op-inspct"))
				matrix[i][6]=7;
			else if(dataItem.occupation.equals("Adm-clerical"))
				matrix[i][6]=8;
			else if(dataItem.occupation.equals("Farming-fishing"))
				matrix[i][6]=9;
			else if(dataItem.occupation.equals("Transport-moving"))
				matrix[i][6]=10;
			else if(dataItem.occupation.equals("Priv-house-serv"))
				matrix[i][6]=11;
			else if(dataItem.occupation.equals("Protective-serv"))
				matrix[i][6]=12;
			else if(dataItem.occupation.equals("Armed-Forces"))
				matrix[i][6]=13;
  			
  			
  			if(dataItem.relationship.equals("Wife"))
  				matrix[i][7]=0;
  			else if(dataItem.relationship.equals("Own-child"))
  				matrix[i][7]=1;
			else if(dataItem.relationship.equals("Husband"))
				matrix[i][7]=2;
			else if(dataItem.relationship.equals("Not-in-family"))
				matrix[i][7]=3;
			else if(dataItem.relationship.equals("Other-relative"))
				matrix[i][7]=4;
			else if(dataItem.relationship.equals("Unmarried"))
				matrix[i][7]=5;
  				
  			
  			if(dataItem.race.equals("White"))
  				matrix[i][8]=0;
  			else if(dataItem.race.equals("Asian-Pac-Islander"))
  				matrix[i][8]=1;
			else if(dataItem.race.equals("Amer-Indian-Eskimo"))
				matrix[i][8]=2;
			else if(dataItem.race.equals("Other"))
				matrix[i][8]=3;
			else if(dataItem.race.equals("Black"))
				matrix[i][8]=4;
  			
  			if(dataItem.sex.equals("Male"))
  				matrix[i][9]=0;
  			else if(dataItem.sex.equals("Female"))
  				matrix[i][9]=1;
  			
  			
  			if(dataItem.capitalGain<=capitalGainSplit)
  				matrix[i][10] = 0;
  			else
  				matrix[i][10] = 1;
  			
  			
  			if(dataItem.capitalLoss<=capitalLossSplit)
  				matrix[i][11] = 0;
  			else
  				matrix[i][11] = 1;
  			
  			
  			if(dataItem.hoursPerWeek<=hoursPerWeekSplit)
  				matrix[i][12] = 0;
  			else
  				matrix[i][12] = 1;
  			
  			if(dataItem.nativeCountry.equals("United-States"))
  				matrix[i][13]=0;
  			else if(dataItem.nativeCountry.equals("Cambodia"))
  				matrix[i][13]=1;
			else if(dataItem.nativeCountry.equals("England"))
				matrix[i][13]=2;
			else if(dataItem.nativeCountry.equals("Puerto-Rico"))
  				matrix[i][13]=3;
			else if(dataItem.nativeCountry.equals("Canada"))
				matrix[i][13]=4;
			else if(dataItem.nativeCountry.equals("Germany"))
  				matrix[i][13]=5;
			else if(dataItem.nativeCountry.equals("Outlying-US(Guam-USVI-etc)"))
				matrix[i][13]=6;
			else if(dataItem.nativeCountry.equals("India"))
  				matrix[i][13]=7;
			else if(dataItem.nativeCountry.equals("Japan"))
				matrix[i][13]=8;
			else if(dataItem.nativeCountry.equals("Greece"))
  				matrix[i][13]=9;
			else if(dataItem.nativeCountry.equals("South"))
				matrix[i][13]=10;
			else if(dataItem.nativeCountry.equals("China"))
  				matrix[i][13]=11;
			else if(dataItem.nativeCountry.equals("Cuba"))
				matrix[i][13]=12;
			else if(dataItem.nativeCountry.equals("Iran"))
  				matrix[i][13]=13;
			else if(dataItem.nativeCountry.equals("Honduras"))
				matrix[i][13]=14;
			else if(dataItem.nativeCountry.equals("Philippines"))
  				matrix[i][13]=15;
			else if(dataItem.nativeCountry.equals("Italy"))
				matrix[i][13]=16;
			else if(dataItem.nativeCountry.equals("Poland"))
  				matrix[i][13]=17;
			else if(dataItem.nativeCountry.equals("Jamaica"))
				matrix[i][13]=18;
			else if(dataItem.nativeCountry.equals("Vietnam"))
  				matrix[i][13]=19;
			else if(dataItem.nativeCountry.equals("Mexico"))
				matrix[i][13]=20;
			else if(dataItem.nativeCountry.equals("Portugal"))
  				matrix[i][13]=21;
			else if(dataItem.nativeCountry.equals("Ireland"))
				matrix[i][13]=22;
			else if(dataItem.nativeCountry.equals("France"))
  				matrix[i][13]=23;
			else if(dataItem.nativeCountry.equals("Dominican-Republic"))
				matrix[i][13]=24;
			else if(dataItem.nativeCountry.equals("Laos"))
  				matrix[i][13]=25;
			else if(dataItem.nativeCountry.equals("Ecuador"))
				matrix[i][13]=26;
			else if(dataItem.nativeCountry.equals("Taiwan"))
  				matrix[i][13]=27;
			else if(dataItem.nativeCountry.equals("Haiti"))
				matrix[i][13]=28;
			else if(dataItem.nativeCountry.equals("Columbia"))
  				matrix[i][13]=29;
			else if(dataItem.nativeCountry.equals("Hungary"))
				matrix[i][13]=30;
			else if(dataItem.nativeCountry.equals("Guatemala"))
  				matrix[i][13]=31;
			else if(dataItem.nativeCountry.equals("Nicaragua"))
				matrix[i][13]=32;
			else if(dataItem.nativeCountry.equals("Scotland"))
  				matrix[i][13]=33;
			else if(dataItem.nativeCountry.equals("Thailand"))
				matrix[i][13]=34;
			else if(dataItem.nativeCountry.equals("Yugoslavia"))
  				matrix[i][13]=35;
			else if(dataItem.nativeCountry.equals("El-Salvador"))
				matrix[i][13]=36;
			else if(dataItem.nativeCountry.equals("Trinadad&Tobago"))
  				matrix[i][13]=37;
			else if(dataItem.nativeCountry.equals("Peru"))
				matrix[i][13]=38;
			else if(dataItem.nativeCountry.equals("Hong"))
  				matrix[i][13]=39;
			else if(dataItem.nativeCountry.equals("Holand-Netherlands"))
				matrix[i][13]=40;
  			
			matrix[i][14] = dataItem.result;
  				
  			i++;
  		}
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