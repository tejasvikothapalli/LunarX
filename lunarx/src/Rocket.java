import java.awt.*;
import java.awt.geom.Area;
import java.util.*;


/**
 * Represents the rocket object of the game, specifically the movement and
 * direction controls.
 *
 * @author Tejasvi Kothapalli
 * @version May 27, 2016
 * @author Period: 3
 * @author APCS Final Project: LunarX
 *
 * @author Sources: None
 */
public class Rocket extends RocketShape
{
    /**
     * The rockets X coordinate velocity
     */
    private double velocityX;

    /**
     * The rockets Y coordinate velocity
     */
    private double velocityY;

    /**
     * The resistance the rocket encounters as it falls
     */
    private double airResistance;

    /**
     * The gravity of the rocket (how fast it falls)
     */
    private double gravity;

    /**
     * The rocket thruster constant.
     */
    private double upArrow;

    /**
     * The angle of the rocket
     */
    private int angleOfRocket;

    /**
     * The number of points the player controlling the rocket has
     */
    private int points;

    /**
     * A list of the individual lines that compromise the rocket
     */
    private LinkedList<RocketPiece> rocketPieces;

    /**
     * A boolean that contains the value true if the rocket is currently
     * crashed, and false if it is not currently crashed
     */
    private boolean crash = false;

    /**
     * The X value of the coordinate of the crash point
     */
    private int crashX;

    /**
     * The Y value of the coordinate of the crash point
     */
    private int crashY;

    /**
     * The number of times that the rocket has crashed in a single game
     */
    private int crashNum = 0;

    /**
     * The amount of fuel the rocket has
     */
    public int fuel = 500;

    /**
     * The number to scale the rocket by (the original rocket was taken from a
     * Dartmouth sample program and is too large for our purposes)
     */
    double rocketScaleFactor = 0.2;


    /**
     * Constructs a rocket object.
     * 
     * @param centerX
     *            the X value of the coordinate of the center rocket
     * @param centerY
     *            the Y value of the coordinate of the center of the rocket
     */
    public Rocket( int centerX, int centerY )
    {
        super( centerX, centerY );

        velocityX = 2;
        velocityY = 1;

        gravity = 0.09;
        airResistance = 0.001;

        upArrow = 0.3;

        points = 0;

        int[] coordsX = getXCoords();

        int[] coordsY = getYCoords();

        rocketPieces = new LinkedList<RocketPiece>();
        for ( int i = 0; i < coordsX.length - 1; i++ )
        {

            rocketPieces.add( new RocketPiece( (int)( rocketScaleFactor * coordsX[i] ),
                (int)( rocketScaleFactor * coordsY[i] ),
                (int)( rocketScaleFactor * coordsX[i + 1] ),
                (int)( rocketScaleFactor * coordsY[i + 1] ) ) );
        }
        rocketPieces.add( new RocketPiece( (int)( rocketScaleFactor * coordsX[coordsX.length - 1] ),
            (int)( rocketScaleFactor * coordsY[coordsY.length - 1] ),
            (int)( rocketScaleFactor * coordsX[0] ),
            (int)( rocketScaleFactor * coordsY[0] ) ) );
    }


    /**
     * Draws the rocket and adjusts its position based on the key strokes
     * entered if the rocket has not landed yet.
     * 
     * @param g
     *            the Graphics object
     * @param width
     *            the width of the game window
     * @param height
     *            the height of the game window
     * @param thruster
     *            true if the thruster is ignited (up arrow key being pressed)
     * @param rightArrow
     *            true if the right arrow key is being pressed
     * @param leftArrow
     *            true if the left arrow key is being pressed
     * @param l
     *            the LunarX game the rocket is in
     * @param landed
     *            true if the rocket is landed
     * @param started
     *            true if the game has already been started
     * @return true if a change in the rocket in the frame has been made
     */
    public boolean doFrame(
        Graphics g,
        int width,
        int height,
        boolean thruster,
        boolean rightArrow,
        boolean leftArrow,
        LunarX l,
        boolean landed,
        boolean started )
    {
        if ( fuel == 0 )
        {
            thruster = false;
        }
        if ( !started )
        {
            return false;
        }
        angleOfRocket = drawFilled( g, thruster, rightArrow, leftArrow );
        if ( !landed )
        {
            int newX = (int)( findPositionX( thruster ) );
            int newY = (int)( findPositionY( thruster ) );

            setPosition( newX, newY );

            setRocketPolygon( newX, newY );

            velocityY = findVelocityY( thruster );

            velocityX = findVelocityX( thruster );
        }
        return true;
    }


    /**
     * Finds the X coordinate of the position of the rocket.
     * 
     * @param thruster
     *            true if the thruster is ignited (up arrow key being pressed)
     * @return the X coordinate
     */
    public double findPositionX( boolean thruster )
    {
        return getX() + velocityX + 1.0 / 2.0 * findAccelerationX( thruster );
    }


    /**
     * Finds the Y coordinate of the position of the rocket
     * 
     * @param thruster
     *            true if the thruster is ignited (up arrow key being pressed)
     * @return the Y coordinate
     */
    public double findPositionY( boolean thruster )
    {
        return getY() + velocityY + 1.0 / 2.0 * findAccelerationY( thruster );
    }


    /**
     * Gets the X velocity of the rocket.
     * 
     * @param thruster
     *            true if the thruster is ignited (up arrow key being pressed)
     * @return the X velocity
     */
    public double findVelocityX( boolean thruster )
    {
        return velocityX + findAccelerationX( thruster );
    }


    /**
     * Gets the Y velocity of the rocket.
     * 
     * @param thruster
     *            true if the thruster is ignited (up arrow key being pressed)
     * @return the Y velocity
     */
    public double findVelocityY( boolean thruster )
    {
        return velocityY + findAccelerationY( thruster );
    }


    /**
     * Gets the X acceleration value of the rocket.
     * 
     * @param thruster
     *            true if the thruster is ignited (up arrow key being pressed)
     * @return X acceleration value
     */
    public double findAccelerationX( boolean thruster )
    {
        if ( thruster )
        {
            if ( velocityX < 0 )
            {
                return upArrow * Math.sin( Math.toRadians( angleOfRocket ) ) + airResistance;
            }
            else
            {
                return upArrow * Math.sin( Math.toRadians( angleOfRocket ) ) - airResistance;
            }
        }
        if ( velocityX > 0 )
        {
            return -airResistance;
        }
        else
        {
            return airResistance;
        }
    }


    /**
     * Gets the Y acceleration value of the rocket.
     * 
     * @param thruster
     *            true if the thruster is ignited (up arrow key being pressed)
     * @return the Y acceleration value
     */
    public double findAccelerationY( boolean thruster )
    {
        if ( thruster )
        {
            return gravity - upArrow * Math.cos( Math.toRadians( angleOfRocket ) );
        }

        return gravity;
    }


    /**
     * Gets the amount of points the player controlling the rocket has in this
     * game.
     * 
     * @return the amount of points
     */
    public int getPoints()
    {
        return points;
    }


    /**
     * Explodes the rocket into multiple pieces when it is unable to land safely
     * or collides with the terrain which is not a landing platform.
     * 
     * @param g
     *            the Graphics object
     * @param lunar
     *            the LunarX game this rocket is in
     */
    public void explode( Graphics g, LunarX lunar )
    {
        if ( crash == true )
        {
            for ( RocketPiece p : rocketPieces )
            {
                p.setCrashCoords( crashX, crashY );
            }
        }
        crash = false;
        crashNum = 1;
        for ( RocketPiece p : rocketPieces )
        {
            p.draw( g );
        }
    }


    /**
     * Returns 1 if the rocket has already collided with the surface in this
     * flight; return 0 otherwise (for purposes of setting the crash coordinates
     * correctly)
     * 
     * @return Returns 1 if the rocket has already collided with the surface in
     *         this flight; return 0 otherwise
     */
    public int getCrashNum()
    {
        return crashNum;
    }


    /**
     * Checks to see if the rocket should land.
     * 
     * @param land
     *            landscape to check to see if the rockt has landed on
     * @param g
     *            the Graphics object
     * @param l
     *            the LunarX game this rocket is in
     * @return 0 to keep animating, 1 to show rocket has won, 2 if the rocket
     *         should exploding
     */
    public int checkForLanding( Landscape land)
    {
        int[] x = { 0, land.getWidth(), land.getWidth(), 0 };
        int[] y = { 0, 0, land.getMaxHeight()  + 1, land.getMaxHeight() + 1 };
        Polygon screen = new Polygon( x, y, x.length );

        Area areaOfScreen = new Area( screen );

        Area area = new Area( getRocketPolygon() );
        Area area2 = new Area( getRocketPolygon() );

        areaOfScreen.intersect( area2 );

        area.intersect( new Area( land.getLandscapePolygon() ) );
        if ( !area.isEmpty() || areaOfScreen.isEmpty() )
        {
            if ( angleOfRocket == 0 && getX() >= land.getLandingPoints().get( getX() / land.getZoneWidth() ).getStartX()
                && getX() + getWidthOfRocket() <= land.getLandingPoints().get( getX() / land.getZoneWidth() ).getEndX()
                && getVelocityY() * 20 < 60 )
            {
                return 1;
            }
            else if ( crashNum == 0 )
            {
                crash = true;
                crashX = getX();
                crashY = getY();
            }
            return 2;
        }
        return 0;
    }


    /**
     * Decrements the fuel by a single fuel unit if the rocket is not crashed.
     */
    public void decrementFuel()
    {
        if ( !crash )
            fuel--;
    }


    /**
     * Gets the current amount of fuel.
     * 
     * @return the amount of fuel
     */
    public int getFuel()
    {
        return fuel;
    }


    /**
     * Gets the current velocity of the X value of the rocket.
     * 
     * @return the X velocity
     */
    public double getVelocityX()
    {
        return velocityX;
    }


    /**
     * Gets the current velocity of the Y value of the rocket.
     * 
     * @return the Y velocity
     */
    public double getVelocityY()
    {
        return velocityY;
    }


    /**
     * Stops the rocket in its current position.
     */
    public void stopRocket()
    {
        velocityX = 0;
        velocityY = 0;
    }


    /**
     * Resets the rocket to its position at the start of the game and also
     * resets its velocity to the original velocity. Also resets the rocket
     * piece coordinates to the original position of the rocket.
     */
    public void reset()
    {
        velocityX = 2;
        velocityY = 1;

        setPosition( 100, 100 );

        angleOfRocket = 0;
        crash = false;
        crashNum = 0;
        for ( RocketPiece p : rocketPieces )
        {
            p.resetCrashCoords();
        }
    }

}
