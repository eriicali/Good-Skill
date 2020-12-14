package Entity;
import Main.GamePanel;
import TileMap.TileMap;
import TileMap.Tile;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

//class for enemy objects, etc.
public abstract class MapObject {
    //tilestuff
    protected TileMap tileMap;
    protected int tilesize;
    protected double xmap;
    protected double ymap;

    //position and vector
    protected double x;
    protected double y;
    protected double dx;
    protected double dy;

    //dimensions
    protected int width;
    protected int height;

    //collision box ->collision with enemies, etc.
    protected int characterWidth;
    protected int characterHeight;

    //collision other
    //vars
    protected int currRow;
    protected int currCol;
    protected double xdest;
    protected double ydest;
    protected double xtemp;
    protected double ytemp;
    protected boolean topLeft;
    protected boolean topRight;
    protected boolean bottomLeft;
    protected boolean bottomRight;

    //animation
    protected Animation animation;
    protected int currentAction;
    protected int previousAction;
    protected boolean facingRight;

    //movement
    //determine what object is doing
    protected boolean left;
    protected boolean right;
    protected boolean up;
    protected boolean down;
    protected boolean jumping;
    protected boolean falling;

    //movement attributes
    protected double moveSpeed;
    protected double maxSpeed;
    protected double stopSpeed;//value that speed decrements by
    protected double fallSpeed;//gravity
    protected double maxFallSpeed; //terminal velocity
    protected double jumpStart;
    //longer u hold button for, builds up speed?
    protected double stopJumpSpeed;

    //added this
    protected int tileSize;
    protected boolean remove;

    private BufferedImage[] sprites;

    public MapObject(TileMap tm){
        //set tilemap
        tileMap = tm;
        tileSize = tm.getTileSize();
    }

    public void loadSprites(String filename){
        //load sprites
        try{
            //read sprites from file
            BufferedImage spritesheet = ImageIO.read(
                    getClass().getResourceAsStream("/Sprites"+filename)
            );

            sprites = new BufferedImage[3];
            //get individual images from sprites sheet (many small images to make animations)
            for (int i=0; i<sprites.length; i++){
                sprites[i] = spritesheet.getSubimage(i*width, 0, width, height);
            }
            //changed to IOException instead of Exception
        }catch(IOException e){
            System.out.println("problem loading sprites");
            e.printStackTrace();
        }
        // create new animation object and set the sprites as the frames of the animation
        animation = new Animation();
        animation.setFrames(sprites);
        animation.setDelay(300);

        right = true;//assignment starts heading right
        facingRight = true;
    }

    //check if MapObject can collide with other MapObject
    public boolean intersects(MapObject o){
        Rectangle r1 = getRectangle();
        Rectangle r2 = o.getRectangle();
        //use rectangles intersects method
        return r1.intersects(r2);
    }
    //helper method for above
    public Rectangle getRectangle(){
        return new Rectangle((int) x-characterWidth, (int)y-characterHeight, characterWidth, characterHeight);
    }
    
    public void calculateCorners(double x, double y){
        //get tiles around player
        int leftTile = (int)(x-characterWidth/2)/tileSize;
        int rightTile = (int)(x+characterWidth/2-1)/tileSize;
        int topTile = (int)(y+characterHeight/2-1)/tileSize;
        int bottomTile = (int)(y+characterHeight/2-1)/tileSize;

        //get tile types (normal/blocked)
        int tl = tileMap.getType(topTile, leftTile);
        int tr = tileMap.getType(topTile, rightTile);
        int bl = tileMap.getType(bottomTile, leftTile);
        int br = tileMap.getType(bottomTile, rightTile);
        //prevent player from going thru blocked tile
        topLeft = tl==Tile.BLOCKED;
        topRight = tr==Tile.BLOCKED;
        bottomLeft = bl==Tile.BLOCKED;
        bottomRight = br==Tile.BLOCKED;
    }

    //check if collided with blocked tile or normal tile
    public void checkTileMapCollision(){
        //cur column and row
        currCol=(int)x/tileSize;
        currRow = (int)y/tileSize;

        xdest = x+dx;
        ydest = y+dy;

        //keep track of original x and y to make final changes
        xtemp = x;
        ytemp = y;

        //y direction
        calculateCorners(x, ydest);
        if(dy<0) {
            //going upwards, reset positions
            if (topLeft || topRight) {
                dy = 0;
                //set player right below tile that player bumped into
                // we used currRow+1 so that we don't get the player stuck in the dirt
                ytemp = (currRow+1) * tileSize + characterHeight / 2;
            }else{
                ytemp+=dy;
            }
        }
        if(dy>0){
            //going downwards landed on tile
            if(bottomLeft||bottomRight){
                dy=0;
                falling=false;//not falling anymore
                ytemp = (currRow + 1) * tileSize - characterHeight/2;
            }else{
                ytemp+=dy;
            }
        }
        //x direction
        calculateCorners(xdest, y);
        if(dx<0){
            //going left
            if(topLeft || bottomLeft){
                dx=0;
                xtemp = currCol * tileSize + characterWidth/2;
            }else{
                xtemp+=dx;
            }
        }
        if(dx > 0){
            //going right
            if(topRight || bottomRight){
                //set vector to 0 if objet hits tiles to right
                dx = 0;
                xtemp = (currCol + 1) * tileSize - characterWidth/2;
            }else{
                xtemp += dx;
            }
        }
        if(!falling){
            //make sure player didn't fall off cliff
            calculateCorners(x, ydest+1);
            //not on solid ground, have to start falling
            if(!bottomLeft && !bottomRight){
                falling=true;
            }
        }
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void setPosition(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public void setVector(double dx, double dy) {
        this.dx = dx;
        this.dy = dy;
    }

    public void setMapPosition(){
        //every map object has 2 position (local and global)
        //where to draw character
        //offset player back on screen if they exit it
        xmap = tileMap.getX();
        ymap = tileMap.getY();
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    public void setUp(boolean up) {
        this.up = up;
    }

    public void setDown(boolean down) {
        this.down = down;
    }

    public void setJumping(boolean jumping) {
        this.jumping = jumping;
    }

    public abstract void update();

    public void draw (java.awt.Graphics2D g){
        //draw mapObject animation in direction facing
        if(facingRight) {
            g.drawImage(animation.getImage(), (int)(x+xmap - width / 2), (int) (y + ymap - height/2),null);
        }
        else {
            g.drawImage(animation.getImage(), (int) (x + xmap - width / 2 + width),(int) (y+ymap-height/2),-width,height,null);
        }
    }
}
