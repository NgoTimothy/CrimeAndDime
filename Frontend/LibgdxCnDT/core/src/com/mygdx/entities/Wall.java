package com.mygdx.entities;

import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Rectangle;

public class Wall  {

    private Rectangle bounds;

    public Wall(RectangleMapObject object1){
        bounds = new Rectangle((int) object1.getRectangle().getX(), (int) object1.getRectangle().getY(),(int) object1.getRectangle().getWidth(),(int) object1.getRectangle().getHeight());
    }

    public boolean collides(Rectangle player){
        return player.overlaps(bounds);

    }
}
