class LoopArrayPractice2 {
	public static int roleDice(int x) {
		int[] array = new int[x];
		for (int i = 0; i < x; i++) {
			array[i] = i+1;
		}

		int randi = (int)(Math.random()*x);
		return array[randi];
	}

	public static int luckyDice(int x) {
		int[] array = new int[x];
		for (int i = 0; i < x; i++) {
			array[i] = i+1;
		}

		int randi1 = (int)(Math.random()*x);
		int randi2 = (int)(Math.random()*x);
		return Math.max(array[randi1], array[randi2]);
	}

	public static boolean evenOrOdd(int[] array) {
		int even = 0; int odd = 0;
		for (int i = 0; i < array.length; i++) {
			if (array[i] % 2 == 0) {
				even++;
			} else {odd++;}
		}
		if (even == odd) {System.out.println("evens and odds are equal");}
		return (even >= odd);
	}

	public static String nickName(String name) {
		int len = ((int)(Math.random() * name.length())) +1;

		int index = (int)(Math.random() * (name.length()-len+1));

		return name.substring(index, index+len); 
	}

	public static boolean isPalindrome(String word) {
		for (int i = 0; i < word.length(); i++) {
			if (word.charAt(i) != word.charAt(word.length()-i-1)) {
				return false;
			}
		}
		return true;
	}
	public static void main(String[] args) {
		System.out.println(roleDice(5));
		System.out.println(luckyDice(5));
		int[] array = {1,2,3,4,5};
		System.out.println(evenOrOdd(array));
		System.out.println(nickName("Zack"));
		System.out.println(isPalindrome("racecar"));
	}
}
