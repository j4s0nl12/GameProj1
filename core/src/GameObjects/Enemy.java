package GameObjects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Enemy extends BaseObject{
	
	public String spritefile = "Zombie.png";

	public Enemy(float x, float y, float angle) {
		super(x, y, angle);
		this.maxHp = 1000f;
		this.hp = 1000f;
		this.sprite = new Sprite(new Texture(spritefile));
	}
	
	public void update(float delta){
		super.update(delta);
	}
}
