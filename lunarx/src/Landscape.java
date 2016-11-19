import java.awt.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.util.*;
import javax.swing.*;


/**
 * 
 * Represents the randomly generated landscape of the LunarX game.
 *
 * @author Andrew Kou and Tejasvi Kothapalli
 * @version May 23, 2016
 * @author Period: 3
 * @author APCS Final Project: LunarX
 *
 * @author Sources: None
 */
public class Landscape
{
    /**
     * The width of the landscape
     */
    int width;

    /**
     * The set maximum height for the terrain
     */
    int maxHeight;

    /**
     * The set minimum height for the terrain
     */
    int minHeight;

    /**
     * The step size for each portion of the terrain
     */
    int stepChange;

    /**
     * The maximum step change for each portion of the terrain
     */
    int stepMax;

    /**
     * The width of the zone where a single landing platform exists
     */
    int zoneWidth;

    /**
     * A map with the Integer of the X-coordinate of where the landing platforms
     * start corresponding the LandPoint objects
     */
    Map<Integer, LandingPoint> landingZones;



    /**
     * This is the polygon representation of the landscape
     */
    Polygon shape;

    /**
     * The Y coordinates of the drawn terrain
     */
    int[] yCoords;

    /**
     * A list of consecutive list of numbers from 0 to width - 1 to represent
     * the x values of the terrain.
     */
    int[] xCoords;

    /**
     * A double representation of the x values
     */
    double[] xValues;

    /**
     * A double representation of the y values
     */
    double[] yValues;


    /**
     * Initializes the fields and assigns randomly generated coordinates that
     * are continuous and have flat areas for the landing point.
     * 
     * @param w
     *            the width
     * @param max
     *            the max height
     * @param min
     *            the min height
     * @param sMax
     *            the sMax
     * @param change
     *            the change
     * @param numberOfZones
     *            the number of landing zones
     * 
     */
    public Landscape( int w, int max, int min, int sMax, int change, int numberOfZones )
    {


        width = w;
        maxHeight = max;
        minHeight = min;
        stepChange = change;
        stepMax = sMax;
        yCoords = new int[width + 2];

        zoneWidth = width / numberOfZones;

        int x = 0;

        landingZones = new HashMap<Integer, LandingPoint>( numberOfZones );
        int numberStarter = 0;

        while ( x < width )
        {
            int startX = (int)( ( Math.random() * zoneWidth / 2 ) + x );

            int length = (int)( Math.random() * ( x + zoneWidth - startX ) );

            if ( length < 30 )
            {
                length = (int)( Math.random() * 70 ) + 30;
            }
            if ( length > 80 )
            {
                length = (int)( Math.random() * 50 ) + 30;
            }

            landingZones.put( numberStarter++, new LandingPoint( startX, minHeight, length ) );

            x = x + zoneWidth;

        }

        numberStarter = 0;

        double height = Math.random() * maxHeight;
        double slope = ( Math.random() * stepMax ) * 2 - stepMax;

        for ( int i = 0; i < width; i++ )
        {

            height += slope;
            slope += ( Math.random() * stepChange ) * 2 - stepChange;

            if ( slope > stepMax )
            {
                slope = stepMax;
            }

            if ( slope < -stepMax )
            {
                slope = -stepMax;
            }

            if ( height > maxHeight )
            {
                height = maxHeight;
                slope *= -1;
            }

            if ( height < 0 )
            {
                height = 0;
                slope *= -1;
            }

            if ( height < 0 )
            {
                height = 0;
                slope *= -1;
            }

            if ( height < minHeight )
            {
                height = minHeight;
                slope *= -1;
            }

            yCoords[i] = (int)height;

            if ( numberStarter < landingZones.size() && i == landingZones.get( numberStarter ).getStartX() )
            {

                landingZones.get( numberStarter ).setStartY( yCoords[i] );

                int l = 0;

                while ( i < width && l < landingZones.get( numberStarter ).getLength() )
                {
                    i++;
                    l++;
                    if ( i < width )
                    {

                        yCoords[i] = (int)height;
                    }
                }
                numberStarter++;
            }
        }
        int[] xCoordinates = new int[w + 2];
        for ( int i = 0; i < xCoordinates.length - 2; i++ )
        {
            xCoordinates[i] = i;
        }
        xCoordinates[xCoordinates.length - 2] = w - 1;
        xCoordinates[xCoordinates.length - 1] = 0;

        xCoords = xCoordinates;

        yCoords[yCoords.length - 2] = max + 1;
        yCoords[yCoords.length - 1] = max + 1;

        shape = new Polygon( xCoordinates, yCoords, width + 2 );

        xValues = new double[xCoordinates.length];
        yValues = new double[yCoords.length];
        for ( int i = 0; i < xCoordinates.length; i++ )
        {
            xValues[i] = xCoords[i];
            yValues[i] = yCoords[i];
        }

    }


    /**
     * Draws the landscape using the xCoords and yCoords.
     * 
     * @param g
     *            the Graphics object
     * @param start
     *            the X-coordinate of the starting point of where to draw the
     *            terrain
     * @param end
     *            the X-coordinate of the ending point of where to draw the
     *            terrain
     */
    public void drawLandscape( Graphics g )
    {

        g.setColor( Color.white );
        g.drawPolyline( xCoords, yCoords, width );

        for ( int i = 0; i < landingZones.size(); i++ )
        {
            g.setColor( Color.blue );
            g.drawLine( landingZones.get( i ).getStartX(),
                landingZones.get( i ).getStartY(),
                landingZones.get( i ).getEndX(),
                landingZones.get( i ).getEndY() );
        }

    }


    /**
     * This places the point values on the GUI
     * 
     * @param g
     *            the graphics object
     * @param frameNums
     *            the frame number
     */
    public void putPointValues( Graphics g, int frameNums )
    {
        g.setColor( Color.WHITE );
        for ( int k = 0; k < landingZones.size(); k++ )
        {
            g.setColor( Color.WHITE );
            LandingPoint zone = landingZones.get( k );
            int mid = ( zone.getStartX() + zone.getEndX() ) / 2;
            if ( frameNums % 20 == 0 || frameNums % 20 == 1 || frameNums % 20 == 2 )
            {
                g.drawString( zone.getPoints() + "", mid - 12, zone.getStartY() + 35 );
            }
        }
    }


    // Getters
    /**
     * Gets the coordinates of the landscape terrain.
     * 
     * @return the an array containing the coordinates of the terrain
     */
    public int[] getCoordinates()
    {
        return yCoords;
    }


    /**
     * Gets the width of the landscape.
     * 
     * @return the width of the landscape.
     */
    public int getWidth()
    {
        return width;
    }


    /**
     * Gets the minimum height of any point in the terrain of the landscape.
     * 
     * @return the minimum height of the terrain
     */
    public int getMinHeight()
    {
        return minHeight;
    }


    /**
     * Gets the maximum height of any point in the terrain of the landscape.
     * 
     * @return the maximum height of the terrain
     */
    public int getMaxHeight()
    {
        return maxHeight;
    }


    /**
     * Gets the landscape polygon shape.
     * 
     * @return the shape of the landscape
     */
    public Polygon getLandscapePolygon()
    {
        return shape;
    }


    /**
     * Gets the map in which the X-coordinate of the landing platforms
     * corresponds to the LandingPoint object.
     * 
     * @return the map containing the LandingPoints
     */
    public Map<Integer, LandingPoint> getLandingPoints()
    {
        return landingZones;
    }


    /**
     * Gets the width of the zone in which a single landing platform exists.
     * 
     * @return the width of the zone
     */
    public int getZoneWidth()
    {
        return zoneWidth;
    }


    /**
     * This returns the double representation of the x values
     * 
     * @return x values
     */
    public double[] getXDoubleValues()
    {
        return xValues;
    }


    /**
     * This returns the double representation of the y values
     * 
     * @return y values
     */
    public double[] getYDoubleValues()
    {
        return yValues;
    }

}
