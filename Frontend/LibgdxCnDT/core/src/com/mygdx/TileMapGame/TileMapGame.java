package com.mygdx.TileMapGame;

import com.badlogic.gdx.Game;
import com.mygdx.Screen.*;

public class TileMapGame extends Game {

    @Override
    public void create (){
        setScreen(new tileMapScreen());
    }

    @Override
    public void render () {
        super.render();
    }

    @Override
    public void dispose () {
        super.dispose();
    }

    @Override
    public void resize(int width, int height){
       super.resize(width,height);
    }

    @Override
    public void pause(){
        super.pause();
    }

    @Override
    public void resume(){
        super.resume();
    }
}
