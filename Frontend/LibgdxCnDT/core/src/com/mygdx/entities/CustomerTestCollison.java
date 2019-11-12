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

    private static final int MOVEMENT = 100;
 //   private Texture customerTexture;
    private Vector2 position;
    private Vector2 velocity = new Vector2();
    private Rectangle bounds;

    private ArrayList<Wall> walls;

    public CustomerTestCollison(int x, int y, Sprite sprite){
        super(sprite);
        position = new Vector2(x,y);
        bounds = new Rectangle(x,y,sprite.getWidth(),sprite.getWidth());
    }

    public void update(float dt){
        position.x += MOVEMENT * dt;

        velocity.scl(dt);
       // position.add(MOVEMENT * Gdx.graphics.getDeltaTime(), velocity.y,0);
        bounds.setPosition(position.x,position.y);
    }

    @Override
    public void draw(Batch batch){
        update(Gdx.graphics.getDeltaTime());
        super.draw(batch);
    }

    public Rectangle getBounds(){
        return bounds;
    }

    public void setWallArrayList(ArrayList<Wall> walls){
        this.walls = walls;
    }
}
