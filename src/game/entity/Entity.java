package game.entity;

import java.util.List;
import java.util.Random;

import game.GameComponent;
import game.gfx.Art;
import game.gfx.Bitmap;
import game.level.Level;

public abstract class Entity {

	public static final Random random = new Random();
	public float xo;
	public float yo;
	public float x;
	public float y;
	public float xa;
	public float ya;
	public float xr = 16;
	public float yr = 16;
	public float hw = 16;
	public float hh = 16;
	public Level level;
	public int sprite;
	public int steps;
	public boolean onGround;
	public boolean removed;
	
	public Entity(float x, float y) {
		this.xo = this.x = x;
		this.yo = this.y = y;
	}
	
	public void init(Level level) { this.level = level; }
	
	public void tick() {
		this.xo = this.x;
		this.yo = this.y;
		move();
		this.xa *= 0.92;
		this.ya *= 0.92;
		this.ya += 0.37;
	}
	
	public void renderShadow(Bitmap screen, double alpha) {}
	
	public void render(Bitmap screen, double alpha) {
		int xx = (int)(this.x - this.xr);
		int yy = (int)(this.y - this.yr);
		screen.draw(Art.sprites[this.sprite], xx, yy);
	}
	
	public void move() {
		this.onGround = false;
		
		int xSteps = (int)(Math.abs(this.xa * 100) + 1);
		for(int i = xSteps; i >= 0; i--) {
			if(isFree(this.xa * i / xSteps, 0)) {
				this.x += this.xa * i / xSteps;
				this.steps++;
				break;
			}
			
			this.xa = 0;
		}
		
		int ySteps = (int)(Math.abs(this.ya * 100) + 1);
		for(int i = ySteps; i >= 0; i--) {
			if(isFree(0, this.ya * i / ySteps)) {
				this.y += this.ya * i / ySteps;
				break;
			}
			
			onGround(Math.abs(this.ya) > 0.37);
			this.ya = 0;
		}
		
	}
	
	public boolean isFree(float xxa, float yya) {
		float x0 = this.x + xxa - this.hw;
		float y0 = this.y + yya - this.hh;
		float x1 = this.x + xxa + this.hw;
		float y1 = this.y + yya + this.hh;
		
		List<Entity> entities = this.level.getEntities(this, x0, y0, x1, y1);
		for(Entity e : entities) {
			e.touchedBy(this);
			touch(e);
		}
		
		return !(y1 >= GameComponent.HEIGHT - 48);
	}
	
	public void setPos(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	public boolean intersects(float x0, float y0, float x1, float y1) {
		return !(x0 > this.x + this.hw || y0 > this.y + this.hh || x1 < this.x - this.hw) && !(y1 < this.y - this.hh);
	}
	
	protected void touch(Entity e) {}
	protected void touchedBy(Entity e) {}
	
	public void onGround(boolean doubleJump) { this.onGround = true; }
	public void collide(Entity e, float xxa, float yya) {}
	public void die() { this.removed = true; }
	
}