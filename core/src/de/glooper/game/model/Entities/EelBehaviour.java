package de.glooper.game.model.Entities;

/**
 * Created by munsel on 27.06.16.
 */
public class EelBehaviour implements IEntityBehaviour {

    private IEntity entity;
    private float stateTime;
    private float velocity;
    private boolean isSwinmmingFromRigthToLeft;

    public EelBehaviour(IEntity parent){
        entity = parent;
        stateTime = 0;
        velocity = 0.6f;
        isSwinmmingFromRigthToLeft = true;
    }


    @Override
    public void update(float delta) {
        stateTime+=delta;
        if (stateTime > 2){
            stateTime = 0;
            if (entity.getXFlip())
                entity.setXFlip(false);
            else
                entity.setXFlip(true);
        }
        float x = entity.getX();
        if(entity.getXFlip())
            x += delta*velocity;
        else
            x -= delta*velocity;
        entity.setX(x);
    }
}
