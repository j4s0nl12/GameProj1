package Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.GameProj1;

public class BaseScreen extends InputAdapter implements Screen{

	final GameProj1 game;
	
	public boolean isInit;
	
	public BaseScreen(final GameProj1 gam){
		game = gam;
		this.isInit = false;
	}
	
	
	@Override
	public void show() {
		Gdx.input.setInputProcessor(this);
	}

	@Override
	public void render(float delta) {
		if(Gdx.input.isKeyPressed(Keys.ESCAPE) && game.debugOn)
			Gdx.app.exit();
		
		Gdx.gl.glClearColor(0f, 0f, 0f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		GameProj1.camera.update();
		GameProj1.batch.setProjectionMatrix(GameProj1.camera.combined);
	}

	@Override
	public void resize(int width, int height) {
		game.resize(width, height);
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
		Gdx.input.setInputProcessor(null);
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
	}
	
	public Vector2 getProjectAt(float x, float y){
		Vector3 v = GameProj1.camera.unproject(new Vector3(x, y, 0));
		return (new Vector2(v.x, v.y));
	}

}
