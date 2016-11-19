/**
 *  Represents a single landing point in the terrain. 
 *
 *  @author  Andrew Kou
 *  @version May 25, 2016
 *  @author  Period: 3
 *  @author  APCS Final Project: LunarX
 *
 *  @author  Sources: none
 */
public class LandingPoint
{
    /**
     * The length of the landing point
     */
    private int length;

    /**
     * The first X coordinate of the linear landing point
     */
    private int startX;

    /**
     * The first Y coordinate of the linear landing point
     */
    private int startY;

    /**
     * The second X coordinate of the linear landing point
     */
    private int endX;

    /**
     * The second Y coordinate of the linear landing point
     */
    private int endY;

    /**
     * The number of points that landing point is worth if a rocket lands on it
     */
    private int points;


    /**
     * Constructs the landing point object.
     * 
     * @param x
     *            the starting X coordinate
     * @param y
     *            the starting Y coordinate
     * @param l
     *            the length of the landing point
     */
    public LandingPoint( int x, int y, int l )
    {
        startX = x;
        startY = y;
        endY = y;
        length = l;
        
        endX = startX + l;
        points = 100 - l;
    }


    // Getters
    /**
     * Gets the number of points that the landing point is worth if landed on.
     * 
     * @return the number of points
     */
    public int getPoints()
    {
        return points;
    }


    /**
     * Gets the length of the landing point.
     * 
     * @return the length of the landing point
     */
    public int getLength()
    {
        return length;
    }


    /**
     * Gets the first X coordinate of the landing point.
     * 
     * @return the first X coordinate of the landing point
     */
    public int getStartX()
    {
        return startX;
    }


    /**
     * Gets the first Y coordinate of the landing point.
     * 
     * @return the first Y coordinate of the landing point
     */
    public int getStartY()
    {
        return startY;
    }


    /**
     * Gets the second X coordinate of the landing point
     * 
     * @return the second X coordinate of the landing point
     */
    public int getEndX()
    {
        return endX;
    }


    /**
     * Gets the second Y coordinate of the landing point
     * 
     * @return the second Y coordinate of the landing point
     */
    public int getEndY()
    {
        return getStartY();
    }


    // Setters
    /**
     * Sets the first Y coordinate of the landing point.
     * 
     * @param y
     *            the Y coordinate value to be set
     */
    public void setStartY( int y )
    {
        startY = y;
    }
}
