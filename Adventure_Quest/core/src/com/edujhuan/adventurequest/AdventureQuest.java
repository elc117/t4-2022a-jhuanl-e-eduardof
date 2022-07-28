package com.edujhuan.adventurequest;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.edujhuan.adventurequest.Screens.PlayScreen;

public class AdventureQuest extends Game {

	public static final int V_WIDTH = 400;  // Virtual Screen size
	public static final int V_HEIGHT = 208; // Virtual Screen size
	public static final float PPM = 100;    // Box2D(Pixels Per Meter)

	//A gente vai ter q tratar as colisões 
	
	public SpriteBatch batch;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		setScreen(new PlayScreen(this));
	}

	@Override
	public void render () {
		super.render(); //delegate the render method to the PlayScreen whatever screen is active at the time
	}
	
}