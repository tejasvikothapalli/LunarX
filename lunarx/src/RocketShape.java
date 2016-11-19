import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;

import javax.swing.*;


/**
 * Represents the shape of the rocket.
 *
 * @author Tejasvi Kothapalli
 * @version May 26, 2016
 * @author Period: 3
 * @author APCS Final Project: LunarX
 *
 * @author Sources: None
 */

public class RocketShape
{
    /**
     * The X and Y coordinates of the rocket
     */
    private int x, y;

    /**
     * The angle that the rocket is alligned with
     */
    private int angleOfRocket = 0;

    /**
     * The number that the rocket is scaled to (the original rocket shape was
     * taken from a Dartmouth sample code)
     */
    private double rocketScaleFactor = 0.2;

    /**
     * The polygon that represents the rocket
     */
    private Polygon rocketPolygon;

    /**
     * The X coordinates of the rocket parts
     */
    private int[] xRocket = { 30, 50, 50, 60, 60, 0, 0, 10, 10 }; // -70

    /**
     * The Y coordinates of the rocket parts
     */
    private int[] yRocket = { 0, 25, 100, 110, 135, 135, 110, 100, 25 }; // -315

    /**
     * The original X coordinates of the rocket flame parts
     */
    private int[] xFlameOrig = { 0, 0, 5, 10, 20, 30, 40, 45, 50, 60, 60 }; // -70

    /**
     * The current X coordinates of the rocket flame parts
     */
    private int[] xFlame = new int[xFlameOrig.length];

    /**
     * The current Y coordinates of the rocket flame parts
     */
    private int[] yFlame = { 140, 155, 150, 175, 155, 160, 145, 170, 145, 160, 140 }; // -455

    private Point[] points;


    /**
     * Constructs the rocket shape.
     * 
     * @param x
     *            the X value of the starting coordinate of the rocket
     * @param y
     *            the Y value of the stating coordinate of the rocket
     */
    public RocketShape( int x, int y )
    {
        this.x = x;
        this.y = y;

        rocketPolygon = new Polygon( xRocket, yRocket, xRocket.length );
        setRocketPolygon( x, y );

    }


    /**
     * Draws the rocket by filling in the shape drawn by the X and Y coordinates
     * 
     * @param gr
     *            the Graphics object
     * @param thrusters
     *            the boolean that checks if the thrusters are ignited (the up
     *            arrow is being pressed)
     * @param rightArrow
     *            the boolean that checks if the rocket needs to rotate to the
     *            right (the right arrow key is being pressed)
     * @param leftArrow
     *            the boolean that checks if the rocket needs to rotate to the
     *            left (the left arrow key is being pressed)
     * @return the new angle of the rocket
     */
    public int drawFilled( Graphics gr, boolean thrusters, boolean rightArrow, boolean leftArrow )
    {

        if ( rightArrow && angleOfRocket != -90 )
        {
            angleOfRocket -= 10;

        }
        else if ( leftArrow && angleOfRocket != 90 )
        {
            angleOfRocket += 10;
        }
        if ( gr != null )
        {
            Graphics2D g2d = (Graphics2D)gr;
            AffineTransform old = g2d.getTransform();

            g2d.rotate( Math.toRadians( angleOfRocket ), getXToRotateAbout(), getYToRotateAbout() );

            gr.setColor( Color.WHITE );
            gr.drawPolygon( shiftArrayByConstant( xRocket, x ), shiftArrayByConstant( yRocket, y ), xRocket.length );

            if ( thrusters )
            {
                xFlame[0] = xFlameOrig[0];
                xFlame[xFlame.length - 1] = xFlameOrig[xFlameOrig.length - 1];
                for ( int i = 1; i < xFlame.length - 1; i++ )
                    xFlame[i] = xFlameOrig[i] + ( (int)( Math.random() * 6.0 ) ) - 3;

                gr.setColor( Color.WHITE );
                gr.drawPolyline( shiftArrayByConstant( xFlame, x ), shiftArrayByConstant( yFlame, y ), xFlame.length );
            }

            g2d.setTransform( old );
        }

        return angleOfRocket;
    }


    /**
     * Sets the current rocket polygon coordinates by shifting it by the current
     * X and Y coordinates.
     * 
     * @param xVal
     *            the current X value coordinate position of the rocket
     * @param yVal
     *            the current Y value coordinate position of the rocket
     */
    public void setRocketPolygon( int xVal, int yVal )
    {

        int[] rotatedX = new int[xRocket.length];
        int[] rotatedY = new int[xRocket.length];

        rotateXandYCoordinates( shiftArrayByConstant( xRocket, xVal ),
            shiftArrayByConstant( yRocket, yVal ),
            rotatedX,
            rotatedY );

        rocketPolygon = new Polygon( rotatedX, rotatedY, rotatedX.length );

    }


    public void rotateXandYCoordinates( int[] origX, int[] origY, int[] storeToX, int[] storeToY )
    {
        for ( int i = 0; i < origX.length; i++ )
        {
            Point2D result = new Point();
            AffineTransform
                .getRotateInstance( Math.toRadians( angleOfRocket ), getXToRotateAbout(), getYToRotateAbout() )
                .transform( new Point( origX[i], origY[i] ), result );

            storeToX[i] = (int)result.getX();
            storeToY[i] = (int)result.getY();
        }
    }


    /**
     * Shifts the array by a fixed number.
     * 
     * @param arr
     *            the array
     * @param constant
     *            the number to shift the elements by
     * @return the shifted array
     */
    public int[] shiftArrayByConstant( int[] arr, int constant )
    {
        int[] array = new int[arr.length];

        for ( int i = 0; i < arr.length; i++ )
        {
            array[i] = (int)( rocketScaleFactor * arr[i] + constant );
        }

        return array;
    }


    /**
     * Changes the center of the circle to a new X and Y.
     * 
     * @param newX
     *            the new X coordinate of the center
     * @param newY
     *            the new Y coordinate of the center
     */
    public void setPosition( int newX, int newY )
    {
        x = newX;
        y = newY;
    }


    /**
     * Resets the the position of the rocket and sets the angle of the rocket to
     * zero.
     */
    public void resetPos()
    {
        x = 100;
        y = 100;
        angleOfRocket = 0;
    }


    // Getters

    /**
     * Returns the polygon of the rocket shape
     * 
     * @return the polygon
     */
    public Polygon getRocketPolygon()
    {
        return rocketPolygon;
    }


    /**
     * Returns the angle of the rocket.
     * 
     * @return the angle of the rocket
     */
    public int getAngleOfRocket()
    {
        return angleOfRocket;
    }


    /**
     * Gets the X value to which the rocket will rotate around.
     * 
     * @return the X value
     */
    public int getXToRotateAbout()
    {
        return (int)( rocketScaleFactor * ( xRocket[5] + xRocket[4] ) / 2 + x );
    }


    /**
     * Gets the Y value to which the rocket will rotate around.
     * 
     * @return the Y value
     */
    public int getYToRotateAbout()
    {
        return (int)( rocketScaleFactor * ( yRocket[0] + yRocket[4] ) / 2 + y );
    }


    /**
     * Gets the X value of the coordinate of the rocket.
     * 
     * @return the X value
     */
    public int getX()
    {
        return x;
    }


    /**
     * Gets the Y value of the coordinate of the rocket.
     * 
     * @return the Y value
     */
    public int getY()
    {
        return y;
    }


    /**
     * Gets the height of the rocket.
     * 
     * @return the height
     */
    public int getHeightOfRocket()
    {
        return (int)( rocketScaleFactor * ( yRocket[0] + yRocket[4] ) );

    }


    /**
     * Returns the scale factor to which the rocket is scaled to.
     * 
     * @return the scale factor
     */
    public double getRocketFactor()
    {
        return rocketScaleFactor;
    }


    /**
     * Gets the width of the rocket.
     * 
     * @return the width
     */
    public int getWidthOfRocket()
    {
        return (int)( rocketScaleFactor * ( xRocket[5] + xRocket[4] ) );
    }


    /**
     * Return an array of the X coordinates of the rocket.
     * 
     * @return the array of X coordinates
     */
    public int[] getXCoords()
    {
        return xRocket;
    }


    /**
     * Returns an array of the Y coordinates of the rocket.
     * 
     * @return the array of Y coordinates
     */
    public int[] getYCoords()
    {
        return yRocket;
    }

}
