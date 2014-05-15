import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.AbstractQueue;

/**
 * This class provides a framework for accessing a decision tree.
 * Put your code in constructor, printInfoGain(), buildTree and buildPrunedTree()
 * You can add your own help functions or variables in this class 
 */
public class DecisionTree {
	/**
	 * training data set, pruning data set and testing data set
	 */
	private DataSet train = null;		// Training Data Set
	private DataSet tune = null;		// Tuning Data Set
	private DataSet test = null;		// Testing Data Set
	private final double LOGTWO = Math.log(2);
	private double[][] attr_valSample;
	private double[] info_gain;
	private HashMap<String,String[]> attributeInfo;
	DecTreeNode root;
	private boolean[] filters;
	private boolean calculateInfoGain;
	/**
	 * Constructor
	 * 
	 * @param train  
	 * @param tune
	 * @param test
	 */
	DecisionTree(DataSet train, DataSet tune, DataSet test) {
		this.train = train;
		this.tune = tune;
		this.test = test;
		attr_valSample = new double[train.attr_val.length][];
		for(int i = 0;i < attr_valSample.length;i++){
			attr_valSample[i] = new double[train.attr_val[i].length];
		}
		info_gain = new double[train.attr_name.length];
		filters = new boolean[train.attr_name.length];
		attributeInfo = new HashMap<String, String[]>();
		
		for(int i = 0; i < train.attr_name.length;i++){
			attributeInfo.put(train.attr_name[i],train.attr_val[i]);
		}
		calculateInfoGain = false;
		
		// TODO: you can add code here, if it is necessary
	}
	
	/**
	 * print information gain of each possible question at root node.
	 * 
	 */
	public void printInfoGain()
	{
		// TODO: add code here
		buildTree();
		for(int i = 0;i < info_gain.length;i++){
			System.out.print("Info gain for " + train.attr_name[i] + " = " );
			System.out.printf("%6.3f\n",info_gain[i]);

		}
	}
	
	/**
	 * Build a decision tree given only a training set.
	 * 
	 */
	public void buildTree() {
		System.out.println("building");
		
		root = buildTree(train.instances,train.attr_name,null,"ROOT");
		
		print();
		// TODO: add code here


		
	}
	public DecTreeNode buildTree(List<Instance> examples,String[] attributes,List<Instance> prevExamples, String parent_attrVal ){
		

		//System.out.println("Hello");
		
		//for(int i = 0;i < attributes.length;i++){
			//System.out.println(i+ " " +  attributes[i]);
		//}
		
		if(examples.isEmpty()){
			System.out.println("no more examples");
			DecTreeNode leaf = new DecTreeNode(train.labels[0], "leaf", parent_attrVal, true);
			return leaf;
		}
		else {
			boolean same = true;
			String label = examples.get(0).label;
			for(int i = 1;i < examples.size();i++){
				if(!examples.get(i).label.equals(label)){
					same = false;
				}
			}
			if(same){
				System.out.println("SAME");
				DecTreeNode leaf = new DecTreeNode(label, "leaf", parent_attrVal, true);
				return leaf;
			}
			
		}
		if(attributes.length == 0){
			System.out.println("no more attr");
			DecTreeNode leaf = new DecTreeNode(train.labels[0], "leaf", parent_attrVal, true);
			return leaf;
		}
		else{
			
			double[] labelCounter = new double[this.train.labels.length];
			
			double[][] totalAttr = new double [attributes.length][];
			for(int j = 0;j < attributes.length;j++){
				//System.out.println(attributeInfo.get(attributes[j]).length);	
				totalAttr[j] = new double[attributeInfo.get(attributes[j]).length];
			}
			
			ArrayList<double[][]> attrCounterList = new ArrayList<double[][]>();
			
			
			//Add 2D arrays for each kind of label to count the attributes associated with it.
			for(int i = 0; i < train.labels.length; i++){
				double[][] toAdd = new double[attributes.length][];
				for(int j = 0;j < attributes.length;j++){
					toAdd[j] = new double[attributeInfo.get(attributes[j]).length];
				}
				attrCounterList.add(toAdd);
			}
			
			for(Instance example:examples){
				String label = example.label;
				int labelIndex = 0;
				
				//Count label and also find the index of that label.
				for(int i = 0;i < train.labels.length;i++){
					if(label.equals(train.labels[i])){
						labelCounter[i]++;
						labelIndex = i;
					}
				}
				
				for(int i = 0;i < example.attributes.size();i++){
					String attrVal = example.attributes.get(i);
					String attributeName = attributes[i];
					String[] attrValList = attributeInfo.get(attributeName);
					int attrIndex = 0;
					for(int j = 0; j < attrValList.length;j++){
						if(attrVal.equals(attrValList[j])){
							attrIndex = j;
							break;
						}
					}
					totalAttr[i][attrIndex]+=1;
					attrCounterList.get(labelIndex)[i][attrIndex]+=1;
				}
				
			}
			
			String bestAttr = bestAttribute(labelCounter,totalAttr,attrCounterList,attributes);
			int bestAttrIndex = 0;
			String[] newAttrList = new String[attributes.length-1];
			int index = 0;
			int counter = 0;
			for(String attr:attributes){
				if(!attr.equals(bestAttr)){
					newAttrList[counter] = attr;
					counter++;

				}
				else if(attr.equals(bestAttr)){
					bestAttrIndex = index;
				}
				index++;
			}
			
			String[] bestAttrList = attributeInfo.get(bestAttr);
			DecTreeNode subRoot = new DecTreeNode("none",  bestAttr,  parent_attrVal,  false);
			for(int i = 0;i < bestAttrList.length;i++){
				List<Instance> reducedExamples = new ArrayList<Instance>();
				for(int j = 0;j < examples.size();j++){
					
					Instance currExample = examples.get(j);
					
					if(currExample.attributes.get(bestAttrIndex).equals(bestAttrList[i])){
						Instance newExample = new Instance();
						for(int k = 0;k < currExample.attributes.size();k++){

							if(!currExample.attributes.get(k).equals(bestAttrList[i])){
								
								newExample.label = currExample.label;
								newExample.addAttribute(currExample.attributes.get(k));

							}
						}
						reducedExamples.add(newExample);
					}
					
					
					
				}
				DecTreeNode child = buildTree(reducedExamples,newAttrList,examples,bestAttrList[i]);
				subRoot.addChild(child);
			}
			
			
			
			return subRoot;
			
		}
		
	}

	public String bestAttribute(double[] labelCounter, double[][] totalAttr, ArrayList<double[][]> attrCounterList, String[] attributes){
		
		double totalEx = 0.0;
		for(int i = 0;i < labelCounter.length;i++){
			totalEx+=labelCounter[i];
		}
		double hClass = hClass(labelCounter,totalEx);
		double[] hClassGivenAttr = hClassGivenAttr(totalAttr, attrCounterList, totalEx);
		double maxInfoGain = 0.0;
		String bestAttribute = "";
		for(int i = 0;i < hClassGivenAttr.length;i++){
			double infoGain = hClass - hClassGivenAttr[i];
			if(infoGain > maxInfoGain){
				maxInfoGain = infoGain;
				bestAttribute = attributes[i];
			}
			if(!calculateInfoGain){
				info_gain[i] = infoGain;
			}
		}
		calculateInfoGain = true;



		return bestAttribute;
	}
	public double hClass(double[] labelCounter,double total){
		double hClass = 0.0;

		for(int i = 0;i < labelCounter.length;i++){
			double coeff = (labelCounter[i])/total;
			hClass += -1*coeff*logTwo(coeff);
		}

		
		return hClass;
		
	}
	public double[] hClassGivenAttr(double[][] totalAttr, ArrayList<double[][]> attrCounterList, double total){
		
		double[] hClassAttr = new double[totalAttr.length];
		
		for(int i = 0;i < totalAttr.length;i++){
			double HI = 0;
			for(int j = 0; j < totalAttr[i].length;j++){
				
				double coeffJ = totalAttr[i][j]/total;
				//System.out.println(coeffJ);

				double HJ = 0;
				for(int k = 0;k < attrCounterList.size();k++){
					if(totalAttr[i][j]!=0){
						//System.out.println("HJ!!!!!!!!!");
						double coeffK = attrCounterList.get(k)[i][j]/totalAttr[i][j];
						//System.out.println(coeffK);
						//System.out.println(logTwo(coeffK));
						HJ += -1*coeffK*logTwo(coeffK);
						//System.out.println(HJ);
					}
					
				}
				HI += coeffJ*HJ;
			}
			hClassAttr[i] = HI;
		
		}
		
		return hClassAttr;
	}
	
	
	public double logTwo(double x){
		if(x != 0.0){
			return Math.log(x)/LOGTWO;
		}
		else{
			return 0.0;
		}
	}
	

	/**
	 * Build a decision tree given a training set then prune it using a tuning set.
	 * 
	 */
	public void buildPrunedTree() {

		// TODO: add code here
		
	}

	
  /**
   * Evaluates the learned decision tree on a test set.
   * @return the label predictions for each test instance 
   * 	according to the order in data set list
   */
	public String[] classify() {
		
		// TODO: add code here
		String[] classification = new String[test.instances.size()];
		int counter = 0;
		for(Instance example:test.instances){
			
			DecTreeNode currNode = root;
			while(!currNode.terminal){
				String attribute = currNode.attribute;
				int attrIndex = 0;
				for(int i = 0;i < test.attr_name.length;i++){
					if(attribute.equals(test.attr_name[i])){
						attrIndex = i;
						break;
					}
				}
				String attr_val = example.attributes.get(attrIndex);
				for(int i = 0;i < currNode.children.size();i++){
					if(currNode.children.get(i).parentAttributeValue.equals(attr_val)){
						currNode = currNode.children.get(i);
						break;
					}
				}
			}
			classification[counter] = currNode.label;
			
		}
		
		
		return classification;
	}

	/**
	 * Prints the tree in specified format. It is recommended, but not
	 * necessary, that you use the print method of DecTreeNode.
	 * 
	 * Example:
	 * Root {odor?}
     *     a (e)
     *     m (e)
   	 *	   n {habitat?}
     *         g (e)
     *  	   l (e)
     *	   p (p)
   	 *	   s (e)
	 *         
	 */
	public void print() {
		root.print(1);
		// TODO: add code here

	}
}
