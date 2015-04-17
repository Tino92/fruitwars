package com.mygdx.fruitwars;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
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
	
	private Skin skin = new Skin(Gdx.files.internal("skins/uiSkin.json"),
			new TextureAtlas(Gdx.files.internal("skins/menuButtons.pack")));

	private TextButton buttonJump = new TextButton("Jump", skin),
			buttonLeft = new TextButton("<", skin),
			buttonRight = new TextButton(">", skin),
			buttonAim = new TextButton("Aim!",skin);

	private Label player = new Label("Player",skin);
	private Label score = new Label("Score",skin);
	
	public UserInterface(final GameScreen gameScreen){
		this.gameScreen = gameScreen;
		
		
		//table.setDebug(true);
		table.add(score).expand().left().top().padLeft(20);
		table.add(player).expand().right().top().padRight(20).row();
		buttonTable.add(buttonLeft).size(100, 50).left().expand().pad(10);
		buttonTable.add(buttonRight).size(100, 50).left().pad(10);
		buttonTable.add(buttonJump).size(100, 50).center().pad(10);
		table.add(buttonTable).height(50);
		table.add(buttonAim).size(100, 50);
		
		table.setFillParent(true);
		stage.addActor(table);
		
		buttonJump.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
            	System.out.println("Jump pressed!");
                //gameScreen.getCurrentPlayer().getActiveMinion().jump();
            }
        });
		
		buttonLeft.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
            	System.out.println("Left pressed!");
                //gameScreen.getCurrentPlayer().getActiveMinion().left();
            }
        });
		
		buttonRight.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
            	System.out.println("Right pressed!");
                //gameScreen.getCurrentPlayer().getActiveMinion().right();
            }
        });
		
		buttonAim.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
            	System.out.println("Aim pressed!");
                //gameScreen.getCurrentPlayer().getActiveMinion().aiming = true;
            }
        });
		
	}
	
	public void draw() {
		player.setText("Player " + gameScreen.getCurrentPlayer().getPlayerNumber());
		score.setText("Score " + gameScreen.getCurrentPlayer().getScore());
		stage.draw();
	}

	public Stage getStage(){
		return stage;
	}

}

