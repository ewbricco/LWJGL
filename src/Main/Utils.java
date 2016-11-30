package Main;

import java.util.ArrayList;
import java.util.Random;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glVertex2f;

/**
 * Created by ebricco on 6/25/16.
 */
public class Utils {

    public enum Direction{
        LEFT, RIGHT, UP, DOWN
    }

    public static void refreshColor(Color color) {
        color = Color.invert(color);
        glClearColor(color.getRed(), color.getGreen(), color.getBlue(), 0);
    }

    public static void checkForProjectileCollision(Projectiles pro, Enemies ene) {
        ArrayList<Integer> proToRemove = new ArrayList<Integer>();
        ArrayList<Integer> eneToRemove = new ArrayList<Integer>();
        //for each projectile
        for (int i = 0; i < pro.getDirection().size(); i++) {
            //for each enemy
            for (int j = 0; j < ene.getColors().size(); j++) {
                //if projectile hits enemy
                if (Math.abs(pro.getXPos().get(i) - ene.getXPos().get(j)) < 5 && Math.abs(pro.getYPos().get(i) - ene.getYPos().get(j)) < 5) {
                    proToRemove.add(i);
                    eneToRemove.add(j);
                }
            }
        }
        for(int i: proToRemove){
            pro.remove(i);
        }
        for(int j:eneToRemove){
            ene.remove(j);
        }

    }

    public static long getTime() {
        return System.nanoTime() / 1000000;
    }
}
