package Projectiles;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.GameProj1;

import GameObjects.BaseObject;

public class Flame extends BaseProjectile{
	
	private String spriteFile = "fireBall.gif";
	
	private float curTime;
	private boolean hasDamaged;
	
	public BitmapFont font;

	public Flame(BaseObject owner, Vector2 spawn, Vector2 dir) {
		super(owner, spawn, dir);
		
		this.sprite = new Sprite(new Texture(spriteFile));
		this.font = new BitmapFont();
		
		this.dmg = 5f;
		
		Random rand = new Random();
		float max = 22f;
		float min = 20f;
		this.maxSpd = rand.nextFloat() * (max - min) + min;
		
		this.scaleX = 0.125f;
		this.scaleY = 0.125f;
		
		this.curTime = 0f;
		float ttlMax = .9f;
		float ttlMin = .6f;
		this.ttl = rand.nextFloat() * (ttlMax - ttlMin) + ttlMin;
		
		this.hasDamaged = false;
	}
	
	public void update(float delta){
		super.update(delta);
		
		if(!this.isDead){
			this.curTime += Gdx.graphics.getRawDeltaTime();
			if(this.curTime > this.ttl)
				this.isDead = true;
		}
	}
	
	@Override
	public void render(){
		GameProj1.batch.begin();
		float tmp = 1f - this.curTime/this.ttl;
		if(tmp < .2f)
			tmp = 0;
		GameProj1.batch.setColor(1f, 1f, 1f, tmp);
		GameProj1.batch.draw(this.sprite, this.pos.x - this.sprite.getWidth()/2, 
							 this.pos.y - this.sprite.getHeight()/2, this.sprite.getOriginX(), 
							 this.sprite.getOriginY(), this.sprite.getWidth(), this.sprite.getHeight(), 
							 this.scaleX, this.scaleY, this.angle);
		GameProj1.batch.setColor(1f, 1f, 1f, 1f);
		GameProj1.batch.end();
	}
	
	@Override
	public void damageObj(BaseObject o, float dmg){
		float tmp = 1f - this.curTime/this.ttl;
		if(!this.hasDamaged && tmp >= .5f){
			super.damageObj(o, dmg * (1f - (1f-tmp)/.5f));
			this.hasDamaged = true;
		}
	}
}
