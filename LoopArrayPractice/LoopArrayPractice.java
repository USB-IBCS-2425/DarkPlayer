class LoopArrayPractice {
	public static int middleValues(int[] Array) {
		if (Array.length % 2 == 0) {
			return Array[0];
		}
		return Array[Array.length/2];
	}

	public static int numMatches(String[] Array, String word) {
		int nums = 0;
		for (int i = 0; i < Array.length; i++) {
			if (Array[i] == word) {
				nums++;
			}
		}
		return nums;
	}

	public static boolean inOrder(int Array[]) {
		for (int i = 1; i < 5; i++) {
			if (Array[i] < Array[i-1]) {
				return false;
			}
		}
		return true;
	}

	public static boolean[] doubleLetter(String[] Array) {
		boolean returnArray[] = new boolean[Array.length];

		for (int i = 0; i < Array.length; i++) {
			returnArray[i] = false;
			for (int j = 1; j < Array[i].length(); j++) {
				if (Array[i].charAt(j) == Array[i].charAt(j-1)) {returnArray[i] = true; break;}
			}
		}

		return returnArray;
	}

	public static String[] bookEnds(String[] Array) {
		int numStuff = 0;
		for (int i = 0; i < Array.length; i++) {
			if (Array[i].charAt(0) == Array[i].charAt(Array[i].length()-1)) {numStuff++;}
		}
		String returnArray[] = new String[numStuff];
		int j = 0;
		for (int i = 0; i < Array.length; i++) {
			if (Array[i].charAt(0) == Array[i].charAt(Array[i].length()-1)) {returnArray[j] = Array[i]; j++;}
		}
		return returnArray;
	}

	public static void main(String[] args) {
		//middleValues test
		int[] array1 = {1,2,3,4,5};
		System.out.println(middleValues(array1));

		//numMatches test
		String[] array2 = {"hello", "java", "world", "sigma", "java"};
		String word = "java";
		System.out.println(numMatches(array2, word));

		//inOrder test
		int[] array3 = {5,4,3,2,1};
		System.out.println(inOrder(array3));

		//doubleLetter 
		String[] array4 = {"Considine", "Phillips", "Vankatesh", "Elliott", "Droubay", "Woolley"};
		boolean[] array5 = doubleLetter(array4);
		for (int i = 0; i < array5.length; i++) {System.out.print(array5[i] + " ");}
		System.out.print("\n");

		//bookEnds
		String[] array6 = {"bob", "jim", "sally", "anna", "boen", "elle", "considinec"};
		String[] array7 = bookEnds(array6);
		for (int i = 0; i < array7.length; i++) {
			System.out.print(array7[i] + " ");
		}
		System.out.print("\n");







	}
}