package com.edujhuan.adventurequest.Sprites;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.edujhuan.adventurequest.AdventureQuest;
import com.edujhuan.adventurequest.Screens.PlayScreen;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.particles.influencers.RegionInfluencer.Animated;

public class Player extends Sprite {
	public enum State {WALKING, ATTACKING, STANDING};
	public State currentState;
	public State previousState;
    public World world;
    public Body b2body;
    private TextureRegion playerStand;
	private Animation playerWalk;
	private Animation playerAttack;
	private float stateTimer;
	private boolean walkingRight;

    public Player(World world, PlayScreen screen){
        super(screen.getAtlas().findRegion("walk"));
        this.world=world;
		currentState = State.STANDING;
		previousState = State.STANDING;
		stateTimer = 0;
		walkingRight = true;

		Array<TextureRegion> frames = new Array<TextureRegion>();

		for(int i = 2; i < 4; i++)
			frames.add(new TextureRegion(getTexture(), i * 65, 0, 58, 58));
		playerWalk = new Animation(0.5f, frames);
		frames.clear();

		for(int i = 1; i < 16; i++)
			frames.add(new TextureRegion(getTexture(), i * 65, 63, 58, 58));
		playerAttack = new Animation(0.3f, frames);
		frames.clear();

		playerStand = new TextureRegion(getTexture(), 1163, 67, 58, 58);

		definePlayer();
        setBounds(0, 0, 25/AdventureQuest.PPM, 25/AdventureQuest.PPM);
        setRegion(playerStand);
    }

    public void update(float delta){
        setPosition(b2body.getPosition().x - getWidth()/2, b2body.getPosition().y - getHeight()/2);
		setRegion(getFrame(delta));
    }

	public TextureRegion getFrame(float delta){
		currentState = getState();
		TextureRegion region;

		switch(currentState){
			case WALKING:
				region = (TextureRegion) playerWalk.getKeyFrame(stateTimer, true);
				break;
			case ATTACKING:
				region = (TextureRegion) playerAttack.getKeyFrame(stateTimer, true);
				break;
			case STANDING:
			default:
				region = playerStand;
				break;
		}
		if((b2body.getLinearVelocity().x < 0 || !walkingRight) && !region.isFlipX()){
			region.flip(true, false);
			walkingRight = false;
		}
		else if((b2body.getLinearVelocity().x > 0 || walkingRight) && region.isFlipX()){
			region.flip(true, false);
			walkingRight = true;
		}
		stateTimer = currentState == previousState ? stateTimer + delta : 0;
		previousState = currentState;
		return region;
	}

	public State getState(){
		if(b2body.getLinearVelocity().x != 0)
			return State.WALKING;
		else if(b2body.isBullet()){
			b2body.setBullet(false);
			return State.ATTACKING;
		}	
		else
			return State.STANDING;

	}

    public void definePlayer(){
        BodyDef bdef = new BodyDef();
        bdef.position.set(32/AdventureQuest.PPM, 32/AdventureQuest.PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);
        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(8/AdventureQuest.PPM);
        fdef.shape = shape;
        b2body.createFixture(fdef);
    }
}
