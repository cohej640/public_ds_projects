
import java.util.*;
import java.io.*;
import java.text.DecimalFormat;

public class Chatbot{
    private static String filename = "./corpus.txt";
    private static ArrayList<Integer> readCorpus(){
        ArrayList<Integer> corpus = new ArrayList<Integer>();
        try{
            File f = new File(filename);
            Scanner sc = new Scanner(f);
            while(sc.hasNext()){
                if(sc.hasNextInt()){
                    int i = sc.nextInt();
                    corpus.add(i);
                }
                else{
                    sc.next();
                }
            }
        }
        catch(FileNotFoundException ex){
            System.out.println("File Not Found.");
        }
        return corpus;
    }
    static public void main(String[] args){
        ArrayList<Integer> corpus = readCorpus();
		int flag = Integer.valueOf(args[0]);
		int [] vocab = new int[4700];
        for (int i = 0; i < corpus.size(); i++) {
        	int index = corpus.get(i);
        	vocab[index]++;
        }
        
        //now we need to calculate PDF for the voabulary
        double [] probs = new double[4700];
        for (int j = 0; j < vocab.length; j++) {
        	probs[j] = (double) (vocab[j] + 1) / (double) (228548 + 4700);
        }
        
        int count = 0;
        //here is the bigram model
        HashMap<String, Integer> words_after_h = new HashMap<String, Integer>();
        for (int k = 0; k < corpus.size()-1; k++) {
        	int history = corpus.get(k);
        	int word = corpus.get(k+1);
        	boolean foundFlag = true;
        	String histories = history+","+word;
        	try {
        		count = words_after_h.get(histories);
        	} catch (Exception e) {
        		count = 0;
        		foundFlag = false;
        		words_after_h.put(histories, 1);
        	}
        	if (foundFlag == true) {
        		count += 1;
        		words_after_h.replace(histories, count);
        	}
        }
        
        //this is for the trigram model
        count = 0;
        HashMap<String, Integer> words_after_h1h2 = new HashMap<String, Integer>();
        for (int k = 0; k < corpus.size()-2; k++) {
        	int history1 = corpus.get(k);
        	int history2 = corpus.get(k+1);
        	int word = corpus.get(k+2);
        	boolean foundFlag = true;
        	String histories = history1 + "," + history2 + "," + word;
        	try {
        		count = words_after_h1h2.get(histories);
        	} catch (Exception e) {
        		count = 0;
        		foundFlag = false;
        		words_after_h1h2.put(histories, 1);
        	}
        	if (foundFlag == true) {
        		count += 1;
        		words_after_h1h2.replace(histories, count);
        	}
        }
        
        
        
        
        //now we need to create a unigram
        
        if(flag == 100){
			int w = Integer.valueOf(args[1]);
            //Print the count \n probability
            System.out.println(vocab[w]);
            System.out.printf("%.7f", probs[w]);
            System.out.println();
        }
        else if(flag == 200){
            int n1 = Integer.valueOf(args[1]);
            int n2 = Integer.valueOf(args[2]);
            double r = (double)n1/ (double)n2;
            //TODO generate
            double currProb = 0;
            for (int i =0; i < vocab.length; i++) {
            	if (currProb <= r && currProb+probs[i] > r) {
            		System.out.println(i);
            		System.out.printf("%.7f", currProb);
            		System.out.println();
            		System.out.printf("%.7f", currProb+probs[i]);
            		System.out.println();
            	}
            	currProb += probs[i];
            }
        }
        else if(flag == 300){
            int h = Integer.valueOf(args[1]);
            int w = Integer.valueOf(args[2]);
            //ArrayList<Integer> words_after_h = new ArrayList<Integer>(); //not sure how to use this to fix space complexity. 
//            
//            HashMap<String, Integer> words_after_h = new HashMap<String, Integer>();
//            for (int k = 0; k < corpus.size()-1; k++) {
//            	int history = corpus.get(k);
//            	int word = corpus.get(k+1);
//            	int count = 0;
//            	boolean foundFlag = true;
//            	String histories = history+","+word;
//            	try {
//            		count = words_after_h.get(histories);
//            	} catch (Exception e) {
//            		count = 0;
//            		foundFlag = false;
//            		words_after_h.put(histories, 1);
//            	}
//            	if (foundFlag == true) {
//            		count += 1;
//            		words_after_h.replace(histories, count);
//            	}
//            }
//            
            int bigramCount = 0; 
            for (int i = 0; i < vocab.length; i++) {
            	boolean foundFlag2 = true;
            	int add = 0;
            	try {
            		add = words_after_h.get(h+","+i);
            	} catch (Exception e) {
            		bigramCount += 0;
            		foundFlag2 = false;
            	}
            	if (foundFlag2 == true) {
            		bigramCount += add;
            	}
            	
            }
            int myCount = 0;
            try {
            	myCount = words_after_h.get(h+","+w);
            
            } catch (Exception e) {
            	myCount = 0;
            }
        	System.out.println(myCount);
            System.out.println(bigramCount);
            double pwh = (double) (myCount+1) / (double) (bigramCount + 4700);
            System.out.printf("%.7f", pwh);
            System.out.println();
            

        }
        else if(flag == 400){
            int n1 = Integer.valueOf(args[1]);
            int n2 = Integer.valueOf(args[2]);
            int h = Integer.valueOf(args[3]);
            double r = (double)n1/ (double)n2;
            
//            HashMap<String, Integer> words_after_h = new HashMap<String, Integer>();
//            for (int k = 0; k < corpus.size()-1; k++) {
//            	int history = corpus.get(k);
//            	int word = corpus.get(k+1);
//            	int count = 0;
//            	boolean foundFlag = true;
//            	String histories = history + "," + word;
//            	try {
//            		count = words_after_h.get(histories);
//            	} catch (Exception e) {
//            		count = 0;
//            		foundFlag = false;
//            		words_after_h.put(histories, 1);
//            	}
//            	if (foundFlag == true) {
//            		count += 1;
//            		words_after_h.replace(histories, count);
//            	}
//            }
//            
            double currProb = 0;
           
            double bigramCount = 0;
            for (int i = 0; i < vocab.length; i++) {
            	boolean foundFlag2 = true;
            	int add = 0;
            	try {
            		add = words_after_h.get(h+","+i);
            	} catch (Exception e) {
            		bigramCount += 0;
            		foundFlag2 = false;
            	}
            	if (foundFlag2 == true) {
            		bigramCount += add;
            	}
            }
            for (int w = 0; w < vocab.length; w++) {
            	int wordCount;
            	try {
            		wordCount = words_after_h.get(h+","+w);
            	} catch (Exception e) {
            		wordCount = 0;
            	}
            	double pwh = (double) (wordCount+1) / (double) (bigramCount + 4700);
            	double newProb = currProb + pwh;
            	
            	if (currProb <= r && newProb > r) {
            		System.out.println(w);
            		System.out.printf("%.7f", currProb);        
            		System.out.println();
             		System.out.printf("%.7f", newProb);
             		System.out.println();
            	}
            	currProb += pwh;
            }
        }
        else if(flag == 500){
            int h1 = Integer.valueOf(args[1]);
            int h2 = Integer.valueOf(args[2]);
            int w = Integer.valueOf(args[3]);
//            int count = 0;
//            HashMap<String, Integer> words_after_h1h2 = new HashMap<String, Integer>();
//            for (int k = 0; k < corpus.size()-2; k++) {
//            	int history1 = corpus.get(k);
//            	int history2 = corpus.get(k+1);
//            	int word = corpus.get(k+2);
//            	boolean foundFlag = true;
//            	String histories = history1 + "," + history2 + "," + word;
//            	try {
//            		count = words_after_h1h2.get(histories);
//            	} catch (Exception e) {
//            		count = 0;
//            		foundFlag = false;
//            		words_after_h1h2.put(histories, 1);
//            	}
//            	if (foundFlag == true) {
//            		count += 1;
//            		words_after_h1h2.replace(histories, count);
//            	}
//            }
            int trigramCount = 0;
            for (int v = 0; v < vocab.length; v++) {
            	boolean foundFlag2 = true;
            	int add = 0;
            	try {
            		add = words_after_h1h2.get(h1+","+h2+","+v);
            	} catch (Exception e) {
            		trigramCount += 0;
            		foundFlag2 = false;
            	}
            	if (foundFlag2 == true) {
            		trigramCount += add;
            	}
            	
            }
            int myCount = 0;
            try {
            	myCount = words_after_h1h2.get(h1+","+h2+","+w);
            } catch (Exception e) {
            	myCount = 0;
            }
            System.out.println(myCount);
            System.out.println(trigramCount);
            double pwh1h2 = (double) (myCount+1) / (double) (trigramCount + 4700);
            System.out.printf("%.7f", pwh1h2);
            System.out.println();	
        }
        else if(flag == 600){
            int n1 = Integer.valueOf(args[1]);
            int n2 = Integer.valueOf(args[2]);
            double r = (double) n1 / (double) n2;
            
            int h1 = Integer.valueOf(args[3]);
            int h2 = Integer.valueOf(args[4]);
//            int count = 0;
//            HashMap<String, Integer> words_after_h1h2 = new HashMap<String, Integer>();
//            for (int k = 0; k < corpus.size()-2; k++) {
//            	int history1 = corpus.get(k);
//            	int history2 = corpus.get(k+1);
//            	int word = corpus.get(k+2);
//            	boolean foundFlag = true;
//            	String histories = history1 + "," + history2 + "," + word;
//            	try {
//            		count = words_after_h1h2.get(histories);
//            	} catch (Exception e) {
//            		count = 0;
//            		foundFlag = false;
//            		words_after_h1h2.put(histories, 1);
//            	}
//            	if (foundFlag == true) {
//            		count += 1;
//            		words_after_h1h2.replace(histories, count);
//            	}
//            }
            int trigramCount = 0;
            for (int v = 0; v < vocab.length; v++) {
            	boolean foundFlag2 = true;
            	int add = 0;
            	try {
            		add = words_after_h1h2.get(h1+","+h2+","+v);
            	} catch (Exception e) {
            		trigramCount += 0;
            		foundFlag2 = false;
            	}
            	if (foundFlag2 == true) {
            		trigramCount += add;
            	}
            }
            double currProb = 0;
            for (int w = 0; w < vocab.length; w++) {
            	int wordCount;
            	try {
            		wordCount = words_after_h1h2.get(h1 + "," + h2 + "," + w);
            	} catch (Exception e) {
            		wordCount = 0;
            	}
            	double pwh = (double) (wordCount+1) / (double) (trigramCount + 4700);
            	double newProb = currProb + pwh;
            	
            	if (currProb <= r && newProb > r) {
            		System.out.println(w);
            		System.out.printf("%.7f", currProb);        
            		System.out.println();
             		System.out.printf("%.7f", newProb);
             		System.out.println();
            	}
            	currProb += pwh;
            }
            
            
        }
        else if(flag == 700){
            int seed = Integer.valueOf(args[1]);
            int t = Integer.valueOf(args[2]);
            int h1=0,h2=0;

            Random rng = new Random();
            if (seed != -1) rng.setSeed(seed);

            if(t == 0){
                // TODO Generate first word using r
                double r = rng.nextDouble();
                double currProb = 0;
                for (int i = 0; i < vocab.length; i++) {
                	if (currProb <= r && currProb+probs[i] > r) {
                		h1 = i;
                		break;
                	}
                	currProb += probs[i];
                }
                System.out.println(h1);
                if(h1 == 9 || h1 == 10 || h1 == 12){
                    return;
                }
                
                //generate the next word using r
                r = rng.nextDouble();
                double bigramCount = 0;
                for (int i = 0; i < vocab.length; i++) {
                	boolean foundFlag2 = true;
                	int add = 0;
                	try {
                		add = words_after_h.get(h1+","+i);
                	} catch (Exception e) {
                		bigramCount += 0;
                		foundFlag2 = false;
                	}
                	if (foundFlag2 == true) {
                		bigramCount += add;
                	}
                }
                currProb = 0;
                for (int w = 0; w < vocab.length; w++) {
                	int wordCount;
                	try {
                		wordCount = words_after_h.get(h1+","+w);
                	} catch (Exception e) {
                		wordCount = 0;
                	}
                	double pwh = (double) (wordCount+1) / (double) (bigramCount + 4700);
                	double newProb = currProb + pwh;
                	
                	if (currProb <= r && newProb > r) {
                		h2 = w;
                	}
                	currProb += pwh;
                }
                
                System.out.println(h2);
            }
            else if(t == 1){
                h1 = Integer.valueOf(args[3]);
                
                
                // TODO Generate second word using r
                double r = rng.nextDouble();
                double bigramCount = 0;
                for (int i = 0; i < vocab.length; i++) {
                	boolean foundFlag2 = true;
                	int add = 0;
                	try {
                		add = words_after_h.get(h1+","+i);
                	} catch (Exception e) {
                		bigramCount += 0;
                		foundFlag2 = false;
                	}
                	if (foundFlag2 == true) {
                		bigramCount += add;
                	}
                }
                double currProb = 0;
                for (int w = 0; w < vocab.length; w++) {
                	int wordCount;
                	try {
                		wordCount = words_after_h.get(h1+","+w);
                	} catch (Exception e) {
                		wordCount = 0;
                	}
                	double pwh = (double) (wordCount+1) / (double) (bigramCount + 4700);
                	double newProb = currProb + pwh;
                	
                	if (currProb <= r && newProb > r) {
                		h2 = w;
                	}
                	currProb += pwh;
                }
                
                System.out.println(h2);
            }
            else if(t == 2){
                h1 = Integer.valueOf(args[3]);
                h2 = Integer.valueOf(args[4]);
                
            }

            while(h2 != 9 && h2 != 10 && h2 != 12){
                
            	double r = rng.nextDouble();
                int w  = 0;
            	int trigramCount = 0;
                for (int v = 0; v < vocab.length; v++) {
                	boolean foundFlag2 = true;
                	int add = 0;
                	try {
                		add = words_after_h1h2.get(h1+","+h2+","+v);
                	} catch (Exception e) {
                		trigramCount += 0;
                		foundFlag2 = false;
                	}
                	if (foundFlag2 == true) {
                		trigramCount += add;
                	}
                }
                
                double currProb = 0;
                for (int w1 = 0; w1 < vocab.length; w1++) {
                	int wordCount;
                	try {
                		wordCount = words_after_h1h2.get(h1 + "," + h2 + "," + w1);
                	} catch (Exception e) {
                		wordCount = 0;
                	}
                	double pwh = (double) (wordCount+1) / (double) (trigramCount + 4700);
                	double newProb = currProb + pwh;
                	
                	if (currProb <= r && newProb > r) {
                		w = w1;
                	}
                	currProb += pwh;
                }

                System.out.println(w);
                h1 = h2;
                h2 = w;
            }
        }

        return;
    }
} // this is the final version of Chatbot.java

