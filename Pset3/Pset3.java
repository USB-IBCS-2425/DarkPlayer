class Wallet {
	private int money;
	private int IDnumber;

	public Wallet() {
		money = 0;
		IDnumber = 123;
	}

	public int getMoney() {
		return money;
	}
	public int getIDnumber() {
		return IDnumber;
	}

	public void setMoney(int m) {
		money = m;
	}
	public void setIDnumber(int id) {
		IDnumber = id;
	}

	public void payDay(int wage) {
		money += wage;
	}
	public void pay(int amt) {
		money -= amt;
	}

	public static void main(String[] args) {
		Wallet boen_money = new Wallet();
		boen_money.setMoney(1000);
		System.out.println(boen_money.getMoney());
		boen_money.payDay(1000);
		System.out.println(boen_money.getMoney());

	}
}

class Shape {
	double area;
	public Shape() {
		area = 5;
	}

	public double area() {
		return area;
	}

}

class Rectangle extends Shape {
	int length;
	int width;

	public Rectangle(int l, int w) {
		length = l;
		width = w;
	}

	
	@Override public double area() {
		return length * width;
	}
}

class Circle extends Shape {
	int radius;

	public Circle(int r) {
		radius = r;
	}

	
	@Override public double area() {
		return radius * radius * 3.1415;
	}

	public static void main(String[] args) {
		Rectangle rect = new Rectangle(2, 3);
		Circle circ = new Circle(4);

		System.out.println(rect.area());
		System.out.println(circ.area());
	}
}