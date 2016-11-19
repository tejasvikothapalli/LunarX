import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;


/**
 * Represents the information that the player sees displayed on the screen.
 *
 * @author Tejasvi Kothapalli and Andrew Kou
 * @version May 23, 2016
 * @author Period: 3
 * @author APCS Final Project: LunarX
 *
 * @author Sources: None
 */
public class PlayerDisplay
{
    /**
     * The rocket the information refers to
     */
    Rocket rocket;

    /**
     * The game in which the information is displayed
     */
    LunarX lunar;

    /**
     * The number of the player has
     */
    static int points;

    /**
     * The width of the screen.
     */
    private int width;

    /**
     * The height of the screen.
     */
    private int height;


    /**
     * Constructs the PlayerDisplay object. Assigns the rocket and the LunarX
     * game to the fields, and sets the initial point value to 0.
     * 
     * @param r
     *            the rocket the information refers to
     * @param l
     *            the game in which the information is displayed
     */
    public PlayerDisplay( Rocket r, LunarX l )
    {
        points = 0;
        rocket = r;
        lunar = l;
    }


    /**
     * Displays the information about the rocket and the player's points. In
     * addition, the showInfo also sets the font and determines whether or not
     * to decrement the fuel count based on if the player is holding down the
     * key for thrust.
     * 
     * @param g
     *            the Graphics object
     * @param started
     *            the boolean to detect whether the game is in progress or not
     */
    public void showInfo( Graphics g, boolean started )
    {
        if ( g != null )
        {
            g.setColor( Color.WHITE );

            Font f = new Font( "Agency FB", Font.PLAIN, 30 );

            g.setFont( f );

            g.drawString( "POINTS: " + points, 10, 30 );

            if ( lunar.getUpArrow() && rocket.getFuel() != 0 && started )
            {
                rocket.decrementFuel();
            }

            g.drawString( "FUEL: " + rocket.getFuel(), 10, 90 );

            f = new Font( "Agency FB", Font.PLAIN, 30 );
            FontMetrics metrics = g.getFontMetrics( f );

            int x = ( width - metrics.stringWidth( "ALTITUDE: " + ( lunar.getHeight() - rocket.getY() ) ) );
            g.drawString( "ALTITUDE: " + ( lunar.getHeight() - rocket.getY() ), x - 10, 30 );

            x = ( width - metrics.stringWidth( "HORIZONTAL VELOCITY: " + (int)( rocket.getVelocityX() * 20 ) ) );
            g.drawString( "HORIZONTAL VELOCITY: " + (int)( rocket.getVelocityX() * 20 ), x - 10, 60 );

            x = ( width - metrics.stringWidth( "VERTICAL VELOCITY: " + (int)( rocket.getVelocityY() * 20 ) ) );
            g.drawString( "VERTICAL VELOCITY: " + (int)( rocket.getVelocityY() * 20 ), x - 10, 90 );
        }
    }


    /**
     * Increments the player's points by the given value.
     * 
     * @param x
     *            the value to increment the points by
     */
    public static void incrementPoints( int x )
    {
        points += x;
    }


    /**
     * Displays the start menu on the screen.
     * 
     * @param g
     *            the Graphics object
     */
    public void showMenu( Graphics g, int w, int h )
    {
        width = w;
        height = h;
        if ( g != null )
        {
            Font f = new Font( "Agency FB", Font.PLAIN, 30 );
            FontMetrics metrics = g.getFontMetrics( f );
            int x = ( width - metrics.stringWidth( "INSERT COINS" ) ) / 2;
            int y = ( height - metrics.getHeight() ) / 2 + metrics.getAscent();
            // Set the font

            g.setFont( f );
            // Draw the String
            g.drawString( "INSERT COINS", x, y - 300 );
            x = ( width - metrics.stringWidth( "PRESS SPACE BAR TO PLAY" ) ) / 2;
            g.drawString( "PRESS SPACE BAR TO PLAY", x, y - 200 );
            x = ( width - metrics.stringWidth( "ARROW KEYS TO MOVE" ) ) / 2;
            g.drawString( "ARROW KEYS TO MOVE", x, y - 150 );
        }
    }


    /**
     * Displays the game over screen sequence in the center of the screen.
     *
     * @param g
     *            the Graphics object
     */
    public void gameOver( Graphics g, int w, int h )
    {
        width = w;
        height = h;
        Font font = new Font( "Agency FB", Font.PLAIN, 30 );

        g.setFont( font );
        FontMetrics metrics = g.getFontMetrics( font );
        int x = ( width - metrics.stringWidth( "GAMEOVER" ) ) / 2;
        int y = ( height - metrics.getHeight() ) / 2 + metrics.getAscent();
        g.setFont( font );
        // Draw the String
        g.drawString( "GAMEOVER", x, y - 100 );
        x = ( width - metrics.stringWidth( "OUT OF FUEL" ) ) / 2;
        g.drawString( "OUT OF FUEL", x, y - 200 );
        // g.drawString("")
        // x = (width - metrics.stringWidth("GENERATING LANDSCAPE..")) / 2 ;
        // g.drawString( "GENERATING LANDSCAPE...", x, y-100 );
    }
    // public int getFuel()
    // {
    // return fuel ;
    //
    // }


    // For testing purposes

    /**
     * This returns the width
     * 
     * @return width
     */
    public int getWidth()
    {
        return width;
    }


    /**
     * This returns the height
     * 
     * @return height
     */
    public int getHeight()
    {
        return height;
    }
}
// public void time()
// {
// long startTime = System.nanoTime();
// float currentTime = 0 ;
// while (currentTime < 10)
// {
// currentTime = (system.n)
// }
// }

// long globalStartTime = System.nanoTime();
// float currentTime = 0;
// while ( currentTime < 10){
// currentTime = (System.nanoTime() - globalStartTime) / 1000000000f;
// System.out.println(currentTime);
// }
// public void start(Graphics g, boolean spaceBar)
// {
// g.setColor( Color.WHITE );
// g.drawString( "CLICK TO PLAY/nARROW KEYS TO MOVE", lunar.getWidth()/2,
// lunar.getHeight()/2 );
// }
// }
