/**
 *	SortMethods - Sorts an array of Integers in ascending order.
 *
 *	@author Qi Huang
 *	@since	11/30/2023
 */
public class SortMethods {
	
	/**
	 *	Bubble Sort algorithm - in ascending order
	 *	@param arr		array of Integer objects to sort
	 */
	public void bubbleSort(Integer [] arr) {
		
		for (int outer = arr.length - 1; outer > 0; outer--)
			for (int inner = 0; inner < outer; inner++)
				if (arr[inner].compareTo(arr[inner + 1]) > 0)
					swap(arr, inner, inner + 1);
	}
	
	/**
	 *	Swaps two Integer objects in array arr
	 *	@param arr		array of Integer objects
	 *	@param x		index of first object to swap
	 *	@param y		index of second object to swap
	 */
	private void swap(Integer[] arr, int x, int y) {
		Integer temp = arr[x];
		arr[x] = arr[y];
		arr[y] = temp;
	}
	
	/**
	 *	Selection Sort algorithm - in ascending order (you implement)
	 *	@param arr		array of Integer objects to sort
	 */
	public void selectionSort(Integer [] arr) {
		int maxIndex = 0;
		for (int outer = arr.length - 1; outer > 0; outer--) {
			maxIndex = 0;
			for (int inner = 1; inner <= outer; inner++) {
				if (arr[maxIndex].compareTo(arr[inner]) < 0) {
					maxIndex = inner;
				}
			}
			swap(arr, maxIndex, outer);
		}
	}
	
	/**
	 *	Insertion Sort algorithm - in ascending order (you implement)
	 *	@param arr		array of Integer objects to sort
	 */
	public void insertionSort(Integer [] arr) {
		for (int i = 1; i < arr.length; i++) {
			int j = i - 1;
			while (j > -1 && arr[i].compareTo(arr[j]) < 0) {
				j--;
			}
			Integer temp = arr[i];
			
			for (int k = i; k > j + 1; k--) {
				arr[k] = arr[k - 1];
			}
			arr[j + 1] = temp;
		}
	}
	
	/**
	 *	Merge Sort algorithm - in ascending order (you implement)
	 *	@param arr		array of Integer objects to sort
	 */
	public void mergeSort(Integer [] arr) {
		Integer [] tempArr = new Integer[arr.length];
		mergeSort(arr, tempArr, 0, arr.length - 1);
	}
	
	// note: when debugging, just checking the logic of the code is fine
	private void mergeSort(Integer [] arr, Integer [] tempArr, 
							int start, int end) {
		int range = end - start + 1;
		if (range > 2) {
			// split arrays below
			int middle = (end + start) / 2; // middle is not range / 2
			
			mergeSort(arr, tempArr, start, middle);
			mergeSort(arr, tempArr, middle + 1, end);
			
			// i = first split index
			// j = second split index
			// k = tempArr index
			/* note: k is initialized at start, not 0. It would only be 0
			 * if you had a tempArr that was exactly the total size of 
			 * the arrays currently being merged */
			int i, j, k;
			i = k = start; 
			j = middle + 1;
			
			// Exits when one of the pointers move off the array
			while (i <= middle && j <= end) /* end is included for j*/ {
				// stores smaller element
				// increments the pointer of the smaller element				
				if (arr[i].compareTo(arr[j]) <= 0) {
					tempArr[k] = arr[i]; // remember to actually store this 
										// in tempArr
					i++;
				} else if (arr[i].compareTo(arr[j]) > 0) {
					tempArr[k] = arr[j];
					j++;
				}
				// increment tempArr pointer
				k++;
			}
			
			// if one pointer is out of its split, flush the rest of the 
			// split with the other pointer
			if (i == middle + 1) {
				while (j <= end) /* end is included for j*/ {
					tempArr[k] = arr[j]; // remember to actually store this 
										// in tempArr
					j++;
					k++;
					
				}
			} else if (j == end + 1) /* out of range element for j is 
															end + 1*/ {
				while (i <= middle) {
					tempArr[k] = arr[i];
					i++;
					k++;
				}
			}
			
			// copy results from temp array to original array
			for (k = start; k <= end; k++) {
				arr[k] = tempArr[k];
			}
		} else if (range == 2) {
			// swap arrays
			if (arr[start] > arr[end]) {
				swap(arr, start, end);
			} 
		}
		// if the range is one, do nothing
		// if the range is somehow less than or equal to 0, do nothing
	}
	
	/*****************************************************************/
	/************************* For Testing ***************************/
	/*****************************************************************/
	
	/**
	 *	Print an array of Integers to the screen
	 *	@param arr		the array of Integers
	 */
	public void printArray(Integer[] arr) {
		if (arr.length == 0) System.out.print("(");
		else System.out.printf("( %4d", arr[0]);
		for (int a = 1; a < arr.length; a++) {
			if (a % 10 == 0) System.out.printf(",\n  %4d", arr[a]);
			else System.out.printf(", %4d", arr[a]);
		}
		System.out.println(" )");
	}

	public static void main(String[] args) {
		SortMethods se = new SortMethods();
		se.run();
	}
	
	public void run() {
		Integer[] arr = new Integer[10];
		// Fill arr with random numbers
		for (int a = 0; a < 10; a++)
			arr[a] = (int)(Math.random() * 100) + 1;
		System.out.println("\nBubble Sort");
		System.out.println("Array before sort:");
		printArray(arr);
		System.out.println();
		bubbleSort(arr);
		System.out.println("Array after sort:");
		printArray(arr);
		System.out.println();
		
		for (int a = 0; a < 10; a++)
			arr[a] = (int)(Math.random() * 100) + 1;
		System.out.println("\nSelection Sort");
		System.out.println("Array before sort:");
		printArray(arr);
		System.out.println();
		selectionSort(arr);
		System.out.println("Array after sort:");
		printArray(arr);
		System.out.println();
		
		for (int a = 0; a < 10; a++)
			arr[a] = (int)(Math.random() * 100) + 1;
		System.out.println("\nInsertion Sort");
		System.out.println("Array before sort:");
		printArray(arr);
		System.out.println();
		insertionSort(arr);
		System.out.println("Array after sort:");
		printArray(arr);
		System.out.println();

		for (int a = 0; a < 10; a++)
			arr[a] = (int)(Math.random() * 100) + 1;
		System.out.println("\nMerge Sort");
		System.out.println("Array before sort:");
		printArray(arr);
		System.out.println();
		mergeSort(arr);
		System.out.println("Array after sort:");
		printArray(arr);
		System.out.println();

	}
}
