import java.io.*;
import java.math.*;
import java.util.*;


public class BodyVsBrain {
	private static String filename = "./data.csv";
	private static ArrayList<String> readCSV() {
		ArrayList<String> data = new ArrayList<String>();
		try {
			File f = new File(filename);
			Scanner sc = new Scanner(f);
			int index = 0;
			while(sc.hasNext()) {
				String s = sc.next();
				if (index > 2) {
					data.add(s);	
				}
				index++;
			}
			
		} catch (FileNotFoundException ex) {
			System.out.println("File Not Found.");
		}
		return data;
	}
	
	static public void main(String args []) {
		
		boolean argsFlag = false; 
		double beta0 = 0;
		double beta1 = 0;
		int flag = Integer.valueOf(args[0]);
		if (args.length < 3 && args.length != 1 || args.length > 3) {
			
			if (flag != 600) {
				System.out.println("Incorrect number of arguements");
				return;
			} else {
				if (args.length != 2) {
					System.out.println("Incorrect number of arguements");
					return;
				} else {
					beta0 = Double.valueOf(args[1]);
					argsFlag = true;
				}
			}
		} else if (args.length == 3) {
			if (flag == 600) {
				System.out.println("Incorrect number of arguements");
				return; 
			}
			beta0 = Double.valueOf(args[1]);
			beta1 = Double.valueOf(args[2]);
			argsFlag = true;
		} else if (args.length == 1) {
			if (flag == 500 || flag == 100) {
				 //cool
			} else {
				System.out.println("Incorrect number of arguements");
				return;
			}
		}
		
		if (flag == 600 && args.length == 2) {
			beta0 = Double.valueOf(args[1]);
			argsFlag = true;
		}
		
		if (flag == 100 || flag == 500) {
			if (args.length != 1) {
				System.out.println("Incorrect number of arguements");
				return;
			} else {
				argsFlag = true;
			}
		}
		
		
		ArrayList<String> my_data = readCSV();
		double meanWeight = 0;
		double stdWeight = 0;
		double meanBrain = 0;
		double stdBrain = 0;
		double totalAmtWeight = 0;
		double totalAmtBrain = 0;
		for (int i = 0; i < my_data.size(); i++) {
			String [] stringArr = my_data.get(i).split(",");
			double first = Double.valueOf(stringArr[0].trim());
			double second = Double.valueOf(stringArr[1].trim());
			totalAmtWeight += first;
			totalAmtBrain += second;
		}
		meanWeight = totalAmtWeight/my_data.size();
		meanBrain = totalAmtBrain/my_data.size();
		
		double stdTotalW = 0;
		double stdTotalB = 0;
		for (int i = 0; i < my_data.size(); i++) {
			String [] stringArr = my_data.get(i).split(",");
			double first = Double.valueOf(stringArr[0].trim());
			double second = Double.valueOf(stringArr[1].trim());
			first = Math.pow((first - meanWeight), 2);
			second = Math.pow((second - meanBrain), 2);
			stdTotalW += first;
			stdTotalB += second;
		}
		

		double totNum = 0;
		double totDenom = 0;
		//meanWeight
		//meanBrain
		for (int i = 0; i < my_data.size(); i++) {
			String [] stringArr = my_data.get(i).split(",");
			double first = Double.valueOf(stringArr[0].trim());
			double second = Double.valueOf(stringArr[1].trim());
			first = first-meanWeight;
			second = second-meanBrain;
			double num = first*second;
			double denom = Math.pow(first, 2);
			totNum += num;
			totDenom += denom;
		}
		
		double beta1OLS = totNum/totDenom;
		double beta0OLS = meanBrain - (beta1OLS*meanWeight);
		double olsMSE = mseCalculator(my_data, beta0OLS, beta1OLS);
		
		stdWeight  = Math.sqrt(stdTotalW/(my_data.size()-1));
		stdBrain  = Math.sqrt(stdTotalB/(my_data.size()-1)) ;
		if (flag == 100) {
			//average weight of the body and the weight of the brain for a number of mammal species
			//System.out.println("You made it!!");
			
			/**
			 * Print n, the number of data points on the first line, the sample mean
			 * and the sample standard deviation of body weight on the second line, 
			 * and the sample mean and the sample standard deviation of brain
			 * weight on the third line.
			 */
		
			System.out.println(my_data.size());
			
			//keep four digits after the decimal point!
//			double meanWeight;
//			double stdWeight;
//			double meanBrain;
//			double stdBrain;
			
			
			System.out.printf("%.4f", meanWeight);
			System.out.print(" ");
			System.out.printf("%.4f", stdWeight);
			System.out.println();
			
		
			
		
			
			System.out.printf("%.4f", meanBrain);
			System.out.print(" ");
			System.out.printf("%.4f", stdBrain);
			System.out.println();
		}
		
		else if (flag == 200 && argsFlag == true) {
			
			//x  is the input feature, Body Weight and y is the target feature, Brain Weight. 
			double mse = mseCalculator(my_data, beta0, beta1);
			System.out.printf("%.4f", mse);
			System.out.println();
			
		} 
		
		else if (flag == 300 && argsFlag == true) {

			double gd0 = gdBeta0(my_data, beta0, beta1);
			double gd1 = gdBeta1(my_data, beta0, beta1);
		
			System.out.printf("%.4f", gd0);
			System.out.println();
			System.out.printf("%.4f", gd1);
			System.out.println();
		}
		
		else if (flag == 400 && argsFlag == true) {
			
			double beta0_last = 0;
			double beta1_last = 0;
			double n = beta0;
			double T = beta1;
			for (int t = 0; t < T; t++) {
				double beta0T = beta0_last - ((double )n )*(gdBeta0(my_data, beta0_last, beta1_last));
				double beta1T = beta1_last - ((double )n )*(gdBeta1(my_data, beta0_last, beta1_last));
				double totalMSE = mseCalculator(my_data, beta0T, beta1T);
				System.out.print(t+1);
				System.out.print(" ");
				System.out.printf("%.4f", beta0T);
				System.out.print(" ");
				System.out.printf("%.4f", beta1T);
				System.out.print(" ");
				System.out.printf("%.4f", totalMSE);
				System.out.println();
				beta0_last = beta0T;
				beta1_last = beta1T;
			}
			
		}
		
		else if (flag == 500 && argsFlag == true) {
			
			System.out.printf("%.4f", beta0OLS);
			System.out.print(" ");
			System.out.printf("%.4f", beta1OLS);
			System.out.print(" ");
			System.out.printf("%.4f", olsMSE);
			System.out.println();
		}
		
		else if (flag == 600 && argsFlag == true) {
			double x = beta0;
			double y = beta0OLS + beta1OLS*x;
			System.out.printf("%.4f", y);
			System.out.println();
		}
		
		else if (flag == 700 && argsFlag == true) {
			//meanWeight
			//stdWeight
			//xi = (xi - meanWeight) / stdWeight
			double beta0_last = 0;
			double beta1_last = 0;
			double n = beta0;
			double T = beta1;
			for (int t = 0; t < T; t++) {
				double beta0T = beta0_last - ((double )n )*(gdNormalBeta0(my_data, beta0_last, beta1_last, meanWeight, stdWeight));
				double beta1T = beta1_last - ((double )n )*(gdNormalBeta1(my_data, beta0_last, beta1_last, meanWeight, stdWeight));
				double totalMSE = mseCalculator2(my_data, beta0T, beta1T, meanWeight, stdWeight);
				System.out.print(t+1);
				System.out.print(" ");
				System.out.printf("%.4f", beta0T);
				System.out.print(" ");
				System.out.printf("%.4f", beta1T);
				System.out.print(" ");
				System.out.printf("%.4f", totalMSE);
				System.out.println();
				beta0_last = beta0T;
				beta1_last = beta1T;
			}
			
		}
		
		else if (flag == 800 && argsFlag == true) {
			 Random rng = new Random();
			 int seed = 1000;
			 if (seed != -1) rng.setSeed(seed);
	         double n = beta0;
			double T = beta1;
			double beta0_last = 0;
			double beta1_last = 0;
			for (int t = 0; t < T; t++) {
				int r = rng.nextInt(my_data.size());
				double beta0T = beta0_last - ((double )n )*(gdStochasticBeta0(my_data, beta0_last, beta1_last, meanWeight, stdWeight, r));
				double beta1T = beta1_last - ((double )n )*(gdStochasticBeta1(my_data, beta0_last, beta1_last, meanWeight, stdWeight, r));
				double totalMSE = mseCalculator2(my_data, beta0T, beta1T, meanWeight, stdWeight);
				System.out.print(t+1);
				System.out.print(" ");
				System.out.printf("%.4f", beta0T);
				System.out.print(" ");
				System.out.printf("%.4f", beta1T);
				System.out.print(" ");
				System.out.printf("%.4f", totalMSE);
				System.out.println();
				beta0_last = beta0T;
				beta1_last = beta1T;
			}
		}
		
		
		
	}
	static double gdStochasticBeta0 (ArrayList<String> my_data, double beta0, double beta1, double meanWeight, double stdWeight, int r) {
		String [] stringArr = my_data.get(r).split(",");
		double first = Double.valueOf(stringArr[0].trim());
		double second = Double.valueOf(stringArr[1].trim());
		first = (first - meanWeight) / stdWeight;
		double eq = 2.0*(beta0 + (beta1*first) - second);
		return eq;
	}
	
	static double gdStochasticBeta1 (ArrayList<String> my_data, double beta0, double beta1, double meanWeight, double stdWeight, int r) {
		String [] stringArr = my_data.get(r).split(",");
		double first = Double.valueOf(stringArr[0].trim());
		double second = Double.valueOf(stringArr[1].trim());
		first = (first - meanWeight) / stdWeight;
		double eq = 2.0*(beta0 + (beta1*first) - second)*first;
		return eq;
	}
	
	static double gdBeta0 (ArrayList<String> my_data, double beta0, double beta1) {
		double gdTotalB0 = 0;
		double gd0 = 0; 
		for(int i = 0; i < my_data.size(); i++) {
			String [] stringArr = my_data.get(i).split(",");
			double first = Double.valueOf(stringArr[0].trim());
			double second = Double.valueOf(stringArr[1].trim());
			double eq = beta0 + beta1*first - second;
			gdTotalB0 += eq;
		}
		gd0 = gdTotalB0*(2.0/(double)my_data.size());
		return gd0;
	}
	
	static double gdNormalBeta0 (ArrayList<String> my_data, double beta0, double beta1, double meanWeight, double stdWeight) {
		double gdTotalB0 = 0;
		double gd0 = 0; 
		for(int i = 0; i < my_data.size(); i++) {
			String [] stringArr = my_data.get(i).split(",");
			double first = Double.valueOf(stringArr[0].trim());
			double second = Double.valueOf(stringArr[1].trim());
			first = (first - meanWeight) / stdWeight;
			double eq = beta0 + beta1*first - second;
			gdTotalB0 += eq;
		}
		gd0 = gdTotalB0*(2.0/(double)my_data.size());
		return gd0;
	}
	
	static double gdBeta1 (ArrayList<String> my_data, double beta0, double beta1) {
		double gdTotalB0 = 0;
		double gd1 = 0; 
		for(int i = 0; i < my_data.size(); i++) {
			String [] stringArr = my_data.get(i).split(",");
			double first = Double.valueOf(stringArr[0].trim());
			double second = Double.valueOf(stringArr[1].trim());
			double eq = (beta0 + beta1*first - second)*first;
			gdTotalB0 += eq;
		}
		gd1 = gdTotalB0*(2.0/(double)my_data.size());
		return gd1;
	}
	
	static double gdNormalBeta1 (ArrayList<String> my_data, double beta0, double beta1, double meanWeight, double stdWeight) {
		double gdTotalB0 = 0;
		double gd1 = 0; 
		for(int i = 0; i < my_data.size(); i++) {
			String [] stringArr = my_data.get(i).split(",");
			double first = Double.valueOf(stringArr[0].trim());
			double second = Double.valueOf(stringArr[1].trim());
			first = (first - meanWeight) / stdWeight;
			double eq = (beta0 + beta1*first - second)*first;
			gdTotalB0 += eq;
		}
		gd1 = gdTotalB0*(2.0/(double)my_data.size());
		return gd1;
	}
	
	static double mseCalculator (ArrayList<String> my_data, double beta0, double beta1) {
		double mseTotal = 0;
		for(int i = 0; i < my_data.size(); i++) {
			String [] stringArr = my_data.get(i).split(",");
			double first = Double.valueOf(stringArr[0].trim());
			double second = Double.valueOf(stringArr[1].trim());
			double eq = Math.pow((beta0 + beta1*first - second), 2);
			mseTotal += eq;
		}
		
		double mse = mseTotal/my_data.size();
		return mse;
	}
	
	static double mseCalculator2 (ArrayList<String> my_data, double beta0, double beta1, double meanWeight, double stdWeight) {
		double mseTotal = 0;
		for(int i = 0; i < my_data.size(); i++) {
			String [] stringArr = my_data.get(i).split(",");
			double first = Double.valueOf(stringArr[0].trim());
			first = (first - meanWeight) / stdWeight;
			double second = Double.valueOf(stringArr[1].trim());
			double eq = Math.pow((beta0 + beta1*first - second), 2);
			mseTotal += eq;
		}
		
		double mse = mseTotal/my_data.size();
		return mse;
	}
//this is the final version of my code, with the fixed 100
}
