//import utlity
import java.util.*;

class ArrayNotes {
	//fixed length
	//collections of data
	//same data types
	public static void main(String[] args) {
		
		int[] a = {1,2,3,4,5};
		System.out.println(a[0]);

		int[] b = new int[10];

		for (int i =0; i < 10; i++) {
			System.out.println(b[i]);
		}

		for (int i = 0; i < 10; i++) {
			a[i] = (int)(Math.random()*101);
		}
		for (int i:a) {
			System.out.println(i);
		}
	}
}