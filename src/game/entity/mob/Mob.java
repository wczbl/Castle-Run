package game.entity.mob;

import game.Ability;
import game.entity.Entity;
import game.entity.Knight;
import game.gfx.Art;
import game.gfx.Bitmap;

public class Mob extends Entity {

	public int time = 1800;
	public boolean die;
	public int dieTime;
	public int dmg = 1;
	public int health = 1;
	
	public Mob(float x, float y) {
		super(x, y);
		this.hw = 12;
	}

	public void tick() {
		super.tick();
		
		if(this.time-- <= 0) this.removed = true;
		if(this.die && this.dieTime-- <= 0) this.removed = true;
	}
	
	public void renderShadow(Bitmap screen, double alpha) {
		int xx = (int)(this.x - this.xr);
		screen.draw(Art.sprites[1], xx, 160);
	}

	public void hurt(Knight knight) {
		this.health -= Ability.attackTime.getLevel() + 1;
		if(this.health <= 0) die();
	}
	
	public void die() { this.die = true; }
	
}