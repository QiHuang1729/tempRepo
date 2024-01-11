import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;		// for testing purposes

/**
 *	SortMethods - Sorts an ArrayList of Strings in ascending order.
 *
 *	Requires FileUtils class to compile.
 *	Requires file randomWords.txt to execute a test run.
 *
 *	@author	Qi Huang
 *	@since	January 11, 2024
 */
public class SortMethods {
	
	/**
	 *	Merge Sort algorithm - in ascending order
	 *	@param arr		List of String objects to sort
	 */
	public void mergeSort(List<String> arr) {
		long startMillisec = System.currentTimeMillis();
		mergeSortRecurse(arr, 0, arr.size() - 1);
		long endMillisec = System.currentTimeMillis();
		System.out.println(endMillisec - startMillisec);
	}
	
	/**
	 *	Recursive mergeSort method.
	 *	@param arr		List of String objects to sort
	 *	@param first	first index of arr to sort
	 *	@param last		last index of arr to sort
	 */
	public void mergeSortRecurse(List<String> arr, int first, int last) {
		// insert your code here
		int mid = (first + last) / 2;
		if (last - first + 1 > 2) {
			mergeSortRecurse(arr, first, mid);
			mergeSortRecurse(arr, mid + 1, last);
			merge(arr, first, mid, last);
		} else if (last - first + 1 == 2) {
			if (arr.get(last).compareTo(arr.get(first)) < 0) {
				String temp = arr.get(last);
				arr.set(last, arr.get(first));
				arr.set(first, temp);
			}
		}
	}
	
	/**
	 *	Merge two lists that are consecutive elements in array.
	 *	@param arr		List of String objects to merge
	 *	@param first	first index of first list
	 *	@param mid		the last index of the first list;
	 *					mid + 1 is first index of second list
	 *	@param last		last index of second list
	 */
	public void merge(List<String> arr, int first, int mid, int last) {
		// Insert your code here
		List<String> tempArr = new ArrayList<String>(arr.size());
		for (int index = 0; index < arr.size(); index++) {
			tempArr.add(null);
		}
		int i, j, k;
		i = k = first;
		j = mid + 1;
		
		while (i <= mid && j <= last) {
			if (arr.get(i).compareTo(arr.get(j)) <= 0) {
				tempArr.set(k, arr.get(i));
				i++;
				k++;
			} else {
				tempArr.set(k, arr.get(j));
				j++;
				k++;
			}
		}
		
		if (i == mid + 1) {
			while (j <= last) {
				tempArr.set(k, arr.get(j));
				j++;
				k++;
			}
		} else {
			while (i <= mid) {
				tempArr.set(k, arr.get(i));
				i++;
				k++;
			}
		}
		
		for (k = first; k <= last; k++) {
			arr.set(k, tempArr.get(k));
		}
	}

	
	/**
	 *	Print an List of Strings to the screen
	 *	@param arr		the List of Strings
	 */
	public void printArray(List<String> arr) {
		if (arr.size() == 0) System.out.print("(");
		else System.out.printf("( %-15s", arr.get(0));
		for (int a = 1; a < arr.size(); a++) {
			if (a % 5 == 0) System.out.printf(",\n  %-15s", arr.get(a));
			else System.out.printf(", %-15s", arr.get(a));
		}
		System.out.println(" )");
	}
	
	/*************************************************************/
	/********************** Test program *************************/
	/*************************************************************/
	private final String FILE_NAME = "randomWords.txt";
	
	public static void main(String[] args) {
		SortMethods se = new SortMethods();
		se.run();
	}
	
	public void run() {
		List<String> arr = new ArrayList<String>();
		// Fill List with random words from file		
		fillArray(arr);
		
		System.out.println("\nMerge Sort");
		System.out.println("Array before sort:");
		printArray(arr);
		System.out.println();
		mergeSort(arr);
		System.out.println("Array after sort:");
		printArray(arr);
		System.out.println();
	}
	
	// Fill String array with words
	public void fillArray(List<String> arr) {
		Scanner inFile = FileUtils.openToRead(FILE_NAME);
		while (inFile.hasNext())
			arr.add(inFile.next());
		inFile.close();
	}
}
