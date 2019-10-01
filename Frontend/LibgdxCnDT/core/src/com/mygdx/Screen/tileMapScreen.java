package com.mygdx.Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.mygdx.Objects.tempTile;

public class tileMapScreen implements Screen {

    private TiledMap maps;
    private TiledMapTileLayer layer;
    private OrthogonalTiledMapRenderer render;
    private OrthographicCamera camera;
    private tempTile shelfTile;

    @Override
    public void render(float delta){
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        render.setView(camera);
        render.render();

        layer = (TiledMapTileLayer) maps.getLayers().get("Shelf Object layer");

        MapLayer layer = maps.getLayers().get("Shelf Object Layer");
        MapObjects shelf = layer.getObjects();

        shelf.get("Shelf");
        System.out.println(shelf.get("Shelf"));

        shelfTile = new tempTile();
        for (int i = 0; i < shelf.getCount(); i++){
        //    TiledMapTileLayer.Cell cell = layer.getCell(x, y);
        }


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
        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();

        camera = new OrthographicCamera();
        camera.setToOrtho(false,w,h);
        camera.update();

        maps = new TmxMapLoader().load("img/NewStoreMap.tmx");

        render = new OrthogonalTiledMapRenderer(maps);
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

