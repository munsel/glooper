package de.glooper.game.Screens.MenuScreen.Animations;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;

/**
 * Created by munsel on 27.08.15.
 */
public class GlooperEyes {
    private Texture[] textures;
    private float stateTime;
    private  Texture currentTexture;

    public  GlooperEyes(){
        stateTime = 0;
        textures = new Texture[3];
        textures[0] = new Texture("Menu/glooper_open.png");
        textures[1] = new Texture("Menu/glooper_between.png");
        textures[2] = new Texture("Menu/glooper_closed.png");
    }

    public void update(float deltaTime){
        stateTime += deltaTime;
        if (stateTime < 3){
            currentTexture = textures[0];
            return;
        }else
            if (stateTime < 3.1f){
                currentTexture = textures[1];
                return;
            }else
                if (stateTime < 3.2f){
                    currentTexture = textures[2];
                    return;
                }else
                if (stateTime < 3.4f){
                    currentTexture = textures[1];
                    return;
                }
        stateTime = 0;
    }


    public Texture getCurrentTexture(){
        return currentTexture;
    }

}
