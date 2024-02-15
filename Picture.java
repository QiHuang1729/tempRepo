import java.awt.*;
import java.awt.font.*;
import java.awt.geom.*;
import java.awt.image.BufferedImage;
import java.text.*;
import java.util.*;
import java.util.List; // resolves problem with java.awt.List and java.util.List

/**
 * A class that represents a picture.  This class inherits from 
 * SimplePicture and allows the student to add functionality to
 * the Picture class.  
 * 
 * @author Barbara Ericson ericson@cc.gatech.edu
 */
public class Picture extends SimplePicture 
{
  ///////////////////// constructors //////////////////////////////////
  
  /**
   * Constructor that takes no arguments 
   */
  public Picture ()
  {
    /* not needed but use it to show students the implicit call to super()
     * child constructors always call a parent constructor 
     */
    super();  
  }
  
  /**
   * Constructor that takes a file name and creates the picture 
   * @param fileName the name of the file to create the picture from
   */
  public Picture(String fileName)
  {
    // let the parent class handle this fileName
    super(fileName);
  }
  
  /**
   * Constructor that takes the width and height
   * @param height the height of the desired picture
   * @param width the width of the desired picture
   */
  public Picture(int height, int width)
  {
    // let the parent class handle this width and height
    super(width,height);
  }
  
  /**
   * Constructor that takes a picture and creates a 
   * copy of that picture
   * @param copyPicture the picture to copy
   */
  public Picture(Picture copyPicture)
  {
    // let the parent class do the copy
    super(copyPicture);
  }
  
  /**
   * Constructor that takes a buffered image
   * @param image the buffered image to use
   */
  public Picture(BufferedImage image)
  {
    super(image);
  }
  
  ////////////////////// methods ///////////////////////////////////////
  
  /**
   * Method to return a string with information about this picture.
   * @return a string with information about the picture such as fileName,
   * height and width.
   */
  public String toString()
  {
    String output = "Picture, filename " + getFileName() + 
      " height " + getHeight() 
      + " width " + getWidth();
    return output;
    
  }
  
  /** Method to set the blue to 0 */
  public void zeroBlue()
  {
    Pixel[][] pixels = this.getPixels2D();
    for (Pixel[] rowArray : pixels)
    {
      for (Pixel pixelObj : rowArray)
      {
        pixelObj.setBlue(0);
      }
    }
  }
  
  /** Method to set the red and green to 0 */
  public void keepOnlyBlue()
  {
    Pixel[][] pixels = this.getPixels2D();
    for (Pixel[] rowArray : pixels)
    {
      for (Pixel pixelObj : rowArray)
      {
        pixelObj.setRed(0);
        pixelObj.setGreen(0);
      }
    }
  }
  
  /** Method to negate the red, green, and blue values */
  public void negate()
  {
    Pixel[][] pixels = this.getPixels2D();
    for (Pixel[] rowArray : pixels)
    {
      for (Pixel pixelObj : rowArray)
      {
        pixelObj.setRed(255 - pixelObj.getRed());
        pixelObj.setGreen(255 - pixelObj.getGreen());
        pixelObj.setBlue(255 - pixelObj.getBlue());
      }
    }
  }
  
  /** Method to convert the picture to grayscale */
  public void grayscale()
  {
    Pixel[][] pixels = this.getPixels2D();
    int average = -1;
    for (Pixel[] rowArray : pixels)
    {
      for (Pixel pixelObj : rowArray)
      {
        average = (pixelObj.getRed() + pixelObj.getGreen()
                + pixelObj.getBlue()) / 3;
        Color newColor = new Color(average, average, average);
        pixelObj.setColor(newColor);
      }
    }
  }
  
  /** To pixelate by dividing area into size x size.
   * @param size Side length of square area to pixelate.
   */
	public void pixelate(int size) {
		Pixel[][] pixels = this.getPixels2D();
		int red = 0; int green = 0; int blue = 0;
		int areaHeight = -1;
		int areaWidth = -1;
		
		// we don't want the areaHeight to ever be zero, 
		// if row * size == pixels.length, then the thing is out of bounds
		for (int rowArea = 0; rowArea <= pixels.length / size; rowArea++) 
		{
			for (int colArea = 0; colArea <= pixels[0].length / size; colArea++) 
			{
				// resets variables
				red = 0; green = 0; blue = 0;
				areaHeight = 0; areaWidth = 0;
				
				// determines height of the area, or the total number of rows
				if (rowArea < pixels.length / size) 
					areaHeight = size; 
				else 
					areaHeight = pixels.length - size * 
												(pixels.length / size);
				
				// determines width of the area, or the toal number of columns
				if (colArea < pixels[0].length / size)
					areaWidth = size;
				else
					areaWidth = pixels[0].length - size * 
											(pixels[0].length / size);
				
				// find the total RGB values of the pixels in the area
				for (int row = 0; row < areaHeight; row++)
				{
					for (int col = 0; col < areaWidth; col++)
					{
						red += 
							pixels[size * rowArea + row][size * colArea + col]
								.getRed();
						green += 
							pixels[size * rowArea + row][size * colArea + col]
								.getGreen();
						blue += 
							pixels[size * rowArea + row][size * colArea + col]
								.getBlue();
					}
				}
					
				// When the areaHeight or the areaWidth is zero, I 
				// prevent all the code in this loop from executing
				if (areaHeight != 0 && areaWidth != 0)
				{
					red /= areaHeight * areaWidth;
					green /= areaHeight * areaWidth;
					blue /= areaHeight * areaWidth;
				}
				
				// loops through the area again to set the average color
				// to each pixel
				for (int row = 0; row < areaHeight; row++)
				{
					for (int col = 0; col < areaWidth; col++)
					{
						Color averageColor = new Color(red, green, blue);
						pixels[size * rowArea + row][size * colArea + col]
							.setColor(averageColor);
					}
				}
			}
		}
	}
	
	/** Method that blurs the picture
	* @param size Blur size, greater is more blur
	* @return Blurred picture
	*/
	public Picture blur(int size)
	{
		Pixel[][] pixels = this.getPixels2D();
		Picture result = new Picture(pixels.length, pixels[0].length);
		Pixel[][] resultPixels = result.getPixels2D();
		
		int radius = (size - 1) / 2;
		
		for (int row = 0; row < pixels.length; row++)
		{
			for (int col = 0; col < pixels[0].length; col++)
			{
				Color colorOut = averageColor(row, col, radius);
				resultPixels[row][col].setColor(colorOut);
			}
		}
		
		return result;
	}
	
	/** Method that finds the average color in a specific square area
	 * @param row Row of the area's center
	 * @param col Col of the area's center
	 * @param radius Distance from the center pixel to the edge pixel
	 * @return the average color in the area
	 */
	// Previous time: 1 m 20 s (1st) 1 m 15 s (2nd), 1 m 12 s (3rd)
	// New time: 1 m 13 s (4th) 1 m 14 s (5th), 1 m 13 s (6th)
	// Blur time for Beach, 11 x 11 -- 3:55 
	// Blur time for Beach, 5 x 5 -- 3:55 (again?) 
	// Blur time for Beach, 5 x 5 -- 3:55 (again?) (5 x 5 has same time as 11 x 11)
	private Color averageColor(int row, int col, int radius)
	{
		Pixel[][] pixels = this.getPixels2D();
		int totalRed = 0; int totalGreen = 0; int totalBlue = 0;
		int count = 0;
		Color colorOut = null;
				
		for (int r = row - radius; r <= row + radius; r++)
		{
			for (int c = col - radius; c <= col + radius; c++)
			{
				if ( (0 <= r && r <= pixels.length - 1)
						&& (0 <= c && c <= pixels[0].length - 1) )
				{
					totalRed += pixels[r][c].getRed();
					totalGreen += pixels[r][c].getGreen();
					totalBlue += pixels[r][c].getBlue();
					count++;
				}
			}
		}		
		
		colorOut = new Color(totalRed / count, totalGreen / count, 
			totalBlue / count);
			
		return colorOut;
	}
	
	/** Method that enhances a picture by getting average Color around
	 * a pixel then applies the following formula:
	 *
	 * pixelColor <- 2 * currentValue - averageValue
	 *
	 * size is the area to sample for blur.
	 *
	 * @param size Larger means more area to average around pixel
	 * and longer compute time.
	 * @return enhanced picture
	 */
	// takes ~2:20 for water, enhance area 5
	public Picture enhance(int size)
	{
		Pixel[][] pixels = this.getPixels2D();
		Picture result = new Picture(pixels.length, pixels[0].length);
		Pixel[][] resultPixels = result.getPixels2D();
		
		int radius = (size - 1) / 2;
		
		for (int row = 0; row < pixels.length; row++)
		{
			for (int col = 0; col < pixels[0].length; col++)
			{
				Color enhancedColor = enhancePixel(row, col, radius);
				resultPixels[row][col].setColor(enhancedColor);
			}
		}
		
		return result;
	}
	
	/** Method that finds the enhanced color of a pixel given its surrounding
	 * square area
	 * @param row Row of the area's center
	 * @param col Col of the area's center
	 * @param radius Length (in pixels) from the left side of the center
	 *   to the right side of the edge (of the area)
	 * @return the enhanced color of the pixel
	 */
	private Color enhancePixel(int row, int col, int radius)
	{
		Pixel[][] pixels = this.getPixels2D();
		int sumRed = 0; int sumGreen = 0; int sumBlue = 0;
		int outRed = 0; int outBlue = 0; int outGreen = 0;
		int count = 0;
		Color colorOut = null;
				
		for (int r = row - radius; r <= row + radius; r++)
		{
			for (int c = col - radius; c <= col + radius; c++)
			{
				if ( (0 <= r && r <= pixels.length - 1)
						&& (0 <= c && c <= pixels[0].length - 1) )
				{
					sumRed += pixels[r][c].getRed();
					sumGreen += pixels[r][c].getGreen();
					sumBlue += pixels[r][c].getBlue();
					count++;
				}
			}
		}
		
		outRed = 2 * pixels[row][col].getRed() - sumRed / count;
		outGreen = 2 * pixels[row][col].getGreen() - sumGreen / count;
		outBlue = 2 * pixels[row][col].getBlue() - sumBlue / count;
		
		if (outRed > 255) outRed = 255; else if (outRed < 0) outRed = 0;
		if (outGreen > 255) outGreen = 255; else if (outGreen < 0) outGreen = 0;
		if (outBlue > 255) outBlue = 255; else if (outBlue < 0) outBlue = 0;
		
		colorOut = new Color(outRed, outGreen, outBlue);
		
		return colorOut;
	}
	
	/** Method to create a new picture which is shifted right by percent.
	 * The rightmost portion of the picture is wrapped around to the left.
	 * @param percent The percentage of the picture to shift right
	 * @return the new shifted Picture
	 */
	public Picture shiftRight(int percent)
	{
		Pixel[][] pixels = this.getPixels2D();
		Picture shifted = new Picture(pixels.length, pixels[0].length);
		Pixel[][] shiftedPixels = shifted.getPixels2D();
		Color pixelColor = null;
		
		int shift = pixels[0].length * percent / 100;
		int newCol = -1;
		
		for (int row = 0; row < pixels.length; row++)
		{
			for (int col = 0; col < pixels[0].length; col++)
			{
				newCol = (col + shift) % pixels[0].length;
				pixelColor = pixels[row][col].getColor();
				shiftedPixels[row][newCol].setColor(pixelColor);
			}
		}
		
		return shifted;
	}
	
	/** Method to create a new picture which is shifted right by shiftCount
	 * a "steps" number of steps. 
	 * Pixels wrap around right to left.
	 * @param shiftCount The number of pixels shifted on each step
	 * @param steps The number of vertical steps to shift
	 * @return the new shifted Picture
	 */
	public Picture stairStep(int shiftCount, int steps)
	{
		Pixel[][] pixels = this.getPixels2D();
		Picture staired = new Picture(pixels.length, pixels[0].length);
		Pixel[][] stairedPixels = staired.getPixels2D();
		Color pixelColor = null;
		
		int shift = -1;
		int stepLength = pixels.length / steps; // (this could be double and would
												// be more accurate)
		int shiftedCol = -1;
		
		for (int row = 0; row < pixels.length; row++)
		{
			// shiftCount * number of steps above the row
			// NOTE: shiftCount * row / stepLength does something interesting
			shift = shiftCount * (row / stepLength); 
			
			for (int col = 0; col < pixels[0].length; col++)
			{
				shiftedCol = (col + shift) % pixels[0].length;
				pixelColor = pixels[row][col].getColor();
				stairedPixels[row][shiftedCol].setColor(pixelColor);
			}
		}
		
		return staired;
	}
	// note: the resulting pixels in the (1, 400) is shifted 1 to the left
	// relative to the example. I thought the first stair step is supposed to be
	// touching the left side?
	
	/**
	 * Method to create a new picture which is turned 90 degrees clockwise
	 */
	public Picture turn90()
	{
		Pixel[][] pixels = this.getPixels2D();
		Picture turned = new Picture(pixels[0].length, pixels.length);
		Pixel[][] turnedPixels = turned.getPixels2D();
		
		for (int row = 0; row < pixels.length; row++)
		{
			for (int col = 0; col < pixels[0].length; col++)
			{
				Color pixelColor = pixels[row][col].getColor();
				turnedPixels[col][pixels.length - 1 - row].setColor(pixelColor);
			}
		}
		
		return turned;
	}
	
	/**
	 * Method to create a new picture which is turned 90 degrees clockwise
	 */
	public Picture zoomUpperLeft()	
	{
		Pixel[][] pixels = this.getPixels2D();
		Picture zoomed = new Picture(pixels.length, pixels[0].length);
		Pixel[][] zoomedPixels = zoomed.getPixels2D();
		
		for (int row = 0; row < (pixels.length + 1) / 2; row++)
		{
			for (int col = 0; col < (pixels[0].length + 1) / 2; col++)
			{
				Color pixelColor = pixels[row][col].getColor();
				
				if (2 * row + 1 == zoomedPixels.length && 
					2 * col + 1 == zoomedPixels[0].length)
				{
					zoomedPixels[2 * row][2 * col].setColor(pixelColor);
				}
				else if (2 * row + 1 == zoomedPixels.length) 
				{
					zoomedPixels[2 * row][2 * col].setColor(pixelColor);
					zoomedPixels[2 * row][2 * col + 1].setColor(pixelColor);
				}
				else if (2 * col + 1 == zoomedPixels[0].length)
				{
					zoomedPixels[2 * row][2 * col].setColor(pixelColor);
					zoomedPixels[2 * row + 1][2 * col].setColor(pixelColor);
				}
				else
				{
					zoomedPixels[2 * row][2 * col].setColor(pixelColor);
					zoomedPixels[2 * row + 1][2 * col].setColor(pixelColor);
					zoomedPixels[2 * row][2 * col + 1].setColor(pixelColor);
					zoomedPixels[2 * row + 1][2 * col + 1].setColor(pixelColor);
				}
			}
		}
		
		return zoomed;
	}
	
	/**
	 * Method to reduce an image and tile it in the other three quadrants with
	 * mirrored versions
	 */
	public Picture tileMirror()		
	{
		Pixel[][] pixels = this.getPixels2D();
		Picture tiled = new Picture(pixels.length, pixels[0].length);
		Pixel[][] tiledPixels = tiled.getPixels2D();
		
		tiled.showInUpperLeft(pixels);
		tiled.mirrorVertical();
		tiled.mirrorHorizontal();
		
		return tiled;
	}
	
	/**
	 * Method to paste the pixels in another image into the upper corner of this
	 * picture
	 * Note if the number of rows is odd, then it will include one more than 
	 * the floor of (the number of rows) / 2. Similarly for the number of columns
	 */
	public void showInUpperLeft(Pixel[][] fromPixels)	
	{
		Pixel[][] toPixels = this.getPixels2D();
		Pixel fromPixel = null;
		Pixel toPixel = null;
		
		for (int row = 0; row < fromPixels.length; row += 2)
		{
			for (int col = 0; col < fromPixels[0].length; col += 2)
			{
				fromPixel = fromPixels[row][col];
				toPixel = toPixels[row / 2][col / 2];
				toPixel.setColor(fromPixel.getColor());
			}
		}
	}
  
  /** Method that mirrors the picture around a 
    * vertical mirror in the center of the picture
    * from left to right 
    * (Note if the number of rows is odd, the middle pixel remains untouched
    * */
  public void mirrorVertical()
  {
    Pixel[][] pixels = this.getPixels2D();
    Pixel leftPixel = null;
    Pixel rightPixel = null;
    int width = pixels[0].length;
    for (int row = 0; row < pixels.length; row++)
    {
      for (int col = 0; col < width / 2; col++)
      {
        leftPixel = pixels[row][col];
        rightPixel = pixels[row][width - 1 - col];
        rightPixel.setColor(leftPixel.getColor());
      }
    } 
  }
  
  /** Method that mirrors the picture around a 
    * horizontal mirror in the center of the picture
    * from top to bottom */
  public void mirrorHorizontal()
  {
    Pixel[][] pixels = this.getPixels2D();
    Pixel topPixel = null;
    Pixel bottomPixel = null;
    int length = pixels.length;
    for (int row = 0; row < length / 2; row++)
    {
      for (int col = 0; col < pixels[0].length; col++)
      {
        topPixel = pixels[row][col];
        bottomPixel = pixels[length - 1 - row][col];
        bottomPixel.setColor(topPixel.getColor());
      }
    } 
  }
  
	/** 
	 * Method that detects edges by comparing the color of pixel
	 * with the pixel directly below 
	 * @param threshold The minimum difference between colors for which
	 *   an edge will be detected
	 * @return a picture with detected edges colored black 
	 */
	public Picture edgeDetectionBelow(int threshold)
	{
		// constant colors
		final Color BLACK = new Color(0, 0, 0);
		final Color WHITE = new Color(255, 255, 255);
		
		// this picture
		Pixel topPixel = null;
		Pixel bottomPixel = null;
		Pixel[][] pixels = this.getPixels2D();
		Color bottomColor = null;
		
		// output picture
		Picture edges = new Picture(pixels.length, pixels[0].length);
		Pixel[][] edgesPixels = edges.getPixels2D();
		
		// loop through the pixels
		for (int row = 0; row < pixels.length - 1; row++)
		{
			for (int col = 0; col < pixels[0].length; col++)
			{
				topPixel = pixels[row][col];
				bottomPixel = pixels[row + 1][col];
				bottomColor = bottomPixel.getColor();
				if (topPixel.colorDistance(bottomColor) > threshold)
				{
					edgesPixels[row][col].setColor(BLACK);
				}
				else
				{
					edgesPixels[row][col].setColor(WHITE);
				}
			}
		}
		
		return edges;
	}
	
	/** 
	 * Method that creates a scene using green screen images
	 * @return green screen images overlayed on a background
	 */
	public static Picture greenScreen()
	{
		Picture background = 
			new Picture("GreenScreenCatMouse/"
			+ "IndoorJapeneseRoomBackground.jpg");
		// kitten2
		Picture object1 = new Picture("GreenScreenCatMouse/"
			+ "kitten2GreenScreen.jpg");
		// mouse1
		Picture object2 = new Picture("GreenScreenCatMouse/"
			+ "mouse1GreenScreen.jpg");
		
		background.overlay(object1, 51, 45, 0.1, 10);
		background.overlay(object2, 300, 45, 0.1, 10);
		
		return background;
	}
	
	private void overlay(Picture picture, int upperLeftRow,
		int upperLeftCol, double scale, int threshold)
	{
		final Color GREEN = new Color(53, 204, 51);
		Pixel[][] pixels = this.getPixels2D();
		Pixel[][] picturePixels = picture.getPixels2D();
		int pictureRow = -1;
		int pictureCol = -1;
		Pixel fromPixel = null;
		Color fromColor = null;
		Pixel toPixel = null;
		
		for (int row = upperLeftRow;
			row < upperLeftRow + scale * picturePixels.length; 
			row++)
		{
			for (int col = upperLeftCol; 
				col < upperLeftCol + scale * picturePixels[0].length;
				col++)
			{
				pictureRow = (int) ((row - pictureRow) * (1 / scale));
				pictureCol = (int) ((col - pictureCol) * (1 / scale));
				fromPixel = picturePixels[pictureRow][pictureCol];
				toPixel = pixels[row][col];
				if (fromPixel.colorDistance(GREEN) > threshold)
				{
					fromColor = fromPixel.getColor();
					toPixel.setColor(fromColor);
				}
			}
		}
	}
  
  
  /** Mirror just part of a picture of a temple */
  public void mirrorTemple()
  {
    int mirrorPoint = 276;
    Pixel leftPixel = null;
    Pixel rightPixel = null;
    int count = 0;
    Pixel[][] pixels = this.getPixels2D();
    
    // loop through the rows
    for (int row = 27; row < 97; row++)
    {
      // loop from 13 to just before the mirror point
      for (int col = 13; col < mirrorPoint; col++)
      {
        
        leftPixel = pixels[row][col];      
        rightPixel = pixels[row]                       
                         [mirrorPoint - col + mirrorPoint];
        rightPixel.setColor(leftPixel.getColor());
      }
    }
  }
  
  /** copy from the passed fromPic to the
    * specified startRow and startCol in the
    * current picture
    * @param fromPic the picture to copy from
    * @param startRow the start row to copy to
    * @param startCol the start col to copy to
    */
  public void copy(Picture fromPic, 
                 int startRow, int startCol)
  {
    Pixel fromPixel = null;
    Pixel toPixel = null;
    Pixel[][] toPixels = this.getPixels2D();
    Pixel[][] fromPixels = fromPic.getPixels2D();
    for (int fromRow = 0, toRow = startRow; 
         fromRow < fromPixels.length &&
         toRow < toPixels.length; 
         fromRow++, toRow++)
    {
      for (int fromCol = 0, toCol = startCol; 
           fromCol < fromPixels[0].length &&
           toCol < toPixels[0].length;  
           fromCol++, toCol++)
      {
        fromPixel = fromPixels[fromRow][fromCol];
        toPixel = toPixels[toRow][toCol];
        toPixel.setColor(fromPixel.getColor());
      }
    }   
  }

  /** Method to create a collage of several pictures */
  public void createCollage()
  {
    Picture flower1 = new Picture("flower1.jpg");
    Picture flower2 = new Picture("flower2.jpg");
    this.copy(flower1,0,0);
    this.copy(flower2,100,0);
    this.copy(flower1,200,0);
    Picture flowerNoBlue = new Picture(flower2);
    flowerNoBlue.zeroBlue();
    this.copy(flowerNoBlue,300,0);
    this.copy(flower1,400,0);
    this.copy(flower2,500,0);
    this.mirrorVertical();
    this.write("collage.jpg");
  }
  
  
  /** Method to show large changes in color 
    * @param edgeDist the distance for finding edges
    */
  public void edgeDetection(int edgeDist)
  {
    Pixel leftPixel = null;
    Pixel rightPixel = null;
    Pixel[][] pixels = this.getPixels2D();
    Color rightColor = null;
    for (int row = 0; row < pixels.length; row++)
    {
      for (int col = 0; 
           col < pixels[0].length-1; col++)
      {
        leftPixel = pixels[row][col];
        rightPixel = pixels[row][col+1];
        rightColor = rightPixel.getColor();
        if (leftPixel.colorDistance(rightColor) > 
            edgeDist)
          leftPixel.setColor(Color.BLACK);
        else
          leftPixel.setColor(Color.WHITE);
      }
    }
  }
  
  
  /* Main method for testing - each class in Java can have a main 
   * method 
   */
  public static void main(String[] args) 
  {
    Picture beach = new Picture("images/beach.jpg");
    beach.explore();
    beach.zeroBlue();
    beach.explore();
  }
  
} // this } is the end of class Picture, put all new methods before this
