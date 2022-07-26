package com.edujhuan.adventurequest;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.edujhuan.adventurequest.Screens.PlayScreen;

public class AdventureQuest extends Game {
	public static final int V_WIDTH = 400;
	public static final int V_HEIGHT = 208;
	public static final int PPM = 100; //Pixels Per Meter
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