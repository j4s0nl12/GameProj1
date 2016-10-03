package Skills;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.GameProj1;

import GameObjects.BaseObject;

public class BaseSkill {

	BaseObject skillOwner;
	
	public BaseSkill(BaseObject owner){
		this.skillOwner = owner;
	}
	
	public void update(float delta){
		
	}
	
	public void skillActive(){
		
	}
	
	public void setCheats(boolean b){
		
	}
	
	public Vector2 getMousePos(){
		Vector3 v = GameProj1.camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
		return new Vector2(v.x, v.y);
	}
}
