package com.mygdx.fruitwars.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.mygdx.fruitwars.FruitWarsMain;

public class IntroScreen implements Screen{

	final FruitWarsMain game;

	private Music music;
	
	private Stage stage = new Stage(new StretchViewport(Gdx.graphics.getWidth(),Gdx.graphics.getHeight()));
	private Table table = new Table();


	private Skin skin = new Skin(Gdx.files.internal("skins/menuSkin.json"),
			new TextureAtlas(Gdx.files.internal("skins/menuButtons.pack")));

	private TextButton buttonPlay = new TextButton("Play", skin),
			buttonSettings = new TextButton("Settings", skin),
			buttonExit = new TextButton("Exit", skin);

	private Label title = new Label("Fruit Wars",skin);


	public IntroScreen(final FruitWarsMain game) {
		this.game = game;

	}

	@Override
	public void show() {
		
		buttonPlay.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new GameOverScreen(game));
            }
        });
		
        buttonExit.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });
        
        buttonSettings.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
            	game.setScreen(new SettingsScreen(game));
            }
        });


        NinePatch bg = new NinePatch(new Texture(Gdx.files.internal("backgrounds/background-main.png")), 1, 1, 1, 12);
		//The elements are displayed in the order you add them.
	    //The first appear on top, the last at the bottom.
        table.setBackground(new NinePatchDrawable(bg));
	    table.add(title).padBottom(80).row();
	    table.add(buttonPlay).size(150,60).padBottom(20).row();
	    table.add(buttonSettings).size(150,60).padBottom(20).row();
	    table.add(buttonExit).size(150,60).padBottom(20).row();

	    table.setFillParent(true);
	    stage.addActor(table);

	    Gdx.input.setInputProcessor(stage);
	    
	    
	    //Setting up music of the main menu
	    music = Gdx.audio.newMusic(Gdx.files.internal("music/main-menu.mp3"));
	    music.setLooping(true);
	    music.play();



	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		stage.act();
		stage.draw();

	}

	@Override
	public void resize(int width, int height) {
	    // use true here to center the camera
	    // that's what you probably want in case of a UI
	    stage.getViewport().update(width, height, false);

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
		music.dispose();

	}

}
