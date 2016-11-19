import java.awt.*;


/**
 * Represents the randomly generated stars in the landscape.
 *
 * @author Andrew Kou
 * @version May 23, 2016
 * @author Period: 3
 * @author APCS Final Project: LunarX
 *
 * @author Sources: None
 */
public class Stars
{
    /**
     * The coordinates of the stars
     */
    int[][] coords;

    /**
     * The landscape on which the stars will be drawn
     */
    Landscape landscape;


    /**
     * Constructs the star object. It initializes the 2D array for which the
     * coordinates will be drawn and landscape on which the stars will be drawn.
     * It randomly generates X and Y coordinates for the stars. If the randomly
     * generated Y coordinate ends up below the landscape, it subtracts a random
     * amount such that the star is no longer "in" the terrain.
     * 
     * @param numStars
     *            the number of stars to be made
     * @param land
     *            the landscape on which the stars will be drawn
     */
    public Stars( int numStars, Landscape land )
    {
        landscape = land;
        coords = new int[numStars][2];
        int[] landCoords = land.getCoordinates();
        for ( int i = 0; i < numStars; i++ )
        {
            int x = (int)( Math.random() * landscape.getWidth() );
            int y = (int)( Math.random() * landscape.getMaxHeight() );
            if ( y > landCoords[x] )
            {
                y -= (int)( Math.random() * 100 ) + 10;
            }
            else if (y==0)
            {
                y = 100 ;
            }
            else
            {
                coords[i][1] = y;
            }
            
            if ( x == landscape.getWidth() )
            {
                coords[i][0] = x - 2;
            }
            if ( x == 0 )
            {
                x += 10;
            }
            else
            {
                coords[i][0] = x;
            }
        }
    }


    /**
     * Draws the circular stars on the landscape using the given coordinates.
     * 
     * @param g
     *            the graphics object
     */
    public void drawStars( Graphics g )
    {
        g.setColor( Color.WHITE );
        for ( int i = 0; i < coords.length; i++ )
        {
            g.drawOval( coords[i][0], coords[i][1], 2, 2 );
        }
    }


    /**
     * The toString method of the Stars class.
     * 
     * @return the string representation of stars
     */
    public String toStr()
    {
        return "Number of Stars: " + coords.length;
    }
}
