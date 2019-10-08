package com.mygdx.Screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.Objects.tempTile;

public class tileMapScreen implements Screen {

    private TiledMap maps;
    private TiledMapTileLayer layer;
    private OrthogonalTiledMapRenderer render;
    private OrthographicCamera camera;
    private tempTile shelfTile;
    private TextButton shelfButton;
    private TextButton.TextButtonStyle shelfButtonStyle;
    private Skin skin;
    private BitmapFont font;
    private Stage stage;
    private TextureAtlas shelfButtonAtlas;


    @Override
    public void render(float delta){
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl.glClear(GL20.GL_ALPHA_BITS);
        camera.update();
        render.setView(camera);
        render.render();

        stage.setDebugAll(true);
        stage.draw();
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
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        skin = new Skin();
        font = new BitmapFont();
        shelfButtonAtlas = new TextureAtlas(Gdx.files.internal("ui/button.pack"));
        skin.addRegions(shelfButtonAtlas);
        shelfButtonStyle = new TextButton.TextButtonStyle();
        shelfButtonStyle.font = font;
        shelfButtonStyle.up = skin.getDrawable("button.up.9");
        shelfButtonStyle.down = skin.getDrawable("button.down");

        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();

        camera = new OrthographicCamera();
        camera.setToOrtho(false,w,h);
        camera.update();

        maps = new TmxMapLoader().load("img/NewStoreMap.tmx");

        render = new OrthogonalTiledMapRenderer(maps);

        MapObjects shelfMapObject = maps.getLayers().get("Shelf Object Layer").getObjects();
        int stringNumber = 1;
        int y = 0;
        for (MapObject shelfObjects : shelfMapObject )
        {
            if (shelfObjects instanceof RectangleMapObject){
                tempTile shelfTile = new tempTile();
                System.out.println("Found Shelf " + Integer.toString(stringNumber));
                stringNumber++;
              //  shelfButton = new TextButton("Temp", shelfButtonStyle);
               // shelfButton.moveBy(y,y);
              //  y += 10;
                Texture texture = new Texture(Gdx.files.internal("img/transparentPicture.png"));
                Image shelfImage = new Image(texture);
                shelfImage.setPosition(((RectangleMapObject) shelfObjects).getRectangle().getX(),((RectangleMapObject) shelfObjects).getRectangle().getY());
                shelfImage.setSize(((RectangleMapObject) shelfObjects).getRectangle().getWidth(),((RectangleMapObject) shelfObjects).getRectangle().getHeight());
                shelfImage.setColor(0,0,0,0);

                System.out.println("This is shelfObjects's Height" + ((RectangleMapObject) shelfObjects).getRectangle().getHeight());
                System.out.println("This is shelfObjects's Width" + ((RectangleMapObject) shelfObjects).getRectangle().getWidth());
                System.out.println("This is shelfObjects's X Value" + ((RectangleMapObject) shelfObjects).getRectangle().getX());
                System.out.println("This is shelfObjects's Y Value"+ ((RectangleMapObject) shelfObjects).getRectangle().getY());

                stage.addActor(shelfImage);
                shelfImage.addListener(new ClickListener()
                {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        ((Game) Gdx.app.getApplicationListener()).setScreen(new Splash());
                    }
                });

            }
        }

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

