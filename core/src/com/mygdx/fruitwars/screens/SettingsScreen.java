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

import static com.mygdx.fruitwars.utils.Constants.*;

public class SettingsScreen implements Screen{
	
	private FruitWarsMain game;
	
	private Stage stage = new Stage(new StretchViewport(Gdx.graphics.getWidth(),Gdx.graphics.getHeight()));
	private Table table = new Table();


	private Skin skin = new Skin(Gdx.files.internal("skins/menuSkin.json"),
			new TextureAtlas(Gdx.files.internal("skins/menuButtons.pack")));

	private TextButton buttonEasy = new TextButton("Easy", skin),
			buttonNormal = new TextButton("Normal", skin), 
			buttonHard = new TextButton("Hard", skin),
			buttonExit = new TextButton("Exit", skin) ;

	private Label title = new Label("Settings",skin);

	
	public SettingsScreen(final FruitWarsMain game) {
		this.game = game;

	}

	@Override
	public void show() {
		
		Preferences prefs = Gdx.app.getPreferences("fruitwars");
		int difficulty = prefs.getInteger("difficulty",0);
		
		if (difficulty == EASY)
			buttonEasy.setChecked(true);
		else if (difficulty == NORMAL)
			buttonNormal.setChecked(true);
		else if (difficulty == HARD)
			buttonHard.setChecked(true);
		
		buttonEasy.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
            	Preferences prefs = Gdx.app.getPreferences("fruitwars");
    			prefs.putInteger("difficulty",EASY);
    			game.difficultyConfig  = prefs.getInteger("difficulty",0);
    			buttonEasy.setChecked(true);
    			buttonNormal.setChecked(false);
    			buttonHard.setChecked(false);
            }
        });
		
        buttonNormal.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
            	Preferences prefs = Gdx.app.getPreferences("fruitwars");
    			prefs.putInteger("difficulty",NORMAL);
    			game.difficultyConfig  = prefs.getInteger("difficulty",0);
    			buttonNormal.setChecked(true);
    			buttonEasy.setChecked(false);
    			buttonHard.setChecked(false);
            }
        });
        
        buttonHard.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
            	Preferences prefs = Gdx.app.getPreferences("fruitwars");
    			prefs.putInteger("difficulty",HARD);
    			game.difficultyConfig  = prefs.getInteger("difficulty",0);
    			buttonHard.setChecked(true);
    			buttonNormal.setChecked(false);
    			buttonEasy.setChecked(false);
            }
        });
        
        buttonExit.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
            	game.setScreen(new IntroScreen(game));
            }
        });
        
        table.add(title).padBottom(80).colspan(3).row();
	    table.add(buttonEasy).size(150,60).padBottom(20).padRight(20);
	    table.add(buttonNormal).size(150,60).padBottom(20).padRight(20);
	    table.add(buttonHard).size(150,60).padBottom(20).padRight(20).row();
	    table.add();
	    table.add(buttonExit).size(150,60);

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
