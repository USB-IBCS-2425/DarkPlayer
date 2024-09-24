class Raffle {
	boolean[] won = new boolean[10];
	public Raffle() {
		for (int i = 0; i < 10; i++) {won[i]=false;}
	}

	//can only win once
	public void cantWinAgain(int s) {
		won[s] = true;
	}

	//picking winners
	public Student getRaffleWinner(Student[] kids) {
		int randomNum = (int)((Math.random())*10001);
		int mindiff = 2147483647;
		int winner = -1;
		for (int i = 0; i < 10; i++) {
			if (!won[i] && (Math.abs(randomNum - kids[i].getRafNum()) < mindiff)) {
				mindiff = Math.abs(randomNum - kids[i].getRafNum());
				winner = i;
			}
		}
		

		if (winner == -1) {return null;}
		cantWinAgain(winner);
		return kids[winner];
	}

	public static void main(String[] args) {
		//ten students
		Student Andy = new Student("Andy");
		Student Brian = new Student("Brian");
		Student Cassidy = new Student("Cassidy");
		Student Dick = new Student("Dick");
		Student Ethan = new Student("Ethan");
		Student Frank = new Student("Frank");
		Student Grady = new Student("Grady");
		Student Hamilton = new Student("Hamilton");
		Student Iman = new Student("Iman");
		Student James = new Student("James");
		Student[] students = {Andy, Brian, Cassidy, Dick, Ethan, Frank, Grady, Hamilton, Iman, James};

		//raffle
		Raffle CasinoNightRaffle = new Raffle();
		
		//three winners
		for (int i = 0; i < 3; i++) {
			Student theWinner = CasinoNightRaffle.getRaffleWinner(students);
			System.out.println(theWinner.getName() + " wins with " + theWinner.getRafNum());
		}
		
	}
}

class Student {
	String name; 
	int rafNum;

	public String getName() {
		return name;
	}
	public int getRafNum() {
		return rafNum;
	}

	public Student(String n) {
		name = n;
		rafNum = (int)((Math.random())*10001);
	}
}

class Fibonnacci {
	int[] array = new int[20];
	int correct;

	public void printArray() {
		for (int i = 0; i < 20; i++) {
			System.out.println(array[i]);
		}
	}

	public void update(int n) {
		correct = Math.max(correct, n);
		for (int i = 2; i < n; i++) {
			array[i] += array[i-1] + array[i-2];
		}
	}

	public Fibonnacci() {
		//first is 0 and second are 1, everything else is 0
		array[1] = 1;
		correct = 2;
	}

	public static void main(String[] args) {
		Fibonnacci fibs = new Fibonnacci();
		fibs.update(20);
		fibs.printArray();
	}

}

class Pset4 {
	//test
	public static void main(String[] args) {
		//ten students
		Student Andy = new Student("Andy");
		Student Brian = new Student("Brian");
		Student Cassidy = new Student("Cassidy");
		Student Dick = new Student("Dick");
		Student Ethan = new Student("Ethan");
		Student Frank = new Student("Frank");
		Student Grady = new Student("Grady");
		Student Hamilton = new Student("Hamilton");
		Student Iman = new Student("Iman");
		Student James = new Student("James");
		Student[] students = {Andy, Brian, Cassidy, Dick, Ethan, Frank, Grady, Hamilton, Iman, James};

		//raffle
		Raffle CasinoNightRaffle = new Raffle();
		
		//three winners
		for (int i = 0; i < 3; i++) {
			Student theWinner = CasinoNightRaffle.getRaffleWinner(students);
			System.out.println(theWinner.getName() + " wins with " + theWinner.getRafNum());
		}

		//fibonnacci
		Fibonnacci fibs = new Fibonnacci();
		fibs.update(20);
		fibs.printArray();
	}
}