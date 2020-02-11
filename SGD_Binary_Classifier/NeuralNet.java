import java.io.*;
import java.math.*;
import java.util.*;

public class NeuralNet {
	private static ArrayList<String> readCSV(String filename) {
		ArrayList<String> data = new ArrayList<String>();
		try {
			File f = new File(filename);
			Scanner sc = new Scanner(f);
			while(sc.hasNext()) {
				String s = sc.next();
				data.add(s);	
			}
			
		} catch (FileNotFoundException ex) {
			System.out.println("File Not Found.");
		}
		return data;
	}
	static public HashMap<String, Double> a_values = new HashMap<String, Double> ();
	static public HashMap<String, Double> z_values = new HashMap<String, Double> ();
	static public HashMap<String, Double> d_values = new HashMap<String, Double> ();
	static public HashMap<String, Double> derivative_values = new HashMap<String, Double> ();
	static public LinkedHashMap<String, Double> user_Args = new LinkedHashMap<String, Double>();
	static public void main(String args []) {
		ArrayList<Double> userArgs = new ArrayList<Double>();
		//LinkedHashMap<String, Double> user_Args = new LinkedHashMap<String, Double>();
		if (args.length < 1) {
			System.out.println("Usage: flag [args]");
		} else if (args.length == 1) {
			
		} else {
			
			// this is for flag == 100
			int flag = Integer.valueOf(args[0]);
			if (flag == 100) {
				try {
					for (int i = 1; i < args.length-4; i++) {
						double d = Double.valueOf(args[i]);
						userArgs.add(d);
						int k = 0;
						if (i < 6 || i > 10) {
							k = 1;
						} else {
							k = 2;
						}
						int j = 0;
						j = (i-1) % 5;
						int l = 0;
						if (i < 11) {
							l = 2;
						} else {
							l = 3;
						}
						String kjl = k + "," + j + "," +  l;
						//String kjl = ((i / 6)+1) + "" + ((i-1) % 5) + ((i/11)+2);
						user_Args.put(kjl, d);
					}
				} catch (Exception e) {
					System.out.println("Usage: args[13]");
					return;
				}
			
			} else if (flag == 200 || flag == 300 || flag == 400) {
				try {
					for (int i = 1; i < args.length-5; i++) {
						double d = Double.valueOf(args[i]);
						userArgs.add(d);
						int k = 0;
						if (i < 6 || i > 10) {
							k = 1;
						} else {
							k = 2;
						}
						int j = 0;
						j = (i-1) % 5;
						int l = 0;
						if (i < 11) {
							l = 2;
						} else {
							l = 3;
						}
						String kjl = k + "," + j + "," +  l;
						//String kjl = ((i / 6)+1) + "" + ((i-1) % 5) + ((i/11)+2);
						user_Args.put(kjl, d);
					}
				} catch (Exception e) {
					System.out.println("Usage: args[13]");
					return;
				}
				
			} else if (flag == 500 || flag == 600) {
				try {
					for (int i = 1; i < args.length-1; i++) {
						double d = Double.valueOf(args[i]);
						int k = 0;
						if (i < 6 || i > 10) {
							k = 1;
						} else {
							k = 2;
						}
						int j = 0;
						j = (i-1) % 5;
						int l = 0;
						if (i < 11) {
							l = 2;
						} else {
							l = 3;
						}
						String kjl = k + "," + j + "," +  l;
						//String kjl = ((i / 6)+1) + "" + ((i-1) % 5) + ((i/11)+2);
						user_Args.put(kjl, d);
					}
				} catch (Exception e) {
					System.out.println("Usage: args[13]");
					return;
				}
				
			}
			
		}
	/*	
		 Set set = user_Args.entrySet();
	      
	      // Get an iterator
	      Iterator i1 = set.iterator();
	      
	      // Display elements
	      while(i1.hasNext()) {
	         Map.Entry me = (Map.Entry)i1.next();
	         System.out.print(me.getKey() + ": ");
	         System.out.println(me.getValue());
	      }
	      System.out.println();
	*/
		
		int flag = Integer.valueOf(args[0]);
		
		if (flag == 100) {
			
			ArrayList<String> my_train = readCSV("./train.csv");
			//x1, x2, x3, x4, y
			
			// WELL FOR THIS ONE WE WILL USE FIXED WEIGHTS
			

			double [] fixedX = {1.0, Double.valueOf(args[14]), Double.valueOf(args[15]),
					Double.valueOf(args[16]), Double.valueOf(args[17])};
			
			// Now we have the first couple of x's
			
			//we need to pass the weight function, and
			//we need a DS to hold all the values of ai(l-1)
			
			//lets a hashmap that is denoted with key k, j, l
			
			//HashMap<String, Double> a_values = new HashMap<String, Double> ();
			
			// Note that the activations for the input layer are the input themselves
			// aj(1) = xj
			// a0(1) = 1
			
			// Try and just get the initial activations
			getzVals (a_values, z_values, user_Args, fixedX);
			System.out.printf("%.5f", a_values.get(1+","+2));
			System.out.print(" ");
			System.out.printf("%.5f", a_values.get(2+","+2));
			System.out.println();
			System.out.printf("%.5f", a_values.get(1+","+3));
			System.out.println();
		}
		
		else if (flag == 200) {
			
			double [] fixedX = {1.0, Double.valueOf(args[14]), Double.valueOf(args[15]),
					Double.valueOf(args[16]), Double.valueOf(args[17])};
			
			double y = Double.valueOf(args[18]);
			getzVals (a_values, z_values, user_Args, fixedX);
			String kl = 1 + "," + 3;
			double a_13 = a_values.get(kl);
			double delta = (a_13-y)*a_13*(1-a_13);
			System.out.printf("%.5f", delta);
			System.out.println();
			
		}
		
		else if (flag == 300) {
			
			// For the hidden layer, the two nodes, we need this
			double [] fixedX = {1.0, Double.valueOf(args[14]), Double.valueOf(args[15]),
					Double.valueOf(args[16]), Double.valueOf(args[17])};
			double y = Double.valueOf(args[18]);
			getzVals (a_values, z_values, user_Args, fixedX);
			String kl = 1 + "," + 3;
			double a_13 = a_values.get(kl);
			double delta = (a_13-y)*a_13*(1-a_13);
			for (int j = 1; j < 3; j++) {
				String kjl = 1+","+j+","+3; 
				String jl = j + "," + 2;
				double eq = delta*user_Args.get(kjl)*a_values.get(jl)*(1-a_values.get(jl));
				//System.out.println(eq);
				d_values.put(jl, eq);
			}
			
			System.out.printf("%.5f", d_values.get(1+","+2));
			System.out.print(" ");
			System.out.printf("%.5f", d_values.get(2+","+2));
			System.out.println();
		}
		
		else if (flag == 400) {
			double [] fixedX = {1.0, Double.valueOf(args[14]), Double.valueOf(args[15]),
					Double.valueOf(args[16]), Double.valueOf(args[17])};
			double y = Double.valueOf(args[18]);
			getzVals (a_values, z_values, user_Args, fixedX);
			getDerivativeVals (a_values, z_values, user_Args, fixedX, y);
			
			for (int i = 0; i < 3; i++) {
				String kl = 1 + "," + i +"," + 3;
				double eq = derivative_values.get(kl);
				System.out.printf("%.5f", eq);
				if (i < 2) {
					System.out.print(" ");
				}
			}
			System.out.println();
			
			for (int k = 0; k < 5; k++) {
				String jkl = 1+","+k+","+2;
				Double eq = derivative_values.get(jkl);
				System.out.printf("%.5f", eq);
				if (k < 4) {
					System.out.print(" ");
				}
			}
			System.out.println();
			
			for (int k = 0; k < 5; k++) {
				String jkl = 2+","+k+","+2;
				Double eq = derivative_values.get(jkl);
				System.out.printf("%.5f", eq);
				if (k < 4) {
					System.out.print(" ");
				}
			}
			System.out.println();
		}
		
		else if (flag == 500) {
			
			double learn_rate = Double.valueOf(args[14]);
			ArrayList<String> my_train = readCSV("./train.csv");
			ArrayList<String> my_eval = readCSV("./eval.csv");
			int count = 1;
			
			a_values = new HashMap<String, Double> ();
			z_values = new HashMap<String, Double> ();
			d_values = new HashMap<String, Double> ();
			derivative_values = new HashMap<String, Double> ();
			for (int i = 0; i < my_train.size(); i++) {
				a_values.clear();
				z_values.clear();
				d_values.clear();
				derivative_values.clear();
				String[] my_array = my_train.get(i).split(",");
				double y = Double.valueOf(my_array[4].trim());
				double [] fixedX = {1.0, Double.valueOf(my_array[0].trim()), Double.valueOf(my_array[1].trim()),
						Double.valueOf(my_array[2].trim()), Double.valueOf(my_array[3].trim())};
				getzVals (a_values, z_values, user_Args, fixedX);
				getDerivativeVals (a_values, z_values, user_Args, fixedX, y);
				
				//now update the weights
				
				
				
				for (int z = 1; z < args.length-1; z++) {
					int k = 0;
					if (z < 6 || z > 10) {
						k = 1;
					} else {
						k = 2;
					}
					int j = 0;
					j = (z-1) % 5;
					int l = 0;
					if (z < 11) {
						l = 2;
					} else {
						l = 3;
					}
					String kjl = k + "," + j + "," +  l;
					//System.out.print("This is the original: ");
					//System.out.println(kjl);
					double eq = user_Args.get(kjl) - (learn_rate*derivative_values.get(kjl));
					user_Args.replace(kjl, eq);
					//System.out.print("This is the updated verision: ");
					//System.out.println(user_Args.get(kjl));
				}
				
				//YOU WILL WANT TO ACTUALLY PRINT ALL OF THIS
					for (int z = 1; z < args.length-1; z++) {
						int k = 0;
						if (z < 6 || z > 10) {
							k = 1;
						} else {
							k = 2;
						}
						int j = 0;
						j = (z-1) % 5;
						int l = 0;
						if (z < 11) {
							l = 2;
						} else {
							l = 3;
						}
						String kjl = k + "," + j + "," +  l;
						//System.out.println(kjl);
						//System.out.print(kjl + ": ");
						System.out.printf("%.5f" , user_Args.get(kjl));
						if (z < args.length - 2) {
							System.out.print(" ");
						}
					}
					System.out.println();
					count++;
				//}
					double sum = 0;
					for (int p = 0; p < my_eval.size(); p++) {
						my_array = my_eval.get(p).split(",");
						y = Double.valueOf(my_array[4].trim());
						double [] fixedXEvals = {1.0, Double.valueOf(my_array[0].trim()), Double.valueOf(my_array[1].trim()),
								Double.valueOf(my_array[2].trim()), Double.valueOf(my_array[3].trim())};
						a_values.clear();
						z_values.clear();
						derivative_values.clear();
						d_values.clear();
						getzVals(a_values, z_values, user_Args, fixedXEvals);
						double a13 = a_values.get(1+","+3);
						
						double eq = Math.pow((a13- y), 2);
						sum += eq/2.0; 
					}
					System.out.printf("%.5f", sum);
					System.out.println();
			// end of the big for loop!
			
			
			}
		} 
		
		else if (flag == 600) {
			
			double learn_rate = Double.valueOf(args[14]);
			ArrayList<String> my_train = readCSV("./train.csv");
			ArrayList<String> my_eval = readCSV("./eval.csv");
			int count = 1;
			
			a_values = new HashMap<String, Double> ();
			z_values = new HashMap<String, Double> ();
			d_values = new HashMap<String, Double> ();
			derivative_values = new HashMap<String, Double> ();
			for (int i = 0; i < my_train.size(); i++) {
				a_values.clear();
				z_values.clear();
				d_values.clear();
				derivative_values.clear();
				String[] my_array = my_train.get(i).split(",");
				double y = Double.valueOf(my_array[4].trim());
				double [] fixedX = {1.0, Double.valueOf(my_array[0].trim()), Double.valueOf(my_array[1].trim()),
						Double.valueOf(my_array[2].trim()), Double.valueOf(my_array[3].trim())};
				getzVals (a_values, z_values, user_Args, fixedX);
				getDerivativeVals (a_values, z_values, user_Args, fixedX, y);
				
				//now update the weights
				
				
				
				for (int z = 1; z < args.length-1; z++) {
					int k = 0;
					if (z < 6 || z > 10) {
						k = 1;
					} else {
						k = 2;
					}
					int j = 0;
					j = (z-1) % 5;
					int l = 0;
					if (z < 11) {
						l = 2;
					} else {
						l = 3;
					}
					String kjl = k + "," + j + "," +  l;
					//System.out.print("This is the original: ");
					//System.out.println(kjl);
					double eq = user_Args.get(kjl) - (learn_rate*derivative_values.get(kjl));
					user_Args.replace(kjl, eq);
					//System.out.print("This is the updated verision: ");
					//System.out.println(user_Args.get(kjl));
				}
				
				//YOU WILL WANT TO ACTUALLY PRINT ALL OF THIS
					for (int z = 1; z < args.length-1; z++) {
						int k = 0;
						if (z < 6 || z > 10) {
							k = 1;
						} else {
							k = 2;
						}
						int j = 0;
						j = (z-1) % 5;
						int l = 0;
						if (z < 11) {
							l = 2;
						} else {
							l = 3;
						}
						String kjl = k + "," + j + "," +  l;
						//System.out.println(kjl);
					}
					
				//}
	
			// end of the big for loop!
			
			
			}
			
			// Now the neural network has been trained !!!
			ArrayList<String> my_test = readCSV("./test.csv"); 
			int testSetTotalCorrect = 0;
			for (int p = 0; p < my_test.size(); p++) {
				String [] my_array = my_test.get(p).split(",");
				int y = Integer.valueOf(my_array[4].trim());
				double [] fixedXTest = {1.0, Double.valueOf(my_array[0].trim()), Double.valueOf(my_array[1].trim()),
						Double.valueOf(my_array[2].trim()), Double.valueOf(my_array[3].trim())};
				a_values.clear();
				z_values.clear();
				derivative_values.clear();
				d_values.clear();
				getzVals(a_values, z_values, user_Args, fixedXTest);
				double a13 = a_values.get(1+","+3);
				int myPer = 0;
				if (a13 > .5) {
					myPer = 1;
				} else {
					myPer = 0;
				}
				
				if (myPer == y) {
					testSetTotalCorrect++;
				}
				System.out.print(y + " " + myPer + " ");
				System.out.printf("%.5f", a13);
				System.out.println();
			}
			System.out.printf("%.2f", testSetTotalCorrect/100.00);
			System.out.println();
		}
	}
		
		
		
	private static void getDerivativeVals (HashMap<String, Double> a_values, HashMap<String, Double> z_values,
			LinkedHashMap<String, Double> user_Args, double [] fixedX, double y) {
		
		
		String kl = 1 + "," + 3;
		double a_j3 = a_values.get(kl);
		double deltaj3 = (a_j3-y)*a_j3*(1-a_j3);
		//this d13
		//we need a012
		for (int k = 0; k < 3; k++) {
			String key = 1 + "," + k + "," + 3;
			String key2 = k +  "," + 2;
			double eq = deltaj3*a_values.get(key2);
			derivative_values.put(key, eq);
		}
		
		//System.out.println();
		
		//this is to get the d_values for j = 1 and 2
		for (int j = 1; j < 3; j++) {
			String key = j + "," + 2;
			double eq = deltaj3*user_Args.get(1+"," +j + "," +3)*a_values.get(key)*(1-a_values.get(key));
			d_values.put(key, eq);
		}
		
		for (int k = 0; k < 5; k++) {
			//j =1
			String jkl = 1 + "," + k + "," + 2;
			double eq = a_values.get(k+","+1)*d_values.get(1+","+2);
			derivative_values.put(jkl, eq);
		}
		
		for (int k = 0; k < 5; k++) {
			//j =1
			String jkl = 2 + "," + k + "," + 2;
			double eq = a_values.get(k+","+1)*d_values.get(2+","+2);
			derivative_values.put(jkl, eq);
		}
	}
	private static void getzVals (HashMap<String, Double> a_values, HashMap<String, Double> z_values,
			LinkedHashMap<String, Double> user_Args, double [] fixedX) {
			
		for (int j = 0; j < 5; j++) {
			String jl = j + "," + 1;  
			a_values.put(jl, fixedX[j]);
		}
		
		// now we need the z values for the next layer
		
		//HashMap<String, Double> z_values = new HashMap<String, Double> ();
		//getzVals();
		
		//to get a key you need the kjl, you will do it j times
		//maybe 3 
		for (int k = 1; k < 3; k++) {
			double sum = 0;
			for (int j = 0; j < 5; j++) {
				// layer = 2 here, l-1 = 1
				String jl = j + "," + 1;
				String kjl = k + "," + j + "," + 2;
				double ai = a_values.get(jl);
				double wji = user_Args.get(kjl);
				double eq = ai*wji;
				sum += eq;
			}
			String kl = k + "," + 2;
			z_values.put(kl, sum);
			double eq = 1.0 / (1.0 + Math.pow(Math.E, (z_values.get(kl)*-1.0)));
			a_values.put(kl, eq);
		}
		
		String key = 0+","+2;
		a_values.put(key, 1.0);
		
		
		double sum = 0;
		for (int j = 0; j < 3; j++) {
			String jl = j + "," + 2;
			String kjl = 1 + "," + j + "," + 3;
			double ai = 0;
			if (j == 0) {
				ai = 1;
			} else {
				ai = a_values.get(jl);
			}
			double wji = user_Args.get(kjl);
			double eq = ai*wji;
			sum += eq;
		}
		String kl = 1 + "," + 3;
		z_values.put(kl, sum);
		double eq = 1.0 / (1.0 + Math.pow(Math.E, (z_values.get(kl)*-1.0)));
		a_values.put(kl, eq);
		return;
	} // THIS IS THE FINAL VERSION OF NUERALNET.JAVA , for real
}
