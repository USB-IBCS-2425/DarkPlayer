import java.util.*;
class Mather {
	public static void main(String[] args) {
		double[] arr = {0.0000868, 0.000742, 0.00161, 0.00368, 0.00789, 0.0179, 0.0372, 0.0800};
		for (int i = 1; i < arr.length; i++) {
			System.out.println((double)(arr[i]/arr[i-1]));
		}
	}
}