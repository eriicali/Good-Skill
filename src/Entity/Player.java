package Entity;
import Entity.Drinks.Beverage;
import Entity.Enemies.Enemy;
import TileMap.*;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Player extends MapObject {
    // player stuff
    //instead of ink do ink
    private int health;
    private int maxHealth;
    private int ink;
    private int maxInk;
    private boolean dead;
    private boolean flinching;
    private long flinchTimer;

    //fireball or ink ball
    private boolean throwingInk;
    private int inkCost;
    private int inkBlobDamage;

    private ArrayList<InkBlob> inkBlobs;
    //maybe we can do some polymorphism here later and have more than one attack
    //private ArrayList<FireBall> fireBalls;

    //scratch
    private boolean pencilAttack;
    private int pencilDamage;
    private int pencilRange;

    //glising
    private boolean gliding;
    //animations
    private ArrayList<BufferedImage[]> sprites;
    private final int[] numFrames = {
    // number of frames of each animation action
       2,8,1,2,4,2,5
    };
    //animation actions
    private static final int IDLE = 0;
    private static final int WALKING = 1;
    private static final int JUMPING = 2;
    private static final int FALLING = 3;
    private static final int GLIDING = 4;
    private static final int INKBLOB = 5;
    private static final int PENCILATTACK = 6;

    public Player(TileMap tm) {
        super(tm);
        width = 30;
        height = 30;
        //character width character height
        characterWidth = 20;
        characterHeight = 20;
        //try not to mess with these too much
        moveSpeed = 0.3;
        maxSpeed = 1.6;
        stopSpeed = 0.4;

        fallSpeed = 0.15;
        maxFallSpeed = 4.0;
        jumpStart = -4.8;
        stopJumpSpeed = 0.3;

        facingRight = true;
        health = maxHealth = 5;
        ink = maxInk = 2500;
        inkCost = 200;
        inkBlobDamage = 2;
        //arrayList of inkBlobs for the player
        inkBlobs = new ArrayList<InkBlob>();
        pencilDamage = 8;
        pencilRange =40;
        loadSprites("/Player/studentsprites.gif");
    }
    //need to override this method since player sprite sheet is different from others
    //more animations for the player for different actions
    public void loadSprites(String filename){
        //try catch exception handling
        //fileIO
        try{
            BufferedImage spritesheet = ImageIO.read(
                    getClass().getResourceAsStream("/Sprites"+filename)
            );
            sprites = new ArrayList<BufferedImage[]>();
            for(int i = 0; i < 7; i++) {
                //array of buffered images
                BufferedImage[] bi = new BufferedImage[numFrames[i]];
                for(int j = 0; j<numFrames[i]; j++){
                    //pencilattack images are different from the others (diff width for subimages)
                    //other actions have same width for subimages
                    if(i != PENCILATTACK){
                        bi[j] = spritesheet.getSubimage(j * width, i * height, width, height);
                    }
                    //adding pencilattack images
                    //subimages are double the width of other actions
                    else {
                        bi[j] = spritesheet.getSubimage(j * width*2, i * height, width * 2, height);
                    }
                }
                //array of BufferedImages arrays
                //add bufferImage array to sprites
                //will happen 6 times (6 diff animations)
                sprites.add(bi);
            }
        }
        catch (Exception e){
            System.out.println("couldn't load player images");
            e.printStackTrace();
        }
        animation = new Animation();
        //play idle animation ->default
        //if keys are pressed other animations are played instead
        currentAction = IDLE;
        animation.setFrames(sprites.get(IDLE));
        animation.setDelay(400);
    }

    public void checkAttack(ArrayList<Enemy> enemies) {
        //loop through enemies and check if they get hit by attack
        //polymorphism, treat all enemies general enemies
        for(int i= 0; i <enemies.size(); i++) {
            Enemy e = enemies.get(i);
            //pencil attack
            if(pencilAttack){
                if(facingRight){
                    //if enemies is close enough to the right to the player
                    if (e.getX() > x &&
                        e.getX() < x + pencilRange &&
                        e.getY() > y - height/2 &&
                        e.getY() < y + height/2
                    ) {
                        //they will get attacked
                        e.hit(pencilDamage);
                    }
                }
                //if enemies is close enough to the left to the player
                else {
                    if (e.getX() < x &&
                        e.getX() > x - pencilRange &&
                        e.getY() > y - height/2 &&
                        e.getY() < y + height/2
                    ) {
                        //they will get attacked
                        e.hit(pencilDamage);
                    }
                }
            }
            //for inkBlobs if it intersects with an enemy
            //hit enemy and apply damage to them
            for (int j = 0; j< inkBlobs.size(); j++) {
                if (inkBlobs.get(j).intersects(e)){
                    e.hit(inkBlobDamage);
                    inkBlobs.get(j).setHit();
                    break;
                }
            }
            // check enemies
            //player takes damage if hit by enemy
            if(intersects(e)) {
                hit(e.getDamage());
            }
        }
    }
    public void checkBeverage(ArrayList<Beverage> drinks){
        //for every drink in the list
        for(int i= 0; i <drinks.size(); i++) {
            Beverage d = drinks.get(i);
            //if player intersects with beverage
            //collect if if they have less than 5 health
            if (intersects(d) && health < 5) {
                //increase player's health
                drinkBeverage(d.getHealth());
                //player picked it up and remove it from the screen
                d.setPickedUp(true);
            }
        }
    }
    public void drinkBeverage(int increase) {
        //increase health by 1
        health += increase;
        //extra check
        if (health > 5) health = 5;
    }
    //
    public void hit(int damage) {
        //cant get hurt when flinching
        if(flinching) return;
        //otherwise player will take damage
        health -= damage;
        //set players health to 0 if they lost it all at once
        //cant have negative health
        if(health < 0) health =0;
        //player dies
        if(health == 0) dead = true;
        //set flinching to true once player is hit
        //give some time for player to escape
        flinching = true;
        flinchTimer = System.nanoTime();
    }

    private void findNextPosition() {
        //movement
        if(left){
            //change movement vectors
            //subtract if going left
            dx -= moveSpeed;
            if(dx < -maxSpeed){
                dx = -maxSpeed;
            }
        }
        //add if going right
        else if (right) {
            dx += moveSpeed;
            if (dx > maxSpeed) {
                dx = maxSpeed;
            }
        }
        else {
            if (dx > 0 ){
                dx -= stopSpeed;
                if(dx<0){
                    dx =0;
                }
            }
            else if(dx <0){
                dx += stopSpeed;
                if(dx>0){
                    dx =0;
                }
            }
        }
        // cannot move while attacking except in air
        if((currentAction== PENCILATTACK ||currentAction== INKBLOB)&& !(jumping ||falling)){
            dx = 0;
        }
        if (jumping && !falling) {
            //start jump speed, going upwards
            dy = jumpStart;
            //start falling bc in the air
            falling = true;
        }
        if (falling) {
            // gliding has you falling at ten percent of the speed
            if(dy> 0 && gliding) dy+= fallSpeed*0.1;
            //falling accelerating until maxFallSpeed(terminal velocity)
            //simulate gravity
            else dy += fallSpeed;
            //if moving up already cant jump
            //no double jumps
            if (dy > 0) jumping = false;
            //falling and not jumping
            if(dy<0&& !jumping) dy += stopJumpSpeed;
            //terminal velocity
            if(dy>maxFallSpeed) dy = maxFallSpeed;
        }
    }
    public void update() {
        //update position
        findNextPosition();
        //check for any collisions with map Objects
        checkTileMapCollision();
        //set x&y position for player when moving
        setPosition(xtemp, ytemp);
        // check attack has stopped
        //only want to play it once
        if(currentAction == PENCILATTACK){
            if(animation.hasPlayedOnce()) pencilAttack = false;
        }
        if(currentAction == INKBLOB){
            if(animation.hasPlayedOnce()) throwingInk = false;
        }
        // ink attack
        //add an ink every time it updates
        ink += 1;
        //cant have more than maxINk num of inks
        if(ink > maxInk) ink = maxInk;
        //throw ink
        if(throwingInk && currentAction != INKBLOB){
            if(ink > inkCost){
                //subtract ink
                ink -= inkCost;
                //make new inkBlob
                InkBlob fb = new InkBlob(tileMap, facingRight);
                //set position on screen
                fb.setPosition(x, y);
                //add to screen
                inkBlobs.add(fb);
            }
        }
        //for each inkBlob
        for (int i = 0; i < inkBlobs.size(); i++){
            //if inkBlob hits something need to remove it
            inkBlobs.get(i).update();
            if(inkBlobs.get(i).getRemove()){
                inkBlobs.remove(i);
                i--;
            }
        }
        //check done flinching
        if (flinching) {
            //stop flinching after it happened for some time
            long elapsed = (System.nanoTime() - flinchTimer) / 1000000;
            if (elapsed > 1000) {
                flinching = false;
            }
        }
        // set animations
        if(pencilAttack) {
            if (currentAction != PENCILATTACK) {
                currentAction = PENCILATTACK;
                //draw pencil attack animation if pencil attacking
                animation.setFrames(sprites.get(PENCILATTACK));
                animation.setDelay(50);
                width = 60;
            }
        }
        else if(throwingInk) {
            if(currentAction != INKBLOB) {
                currentAction = INKBLOB;
                //draw throwing ink animation if ink thrown
                animation.setFrames(sprites.get(INKBLOB));
                animation.setDelay(100);
                width = 30;
            }
        }
        //if in the air
        else if (dy > 0){
            if(gliding) {
                if(currentAction != GLIDING) {
                    currentAction = GLIDING;
                    //set gliding animation
                    animation.setFrames(sprites.get(GLIDING));
                    animation.setDelay(100);
                    width = 30;
                }
            }
            //if falling (not actively gliding)
            else if (currentAction != FALLING) {
                currentAction = FALLING;
                //falling animation
                animation.setFrames(sprites.get(FALLING));
                animation.setDelay(100);
                width = 30;
            }
        }
        //if player is in the air
        else if (dy < 0) {
            //if player isnt jumping set to jumping
            if (currentAction != JUMPING) {
                currentAction = JUMPING;
                //do corresponding jumping animation
                animation.setFrames(sprites.get(JUMPING));
                animation.setDelay(-1);
                width = 30;

            }
        }
        //if facing left/right
        else if (left ||right) {
            //if player's action isnt walking,
            // make player's animation walking
            if (currentAction != WALKING) {
                currentAction = WALKING;
                animation.setFrames(sprites.get(WALKING));
                animation.setDelay(40);
                width = 30;
            }
        }
        else {
            //if player is staying still
            //draw idle animations
            if (currentAction != IDLE) {
                currentAction = IDLE;
                animation.setFrames(sprites.get(IDLE));
                animation.setDelay(400);
                width = 30;
            }
        }
        animation.update();
        //if not attacking
        //reset direction player is facing
        if(currentAction != PENCILATTACK && currentAction != INKBLOB){
            if(right) facingRight = true;
            if(left) facingRight = false;
        }
    }
    //override draw method since need to draw inkBlobs and flicnhing animations
    public void draw(Graphics2D g) {
        //set player's position on the screen
        setMapPosition();
        //draw inkBlobs when thrown
        for (int i = 0; i < inkBlobs.size(); i++) {
            inkBlobs.get(i).draw(g);
        }
        if (flinching) {
            //flinch for a few seconds when collides with enemy
            long elapsed = (System.nanoTime() - flinchTimer) / 1000000;
            if (elapsed / 100 % 2 == 0) {
                return;
            }
        }
        super.draw(g);
    }
    //setter/getter
    public int getHealth() {return health;}
    public int getMaxHealth() {return maxHealth;}
    public int getInk() {return ink;}
    public int getMaxInk() {return maxInk;}
    public void setThrowingInk() {
        throwingInk = true;
    }
    public void setPencilAttack() {
        pencilAttack = true;
    }
    public void setGliding(boolean b) {
        gliding = b;
    }
    public boolean getDead(){
        return dead;
    }
    public void setDead(boolean dead) {this.dead = dead;}
}
