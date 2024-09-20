class Calculator {
	String brand; 
	double weight;
	String color;
	boolean SATuse;
	boolean graphing;
	int year;

	public Calculator(int y, String b) {
		brand = b;
		weight = 50.5;
		color = "yellow";
		SATuse = true;
		graphing = true;
		year = y;
	}

	public static void main(String[] args) {
		Calculator boenCalc = new Calculator(2024, "Casio");

		System.out.println(boenCalc.year);
	}
}