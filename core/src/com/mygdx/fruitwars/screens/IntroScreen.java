package com.mygdx.fruitwars.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.fruitwars.FruitWarsMain;

public class IntroScreen implements Screen{

	final FruitWarsMain game;
	OrthographicCamera camera;

	private Stage stage = new Stage();
	private Table table = new Table();


	private Skin skin = new Skin(Gdx.files.internal("skins/menuSkin.json"),
			new TextureAtlas(Gdx.files.internal("skins/menuButtons.pack")));

	private TextButton buttonPlay = new TextButton("Play", skin),
			buttonExit = new TextButton("Exit", skin);

	private Label title = new Label("Fruit Wars",skin);


	public IntroScreen(final FruitWarsMain game) {
		this.game = game;

		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 480);

	}

	@Override
	public void show() {
		buttonPlay.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                //Same way we moved here from the Splash Screen
                //We set it to new Splash because we got no other screens
                //otherwise you put the screen there where you want to go
                game.setScreen(new GameScreen(game));
            }
        });
        buttonExit.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
                // or System.exit(0);
            }
        });

        
		 //The elements are displayed in the order you add them.
	    //The first appear on top, the last at the bottom.
	    table.add(title).padBottom(40).row();
	    table.add(buttonPlay).size(150,60).padBottom(20).row();
	    table.add(buttonExit).size(150,60).padBottom(20).row();

	    table.setFillParent(true);
	    stage.addActor(table);

	    Gdx.input.setInputProcessor(stage);



	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(1.0f, 0.9f, 0.7f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		stage.act();
		stage.draw();

	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void hide() {
		dispose();

	}

	@Override
	public void dispose() {
		stage.dispose();
		skin.dispose();

	}

}
