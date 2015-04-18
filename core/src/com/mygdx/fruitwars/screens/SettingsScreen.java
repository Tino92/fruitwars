package com.mygdx.fruitwars.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.mygdx.fruitwars.FruitWarsMain;
import com.mygdx.fruitwars.utils.Constants;

import static com.mygdx.fruitwars.utils.Constants.*;

public class SettingsScreen implements Screen{
	
	private FruitWarsMain game;
	
	private Stage stage = new Stage(new StretchViewport(Gdx.graphics.getWidth(),Gdx.graphics.getHeight()));
	private Table table = new Table();


	private Skin skin = new Skin(Gdx.files.internal("skins/menuSkin.json"),
			new TextureAtlas(Gdx.files.internal("skins/menuButtons.pack")));

	private TextButton buttonDefault = new TextButton("Default", skin),
			buttonHighPace = new TextButton("HighPace", skin), 
			buttonJuggernaut = new TextButton("Juggernaut", skin),
			buttonOneShot = new TextButton("OneShot", skin),
			buttonExit = new TextButton("Ok", skin) ;

	private Label title = new Label("Settings",skin);

	
	public SettingsScreen(final FruitWarsMain game) {
		this.game = game;

	}

	@Override
	public void show() {
		
		Preferences prefs = Gdx.app.getPreferences("fruitwars");
		int gameMode = prefs.getInteger("gameMode",DEFAULT_GAME_MODE);
		
		if (gameMode == DEFAULT_GAME_MODE)
			buttonDefault.setChecked(true);
		else if (gameMode == HIGH_PACE)
			buttonHighPace.setChecked(true);
		else if (gameMode == JUGGERNAUT)
			buttonJuggernaut.setChecked(true);
		else if (gameMode == ONESHOT)
			buttonOneShot.setChecked(true);
		
		buttonDefault.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
            	Preferences prefs = Gdx.app.getPreferences("fruitwars");
    			prefs.putInteger("gameMode",DEFAULT_GAME_MODE);
    			game.gameMode  = prefs.getInteger("gameMode",DEFAULT_GAME_MODE);
    			buttonDefault.setChecked(true);
    			buttonHighPace.setChecked(false);
    			buttonJuggernaut.setChecked(false);
    			buttonOneShot.setChecked(false);
            }
        });
		
        buttonHighPace.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
            	Preferences prefs = Gdx.app.getPreferences("fruitwars");
    			prefs.putInteger("gameMode",HIGH_PACE);
    			game.gameMode  = prefs.getInteger("gameMode",DEFAULT_GAME_MODE);
    			buttonDefault.setChecked(false);
    			buttonHighPace.setChecked(true);
    			buttonJuggernaut.setChecked(false);
    			buttonOneShot.setChecked(false);
            }
        });
        
        buttonJuggernaut.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
            	Preferences prefs = Gdx.app.getPreferences("fruitwars");
    			prefs.putInteger("gameMode",JUGGERNAUT);
    			game.gameMode  = prefs.getInteger("gameMode",DEFAULT_GAME_MODE);
    			buttonDefault.setChecked(false);
    			buttonHighPace.setChecked(false);
    			buttonJuggernaut.setChecked(true);
    			buttonOneShot.setChecked(false);
            }
        });
        
        buttonOneShot.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
            	Preferences prefs = Gdx.app.getPreferences("fruitwars");
    			prefs.putInteger("gameMode",ONESHOT);
    			game.gameMode  = prefs.getInteger("gameMode",DEFAULT_GAME_MODE);
    			buttonDefault.setChecked(false);
    			buttonHighPace.setChecked(false);
    			buttonJuggernaut.setChecked(false);
    			buttonOneShot.setChecked(true);
            }
        });
        
        buttonExit.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
            	game.setScreen(new IntroScreen(game));
            }
        });
        
        //table.setDebug(true);
        table.add(title).padBottom(80).colspan(4).row();
	    table.add(buttonDefault).size(150,60).padBottom(20).padRight(20);
	    table.add(buttonHighPace).size(150,60).padBottom(20).padRight(20);
	    table.add(buttonJuggernaut).size(150,60).padBottom(20).padRight(20);
	    table.add(buttonOneShot).size(150,60).padBottom(20).padRight(20).row();
	    table.add(buttonExit).colspan(4).size(150,60);

	    table.setFillParent(true);
	    stage.addActor(table);

	    Gdx.input.setInputProcessor(stage);
		
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
		
	}

}
