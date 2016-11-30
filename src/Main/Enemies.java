package Main;

import org.newdawn.slick.Game;

import java.util.ArrayList;

import static org.lwjgl.opengl.GL11.*;

/**
 * Created by ebricco on 6/25/16.
 */
public class Enemies {
    private final int TIMEBETWEENMOVEMENTS = 200;
    private final int TIMEBETWEENGENERATION = 1500;
    private final int MOVEMENTSPEED = 1;
    private final int WIDTH = 7;
    private final int HEIGHT = 7;

    private ArrayList<Integer> xPos;
    private ArrayList<Integer> yPos;
    private ArrayList<ArrayList<Float>> colors;
    private Long lastMovement;
    private Long lastGeneration;


    public Enemies() {
        colors = new ArrayList<ArrayList<Float>>();
        xPos = new ArrayList<Integer>();
        yPos = new ArrayList<Integer>();
        lastMovement = Utils.getTime();
        lastGeneration = Utils.getTime();
    }

    public void move(MainCharacter mc) {
        for (int i = 0; i < xPos.size(); i++) {
            if (mc.getX() < xPos.get(i)) {
                xPos.set(i, xPos.get(i) - MOVEMENTSPEED);
            } else {
                xPos.set(i, xPos.get(i) + MOVEMENTSPEED);
            }
            if (mc.getY() < yPos.get(i)) {
                yPos.set(i, yPos.get(i) - MOVEMENTSPEED);
            } else {
                yPos.set(i, yPos.get(i) + MOVEMENTSPEED);
            }
        }
    }

    public void add(MainCharacter mc) {
        int x = GameState.RAND.nextInt(GameState.WIDTH - WIDTH/2 -1)+WIDTH/2 + 1;
        int y = GameState.RAND.nextInt(GameState.HEIGHT - HEIGHT/2 -1) + HEIGHT/2 + 1;
        xPos.add(x);
        yPos.add(y);
        ArrayList<Float> color = new ArrayList<Float>();
        color.add(GameState.RAND.nextFloat());
        color.add(GameState.RAND.nextFloat());
        color.add(GameState.RAND.nextFloat());
        //color.add(1-mc.getColor().getRed());
        //color.add(1-mc.getColor().getGreen());
        //color.add(1-mc.getColor().getBlue());
        colors.add(color);
    }

    public void render() {
        for (int i = 0; i < xPos.size(); i++) {
            glBegin(GL_TRIANGLES);
            {
                glColor3f(colors.get(i).get(0), colors.get(i).get(1), colors.get(i).get(2));
                glVertex2f(xPos.get(i) + WIDTH/2, yPos.get(i) + HEIGHT/2);
                glVertex2f(xPos.get(i) - WIDTH/2, yPos.get(i) + HEIGHT/2);
                glVertex2f(xPos.get(i) + WIDTH/2, yPos.get(i) - HEIGHT/2);
                //glVertex2f(xPos.get(i) - WIDTH/2, yPos.get(i) - HEIGHT/2);
            }
            glEnd();
        }
    }

    public void update(long time, MainCharacter mc) {
        if (time - lastGeneration > TIMEBETWEENGENERATION) {
            add(mc);
            lastGeneration = time;
        }
        if (time - lastMovement > TIMEBETWEENMOVEMENTS) {
            move(mc);
            lastMovement = time;
        }
    }

    public ArrayList<Integer> getXPos() {
        return xPos;
    }

    public ArrayList<Integer> getYPos() {
        return yPos;
    }

    public ArrayList<ArrayList<Float>> getColors() {
        return colors;
    }

    public void setXPos(ArrayList<Integer> xPos) {
        this.xPos = xPos;
    }

    public void setYPos(ArrayList<Integer> yPos) {
        this.yPos = yPos;
    }

    public void setColors(ArrayList<ArrayList<Float>> colors) {
        this.colors = colors;
    }

    public void remove(int i){
        if(i<xPos.size()) {
            xPos.remove(i);
            yPos.remove(i);
            colors.remove(i);
        }
    }
}
