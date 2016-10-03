package Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.GameProj1;

import GameObjects.BaseObject;
import GameObjects.Enemy;
import Player.Player;

public class GameScreen extends BaseScreen{

	public ShapeRenderer sr;
	
	public GameScreen(GameProj1 gam) {
		super(gam);
		GameProj1.om.add(new Player(100,100,0));
		GameProj1.om.add(new Enemy(700,300,0));
		
		sr = new ShapeRenderer();
		sr.setAutoShapeType(true);
	}
	
	public void render(float delta){
		super.render(delta);
		GameProj1.om.update(delta);
		
		sr.begin();
		BaseObject o = GameProj1.om.get(0);
		Vector2 v = getProjectAt(Gdx.input.getX(), Gdx.input.getY());
		sr.circle(v.x, v.y, 10);
		sr.line(o.pos, v);	
		sr.end();
	}
	
	public Vector2 getProjectAt(float x, float y){
		return super.getProjectAt(x, y);
	}
}
