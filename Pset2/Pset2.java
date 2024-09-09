class Pset2 {
	public static void main(String[] args) {
		String name = "Boen";
		String date = "9/5/2024";

		System.out.println(name);
		System.out.println(date);
		System.out.println();
		System.out.println();

		int students = 10;
		System.out.println(students + " students are in our class");
		System.out.println("A new student has been enrolled.");
		students++;
		System.out.println(students + " students are in our class");
		if (students < 4) {
			System.out.println("Class is cancelled");
		} else {
			System.out.println("Class continues.");
		}

		int numCharacters = 4;
		System.out.println(numCharacters + " characters in my name");
		students -= numCharacters;

		if (students < 4) {
			System.out.println("Class is cancelled");
		} else {
			System.out.println("Class continues.");
		}

		boolean divisible = (students % 3 == 0);
		if (divisible) {
			System.out.println(divisible + " " + students/3);
		} else {
			System.out.println(students % 3 + " students need to be added");
		}


	}
}