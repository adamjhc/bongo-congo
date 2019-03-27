package com.knightlore.client.gui.engine;

import java.util.ArrayList;
import java.util.List;

import org.joml.Vector4f;

import com.knightlore.client.gui.engine.graphics.FontTexture;
import com.knightlore.client.gui.engine.graphics.Material;
import com.knightlore.client.gui.engine.graphics.Mesh;

/**
 * Object to render on the screen that has text
 * 
 * @author Joseph
 *
 */
public class TextObject extends GuiObject {

		/** Fixed z position */
    private static final float ZPOS = 0.0f;
    /** Number of vertices required */
    private static final int VERTICES_PER_QUAD = 4;

    /** Font texture used */
    private FontTexture fontTexture;
    /** Text to draw */
    private String text;
    /** id to uniquely identify */
    private String id;

    /**
     * Initialise the object
     * 
     * @param text The text to draw
     * @param fontTexture The font texture used
     * @author Joseph
     * 
     */
    public TextObject(String text, FontTexture fontTexture) {
        super();
        this.text = text;
        this.id = text;
        this.fontTexture = fontTexture;
        setMesh(buildMesh());
    }
    
    /**
     * Builds the mesh
     * Quad is made up of a set of tiles each one being a single character
     * Assigns the texture coordinates depending on character
     * Loops through for each character
     * 
     * @return Mesh
     * @author Joseph
     * 
     */
    private Mesh buildMesh() {
        List<Float> positions = new ArrayList();
        List<Float> textCoords = new ArrayList();
        float[] normals   = new float[0];
        List<Integer> indices   = new ArrayList();
        char[] characters = text.toCharArray();
        int numChars = characters.length;

        float startx = 0;
        for(int i=0; i<numChars; i++) {
            FontTexture.CharInfo charInfo = fontTexture.getCharInfo(characters[i]);
            
            // Build a character tile composed by two triangles
            
            // Left Top vertex
            positions.add(startx); // x
            positions.add(0.0f); //y
            positions.add(ZPOS); //z
            textCoords.add( (float)charInfo.getStartX() / (float)fontTexture.getWidth());
            textCoords.add(0.0f);
            indices.add(i*VERTICES_PER_QUAD);
                        
            // Left Bottom vertex
            positions.add(startx); // x
            positions.add((float)fontTexture.getHeight()); //y
            positions.add(ZPOS); //z
            textCoords.add((float)charInfo.getStartX() / (float)fontTexture.getWidth());
            textCoords.add(1.0f);
            indices.add(i*VERTICES_PER_QUAD + 1);

            // Right Bottom vertex
            positions.add(startx + charInfo.getWidth()); // x
            positions.add((float)fontTexture.getHeight()); //y
            positions.add(ZPOS); //z
            textCoords.add((float)(charInfo.getStartX() + charInfo.getWidth() )/ (float)fontTexture.getWidth());
            textCoords.add(1.0f);
            indices.add(i*VERTICES_PER_QUAD + 2);

            // Right Top vertex
            positions.add(startx + charInfo.getWidth()); // x
            positions.add(0.0f); //y
            positions.add(ZPOS); //z
            textCoords.add((float)(charInfo.getStartX() + charInfo.getWidth() )/ (float)fontTexture.getWidth());
            textCoords.add(0.0f);
            indices.add(i*VERTICES_PER_QUAD + 3);
            
            // Add indices por left top and bottom right vertices
            indices.add(i*VERTICES_PER_QUAD);
            indices.add(i*VERTICES_PER_QUAD + 2);
            
            startx += charInfo.getWidth();
        }

        float[] posArr = Utils.listToArray(positions);
        float[] textCoordsArr = Utils.listToArray(textCoords);
        int[] indicesArr = indices.stream().mapToInt(i->i).toArray();
        Mesh mesh = new Mesh(posArr, textCoordsArr, normals, indicesArr);
        mesh.setMaterial(new Material(fontTexture.getTexture()));
        return mesh;
    }

    /**
     * Return the text
     * 
     * @return Text
     * @author Joseph
     * 
     */
    public String getText() {
        return text;
    }
    
    /**
     * Set the text
     * Save old colour before deleting and rebuilding mesh
     * 
     * @param text The new text
     * @author Joseph
     * 
     */
    public void setText(String text) {
        this.text = text;
        Vector4f colour = getColour();
        
        this.getMesh().deleteBuffers();
        this.setMesh(buildMesh());
        
        this.setColour(colour);
    }

    /**
     * Return text size
     * 
     * @return Length multiplied by height
     * @author Joseph
     * 
     */
    public float getSize() {
    	return text.length()*fontTexture.getHeight();
    }
    
    /**
     * Returns text height
     * 
     * @return Height
     * @author Joseph
     * 
     */
    public int getHeight() {
    	return fontTexture.getHeight();
    }
    
    /**
     * Sets the font texture
     * 
     * 
     * @param fontTexture The new font texture
     * @author Joseph
     * 
     */
    public void setFontTexture(FontTexture fontTexture) {
    	this.fontTexture = fontTexture;
    	Vector4f colour = getColour();
    	
      this.getMesh().deleteBuffers();
      this.setMesh(buildMesh());
      
      this.setColour(colour);
    }
    
    /**
     * Sets the id
     * 
     * @param id The new id
     * @author Joseph
     * 
     */
    public void setId(String id) {
    	this.id = id;
    }
    
    /**
     * Returns id
     * 
     * @return Id
     * @author Joseph
     * 
     */
    public String getId() {
    	return id;
    }
}