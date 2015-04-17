package com.mygdx.fruitwars;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.mygdx.fruitwars.screens.GameScreen;

public class UserInterface{
	
	private GameScreen gameScreen;
	private Stage stage = new Stage(new StretchViewport(Gdx.graphics.getWidth(),Gdx.graphics.getHeight()));
	private Table table = new Table();
	private Container<Label> scoreContainer,playerContainer;
	
	private Skin skin = new Skin(Gdx.files.internal("skins/uiSkin.json"),
			new TextureAtlas(Gdx.files.internal("skins/menuButtons.pack")));

	private TextButton buttonPlay = new TextButton("Play", skin),
			buttonSettings = new TextButton("Settings", skin),
			buttonExit = new TextButton("Exit", skin);

	private Label player = new Label("Player",skin);
	private Label score = new Label("Score",skin);
	
	public UserInterface(GameScreen gameScreen){
		this.gameScreen = gameScreen;
		
		scoreContainer = new Container<Label>(score);
		scoreContainer.setFillParent(true);
		scoreContainer.left().top().padLeft(20).padTop(20);
		
		playerContainer = new Container<Label>(player);
		playerContainer.setFillParent(true);
		playerContainer.right().top().padRight(20).padTop(20);
		
		stage.addActor(scoreContainer);
		stage.addActor(playerContainer);
		
	}
	
	public void draw() {
		player.setText("Player " + gameScreen.getCurrentPlayer().getPlayerNumber());
		score.setText("Score: " + gameScreen.getCurrentPlayer().getScore());
		stage.draw();
	}

	public Stage getStage(){
		return stage;
	}

}

