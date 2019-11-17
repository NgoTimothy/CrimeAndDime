package com.mygdx.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.Screen.tileMapScreen;

import java.util.ArrayList;

public class CustomerTestCollison extends Sprite{

    private static final int MOVEMENT = 2;
 //   private Texture customerTexture;
    private Vector2 position;
    private Vector2 velocity;
    private Rectangle bounds;
    private Sprite sprite;

    private boolean hasCollided = false;
    private ArrayList<Wall> walls;

    public CustomerTestCollison(int x, int y, Sprite sprite){
        super(sprite);
        this.sprite = sprite;
        position = new Vector2(x,y);
        velocity = new Vector2(0,0);
        bounds = new Rectangle(x,y,sprite.getWidth(),sprite.getWidth());
    }

    public void update(float dt){
        if (!hasCollided){
            velocity.x += MOVEMENT * dt;
        }
        position.set(position.x + velocity.x , position.y + velocity.y);
        bounds.setPosition(position.x,position.y);
    }

    @Override
    public void draw(Batch batch){
        update(Gdx.graphics.getDeltaTime());
        super.draw(batch);
    }

    public void stop(){
        velocity.set(0,0);
        hasCollided = true;
    }
    public Rectangle getBounds(){
        return bounds;
    }

    public Vector2 getPosition()
    {
        return position;
    }

    public void setPosition(){

    }
    public void setWallArrayList(ArrayList<Wall> walls){
        this.walls = walls;
    }
}
