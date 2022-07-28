package com.edujhuan.adventurequest.Screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.edujhuan.adventurequest.AdventureQuest;
import com.edujhuan.adventurequest.Scenes.Hud;
import com.edujhuan.adventurequest.Sprites.Player;
import com.edujhuan.adventurequest.Tools.B2WorldCreator;

public class PlayScreen implements Screen{ // we send the game it self to the screen

    // set Screens variables
    private AdventureQuest game;
    private TextureAtlas atlas;

    // playscreen variables
    private OrthographicCamera cam;
    private Viewport gamePort;
    private Hud hud;

    // map variables
    private TmxMapLoader mapLoader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;

    // Box2d variables
    private World world;
    private Box2DDebugRenderer b2dr;
    private B2WorldCreator creator;

    // sprites
    private Player player;

    public PlayScreen(AdventureQuest game){
        atlas = new TextureAtlas("character_poses.pack");
        this.game = game;

        // cam to follow the player through the game stage
        cam = new OrthographicCamera(); 

        // a FitViewport(the window into our game) that mantains the virtual aspect ratio despite screen size
        gamePort = new FitViewport(AdventureQuest.V_WIDTH/AdventureQuest.PPM, AdventureQuest.V_HEIGHT/AdventureQuest.PPM, cam);
        
        // create the game hud with the basic info of the gameplay
        hud = new Hud(game.batch);

        // load the map
        mapLoader = new TmxMapLoader();
        map = mapLoader.load("MapAQ1.tmx");

        // setup the map renderer
        renderer = new OrthogonalTiledMapRenderer(map, 1f/AdventureQuest.PPM);

        // configures the game camera to be centered correctly
        cam.position.set(gamePort.getWorldWidth()/2, gamePort.getWorldHeight()/2, 0);

        // create Box2D world, setting no gravity in X and -10 in y, allow bodies to sleep
        world = new World(new Vector2(0, -10), true);

        // debug lines of Box2D world
        b2dr = new Box2DDebugRenderer();

        creator = new B2WorldCreator(world, map);
        
        // create the player in the world
        player = new Player(world, this);
    }

    public TextureAtlas getAtlas(){
        return atlas;
    }

    @Override
    public void show() {

    }

    public void handleInput(float delta){
        // the control of the player using game buttons and impulse 
        if(Gdx.input.isKeyJustPressed(Input.Keys.UP))
            player.b2body.applyLinearImpulse(new Vector2(0, 4f), player.b2body.getWorldCenter(), true);
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT) && player.b2body.getLinearVelocity().x <= 2)
            player.b2body.applyLinearImpulse(new Vector2(0.1f, 0), player.b2body.getWorldCenter(), true);
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT) && player.b2body.getLinearVelocity().x <= -2)
            player.b2body.applyLinearImpulse(new Vector2(-0.1f, 0), player.b2body.getWorldCenter(), true);
    }

    public void update(float delta){
        handleInput(delta); // handle the user input
        world.step(1/60f, 6, 2); // take 1 step in the physics simulation(60ps) 
        player.update(delta);
        cam.position.x = player.b2body.getPosition().x;
        cam.update(); // update the cam with the right positions after changes
        renderer.setView(cam); // renderer draw only what the camera can see in the world
    }

    @Override
    public void render(float delta) {
        update(delta); // separate our update logic from render (não entendi direito isso)

        Gdx.gl.glClearColor(0, 0, 0, 1); 
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        renderer.render(); // render the game map
        b2dr.render(world, cam.combined); // render the Box2DDebug
        game.batch.setProjectionMatrix(cam.combined);
        game.batch.begin();
        player.draw(game.batch);
        game.batch.end();

        game.batch.setProjectionMatrix(hud.stage.getCamera().combined); // set and makes the batch draw what the hud sees
        hud.stage.draw();                                               
    }

    @Override
    public void resize(int width, int height) {
        gamePort.update(width, height); // update the viewport
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        // dispose all opened resources
        map.dispose();
        renderer.dispose();
        world.dispose();
        b2dr.dispose();
        hud.dispose();
    }
    
}