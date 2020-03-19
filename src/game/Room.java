/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

/**
 *
 * @author valleta
 */
public class Room {

    private int depth;
    private int height;
    private int width;
    private String textureBottom;
    private String textureNorth;
    private String textureEast;
    private String textureWest;
    private static String textureTop;
    private static String textureSouth;

    public Room() {
        depth = 100;
        height = 60;
        width = 100;
        textureBottom = "textures/floor/grass1.png";
        textureNorth = "textures/floor/clover1.png";
        textureEast = "textures/floor/clover1.png";
        textureWest = "textures/floor/clover1.png";
        
        
            
    }

    public int getDepth() {
        return depth;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public String getTextureBottom() {
        return textureBottom;
    }

    public void setTextureBottom(String textureBottom) {
        this.textureBottom = textureBottom;
    }

    public String getTextureNorth() {
        return textureNorth;
    }

    public void setTextureNorth(String textureNorth) {
        this.textureNorth = textureNorth;
    }

    public String getTextureEast() {
        return textureEast;
    }

    public void setTextureEast(String textureEast) {
        this.textureEast = textureEast;
    }

    public String getTextureWest() {
        return textureWest;
    }

    public void setTextureWest(String textureWest) {
        this.textureWest = textureWest;
    }

    public static String getTextureTop() {
        return textureTop;
    }

    public static void setTextureTop(String textureTop) {
        Room.textureTop = textureTop;
    }

    public static String getTextureSouth() {
        return textureSouth;
    }

    public static void setTextureSouth(String textureSouth) {
        Room.textureSouth = textureSouth;
    }

}
