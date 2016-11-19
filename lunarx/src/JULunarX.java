import java.awt.Graphics;
import java.awt.Polygon;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.regex.*;

import org.junit.*;

import static org.junit.Assert.*;
import junit.framework.JUnit4TestAdapter;


/**
 * This is the JUnit testing for the game.
 *
 * @author Tejasvi Kothapalli and Andrew Kou
 * @version May 30, 2016
 * @author Period: 3
 * @author Assignment: LunarX
 *
 * @author Sources: none
 */
public class JULunarX
{
    // Test for Rocket
    Rocket r;

    int initialX = 100;

    int initialY = 100;

    int widthOfWindow = 400;

    int heightOfWindow = 200;


    @Test
    public void rocketConstructor()
    {
        r = new Rocket( initialX, initialY );

        assertEquals( "<< Rocket: x should be " + r.getX() + " >>", initialX, r.getX() );
        assertEquals( "<< Rocket: y should be " + r.getY() + " >>", initialY, r.getY() );
    }


    @Test
    public void rocketDoFrame()
    {
        r = new Rocket( initialX, initialY );

        r.doFrame( null, widthOfWindow, heightOfWindow, false, false, false, null, false, false );

        assertEquals( "<< Rocket: x should be " + r.getX() + " >>", initialX, r.getX() );

    }


    @Test
    public void rocketFindPositionX()
    {
        r = new Rocket( initialX, initialY );

        double previousVelocityX = r.getVelocityX();

        r.doFrame( null, widthOfWindow, heightOfWindow, false, false, false, null, false, false );

        assertEquals(
            "<< Rocket: x should be " + initialX + previousVelocityX + 1.0 / 2.0 * r.findAccelerationX( false ) + " >>",
            initialX + previousVelocityX + 1.0 / 2.0 * r.findAccelerationX( false ),
            r.findPositionX( false ),
            0.1 );
    }


    @Test
    public void rocketFindPositionY()
    {
        r = new Rocket( initialX, initialY );

        double previousVelocityY = r.getVelocityY();

        r.doFrame( null, widthOfWindow, heightOfWindow, false, false, false, null, false, false );

        assertEquals(
            "<< Rocket: y should be " + ( initialY + previousVelocityY + 1.0 / 2.0 * r.findAccelerationY( false ) )
                + " >>",
            initialX + previousVelocityY + 1.0 / 2.0 * r.findAccelerationY( false ),
            r.findPositionY( false ),
            0.1 );
    }


    @Test
    public void rocketFindVelocityX()
    {
        r = new Rocket( initialX, initialY );

        r.doFrame( null, widthOfWindow, heightOfWindow, false, false, false, null, false, false );

        assertEquals( "<< Rocket: velcotiyX should be " + ( 2 + r.findAccelerationX( false ) ) + " >>",
            ( 2 + r.findAccelerationX( false ) ),
            r.findVelocityX( false ),
            0.01 );

    }


    @Test
    public void rocketRocketFindVelocityY()
    {
        r = new Rocket( initialX, initialY );

        r.doFrame( null, widthOfWindow, heightOfWindow, false, false, false, null, false, false );

        assertEquals( "<< Rocket: velcotiyX should be " + ( 1 + r.findAccelerationY( false ) ) + " >>",
            ( 1 + r.findAccelerationY( false ) ),
            r.findVelocityY( false ),
            0.01 );
    }

    double gravity = 0.09;

    double airResistance = 0.001;

    double upArrow = 0.3;

    Graphics g;

    LunarX lunar;


    @Test
    public void rocketFindAccelerationX()
    {
        r = new Rocket( initialX, initialY );

        assertEquals(
            "<< Rocket: accelerationX should be " + ( upArrow * Math.sin( Math.toRadians( 0 ) ) - airResistance )
                + " >>",
            ( upArrow * Math.sin( Math.toRadians( 0 ) ) - airResistance ),
            r.findAccelerationX( true ),
            0.001 );

    }


    @Test
    public void rocketFindAccelerationY()
    {
        r = new Rocket( initialX, initialY );

        assertEquals(
            "<< Rocket: accelerationY should be " + ( gravity - upArrow * Math.cos( Math.toRadians( 0 ) ) ) + " >>",
            ( gravity - upArrow * Math.cos( Math.toRadians( 0 ) ) ),
            r.findAccelerationY( true ),
            0.001 );
    }


    @Test
    public void rocketGetPoints()
    {
        r = new Rocket( initialX, initialY );

        assertTrue( "<< Rocket: getPoints returns incorrect amount of points >>", r.getPoints() == 0 );
    }


    @Test(expected = NullPointerException.class)
    public void rocketExplode()
    {
        r = new Rocket( initialX, initialY );

        r.explode( null, null );

    }

    Landscape land = new Landscape( widthOfWindow, heightOfWindow - 5, heightOfWindow / 2 - 100, 3, 1, 5 );


    @Test
    public void rocketCheckForLanding()
    {
        r = new Rocket( initialX, initialY );
        assertNotNull( r.checkForLanding( land ) );
    }


    @Test
    public void rocketDecrementFuel()
    {
        r = new Rocket( initialX, initialY );
        int previous = r.getFuel();
        r.decrementFuel();

        assertTrue( "<< Rocket: decrementFuel does not decrement fuel correctly >>", r.getFuel() == previous - 1 );
    }


    @Test
    public void rocketGetVelocityX()
    {
        r = new Rocket( initialX, initialY );

        assertTrue( "<< Rocket: getVelocityX does not return correct velocity >>", r.getVelocityX() == 2 );
    }


    @Test
    public void rocketGetVelocityY()
    {
        r = new Rocket( initialX, initialY );

        assertTrue( "<< Rocket: getVelocityY does not return correct velocity >>", r.getVelocityY() == 1 );
    }


    @Test
    public void rocketStopRocket()
    {
        r = new Rocket( initialX, initialY );

        r.stopRocket();

        assertTrue( "<< Rocket: stopRocket does not stop rocket >>", r.getVelocityX() == 0 && r.getVelocityY() == 0 );
    }


    @Test
    public void rocketReset()
    {
        r = new Rocket( initialX, initialY );

        r.stopRocket();
        r.reset();

        assertTrue( "<< Rocket: reset does not reset rocket properties correctly >>",
            r.getVelocityX() == 2 && r.getVelocityY() == 1 && r.getX() == 100 && r.getY() == 100
                && r.getAngleOfRocket() == 0 );
    }

    // Test for Stars

    Stars s;

    Landscape l;


    @Test
    public void starsConstructor()
    {
        l = new Landscape( 400, 400, 200, 3, 1, 5 );
        s = new Stars( 24, l );

        assertTrue( "<< Stars: coords should have 24 stars >>", s.toStr().contains( "24" ) );
    }


    @Test
    public void starsDrawStars()
    {
        l = new Landscape( 300, 300, 200, 3, 1, 5 );
        s = new Stars( 45, l );

        assertTrue( "<< Stars: coords should have 24 stars >>", s.toStr().contains( "45" ) );
    }


    @Test
    public void starsToString()
    {
        l = new Landscape( 300, 300, 200, 3, 1, 5 );
        s = new Stars( 45, l );

        assertNotNull( "<< Stars: toStr should not be null", s.toStr() );
    }


    // Test for Landscape

    @Test
    public void landscapeConstructor()
    {
        l = new Landscape( 500, 500, 300, 3, 1, 5 );

        assertTrue( "<< Landscape: Invalid landscape Constructor >>",
            l.getWidth() == 500 && l.getMaxHeight() == 500 && l.getMinHeight() == 300 && l.getZoneWidth() == 100 );
    }


    @Test
    public void landscapeDrawLandscape()
    {
        l = new Landscape( 1000, 700, 400, 3, 1, 5 );

        assertTrue( "<< Landscape: drawLandscape does not work correctly >>",
            l.getMaxHeight() == 700 && l.getMinHeight() == 400 );
    }


    @Test(expected = NullPointerException.class)
    public void landscapePutPointValues()
    {
        l = new Landscape( 1000, 700, 400, 3, 1, 5 );
        l.putPointValues( null, 10 );
    }


    @Test
    public void landscapeGetCoordinates()
    {
        l = new Landscape( 1000, 700, 400, 3, 1, 5 );

        assertTrue( "<< Landscape: getCoordinates does not work correctly >>", l.getCoordinates().length == 1000 + 2 );
    }


    @Test
    public void landscapeGetWidth()
    {
        l = new Landscape( 1000, 700, 400, 3, 1, 5 );

        assertTrue( "<< Landscape: getWidth does not return correct width >>", l.getWidth() == 1000 );
    }


    @Test
    public void landscapeGetMinHeight()
    {
        l = new Landscape( 1000, 700, 400, 3, 1, 5 );

        assertTrue( "<< Landscape: getMinHeight does not return correct minimum height >>", l.getMinHeight() == 400 );
    }


    @Test
    public void landscapeGetMaxHeight()
    {
        l = new Landscape( 1000, 700, 400, 3, 1, 5 );

        assertTrue( "<< Landscape: getMaxHeight does not return correct maximum height >>", l.getMaxHeight() == 700 );
    }


    @Test
    public void landscapeGetLandscapePolygon()
    {
        l = new Landscape( 1000, 700, 400, 3, 1, 5 );

        assertNotNull( l.getLandscapePolygon() );
    }


    @Test
    public void landscapeGetLandingPoints()
    {
        l = new Landscape( 500, 500, 300, 3, 1, 5 );

        assertTrue( "<< Landscape: getLandingPoints does not return landing points correclty >>",
            l.getLandingPoints().size() == 5 );
    }


    @Test
    public void landscapeGetZoneWidth()
    {
        l = new Landscape( 500, 500, 300, 3, 1, 5 );

        assertTrue( "<< Landscape: getZoneWidth does not return correct width >>", l.getZoneWidth() == 100 );
    }

    // Test for LandingPoint

    LandingPoint p;


    @Test
    public void landingPointConstructor()
    {
        p = new LandingPoint( 100, 200, 100 );

        assertTrue( "<< LandingPoint: Invalid LandingPoint constructor >>",
            p.getPoints() == 100 - 100 && p.getStartX() == 100 && p.getStartY() == 200 && p.getEndX() == 200
                && p.getEndY() == 200 );
    }


    @Test
    public void landingPointGetPoints()
    {
        p = new LandingPoint( 100, 200, 100 );

        assertTrue( "<< LandingPoint: getPoints does not return correct amount of points >>",
            p.getPoints() == 100 - 100 );
    }


    @Test
    public void landingPointGetLength()
    {
        p = new LandingPoint( 100, 200, 100 );

        assertTrue( "<< LandingPoint: getLength does not return correct length >>", p.getLength() == 100 );
    }


    @Test
    public void landingPointGetStartX()
    {
        p = new LandingPoint( 100, 200, 100 );

        assertTrue( "<< LandingPoint: getStartX does not return correct value >>", p.getStartX() == 100 );
    }


    @Test
    public void landingPointGetStartY()
    {
        p = new LandingPoint( 100, 200, 100 );

        assertTrue( "<< LandingPoint: getStartY does not return correct value >>", p.getStartY() == 200 );
    }


    @Test
    public void landingPointGetEndX()
    {
        p = new LandingPoint( 100, 200, 100 );

        assertTrue( "<< LandingPoint: getEndX does not return correct value >>", p.getEndX() == 200 );
    }


    @Test
    public void landingPointGetEndY()
    {
        p = new LandingPoint( 100, 200, 100 );

        assertTrue( "<< LandingPoint: getEndY does not return correct value >>", p.getEndY() == 200 );
    }


    @Test
    public void landingPointSetStartY()
    {
        p = new LandingPoint( 100, 200, 100 );
        p.setStartY( 300 );

        assertTrue( "<< LandingPoint: setStartY does not set value correctly >>", p.getStartY() == 300 );
    }

    // Test for RocketPiece

    RocketPiece piece;


    @Test
    public void rocketPieceConstructor()
    {
        piece = new RocketPiece( 100, 200, 300, 400 );

        assertTrue( "<< RocketPiece: Invalid RocketPiece Constructor >>",
            piece.toStr().contains( "100" ) && piece.toStr().contains( "200" ) && piece.toStr().contains( "300" )
                && piece.toStr().contains( "300" ) );
    }


    // @Test
    public void rocketPieceDraw()
    {
        piece = new RocketPiece( 100, 200, 300, 400 );

        piece.draw( null );

        assertTrue( "<< RocketPiece: draw does not work as intended >>",
            piece.toStr().contains( "100" ) && piece.toStr().contains( "200" ) && piece.toStr().contains( "300" )
                && piece.toStr().contains( "300" ) );
    }


    @Test
    public void rocketPieceSetCrashCoords()
    {
        piece = new RocketPiece( 100, 200, 300, 400 );

        piece.setCrashCoords( 200, 200 );

        assertTrue( "<< RocketPiece: setCrashCoords does not set values correctly >>",
            piece.toStr().contains( "300" ) && piece.toStr().contains( "400" ) && piece.toStr().contains( "500" )
                && piece.toStr().contains( "600" ) );
    }


    @Test
    public void rocketPieceResetCrashCoords()
    {
        piece = new RocketPiece( 1, 3, 2, 4 );

        piece.setCrashCoords( 200, 300 );

        piece.resetCrashCoords();

        assertTrue( "<< RocketPiece: resetCrashCoords does not reset values correctly >>",
            piece.toStr().contains( "1" ) && piece.toStr().contains( "2" ) && piece.toStr().contains( "3" )
                && piece.toStr().contains( "4" ) );
    }


    @Test
    public void rocketPieceToString()
    {
        piece = new RocketPiece( 1, 2, 3, 4 );

        assertNotNull( "<< RocketPiece: toStr should not be null", piece.toStr() );
    }

    // Test for RocketShape

    RocketShape shape;


    @Test
    public void rocketShapeConstructor()
    {
        shape = new RocketShape( 200, 300 );

        assertTrue( "<< RocketShape: Invalid RocketShape Constructor >>", shape.getX() == 200 && shape.getY() == 300 );
    }


    @Test
    public void rocketShapeDrawFilled()
    {
        shape = new RocketShape( 200, 300 );

        assertEquals( "<< RocketShape: drawFilled should return " + shape.getAngleOfRocket() + " >>",
            -10,
            shape.drawFilled( null, false, true, false ) );
    }


    @Test
    public void rocketShapeSetRocketPolygon()
    {
        shape = new RocketShape( 200, 300 );

        Polygon p = new Polygon( shape.shiftArrayByConstant( shape.getXCoords(), 100 ),
            shape.shiftArrayByConstant( shape.getYCoords(), 100 ),
            shape.shiftArrayByConstant( shape.getXCoords(), 100 ).length );

        shape.setRocketPolygon( 100, 100 );

        assertTrue( "<< RocketShape: setRocketPolygon does not work properly  >>",
            Arrays.equals( p.xpoints, shape.getRocketPolygon().xpoints )
                && Arrays.equals( p.ypoints, shape.getRocketPolygon().ypoints ) );

    }


    @Test
    public void rocketShapeShiftArrayByConstant()
    {
        shape = new RocketShape( 200, 300 );

        int[] arr = { 0, 0, 0, 0 };

        assertNotNull( shape.shiftArrayByConstant( arr, 5 ) );

    }


    @Test
    public void rocketShapeSetPosition()
    {
        shape = new RocketShape( 200, 300 );

        shape.setPosition( 500, 600 );

        assertTrue( "<< RocketShape: setPosition does not work properly  >>",
            shape.getX() == 500 && shape.getY() == 600 );

    }


    @Test
    public void rocketShapeResetPos()
    {
        shape = new RocketShape( 200, 300 );

        shape.resetPos();

        assertTrue( "<< RocketShape: resetPos does not work properly  >>",
            shape.getX() == 100 && shape.getY() == 100 && shape.getAngleOfRocket() == 0 );

    }


    @Test
    public void rocketShapeGetRocketPolygon()
    {
        shape = new RocketShape( 200, 300 );

        assertNotNull( "<< RocketShape: getRocketPolygon returns null >>", shape.getRocketPolygon() );
    }


    @Test
    public void rocketShapeGetAngleOfRocket()
    {
        shape = new RocketShape( 200, 300 );

        assertNotNull( "<< RocketShape: getRocketAngleOfRocket returns null >>", shape.getAngleOfRocket() );
    }


    @Test
    public void rocketShapeGetXToRotateAbout()
    {
        shape = new RocketShape( 200, 300 );

        assertNotNull( "<< RocketShape: getXToRotateAbout returns null >>", shape.getXToRotateAbout() );
    }


    @Test
    public void rocketShapeGetYToRotateAbout()
    {
        shape = new RocketShape( 200, 300 );

        assertNotNull( "<< RocketShape: getYToRotateAbout returns null >>", shape.getYToRotateAbout() );
    }


    @Test
    public void rocketShapeGetX()
    {
        shape = new RocketShape( 200, 300 );

        assertNotNull( "<< RocketShape: getX returns null >>", shape.getX() );
    }


    @Test
    public void rocketShapeGetY()
    {
        shape = new RocketShape( 200, 300 );

        assertNotNull( "<< RocketShape: getY returns null >>", shape.getY() );
    }


    @Test
    public void rocketShapeGetHeightOfRocket()
    {
        shape = new RocketShape( 200, 300 );

        assertNotNull( "<< RocketShape: getHeightOfRocket returns null >>", shape.getHeightOfRocket() );
    }


    @Test
    public void rocketShapeRocketFactor()
    {
        shape = new RocketShape( 200, 300 );

        assertNotNull( "<< RocketShape: getRocketFactor returns null >>", shape.getRocketFactor() );
    }


    @Test
    public void rocketShapeWidthOfRocket()
    {
        shape = new RocketShape( 200, 300 );

        assertNotNull( "<< RocketShape: getWidthOfRocket returns null >>", shape.getWidthOfRocket() );
    }


    @Test
    public void rocketShapeXCoords()
    {
        shape = new RocketShape( 200, 300 );

        assertNotNull( "<< RocketShape: getXCoords returns null >>", shape.getXCoords() );
    }


    @Test
    public void rocketShapeYCoords()
    {
        shape = new RocketShape( 200, 300 );

        assertNotNull( "<< RocketShape: getYCoords returns null >>", shape.getYCoords() );
    }

    // Test for PlayerDisplay

    PlayerDisplay d;


    @Test
    public void playerDisplayConstructor()
    {
        d = new PlayerDisplay( null, null );

        assertTrue( "<< PlayerDisplay: Invalid PlayerDisplay Constructor >>", d.points == 0 );
    }


    @Test
    public void playerDisplayShowInfo()
    {
        d = new PlayerDisplay( null, null );

        d.showInfo( null, true );

        assertNotNull( "<< PlayerDisplay: showInfo does not show correct information >>", d.points );
    }


    @Test
    public void playerDisplayIncrementPoints()
    {
        d = new PlayerDisplay( null, null );

        PlayerDisplay.incrementPoints( 5 );

        assertTrue( "<< PlayerDisplay: incrementPoints does not increment points correctly >>", d.points == 5 );
    }


    @Test
    public void playerDisplayShowMenu()
    {
        d = new PlayerDisplay( null, null );

        d.showMenu( null, 500, 400 );

        assertTrue( "<< PlayerDisplay: showMenu does not properly work >>",
            d.getWidth() == 500 && d.getHeight() == 400 );
    }


    @Test
    public void playerDisplayGameOver()
    {
        d = new PlayerDisplay( null, null );

        d.showMenu( null, 700, 500 );

        assertTrue( "<< PlayerDisplay: showMenu does not properly work >>",
            d.getWidth() == 700 && d.getHeight() == 500 );
    }


    @Test
    public void playerDisplayGetWidth()
    {
        d = new PlayerDisplay( null, null );

        assertNotNull( "<< PlayerDisplay: getWidth returns null >>", d.getWidth() );
    }


    @Test
    public void playerDisplayGetHeight()
    {
        d = new PlayerDisplay( null, null );

        assertNotNull( "<< PlayerDisplay: getHeight returns null >>", d.getHeight() );
    }


    // Test for Zoomer

    @Test(expected = NullPointerException.class)
    public void Zoomerzoom()
    {
        Zoomer.zoom( null, null, null, null );
    }


    @Test(expected = NullPointerException.class)
    public void ZoomerRotateAboutRocket()
    {
        Zoomer.rotateAboutRocket( null, null );
    }


    @Test(expected = NullPointerException.class)
    public void ZoomerRevertToNormal()
    {
        Zoomer.revertToNormal();
    }


    // Test for LunarX

    @Test
    public void lunarXInit()
    {
        LunarX l = new LunarX();
        l.init();

        assertNotNull( "<< LunarX: landscape is null >>", l.getLandscape() );
        assertNotNull( "<< LunarX: stars is null >>", l.getStars() );
        assertNotNull( "<< LunarX: rocket is null >>", l.getRocket() );
        assertNotNull( "<< LunarX: playerDisplay is null >>", l.getPlayerDisplay() );
    }


    @Test(expected = NullPointerException.class)
    public void lunarXDrawFrame()
    {
        LunarX l = new LunarX();
        l.drawFrame( null );
    }


    @Test
    public void lunarXGetUpArrow()
    {
        LunarX l = new LunarX();
        assertNotNull( "<< LunarX: upArrow is null >>", l.getUpArrow() );
    }


    @Test
    public void lunarXGetLandscape()
    {
        LunarX l = new LunarX();
        l.init();
        assertNotNull( "<< LunarX: landscape is null >>", l.getLandscape() );
    }


    @Test
    public void lunarXGetStars()
    {
        LunarX l = new LunarX();
        l.init();
        assertNotNull( "<< LunarX: stars is null >>", l.getStars() );
    }


    @Test
    public void lunarXGetRocket()
    {
        LunarX l = new LunarX();
        l.init();
        assertNotNull( "<< LunarX: rocket is null >>", l.getRocket() );
    }


    @Test
    public void lunarXGetPlayerDisplay()
    {
        LunarX l = new LunarX();
        l.init();
        assertNotNull( "<< LunarX: PlayerDisplay is null >>", l.getPlayerDisplay() );
    }

    // Test for AnimationBase

    AnimationBase a;


    @Test(expected = NullPointerException.class)
    public void animationBaseDrawFrame()
    {
        a = new AnimationBase();

        a.drawFrame( null );
    }


    @Test
    public void animationBaseGetFrameNumber()
    {
        a = new AnimationBase();

        assertNotNull( "<< AnimationBase: frameNumber is null >>", a.getFrameNumber() );
    }


    @Test
    public void animationBaseSetFrameNumber()
    {
        a = new AnimationBase();

        a.setFrameNumber( 100 );

        assertEquals( "<< AnimationBase: frameNumber not set correctly >>", 100, a.getFrameNumber() );
    }


    @Test
    public void animationBaseGetWidth()
    {
        a = new AnimationBase();

        assertNotNull( "<< AnimationBase: width is null >>", a.getWidth() );
    }


    @Test
    public void animationBaseGetHeight()
    {
        a = new AnimationBase();

        assertNotNull( "<< AnimationBase: height is null >>", a.getHeight() );
    }


    @Test
    public void animationBaseGetElapsedTime()
    {
        a = new AnimationBase();

        assertNotNull( "<< AnimationBase: elapsedTime is null >>", a.getElapsedTime() );
    }


    @Test
    public void animationBaseSetFrameCount()
    {
        a = new AnimationBase();
        a.setFrameCount( 100 );

        assertEquals( "<< AnimationBase: frameCount not set properly >>", 100, a.getFrameCount() );
    }


    @Test
    public void animationBaseSetMillisecondsPerFrame()
    {
        a = new AnimationBase();
        a.setMillisecondsPerFrame( 100 );

        assertEquals( "<< AnimationBase: time not set properly >>", 100, a.getTimePerFrame() );
    }


    @Test
    public void animationBaseGetTimePerFrame()
    {
        a = new AnimationBase();

        assertNotNull( "<< AnimationBase: time is null >>", a.getTimePerFrame() );
    }


    @Test
    public void animationBaseConstructor()
    {
        a = new AnimationBase();

        assertNotNull( "<< AnimationBase: constructor does not work properly >>", a.getDisplay() );
    }


    @Test
    public void animationBaseGetDisplay()
    {
        a = new AnimationBase();

        assertNotNull( "<< AnimationBase: display is null >>", a.getDisplay() );
    }


    @Test
    public void animationBaseStart()
    {
        try
        {
            a = new AnimationBase();
            a.start();
        }
        catch ( Exception e )
        {
            fail( "<< AnimationBase: start throws an error >>" );
        }
    }


    @Test
    public void animationBaseStop()
    {
        try
        {
            a = new AnimationBase();
            a.stop();
        }
        catch ( Exception e )
        {
            fail( "<< AnimationBase: stop throws an error >>" );
        }
    }


    @Test
    public void animationBaseActionPerformed()
    {

        try
        {
            a = new AnimationBase();

            a.actionPerformed( null );
        }
        catch ( Exception e )
        {
            fail( "<< AnimationBase: stop throws an error >>" );
        }

    }


    @Test
    public void animationBaseGetFrameCount()
    {
        a = new AnimationBase();

        assertNotNull( "<< AnimationBase: frameCount is null >>", a.getFrameCount() );
    }


    // Remove block comment below to run JUnit test in console
    /**
     * Returns a new adapter for the class
     * 
     * @return new adapter
     */
    public static junit.framework.Test suite()
    {
        return new JUnit4TestAdapter( JULunarX.class );
    }


    /**
     * This main method runs first.
     * 
     * @param args
     *            represents string array.
     */
    public static void main( String args[] )
    {
        org.junit.runner.JUnitCore.main( "JULunarX" );
    }

}
