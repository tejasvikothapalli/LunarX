import static org.junit.Assert.assertEquals;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.PathIterator;
import java.util.ArrayList;

import org.junit.Test;


/**
 * 
 * This class handles all the aspects related to the zooming and shifting of the
 * GUI
 *
 * @author Tejasvi Kothapalli
 * @version May 29, 2016
 * @author Period: 3
 *
 * @author Sources: none
 */
public class Zoomer
{
    /**
     * This saves the old view of the screen so that once the zoom is done it
     * will revert to normal.
     */
    static AffineTransform old;

    /**
     * This is the Graphics2D object that helps perform more complex tasks that
     * are not available in the Graphics object.
     */
    static Graphics2D g2d;


    /**
     * This zooms in a section of the screen based on the rocket location.
     * 
     * @param rocket
     *            The rocket within the GUI
     * @param g
     *            the graphics object
     * @param l
     *            the LunarX object for width and height
     * @param land
     *            the landscape
     */
    public static void zoom( Rocket rocket, Graphics g, LunarX l, Landscape land )
    {
        int zoneWidth = land.getZoneWidth();
        g2d = (Graphics2D)g;
        int width = l.getWidth();
        int height = l.getHeight();
        old = g2d.getTransform();

        if ( 0 - width / ( zoneWidth ) * height / width
            * ( ( ( rocket.getX() + 0.0 ) - width / 5 ) / ( zoneWidth ) * ( zoneWidth ) ) > 0 )
        {
            g2d.translate( 0, -width / ( zoneWidth ) * height / width * ( ( rocket.getY() + 0.0 ) - height / 5 ) );
        }
        else
        {
            g2d.translate(
                -width / ( zoneWidth ) * height / width
                    * ( ( ( rocket.getX() + 0.0 ) - width / 5 ) / ( zoneWidth ) * ( zoneWidth ) ),
                -width / ( zoneWidth ) * height / width * ( ( rocket.getY() + 0.0 ) - height / 5 ) );
        }

        g2d.scale( width / ( zoneWidth ) * height / width, width / ( zoneWidth ) * height / width );
    }


    /**
     * This allows for a certain thing on the GUI to rotate in the same direct
     * as the rockets rotation.
     * 
     * @param rocket
     *            the rocket
     */
    public static void rotateAboutRocket( Rocket rocket, Graphics g )
    {
        g2d = (Graphics2D)g;
        g2d.rotate( Math.toRadians( rocket.getAngleOfRocket() ),
            rocket.getXToRotateAbout(),
            rocket.getYToRotateAbout() );
    }


    /**
     * This method reverts to the old transform.
     */
    public static void revertToNormal()
    {
        g2d.setTransform( old );
    }

}
