package game.entity;

import java.util.List;

import game.Ability;
import game.InputHandler;
import game.entity.mob.Mob;
import game.gfx.Art;
import game.gfx.Bitmap;
import game.level.EntityFilter;
import game.particle.ExplosionParticle;
import game.particle.SparkParticle;
import game.screen.PauseScreen;
import game.screen.UpgradeScreen;

public class Knight extends Entity {

	public boolean canDoubleJump = true;
	public int hurtTime;
	public int score;
	public int attackTime;

	public Knight(float x, float y) {
		super(x, y);
		this.hw = 12;
	}

	public void tick() {
		super.tick();
		this.xa += Ability.movementSpeed.getCurrentValue().floatValue();
		if(this.attackTime > 0) this.attackTime--;
		if(this.hurtTime > 0) this.hurtTime--;
	}

	public void renderShadow(Bitmap screen, double alpha) {
		int xx = (int) (this.x - this.xr);
		screen.draw(Art.sprites[1], xx, 160);
	}

	public void render(Bitmap screen, double alpha) {
		if(this.hurtTime > 0 && this.hurtTime / 4 % 2 == 0) return;

		int anim = (this.attackTime > 0 ? 2 : 1) * 16;
		if(this.attackTime > 0) anim += (int) (this.attackTime / 4.0f / (Ability.attackTime.getCurrentValue().intValue() / 4.0f / 4.0f)) % 4;
		else if(this.xa > 0) anim += (int) ((this.steps + this.xa * 4) / 4) % 16;

		int xx = (int) (this.x - this.xr);
		int yy = (int) (this.y - this.yr);
		screen.draw(Art.sprites[anim], xx, yy);
	}

	public void update(InputHandler input) {
		if(input.jump.clicked && this.canDoubleJump && !this.onGround) {
			this.ya = -(Ability.jumpVelocity.getCurrentValue().floatValue() + this.xa * 0.5f);
			this.canDoubleJump = false;
		}

		if(input.jump.clicked && this.onGround) this.ya = -(Ability.jumpVelocity.getCurrentValue().floatValue() + this.xa * 0.5f);
		if(input.attack.clicked) attack();
		if(input.escape.clicked && !(this.level.gameScreen.game.screen instanceof UpgradeScreen)) this.level.gameScreen.setScreen(new PauseScreen(this.level.gameScreen));
	}

	protected void touch(Entity e) {
		if(e.removed)
			return;

		if(e instanceof Mob) hurt((Mob) e);
		else if(e instanceof ItemEntity) touchItem((ItemEntity) e);
	}

	public void onGround(boolean doubleJump) {
		if(doubleJump && !this.canDoubleJump) {
			for (int i = 0; i < 10; i++) {
				this.level.add(new SparkParticle(this.x + (random.nextFloat() * 2.0f - 1.0f) * (this.xr - 2.0f), this.y + this.yr + 2.0f));
			}

			this.xa *= 0.15;
		}

		super.onGround(doubleJump);
		this.canDoubleJump = true;
	}

	private void touchItem(ItemEntity item) {
		item.collect(this);
		item.die();
	}

	private void hurt(Mob mob) {
		if(mob.die) return;
		if(this.hurtTime > 0) return;
		
		this.hurtTime = 90;
		this.xa *= -0.8;
		this.level.reduceTime(mob.dmg);
	}

	private void attack() {
		if(this.attackTime > 0) return;

		this.attackTime = Ability.attackTime.getCurrentValue();
		List<Entity> entities = this.level.getEntities(this, this.x + this.xr, this.y - this.yr, this.x + this.xr + this.xr, this.y + this.yr, new EntityFilter() {
				public boolean accept(Entity e) {
					return e instanceof Mob;
				}
			});

		for (Entity e : entities) {
			((Mob) e).hurt(this);
			this.level.reduceTime(-1);
		}

		this.level.add(new ExplosionParticle(this.x + 32, this.y + 6));
	}

}