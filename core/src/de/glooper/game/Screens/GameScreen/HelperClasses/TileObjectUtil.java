package de.glooper.game.Screens.GameScreen.HelperClasses;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.PolylineMapObject;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import de.glooper.game.model.Physics.Box2DCategoryBits;

/**
 * Created by munsel on 18.08.16.
 */
public class TileObjectUtil {

    public static final float PPM = 128;
    public static void parseTiledObjectLayer(World world, MapObjects objects){
        for (MapObject object : objects){
            Shape shape;
            if (object instanceof PolylineMapObject){
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
            fixtureDef.filter.categoryBits = Box2DCategoryBits.ROCK;
            body.createFixture(fixtureDef);
            shape.dispose();
        }
    }

    public static Shape createPolyLine(PolylineMapObject object){
        float[] vertices = object.getPolyline().getTransformedVertices();
        Vector2[] worldvertices = new Vector2[vertices.length/2];
        for (int i = 0; i<worldvertices.length; i++){
            worldvertices[i] = new Vector2(vertices[i*2]/PPM,
                    vertices[i*2+1]/PPM);
        }
        ChainShape shape = new ChainShape();
        shape.createChain(worldvertices);
        return shape;
    }

}
