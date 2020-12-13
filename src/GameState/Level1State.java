package GameState;
import Entity.*;
import Entity.Drinks.Beverage;
import Entity.Drinks.Coffee;
import Main.GamePanel;
import TileMap.Background;
import TileMap.TileMap;
import java.awt.*;
import java.util.Random;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import Entity.Enemies.*;

public class Level1State extends GameState
{
    //instance variables
    private TileMap tileMap;
    private Background bg;
    private Player player;
    private ArrayList<Enemy> enemies;
    private ArrayList<Beverage> drinks;
    private ArrayList<Splat> inkBlobs;
    private HUD hud;
    
    public Level1State(GameStateManager gsm){
        //set a game state manager
        this.gsm = gsm;
        init();
    }
    
    public void init() {
        //initialize tilemap
        tileMap = new TileMap(30);
        tileMap.loadTiles("/Backgrounds/grasstileset.gif");
        tileMap.loadMap("/Maps/level1-1.map");
        tileMap.setPosition(0, 0);
        tileMap.setTween(1);//camera speed following player

        //set background image
        bg = new Background("/Backgrounds/night.gif", 0.1);
        player = new Player(tileMap);
        player.setPosition(100,100);

        //instantiating MapObjects
        populateEnemies();
        populateDrinks();
        inkBlobs = new ArrayList<Splat>();
        hud = new HUD(player);
    }
    private void populateEnemies(){
        int n;
        Random rand = new Random();
        //create enemy
        enemies = new ArrayList<Enemy>();
        // polymorphism
        Enemy assignment;
        //set enemy positions on the screen for each enemy in ArrayList
        Point[] points = new Point[] {
            new Point(200,100),
            new Point(860,200),
            new Point(1525, 200),
            new Point(1680, 200),
            new Point(1800, 200)
        };
        //loop through enemy ArrayList and add them to the screen
        for(int i= 0; i< points.length; i++) {
            n = rand.nextInt(3) + 1;
            switch(n) {
                case 1:
                    assignment = new CompAssignment(tileMap);
                    break;
                case 2:
                    assignment = new WritingAssignment(tileMap);
                    break;
                default:
                    assignment = new Assignment(tileMap);
                    break;
                    
            }
            
            // assignment = new Assignment(tileMap);
            assignment.setPosition(points[i].x,points[i].y);
            enemies.add(assignment);
        }

    }
    private void populateDrinks(){

        //create enemy
        drinks = new ArrayList<Beverage>();
        // polymorphism
        Beverage beverage;
        //set enemy positions on the screen for each enemy in ArrayList
        Point[] points = new Point[] {
                new Point(250,100),
                new Point(860,150),
                new Point(1525, 150),

        };
        //loop through enemy ArrayList and add them to the screen
        for(int i= 0; i< points.length; i++) {
            beverage = new Coffee(tileMap);

            beverage.setPosition(points[i].x,points[i].y);
            drinks.add(beverage);
        }

    }
    //update animations on the screen
    public void update() {
        //player wins, gets to the end of the map
        //goes here before i start a new game after i lost sometimes
        if(player.getX() > tileMap.getWidth()-15 && (tileMap.getWidth()-15)>0) {
            //set position back to default
            player.setPosition(100,100);
            System.out.println("you passed!");
            //go to passed screen
            gsm.setState(GameStateManager.PASSSTATE);
        }
        else if(player.getDead()){
            //player dies because 0 health (too many enemies)
            System.out.println("You were killed by the sheer amount of assignments, looks like you took on too much :(!");
            //reset to default value
            player.setDead(false);
            //idk why it says killed by enemies twice
            gsm.setState(GameStateManager.FAILURESTATE);
        }
        //die by falling off cliff
        else if (player.getY() > tileMap.getHeight()-15 && (tileMap.getHeight()-15)>0){
            System.out.println("You fell into a hole of procrastination!");
            //reset to default value
            player.setDead(false);
            //idk why it says killed by enemies twice
            gsm.setState(GameStateManager.PROCRASTINATIONSTATE);
        }
        //still playing
        else {
            player.update();
            tileMap.setPosition(GamePanel.WIDTH/2-player.getX(), GamePanel.HEIGHT/2-player.getY());
            //set background
            bg.setPosition(tileMap.getX(), tileMap.getY());
            // attack enemies
            player.checkAttack(enemies);
            player.checkBeverage(drinks);

            //update all enemies
            for(int i = 0; i < enemies.size(); i++){
                Enemy e = enemies.get(i);
                e.update();
                if(e.getDead()) {
                    //if killed then remove from list
                    enemies.remove(i);
                    i--;
                    inkBlobs.add(
                            //add explosion animation
                            new Splat((int) e.getX(), (int) e.getY(), tileMap)
                    );
                }
            }
            for(int i = 0; i < drinks.size(); i++){
                Beverage b = drinks.get(i);
                b.update();
                //if remove should remove
                if(b.getPickedUp()==true) {
                    drinks.remove(i);
                    i--;
                }

            }
            //update explosions
            for(int i = 0; i< inkBlobs.size(); i++){
                Splat e = inkBlobs.get(i);
                e.update();
                //after explosion happens remove it
                if(e.getRemove()) {
                    inkBlobs.remove(i);
                    i--;
                }
            }

        }


    }

    public void draw(Graphics2D g) {
        //draw bg
        bg.draw(g);

        // draw tilemap
        tileMap.draw(g);

        //draw player
        player.draw(g);

        //draw enemies
        for(int i=0; i<enemies.size(); i++){
            enemies.get(i).draw(g);
        }
        //draw explosions
        for(int i = 0; i< inkBlobs.size(); i++) {
            //call method in splat class
            inkBlobs.get(i).setMapPosition((int)tileMap.getX(), (int)tileMap.getY());
            inkBlobs.get(i).draw(g);
        }
        for(int i=0; i<drinks.size(); i++){
            drinks.get(i).draw(g);
        }
        //draw hud
        hud.draw(g);

    }
    public void keyPressed(int k) {
        if(k == KeyEvent.VK_LEFT) player.setLeft(true);
        if(k == KeyEvent.VK_RIGHT) player.setRight(true);
        if(k == KeyEvent.VK_UP) player.setUp(true);
        if(k == KeyEvent.VK_DOWN) player.setDown(true);
        if(k == KeyEvent.VK_W) player.setJumping(true);
        if(k == KeyEvent.VK_E) player.setGliding(true);
        if(k == KeyEvent.VK_R) player.setPencilAttack();
        if(k == KeyEvent.VK_F) player.setThrowingInk();
    }
    public void keyReleased(int k) {
        if(k == KeyEvent.VK_LEFT) player.setLeft(false);
        if(k == KeyEvent.VK_RIGHT) player.setRight(false);
        if(k == KeyEvent.VK_UP) player.setUp(false);
        if(k == KeyEvent.VK_DOWN) player.setDown(false);
        if(k == KeyEvent.VK_W) player.setJumping(false);
        if(k == KeyEvent.VK_E) player.setGliding(false);
    }
    
}
