class Person {
	private String name;

	public Person() {
		name = "Ken";
	}

	public String getName() {
		return name;
	}
	public String getName(boolean b) {
		if (b) {
			return name + "Considine";
		}
		else {
			return "Mr. Considine";
		}
	}
}

class Student extends Person {
	double grade;

	public Student(double d) {
		super();
		grade = d;
	}
}

class notes9_19 {
	public static void main(String[] args) {
		Person K = new Person();
		System.out.println(K.getName());
		System.out.println(K.getName(true));
		System.out.println(K.getName(false));

		Scanner s = new Scanner(System.in);
		double d = nextDouble();
		Student sigma = new Student(d);
		System.out.println("This student's grade is " + sigma.grade);

	}
}