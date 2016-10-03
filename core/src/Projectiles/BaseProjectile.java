package Projectiles;

import java.text.DecimalFormat;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.GameProj1;

import GameObjects.BaseObject;

public class BaseProjectile{
	
	public Sprite sprite;

	public BaseObject owner;
	
	public Vector2 pos;
	public Vector2 dir;
	public Vector2 vel;
	public float angle;
	
	public float maxSpd;
	public float ttl;//time to live
	public float dmg;
	
	public float scaleX;
	public float scaleY;
	
	public boolean isDead;
	
	public BaseProjectile(BaseObject owner, Vector2 spawn, Vector2 mouse){
		this.owner = owner;
		this.pos = spawn;
		this.dir = mouse.cpy().sub(this.pos).nor();
		this.vel = new Vector2();
		
		this.dmg = 1f;
		
		this.ttl = 9999f;
		this.isDead = false;
		
		this.scaleX = 1f;
		this.scaleY = 1f;
		
		Vector3 v = GameProj1.camera.unproject(new Vector3(mouse.x, mouse.y, 0));
		Vector2 v2 = new Vector2(v.x, v.y);
		Vector2 tmp = v2.cpy().sub(this.pos);
		this.angle = tmp.angle() - 90;
	}
	
	public void update(float delta){
		if(this.sprite != null){
			this.accel(this.dir.cpy().scl(this.maxSpd), delta);
			this.pos.add(this.vel);
			this.sprite.setPosition(this.pos.x - this.sprite.getWidth()/2,  this.pos.y - this.sprite.getHeight()/2);
			this.sprite.setRotation(this.angle);
			this.render();
			
			this.checkCollision();
		}
	}
	
	public void checkCollision(){
		for(int i = 0; i < GameProj1.getOM().size(); i++){
			BaseObject o = GameProj1.getOM().get(i);
			if(this.sprite.getBoundingRectangle().overlaps(o.sprite.getBoundingRectangle())){
				if(!o.equals(this.owner))
					damageObj(o,this.dmg);
			}
		}
	}
	
	public void damageObj(BaseObject o, float dmg){
		DecimalFormat df = new DecimalFormat("#.#");
		dmg = Float.parseFloat(df.format(dmg));
		o.takeDamage(dmg);
	}
	
	public void render(){
		GameProj1.batch.begin();
		GameProj1.batch.draw(this.sprite, this.pos.x - this.sprite.getWidth()/2, 
							 this.pos.y - this.sprite.getHeight()/2, this.sprite.getOriginX(), 
							 this.sprite.getOriginY(), this.sprite.getWidth(), this.sprite.getHeight(), 
							 this.scaleX, this.scaleY, this.angle);
		GameProj1.batch.end();
	}
	
	public void accel(Vector2 acc, float delta){
		this.vel.add(acc.scl(delta));
		if(this.vel.dot(this.vel) > Math.pow(this.maxSpd, 2))
			this.vel.nor().scl(this.maxSpd);
	}
}
