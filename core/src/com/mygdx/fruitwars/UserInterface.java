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
import com.mygdx.fruitwars.collision.Collision;
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
			buttonAim = new TextButton("Aim!",skin),
			buttonPause = new TextButton("||",skin);

	private Label player = new Label("Player",skin);
	private Label score = new Label("Score",skin);
	private Label pause = new Label("Paused! Touch the screen to continue",skin);
	private Collision collision;
	
	public UserInterface(final GameScreen gameScreen, final Collision collision){
		this.gameScreen = gameScreen;
		this.collision = collision;
		
		//table.setDebug(true);
		table.add(score).expand().left().top().padLeft(20);
		table.add(player).expand().right().top().padRight(20).row();
		
		pauseContainer = new Container(pause);
		pauseContainer.setFillParent(true);
		pauseContainer.top().center();
		pause.setColor(Color.RED);
		
		int buttonWidth = Gdx.graphics.getWidth()/7;
		int buttonHeight = Gdx.graphics.getHeight()/7;

		//buttonTable.setDebug(true);
		buttonTable.bottom();
		buttonTable.add(buttonLeft).size(buttonWidth, buttonHeight).pad(10);
		buttonTable.add(buttonRight).size(buttonWidth, buttonHeight).pad(10);
		buttonTable.add(buttonJump).size(buttonWidth, buttonHeight).pad(10);
		buttonTable.add(buttonAim).size(buttonWidth, buttonHeight).pad(10);
		buttonTable.add(buttonPause).size(buttonWidth, buttonHeight).pad(10);
		
		
		table.setFillParent(true);
		buttonTable.setFillParent(true);
		stage.addActor(table);
		stage.addActor(buttonTable);
		
		buttonJump.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
            	System.out.println("Jump pressed, playerOnGround=" + collision.isPlayerOnGround());
            	if (!gameScreen.isPaused() && collision.isPlayerOnGround()){
            		gameScreen.getCurrentPlayer().getActiveMinion().jump();
            	}
            }
        });
		
		buttonLeft.addListener(new ClickListener(){
			
//			@Override
//			public void clicked(InputEvent event, float x, float y) {
//				System.out.println("left btn clicked, playerOnGround=" + collision.isPlayerOnGround());
//				if (!gameScreen.isPaused()){
//					gameScreen.getCurrentPlayer().getActiveMinion().move_left();
//				}
//			}
			
			@Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
            	if (!gameScreen.isPaused()){
            		System.out.println("Left pressed!");
            		if (collision.isPlayerOnGround()) {
            			gameScreen.getCurrentPlayer().getActiveMinion().moveLeft();            			
            		} else {
            			gameScreen.getCurrentPlayer().getActiveMinion().moveLeftInAir();
            		}
            	}
            	return true;
            }
            
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
            	if (!gameScreen.isPaused() && !collision.isPlayerOnGround()){
            		gameScreen.getCurrentPlayer().getActiveMinion().stopHorizontalMovement();
            	}
            }
        });
		
		buttonRight.addListener(new ClickListener(){
//			@Override
//			public void clicked(InputEvent event, float x, float y) {
//				System.out.println("right btn clicked, playerOnGround=" + collision.isPlayerOnGround());
//				if (!gameScreen.isPaused()){
//					gameScreen.getCurrentPlayer().getActiveMinion().move_right();
//				}
//			}
			
			@Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
            	if (!gameScreen.isPaused()){
            		if (collision.isPlayerOnGround()) {
            			gameScreen.getCurrentPlayer().getActiveMinion().moveRight();            			
            		} else {
            			gameScreen.getCurrentPlayer().getActiveMinion().moveRightInAir();
            		}
            		
            	}
            	return true;
            }
            
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
            	if (!gameScreen.isPaused() && collision.isPlayerOnGround()){
            		gameScreen.getCurrentPlayer().getActiveMinion().stopHorizontalMovement();
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
		
		buttonPause.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
            	if (gameScreen.isPaused())
            		gameScreen.resume();
            	else 
            		gameScreen.pause();
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

