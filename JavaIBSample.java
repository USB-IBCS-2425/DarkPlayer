import java.util.*;

class JavaIBSample {
	public static int CountOdd(int[] arr) {
		int sum = 0;
		for (int i = 0; i < arr.length; i++) {
			if (arr[i] % 2 == 1) {sum += arr[i];}
		}
		return sum;
	}

	public static double SecondLargest(double[] arr) {
		int j = 0;
		double a = -1; double b = -1;
		for (int i = 0; i < arr.length; i++) {
			if (arr[i] > a) {j = i; a = arr[i];}
		}

		for (int i = 0; i < arr.length; i++) {
			if (i == j) {continue;}
			if (arr[i] > b) {b = arr[i];}
		}
		if (arr.length < 2) {return 0;}
		return b;
	}

	public static String[] ShiftX(int x, String[] arr) {
		String[] new_arr = new String[arr.length];
		for (int i = 0; i < arr.length-x; i++) {
			new_arr[i+x] = arr[i];
		}

		int j = 0; 
		for (int i = arr.length -x; i < arr.length; i++) {
			new_arr[j] = arr[i];
			j++;
		}
		return new_arr;
	}

	public static int[] Merge(int[] a, int[] b) {
		int[] new_arr = new int[a.length + b.length];
		for (int i = 0; i < a.length; i++) {
			new_arr[i] = a[i];
			new_arr[a.length + i] = b[i];
		}

		Arrays.sort(new_arr);
		return new_arr;
	}

	public static String NoRepeat(String s) {
	    int maxCount = 0;
	    String longest = "";

	    for (int i = 0; i < s.length(); i++) {
	        String sub = "";
	        for (int j = i; j < s.length(); j++) {
	            char c = s.charAt(j);
	            if (sub.contains(String.valueOf(c))) {
	                break; 
	            }
	            sub += c; 
	            if (sub.length() > maxCount) {
	                maxCount = sub.length();
	                longest = sub;
	            }
	        }
	    }

	    return longest;
	}

	public static int FindMissing(int[] arr) {
		Arrays.sort(arr);
		int ind = 0;
		for (int i = 1; i <= arr.length + 1; i++) {
			if (arr[ind] != i) {
				return i;
			}
			ind++;
		}
		return 0;
	}

	public static ArrayList<int[]> threeSum(int[] arr) {
		HashMap<Integer, Integer> val = new HashMap<>();
		HashMap<String, Boolean> dupl = new HashMap<>();
		ArrayList<int[]> sols = new ArrayList<>();

		for (int i = 0; i < arr.length; i++) {
			val.put(arr[i], val.getOrDefault(arr[i], 0) + 1);
		}

		for (int i = 0; i < arr.length; i++) {
			for (int j = i + 1; j < arr.length; j++) {
				int sum = -(arr[i] + arr[j]);
				

				if (val.getOrDefault(sum, 0) > 0) {
					if ((sum == arr[i] || sum == arr[j]) && val.getOrDefault(sum, 0) == 1) {
					    continue;
					}
					
					int[] new_arr = {arr[i], arr[j], sum};
					Arrays.sort(new_arr);
					String key = new_arr[0] + "," + new_arr[1] + "," + new_arr[2];
					if (!dupl.getOrDefault(key, false)){
						sols.add(new_arr);
					}
					dupl.put(key, true);
					
				}
			}
		}
		return sols;



	}


	public static void main(String[] args) {
		int[] arr = {1,3,3,5,-6};
		ArrayList<int[]> sols = threeSum(arr);
		for (int i = 0; i < sols.size(); i++) {
			for (int j = 0; j < 3; j++) {
				System.out.print(sols.get(i)[j] + " ");
			}
			System.out.println();
		}

	}
}