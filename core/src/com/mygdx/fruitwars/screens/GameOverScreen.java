package com.mygdx.fruitwars.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
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

public class GameOverScreen implements Screen{

	final Game game;

	private Music music;
	
	private Stage stage = new Stage(new StretchViewport(Gdx.graphics.getWidth(),Gdx.graphics.getHeight()));
	private Table table = new Table();


	private Skin skin = new Skin(Gdx.files.internal("skins/menuSkin.json"),
			new TextureAtlas(Gdx.files.internal("skins/menuButtons.pack")));

	private TextButton buttonRestart = new TextButton("Restart", skin),
			buttonExit = new TextButton("Exit", skin);

	private Label title = new Label("Game Over",skin);


	public GameOverScreen(int score1,int score2) {
		this.game = ((Game)Gdx.app.getApplicationListener());
		if (score1 > score2)
			title.setText("Player 1 Wins!");
		else
			title.setText("Player 2 Wins!");

	}

	@Override
	public void show() {
		
		buttonRestart.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new IntroScreen());
            }
        });
		
        buttonExit.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });
        

        
		int buttonWidth = Gdx.graphics.getWidth()/7;
		int buttonHeight = Gdx.graphics.getHeight()/7;
		
		
		//NinePatch bg = new NinePatch(new Texture(Gdx.files.internal("backgrounds/gameover.png")), 1, 1, 1, 12);
		//table.setBackground(new NinePatchDrawable(bg));
	    table.add(title).padBottom(80).row();
	    table.add(buttonRestart).size(buttonWidth,buttonHeight).padBottom(20).row();
	    table.add(buttonExit).size(buttonWidth,buttonHeight).padBottom(20).row();

	    table.setFillParent(true);
	    stage.addActor(table);
	    
	    Gdx.input.setInputProcessor(stage);
	    
	    
	    //Setting up music of the main menu
	    music = Gdx.audio.newMusic(Gdx.files.internal("music/game-over.wav"));
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
