
import java.io.*;
import java.util.*;

/**
 * @author Sandeep,Snehal,Kushagra,Tanmaya
 * @version 1.0
 * @since 25-10-2016
 */
public class ID3 {
	/**stores the value at which the age has been split. It is calculated using the calcSplit() method.*/
	static int ageSplit;
	/**stores the value at which the fnlwgt has been split. It is calculated using the calcSplit() method.*/
	static int fnlSplit;
	/**stores the value at which the educationNum has been split. It is calculated using the calcSplit() method.*/
	static int eduNumSplit;
	/**stores the value at which the capitalGain has been split. It is calculated using the calcSplit() method.*/
	static int capitalGainSplit;
	/**stores the value at which the capitalLoss has been split. It is calculated using the calcSplit() method.*/
	static int capitalLossSplit;
	/**stores the value at which the hoursPerWeek has been split. It is calculated using the calcSplit() method.*/
	static int hoursPerWeekSplit;
	/**An ArrayList of <b>data</b> objects. Each data object stores the details pertaining to one row of the dataSet. This is for the <i>Training Data</i>*/
	public static ArrayList<DataSet> data = new ArrayList<DataSet>();
	/**An ArrayList of <b>data</b> objects. Each data object stores the details pertaining to one row of the dataSet. This is for the <i>Testing Data</i>*/
	public static ArrayList<DataSet> testData = new ArrayList<DataSet>();
	/**A matrix which stores the numeric values in the matrix according to values stored in the DataRef Class. This is for the <i>Training Data</i>*/
	public static int matrix[][] = new int[30162][15];
	/**A matrix which stores the numeric values in the matrix according to values stored in the DataRef Class. This is for the <i>Testing Data</i>*/
	public static int testMatrix[][] = new int[15060][15];
	public static void main(String[] args) {
        try{
        	inputHandle("adult.txt",data);
        }catch(Exception e){
        	System.out.println("train "+e);
        }
        
        long LearningstartTime = System.currentTimeMillis();
        
        /*Building ID3 starts*/
        ageSplit = calcSplit(data,"age");
        fnlSplit = calcSplit(data,"fnlwgt");
        eduNumSplit = calcSplit(data,"educationNum");
        capitalGainSplit = calcSplit(data,"capitalGain");
        capitalLossSplit = calcSplit(data,"capitalLoss");
        hoursPerWeekSplit = calcSplit(data,"hoursPerWeek");
        formMatrix(matrix,data);
        ArrayList<AttributeEntropy> attEnt = new ArrayList<AttributeEntropy>();
        for(int i = 0;i<14;i++){
        	attEnt.add(new AttributeEntropy(i));
        }
        int arr[] = {0,1,2,3,4,5,6,7,8,9,10,11,12,13};
        int firstAttribute = findA(matrix,attEnt,arr);
        Tree root = new Tree(firstAttribute,-1);
        root.children = runID3(matrix,firstAttribute,attEnt,false);
        /*Building ID3 ends*/
        
        long LearningstopTime = System.currentTimeMillis();
        long elapsedTime = LearningstopTime - LearningstartTime;
        System.out.println("Time taken for learning and building the ID3: " + (double)elapsedTime/1000 + " seconds");
        
        /* On Testing Data */
        try{
        	inputHandle("newtesting1.txt",testData);
        }catch(Exception e){
        	System.out.println("Test "+e);
        }
        long TeststartTime = System.currentTimeMillis();
        formMatrix(testMatrix,testData);
        calcAccuracy(testMatrix,root);
        long TeststopTime = System.currentTimeMillis();
        long totelapsedTime = TeststopTime - TeststartTime;
        System.out.println("Time taken for learning and building the ID3 also predicting the results on the test data: " + (double)(elapsedTime+totelapsedTime)/1000 + " seconds");
        System.out.println("********************************");
        System.out.println();
        
       /*  RANDOM FOREST IMPLEMENTATION   */
       
        /**
        * Random Forest Implementation Begins here.
        * 300 trees of 4 attributes each.
        * */
        
       
        long RFstartTime = System.currentTimeMillis();
        
        
        RandomForrest rf = new RandomForrest();

        for(int i=0;i<300;i++)
        {
        	attEnt = new ArrayList<AttributeEntropy>();
        	for(int k = 0;k<14;k++){
            	attEnt.add(new AttributeEntropy(k));
            }
        	int attrToConsider[] = generateRandomAttr(14,4);
        	 firstAttribute = findA(matrix,attEnt,attrToConsider);
             root = new Tree(firstAttribute,-1);
             int tempArr[] = generateRandomAttr(30162,20108);
             int tempMatrix[][] = new int[tempArr.length][15];
            
             for(int k = 0; k<tempMatrix.length;k++)
             {
        		 tempMatrix[k] = matrix[tempArr[k]];
             }
             
             root.children = runID3(tempMatrix,firstAttribute,attEnt,true);
             rf.populateMatrix(testMatrix, root, i);
        }
        rf.findAccuracy(testMatrix);
        
        long RFstopTime = System.currentTimeMillis();
        long RFelapsedTime = RFstopTime - RFstartTime;
        System.out.println("Time taken for  the execution of Random Forest Algorithm: " + (double)(RFelapsedTime)/1000 + " seconds");
        System.out.println("********************************");
        System.out.println();
        
        
        
        /*  AdaBoost  */
        long AdastartTime = System.currentTimeMillis();
        
        AdaBoost ab = new AdaBoost(3000,10);
        
        ab.adaBoost(matrix,testMatrix);
        ab.calcAccuracy(testMatrix);
    
        long AdastopTime = System.currentTimeMillis();
        long AdaelapsedTime = AdastopTime - AdastartTime;
        System.out.println("Time taken for the execution after implementing the AdaBoost technique: " + (double)(AdaelapsedTime)/1000 + " seconds");
  	}

  	/**
  	 * @param matrix : The Dataset in the form of a numeric matrix which is returned from the formMatrix() method
  	 * @param targetAttribute : The Parent node at the current level 
  	 * @param attEnt : The arrayList of Attribute Entropy objects.
  	 * @param flag :  will be true if we are implementing random forest i.e random selection of attributes. False otherwise
  	 * @return Tree of ID3
  	 */
  	public static ArrayList<Tree> runID3(int matrix[][], int targetAttribute, ArrayList<AttributeEntropy> attEnt,boolean flag){
  		ArrayList<Tree> root = new ArrayList<Tree>();
  		int positive=0,negative=0;
  		AttributeEntropy tempAtt = attEnt.get(targetAttribute);
  		for(int i = 0;i<tempAtt.diversity.size();i++)
  		{	
  			try{
  			positive += tempAtt.diversity.get(i)[1];
  			negative += tempAtt.diversity.get(i)[0];
  			}catch(Exception e){
  				;
  			}
  		}
  		ArrayList<int[][]> temp = createDataSet(targetAttribute,matrix);
  		ArrayList<AttributeEntropy> nextAttEnt = new ArrayList<AttributeEntropy>();
  		for(int i=0;i<temp.size();i++){
  			int base = checkPN(temp.get(i));
  			if(base == -1){
  				Tree tempTree = new Tree(targetAttribute,i);
  				tempTree.addChild(14,0);
  				root.add(tempTree);
  			}
  			else if(base == 1){
  				Tree tempTree = new Tree(targetAttribute,i);
  				tempTree.addChild(14,1);
  				root.add(tempTree);
  			}
  			else if(base == 0){
  				for(int j=0;j<14;j++){
  					AttributeEntropy a = new AttributeEntropy(attEnt.get(j).attribute);
  					a.flag = attEnt.get(j).flag;
  					nextAttEnt.add(a);
  				}
  				int nextAttribute = -1;
  				if(!flag){
  					int arr[] = {0,1,2,3,4,5,6,7,8,9,10,11,12,13};
  					nextAttribute = findA(temp.get(i),nextAttEnt,arr);
  				}
  				else{
  					int attrToConsider[] = generateRandomAttr(14,4);
  					nextAttribute = findA(temp.get(i),nextAttEnt,attrToConsider);
  				}
  				nextAttEnt.get(targetAttribute).flag = false;
  				Tree tempTree = new Tree(targetAttribute,i);
  				if(nextAttribute==-1){
  					tempTree = new Tree(targetAttribute,i);
                    int pos = 0, neg = 0;
                      for(int z = 0;z<temp.get(i).length;z++){
                          if(temp.get(i)[z][14] == 0)
                              neg++;
                          else
                              pos++;
                      }
                      int baseVal = (neg>pos)?0:1;
                      tempTree.addChild(14,baseVal);
                      root.add(tempTree);
  				}
  				else{
	  				tempTree.children = runID3(temp.get(i),nextAttribute,nextAttEnt,flag);
	  				root.add(tempTree);
  				}
  			}
  			else if(base==2){
  				Tree tempTree = new Tree(targetAttribute,i);
  				int baseVal = (negative>=positive)?0:1;
  				tempTree.addChild(14,baseVal);
  				root.add(tempTree);
  			}
  		}
  		return root;
  	}
  	
  	/**
  	 * @param matrix : The Dataset in the form of a numeric matrix which is returned from the formMatrix() method
  	 * @param attEnt : An array of objects which contain the entropy of each attribute 
  	 * @param attributeToConsider : An array of attributes to be considered while choosing the next node.
  	 * @return the attribute with the lowest entropy, which will form the root at the current level
  	 */
  	public static int findA(int matrix[][], ArrayList<AttributeEntropy> attEnt, int[] attributeToConsider){
  		for(int i=0;i<14;i++){
  				attEnt.get(i).updateFields(matrix);
  				attEnt.get(i).calcEntropy();
  		}
  		int A=-1;
  		double min = 5.0;
  		for(int i=0;i<14;i++){
  			if(attEnt.get(i).flag && contains(i,attributeToConsider)){
  				if(attEnt.get(i).entropy<=min){
  					min = attEnt.get(i).entropy;
  					A = attEnt.get(i).attribute;
  				}
  			}
  		}
  		return A;
  	}
  	
  	/**
  	 * @param val : key to be searched
  	 * @param arr : data in which we have to search
  	 * @return an utility function which says whether an element exists in the array or not.
  	 */
  	public static boolean contains(int val, int arr[]){
  		for(int i=0;i<arr.length;i++){
  			if(val == arr[i]){
  				return true;
  			}
  		}
  		return false;
  	}
  	
  	/**
  	 * @param attribute : The attribute on the basis of which we are dividing the data matrix 
  	 * @param matrix : The Dataset in the form of a numeric matrix which is returned from the formMatrix() method
  	 * @return An ArrayList of Matrices with data divided according to the unique values that the passed argument attribute can take.
  	 */
  	public static ArrayList<int[][]> createDataSet(int attribute,int matrix[][]){
  		Set<Integer> setUniqueNumbers = new LinkedHashSet<Integer>();
  		List<Integer> occurences = new ArrayList<Integer>();
  		DataRef dr = new DataRef();
  		for(int x = 0;x<matrix.length;x++) {
  		    setUniqueNumbers.add(matrix[x][attribute]);
  		    occurences.add(matrix[x][attribute]);
  		}
  		int datamodel[][][] = new int[dr.attrRef[attribute].length][][];
  		for(int i = 0;i<dr.attrRef[attribute].length;i++){
  			datamodel[i] = new int[0][15];
  		}
  		for(int s: setUniqueNumbers){
  			datamodel[s] = new int[Collections.frequency(occurences,s)][15];
		}
  		
  		int indexCount[] = new int[dr.attrRef[attribute].length];
  		for(int i =0;i<matrix.length;i++)
  		{
  			for(int j=0;j<15;j++)
  			{
  				datamodel[matrix[i][attribute]][indexCount[matrix[i][attribute]]][j] = matrix[i][j];
  			}
  			indexCount[matrix[i][attribute]]++;
  		}
  		ArrayList<int [][]> matrices = new ArrayList<int [][]>();
  		for(int i = 0;i<dr.attrRef[attribute].length;i++)
			matrices.add(datamodel[i]);
  		return matrices;
  	}
  	
  	/**
  	 * @param matrix : The matrix which is to be filled
  	 * @param data : The Dataset in the form of a numeric matrix which is returned from the formMatrix() method
  	 * Fills the matrix with the updated values according to the values in the DataRef.
  	 */
  	public static void formMatrix(int [][]matrix,ArrayList<DataSet>data){
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
  	 * @param testData : The matrix filled according to the testing data  
  	 * @param root : The root node of the formed ID3 tree.
  	 * Prints the accuracy of the model.
  	 */
  	public static void calcAccuracy(int[][] testData,Tree root){
  		double accuracy = 0.0;
  		int result[] = {0,0};
  		for (int i=0;i<testData.length;i++) {
			 int dataSet[] = testData[i];
			if(root.traversal(dataSet)==1)
				result[1]++;
			else
				result[0]++;
		}
  		accuracy = ((double)result[1]/(result[0]+result[1]))*100;
  		accuracy = Math.round(accuracy*100) / 100.0;
  		System.out.println("Accuracy of the ID3 is : " + accuracy+"%");
  		System.out.println("It has correctly classified "+result[1]+" instances out of "+(result[0]+result[1])+" instances" );
  	}
  	
  	
  	/**
  	 * @param filename : the filename to be opened
  	 * @param data : The ArrayList of data objects which is to be populated on the basis of data
  	 * @throws IOException : To handle File not found error.
  	 * @see IOException
  	 */
  	public static void inputHandle(String filename,ArrayList<DataSet> data)throws IOException {
  		 BufferedReader br = new BufferedReader(new FileReader(filename));
         String line=null;
         int flag = 1;
         while( (line=br.readLine()) != null) {
        	flag = 1;
			StringTokenizer st = new StringTokenizer(line,",");
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
            	 age = Integer.parseInt((st.nextToken()).trim());
            	 workClass = st.nextToken().trim();
            	 if(workClass.equals("?"))
            		 flag = 0;
            	 fnlwgt = Integer.parseInt(st.nextToken().trim());
            	 education = st.nextToken().trim();
            	 educationNum = Integer.parseInt(st.nextToken().trim());
            	 maritalStatus = st.nextToken().trim();
            	 occupation = st.nextToken().trim();
            	 if(occupation.equals("?"))
            		 flag = 0;
            	 relationship = st.nextToken().trim();
            	 race = st.nextToken().trim();
            	 sex = st.nextToken().trim();
            	 capitalGain = Integer.parseInt(st.nextToken().trim());
            	 capitalLoss = Integer.parseInt(st.nextToken().trim());
            	 hoursPerWeek = Integer.parseInt(st.nextToken().trim());
            	 nativeCountry = st.nextToken().trim();
            	 if(nativeCountry.equals("?"))
            		 flag = 0;
            	 income = st.nextToken().trim();
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
  			_cnt.put(list.get(i),_cnt.get(list.get(i))+ _cnt.get(list.get(i-1)));
  			cnt_.put(list.get(i),cnt_.get(list.get(i))+ cnt_.get(list.get(i-1)));
  		}
  		/**count matrix - count[0][i] and count[0][i+i] has the split value*/ 
  		/**count matrix - count[1][i] has no of instances <=50K and <=Split Value; count[1][i+1] has no of instances >Split Value and result <=50K*/
  		/**count matrix - count[2][i] has no of instances >50K and <=Split Value; count[2][i+1] has no of instances >split value and result = >50K*/
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
  			double ent1,ent2;
  			if(count[1][i] == 0 || count[2][i]==0)
  				ent1 = 0.0;
  			else
  				ent1 = (-1)*(count[1][i]/total1)*Math.log(count[1][i]/total1)/Math.log(2) - (count[2][i]/total1)*Math.log(count[2][i]/total1)/Math.log(2);
  			if(count[1][i+1] ==0 || count[2][i+1] == 0)
  				ent2 = 0.0;
  			else
  				ent2 = (-1)*(count[1][i+1]/total2)*Math.log(count[1][i+1]/total2)/Math.log(2) - (count[2][i+1]/total2)*Math.log(count[2][i+1]/total2)/Math.log(2);
  			
  			double ent = (total1/N)*ent1 + (total2/N)*ent2;  
  			if(ent<minGini){
  				minGini = ent;
  				split = count[0][i];
  			}
  		}
  		return split;
  	} 
  	
  	/**
  	 * @param matrix : The Dataset in the form of a numeric matrix which is returned from the formMatrix() method
  	 * @return checks whether a matrix (result of a divide) contains only positively classified examples, negatively classified examples or a mix of those. This is to handle the base cases of the recursion
  	 */
  	public static int checkPN(int matrix[][]){
  		int result = 0;
  		if(matrix.length == 0){
  			return 2;
  		}
  		for(int i=0;i<matrix.length;i++){
  			result = result + matrix[i][14];
  		}
  		if(result==0){
  			return -1;
  		}
  		else if(result == matrix.length){
  			return 1;
  		}
  		
  		else
  			return 0;
  	}
  	
	/**
	 * @param limit : Random numbers will be generated between 0 - Limit
	 * @param length : 'Length' number of random numbers will be generated
	 * @return An array of randomly chosen 'length' numbers within the range 0-limit (both inclusive). The values will not repeat
	 */
	public static int[] generateRandomAttr(int limit,int length){
		int attr[] = new int[length];
		ArrayList<Integer> list = new ArrayList<Integer>();
        for (int i=0; i<limit; i++) {
            list.add(new Integer(i));
        }
        Collections.shuffle(list);
        for (int i=0;i<length; i++) {
            attr[i] = list.get(i);
        }
        return attr;
	} 
}

/**
 * A class to implement AdaBoost on our ID3 trees. 
 * It learns from 'num_trees' number of samples where each sample has 2/3rd of the entire Data.
 * It then associates each tree with a weight based on the error of classification of the tree.
 * The weights of each miss classified example is then updated based on the error of that particular tree.
 * At the end sign of the linear combination of all the trees give us the prediction and based on this the error is calculated.
 */
class AdaBoost {
	
	
	double values[] = new double[15060];
	int num_trees;
	int num_rows;
	/**
	 * @param num_trees : The number of trees to be generated.
	 * @param num_rows : The number of rows to be considered.
	 */
	AdaBoost(int num_trees, int num_rows){
		this.num_trees= num_trees;
		this.num_rows = num_rows;
		
	}
	 /**
	 * @param matrix : The Dataset in the form of a numeric matrix which is returned from the formMatrix() method
	 * @param testMatrix : @param matrix : The Test Data in the form of a numeric matrix which is returned from the formMatrix() method
	 */
	public void adaBoost(int matrix[][], int testMatrix[][]){
		 double[] weights = new double[matrix.length];
		 int tempMatrix[][] = new int[num_rows][15];
		 int posClass[] = new int[30162];
		 double error = 0.0;
		 double sum_W = 0.0;
		 double alpha = 0.0;
		 
		 for(int i=0;i<matrix.length;i++)
		 {
			 weights[i]=1.0/(double)matrix.length;
		 }
		 Tree root = new Tree(0, 0);
		 ArrayList<AttributeEntropy> attEnt = new ArrayList<AttributeEntropy>();
		 for(int i = 0 ; i<num_trees;i++)
		 {
             for(int k = 0; k<num_rows; k++)
             {
        		 tempMatrix[k] = matrix[getDistributedRandomNumber(weights)];
             }
             attEnt = new ArrayList<AttributeEntropy>();
			 for(int k = 0;k<14;k++){
				 attEnt.add(new AttributeEntropy(k));
			 }
			 int attrToConsider[] = {0,1,2,3,4,5,6,7,8,9,10,11,12,13};

			 int firstAttribute = ID3.findA(tempMatrix,attEnt,attrToConsider);
			 root = new Tree(firstAttribute,-1);
             root.children = ID3.runID3(tempMatrix,firstAttribute,attEnt,false);
             

             error =0.0;
             for(int l=0;l<matrix.length;l++)
             {
            	 posClass[l] = root.traversal(matrix[l]);
            	if( posClass[l]==0)
            		error += weights[l];
             }
			 
			 alpha = 0.5*Math.log((1-error)/error);
			 if(alpha<=0)
			 {
				 i--;
				 continue;
			 }
			 sum_W = 0.0;
			 for(int l =0;l<posClass.length;l++)
			 {
				 if(posClass[l]==1)
	                {
	                    weights[l]=weights[l]*Math.exp(-alpha);
	                }
	                else
	                {
	                    weights[l]=weights[l]*Math.exp(alpha);
	                }
	                sum_W+=weights[l];
			 }
			 for(int j=0;j<weights.length;j++)
			 {
                weights[j]=weights[j]/sum_W;
			 }
			 
			 
			 for(int j = 0;j<testMatrix.length;j++){
				 int temporary;
				 if(root.traversal(testMatrix[j])==1)
					temporary = (testMatrix[j][14]==0)?-1:1;
				 else
					temporary = (1-testMatrix[j][14]==0)?-1:1;
				values[j] += alpha * temporary;
			 }
			
		 }
	 }
	 
	 /**
	 * @param testMatrix : The TestMatrix formed after running formMatrix() on testData.
	 * Calculates the accuracy after the implementation of the boosting technique
	 */
	public void calcAccuracy(int testMatrix[][]){
		 double accuracy = 0.0;
	  		int result[] = {0,0};
	  		for(int i=0;i<values.length;i++){
	  			if(values[i]>0)
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
	
	 public int getDistributedRandomNumber(double distribution[]) {
	        double rand = Math.random();
	        double[] cumul = new double[distribution.length];
	        cumul[0] = distribution[0];
	        for(int i=1;i<cumul.length;i++){
	        	cumul[i] = cumul[i-1] + distribution[i];
	        }
	        for(int j=0;j<cumul.length;j++)
	        {
	            if(rand<=cumul[j])
	            {
	                return j;
	            }
	        }
	        return 0;
	    }
}


/**
 * A class which is used for calculating the entropy of a given attribute in the passed dataset.
 */
class AttributeEntropy {
	/** The attribute for which the object stores information of */
	public int attribute;
	/** A map which has all the possible values an attribute can take and also stores the count of postive and negative examples*/
	public Map<Integer,int[]> diversity;
	/** To indicate whether the attribute is supposed to be considered while choosing the attribute with least entropy */
	public boolean flag;
	/** The value of entropy with the respect to this attribute */
	double entropy;
	public AttributeEntropy(int attribute) {
		flag = true;
		this.attribute = attribute;
		diversity = new HashMap<Integer,int[]>();
		entropy = 0.0;
	}
	
	/**
	 * @param matrix : Dataset
	 * Updates the values of the maps which contain the attribute value as a key and the corresponding array of positive and negative count as value
	 */

	public void updateFields(int matrix[][]){
		diversity = new HashMap<Integer,int[]>();
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
	
	/**
	 * Updates the class variable entropy by calculating the entropy of the attribute
	 */
	public void calcEntropy() {
		double indiEntropy[] =  new double[diversity.size()];
		int sum_value=0;
		int i = 0;
		entropy = 0.0;
		for (Map.Entry<Integer, int[]> entry : diversity.entrySet()) {
		    int[] value = entry.getValue();
		    double temp1 = (double)value[0]/(value[0]+value[1]);
		    double temp2 = (double)value[1]/(value[0]+value[1]);
		    indiEntropy[i] = (-1)*temp1*(Math.log(temp1)/Math.log(2))+ (-1)*temp2*(Math.log(temp2)/Math.log(2));
		    if(Double.isNaN(indiEntropy[i]))
		    	indiEntropy[i] = 0.0;
		    entropy += (value[0]+value[1])*indiEntropy[i];
		    sum_value += value[0]+value[1];
		    i++;
		}
		
		entropy = entropy/sum_value;
	}
	
	/**
	 *  An utility function to print the Map of the object.
	 */
	public void printMap(){
		for(Map.Entry<Integer, int[]> entry : diversity.entrySet()){
			int []value = entry.getValue();
			System.out.println(entry.getKey()+" "+value[0]+" "+value[1]);
		}
	}
}

/**
 * A class which stores the original values of the attributes.
 * Used as a reference class to get back the original string of the attribute based on the value as in the matrix  
 */
class DataRef {
	/** An array to find the attribute given an integer */
	public String majorRef[] = new String[15];
	/** An array to find the value of an attribute given the integers corresponding to majorRef and value  */
	public String attrRef[][] = new String[15][];
	DataRef(){
		majorRef[0] = "age";
		majorRef[1] = "workClass";
		majorRef[2] = "fnlwgt";
		majorRef[3] = "education";
		majorRef[4] = "educationNum";
		majorRef[5] = "maritalStatus";
		majorRef[6] = "occupation";
		majorRef[7] = "relationship";
		majorRef[8] = "race";
		majorRef[9] = "sex";
		majorRef[10] = "capitalGain";
		majorRef[11] = "capitalLoss";
		majorRef[12] = "hoursPerWeek";
		majorRef[13] = "nativeCountry";
		majorRef[14] = "result";
		
		attrRef[0] = new String[2];
		attrRef[0][0] = "<=";
		attrRef[0][1] = ">";
		
		attrRef[1] = new String[8];
		attrRef[1][0] = "Private";
		attrRef[1][1] = "Self-emp-not-inc";
		attrRef[1][2] = "Self-emp-inc";
		attrRef[1][3] = "Federal-gov";
		attrRef[1][4] = "Local-gov";
		attrRef[1][5] = "State-gov";
		attrRef[1][6] = "Without-pay";
		attrRef[1][7] = "Never-worked";
		
		attrRef[2] = new String[2];
		attrRef[2][0] = "<=";
		attrRef[2][1] = ">";
		
		attrRef[3] = new String[16];
		attrRef[3][0] = "Bachelors";
		attrRef[3][1] = "Some-college";
		attrRef[3][2] = "11th";
		attrRef[3][3] = "HS-grad";
		attrRef[3][4] = "Prof-school";
		attrRef[3][5] = "Assoc-acdm";
		attrRef[3][6] = "Assoc-voc";
		attrRef[3][7] = "9th";
		attrRef[3][8] = "7th-8th";
		attrRef[3][9] = "12th";
		attrRef[3][10] = "Masters";
		attrRef[3][11] = "1st-4th";
		attrRef[3][12] = "10th";
		attrRef[3][13] = "Doctorate";
		attrRef[3][14] = "5th-6th";
		attrRef[3][15] = "Preschool";
		
		attrRef[4] = new String[2];
		attrRef[4][0] = "<=";
		attrRef[4][1] = ">";
		
		attrRef[5] = new String[7];
		attrRef[5][0] = "Married-civ-spouse";
		attrRef[5][1] = "Divorced";
		attrRef[5][2] = "Never-married";
		attrRef[5][3] = "Separated";
		attrRef[5][4] = "Widowed";
		attrRef[5][5] = "Married-spouse-absent";
		attrRef[5][6] = "Married-AF-spouse";
				
		attrRef[6] = new String[14];
		attrRef[6][0] = "Tech-support";
		attrRef[6][1] = "Craft-repair";
		attrRef[6][2] = "Other-service";
		attrRef[6][3] = "Sales";
		attrRef[6][4] = "Exec-managerial";
		attrRef[6][5] = "Prof-specialty";
		attrRef[6][6] = "Handlers-cleaners";
		attrRef[6][7] = "Machine-op-inspct";
		attrRef[6][8] = "Adm-clerical";
		attrRef[6][9] = "Farming-fishing";
		attrRef[6][10] = "Transport-moving";
		attrRef[6][11] = "Priv-house-serv";
		attrRef[6][12] = "Protective-serv";
		attrRef[6][13] = "Armed-Forces";
		
		attrRef[7] = new String[6];
		attrRef[7][0] = "Wife";
		attrRef[7][1] = "Own-child";
		attrRef[7][2] = "Husband";
		attrRef[7][3] = "Not-in-family";
		attrRef[7][4] = "Other-relative";
		attrRef[7][5] = "Unmarried";
		
		attrRef[8] = new String[5];
		attrRef[8][0] = "White";
		attrRef[8][1] = "Asian-Pac-Islander";
		attrRef[8][2] = "Amer-Indian-Eskimo";
		attrRef[8][3] = "Other";
		attrRef[8][4] = "Black";
		
		attrRef[9] = new String[2];
		attrRef[9][0] = "Male";
		attrRef[9][1] = "Female";
		
		attrRef[10] = new String[2];
		attrRef[10][0] = "<=";
		attrRef[10][1] = ">";
		
		attrRef[11] = new String[2];
		attrRef[11][0] = "<=";
		attrRef[11][1] = ">";
		
		attrRef[12] = new String[2];
		attrRef[12][0] = "<=";
		attrRef[12][1] = ">";
		
		attrRef[13] = new String[41];
		attrRef[13][0]  = "United-States" ;
		attrRef[13][1]  = "Cambodia" ;
		attrRef[13][2]  = "England" ;
		attrRef[13][3]  = "Puerto-Rico" ;
		attrRef[13][4]  = "Canada" ;
		attrRef[13][5]  = "Germany" ;
		attrRef[13][6]  = "Outlying-US(Guam-USVI-etc)" ;
		attrRef[13][7]  = "India" ;
		attrRef[13][8]  = "Japan" ;
		attrRef[13][9]  = "Greece" ;
		attrRef[13][10]  = "South" ;
		attrRef[13][11]  = "China" ;
		attrRef[13][12]  = "Cuba" ;
		attrRef[13][13]  = "Iran" ;
		attrRef[13][14]  = "Honduras" ;
		attrRef[13][15]  = "Philippines" ;
		attrRef[13][16]  = "Italy" ;
		attrRef[13][17]  = "Poland" ;
		attrRef[13][18]  = "Jamaica" ;
		attrRef[13][19]  = "Vietnam" ;
		attrRef[13][20]  = "Mexico" ;
		attrRef[13][21]  = "Portugal" ;
		attrRef[13][22]  = "Ireland" ;
		attrRef[13][23]  = "France" ;
		attrRef[13][24]  = "Dominican-Republic" ;
		attrRef[13][25]  = "Laos" ;
		attrRef[13][26]  = "Ecuador" ;
		attrRef[13][27]  = "Taiwan" ;
		attrRef[13][28]  = "Haiti" ;
		attrRef[13][29]  = "Columbia" ;
		attrRef[13][30]  = "Hungary" ;
		attrRef[13][31]  = "Guatemala" ;
		attrRef[13][32]  = "Nicaragua" ;
		attrRef[13][33]  = "Scotland" ;
		attrRef[13][34]  = "Thailand" ;
		attrRef[13][35]  = "Yugoslavia" ;
		attrRef[13][36]  = "El-Salvador" ;
		attrRef[13][37]  = "Trinadad&Tobago" ;
		attrRef[13][38]  = "Peru" ;
		attrRef[13][39]  = "Hong" ;
		attrRef[13][40]  = "Holand-Netherlands" ;
		
		attrRef[14] = new String[2];
		attrRef[14][0] = "<=50K";
		attrRef[14][1] = ">50K";
	}
}


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
	/**
	 * @param age : The age attribute in the dataset
	 * @param workClass : The work Class attribute in the dataset
	 * @param fnlwgt : The Fnlwgt attribute in the dataset
	 * @param education : The Education attribute in the dataset
	 * @param educationNum : The Education Number attribute in the dataset
	 * @param maritalStatus : The Marital Status attribute in the dataset
	 * @param occupation : The Occupation attribute in the dataset
	 * @param relationship : The Relationship attribute in the dataset
	 * @param race : The Race attribute in the dataset
	 * @param sex : The Sex attribute in the dataset
	 * @param capitalGain : The Capital Gain attribute in the dataset
	 * @param capitalLoss : The Capital Loss attribute in the dataset
	 * @param hoursPerWeek : The Hours Per Week attribute in the dataset
	 * @param nativeCountry : The Native - Country attribute in the dataset
	 * @param income : The income attribute in the dataset
	 */
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
	   /** Prints one row of the dataSet in the string format */
        return ("Age: "+this.age+" Work Class: "+ this.workClass +" fnlwgt: "+ this.fnlwgt +" Education : " + this.education
        		+" Education Num: "+this.educationNum+" Marital Status "+this.maritalStatus+" Occuptaion: "+this.occupation
        		+ " RelationShip Status: "+this.relationship+" Race: "+this.race+" Sex: "+this.sex
        		+ " Capital Gain: "+this.capitalGain+" Capital Loss: "+this.capitalLoss+" Hours Per Week: "+this.hoursPerWeek
        		+ " nativeCountry :"+this.nativeCountry+" Result: "+this.result );
   }
	
}


/**
 *  A class which returns random numbers based on a given probability distribution
 *
 */
class DistributedRandomNumberGenerator {

    private HashMap<Integer, Double> distribution;
    private double distSum;

    /**
     * Initializes the class with a private variable distribution
     */
    public DistributedRandomNumberGenerator() {
        distribution = new HashMap<>();
    }

    /**
     * @param value : The number to be inserted
     * @param distribution : The probability of that number being selected
     */
    public void addNumber(int value, double distribution) {
        if (this.distribution.get(value) != null) {
            distSum -= this.distribution.get(value);
        }
        this.distribution.put(value, distribution);
        distSum += distribution;
    }

    /**
     * @return : A random number based on the probability distribution
     */
    public int getDistributedRandomNumber() {
        double rand = Math.random();
        double ratio = 1.0f / distSum;
        double tempDist = 0;
        for (Integer i : distribution.keySet()) {
            tempDist += distribution.get(i);
            if (rand / ratio <= tempDist) {
                return i;
            }
        }
        return 0;
    }

}



/**
 * A class to implement Random Forest Algorithm on our generated ID3 trees.
 * It stores all the root nodes of the generated trees and then traverses through each of the tree and stores the result of each tree.
 * The we find the majority and give that as the result
 */
class RandomForrest {
	/** resultRandom[i][j] = 1 if the jth tree classified the ith row of the dataset correctly. 0 otherwise */
	int resultRandom[][] = new int[15060][300];
	
	/**
	 * @param matrix :  The Dataset in the form of a numeric matrix which is returned from the formMatrix() method
	 * Updates a matrix which stores whether a tree has properly classified the given example or not.
	 */
	
	
	/**
	 * @param matrix :  The Dataset in the form of a numeric matrix which is returned from the formMatrix() method
	 * @param root : The tree in which we would be traversing
	 * @param iter : number of the tree
	 */
	public void populateMatrix(int matrix[][],Tree root,int iter){	
		for(int j=0;j<matrix.length;j++)
			resultRandom[j][iter] = root.traversal(matrix[j]);
	}
	/**
	 * @param matrix : The Dataset in the form of a numeric matrix which is returned from the formMatrix() method
	 * Prints the accuracy of the Random Forest implementation
	 */
	public void findAccuracy(int matrix[][]){
		int values[] = new int[matrix.length];
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

		double accuracy = 0.0;
  		int result[] = {0,0};
  		for(int i=0;i<values.length;i++){
  			if(values[i] == matrix[i][14])
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

/**
 *  A Node of the tree 
 *	Contains : Attribute (String and Integer counterparts) , value (String and Integer counterparts)
 *  and the list of children
 */
class Tree {
	/** The attribute of the dataSet in consideration */
	String attribute;
	/** The value which the attribute has taken. */
	String value;
	/** Corresponding integer value for the attribute as defined in DataRef */
	int intAttr;
	/** Corresponding integer value for the value taken by the attribute as defined in DataRef */
	int intVal;
	/** All the possible subtrees of the current attribute and value pair.*/
	ArrayList<Tree> children;
	int cnt;
	
	/**
	 * @param attribute : The attribute of the dataSet in consideration
	 * @param value : The value which the attribute has taken.
	 */
	Tree(int attribute, int value){
		DataRef temp = new DataRef();
		this.attribute = temp.majorRef[attribute];
		this.intAttr = attribute;
		this.intVal = value;
		if(value == -1)
			this.value = "";
		else
			this.value = temp.attrRef[attribute][value];
		children = new ArrayList<Tree>();
	}
		
	/**
	 * @param attribute : The attribute of the child (Next Level)
	 * @param value : The value of the attribute
	 * adds another node as a child of the current node. 
	 */
	public void addChild(int attribute, int value){
		Tree node = new Tree(attribute, value);
		this.children.add(node);
	}
	
	/**
	 * @param attribute : The attribute amongst whose children the desired value lies.
	 * @param value : The value that we are searching
	 * @return the required node
	 */
	public Tree searchChild(int attribute, int value){
		Tree node;
		if(this.children.isEmpty()){
			return null;
		}
		else{
			int i = 0;
			for(i=0;i<this.children.size();i++){
					if(attribute == this.children.get(i).intAttr && value == this.children.get(i).intVal){
					node  = this.children.get(i);
					return node;
					}
			}
			return null;
		}
	}
	
	/**
	 * @param data The instance whose result the tree is supposed to predict
	 * @return 0 if the tree miss-classifies data and 1 if the classification is correct
	 * Traverses the tree from root to the leaf based on values of attributes in data and decides if tree has misclassified the data or not
	 */
	public int traversal(int[] data){
		int value = 0;
		int currAttr = this.intAttr;
		Tree tempNode = this.searchChild(currAttr,data[currAttr]);
		try{
		while(true){
			currAttr = tempNode.children.get(0).intAttr;
			if(currAttr==14){
					
				value = (data[currAttr]==tempNode.children.get(0).intVal)?1:0;
				break;
			}else{
				tempNode = tempNode.searchChild(currAttr,data[currAttr]);
			}
		}
		}catch(Exception e){
			}
		return value;
	}

	/**
	 * An Utility function to print the tree in a rather unconventional way for better understanding
	 */
	
	public void printTree(){
		Queue<Tree> q = new LinkedList<Tree>();
		q.add(this);
		Tree temp;
		System.out.println(this.attribute+"-"+this.value+"\n");
		while(!q.isEmpty()){
			temp = q.poll();
			for(int i=0;i<temp.children.size();i++){
				q.add(temp.children.get(i));
				System.out.println("Parent:"+temp.attribute+"-"+temp.value+" "+" Child:"+temp.children.get(i).attribute+"-"+temp.children.get(i).value+" ");
			}
		}
	}
}
