package com.edujhuan.adventurequest.Sprites;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.edujhuan.adventurequest.AdventureQuest;
import com.edujhuan.adventurequest.Screens.PlayScreen;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Player extends Sprite {
    public World world;
    public Body b2body;
    private TextureRegion playerStand;

    public Player(World world, PlayScreen screen){
        super(screen.getAtlas().findRegion("character_maleAdventurer_run0"));
        this.world=world;
        definePlayer();
        playerStand = new TextureRegion(getTexture(), 0, 0, 16, 16);
        setBounds(0, 0, 16/AdventureQuest.PPM, 16/AdventureQuest.PPM);
        setRegion(playerStand);
    }

    public void update(float delta){
        setPosition(b2body.getPosition().x - getWidth()/2, b2body.getPosition().y - getHeight()/2);
    }

    public void definePlayer(){
        BodyDef bdef = new BodyDef();
        bdef.position.set(32/AdventureQuest.PPM, 32/AdventureQuest.PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);
        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(5/AdventureQuest.PPM);
        fdef.shape = shape;
        b2body.createFixture(fdef);
    }
}
