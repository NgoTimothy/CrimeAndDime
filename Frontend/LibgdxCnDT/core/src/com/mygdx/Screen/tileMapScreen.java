package com.mygdx.Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

public class tileMapScreen implements Screen {

    private TiledMap maps;
    private TiledMapTileLayer layer;
    private OrthogonalTiledMapRenderer render;
    private OrthographicCamera camera;


    @Override
    public void render(float delta){
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        render.setView(camera);
        render.render();

       // camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0);
       // camera.update();
    }


    @Override
    public void resize(int width, int height){
        camera.viewportWidth = width;
        camera.viewportHeight = height;

       camera.update();

    }

    @Override
    public void show ()
    {
        maps = new TmxMapLoader().load("img/CnD_Temp.tmx");

        render = new OrthogonalTiledMapRenderer(maps);

        camera = new OrthographicCamera();

    }


    @Override
    public void pause(){

    }
    @Override
    public void resume(){

    }
    @Override
    public void hide(){
        maps.dispose();
    }

    @Override
    public void dispose () {
        maps.dispose();
        render.dispose();
    }
}

