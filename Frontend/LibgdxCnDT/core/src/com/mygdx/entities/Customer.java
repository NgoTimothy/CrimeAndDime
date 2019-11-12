package com.mygdx.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;

public class Customer extends Sprite {

    private Vector2 velocity = new Vector2();

    private float speed = 60*2;

    private float testWind = 5;

    private TiledMapTileLayer collisionLayer;

    public Customer(Sprite sprite, TiledMapTileLayer collisionLayer){
        super(sprite);
        this.collisionLayer = collisionLayer;
    }

    @Override
    public void draw(Batch batch){
        update(Gdx.graphics.getDeltaTime());
        super.draw(batch);
    }

    public void update(float delta){

        /*
        velocity.x += testWind * delta;
        // Squeeze Values
        if (velocity.y > speed){
            velocity.y = speed;
        }
        else if(velocity.y < speed){
            velocity.y =- speed;

        }

        // Save Old Position
        float oldX = getX(), oldY = getY(); // tildWidth = collisionLayer.getTileWidth(), tileHeight = collisionLayer.getTileHeight();
        boolean collistionX = false, collisionY = false;

        // Move on X
        setX(getX() + velocity.x * delta);

        if (collistionX){
            setX(oldX);
            velocity.x -= testWind * delta;
            velocity.x = 0;
        }
        if (velocity.x < 0){
            collistionX = isCellBlocked(getX(),getY() + getHeight());

            if (!collistionX) {
                collistionX = isCellBlocked(getX(), getY() + getHeight() / 2);
            }

            if (!collistionX) {
                collistionX = isCellBlocked(getX(), getY());
            }

        }
        if (collistionX){
            setX(oldX);
            velocity.x = 0;
        }
        else if (velocity.x > 0){
            collisionY = isCellBlocked(getX(), getY() + getHeight());

            if(!collisionY) {
                collisionY = isCellBlocked(getX() + getWidth() / 2, getY() + getHeight());
            }
            if (!collisionY){
                collisionY = isCellBlocked(getX() + getWidth(), getY() + getHeight());
            }
        }
        if (collisionY){
            setY(oldY);
            velocity.y = 0;
        }
        */
    }

    private boolean isCellBlocked(float x, float y){
        TiledMapTileLayer.Cell cell = collisionLayer.getCell((int) (x / collisionLayer.getTileWidth()) , (int) (y/collisionLayer.getTileHeight()));


        Boolean testBool = false;

        if (cell.getTile().getProperties().containsKey("BLOCKED")){
            testBool = true;
        }
        if (testBool == true){
            System.out.println(Boolean.toString(testBool));
        }

        return cell != null && cell.getTile() != null || cell.getTile().getProperties().containsKey("BLOCKED");
    }

    public void customerWants(){

    }

    public Vector2 getVelocity(){
        return velocity;
    }

    public void setVelocity(Vector2 velocity){
        this.velocity = velocity;
    }

    public float getSpeed(){
        return speed;
    }

    public void setSpeed(Float speed){
        this.speed = speed;
    }

    public TiledMapTileLayer getCollisionLayer() {
        return collisionLayer;
    }
    public void setCollisionLayer(TiledMapTileLayer collisionLayer){
        this.collisionLayer = collisionLayer;
    }

}
