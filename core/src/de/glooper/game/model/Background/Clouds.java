package de.glooper.game.model.Background;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import de.glooper.game.Screens.GameScreen.HelperClasses.AssetHandler;

/**
 * Created by munsel on 16.06.16.
 */
public class Clouds implements IBackground {
    private final String TAG = Clouds.class.getSimpleName();

    private final float VELOCITY_1 = .2f;
    private final float VELOCITY_2 = .4f;
    private final float VELOCITY_3 = .8f;

    private TextureRegion[] cloudsRegions;
    private OrthographicCamera camera;

    private Vector3 movingDirection;
    private Vector3 oldCameraPosition;

    private Array<Sprite> cloudsV1;
    private Array<Sprite> cloudsV2;
    private Array<Sprite> cloudsV3;

    public Clouds(OrthographicCamera camera){
        cloudsRegions = AssetHandler.instance.clouds;
        this.camera = camera;
        movingDirection = new Vector3(camera.position);
        oldCameraPosition = new Vector3();
        cloudsV1 = new Array<Sprite>();
        cloudsV2 = new Array<Sprite>();
        cloudsV3 = new Array<Sprite>();
        createClouds(cloudsV1, 10);
        createClouds(cloudsV2,10);
        createClouds(cloudsV3, 10);
    }
    public void init(){
        initializeCloudDistribution(cloudsV1);
        initializeCloudDistribution(cloudsV2);
        initializeCloudDistribution(cloudsV3);
    }

    private void createClouds(Array<Sprite> clouds, int nClouds) {
        for (int nCloud = 0; nCloud < nClouds; nCloud++) {
            Sprite sprite = new Sprite(cloudsRegions[MathUtils.random(5)]);
            float scaleFactor = MathUtils.random(4);
            sprite.setSize(scaleFactor*1, scaleFactor*1.5f);
            float red = MathUtils.random()*.1f;
            float green = red + MathUtils.random()*.01f;
            float blue = red + MathUtils.random()*.01f;
            Color cloudTint = new Color(red,green,blue, 1);
            sprite.setColor(cloudTint);
            //sprite.setColor(0x223344FF);
            clouds.add(sprite);
        }
    }

    private void initializeCloudDistribution(Array<Sprite> clouds){
        for (Sprite sprite : clouds){
            float randPosX = camera.position.x-camera.viewportWidth/2+ MathUtils.random(camera.viewportWidth);
            float randPosY = camera.position.y-camera.viewportHeight/2+ MathUtils.random(camera.viewportWidth);
            sprite.setPosition(randPosX, randPosY);
        }
    }


    private void updateCloud(Sprite cloud, float velocity, float delta){
        if (cloud.getX()+cloud.getWidth()< camera.position.x-camera.viewportWidth/2){
            cloud.setPosition(camera.position.x+camera.viewportWidth/2,
                    camera.position.y -camera.viewportHeight/2+MathUtils.random(camera.viewportHeight-cloud.getHeight()/2));
        }
        if (cloud.getX()> camera.position.x+camera.viewportWidth/2){
            cloud.setPosition(camera.position.x-camera.viewportWidth/2-cloud.getWidth(),
                    camera.position.y -camera.viewportHeight/2+MathUtils.random(camera.viewportHeight-cloud.getHeight()/2));
        }
        if (cloud.getY()+cloud.getHeight()< camera.position.y-camera.viewportHeight/2){
            cloud.setPosition(
                    camera.position.x -camera.viewportWidth/2 +MathUtils.random(camera.viewportWidth-cloud.getWidth()/2),
                    camera.position.y+camera.viewportHeight/2);
        }
        if (cloud.getY()> camera.position.y+camera.viewportHeight/2){
            cloud.setPosition(
                    camera.position.x -camera.viewportWidth/2 +MathUtils.random(camera.viewportWidth-cloud.getWidth()/2),
                    camera.position.y-camera.viewportHeight/2-cloud.getHeight());
        }
        movingDirection.nor();
        cloud.setPosition(
                cloud.getX() +movingDirection.x*velocity*delta,
                cloud.getY() +movingDirection.y*velocity*delta
        );


    }


    @Override
    public void draw(SpriteBatch batch) {
        for (Sprite cloud : cloudsV3){
            cloud.draw(batch);
        }
        for (Sprite cloud : cloudsV2){
            cloud.draw(batch);
        }
        for (Sprite cloud : cloudsV1){
            cloud.draw(batch);
        }
    }

    @Override
    public void update(float delta) {
        movingDirection.set(camera.position);
        movingDirection.sub(oldCameraPosition);
        for (Sprite cloud : cloudsV1){
            updateCloud(cloud, VELOCITY_1, delta);
        }
        for (Sprite cloud : cloudsV2){
            updateCloud(cloud, VELOCITY_2, delta);
        }
        for (Sprite cloud : cloudsV3){
            updateCloud(cloud, VELOCITY_3, delta);
        }
        oldCameraPosition.set(camera.position);
    }
}
