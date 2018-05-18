
public class TSPMain {


	public static void OX1(String p1, String p2, int start, int end) {

		boolean done1 = false;
		boolean done2 = false;

		int curr1 = end;
		int store1 = end;
		int curr2 = end;
		int store2 = end;

		char[] temp_p1 = p1.toCharArray();
		char[] temp_p2 = p2.toCharArray();

		char[] of1 = new char[8];
		char[] of2 = new char[8];

		populateArray(of1);
		populateArray(of2);

		for (int i = start - 1; i < end; i++) {
			of1[i] = temp_p1[i];
			of2[i] = temp_p2[i];
		}

		while (!done1) {
			curr1 = curr1 % 8;
			store1 = store1 % 8;
			if (!arrayContains(of1, temp_p2[curr1])) {

				of1[store1] = temp_p2[curr1];

				store1++;
			}

			if (isDone(of1))
				done1 = true;

			curr1++;

		}

		while (!done2) {
			curr2 = curr2 % 8;
			store2 = store2 % 8;
			if (!arrayContains(of2, temp_p1[curr2])) {

				of2[store2] = temp_p1[curr2];

				store2++;
			}

			if (isDone(of2))
				done2 = true;

			curr2++;

		}

		System.out.println(of1);
		System.out.println(of2);
	}

	public static boolean isDone(char[] arr) {
		int len = arr.length;

		for (int i = 0; i < len; i++) {
			if (arr[i] == 'x')
				return false;
		}
		return true;
	}

	public static void populateArray(char[] arr) {
		int len = arr.length;

		for (int i = 0; i < len; i++) {
			arr[i] = 'x';
		}
	}

	public static boolean arrayContains(char[] arr, char c) {
		
		int len = arr.length;
		for (int i = 0; i < len; i++) {
			if (arr[i] == c)
				return true;
		}

		return false;
	}

	public static void main(String[] args) {
		String a1 = "12345678";
		char[] temp = a1.toCharArray();

		int i = 0;

		double dis = Math.sqrt(Math.pow((6734-2233),2)+Math.pow(1453-10, 2));
		
		OX1("12345678", "24687531", 3, 5);
		
		System.out.println(Math.ceil(dis));

	}

}
