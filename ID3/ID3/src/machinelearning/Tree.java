package machinelearning;
import java.util.*;

/**
 *  A Node of the tree 
 *	Contains : Attribute , value and the list of children
 */
public class Tree {
	String attribute;
	String value;
	int intAttr;
	int intVal;
	ArrayList<Tree> children;
	int cnt;
	
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
	
	public int traversal(int[] data){
		int value = 0;
		int currAttr = this.intAttr;
		Tree tempNode = this.searchChild(currAttr,data[currAttr]);
		try{
		while(true){
			currAttr = tempNode.children.get(0).intAttr;
			if(currAttr==14){
				if(data[currAttr]==0)
					this.cnt++;
				value = (data[currAttr]==tempNode.children.get(0).intVal)?1:0;
				
				break;
			}else{
				tempNode = tempNode.searchChild(currAttr,data[currAttr]);
			}
		}
		}catch(Exception e){
			//System.out.println(e);
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
