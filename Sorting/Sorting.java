import java.util.*;

class Sorting {
	public static ArrayList<Integer> BubbleSort(ArrayList<Integer> arr) {

		for (int i = 0; i < arr.size(); i++) {
			int swaps = 0;
			for (int j = 0; j < arr.size() - 1; j++) {
				if (arr.get(j) > arr.get(j+1)) {
					int a = arr.get(j+1);
					arr.set(j+1,arr.get(j));
					arr.set(j,a);
					swaps++;
				}
			}
			if (swaps == 0) {
				break;
			}
		}
		return arr;
	}

	public static void main(String[] args) {
		long avg = 0;
		for (int j = 0; j < 10; j++) {
			ArrayList<Integer> array = new ArrayList<Integer>(2000);
			for (int i = 0; i < 2000; i++) {
				int newadd = (int)(Math.random()*100);
				array.add(newadd);
			}
			//System.out.println(array);
			long start = System.nanoTime();
			ArrayList<Integer> newarr = BubbleSort(array);
			System.out.println(System.nanoTime()-start);
		}
		//System.out.println(avg/10);
		
		//System.out.println(BubbleSort(array));
		//System.out.println("Ran in " + (long)(System.nanoTime()-start) + " nano seconds");
	}
}