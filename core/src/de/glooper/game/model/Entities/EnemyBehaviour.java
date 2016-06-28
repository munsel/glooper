package de.glooper.game.model.Entities;

/**
 * Created by munsel on 27.06.16.
 */
public class EnemyBehaviour implements IEntityBehaviour {

    private IEntity entity;

    public EnemyBehaviour(IEntity parent){
        entity = parent;
    }
    @Override
    public void update(float delta) {

    }
}
