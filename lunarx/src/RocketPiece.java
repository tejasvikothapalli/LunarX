import java.awt.*;


/**
 * Represents a single component of the drawn rocket.
 *
 * @author Andrew Kou
 * @version May 25, 2016
 * @author Period: 3
 * @author APCS Final Project: LunarX
 *
 * @author Sources: None
 */

public class RocketPiece
{
    /**
     * The original starting X coordinate
     */
    int originalX1;

    /**
     * The original starting Y coordinate
     */
    int originalY1;

    /**
     * The original ending X coordinate
     */
    int originalX2;

    /**
     * The original ending Y coordinate
     */
    int originalY2;

    /**
     * The first X coordinate of the linear rocket piece
     */
    int startX;

    /**
     * The first Y coordinate of the linear rocket piece
     */
    int startY;

    /**
     * The second X coordinate of the linear rocket piece
     */
    int endX;

    /**
     * The second Y coordinate of the linear rocket piece
     */
    int endY;

    /**
     * How fast the rocket piece travels from across the X-axis
     */
    int velocityX;

    /**
     * How fast the rocket piece travels across the Y-axis
     */
    int velocityY;


    /**
     * Constructs the RocketPiece object. Assigns the x and y coordinate
     * parameters to the corresponding coordinate fields, and assings random X
     * and Y velocities that are not zero.
     * 
     * @param x1
     *            the first X coordinate of the linear rocket piece
     * @param y1
     *            the first Y coordinate of the linear rocket piece
     * @param x2
     *            the second X coordinate of the linear rocket piece
     * @param y2
     *            the second Y coordinate of the linear rocket piece
     */
    public RocketPiece( int x1, int y1, int x2, int y2 )
    {
        originalX1 = x1;
        originalY1 = y1;
        originalX2 = x2;
        originalY2 = y2;
        startX = x1;
        startY = y1;
        endX = x2;
        endY = y2;
        velocityX = (int)( Math.random() * 5 ) - (int)( Math.random() * 7 );
        velocityY = (int)( Math.random() * 5 ) - (int)( Math.random() * 7 );
        if ( velocityX == 0 )
        {
            velocityX += 2;
        }
        if ( velocityY == 0 )
        {
            velocityY += 2;
        }
    }


    /**
     * Draws the rocket piece as the rocket explodes. The piece floats across
     * the screen according to the X and Y velocities.
     * 
     * @param g
     *            the Graphics object
     */
    public void draw( Graphics g )
    {
        g.setColor( Color.WHITE );
        g.drawLine( startX, startY, endX, endY );
        startX += velocityX;
        startY += velocityY;
        endX += velocityX;
        endY += velocityY;
    }


    /**
     * Sets the first and second X and Y coordinates to the location where the
     * rocket collides with the terrain.
     * 
     * @param x
     *            the X coordinate of the collision of the rocket with the
     *            terrain
     * @param y
     *            the Y coordinate of the collision of the rocket with the
     *            terrain
     */
    public void setCrashCoords( int x, int y )
    {
        startX += x;
        startY += y;
        endX += x;
        endY += y;
    }


    /**
     * Resets the first and second X and Y coordinates so they can be set to a
     * different rocket-terrain collision coordinate
     */
    public void resetCrashCoords()
    {
        startX = originalX1;
        startY = originalY1;
        endX = originalX2;
        endY = originalY2;
    }


    /**
     * Returns a RocketPiece object in the form of a string, displaying
     * information about its coordinates and its X and Y velocities.
     * 
     * @return the String corresponding to this RocketPiece
     */
    public String toStr()
    {
        return "Line from (" + startX + ", " + startY + ") to (" + endX + ", " + endY + ") moving at a X-velocity of "
            + velocityX + " and a Y-velocity of " + velocityY;
    }
}
