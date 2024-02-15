/**
 * This class contains class (static) methods
 * that will help you test the Picture class 
 * methods.  Uncomment the methods and the code
 * in the main to test.
 * 
 * @author Barbara Ericson 
 */
public class PictureTester
{
  /** Method to test zeroBlue */
  public static void testZeroBlue()
  {
    Picture beach = new Picture("images/beach.jpg");
    beach.explore();
    beach.zeroBlue();
    beach.explore();
  }
  
  /** Method to test keepOnlyBlue */
  public static void testKeepOnlyBlue()
  {
    Picture beach = new Picture("images/beach.jpg");
    beach.explore();
    beach.keepOnlyBlue();
    beach.explore();
  }
  
  /** Method to test negate */
  public static void testNegate()
  {
    Picture caterpillar = new Picture("images/caterpillar.jpg");
    caterpillar.explore();
    caterpillar.negate();
    caterpillar.explore();
  }
  
  /** Method to test grayscale */
  public static void testGrayscale()
  {
    Picture gorge = new Picture("images/gorge.jpg");
    gorge.explore();
    gorge.grayscale();
    gorge.explore();
  }
  
  /** Method to test pixelate 
   *  @param size One side length of the larger pixels
   */
  public static void testPixelate(int size)
  {
    Picture gorge = new Picture("images/gorge.jpg");
    gorge.explore();
    gorge.pixelate(size);
    gorge.explore();
  }
  
  /** Method to test blur
   *  @param size One side length of the blur area 
   */
  public static void testBlur(int size)
  {
    Picture arch = new Picture("images/arch.jpg");
    arch.explore();
    Picture newArch = arch.blur(size);
    newArch.explore();
  }
  
  /** Method to test shiftRight */
  public static void testShiftRight(int percent)
  {
    Picture gorge = new Picture("images/gorge.jpg");
    gorge.explore();
    Picture newGorge = gorge.shiftRight(percent);
    newGorge.explore();
  }
  
  /** Method to test edgeDetectionBelow */
  public static void testEdgeDetectionBelow(int threshold)
  {
    Picture swan = new Picture("images/swan.jpg");
    swan.explore();
    Picture newSwan = swan.edgeDetectionBelow(threshold);
    newSwan.explore();
  }
  
  /** Method to test greenScreen */
  public static void testGreenScreen()
  {
    Picture overlaid = Picture.greenScreen();
    overlaid.explore();
  }
  
  /** Method to test mirrorVertical */
  public static void testMirrorVertical()
  {
    Picture caterpillar = new Picture("caterpillar.jpg");
    caterpillar.explore();
    caterpillar.mirrorVertical();
    caterpillar.explore();
  }
  
  /** Method to test mirrorTemple */
  public static void testMirrorTemple()
  {
    Picture temple = new Picture("temple.jpg");
    temple.explore();
    temple.mirrorTemple();
    temple.explore();
  }
  
  /** Method to test the collage method */
  public static void testCollage()
  {
    Picture canvas = new Picture("640x480.jpg");
    canvas.createCollage();
    canvas.explore();
  }
  
  /** Method to test edgeDetection */
  public static void testEdgeDetection()
  {
    Picture swan = new Picture("images/swan.jpg");
    swan.edgeDetection(10);
    swan.explore();
  }
  
  /** Main method for testing.  Every class can have a main
    * method in Java */
  public static void main(String[] args)
  {
    // uncomment a call here to run a test
    // and comment out the ones you don't want
    // to run
    //testZeroBlue();
    //testKeepOnlyBlue();
    //testKeepOnlyRed();
    //testKeepOnlyGreen();
    //testNegate();
    //testGrayscale();
    //testPixelate(9);
    //testShiftRight(50);
    //testPixelate(9);
    //testBlur(10);
    //testFixUnderwater();
    //testMirrorVertical();
    //testMirrorTemple();
    //testMirrorArms();
    //testMirrorGull();
    //testMirrorDiagonal();
    //testCollage();
    //testCopy();
    //testEdgeDetection();
    //testEdgeDetectionBelow(15);
    //testEdgeDetection2();
    testGreenScreen();
    //testChromakey();
    //testEncodeAndDecode();
    //testGetCountRedOverValue(250);
    //testSetRedToHalfValueInTopHalf();
    //testClearBlueOverValue(200);
    //testGetAverageForColumn(0);
  }
}
