public class IntArrayWorker
{
  /** two dimensional matrix */
  private int[][] matrix = null;
  
  /** set the matrix to the passed one
    * @param theMatrix the one to use
    */
  public void setMatrix(int[][] theMatrix)
  {
    matrix = theMatrix;
  }
  
  /**
   * Method to return the total 
   * @return the total of the values in the array
   */
  public int getTotal()
  {
    int total = 0;
    for (int row = 0; row < matrix.length; row++)
    {
      for (int col = 0; col < matrix[0].length; col++)
      {
        total = total + matrix[row][col];
      }
    }
    return total;
  }
  
  /**
   * Method to return the total using a nested for-each loop
   * @return the total of the values in the array
   */
  public int getTotalNested()
  {
    int total = 0;
    for (int[] rowArray : matrix)
    {
      for (int item : rowArray)
      {
        total = total + item;
      }
    }
    return total;
  }
  
  /**
   * Method to return the count of the number of times an integer passed appears
   * in the matrix.
   * @param num
   * @return the total number of "num"s in the matrix
   */
  public int getCount (int num) {
	  int count = 0;
	  for (int[] rowArray : matrix) {
		  for (int item: rowArray) {
			  if (item == num) 
				count++;
		  }
	  }
	  
	  return count;
  }
  
  /**
   * Method to return the largest number found in the matrix.
   * @return the largest number in the matrix
   */
  public int getLargest () {
	  int max = 0;
	  for (int[] rowArray : matrix) {
		  for (int item : rowArray) {
			  if (item > max)
				max = item;
		  }
	  }
	  return max;
  }
  
  /**
   * Method to get the total of a column in the matrix
   * @param column
   * @return the sum of the numbers in the column of the matrix represented by 
   *   the parameter
   */
  public int getColTotal (int column) {
	  int total = 0;
	  for (int[] rowArray : matrix) {
		  total += rowArray[column];
	  }
	  return total;
  }
  
  /**
   * Method to reverse each row of a matrix
   */
  public void reverseRows () {
	  int temp = -1;
	  System.out.println("Array before reverseRows:");
	  print();
	  for (int[] rowArray : matrix) {
		  for (int col = 0; col < rowArray.length / 2; col++) {
			  temp = rowArray[col];
			  rowArray[col] = rowArray[ (rowArray.length - 1) - col ];
			  rowArray[ (rowArray.length - col) - 1 ] = temp;
		  }
	  }
	  System.out.println("Array after reverseRows:");
	  print();
  }
  
  /**
   * Method to fill with an increasing count
   */
  public void fillCount()
  {
    int numCols = matrix[0].length;
    int count = 1;
    for (int row = 0; row < matrix.length; row++)
    {
      for (int col = 0; col < numCols; col++)
      {
        matrix[row][col] = count;
        count++;
      }
    }
  }
  
  /**
   * print the values in the array in rows and columns
   */
  public void print()
  {
    for (int row = 0; row < matrix.length; row++)
    {
      for (int col = 0; col < matrix[0].length; col++)
      {
        System.out.print( matrix[row][col] + " " );
      }
      System.out.println();
    }
    System.out.println();
  }
  
  
  /** 
   * fill the array with a pattern
   */
  public void fillPattern1()
  {
    for (int row = 0; row < matrix.length; row++)
    {
      for (int col = 0; col < matrix[0].length; 
           col++)
      {
        if (row < col)
          matrix[row][col] = 1;
        else if (row == col)
          matrix[row][col] = 2;
        else
          matrix[row][col] = 3;
      }
    }
  }
 
}
