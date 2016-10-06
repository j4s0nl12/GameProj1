package Skills;

import java.util.Random;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.GameProj1;

import GameObjects.BaseObject;
import Projectiles.Flame;
import Utility.ProjectileManager;

public class Flamethrower extends BaseSkill {

	public ProjectileManager pm;
	private Vector2 offset;
	
	public int fuelMax;
	
	public BitmapFont font;
	public String str;
	public float x;
	public float y;
	public float offsetX;
	public Color textColor;
	
	public boolean noClip;
	
	public Flamethrower(BaseObject owner){
		super(owner);
		this.pm = new ProjectileManager();
		this.offset = new Vector2(40f, 40f);
		this.fuelMax = 600;
		this.ammo = this.fuelMax;
		this.noClip = false;
		
		this.x = GameProj1.GAME_WORLD_WIDTH * 9 / 10;
		this.y = GameProj1.GAME_WORLD_HEIGHT / 10;
		this.textColor = new Color(1f,1f,1f,1);
		this.str = "Fuel: " + this.ammo + "/" + this.fuelMax;
		this.font = new BitmapFont();
		GlyphLayout layout = new GlyphLayout();
		layout.setText(this.font, this.str);
		this.offsetX = layout.width/2;
	}
	
	@Override
	public void update(float delta){
		this.pm.update(delta);
		this.render();
		//this.offset.rotate(this.skillOwner.angle);//Lol
		this.offset.setAngle(this.skillOwner.angle + 90f);
	}
	
	public void render(){
		GameProj1.batch.begin();
		this.font.setColor(this.textColor);
		this.str = "Fuel: " + this.ammo + "/" + this.fuelMax;
		this.font.draw(GameProj1.batch, this.str, this.x - this.offsetX, this.y);
		GameProj1.batch.end();
	}
	
	@Override
	public void skillActive(){
		if(this.ammo > 0 || this.noClip)
			this.fire();
	}
	
	public void fire(){
		Random rand = new Random();
		float max = 50f;
		float min = max * -1;
		float x = rand.nextFloat() * (max - min) + min;
		float y = rand.nextFloat() * (max - min) + min;
		Vector2 tmp = new Vector2(x,y);
		this.pm.add(new Flame(this.skillOwner, this.skillOwner.pos.cpy().add(this.offset), 
					this.getMousePos().add(tmp)));
		if(!this.noClip)
			this.ammo--;
	}
	
	@Override
	public void setCheats(boolean b){
		System.out.println("[Flamethrower]: No Clip On!");
		this.noClip = true;
	}
}
