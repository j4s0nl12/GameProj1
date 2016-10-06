package Player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.GameProj1;

import GameObjects.BaseObject;
import Skills.BaseSkill;
import Skills.Flamethrower;
import Skills.TimeLapse;

public class Player extends BaseObject{
	
	private String spriteFile = "Human.png";
	
	public boolean isMovingX;
	public boolean isMovingY;

	public Player(float x, float y, float angle) {
		super(x, y, angle);
		this.sprite = new Sprite(new Texture(spriteFile));
		this.skills.add(new Flamethrower(this));
		this.skills.add(new TimeLapse(this));
	}
	
	public void update(float delta){
		super.update(delta);
		for(BaseSkill s : this.skills)
			s.update(delta);
		
		if(this.vel.x != 0 && !this.isMovingX)
			this.frictionX(this.frict, delta);
		if(this.vel.y != 0 && !this.isMovingY)
			this.frictionY(this.frict, delta);
		
		if(!Gdx.input.isKeyPressed(GameProj1.pMoveUpKey & GameProj1.pMoveDownKey)) {
			this.isMovingY = false;
		}
		if(!Gdx.input.isKeyPressed(GameProj1.pMoveLeftKey & GameProj1.pMoveRightKey)){
			this.isMovingX = false;
		}
		
		float spd = this.maxSpd*2;
		
		Vector2 m = GameProj1.getMousePos();
		Vector2 dir = m.cpy().sub(this.pos);
		this.angle = dir.angle() - 90;
		
		if(Gdx.input.isKeyPressed(GameProj1.pMoveUpKey)){
			if(this.vel.y < 0)
				this.vel.y = 0;
			this.accel(new Vector2(0f,spd), delta);
			this.isMovingY = true;
		}
		if(Gdx.input.isKeyPressed(GameProj1.pMoveDownKey)){
			if(this.vel.y > 0)
				this.vel.y = 0;
			this.accel(new Vector2(0f,-spd), delta);
			this.isMovingY = true;
		}
		if(Gdx.input.isKeyPressed(GameProj1.pMoveLeftKey)){
			if(this.vel.x > 0)
				this.vel.x = 0;
			this.accel(new Vector2(-spd,0f), delta);
			this.isMovingX = true;
		}
		if(Gdx.input.isKeyPressed(GameProj1.pMoveRightKey)){
			if(this.vel.x < 0)
				this.vel.x = 0;
			this.accel(new Vector2(spd,0f), delta);
			this.isMovingX = true;
		}
		if(Gdx.input.isTouched()){
			this.skills.get(0).skillActive();
		}
		if(Gdx.input.isKeyPressed(Keys.E)){
			this.skills.get(1).skillActive();
		}
		
		if(Gdx.input.isKeyPressed(Keys.R)){
			this.skills.get(0).setCheats(true);
		}
		if(Gdx.input.isKeyPressed(Keys.T)){
			this.skills.get(1).setCheats(true);
		}
		if(Gdx.input.isKeyPressed(Keys.NUM_1)){
			this.hp -= 10;
		}
	}
	
	public void frictionY(float f, float delta){
		Vector2 old = this.vel.cpy();
		Vector2 tmp = this.vel.cpy().nor();
		Vector2 tmp2 = new Vector2(0f, tmp.y).scl(-f);
		this.vel.add(tmp2.scl(delta));
		if(this.vel.dot(old) < 0)
			this.vel.y = 0;
	}
	
	public void frictionX(float f, float delta){
		Vector2 old = this.vel.cpy();
		Vector2 tmp = this.vel.cpy().nor();
		Vector2 tmp2 = new Vector2(tmp.x, 0f).scl(-f);
		this.vel.add(tmp2.scl(delta));
		if(this.vel.dot(old) < 0)
			this.vel.x = 0;
	}
}
