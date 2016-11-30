package Main;

import java.util.ArrayList;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glVertex2f;


/**
 * Created by ebricco on 6/25/16.
 */
public class Projectiles {
    private final int MOVEMENTSPEED = 5;
    private final int TIMEBETWEENCLEAN = 5000;
    private final int TIMEBETWEENSHOTS = 125;

    public enum Direction {
        LEFT, RIGHT, UP, DOWN
    }

    private ArrayList<Integer> xPos;
    private ArrayList<Integer> yPos;
    private ArrayList<Direction> direction;
    private ArrayList<Color> colors;
    private Long lastMovement;
    private Long lastClean;
    private Long lastShot;
    public Boolean shootingUp;
    public Boolean shootingDown;
    public Boolean shootingRight;
    public Boolean shootingLeft;

    public Projectiles() {
        xPos = new ArrayList<Integer>();
        yPos = new ArrayList<Integer>();
        direction = new ArrayList<Direction>();
        lastMovement = Utils.getTime();
        colors = new ArrayList<Color>();
        lastClean = lastMovement;
        resetShootingBooleans();
        lastShot = 0L;
    }

    public void moveProjectiles() {
        for (int i = 0; i < direction.size(); i++) {
            if (direction.get(i) == Direction.RIGHT) {
                xPos.set(i, xPos.get(i) + MOVEMENTSPEED);
            }
            if (direction.get(i) == Direction.LEFT) {
                xPos.set(i, xPos.get(i) - MOVEMENTSPEED);
            }
            if (direction.get(i) == Direction.UP) {
                yPos.set(i, yPos.get(i) + MOVEMENTSPEED);
            }
            if (direction.get(i) == Direction.DOWN) {
                yPos.set(i, yPos.get(i) - MOVEMENTSPEED);
            }
        }
    }

    public void checkInput(int key, int action) {
        boolean shootingKey=false;
        if (key == GLFW_KEY_D) {
            shootingKey = true;
            shootingRight = true;
        }

        if (key == GLFW_KEY_A) {
            shootingKey = true;
            shootingLeft = true;
        }

        if (key == GLFW_KEY_W) {
            shootingKey = true;
            shootingUp = true;
        }

        if (key == GLFW_KEY_S) {
            shootingKey = true;
            shootingDown = true;
        }

        if(shootingKey && action == GLFW_RELEASE){
            resetShootingBooleans();
        }
    }

    public void launch(Direction d, int launchPointX, int launchPointY, float red, float green, float blue) {
        if (Utils.getTime()-lastShot>TIMEBETWEENSHOTS) {
            lastShot = Utils.getTime();
            colors.add(new Color(red, green, blue));
            xPos.add(Math.round(launchPointX));
            yPos.add(Math.round(launchPointY));
            direction.add(d);
        }
    }


    public void render() {
        for (int i = 0; i < direction.size(); i++) {
            glBegin(GL_QUADS);
            {
                glColor3f(colors.get(i).getRed(), colors.get(i).getGreen(), colors.get(i).getBlue());
                glVertex2f(xPos.get(i) + 1, yPos.get(i) + 1);
                glVertex2f(xPos.get(i) - 1, yPos.get(i) + 1);
                glVertex2f(xPos.get(i) + 1, yPos.get(i) - 1);
                glVertex2f(xPos.get(i) - 1, yPos.get(i) - 1);
            }
            glEnd();
        }
    }

    public void update(long time, MainCharacter mc, float red, float green, float blue) {
        if (time - lastMovement > 1) {
            moveProjectiles();
            lastMovement = time;
        }
        if (time - lastClean > TIMEBETWEENCLEAN) {
            clean();
        }

        if(shootingRight){
            launch(Direction.RIGHT, mc.getX() + (mc.getWidth()/2), mc.getY() + (mc.getWidth()/2), red, green, blue);
        }
        if(shootingLeft){
            launch(Direction.LEFT, mc.getX() + (mc.getWidth()/2), mc.getY() + (mc.getWidth()/2), red, green, blue);
        }
        if(shootingUp){
            launch(Direction.UP, mc.getX() + (mc.getWidth()/2), mc.getY() + (mc.getWidth()/2), red, green, blue);
        }
        if(shootingDown){
            launch(Direction.DOWN, mc.getX() + (mc.getWidth()/2), mc.getY() + (mc.getWidth()/2), red, green, blue);
        }
    }

    public ArrayList<Integer> getXPos() {
        return xPos;
    }

    public ArrayList<Integer> getYPos() {
        return yPos;
    }

    public ArrayList<Direction> getDirection() {
        return direction;
    }

    public void setXPos(ArrayList<Integer> xPos) {
        this.xPos = xPos;
    }

    public void setYPos(ArrayList<Integer> yPos) {
        this.yPos = yPos;
    }

    public void setDirection(ArrayList<Direction> direction) {
        this.direction = direction;
    }

    public void remove(int i) {
        if (i < xPos.size()) {
            xPos.remove(i);
            yPos.remove(i);
            direction.remove(i);
            colors.remove(i);
        }
    }

    private void clean() {
        for (int i = 0; i < xPos.size(); i++) {
            if (xPos.get(i) > GameState.WIDTH || xPos.get(i) < 0 || yPos.get(i) > GameState.HEIGHT || yPos.get(i) < 0) {
                remove(i);
            }
        }
    }

    public void resetShootingBooleans(){
        shootingRight = false;
        shootingLeft = false;
        shootingUp = false;
        shootingDown = false;
    }
}
