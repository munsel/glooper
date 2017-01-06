package de.glooper.game.Screens.GameScreen.HelperClasses;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.PolylineMapObject;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.Array;
import de.glooper.game.model.Entities.Entity;
import de.glooper.game.model.Entities.FoodBehaviour;
import de.glooper.game.model.Entities.GoombaBehaviour;
import de.glooper.game.model.Entities.IEntity;
import de.glooper.game.model.Heros.Hero;
import de.glooper.game.model.Physics.Box2DCategoryBits;

import javax.naming.NameNotFoundException;

/**
 * Created by munsel on 18.08.16.
 */
public class TileObjectUtil {
    private static final String TAG = TileObjectUtil.class.getSimpleName();

    private final static float SEAWEED_WIDTH=.3f;
    private final static float SEAWEED_HEIGHT=.6f;

    private final static float GOOMBA_WIDTH=1.28f;
    private final static float GOOMBA_HEIGHT=1f;

    private final static float EEL_WIDTH=.8f;
    private final static float EEL_HEIGHT=.2f;

    public static final float PPM = 128;
    public static void parseTiledObjectLayer(World world,Array<IEntity> entities,
                                             MapObjects objects,
                                             Hero hero){
        for (MapObject object : objects){
            Shape shape;
            if (object instanceof PolylineMapObject){
                try{
                    if (object.getName().equals("seaweed")){
                        Gdx.app.log(TAG,"Got me some seaweed yo!!");
                        IEntity entity = new Entity(entities,world, AssetHandler.instance.seaweed,
                                "seaweed",
                                ((PolylineMapObject) object).getPolyline().getX()/PPM,
                                ((PolylineMapObject) object).getPolyline().getY()/PPM,
                                SEAWEED_WIDTH, SEAWEED_HEIGHT,
                                Box2DCategoryBits.FOOD, BodyDef.BodyType.StaticBody);
                        entity.setBehaviour(new FoodBehaviour(entity));
                        entities.add(entity);
                        continue;
                    }
                    if (object.getName().equals("goomba")){
                        Gdx.app.log(TAG,"Got me some goomba yo!!");
                        TextureRegion[] spritesheet = Entity.getFramesFromSpritesheet(20,4,5,
                                128,100,AssetHandler.instance.goomba);
                        IEntity entity = new Entity(entities,world, spritesheet,
                                "goomba",
                                ((PolylineMapObject) object).getPolyline().getX()/PPM,
                                ((PolylineMapObject) object).getPolyline().getY()/PPM,
                                GOOMBA_WIDTH, GOOMBA_HEIGHT,
                                Box2DCategoryBits.ENEMY, BodyDef.BodyType.DynamicBody);
                        entity.setBehaviour(new GoombaBehaviour(hero,entity));
                        entities.add(entity);
                        continue;
                    }
                }catch (NullPointerException e){
                    Gdx.app.error(TAG, "name not found or smthn!");
                }
                shape = createPolyLine((PolylineMapObject) object);
            }else{
                continue;
            }
            Body body;
            BodyDef bodyDef = new BodyDef();
            bodyDef.type = BodyDef.BodyType.StaticBody;

            body = world.createBody(bodyDef);
            FixtureDef fixtureDef = new FixtureDef();
            fixtureDef.shape = shape;
            fixtureDef.restitution =0;
            fixtureDef.friction = 0;
            fixtureDef.filter.categoryBits = Box2DCategoryBits.ROCK;
            body.createFixture(fixtureDef);
            shape.dispose();
        }
    }

    public static Shape createPolyLine(PolylineMapObject object){
        float[] vertices = object.getPolyline().getTransformedVertices();
        Vector2[] worldVertices = new Vector2[vertices.length/2];
        for (int i = 0; i<worldVertices.length; i++){
            worldVertices[i] = new Vector2(vertices[i*2]/PPM,
                    vertices[i*2+1]/PPM);
        }
        ChainShape shape = new ChainShape();
        shape.createChain(worldVertices);
        return shape;
    }

}
