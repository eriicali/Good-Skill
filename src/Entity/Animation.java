package Entity;
import java.awt.image.BufferedImage;
//handle sprite animation
public class Animation {
    //animation vars
    private BufferedImage[] frames;
    private int currentFrame;

    private long startTime;
    private long delay;
    //only want certain animations to play once
    private boolean playedOnce;

    public Animation() {
        playedOnce = false;
    }
    // this method sets the frames of the animation by taking in an array of images
    public void setFrames(BufferedImage[] frames) {
        this.frames = frames;
        currentFrame = 0;
        startTime = System.nanoTime();
        playedOnce = false;
    }

    // this method updates the animation by incrementing the current frame by 1
    public void update() {
        if (delay == -1) return;
        //get elapsed time from computer's system time
        long elapsed = (System.nanoTime() - startTime) / 1000000;
        if (elapsed > delay) {
            currentFrame++;
            startTime = System.nanoTime();
        }
        if (currentFrame == frames.length) {
            currentFrame = 0;
            playedOnce = true;
        }
    }

    public BufferedImage getImage() { return frames[currentFrame]; }
    public boolean hasPlayedOnce() {return playedOnce; }
    public void setDelay(long d) {delay = d;}
}
