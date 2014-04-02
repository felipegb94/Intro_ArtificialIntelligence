import java.util.List;

/**
 * You should implement your Perceptron in this class. 
 * Any methods, variables, or secondary classes could be added, but will 
 * only interact with the methods or variables in this framework.
 * 
 * You must add code for at least the 3 methods specified below. Because we
 * don't provide the weights of the Perceptron, you should create your own 
 * data structure to store the weights.
 * 
 */
public class Perceptron {
	
	/**
	 * The initial value for ALL weights in the Perceptron.
	 * We fix it to 0, and you CANNOT change it.
	 */
	public final double INIT_WEIGHT = 0.0;
	
	/**
	 * Learning rate value. You should use it in your implementation.
	 * You can set the value via command line parameter.
	 */
	public final double ALPHA;
	
	/**
	 * Training iterations. You should use it in your implementation.
	 * You can set the value via command line parameter.
	 */
	public final int EPOCH;
	public double correctCounter;
	public double accuracy;
	public double[][] weights;
	public double[] output;
	public int featureNumber;
	public int labelNumber;

	// TODO: create weights variables, input units, and output units.
	
	/**
	 * Constructor. You should initialize the Perceptron weights in this
	 * method. Also, if necessary, you could do some operations on
	 * your own variables or objects.
	 * 
	 * @param alpha
	 * 		The value for initializing learning rate.
	 * 
	 * @param epoch
	 * 		The value for initializing training iterations.
	 * 
	 * @param featureNum
	 * 		This is the length of input feature vector. You might
	 * 		use this value to create the input units.
	 * 
	 * @param labelNum
	 * 		This is the size of label set. You might use this
	 * 		value to create the output units.
	 */
	public Perceptron(double alpha, int epoch, int featureNum, int labelNum) {
		this.ALPHA = alpha;
		this.EPOCH = epoch;
		
		/*
		 * labelNum columns, one for each digit.
		 * one extra row (first one) for the bias input and bias weight.
		 */
		
		this.weights = new double[labelNum][featureNum+1];
		
		this.output = new double[labelNum];
		featureNumber = featureNum;
		labelNumber = labelNum;
		
	}
	
	/**
	 * Train your Perceptron in this method.
	 * 
	 * @param trainingData
	 */
	public void train(Dataset trainingData) {
		
		for(int i = 0; i < this.EPOCH;i++){
			
			for(Instance instance : trainingData.instanceList){
				
				
				String trueLabel = instance.getLabel();
				
				int correctIndex = Integer.parseInt(trueLabel);
				double[] correctOutput = new double[labelNumber];
				correctOutput[correctIndex] = 1;
				
				List<Double> features = instance.getFeatureValue();
				
				calculateOutput(features);
				updateWeights(correctOutput,features);
				
				
			
			
			}
		}
	}
	
	/**
	 * Test your Perceptron in this method. Refer to the homework documentation
	 * for implementation details and requirement of this method.
	 * 
	 * @param testData
	 */
	public void classify(Dataset testData) {
		
		for(Instance instance : testData.instanceList){
			
			List<Double> features = instance.getFeatureValue();
			calculateOutput(features);
			double highest = output[0];
			int outputIndex = 0;
			for(int i = 1;i < output.length;i++){
				if(highest < output[i]){
					highest = output[i];
					outputIndex = i;
				}
			}
			String label = Label.DIGITS[outputIndex];
			if(label.equals(instance.label)){
				correctCounter += 1.0;
			}
			System.out.println(label);
		}
		accuracy = correctCounter/testData.instanceList.size();
		System.out.printf("%6.4f\n",accuracy);
		
	}
	
	public void updateWeights(double[] correctOutput,List<Double> features){
		double T = 0; //correct output
		double O = 0; // output

		for(int i = 0; i < weights.length;i++){
			T = correctOutput[i];
			O = output[i];
			double deltaW = ALPHA*(T-O)*O*(1-O);
			weights[i][0] += deltaW;
			for(int j = 1;j < weights[0].length; j++){
				deltaW = ALPHA*(T-O)*O*(1-O)*features.get(j-1);
				weights[i][j] += deltaW;
			}
			
		}
		
	}
	public void calculateOutput(List<Double> features){
		
		for(int i = 0; i < output.length; i++){
			output[i] = calculateG(features,i);
		}
		
	}
	/**
	 * Calculate activation function
	 */
	public double calculateG(List<Double> features,int row){
		
		double in = weights[row][0];
		for(int i = 0; i < features.size();i++){
			in += features.get(i)*weights[row][i+1];
		}
		in = 1.0/(1.0+Math.exp(-1.0*in));
		return in;
		
	}
	

}