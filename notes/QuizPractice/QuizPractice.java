
class QuizPractice {

	// Q1: Find the average value of an array
	// of doubles
	public static double avgDoub(double[] d) {
		double avg = 0;
		for (int i = 0; i < d.length; i++) {
			avg += d[i];
		}
		avg /= d.length;
		return avg;
	}

	// Q2: Find the longest word in an array
	// of strings
	public static String longestWord(String[] s) {
		int longLength = 0;
		String returnWord = "";
		for (int i = 0; i < s.length; i++) {
			if (s[i].length() > longLength) {
				longLength = s[i].length();
				returnWord = s[i];
			}
		}
		return returnWord;
	}

	// Q3: Determine the most average word length
	// of a string (sentence)
	public static double avgWord(String s) {
		double avg = 0;
		for (String word : s.split(" ")) {
			avg += word.length();
		}
		avg /= s.split(" ").length;
		return avg;
	}

	public static void main(String[] args) {
		double[] array1 = {2.0,4.0,6.0};
		System.out.println(avgDoub(array1));
		String[] array2 = {"skibidi", "sigma", "gyatt"};
		System.out.println(longestWord(array2));
		String sentence = "Sigma rizzlers are skibidi";
		System.out.println(avgWord(sentence));
	}
}