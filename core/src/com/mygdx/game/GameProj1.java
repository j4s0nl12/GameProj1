package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import Utility.ObjectManager;
import Utility.ScreenManager;

public class GameProj1 extends Game{
	
	public boolean debugOn = true;
	public ScreenManager sm;
	public static ObjectManager om;
	
	private Preferences pref;
	
	public static final int GAME_WORLD_WIDTH = 1366;
	public static final int GAME_WORLD_HEIGHT = 768;
	public static final String PREF_NAME = "mygame_pref";
	
	public static OrthographicCamera camera;
	public Viewport viewport;
	
	public static SpriteBatch batch;
	
	//Pref values
	public static final String PREF_VOLUME = "volume";
	public static final String PREF_PLAYER_UP_KEY = "pMoveUpKey";
	public static final String PREF_PLAYER_DOWN_KEY = "pMoveDownKey";
	public static final String PREF_PLAYER_LEFT_KEY = "pMoveLeftKey";
	public static final String PREF_PLAYER_RIGHT_KEY = "pMoveRightKey";
	
	public static float volume;
	public static int pMoveUpKey;
	public static int pMoveDownKey;
	public static int pMoveLeftKey;
	public static int pMoveRightKey;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		
		camera = new OrthographicCamera();
		viewport = new ExtendViewport(GAME_WORLD_WIDTH, GAME_WORLD_HEIGHT, camera);
		viewport.apply();
		camera.position.set(GAME_WORLD_WIDTH/2, GAME_WORLD_HEIGHT/2, 0);
		
		initPref();
		
		om = new ObjectManager();
		sm = new ScreenManager(this);
	}

	@Override
	public void resize(int width, int height){
		viewport.update(width, height);
		camera.position.set(GAME_WORLD_WIDTH/2, GAME_WORLD_HEIGHT/2, 0);
	}
	
	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose(){
		batch.dispose();
	}
	
	public static ObjectManager getOM(){
		return om;
	}

	public static Vector2 getMousePos(){
		Vector3 v = GameProj1.camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
		return new Vector2(v.x, v.y);
	}
	
	public void initPref(){
		if(pref == null)
			pref = Gdx.app.getPreferences(PREF_NAME);
		
		pref.clear();
		
		//Volume
		if(!pref.contains(PREF_VOLUME)){
			volume = 0.02f;
			pref.putFloat(PREF_VOLUME, volume);
		}else{
			volume = pref.getFloat(PREF_VOLUME);
		}
		
		//Player Controls
		//Up
		if(!pref.contains(PREF_PLAYER_UP_KEY)){
			pMoveUpKey = Keys.W;
			pref.putInteger(PREF_PLAYER_UP_KEY, pMoveUpKey);
		}else{
			pMoveUpKey = pref.getInteger(PREF_PLAYER_UP_KEY);
		}
		//Down
		if(!pref.contains(PREF_PLAYER_DOWN_KEY)){
			pMoveDownKey = Keys.S;
			pref.putInteger(PREF_PLAYER_DOWN_KEY, pMoveDownKey);
		}else{
			pMoveDownKey = pref.getInteger(PREF_PLAYER_DOWN_KEY);
		}
		//Left
		if(!pref.contains(PREF_PLAYER_LEFT_KEY)){
			pMoveLeftKey = Keys.A;
			pref.putInteger(PREF_PLAYER_LEFT_KEY, pMoveLeftKey);
		}else{
			pMoveLeftKey = pref.getInteger(PREF_PLAYER_LEFT_KEY);
		}
		//Right
		if(!pref.contains(PREF_PLAYER_RIGHT_KEY)){
			pMoveRightKey = Keys.D;
			pref.putInteger(PREF_PLAYER_RIGHT_KEY, pMoveRightKey);
		}else{
			pMoveLeftKey = pref.getInteger(PREF_PLAYER_RIGHT_KEY);
		}
		

		pref.flush();
	}
}
