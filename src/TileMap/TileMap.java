package TileMap;
import Main.GamePanel;

import java.awt.*;
import java.awt.image.*;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import javax.imageio.ImageIO;

public class TileMap
{
    // position
    private double x;
    private double y;
    
    // domain/range bounds
    private int xmin;
    private int ymin;
    private int xmax;
    private int ymax;

    // animation tween of map
    private double tween;

    // map variables
    private int[][] map;
    private int tileSize;
    private int numRows;
    private int numCols;
    private int width;
    private int height;
    
    // tileset
    private BufferedImage tileset;
    private int numTilesAcross;
    private Tile[][] tiles;
    
    // drawing
    private int rowOffset;
    private int colOffset;
    private int numRowsToDraw;
    private int numColsToDraw;

    // constructor
    public TileMap(int tileSize)
    {
        // initializes variables
        this.tileSize = tileSize;
        // rows to draw
        numRowsToDraw = GamePanel.HEIGHT / tileSize + 2;
        numColsToDraw = GamePanel.WIDTH / tileSize + 2;
        // sets tween
        tween = 0.07;
    }

    //loads tiles
    public void loadTiles(String s)
    {
        try
        {
            // loads tile set from resource stream
            tileset = ImageIO.read(getClass().getResourceAsStream(s));
            numTilesAcross = tileset.getWidth() / tileSize;
            tiles = new Tile[2][numTilesAcross];
            // init subimages (for cutting the tileset images)
            BufferedImage subimage;
            // initializes blocked and normal (traversable) tiles
            for (int col = 0; col < numTilesAcross; col++)
            {
                // creates sub-image of normal and creates a normal tile
                subimage = tileset.getSubimage(col * tileSize, 0, tileSize, tileSize);
                tiles[0][col] = new Tile(subimage, Tile.NORMAL);
                // creates sub-image of normal and creates a blocked tile tile
                subimage = tileset.getSubimage(col * tileSize, tileSize, tileSize, tileSize);
                tiles[1][col] = new Tile(subimage, Tile.BLOCKED);
            }
        }
        // catches exceptions and prints stack trace in case of exceptions
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    // loads map on a level state
    public void loadMap(String s)
    {
        // reads from tilemap file
        try
        {
            //read from the tilemap file
            //load map to the window
            InputStream in = getClass().getResourceAsStream(s);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            // wrap integer
            numCols = Integer.parseInt(br.readLine());
            numRows = Integer.parseInt(br.readLine());
            // initializes map 2d array
            map = new int[numRows][numCols];
            // size of map in terms of pixels (per tile size)
            width = numCols * tileSize;
            height = numRows * tileSize;
            // inits the x min and max (for camera) of the tile map
            xmin = GamePanel.WIDTH - width;
            xmax = 0;
            ymin = GamePanel.HEIGHT - height;
            ymax = 0;
            // this is a regex delimiter for whitespace in the .map text file (it's essentially a text file)
            // and basically translates and parses it into the 2d array tile map
            String delims = "\\s+";
            for (int row = 0; row < numRows; row++)
            {
                String line = br.readLine();
                String[] tokens = line.split(delims);
                for (int col = 0; col < numCols; col++)
                {
                    map[row][col] = Integer.parseInt(tokens[col]);
                }
            }
        }
        //catches resource reading exceptions and shows stack trace
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    // returns tile size
    public int getTileSize()
    {
        return tileSize;
    }

    //returns x of a tile for setting map position of entities
    public double getX()
    {
        return x;
    }

    //returns y of a tile for setting map position of entities
    public double getY()
    {
        return y;
    }

    //returns width of entire tile map
    public int getWidth()
    {
        return width;
    }

    //returns height of entire tilemap
    public int getHeight()
    {
        return height;
    }

    // returns type of tile (blocked or traversable)
    public int getType(int row, int col)
    {
        int rc = map[row][col];
        int r = rc / numTilesAcross;
        int c = rc % numTilesAcross;
        return tiles[r][c].getType();
    }

    // smooth shift between different animations
    public void setTween(double d)
    {
        tween = d;
    }

    //changes/sets position of tilemaps as game progresses
    public void setPosition(double x, double y)
    {
        // tween is used here to make the transition smoother
        this.x += (x - this.x) * tween;
        this.y += (y - this.y) * tween;
        // fixes the bounds of the tilemap/camera
        fixBounds();
        colOffset = (int) -this.x / tileSize;
        rowOffset = (int) -this.y / tileSize;
    }

    // fixes bounds for tilemap (when player is moving) and allows for tiles to be set accordingly (for camera)
    private void fixBounds()
    {
        // sets xmin/max and ymin/max
        if (x < xmin) x = xmin;
        if (y < ymin) y = ymin;
        if (x > xmax) x = xmax;
        if (y > ymax) y = ymax;
    }

    // draws tilemap accordingly
    public void draw(Graphics2D g)
    {
        // for every row in the tile map (shown to player)
        for (int row = rowOffset; row < rowOffset + numRowsToDraw; row++)
        {
            if (row >= numRows) break;
            // for every col in the tile map (shown to player)
            for (int col = colOffset; col < colOffset + numColsToDraw; col++)
            {
                // break if column is bigger than the number of columns needed
                if (col >= numCols) break;
                // if the tile is an open traversable block for the player a "0" in the .map text file, then continue and don't draw the tile
                if (map[row][col] == 0) continue;
                int rc = map[row][col];
                int r = rc / numTilesAcross;
                int c = rc % numTilesAcross;
                // draw tile/image at that place
                g.drawImage(tiles[r][c].getImage(), (int) x + col * tileSize, (int) y + row * tileSize, null);
            }
        }
    }

}
