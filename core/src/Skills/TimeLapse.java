package Skills;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.mygdx.game.GameProj1;

import GameObjects.BaseObject;

public class TimeLapse extends BaseSkill{
	
	public class Stats{
		public float hp;
		public long time;
		public float x;
		public float y;
		public float angle;
		
		public Stats(BaseObject o){
			this.hp = o.hp;
			this.time = TimeUtils.millis();
			this.x = o.pos.x;
			this.y = o.pos.y;
			this.angle = o.angle;
		}
	}
	
	public Array<Stats> pastStats;
	public long lastUpdate;
	
	public float minAlpha;
	public float maxAlpha;
	
	public long curTime;
	public long maxDelay;//in milliseconds
	
	public long cooldown;
	public long lastUsed;
	public boolean onCd;
	
	public BitmapFont font;
	public GlyphLayout layout;
	public Color textColor;
	public String str;
	public float offsetX;
	
	public TimeLapse(BaseObject owner) {
		super(owner);
		this.pastStats = new Array<>();
		this.minAlpha = .04f;
		this.maxAlpha = .06f;
		
		this.maxDelay = 3000;
		this.cooldown = 3000;
		
		this.curTime = TimeUtils.millis();
		this.lastUpdate = this.curTime;
		this.lastUsed = this.curTime - this.cooldown;
		this.onCd = false;
		
		this.textColor = new Color(1f,1f,1f,1);
		this.str = "";
		this.font = new BitmapFont();
		this.layout = new GlyphLayout();
		this.layout.setText(this.font, this.str);
		this.offsetX = layout.width/2;
	}
	
	@Override
	public void update(float delta){
		this.curTime = TimeUtils.millis();
		//Handle cooldown
		if(this.curTime > this.lastUsed + this.cooldown){
			this.onCd = false;
		}else{
			this.onCd = true;
		}
		//Update list
		if(this.curTime > TimeUtils.timeSinceMillis(this.curTime) + this.lastUpdate && !this.onCd){
			this.pastStats.add(new Stats(this.skillOwner));
			if(this.pastStats.first().time + this.maxDelay < this.curTime)
				this.pastStats.removeIndex(0);
			this.lastUpdate = this.curTime;
		}
		this.render();
	}
	
	public void render(){
		GameProj1.batch.begin();
		for(int i = 0; i < this.pastStats.size; i++){
			Stats s = this.pastStats.get(i);
			float alpha = ((float)(this.pastStats.size-i)/(float)this.pastStats.size) * this.maxAlpha;
			if(alpha < this.minAlpha)
				alpha = this.minAlpha;
			if(i == 0)
				alpha = .7f;
			GameProj1.batch.setColor(1f, 1f, 1f, alpha);
			GameProj1.batch.draw(this.skillOwner.sprite, s.x - this.skillOwner.sprite.getWidth()/2, 
								 s.y - this.skillOwner.sprite.getHeight()/2, this.skillOwner.sprite.getOriginX(), 
								 this.skillOwner.sprite.getOriginY(), this.skillOwner.sprite.getWidth(), 
								 this.skillOwner.sprite.getHeight(), 1, 1, s.angle);
			GameProj1.batch.setColor(1f, 1f, 1f, 1);
		}			
		this.font.setColor(this.textColor);
		float cd = (float)this.cooldown/(long)1000 - (float)(this.curTime - this.lastUsed)/1000f;
		this.str = Float.toString(cd).substring(0, 3);
		this.layout.setText(this.font, this.str);
		this.offsetX = this.layout.width/2;
		if(cd < (float)this.cooldown/(long)1000 && cd > 0)
			this.font.draw(GameProj1.batch, this.str, this.skillOwner.pos.x - this.offsetX, this.skillOwner.pos.y + 70f);
		GameProj1.batch.end();
	}
	
	@Override
	public void skillActive(){
		if(!this.onCd){
			this.setStatsToOwner(this.pastStats.first());
			this.pastStats = new Array<>();
			this.lastUsed = TimeUtils.millis();
		}
	}
	
	public void setStatsToOwner(Stats s){
		this.skillOwner.hp = s.hp;
		this.skillOwner.pos.set(s.x, s.y);
		this.skillOwner.angle = s.angle;
	}
	
	@Override
	public void setCheats(boolean b){
		System.out.println("[TimeLapse]: No Cooldown!");
		this.cooldown = 0;
	}

}
