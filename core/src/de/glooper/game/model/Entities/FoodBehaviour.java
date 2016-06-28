package de.glooper.game.model.Entities;

/**
 * Created by munsel on 27.06.16.
 */
public class FoodBehaviour implements IEntityBehaviour {

    private IEntity entity;

    public FoodBehaviour( IEntity parent){
        entity = parent;
    }

    @Override
    public void update(float delta) {

    }
}
