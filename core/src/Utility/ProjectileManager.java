package Utility;

import java.util.Iterator;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.GameProj1;

import Projectiles.BaseProjectile;

public class ProjectileManager {
	
	public Array<BaseProjectile> projList;
	public Rectangle screenRect;
	
	public ProjectileManager(){
		this.projList = new Array<>();
		this.screenRect = new Rectangle(0,0, GameProj1.GAME_WORLD_WIDTH, GameProj1.GAME_WORLD_HEIGHT);
	}
	
	public void update(float delta){
		for(BaseProjectile p : this.projList){
			p.update(delta);
			
			if(!p.sprite.getBoundingRectangle().overlaps(screenRect))
				p.isDead = true;
		}
		checkDestroy();
	}
	
	public void add(BaseProjectile p){
		this.projList.add(p);
	}
	
	public void checkDestroy(){
		Iterator<BaseProjectile> it = this.projList.iterator();
		for(; it.hasNext();){
			BaseProjectile tmp = it.next();
			if(tmp.isDead){
				it.remove();
			}
		}
	}
	
	public BaseProjectile get(int idx){
		return this.projList.get(idx);
	}
	
	public int size(){
		return this.projList.size;
	}
}
