package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class MyGdxGame extends ApplicationAdapter {
   SpriteBatch batch;
   Texture img;
   TextureRegion[] animationFrames;
   Animation<TextureRegion> animation;
   float elapsedTime;
   
   @Override
   public void create () {
      batch = new SpriteBatch();
      img = new Texture("character_pose.png");

      TextureRegion[][] tmpFrames = TextureRegion.split(img,1728,1280);

      animationFrames = new TextureRegion[45];
      int index = 0;

      for (int i = 0; i < 5; i++){
         for(int j = 0; j < 9; j++) {
            animationFrames[index++] = tmpFrames[j][i];
         }
      }

      animation = new Animation<TextureRegion>(1f/15f,animationFrames);
   }

   @Override
   public void render () {
      elapsedTime += Gdx.graphics.getDeltaTime();
      Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
      batch.begin();
      batch.draw(animation.getKeyFrame(elapsedTime,true),0,0);
      batch.end();
   }
}
