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

public class Customer extends Sprite{

    private static final int MOVEMENT = 50;
 //   private Texture customerTexture;
    private Vector2 position;
    private Vector2 velocity;
    private Vector2 targetPosition;
    private Rectangle bounds;
    private Sprite sprite;

    private boolean hasCollided = false;
    private ArrayList<Wall> walls;

    public Customer(int x, int y, Sprite sprite, Vector2 targetPosition){
        super(sprite);
        this.sprite = sprite;
        position = new Vector2(x,y);
        velocity = new Vector2(0,0);
        this.targetPosition = targetPosition;
        bounds = new Rectangle(x,y,sprite.getWidth(),sprite.getWidth());
    }

    public void update(float dt){
        updateMovement(dt);
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

    public void updateMovement(float dt){
        switch (isValidMovement()){
            case 1:
                velocity.y = MOVEMENT * dt;
                position.set(position.x, position.y - velocity.y);
                break;
            case 2:
                velocity.x = MOVEMENT * dt;
                position.set(position.x - velocity.x, position.y);
                break;
            case 3:
                velocity.x = MOVEMENT * dt;
                position.set(position.x + velocity.x, position.y);
                break;
            default:
                System.out.println(isValidMovement());
                System.out.println("It should never get to this????");
                break;
        }
    }

    public int isValidMovement(){
        Vector2 futureMovement = new Vector2();
        Rectangle futureMovementBound;
        if (position.x != targetPosition.x && position.y != targetPosition.y)  // Could make this a boolean that checks the "checkpoint" for the targetGoal.
        {
            if (position.y > targetPosition.y){
                futureMovement.x = targetPosition.x;
                futureMovement.y = targetPosition.y + (MOVEMENT * Gdx.graphics.getDeltaTime());
                futureMovementBound = new Rectangle(futureMovement.x,futureMovement.y,sprite.getWidth(),sprite.getHeight()); // Not 100% sure about this.
                if (!isWallCollistion(futureMovementBound))
                {
                    return 1;
                }
                // Should never be true becasue if it is, position.y <= targetPosition.y
            }
            if (position.x > targetPosition.x){
                futureMovement.x = targetPosition.x - (MOVEMENT * Gdx.graphics.getDeltaTime());
                futureMovement.y = targetPosition.y;
                futureMovementBound = new Rectangle(futureMovement.x,futureMovement.y,sprite.getWidth(),sprite.getHeight());
                if (!isWallCollistion(futureMovementBound))
                {
                    return 2;
                }
            }
            if (position.x < targetPosition.x){
                futureMovement.x = targetPosition.x + (MOVEMENT * Gdx.graphics.getDeltaTime());
                futureMovement.y = targetPosition.y;
                futureMovementBound = new Rectangle(futureMovement.x,futureMovement.y,sprite.getWidth(),sprite.getHeight());
                if (!isWallCollistion(futureMovementBound))
                {
                    return 3;
                }
            }
        }
        else {
            return 0;
        }
        return 0;
    }

    public boolean isWallCollistion(Rectangle input){
        for (int i = 0; i < walls.size(); i++){
            Wall tempWall = walls.get(i);
            if (tempWall.collides(input)){
                return true;
            }
        }
        return false;
    }
}
