package com.mygdx.Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.BaseDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.cndt.CrimeAndDime;

public class Login implements Screen {
    private BitmapFont white, black;
    private SpriteBatch batch;
    private Stage stage;
    private TextButton exitButton;
    private TextureAtlas atlas;
    private Skin skin;
    private CrimeAndDime game;
    private String usr;
    private TextField usernameTextField, passwordTextField, regUsrTextField, regPswTextField, regEmailTextField, regPhoneTextField;

    public Login(CrimeAndDime game) {
        white = new BitmapFont(Gdx.files.internal("font/WhiteFNT.fnt"), false);
        black = new BitmapFont(Gdx.files.internal("font/BlackFNT.fnt"),false);
        batch = new SpriteBatch();
        stage = new Stage();
        this.game = game;
    }
    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        exitButton = new TextButton("X", textButtonStyle());
        exitButton.setPosition(1150, 650);
        exitButton.setWidth(50);
        exitButton.setHeight(50);
        exitButton.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new Splash(game));
            }
        });
        stage.addActor(exitButton);
        usernameTextField = new TextField("", new TextField.TextFieldStyle(white, Color.WHITE, new BaseDrawable(), new BaseDrawable(), new BaseDrawable()));
        usernameTextField.setWidth(150);
        usernameTextField.setPosition(350, 500);
        stage.addActor(usernameTextField);
        final TextButton username = new TextButton("Username", textButtonStyle());
        username.setPosition(200, 500);
        stage.addActor(username);

        passwordTextField = new TextField("", new TextField.TextFieldStyle(white, Color.WHITE, new BaseDrawable(), new BaseDrawable(), new BaseDrawable()));
        passwordTextField.setPasswordMode(true);
        passwordTextField.setPasswordCharacter('*');
        passwordTextField.setWidth(150);
        passwordTextField.setPosition(350, 450);
        stage.addActor(passwordTextField);
        final TextButton password = new TextButton("Password", textButtonStyle());
        password.setPosition(200, 450);
        stage.addActor(password);

        final TextButton login = new TextButton("Login", textButtonStyle());
        login.setPosition(250, 400);
        login.setWidth(200);
        login.addListener(new ClickListener() {
           @Override
           public void clicked(InputEvent input, float x, float y) {
               verifyLogin(usernameTextField.getText(), passwordTextField.getText());
           }
        });
        stage.addActor(login);

        final TextButton regUsername = new TextButton("Username", textButtonStyle());
        regUsername.setPosition(750,500);
        stage.addActor(regUsername);

        regUsrTextField = new TextField("", new TextField.TextFieldStyle(white, Color.WHITE, new BaseDrawable(), new BaseDrawable(), new BaseDrawable()));
        regUsrTextField.setWidth(150);
        regUsrTextField.setPosition(900, 500);
        stage.addActor(regUsrTextField);

        final TextButton regPassword = new TextButton("Password", textButtonStyle());
        regPassword.setPosition(750, 450);
        stage.addActor(regPassword);

        regPswTextField = new TextField("", new TextField.TextFieldStyle(white, Color.WHITE, new BaseDrawable(), new BaseDrawable(), new BaseDrawable()));
        regPswTextField.setPasswordMode(true);
        regPswTextField.setPasswordCharacter('*');
        regPswTextField.setWidth(150);
        regPswTextField.setPosition(900, 450);
        stage.addActor(regPswTextField);

        final TextButton regEmail = new TextButton("Email", textButtonStyle());
        regEmail.setPosition(750, 400);
        regEmail.setWidth(140);
        stage.addActor(regEmail);

        regEmailTextField = new TextField("", new TextField.TextFieldStyle(white, Color.WHITE, new BaseDrawable(), new BaseDrawable(), new BaseDrawable()));
        regEmailTextField.setWidth(150);
        regEmailTextField.setPosition(900, 400);
        stage.addActor(regEmailTextField);

        final TextButton regPhone = new TextButton("Phone", textButtonStyle());
        regPhone.setPosition(750, 350);
        regPhone.setWidth(140);
        stage.addActor(regPhone);

        regPhoneTextField = new TextField("", new TextField.TextFieldStyle(white, Color.WHITE, new BaseDrawable(), new BaseDrawable(), new BaseDrawable()));
        regPhoneTextField.setWidth(150);
        regPhoneTextField.setPosition(900, 350);
        stage.addActor(regPhoneTextField);

        final TextButton register = new TextButton("Register", textButtonStyle());
        register.setPosition(800, 300);
        register.setWidth(200);
        register.addListener(new ClickListener() {
           @Override
           public void clicked(InputEvent event, float x, float y) {
                registerAccount(regUsrTextField.getText(), regPswTextField.getText(), regEmailTextField.getText(), regPhoneTextField.getText());
           }
        });
        stage.addActor(register);
    }

    /**
     * Method will verify login information exists in the database
     * If it is verified it will login
     * Else it will give an error message
     * @param usr
     * @param psw
     */
    private void verifyLogin(String usr, String psw) {
        //TODO
    }

    /**
     * Method will verify if the account can be registered
     * If it can it will add the account to the database and automatically login
     * Else it will send an error message
     * @param usr
     * @param psw
     * @param email
     * @param phoneNumber
     */
    private void registerAccount(String usr, String psw, String email, String phoneNumber) {
        //TODO
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.setDebugAll(true);
        stage.draw();
        batch.begin();

        batch.end();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }

    private TextButton.TextButtonStyle textButtonStyle()
    {
        atlas = new TextureAtlas("ui/button.pack");
        skin = new Skin(atlas);

        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.up = skin.getDrawable("button.up.9");
        textButtonStyle.down = skin.getDrawable("button.down");
        textButtonStyle.pressedOffsetX = 1;
        textButtonStyle.pressedOffsetY = -1;
        textButtonStyle.font = black;
        return textButtonStyle;
    }
}
