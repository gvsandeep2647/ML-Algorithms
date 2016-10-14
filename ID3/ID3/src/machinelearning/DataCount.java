package machinelearning;

import java.util.*;

/*
 * DataCount is an object that stores the count of +,- examples for each feature of each attribute
 * the value of each key is int[], 0th index stores count for "<=50k" and 1st index stores count for ">50k"
 */
class DataCount{
	static Map<String,int[]> age = new HashMap<String, int[]>();
	static Map<String,int[]> workClass = new HashMap<String, int[]>();
	static Map<String,int[]> fnlwgt = new HashMap<String, int[]>();
	static Map<String,int[]> education = new HashMap<String, int[]>();
	static Map<String,int[]> educationNum = new HashMap<String, int[]>();
	static Map<String,int[]> maritalStatus = new HashMap<String, int[]>();
	static Map<String,int[]> occupation = new HashMap<String, int[]>();
	static Map<String,int[]> relationship = new HashMap<String, int[]>();
	static Map<String,int[]> race = new HashMap<String, int[]>();
	static Map<String,int[]> sex = new HashMap<String, int[]>();
	static Map<String,int[]> capitalGain = new HashMap<String, int[]>();
	static Map<String,int[]> capitalLoss = new HashMap<String, int[]>();
	static Map<String,int[]> hoursPerWeek = new HashMap<String, int[]>();
	static Map<String,int[]> nativeCountry = new HashMap<String, int[]>();
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
	
	/**
	 * @param ageSplit
	 * @param fnlSplit
	 * @param eduNumSplit
	 * @param capGainSplit
	 * @param capLossSplit
	 * @param hoursSplit
	 * 
	 * Updates the value of Maps on the basis if data and stores the count of +ve and -ve examples associated with the following features
	 */
	public static void updateValues(int ageSplit,int fnlSplit,int eduNumSplit,int capGainSplit,int capLossSplit,int hoursSplit,ArrayList<DataSet> data)
	{
		age.put("<="+ ageSplit,new int[2]);
		age.put(">"+ ageSplit,new int[2]);
		fnlwgt.put("<="+fnlSplit,new int[2]);
		fnlwgt.put(">"+fnlSplit, new int[2]);
		educationNum.put("<="+eduNumSplit,new int[2]);
		educationNum.put(">"+eduNumSplit, new int[2]);
		capitalGain.put("<="+capGainSplit,new int[2]);
		capitalGain.put(">"+capGainSplit, new int[2]);
		capitalLoss.put("<="+capLossSplit,new int[2]);
		capitalLoss.put(">"+capLossSplit, new int[2]);
		hoursPerWeek.put("<="+hoursSplit,new int[2]);
		hoursPerWeek.put(">"+hoursSplit, new int[2]);
		
		
		for (Iterator<DataSet> iterator = data.iterator(); iterator.hasNext();) {
			DataSet dataItem = (DataSet) iterator.next();
			if(dataItem.result==1)
			{
				if(dataItem.age<=ageSplit)
					age.get("<=")[0]++;
				else
					age.get(">")[0]++;
				workClass.get(dataItem.workClass)[0]++;
				if(dataItem.fnlwgt<=fnlSplit)
					fnlwgt.get("<=")[0]++;
				else
					fnlwgt.get(">")[0]++;
				education.get(dataItem.education)[0]++;
				if(dataItem.educationNum<=eduNumSplit)
					educationNum.get("<=")[0]++;
				else
					educationNum.get(">")[0]++;
				occupation.get(dataItem.occupation)[0]++;
				relationship.get(dataItem.relationship)[0]++;
				race.get(dataItem.race)[0]++;
				sex.get(dataItem.sex)[0]++;
				if(dataItem.capitalGain<=capGainSplit)
					capitalGain.get("<=")[0]++;
				else
					capitalGain.get(">")[0]++;
				if(dataItem.capitalLoss<=capLossSplit)
					capitalLoss.get("<=")[0]++;
				else
					capitalLoss.get(">")[0]++;
				if(dataItem.hoursPerWeek<=hoursSplit)
					hoursPerWeek.get("<=")[0]++;
				else
					hoursPerWeek.get(">")[0]++;
				nativeCountry.get(dataItem.nativeCountry)[1]++;
			}else{
				if(dataItem.age<=ageSplit)
					age.get("<=")[0]++;
				else
					age.get(">")[0]++;
				workClass.get(dataItem.workClass)[0]++;
				if(dataItem.fnlwgt<=fnlSplit)
					fnlwgt.get("<=")[0]++;
				else
					fnlwgt.get(">")[0]++;
				education.get(dataItem.education)[0]++;
				if(dataItem.educationNum<=eduNumSplit)
					educationNum.get("<=")[0]++;
				else
					educationNum.get(">")[0]++;
				occupation.get(dataItem.occupation)[0]++;
				relationship.get(dataItem.relationship)[0]++;
				race.get(dataItem.race)[0]++;
				sex.get(dataItem.sex)[0]++;
				if(dataItem.capitalGain<=capGainSplit)
					capitalGain.get("<=")[0]++;
				else
					capitalGain.get(">")[0]++;
				if(dataItem.capitalLoss<=capLossSplit)
					capitalLoss.get("<=")[0]++;
				else
					capitalLoss.get(">")[0]++;
				if(dataItem.hoursPerWeek<=hoursSplit)
					hoursPerWeek.get("<=")[0]++;
				else
					hoursPerWeek.get(">")[0]++;
				nativeCountry.get(dataItem.nativeCountry)[0]++;
			}
		}
	}
}
