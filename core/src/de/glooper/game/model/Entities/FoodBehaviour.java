package de.glooper.game.model.Entities;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;

/**
 * Created by munsel on 27.06.16.
 */
public class FoodBehaviour implements IEntityBehaviour {

    private IEntity entity;

    public FoodBehaviour( IEntity parent){
        entity = parent;
        Body body =parent.getBody();
        body.setType(BodyDef.BodyType.StaticBody);
        body.getFixtureList().get(0).setSensor(true);
    }

    @Override
    public void update(float delta) {

    }
}
