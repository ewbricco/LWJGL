package Main;

import org.newdawn.slick.Game;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glVertex2f;

/**
 * Created by ebricco on 6/25/16.
 */
public class MainCharacter {
    private final float MOVEMENTSPEED = 2.5f;
    private final int TIMEBETWEENMOVEMENT = 10;
    private final int TIMEBETWEENMOVEMENTCHECK = 40;

    private final int WIDTH = 41;
    private final int HEIGHT = 41;
    private final int STARTPOINTX = 0;
    private final int STARTPOINTY = 0;


    private int xPos;
    private int yPos;
    private boolean moveUp;
    private boolean moveDown;
    private boolean moveRight;
    private boolean moveLeft;
    private Long lastMovement;
    private float red;
    private float green;
    private float blue;
    private long lastKey;


    public MainCharacter(){
        resetMovementBooleans();
        xPos=STARTPOINTX;
        yPos=STARTPOINTY;
        lastMovement = Utils.getTime();
        lastKey =Utils.getTime();
    }

    public void moveUp(){
        if(red<.995){
            red+=.005;
        }
        if(blue>.995){
            blue-=.005;
        }
        if(yPos<GameState.HEIGHT-MOVEMENTSPEED-HEIGHT){
            yPos+=MOVEMENTSPEED;
        }
        else{
            yPos=GameState.HEIGHT-HEIGHT;
        }
    }

    public void moveDown(){
        if(red>.005){
            red-=.005;
        }
        if(blue<.995){
            blue+=.005;
        }
        if(yPos>MOVEMENTSPEED){
            yPos-=MOVEMENTSPEED;
        }
        else{
            yPos=0;
        }
    }

    public void moveRight(){
        if(green<.995){
            green+=.005;
        }
        if(red>.005){
            red-=.005;
        }
        if(xPos< GameState.WIDTH - MOVEMENTSPEED - WIDTH){
            xPos+=MOVEMENTSPEED;
        }
        else{
            xPos=GameState.WIDTH - WIDTH;
        }
    }
    public void moveLeft() {
        if(red<.995){
            red+=.005;
        }
        if(green>.005){
            green-=.005;
        }
        if(xPos>MOVEMENTSPEED){
            xPos-=MOVEMENTSPEED;
        }
        else{
            xPos=0;
        }
    }
    public void render(){
        glColor3f(red, green, blue);
        glBegin(GL_POLYGON);
        {
            glVertex2f(xPos,yPos);
            glVertex2f(xPos+WIDTH,yPos);
            glVertex2f(xPos,yPos+HEIGHT);
            glVertex2f(xPos+WIDTH,yPos+HEIGHT);
        }
        glEnd();
    }

    public void resetMovementBooleans(){
        moveRight = false;
        moveLeft = false;
        moveDown = false;
        moveUp = false;
    }

    public void moveMainCharacter(){
        if(moveRight){
            moveRight();
        }
        else if(moveLeft){
            moveLeft();
        }
        else if(moveDown){
            moveDown();
        }
        else if(moveUp){
            moveUp();
        }
    }

    public void checkInput(int key, int action){
        if (key == GLFW_KEY_UP && action == GLFW_PRESS) {
            resetMovementBooleans();
            moveUp = true;
        }
        else if (key == GLFW_KEY_DOWN && action == GLFW_PRESS){
            resetMovementBooleans();
            moveDown = true;
        }
        else if (key == GLFW_KEY_RIGHT && action == GLFW_PRESS) {
            resetMovementBooleans();
            moveRight = true;
        }
        else if (key == GLFW_KEY_LEFT && action == GLFW_PRESS) {
            resetMovementBooleans();
            moveLeft = true;
        }
        if(action == GLFW_RELEASE && (key == GLFW_KEY_DOWN || key == GLFW_KEY_RIGHT || key == GLFW_KEY_LEFT || key == GLFW_KEY_UP)){
            resetMovementBooleans();
        }
    }

    public void update(long time){
        if(time - lastMovement > TIMEBETWEENMOVEMENT){
            moveMainCharacter();
            lastMovement=time;
            /*if(time - lastKey > TIMEBETWEENMOVEMENTCHECK) {
                //lastKey != GLFW_KEY_UP && lastKey != GLFW_KEY_DOWN && lastKey != GLFW_KEY_RIGHT && lastKey != GLFW_KEY_LEFT
                resetMovementBooleans();
            }*/
        }
    }

    public int getX(){
        return xPos;
    }

    public int getY(){
        return yPos;
    }

    public int getWidth(){
        return WIDTH;
    }

    public int getHeight(){
        return HEIGHT;
    }

    public Color getColor(){
        Color color = new Color(red, green, blue);
        return color;
    }
}
