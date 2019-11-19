package com.mygdx.TileMapGame;

import com.badlogic.gdx.Game;
import com.mygdx.Screen.*;
import com.mygdx.cndt.CrimeAndDime;

public class TileMapGame extends Game {

    @Override
    public void create (){
<<<<<<< HEAD
        setScreen(new tileMapScreen(new CrimeAndDime()));
=======
        setScreen(new Splash());
>>>>>>> SpriteMovement-Hung
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
