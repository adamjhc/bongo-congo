package com.knightlore.hud.engine.graphics;

import org.joml.Matrix4f;
import static org.lwjgl.opengl.GL11.*;
import com.knightlore.hud.engine.GameItem;
import com.knightlore.hud.engine.IHud;
import com.knightlore.hud.engine.Utils;
import com.knightlore.hud.engine.Window;

public class HUDRenderer {

    private final Transformation transformation;
    
    private HUDShaderProgram hudShaderProgram;


    public HUDRenderer() {
        transformation = new Transformation();
    }

    public void init(Window window) throws Exception {
        setupHudShader();
    }

    private void setupHudShader() throws Exception {
        hudShaderProgram = new HUDShaderProgram();
        hudShaderProgram.createVertexShader(Utils.loadResource("/shaders/hud_vertex.vs"));
        hudShaderProgram.createFragmentShader(Utils.loadResource("/shaders/hud_fragment.fs"));
        hudShaderProgram.link();

        hudShaderProgram.createUniform("projModelMatrix");
        hudShaderProgram.createUniform("colour");
        hudShaderProgram.createUniform("hasTexture");
    }

    public void clear() {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
    }

    public void render(Window window, IHud hud) {

        clear();

        renderHud(window, hud);
    }

    private void renderHud(Window window, IHud hud) {
        hudShaderProgram.bind();

        Matrix4f ortho = transformation.getOrthoProjectionMatrix(0, window.getWidth(), window.getHeight(), 0);
        for (GameItem gameItem : hud.getGameItems()) {
            Mesh mesh = gameItem.getMesh();

            Matrix4f projModelMatrix = transformation.getOrtoProjModelMatrix(gameItem, ortho);
            hudShaderProgram.setUniform("projModelMatrix", projModelMatrix);
            hudShaderProgram.setUniform("colour", gameItem.getMesh().getMaterial().getColour());
            hudShaderProgram.setUniform("hasTexture", gameItem.getMesh().getMaterial().isTextured() ? 1 : 0);

            mesh.render();
        }

        hudShaderProgram.unbind();
    }

    public void cleanup() {
        if (hudShaderProgram != null) {
            hudShaderProgram.cleanup();
        }
    }
}
