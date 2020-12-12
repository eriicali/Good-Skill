package Entity;
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
    private ArrayList<FireBall> inkBlobs;
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
        inkBlobDamage = 5;
        inkBlobs = new ArrayList<FireBall>();
        pencilDamage = 8;
        pencilRange =40;
        try{
            BufferedImage spritesheet = ImageIO.read(
              getClass().getResourceAsStream("/Sprites/Player/playersprites.gif")
            );
            sprites = new ArrayList<BufferedImage[]>();
            for(int i = 0; i < 7; i++) {
                BufferedImage[] bi = new BufferedImage[numFrames[i]];
                for(int j = 0; j<numFrames[i]; j++){
                    if(i != PENCILATTACK){
                        bi[j] = spritesheet.getSubimage(j * width, i * height, width, height);
                    }
                    else {
                        bi[j] = spritesheet.getSubimage(j * width*2, i * height, width * 2, height);
                    }

                }
                sprites.add(bi);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        animation = new Animation();
        currentAction = IDLE;
        animation.setFrames(sprites.get(IDLE));
        animation.setDelay(400);

    }
    public int getHealth() {return health;}
    public int getMaxHealth() {return maxHealth;}
    public int getInk() {return ink;}
    public int getMaxInk() {return maxInk;}
    public void setFiring() {
        throwingInk = true;

    }
    public void setScratching() {
        pencilAttack = true;
    }
    public void setGliding(boolean b) {
        gliding = b;
    }
    public void checkAttack(ArrayList<Enemy> enemies) {
        //loop through enemies and check if they get hit by attack
        for(int i= 0; i <enemies.size(); i++) {
            Enemy e = enemies.get(i);
            //scratch attack
            if(pencilAttack){
                if(facingRight){
                    if (e.getX() > x &&
                        e.getX() < x + pencilRange &&
                        e.getY() > y - height/2 &&
                        e.getY() < y + height/2
                    ) {
                        e.hit(pencilDamage);
                    }
                }
                else {
                    if (e.getX() < x &&
                        e.getX() > x - pencilRange &&
                        e.getY() > y - height/2 &&
                        e.getY() < y + height/2
                    ) {
                        e.hit(pencilDamage);
                        
                    }

                }
            }
            for (int j = 0; j< inkBlobs.size(); j++) {
                if (inkBlobs.get(j).intersects(e)){
                    e.hit(inkBlobDamage);
                    inkBlobs.get(j).setHit();
                    break;
                }
            }
            // check enemies
            if(intersects(e)) {
                hit(e.getDamage());
            }
        }

    }
    public void checkBeverage(ArrayList<Beverage> drinks){
        for(int i= 0; i <drinks.size(); i++) {
            Beverage d = drinks.get(i);
            if (intersects(d) && health < 5) {
                drinkCoffee(d.getHealth());
                d.pickedUp = true;
            }
        }
    }
    public void drinkCoffee(int increase) {
        health += increase;
        if (health > 5) health = 5;

    }
    public void hit(int damage) {
        if(flinching) return;
        health -= damage;
        if(health < 0) health =0;
        if(health == 0) dead = true;
        flinching = true;
        flinchTimer = System.nanoTime();

    }
    public boolean getDead(){
        return dead;
    }
    public void setDead(boolean dead) {this.dead = dead;}
    private void getNextPosition() {
        //movement
        if(left){
            dx -= moveSpeed;
            if(dx < -maxSpeed){
                dx = -maxSpeed;
            }
        }
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
            dy = jumpStart;
            falling = true;
        }
        if (falling) {
            // gliding has you falling at ten percent of the speed
            if(dy> 0 && gliding) dy+= fallSpeed*0.1;
            else dy += fallSpeed;
            if (dy > 0) jumping = false;
            if(dy<0&& !jumping) dy += stopJumpSpeed;
            if(dy>maxFallSpeed) dy = maxFallSpeed;

        }
    }
    public void update() {
        //update position

        getNextPosition();
        checkTileMapCollision();
        setPosition(xtemp, ytemp);
        // check attack has stopped
        if(currentAction == PENCILATTACK){
            if(animation.hasPlayedOnce()) pencilAttack = false;
        }
        if(currentAction == INKBLOB){
            if(animation.hasPlayedOnce()) throwingInk = false;
        }
        // fireball attack
        ink += 1;
        if(ink > maxInk) ink = maxInk;
        if(throwingInk && currentAction != INKBLOB){
            if(ink > inkCost){
                ink -= inkCost;
                FireBall fb = new FireBall(tileMap, facingRight);
                fb.setPosition(x, y);
                inkBlobs.add(fb);
            }
        }
        for (int i = 0; i < inkBlobs.size(); i++){
            // do condition right here to remove ink ball if out of bounds?
           // System.out.print("x:"+inkBlobs.get(i).getX()+"\n");


            inkBlobs.get(i).update();
            if(inkBlobs.get(i).shouldRemove()){
                inkBlobs.remove(i);
                i--;
            }
        }
        //check done flinching
        if (flinching) {
            long elapsed = (System.nanoTime() - flinchTimer) / 1000000;
            if (elapsed > 1000) {
                flinching = false;
            }
        }
        
        // set animations
        if(pencilAttack) {
            if (currentAction != PENCILATTACK) {
                currentAction = PENCILATTACK;
                animation.setFrames(sprites.get(PENCILATTACK));
                animation.setDelay(50);
                width = 60;
            }
        }
        else if(throwingInk) {
            if(currentAction != INKBLOB) {
                currentAction = INKBLOB;
                animation.setFrames(sprites.get(INKBLOB));
                animation.setDelay(100);
                width = 30;
            }
        }
        else if (dy > 0){
            if(gliding) {
                if(currentAction != GLIDING) {
                    currentAction = GLIDING;
                    animation.setFrames(sprites.get(GLIDING));
                    animation.setDelay(100);
                    width = 30;
                }
            }
            else if (currentAction != FALLING) {
                currentAction = FALLING;
                animation.setFrames(sprites.get(FALLING));
                animation.setDelay(100);
                width = 30;
            }
        }
        else if (dy < 0) {
            if (currentAction != JUMPING) {
                currentAction = JUMPING;
                animation.setFrames(sprites.get(JUMPING));
                animation.setDelay(-1);
                width = 30;

            }
        }
        else if (left ||right) {
            if (currentAction != WALKING) {
                currentAction = WALKING;
                animation.setFrames(sprites.get(WALKING));
                animation.setDelay(40);
                width = 30;
            }
        }
        else {
            if (currentAction != IDLE) {
                currentAction = IDLE;
                animation.setFrames(sprites.get(IDLE));
                animation.setDelay(400);
                width = 30;
            }
        }
        animation.update();
        if(currentAction != PENCILATTACK && currentAction != INKBLOB){
            if(right) facingRight = true;
            if(left) facingRight = false;
        }
    }
    public void draw(Graphics2D g) {
        setMapPosition();
        //draw inkBlobs
        for (int i = 0; i < inkBlobs.size(); i++) {
            inkBlobs.get(i).draw(g);
        }
        if (flinching) {
            long elapsed = (System.nanoTime() - flinchTimer) / 1000000;
            if (elapsed / 100 % 2 == 0) {
                return;
            }
        }
        super.draw(g);
    }
}
