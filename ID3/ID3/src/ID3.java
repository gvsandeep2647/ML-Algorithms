import java.io.*;
import java.util.*;
/**
 * @author Sandeep,Snehal,Kushagra,Tanmay
 * AIM : To implement and find the efficiency of ID3 algorithm on a particular Dataset.
 */

/**
 * DataSet is an object which captures the information of one row in the Data.
 */
class DataSet{
	int age;
	String workClass;
	int fnlwgt;
	String education;
	int educationNum;
	String maritalStatus;
	String occupation;
	String relationship;
	String race;
	String sex;
	int capitalGain;
	int capitalLoss;
	int hoursPerWeek;
	String nativeCountry;
	int result;
	DataSet(int age,String workClass,int fnlwgt,String education,int educationNum,String maritalStatus,String occupation,String relationship,String race,String sex,int capitalGain,int capitalLoss,int hoursPerWeek,String nativeCountry,String income){
		this.age =  age;
		this.workClass = workClass;
		this.fnlwgt = fnlwgt;
		this.education = education;
		this.educationNum = educationNum;
		this.maritalStatus = maritalStatus;
		this.occupation = occupation;
		this.relationship = relationship;
		this.race = race;
		this.sex = sex;
		this.capitalGain = capitalGain;
		this.capitalLoss = capitalLoss;
		this.hoursPerWeek = hoursPerWeek;
		this.nativeCountry = nativeCountry;
		result = income.equals("<=50k")?0:1;
	}
	
}
/*
 * DataCount is an object that stores the count of +,- examples for each feature of each attribute
 * the value of each key is int[], 0th index stores count for "<=50k" and 1st index stores count for ">50k"
 */
class DataCount{
	Map<String,int[]> age = new HashMap<String, int[]>();
	Map<String,int[]> workClass = new HashMap<String, int[]>();
	Map<String,int[]> fnlwgt = new HashMap<String, int[]>();
	Map<String,int[]> education = new HashMap<String, int[]>();
	Map<String,int[]> educationNum = new HashMap<String, int[]>();
	Map<String,int[]> maritalStatus = new HashMap<String, int[]>();
	Map<String,int[]> occupation = new HashMap<String, int[]>();
	Map<String,int[]> relationship = new HashMap<String, int[]>();
	Map<String,int[]> race = new HashMap<String, int[]>();
	Map<String,int[]> sex = new HashMap<String, int[]>();
	Map<String,int[]> capitalGain = new HashMap<String, int[]>();
	Map<String,int[]> capitalLoss = new HashMap<String, int[]>();
	Map<String,int[]> hoursPerWeek = new HashMap<String, int[]>();
	Map<String,int[]> nativeCountry = new HashMap<String, int[]>();
	DataCount(){
		
		//age is continuous. Split appropriately.
		
		workClass.put("Private", new int[2]);
		workClass.put("Self-emp-not-inc",new int[2]);
		workClass.put("Self-emp-inc",new int[2]);
		workClass.put("Federal-giv",new int[2]);
		workClass.put("Local-gov",new int[2]);
		workClass.put("State-gov",new int[2]);
		workClass.put("Without-pay",new int[2]);
		workClass.put("Never-worked",new int[2]);
		
		//fnlwgt is continuous. Split appropriately.
		
		education.put("Bachelors", new int[2]);
		education.put("Some-college", new int[2]);
		education.put("11th", new int[2]);
		education.put("HS-grad", new int[2]);
		education.put("Prof-school", new int[2]);
		education.put("Assoc-acdm", new int[2]);
		education.put("Assoc-voc", new int[2]);
		education.put("9th", new int[2]);
		education.put("7th-8th", new int[2]);
		education.put("12th", new int[2]);
		education.put("Masters", new int[2]);
		education.put("1st-4th", new int[2]);
		education.put("10th", new int[2]);
		education.put("Doctorate", new int[2]);
		education.put("5th-6th", new int[2]);
		education.put("Preschool", new int[2]);
		
		//educationNum is continuous. Split appropriately.
		
		maritalStatus.put("Married-civ-spouse",new int[2]);
		maritalStatus.put("Divorced",new int[2]);
		maritalStatus.put("Never-married",new int[2]);
		maritalStatus.put("Separated",new int[2]);
		maritalStatus.put("Widowed",new int[2]);
		maritalStatus.put("Married-spouse-absent",new int[2]);
		maritalStatus.put("Married-AF-spouse",new int[2]);
		
		occupation.put("Tech-support", new int[2]);
		occupation.put("Craft-repair", new int[2]);
		occupation.put("Other-service", new int[2]);
		occupation.put("Sales", new int[2]);
		occupation.put("Exec-managerial", new int[2]);
		occupation.put("Prof-specialty", new int[2]);
		occupation.put("Handlers-cleaners", new int[2]);
		occupation.put("Machine-op-inspct", new int[2]);
		occupation.put("Adm-clerical", new int[2]);
		occupation.put("Farming-fishing", new int[2]);
		occupation.put("Transport-moving", new int[2]);
		occupation.put("Priv-house-serv", new int[2]);
		occupation.put("Protective-serv", new int[2]);
		occupation.put("Armed-Forces", new int[2]);
		
		relationship.put("Wife", new int[2]);
		relationship.put("Own-child", new int[2]);
		relationship.put("Husband", new int[2]);
		relationship.put("Not-in-family", new int[2]);
		relationship.put("other-relative", new int[2]);
		relationship.put("Unmarried", new int[2]);
		
		race.put("White", new int[2]);
		race.put("Asian-Pac-Islander", new int[2]);
		race.put("Amer-Indian-Eskimo", new int[2]);
		race.put("Other", new int[2]);
		race.put("Black", new int[2]);
		
		sex.put("Male",new int[2]);
		sex.put("Female",new int[2]);
		
		//capitalGain continuous 
		//capitalLoss continuous
		//hoursPerWeek continuous

		nativeCountry.put("United-States",new int[2]);
		nativeCountry.put("Cambodia",new int[2]);
		nativeCountry.put("England",new int[2]);
		nativeCountry.put("Puerto-Rico",new int[2]);
		nativeCountry.put("Canada",new int[2]);
		nativeCountry.put("Germany",new int[2]);
		nativeCountry.put("Outlying-US(Guam-USVI-etc)",new int[2]);
		nativeCountry.put("India",new int[2]);
		nativeCountry.put("Japan",new int[2]);
		nativeCountry.put("Greece",new int[2]);
		nativeCountry.put("South",new int[2]);
		nativeCountry.put("China",new int[2]);
		nativeCountry.put("Cuba",new int[2]);
		nativeCountry.put("Iran",new int[2]);
		nativeCountry.put("Honduras",new int[2]);
		nativeCountry.put("Philippines",new int[2]);
		nativeCountry.put("Italy",new int[2]);
		nativeCountry.put("Poland",new int[2]);
		nativeCountry.put("Jamaica",new int[2]);
		nativeCountry.put("Vietnam",new int[2]);
		nativeCountry.put("Mexico",new int[2]);
		nativeCountry.put("Portugal",new int[2]);
		nativeCountry.put("Ireland",new int[2]);
		nativeCountry.put("France",new int[2]);
		nativeCountry.put("Dominican-Republic",new int[2]);
		nativeCountry.put("Laos",new int[2]);
		nativeCountry.put("Ecuador",new int[2]);
		nativeCountry.put("Taiwan",new int[2]);
		nativeCountry.put("Haiti",new int[2]);
		nativeCountry.put("Columbia",new int[2]);
		nativeCountry.put("Hungary",new int[2]);
		nativeCountry.put("Guatemala",new int[2]);
		nativeCountry.put("Nicaragua",new int[2]);
		nativeCountry.put("Scotland",new int[2]);
		nativeCountry.put("Thailand",new int[2]);
		nativeCountry.put("Yugoslavia",new int[2]);
		nativeCountry.put("El-Salvador",new int[2]);
		nativeCountry.put("Trinadad&Tobago",new int[2]);
		nativeCountry.put("Peru",new int[2]);
		nativeCountry.put("Hong",new int[2]);
		nativeCountry.put("Holand-Netherlands",new int[2]);
		
	}
}

public class ID3 {
	public static ArrayList<DataSet> data = new ArrayList<DataSet>();
  	public static void main(String[] args) {
        try{
        	inputHandle();
        }catch(Exception e){
        	System.out.println(e);
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
         System.out.println(data.size());
         br.close();
  	}
}