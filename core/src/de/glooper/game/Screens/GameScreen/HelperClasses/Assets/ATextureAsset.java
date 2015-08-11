package de.glooper.game.Screens.GameScreen.HelperClasses.Assets;

import com.badlogic.gdx.graphics.Texture;

/**
 * Created by munsel on 10.08.15.
 */
public abstract class ATextureAsset {

          private Texture texture;

          public ATextureAsset( Texture texture){
               this.texture = texture;
          }



          public Texture getTexture() {
               return texture;
          }




}
