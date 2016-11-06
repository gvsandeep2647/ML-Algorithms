package machinelearning;
import java.util.*;

/**
 *  A Node of the tree 
 *	Contains : Attribute (String and Integer counterparts) , value (String and Integer counterparts)
 *  and the list of children
 */
public class Tree {
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
