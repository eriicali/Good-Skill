package GameState;
import TileMap.Background;
import TileMap.TileMap;
import java.awt.*;

public class Level1State extends GameState {
    private TileMap tileMap;
    private Background bg;
    
    public Level1State(GameStateManager gsm){
        this.gsm = gsm;
        init();
    }
    
    public void init() {
        tileMap = new TileMap(30);
        tileMap.loadTiles("/Backgrounds/grasstileset.gif");
        tileMap.loadMap("/Maps/level1-1.map");
        tileMap.setPosition(0, 0);

        bg = new Background("/Backgrounds/grassbg1.gif", 0.1);

    }
    public void update() {}
    public void draw(Graphics2D g) {
        //draw bg
        bg.draw(g);

        // draw tilemap
        tileMap.draw(g);
    }
    public void keyPressed(int k) {}
    public void keyReleased(int k) {}
    
}
