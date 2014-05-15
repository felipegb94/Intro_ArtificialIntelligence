import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.StringTokenizer;

public class NBSpamDetect {

	@SuppressWarnings("static-access")
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		double nMessagesHam = 0;
		double nMessagesSpam = 0;
		double nMessagesTotal = 0;
		double pHam = 0;
		double pSpam = 0;

		Hashtable<String, Integer> hamDictionary = new Hashtable<String, Integer>();
		Hashtable<String, Integer> spamDictionary = new Hashtable<String, Integer>();
		Hashtable<String, Double> hamWordsProbability = new Hashtable<String, Double>();
		Hashtable<String, Double> spamWordsProbability = new Hashtable<String, Double>();

		double nWordsHam = 0;
		double nWordsSpam = 0;

		NBSpamDetectionIO spamDetectorTrain = new NBSpamDetectionIO();

		String[] arguments = new String[2];
		arguments[0] = args[0];
		arguments[1] = "0";
		try {
			spamDetectorTrain.main(arguments);
			nMessagesHam = (double) spamDetectorTrain.nMessagesHam;
			nMessagesSpam = (double) spamDetectorTrain.nMessagesSpam ;
			nMessagesTotal = (double) spamDetectorTrain.nMessagesTotal;
			nWordsHam = (double) spamDetectorTrain.nWordsHam ;
			nWordsSpam = (double) spamDetectorTrain.nWordsSpam ;
			hamDictionary = spamDetectorTrain.hamDictionary;
			spamDictionary = spamDetectorTrain.spamDictionary;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		pHam = Math.log(nMessagesHam / nMessagesTotal);
		pSpam = Math.log(nMessagesSpam / nMessagesTotal);

		for (Enumeration<String> e = hamDictionary.keys(); e.hasMoreElements();) {

			String word;
			word = e.nextElement();
			double numerator = Math.log(hamDictionary.get(word) + 1.0);
			double denominator = Math.log(nWordsHam + hamDictionary.size());
			double logProbability = numerator - denominator;
			hamWordsProbability.put(word, logProbability);

		}
		for (Enumeration<String> e = spamDictionary.keys(); e.hasMoreElements();) {

			String word;
			word = e.nextElement();
			double numerator = Math.log(spamDictionary.get(word) + 1.0);
			double denominator = Math.log(nWordsSpam + spamDictionary.size());
			double logProbability = numerator - denominator;
			spamWordsProbability.put(word, logProbability);

		}

		ArrayList<File[]> listings = getListings(args[1]);
		int[] confusionMatrix1 = classification1(pHam, pSpam,
				hamWordsProbability, spamWordsProbability, listings, 0);

		/**
		 * Part 2. All words to lower case
		 */

		hamDictionary = new Hashtable<String, Integer>();
		spamDictionary = new Hashtable<String, Integer>();
		hamWordsProbability = new Hashtable<String, Double>();
		spamWordsProbability = new Hashtable<String, Double>();

		nWordsHam = 0;
		nWordsSpam = 0;

		spamDetectorTrain = new NBSpamDetectionIO();

		arguments = new String[2];
		arguments[0] = args[0];
		arguments[1] = "1";
		try {
			spamDetectorTrain.main(arguments);
			nMessagesHam = (double) spamDetectorTrain.nMessagesHam;
			nMessagesSpam = (double) spamDetectorTrain.nMessagesSpam;
			nMessagesTotal = (double) spamDetectorTrain.nMessagesTotal;
			nWordsHam = (double) spamDetectorTrain.nWordsHam;
			nWordsSpam = (double) spamDetectorTrain.nWordsSpam;
			hamDictionary = spamDetectorTrain.hamDictionary;
			spamDictionary = spamDetectorTrain.spamDictionary;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		pHam = Math.log(nMessagesHam / nMessagesTotal);
		pSpam = Math.log(nMessagesSpam / nMessagesTotal);

		for (Enumeration<String> e = hamDictionary.keys(); e.hasMoreElements();) {

			String word;
			word = e.nextElement();
			double numerator = Math.log(hamDictionary.get(word) + 1.0);
			double denominator = Math.log(nWordsHam+hamDictionary.size());
			double logProbability = numerator - denominator;
			hamWordsProbability.put(word, logProbability);

		}
		for (Enumeration<String> e = spamDictionary.keys(); e.hasMoreElements();) {

			String word;
			word = e.nextElement();
			double numerator = Math.log(spamDictionary.get(word) + 1.0);
			double denominator = Math.log(nWordsSpam+spamDictionary.size());
			double logProbability = numerator - denominator;
			spamWordsProbability.put(word, logProbability);

		}
		listings = getListings(args[1]);
		int[] confusionMatrix2 = classification1(pHam, pSpam, hamWordsProbability,
				spamWordsProbability, listings, 1);
		
		/**
		 * Part 3. All words to lower case
		 */

		hamDictionary = new Hashtable<String, Integer>();
		spamDictionary = new Hashtable<String, Integer>();
		hamWordsProbability = new Hashtable<String, Double>();
		spamWordsProbability = new Hashtable<String, Double>();

		nWordsHam = 0;
		nWordsSpam = 0;

		spamDetectorTrain = new NBSpamDetectionIO();

		arguments = new String[2];
		arguments[0] = args[0];
		arguments[1] = "2";
		try {
			spamDetectorTrain.main(arguments);
			nMessagesHam = (double) spamDetectorTrain.nMessagesHam;
			nMessagesSpam = (double) spamDetectorTrain.nMessagesSpam;
			nMessagesTotal = (double) spamDetectorTrain.nMessagesTotal;
			nWordsHam = (double) spamDetectorTrain.nWordsHam;
			nWordsSpam = (double) spamDetectorTrain.nWordsSpam;
			hamDictionary = spamDetectorTrain.hamDictionary;
			spamDictionary = spamDetectorTrain.spamDictionary;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		pHam = Math.log(nMessagesHam / nMessagesTotal);
		pSpam = Math.log(nMessagesSpam / nMessagesTotal);

		for (Enumeration<String> e = hamDictionary.keys(); e.hasMoreElements();) {
			String word;
			word = e.nextElement();
			double numerator = Math.log(hamDictionary.get(word) + 1.0);
			double denominator = Math.log(nWordsHam+hamDictionary.size());
			double logProbability = numerator - denominator;
			hamWordsProbability.put(word, logProbability);

		}
		for (Enumeration<String> e = spamDictionary.keys(); e.hasMoreElements();) {
			String word;
			word = e.nextElement();
			double numerator = Math.log(spamDictionary.get(word) + 1.0);
			double denominator = Math.log(nWordsSpam+spamDictionary.size());
			double logProbability = numerator - denominator;
			spamWordsProbability.put(word, logProbability);

		}
		listings = getListings(args[1]);
		int[] confusionMatrix3 = classification1(pHam, pSpam, hamWordsProbability,
				spamWordsProbability, listings, 2);

		System.out.println("NBSpamDetect");
	}

	public static ArrayList<String> getWordsList(File email, int flag)
			throws FileNotFoundException {
		

		String[] specificWords = new String[4];
		specificWords[0] = "to:";
		specificWords[1] = "from:";
		specificWords[2] = "cc:";
		specificWords[3] = "subject:";
		
		ArrayList<String> words = new ArrayList<String>();
		FileInputStream i_s = new FileInputStream(email);
		BufferedReader in = new BufferedReader(new InputStreamReader(i_s));
		String line;
		String word;
		try {

			
			while ((line = in.readLine()) != null) { // read a line
				if(flag == 2){
					line = line.toLowerCase();
				}
				StringTokenizer st = new StringTokenizer(line); // parse it into
				int counter = 0;												// words
				boolean containsWord = false;

				while (st.hasMoreTokens()) {
					if(flag == 2){
						word = st.nextToken().replaceAll("[^a-zA-Z:]", "");

					}
					else{
						word = st.nextToken().replaceAll("[^a-zA-Z]", "");
					}
					if(counter == 0){
						for(int j = 0;j < specificWords.length;j++){
							if(word.equals(specificWords[j])){
								//System.out.println(word);
								containsWord = true;
								break;
						}
					}
						counter++;
					}
					
					if(flag == 2){
						if(containsWord){
							words.add(word);
						}
					}
					else{
					if (flag == 1) {
						word = word.toLowerCase();
					}
					words.add(word);
					}
				}
			}
			in.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return words;
	}

	public static ArrayList<File[]> getListings(String location) {
		// Location of the directory (the path) taken from the cmd line (first
		// arg)
		ArrayList<File[]> listings = new ArrayList<File[]>();
		File dir_location = new File(location);

		// Listing of the directory (should contain 2 subdirectories: ham/ and
		// spam/)
		File[] dir_listing = new File[0];

		// Check if the cmd line arg is a directory and list it
		if (dir_location.isDirectory()) {
			dir_listing = dir_location.listFiles();
		} else {
			System.out.println("- Error: cmd line arg not a directory.\n");
			Runtime.getRuntime().exit(0);
		}

		// Listings of the two sub-directories (ham/ and spam/)
		File[] listing_ham = new File[0];
		File[] listing_spam = new File[0];

		// Check that there are 2 sub-directories
		boolean hamFound = false;
		boolean spamFound = false;
		for (int i = 0; i < dir_listing.length; i++) {
			if (dir_listing[i].getName().equals("ham")) {
				listing_ham = dir_listing[i].listFiles();
				hamFound = true;
			} else if (dir_listing[i].getName().equals("spam")) {
				listing_spam = dir_listing[i].listFiles();
				spamFound = true;
			}
		}
		if (!hamFound || !spamFound) {
			System.out
					.println("- Error: specified directory does not contain ham and spam subdirectories.\n");
			Runtime.getRuntime().exit(0);
		}

		// Print out the number of messages in ham and in spam
		System.out.println("\t number of ham messages is: "
				+ listing_ham.length);
		System.out.println("\t number of spam messages is: "
				+ listing_spam.length);
		listings.add(listing_ham);
		listings.add(listing_spam);

		return listings;

	}

	public static int[] classification1(double pHam, double pSpam,
			Hashtable<String, Double> hamWordsProbability,
			Hashtable<String, Double> spamWordsProbability,
			ArrayList<File[]> listings, int flag) {
		int[] confusionMatrix = new int[4];
		File[] ham_listings = listings.get(0);
		File[] spam_listings = listings.get(1);

		int trueNegatives = 0;
		int falseNegatives = 0;
		for (int i = 0; i < ham_listings.length; i++) {
			File email = ham_listings[i];
			ArrayList<String> words = null;
			try {
				words = getWordsList(email, flag);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			double pWordsGivenHam = 0;
			if (words != null) {
				for (int j = 0; j < words.size(); j++) {
					// Calculate the probability of each word given ham
					String currWord = words.get(j);
					if (hamWordsProbability.containsKey(currWord)) {
						pWordsGivenHam += hamWordsProbability.get(currWord);
					}
				}

			} else {
				System.out.println("There is a problem with getWordsList");
			}

			double pHamGivenWords = pHam + pWordsGivenHam;

			double pWordsGivenSpam = 0;
			if (words != null) {
				for (int j = 0; j < words.size(); j++) {
					// Calculate the probability of each word given spam
					String currWord = words.get(j);
					if (spamWordsProbability.containsKey(currWord)) {
						pWordsGivenSpam += spamWordsProbability.get(currWord);
					}
				}
			} else {
				System.out.println("There is a problem with getWordsList");
			}

			double pSpamGivenWords = pSpam + pWordsGivenSpam;

			if (pSpamGivenWords > pHamGivenWords) {
				falseNegatives++;
			} else {
				trueNegatives++;
			}
		}

		int truePositives = 0;
		int falsePositives = 0;
		for (int i = 0; i < spam_listings.length; i++) {
			File email = spam_listings[i];
			ArrayList<String> words = null;
			try {
				words = getWordsList(email, flag);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			double pWordsGivenSpam = 0;
			if (words != null) {
				for (int j = 0; j < words.size(); j++) {
					// Calculate the probability of each word given ham
					String currWord = words.get(j);
					if (spamWordsProbability.containsKey(currWord)) {
						pWordsGivenSpam += spamWordsProbability.get(currWord);
					}
				}

			} else {
				System.out.println("There is a problem with getWordsList");
			}

			double pSpamGivenWords = pSpam + pWordsGivenSpam;

			double pWordsGivenHam = 0;
			if (words != null) {
				for (int j = 0; j < words.size(); j++) {
					// Calculate the probability of each word given spam
					String currWord = words.get(j);
					if (hamWordsProbability.containsKey(currWord)) {
						pWordsGivenHam += hamWordsProbability.get(currWord);
					}
				}
			} else {
				System.out.println("There is a problem with getWordsList");
			}

			double pHamGivenWords = pHam + pWordsGivenHam;

			if (pSpamGivenWords > pHamGivenWords) {
				truePositives++;
			} else {
				falsePositives++;
			}
		}
		confusionMatrix[0] = truePositives;
		confusionMatrix[1] = falsePositives;
		confusionMatrix[2] = trueNegatives;
		confusionMatrix[3] = falseNegatives;

		System.out.println("TruePositives: " + truePositives);
		System.out.println("falsePositives: " + falsePositives);
		System.out.println("TrueNegatives: " + trueNegatives);
		System.out.println("FalseNegatives: " + falseNegatives);

		return confusionMatrix;
	}

}
