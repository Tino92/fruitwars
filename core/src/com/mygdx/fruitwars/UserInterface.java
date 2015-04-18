package com.mygdx.fruitwars;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.mygdx.fruitwars.screens.GameScreen;

public class UserInterface{
	
	private final GameScreen gameScreen;
	private Stage stage = new Stage(new StretchViewport(Gdx.graphics.getWidth(),Gdx.graphics.getHeight()));
	private Table table = new Table(),
			buttonTable = new Table();
	private Container pauseContainer;
	
	private Skin skin = new Skin(Gdx.files.internal("skins/uiSkin.json"),
			new TextureAtlas(Gdx.files.internal("skins/menuButtons.pack")));

	private TextButton buttonJump = new TextButton("Jump", skin),
			buttonLeft = new TextButton("<", skin),
			buttonRight = new TextButton(">", skin),
			buttonAim = new TextButton("Aim!",skin);

	private Label player = new Label("Player",skin);
	private Label score = new Label("Score",skin);
	private Label pause = new Label("Paused! Touch the screen to continue",skin);
	
	public UserInterface(final GameScreen gameScreen){
		this.gameScreen = gameScreen;
		
		
		//table.setDebug(true);
		table.add(score).expand().left().top().padLeft(20);
		table.add(player).expand().right().top().padRight(20).row();
		
		pauseContainer = new Container(pause);
		pauseContainer.setFillParent(true);
		pauseContainer.top().center();
		pause.setColor(Color.RED);
		
		//buttonTable.setDebug(true);
		buttonTable.bottom().left();
		buttonTable.add(buttonLeft).size(100, 50).pad(10);
		buttonTable.add(buttonRight).size(100, 50).pad(10);
		buttonTable.add(buttonJump).size(100, 50).pad(10);
		buttonTable.add(buttonAim).size(100, 50).pad(10);
		//table.add(buttonTable).height(50);
		
		
		table.setFillParent(true);
		buttonTable.setFillParent(true);
		stage.addActor(table);
		stage.addActor(buttonTable);
		
		buttonJump.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
            	if (!gameScreen.isPaused()){
            		System.out.println("Jump pressed!");
            		//gameScreen.getCurrentPlayer().getActiveMinion().jump();
            	}
            }
        });
		
		buttonLeft.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
            	if (!gameScreen.isPaused()){
            		System.out.println("Left pressed!");
            		//gameScreen.getCurrentPlayer().getActiveMinion().left();
            	}
            }
        });
		
		buttonRight.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
            	if (!gameScreen.isPaused()){
            		System.out.println("Right pressed!");
            		//gameScreen.getCurrentPlayer().getActiveMinion().right();
            	}
            }
        });
		
		buttonAim.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
            	if (!gameScreen.isPaused()){
            		System.out.println("Aim pressed!");
            		//gameScreen.getCurrentPlayer().getActiveMinion().aiming = true;
            	}
            }
        });
		
	}
	
	public void draw() {
		player.setText("Player " + gameScreen.getCurrentPlayer().getPlayerNumber() + "\n" 
	+ String.format("%02d", gameScreen.getTimeLeft()/60) + " sec");
		score.setText("Score " + gameScreen.getCurrentPlayer().getScore());
		stage.draw();
	}

	public Stage getStage(){
		return stage;
	}
	
	public void showPauseTitle(String title){
		pause.setText(title);
		stage.addActor(pauseContainer);
	}
	
	public void hidePauseTitle(){
		pauseContainer.remove();
	}

	

}

