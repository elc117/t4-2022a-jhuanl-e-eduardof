package com.edujhuan.adventurequest.Scenes;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.edujhuan.adventurequest.AdventureQuest;

public class Hud implements Disposable{
    public Stage stage;        // when the game moves, the hud must stay in the same position.
    private Viewport viewport; // we need to use a new camera and a new viewport for the hud.
    
    // game variables
    private Integer worldTimer;
    private boolean timeUp;
    private float timeCount;
    private static Integer score;

    // game widgets
    private Label countdownLabel;
    private static Label scoreLabel;
    private Label timeLabel;
    private Label levelLabel;
    private Label stageLabel;
    private Label characterLabel;

    public Hud(SpriteBatch batch){
        // define the game variables
        worldTimer = 300;
        timeCount = 0;
        score = 0;

        // setting the hud viewport with a new camera
        viewport = new FitViewport(AdventureQuest.V_WIDTH, AdventureQuest.V_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, batch);  // define stage using the new viweport
        Table table = new Table(); // Table helps to organize the labels of the hud
        table.top(); // labels at the top of the screen
        table.setFillParent(true); // table is the size of the stage

        // define the label using a string, a LabelStyle and a color
        countdownLabel = new Label(String.format("%03d", worldTimer), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        scoreLabel = new Label(String.format("%06d", score), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        timeLabel = new Label("Time", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        levelLabel = new Label("1", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        stageLabel = new Label("Stage", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        characterLabel = new Label("YourName", new Label.LabelStyle(new BitmapFont(), Color.WHITE));

        // add the labels in the table
        table.add(characterLabel).expandX().padTop(10); 
        table.add(stageLabel).expandX().padTop(10);
        table.add(timeLabel).expandX().padTop(10);
        table.row(); // create a new row
        table.add(scoreLabel).expandX();
        table.add(levelLabel).expandX();
        table.add(countdownLabel).expandX();

        stage.addActor(table); // add the table in the stage
    }

    public void update(float dt){
        timeCount += dt;
        if(timeCount >= 1){
            if (worldTimer > 0) {
                worldTimer--;
            } else {
                timeUp = true;
            }
            countdownLabel.setText(String.format("%03d", worldTimer));
            timeCount = 0;
        }
    }

    public static void addScore(int value){
        score += value;
        scoreLabel.setText(String.format("%06d", score));
    }

    @Override
    public void dispose(){
        stage.dispose();
    }

    public boolean isTimeUp() { 
        return timeUp; 
    }
}