class notesLoops {
	public static void main(String[] args) {
		/*
		//Definite Loop
		for (int i = 0; i < 10; i++) {
			System.out.println("sigma");
		}
		//first 20 multiples of 11
		for (int i = 1; i <= 20; i++) {
			System.out.println(i*11);
		}*/

		for (int i = 1; i <= 400; i++) {
			for (int j = 1; j*j <= i; j++) {
				if (j*j == i) {System.out.println(i);}
			}
		}
	}
}