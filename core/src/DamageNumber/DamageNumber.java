package DamageNumber;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.mygdx.game.GameProj1;

public class DamageNumber {
	
	public BitmapFont font;
	
	public String str;
	public float x;
	public float y;
	public float alpha;
	public float offsetX;
	public float r;
	public float g;
	public float b;
	public float incr;
	
	public boolean isDead;
	
	public DamageNumber(float x, float y, String s){
		this.x = x;
		this.y = y;
		this.str = s;
		
		this.font = new BitmapFont();
		GlyphLayout layout = new GlyphLayout();
		layout.setText(this.font, this.str);
		this.offsetX = layout.width/2;
		this.r = 1f;
		this.g = 0f;
		this.b = 0f;
		this.incr = .05f;
		this.alpha = 1f;
	}
	
	public void update(float delta){
		this.y += 2f;
		this.g += incr;
		this.b += incr;
		if(this.g >= 1f || this.g <= 0f)
			incr *= -1;
		this.alpha -= .01f;
		this.render();
		
		if(this.alpha < .2f)
			this.isDead = true;
	}
	
	public void render(){
		GameProj1.batch.begin();
		this.font.setColor(this.r, this.g, this.b, this.alpha);
		this.font.draw(GameProj1.batch, this.str, this.x - this.offsetX, this.y);
		GameProj1.batch.end();
	}
}
