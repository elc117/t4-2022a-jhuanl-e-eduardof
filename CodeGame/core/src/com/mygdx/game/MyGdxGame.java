package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.OrthographicCamera;

public class MyGdxGame extends ApplicationAdapter {

	// Constant rows and columns of the sprite sheet
	private static final int FRAME_COLS = 8, FRAME_ROWS = 1;

	// Objects used
	Animation<TextureRegion> frontAnimation; // Must declare frame type (TextureRegion)
	Texture frontSheet;
	SpriteBatch spriteBatch;
	OrthographicCamera camera;
	private Rectangle person;

	// A variable for tracking elapsed time for the animation
	float stateTime;

	@Override
	public void create() {

		// Load the sprite sheet as a Texture
		frontSheet = new Texture(Gdx.files.internal("front.png"));

		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 480);

		// Use the split utility method to create a 2D array of TextureRegions. This is
		// possible because this sprite sheet contains frames of equal size and they are
		// all aligned.
		TextureRegion[][] tmp = TextureRegion.split(frontSheet,
				frontSheet.getWidth() / FRAME_COLS,
				frontSheet.getHeight() / FRAME_ROWS);

		// Place the regions into a 1D array in the correct order, starting from the top
		// left, going across first. The Animation constructor requires a 1D array.
		TextureRegion[] frontFrames = new TextureRegion[8];
		int index = 0;

		frontFrames[index++] = tmp[0][1];
		frontFrames[index++] = tmp[0][2];
		frontFrames[index++] = tmp[0][3];
		frontFrames[index++] = tmp[0][4];
		frontFrames[index++] = tmp[0][5];
		frontFrames[index++] = tmp[0][6];
		frontFrames[index++] = tmp[0][7];
		frontFrames[index++] = tmp[0][2];

		// Initialize the Animation with the frame interval and array of frames
		frontAnimation = new Animation<TextureRegion>(1f/10f, frontFrames);
		person = new Rectangle();
		person.x = 200 / 2 - 64 / 2; // center the person horizontally
		person.y = 60; // bottom left corner of the person is 20 pixels above the bottom screen edge
		person.width = 64;
		person.height = 64;

		// Instantiate a SpriteBatch for drawing and reset the elapsed animation
		// time to 0
		spriteBatch = new SpriteBatch();
		stateTime = 0f;
	}

	@Override
	public void render() {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); // Clear screen
		stateTime += Gdx.graphics.getDeltaTime(); // Accumulate elapsed animation time

		// Get current frame of animation for the current stateTime
		TextureRegion currentFrame = frontAnimation.getKeyFrame(stateTime, true);
		spriteBatch.begin();
		spriteBatch.draw(currentFrame, person.x, person.y); // Draw current frame at (50, 50)
		spriteBatch.end();

		if(Gdx.input.isKeyPressed(Keys.LEFT)) person.x -= 200 * Gdx.graphics.getDeltaTime();
		if(Gdx.input.isKeyPressed(Keys.RIGHT)) person.x += 200 * Gdx.graphics.getDeltaTime();
		if(Gdx.input.isKeyPressed(Keys.DOWN)) person.y -= 200 * Gdx.graphics.getDeltaTime();
		if(Gdx.input.isKeyPressed(Keys.UP)) person.y += 200 * Gdx.graphics.getDeltaTime();
	}

	@Override
	public void dispose() { // SpriteBatches and Textures must always be disposed
		spriteBatch.dispose();
		frontSheet.dispose();
	}
}
