class notes9_9 {
	public static void main(String[] args) {
		
		int x = 10; 
		double y = 5.2;

		String response = "WOAH";
		//conditions use key words 'if', 'else', 'elif'
		if (x > 8) {
			System.out.println("the variable x is greater than 8");
		}

		if (y/2 <= 4) {
			System.out.println(response);
		} else {
			y++;
		}

		if (x == 4) {
			System.out.println("x is 4");
		}

		String response2 = "WOAH";
		if (response == response2) {
			System.out.println("repsonses are equivalent");
		}
	}
}