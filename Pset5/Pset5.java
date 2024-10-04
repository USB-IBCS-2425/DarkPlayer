import java.util.*;
class ChompBistro {
	String[] employees;
	String[] menu_items;
	int day = 1;

	public String getEmployees() {
		String emp = "";
		for (int i = 0; i < employees.length; i++) {
			emp += employees[i] + " ";
		}
		return emp;
	}

	public String getMenuItems() {
		String menu = "";
		for (int i = 0; i < menu_items.length; i++) {
			menu += menu_items[i] + " ";
		}
		return menu;
	}

	public int getDay() {
		return day;
	}

	public void setDay(int d) {
		day = d;
	}

	public String generateMenu() {
		String word = "";
		for (int i = 0; i < 3; i++) {
			int randInd = (int)(Math.random() * menu_items.length);
			word += menu_items[randInd] + " ";
		}
		return word;
	}

	public String whoWorking() {
		String word;
		if (day != 5) {
			word = employees[day-1] + " " + employees[day]; 
		} else {
			word = employees[4] + " " + employees[0];
		}
		return word;
	}

	public ChompBistro(String[] emp, String[] menu) {
		employees = emp;
		menu_items = menu;
	}

	public static void main(String[] args) {
		String[] emp = {"Andy", "Boen", "Candice", "Donald", "Evan"};
		String[] menu = {"Tacos", "Burritos", "Burgers", "Sandwich", "BigMac"};
		ChompBistro the_bistro = new ChompBistro(emp, menu);

		Scanner s = new Scanner(System.in);
		boolean done = false;

		while (!done) {
			System.out.println("Would you like to:");
			System.out.println("1. See the Menu of the Day");
			System.out.println("2. See who is working today");
			System.out.println("3. Change day");
			System.out.println("4. Terminate program");

			System.out.print("Choose (1,2,3,4): ");
			int choice = s.nextInt();
			System.out.println();
			if (choice == 1) {
				System.out.println("Today's menu: " + the_bistro.generateMenu());
			}
			else if (choice == 2) {
				System.out.println("Today's staff: " + the_bistro.whoWorking());
			}
			else if (choice == 3) {
				System.out.print("Change day to: ");
				int changed_day = s.nextInt();
				the_bistro.setDay(changed_day);
				System.out.println("Day changed to " + changed_day);
			}
			else {
				done = true;
			}
			System.out.println();
		}
		
	}


}