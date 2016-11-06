package machinelearning;

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
		result = income.equals("<=50K")?0:1;
	}
	
   @Override
   public String toString() {
        return ("Age: "+this.age+" Work Class: "+ this.workClass +" fnlwgt: "+ this.fnlwgt +" Education : " + this.education
        		+" Education Num: "+this.educationNum+" Marital Status "+this.maritalStatus+" Occuptaion: "+this.occupation
        		+ " RelationShip Status: "+this.relationship+" Race: "+this.race+" Sex: "+this.sex
        		+ " Capital Gain: "+this.capitalGain+" Capital Loss: "+this.capitalLoss+" Hours Per Week: "+this.hoursPerWeek
        		+ " nativeCountry :"+this.nativeCountry+" Result: "+this.result );
   }
	
}