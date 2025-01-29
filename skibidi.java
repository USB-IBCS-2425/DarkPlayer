class skibidi {
	boolean one_and_five(int[] a) {
		for (int i = 0; i < a.length; i++) {
			if (a[i] == 1 || a[i] == 5) {
				return false;
			}
		}
		return true;
	}

	public static void main(String[] args) {
		Scanner s = new Scanner; 
		int[5] a = new int[5];
		for (int i = 0; i < 5; i++) {
			a[i] = s.nextInt;
		}
	}
}