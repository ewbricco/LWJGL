package Zombies;

import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.*;

import java.awt.Font;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

public class Run {
    private final static int WINDOWWIDTH = 2560;
    private final static int WINDOWHEIGHT = 1600;
    public static long window;

    public static void main(String[] args) {
        try {
            initGLFW();
            initGL();
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
        window = glfwCreateWindow(WINDOWWIDTH, WINDOWHEIGHT, "Zombies", NULL, NULL);

        if (window == NULL)
            throw new RuntimeException("Failed to create the GLFW window");

        glfwSetKeyCallback(window, (window, key, scancode, action, mods) -> {
            if (key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE)
                glfwSetWindowShouldClose(window, true);
        });

        GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
        glfwSetWindowPos(
                window,
                (vidmode.width() - WINDOWWIDTH) / 2,
                (vidmode.height() - WINDOWHEIGHT) / 2
        );

        glfwMakeContextCurrent(window);
        glfwSwapInterval(1);
        glfwShowWindow(window);
    }

    private static void initGL(){
        GL.createCapabilities();
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        glOrtho(0, WINDOWWIDTH,0, WINDOWHEIGHT, -1, 1);
        glMatrixMode(GL_MODELVIEW);
        glDisable(GL_DEPTH_TEST);
    }


    private static void loop() {
        System.out.println("reaches loop");
        GL.createCapabilities();
        System.out.println("reaches textureload");
        Texture grass = loadTexture("grassBitmap.png");
        System.out.println("texture loaded");
        while (!glfwWindowShouldClose(window)) {
            //long testTime = System.nanoTime()/1000000;
            System.out.println("texture width: " + grass.getHeight());
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer
            glfwPollEvents();
            glEnable(GL_TEXTURE_2D);
            grass.bind();
            glBegin(GL_QUAD_STRIP);
            {
                glTexCoord2f(1,1);
                glVertex2f(2560,1600);
                glTexCoord2f(1,0);
                glVertex2f(2560,0);
                glTexCoord2f(0,1);
                glVertex2f(0,1600);
                glTexCoord2f(0,0);
                glVertex2f(0,0);
            }
            glEnd();

            glfwSwapBuffers(window); // swap the color buffers
            //System.out.println((System.nanoTime()/1000000)-time);
        }
    }

    public static Texture loadTexture(String key) {
        try {
            System.out.println("returns texture");
            return TextureLoader.getTexture("png", new FileInputStream(new File("/Users/ebricco/Programming/ManagementGame/" + key)));
        } catch(Exception e){
            System.out.println("exception with texture loading");
            System.exit(777);
        }
        System.out.println("texture loading returns null");
        return null;
    }
}