package Main;

import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.*;

import java.awt.Font;
import java.io.InputStream;

import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.util.ResourceLoader;

public class Main {
    public static long window;
    public static MainCharacter mc;
    public static Enemies enemies;
    public static Projectiles projectiles;

    public static void main(String[] args) {
        mc = new MainCharacter();
        enemies = new Enemies();
        projectiles = new Projectiles();
        try {
            initGLFW();
            initGL();
            //initFont();
            loop();
            glfwFreeCallbacks(window);
            glfwDestroyWindow(window);
        } finally {
            glfwTerminate();
            glfwSetErrorCallback(null).free();
        }
    }

    private static void initGLFW() {
        GLFWErrorCallback.createPrint(System.err).set();

        if (!glfwInit())
            throw new IllegalStateException("Unable to initialize GLFW");

        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);
        window = glfwCreateWindow(GameState.WIDTH, GameState.HEIGHT, GameState.NAME, NULL, NULL);

        if (window == NULL)
            throw new RuntimeException("Failed to create the GLFW window");

        glfwSetKeyCallback(window, (window, key, scancode, action, mods) -> {
            if (key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE)
                glfwSetWindowShouldClose(window, true);

            mc.checkInput(key, action);
            projectiles.checkInput(key, action);
        });

        GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
        glfwSetWindowPos(
                window,
                (vidmode.width() - GameState.WIDTH) / 2,
                (vidmode.height() - GameState.HEIGHT) / 2
        );

        glfwMakeContextCurrent(window);
        glfwSwapInterval(1);
        glfwShowWindow(window);
    }

    private static void initGL(){
        GL.createCapabilities();
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        glOrtho(0, GameState.WIDTH,0, GameState.HEIGHT, -1, 1);
        glMatrixMode(GL_MODELVIEW);
        glDisable(GL_DEPTH_TEST);
    }


    private static void loop() {
        GL.createCapabilities();
        while (!glfwWindowShouldClose(window)) {
            //long testTime = System.nanoTime()/1000000;
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer
            /*font.drawString(100, 50, "THE LIGHTWEIGHT JAVA GAMES LIBRARY", Color.yellow);
            font2.drawString(100, 100, "NICE LOOKING FONTS!", Color.green);*/
            glfwPollEvents();
            Long time = Utils.getTime();
            Utils.checkForProjectileCollision(projectiles, enemies);
            glfwPollEvents();
            mc.update(time);
            mc.render();
            enemies.update(time, mc);
            enemies.render();
            glfwPollEvents();
            projectiles.update(time, mc, mc.getColor().getRed(), mc.getColor().getGreen(), mc.getColor().getBlue());
            projectiles.render();
            Utils.refreshColor(mc.getColor());
            glfwPollEvents();

            glfwSwapBuffers(window); // swap the color buffers
            //System.out.println((System.nanoTime()/1000000)-time);
        }
    }
}