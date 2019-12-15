package com.mentalabs.christmasTree;

import java.util.Random;


public class Albero {
    public static final int STELLA = 255;

    public static final int LUCINA_ACCESA = 14;
    public static final int LUCINA_SPENTA = 13;

    public static final int PALLINA_1 = 12;
    public static final int PALLINA_0 = 11;

    public static final int RAMO_2 = 2;
    public static final int RAMO_1 = 1;
    public static final int RAMO_0 = 0;
    public static final int SFONDO = -1;
    public static final int CEPPO = -2;

    private int[][] pic;
    private int width;
    private int height;
    public static final int CEPPOHEIGHT = 3;

    private Random rng;

    public Albero(int width, int height) {
        this.height = height;
        this.width = width;
        pic = new int[this.height][this.width];
        rng = new Random(System.currentTimeMillis());
        buildTree();
    }

    public void buildTree() {
        initTree();
        layFoundation();
        placeThings();
    }

    private void initTree() {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                pic[i][j] = SFONDO;
            }
        }
    }

    private void layFoundation() {
        int i = 0;
        int m = (width / 2);
        System.out.println(m);
        int off = 0;
        int offDir = 1;
        boolean inside = false;

        while (i < height) {
            pic[i][m + off] = RAMO_2;
            pic[i][m - off] = RAMO_1;
            if (i == 0) {
                pic[i][m] = STELLA;
            }
            off += offDir;
            if (i > 1 && i < height - 1) {
                if (m + off >= width - 1) {
                    for (int tmpoff = 3; tmpoff < m; tmpoff++) {
                        System.out.println(tmpoff);
                        pic[i][m + tmpoff] = RAMO_0;
                        pic[i][m - tmpoff] = RAMO_0;
                    }
                    off = 3;
                }
            }
            if (i >= height - CEPPOHEIGHT) {
                for (int ceppoCnt = 0; ceppoCnt < width; ceppoCnt++) {
                    if (pic[i][ceppoCnt] == RAMO_0 ||pic[i][ceppoCnt] == RAMO_1 ||pic[i][ceppoCnt] == RAMO_2)
                        inside = !inside;
                    if (ceppoCnt < width - 2 && ceppoCnt >= 2 && pic[i][ceppoCnt] != RAMO_0 && pic[i][ceppoCnt] != RAMO_1 && pic[i][ceppoCnt] != RAMO_2 && inside)
                        pic[i][ceppoCnt] = CEPPO;
                    // else pic[i][ceppoCnt] = SFONDO;

                }
            }
            i++;
        }
    }

    private void placeThings() {
        boolean inside = false;
        for (int i = 1; i < height; i++) {
            for (int j = 0; j < width; j++) {
                // check if inside the tree or not
                if (pic[i][j] == RAMO_0 ||pic[i][j] == RAMO_1 ||pic[i][j] == RAMO_2)
                    inside = !inside;
                // if inside check if current cell isnt the tree's branch
                else if (inside && pic[i][j] != RAMO_0 && pic[i][j] != RAMO_1 && pic[i][j] != RAMO_2 && i < height - CEPPOHEIGHT) {
                    if (rng.nextInt(9) % 4 == 1)
                        pic[i][j] = rng.nextInt(3) + PALLINA_0;
                    else
                        pic[i][j] = SFONDO;
                }
            }
        }

    }

    public void nextFrame() {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
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

    public String toString() {
        String out = "";
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
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
                    out += "\033[" + (rng.nextInt(7) + 90) + "m+";
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
            if (i != height - 1)
                out += "\n";
        }
        return out;
    }
    
}
