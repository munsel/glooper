package de.glooper.game.model.Entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import de.glooper.game.model.Heros.Hero;

/**
 * Created by munsel on 15.12.16.
 */
public class GoombaBehaviour implements IEntityBehaviour{

    private final float VELOCITY = 1.2f;

    private Hero hero;
    private IEntity parent;

    private Vector2 updateVelocity;


    public GoombaBehaviour(Hero hero, IEntity entity){
        this.hero = hero;
        this.parent = entity;
        updateVelocity = new Vector2();
        entity.getBody().getFixtureList().get(0).setSensor(false);
        entity.getBody().setType(BodyDef.BodyType.DynamicBody);
    }


    @Override
    public void update(float delta) {
        Body body =parent.getBody();
        updateVelocity.set((hero.getPosition().x-body.getPosition().x),
                (hero.getPosition().y-body.getPosition().y)).nor();
        body.setLinearVelocity(updateVelocity.scl(VELOCITY));
        float angle = body.getAngle();
        float error = updateVelocity.angleRad()-angle;
        body.setAngularVelocity(2*error);
        Sprite sprite =parent.getSprite();
        sprite.setPosition(body.getPosition().x-sprite.getWidth()/2
                , body.getPosition().y-sprite.getHeight()/2);
        sprite.setRotation(angle*MathUtils.radiansToDegrees+180);

    }
}
