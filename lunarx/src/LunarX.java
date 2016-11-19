
import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import math.geom2d.polygon.SimplePolygon2D;


/**
 * This class is the main class that runs the whole program and extends
 * AnimationBase. LunarX initializes the rocket, landscape, player display, and
 * the stars. Once initialized the class will continually animate frames through
 * the drawFrame() method.
 * 
 * 
 * @author Tejasvi Kothapalli
 * @version May 25, 2016
 * @author Period: 3
 * @author APCS Final Project: LunarX
 * 
 * @author Sources - none
 */
public class LunarX extends AnimationBase
{

    /**
     * The rocket placed within the GUI.
     */
    Rocket rocket;

    /**
     * The landscape that is draw in the GUI.
     */
    Landscape land;

    /**
     * The stars within the GUI.
     */
    Stars stars;

    /**
     * This boolean represents whether the up arrow key is pressed or not.
     */
    public boolean upArrow = false;

    /**
     * This boolean represents whether the right arrow key is pressed or not.
     */
    public boolean rightArrow = false;

    /**
     * This boolean represents whether the left arrow key is pressed or not.
     */
    public boolean leftArrow = false;

    /**
     * This boolean represents whether the up space bar is pressed or not.
     */
    public boolean spaceBar = false;

    /**
     * This is the display of all the info draw in the GUI.
     */
    PlayerDisplay playerInfo;

    /**
     * This boolean is used to tell whether or not the rocket has exploded.
     */
    private boolean exploded = false;

    /**
     * This boolean tells if the game has started or not and the rocket is in
     * motion.
     */
    private boolean started = false;
    
    private boolean speed = false;


    /**
     * This routine is called by the system when the applet is first created.
     */
    public void init()
    {
        first = true;

        getDisplay().addKeyListener( new KeyListener()
        {

            @Override
            public void keyTyped( KeyEvent e )
            {

            }


            @Override
            public void keyReleased( KeyEvent e )
            {

                int c = e.getKeyCode();
                if ( c == KeyEvent.VK_UP )
                {
                    upArrow = false;
                }
                else if ( c == KeyEvent.VK_LEFT )
                {
                    leftArrow = false;
                }
                else if ( c == KeyEvent.VK_RIGHT )
                {
                    rightArrow = false;
                }
                else if ( c == KeyEvent.VK_SPACE )
                {
                    spaceBar = false;
                }
                else if (c ==KeyEvent.VK_S)
                {
                    speed = false;
                }
            }


            @Override
            public void keyPressed( KeyEvent e )
            {
                int c = e.getKeyCode();
                if ( c == KeyEvent.VK_UP )
                {
                    upArrow = true;
                }
                else if ( c == KeyEvent.VK_LEFT )
                {
                    leftArrow = true;
                }
                else if ( c == KeyEvent.VK_RIGHT )
                {
                    rightArrow = true;
                }
                else if ( c == KeyEvent.VK_SPACE )
                {
                    spaceBar = true;
                }
                else if (c ==KeyEvent.VK_S)
                {
                    speed = true;
                }
            }
        } );

        getDisplay().setFocusable( true );
        getDisplay().requestFocusInWindow();

        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        getDisplay().setSize( dim.width, dim.height - 80 );
        setSize( dim.width, dim.height - 80 );

        land = new Landscape( getWidth(), getHeight() - 5, getHeight() / 2 - 100, 3, 1, 5 );

        stars = new Stars( 20, land );

        rocket = new Rocket( 100, 100 );
        playerInfo = new PlayerDisplay( rocket, this );

        setMillisecondsPerFrame( 70 );
        started = false;
    }

    private boolean first = true;


    /**
     * This routine is called by the animation framework every time a new frame
     * needs to be drawn.
     * 
     * For this animation, the balls bounce.
     * 
     */
    public void drawFrame( Graphics g )
    {
        if (speed)
        {
            setMillisecondsPerFrame( 40);
        }
        else
        {
            setMillisecondsPerFrame( 70 );
        }
        super.drawFrame( g );
        setBackground( Color.black );
        int width = getWidth();
        int height = getHeight();
        playerInfo.showInfo( g, started );

        if ( rocket.getFuel() == 0 )
        {
            playerInfo.gameOver( g, width, height );
        }

        if ( spaceBar )
        {
            started = true;
        }

        if ( !started )
        {
            playerInfo.showMenu( g, width, height );
        }

        SimplePolygon2D s = new SimplePolygon2D( land.getXDoubleValues(), land.getYDoubleValues() );

        if ( !first && s.distance( rocket.getX(), rocket.getY() ) < 150 )
        {

            Zoomer.zoom( rocket, g, this, land );
        }

        land.drawLandscape( g );
        stars.drawStars( g );

        land.putPointValues( g, getFrameNumber() );
        int numberForLanding = 0;

        if ( !first )
        {

            numberForLanding = rocket.checkForLanding( land );
            if ( numberForLanding == 2 )
            {
                Zoomer.rotateAboutRocket( rocket, g );
                rocket.explode( g, this );
                exploded = true;

                if ( rocket.getFuel() == 0 )
                {
                    Timer timer = new Timer( 3000, new ActionListener()
                    {
                        @Override
                        public void actionPerformed( ActionEvent arg0 )
                        {
                            init();
                            started = false;

                        }
                    } );
                    timer.setRepeats( false );
                    timer.start();

                }
                else
                {
                    Timer timer = new Timer( 3000, new ActionListener()
                    {
                        @Override
                        public void actionPerformed( ActionEvent arg0 )
                        {
                            rocket.reset();
                            rocket.resetPos();
                            Zoomer.revertToNormal();
                            first = true;
                        }
                    } );
                    timer.setRepeats( false );
                    timer.start();
                }
            }
        }

        if ( numberForLanding == 0 )
        {
            rocket.doFrame( g, width, height, upArrow, leftArrow, rightArrow, this, false, started );
        }
        else if ( numberForLanding == 1 )
        {
            rocket.stopRocket();

            PlayerDisplay
                .incrementPoints( land.getLandingPoints().get( rocket.getX() / land.getZoneWidth() ).getPoints() );

            try
            {
                Thread.sleep( 1000 );
            }
            catch ( InterruptedException e )
            {
                e.printStackTrace();
            }
            rocket.reset();
            rocket.resetPos();
            Zoomer.revertToNormal();
            first = true;
            return;

        }

        // else
        // {
        // stars.drawStars( g );
        // land.drawLandscape( g );
        //
        // rocket.doFrame( g, width, height, upArrow, leftArrow, rightArrow,
        // this, false, started );
        // land.putPointValues( g, getFrameNumber() );
        // }
        first = false;
    }


    /**
     * This returns the boolean of whether or not the up arrow is being clicked
     * 
     * @return upArrow boolean
     */
    public boolean getUpArrow()
    {
        return upArrow;
    }


    // For testing purposes
    /**
     * This returns the landscape within the GUI
     * 
     * @return landscape
     */
    public Landscape getLandscape()
    {
        return land;
    }


    /**
     * This returns the stars
     * 
     * @return stars
     */
    public Stars getStars()
    {
        return stars;
    }


    /**
     * This returns the rocket
     * 
     * @return
     */
    public Rocket getRocket()
    {
        return rocket;
    }


    /**
     * This returns the player display
     * 
     * @return player display
     */
    public PlayerDisplay getPlayerDisplay()
    {
        return playerInfo;
    }

}