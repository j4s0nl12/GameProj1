package GameObjects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.GameProj1;

import DamageNumber.DNManager;
import DamageNumber.DamageNumber;
import Skills.BaseSkill;

public class BaseObject {
	
	public Sprite sprite;
	
	public DNManager dnM;
	
	public Vector2 pos;
	public Vector2 vel;
	public float angle;
	
	public float maxSpd;
	public float frict;
	public float turnSpd;
	public float hp;
	public float maxHp;
	
	public boolean isDead = false;
	
	public ShapeRenderer sr;
	public Rectangle bar;
	public Rectangle hpBar;
	
	public Array<BaseSkill> skills;
	
	public BaseObject(float x, float y, float angle){
		this.pos = new Vector2(x,y);
		this.vel = new Vector2();
		this.angle = angle;
		
		this.maxSpd = 8f;
		this.frict = 16f;
		this.turnSpd = 5f;
		this.maxHp = 100;
		this.hp = 100;
		
		this.dnM = new DNManager();
		
		this.isDead = false;
		
		this.sr = new ShapeRenderer();
		this.skills = new Array<>();
	}
	
	public void update(float delta){
		if(this.sprite != null){
			this.pos.add(this.vel);
			this.blockBorder();
			//this.wrapAroundBorder();
			this.sprite.setPosition(this.pos.x - this.sprite.getWidth()/2, this.pos.y - this.sprite.getHeight()/2);
			
			this.bar = new Rectangle(this.pos.x - this.sprite.getWidth()/2,this.pos.y + this.sprite.getHeight()/2 + 10f,this.sprite.getWidth(),10f);
			float tmp = this.hp/this.maxHp;
			this.hpBar = new Rectangle(this.pos.x - this.sprite.getWidth()/2,this.pos.y + this.sprite.getHeight()/2 + 10f,this.sprite.getWidth() * tmp,10f);
			
			this.sprite.setRotation(this.angle);
			this.render();
			
			this.dnM.update(delta);
		}
	}
	
	public void render(){
		GameProj1.batch.begin();
		GameProj1.batch.draw(this.sprite, this.pos.x - this.sprite.getWidth()/2, this.pos.y - this.sprite.getHeight()/2, this.sprite.getOriginX(), this.sprite.getOriginY(), this.sprite.getWidth(), this.sprite.getHeight(), 1, 1, this.angle);
		GameProj1.batch.end();
		
		this.sr.begin(ShapeType.Filled);
		this.sr.setColor(new Color(1f,0f,0f,1));
		this.sr.rect(bar.x, bar.y, bar.width, bar.height);
		this.sr.setColor(new Color(0f,1f,0f,1));
		this.sr.rect(hpBar.x, hpBar.y, hpBar.width, hpBar.height);
		this.sr.end();
	}
	
	public void accel(Vector2 acc, float delta){
		this.vel.add(acc.scl(delta));
		if(this.vel.dot(this.vel) > Math.pow(this.maxSpd, 2)){
			this.vel.nor().scl(this.maxSpd);
		}
	}
	
	public void friction(float f, float delta){
		Vector2 old = this.vel.cpy();
		Vector2 tmp = this.vel.cpy();
		Vector2 frict = tmp.nor().scl(-f);
		this.vel.add(frict.scl(delta));
		if(this.vel.dot(old) < 0)
			this.vel = new Vector2(0,0);
	}
	
	public void takeDamage(float dmg){
		this.hp -= dmg;
		this.dnM.add(new DamageNumber(this.pos.x, this.pos.y + 50f, Float.toString(-dmg)));
		if(this.hp < 0)
			this.isDead = true;
	}
	
	public void blockBorder(){
		float borderMinX = this.sprite.getWidth()/2;
		float borderMaxX = GameProj1.GAME_WORLD_WIDTH - this.sprite.getWidth()/2;
		float borderMinY = this.sprite.getHeight()/2;
		float borderMaxY = GameProj1.GAME_WORLD_HEIGHT - this.sprite.getHeight()/2;
		if(this.pos.x < borderMinX){
			this.pos.x = borderMinX;
		}else if(this.pos.x > borderMaxX){
			this.pos.x = borderMaxX;
		}
		if(this.pos.y < borderMinX){
			this.pos.y = borderMinY;
		}else if(this.pos.y > borderMaxY){
			this.pos.y = borderMaxY;
		}
	}
	
	public void wrapAroundBorder(){
		float borderMinX = -this.sprite.getWidth()/2;
		float borderMaxX = GameProj1.GAME_WORLD_WIDTH + this.sprite.getWidth()/2;
		float borderMinY = -this.sprite.getHeight()/2;
		float borderMaxY = GameProj1.GAME_WORLD_HEIGHT + this.sprite.getHeight()/2;
		if(this.pos.x < borderMinX){
			this.pos.x = borderMaxX;
		}else if(this.pos.x > borderMaxX){
			this.pos.x = borderMinX;
		}
		if(this.pos.y < borderMinY){
			this.pos.y = borderMaxY;
		}else if(this.pos.y > borderMaxY){
			this.pos.y = borderMinY;
		}
	}
}
