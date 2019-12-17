/**
 * @author Lorenzo Cauli
 * @version 1.0
 */

//set package
package com.mentalabs.christmasTree;

//import Random class.
import java.util.Random;

public class Albero {
    /** Star constant */
    public static final int STELLA = 255;

    /** Lit light constant */
    public static final int LUCINA_ACCESA = 14;

    /** Unlit light constant */
    public static final int LUCINA_SPENTA = 13;

    /** Ball #1 constant */
    public static final int PALLINA_1 = 12;

    /** Ball #2 constant */
    public static final int PALLINA_0 = 11;

    /** Right side branch constant */
    public static final int RAMO_2 = 2;

    /** Left side branch constant */
    public static final int RAMO_1 = 1;

    /** Horizontal branch constant */
    public static final int RAMO_0 = 0;

    /** Wallpaper constant */
    public static final int SFONDO = -1;

    /** Stump constant */
    public static final int CEPPO = -2;

    /**
     * The tree itself in int matrix form. Uses the above mentioned elements
     */
    private int[][] pic;

    /** Tree width */
    private int width;

    /** Tree height */
    private int height;

    /** Tree stump height */
    public static final int CEPPOHEIGHT = 3;

    /** Random class object used for RNG in some methods */
    private Random rng;

    /** Variable for keeping track of tree power state. */
    private boolean acceso;

    /**
     * Constructor given height and width
     * 
     * @param width  Tree height.
     * @param height Tree width.
     */
    public Albero(int width, int height) {
        // assign dimentions
        this.height = height;
        this.width = width;
        // allocate tree matrix
        pic = new int[this.height][this.width];
        // initialize Random object with current time in milliseconds as seed
        rng = new Random(System.currentTimeMillis());
        // make sure the tree is not powered on
        acceso = false;
        // build the tree
        buildTree();
    }

    /**
     * Build the tree.
     */
    public void buildTree() {
        initTree();
        layFoundation();
        placeThings();
    }

    /**
     * Method that inits the tree's matrix to SFONDO element (wallpaper)
     */
    private void initTree() {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                pic[i][j] = SFONDO;
            }
        }
    }

    /**
     * Method that lays the tree's foundation (star at the tip, branches and stump)
     */
    private void layFoundation() {
        // used as counter to cycle through height
        int i = 0;
        // line middle point
        int m = (width / 2);
        // middle point offset to make branches symmetric
        int off = 0;
        // offset direction
        int offDir = 1;
        // variable to know if inside the tree or not. used to lay stump
        boolean inside = false;

        // cycle through tree height
        while (i < height) {
            // lay branches on symmetrical points
            pic[i][m + off] = RAMO_2;
            pic[i][m - off] = RAMO_1;
            // if on first line, put star at the center
            if (i == 0) {
                pic[i][m] = STELLA;
            }
            // increment offset
            off += offDir;
            // if inside the tree's main body lines
            if (i > 1 && i < height - 1) {
                // if at the end of the line
                if (m + off >= width - 1) {
                    // lay horizontal branch
                    for (int tmpoff = 3; tmpoff < m; tmpoff++) {
                        System.out.println(tmpoff);
                        pic[i][m + tmpoff] = RAMO_0;
                        pic[i][m - tmpoff] = RAMO_0;
                    }
                    // go back to starting point for next branch
                    off = 3;
                }
            }
            // if inside stump lines
            if (i >= height - CEPPOHEIGHT) {
                // cycle line with temporary counter
                for (int ceppoCnt = 0; ceppoCnt < width; ceppoCnt++) {
                    // check if inside tree (inside branch denoted part)
                    if (pic[i][ceppoCnt] == RAMO_0 || pic[i][ceppoCnt] == RAMO_1 || pic[i][ceppoCnt] == RAMO_2)
                        inside = !inside;
                    // draw tree stump
                    if (ceppoCnt < width - 2 && ceppoCnt >= 2 && pic[i][ceppoCnt] != RAMO_0
                            && pic[i][ceppoCnt] != RAMO_1 && pic[i][ceppoCnt] != RAMO_2 && inside)
                        pic[i][ceppoCnt] = CEPPO;
                }
            }
            // increment main counter
            i++;
        }
    }

    /**
     * Method that places the tree's things (little balls, lights, etc)
     */
    private void placeThings() {
        // variable to see if inside main tree's body
        boolean inside = false;
        // cycle through height
        for (int i = 1; i < height; i++) {
            // cycle through line
            for (int j = 0; j < width; j++) {
                // check if inside the tree or not
                if (pic[i][j] == RAMO_0 || pic[i][j] == RAMO_1 || pic[i][j] == RAMO_2)
                    inside = !inside;
                // if inside check if current cell isnt a branch
                else if (inside && pic[i][j] != RAMO_0 && pic[i][j] != RAMO_1 && pic[i][j] != RAMO_2
                        && i < height - CEPPOHEIGHT) {
                    // check if next integer is a multiple of 4, used to get more random chances and
                    // if yes then place a random kind of element
                    if (rng.nextInt(9) % 4 == 1)
                        pic[i][j] = rng.nextInt(3) + PALLINA_0;
                    // else empty the area, probably not needed and reduntant, doesnt harm nobody
                    // though
                    else
                        pic[i][j] = SFONDO;
                }
            }
        }

    }

    /**
     * Method used for animation in the tree
     */
    public void nextFrame() {
        // cycle through height
        for (int i = 0; i < height; i++) {
            // cycle through line
            for (int j = 0; j < width; j++) {
                // check if current cell is an animatable element
                switch (pic[i][j]) {
                case PALLINA_0:
                    pic[i][j] = PALLINA_1;
                    break;
                case PALLINA_1:
                    pic[i][j] = PALLINA_0;
                    break;
                case LUCINA_ACCESA:
                    pic[i][j] = LUCINA_SPENTA;
                    break;
                case LUCINA_SPENTA:
                    pic[i][j] = LUCINA_ACCESA;
                    break;
                }
            }
        }
    }

    /**Method that turns the tree on */
    public void accendi() { acceso = true; }

    /**Method that turns the tree off */
    public void spegni(){ acceso = false; }

    /**
     * Method that returns the power state of the tree
     * @return true if the tree is on, false if the tree is off
     */
    public boolean isAcceso() {return acceso;}

    /**
     * Method that returns the tree in String form.
     * 
     * @return The tree.
     */
    public String toString() {
        // declare output var
        String out = "";
        // cycle through height
        for (int i = 0; i < height; i++) {
            // cycle through line
            for (int j = 0; j < width; j++) {
                // see if current cell is a certain element and concat a symbol corresponding to
                // said element
                switch (pic[i][j]) {
                case RAMO_0:
                    out += "\033[32m=";
                    break;
                case RAMO_1:
                    out += "\033[32m/";
                    break;
                case RAMO_2:
                    out += "\033[32m\\";
                    break;
                case PALLINA_0:
                    out += "\033[91mo";
                    break;
                case PALLINA_1:
                    out += "\033[94mo";
                    break;
                case LUCINA_SPENTA:
                    out += " ";
                    break;
                case LUCINA_ACCESA:
                    // check if tree is turned on and give some random color to the light for added
                    // flare
                    if (acceso) out += "\033[" + (rng.nextInt(7) + 90) + "m+";
                    else out +=" ";
                    break;
                case SFONDO:
                    out += " ";
                    break;
                case CEPPO:
                    out += "\033[33m|";
                    break;
                case STELLA:
                    out += "\033[5m*";
                    break;
                }
                out += "\033[0m";
            }
            // if not at the last line, removes unneeded newline
            if (i != height - 1)
                out += "\n";
        }
        // return said string
        return out;
    }

}
