package com.mygdx.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import java.util.ArrayList;

public class CustomerSprite extends Sprite{

    private static final int MOVEMENT = 70;
 //   private Texture customerTexture;
    private Vector2 position;
    private float velocity;
    private Vector2 targetPosition;
    private Rectangle bounds;

    public String curDirection;

    private boolean hasCollided = false;
    private ArrayList<Wall> walls;
    private Vector2 endVector = new Vector2(870, 620);

    public CustomerSprite(int x, int y, Sprite sprite, Vector2 targetPosition){
        super(new Sprite(new Texture("img/customers/customer1-right.png")));
        position = new Vector2(x,y);
       // velocity = new Vector2(0,0);
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
        velocity = 0;
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
        velocity = MOVEMENT * dt;
            switch (isValidMovement()){
                case 1:
                    position.set(position.x, position.y - 1);
                    hasReachedTargetPosition();
                    hasReachedEnd();
                    curDirection = "Down";
                    setTexture(new Texture("img/customers/customer1-down.png"));
                    break;
                case 2:
                    position.set(position.x - 1, position.y);
                    hasReachedTargetPosition();
                    hasReachedEnd();
                    curDirection = "Left";
                    setTexture(new Texture("img/customers/customer1-left.png"));
                    break;
                case 3:
                    position.set(position.x + 1, position.y);
                    hasReachedTargetPosition();
                    hasReachedEnd();
                    curDirection = "Right";
                    setTexture(new Texture("img/customers/customer1-right.png"));
                    break;
                case 4:
                    position.set(position.x, position.y + 1);
                    hasReachedTargetPosition();
                    hasReachedEnd();
                    curDirection = "Up";
                    setTexture(new Texture("img/customers/customer1-up.png"));
                    break;
                default:
                    break;
            }
    }



    public int isValidMovement(){
        Vector2 futureMovement = new Vector2();
        Rectangle futureMovementBound;
        if (position.y != (int) targetPosition.y)  // Could make this a boolean that checks the "checkpoint" for the targetGoal.
        {
            if (position.y > (int) targetPosition.y) {
//                futureMovement.x = targetPosition.x;
//                futureMovement.y = targetPosition.y + (MOVEMENT * Gdx.graphics.getDeltaTime());
//                futureMovementBound = new Rectangle(futureMovement.x, futureMovement.y, getWidth(), getHeight()); // Not 100% sure about this.
//                if (!isWallCollistion(futureMovementBound)) {
//                    return 1;
//                }
                return 1;
                // Should never be true becasue if it is, position.y <= targetPosition.y
            }
            if (position.y < (int) targetPosition.y) {
//                futureMovement.x = targetPosition.x;
//                futureMovement.y = targetPosition.y + (MOVEMENT * Gdx.graphics.getDeltaTime());
//                futureMovementBound = new Rectangle(futureMovement.x, futureMovement.y, getWidth(), getHeight());
//                if (!isWallCollistion(futureMovementBound)) {
//                    return 4;
//                }
                return 4;
            }
        }
        else if (position.x != (int) targetPosition.x)
        {
            // I know the problem. Its because of how if statemenets work going down a chain that it is not checing all the four directions.
            if (position.x > (int) targetPosition.x){
//                futureMovement.x = targetPosition.x - (MOVEMENT * Gdx.graphics.getDeltaTime());
//                futureMovement.y = targetPosition.y;
//                futureMovementBound = new Rectangle(futureMovement.x,futureMovement.y,getWidth(),getHeight());
//                if (!isWallCollistion(futureMovementBound))
//                {
//                    return 2;
//                }
                return 2;
            }
            if (position.x < (int) targetPosition.x){
//                futureMovement.x = targetPosition.x + (MOVEMENT * Gdx.graphics.getDeltaTime());
//                futureMovement.y = targetPosition.y;
//                futureMovementBound = new Rectangle(futureMovement.x,futureMovement.y,getWidth(),getHeight());
//                if (!isWallCollistion(futureMovementBound))
//                {
//                    return 3;
//                }
                return 3;
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

    public void hasReachedTargetPosition(){
        if ((int) (targetPosition.x - position.x) == 0)
        {
            if ((int) (targetPosition.y - position.y) == 0)
            {
                Vector2 endVector = new Vector2(870, 620);
                targetPosition = endVector;
            }
        }
    }

    public boolean hasReachedEnd(){
        if ((int) (endVector.x - position.x) == 0)
        {
            if ((int) (endVector.y - position.y) == 0)
            {
               return true;
            }
        }
        return false;
    }
}
