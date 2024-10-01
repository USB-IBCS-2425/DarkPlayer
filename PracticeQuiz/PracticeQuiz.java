class PracticeQuiz {
	public static int rangeVals(int[] array) {
		int mini = 2147483647; int maxi = 0;
		for (int i = 0; i < array.length; i++) {
			mini = Math.min(mini, array[i]);
			maxi = Math.max(maxi, array[i]);
		}
		return maxi-mini;
	}

	public static String smallWords(String[] array) {
		String returner = "";
		for (int i = 0; i < array.length; i++) {
			if (array[i].length() <= 4) {
				returner += array[i] + " ";
			}
		}
		return returner;
	}

	public static String randomSentence(String[] array, int n) {
		String word = "";
		boolean[] done = new boolean[array.length];
		for (int i = 0; i < done.length; i++) {done[i]=false;}

		for (int i = 0; i < n; i++) {

			int randIndex = (int)(Math.random()*array.length);
			while (done[randIndex]) {
				randIndex = (int)(Math.random()*array.length);
			}
			done[randIndex] = true;
			word += array[randIndex];
		}
		return word;
	}

	public static void main(String[] args) {
		int[] array1 = {1,2,3,4,5};
		System.out.println(rangeVals(array1));
		String[] array2 = {"help", "quiz", "today", "or", "tomorrow"};
		System.out.println(smallWords(array2));
		String[] array3 = {"I", "love", "CS", "and", "homework"};
		System.out.println(randomSentence(array3, 3));

	}


}