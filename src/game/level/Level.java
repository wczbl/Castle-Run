package game.level;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import game.Ability;
import game.GameComponent;
import game.entity.Entity;
import game.entity.Knight;
import game.entity.Reaper;
import game.gfx.Art;
import game.gfx.Bitmap;
import game.particle.Particle;
import game.screen.GameScreen;

public class Level {

	public static final Random random = new Random();
	public List<Entity> entities = new ArrayList<Entity>();
	public List<Particle> particles = new ArrayList<Particle>();
	public int distanceTime = 10;
	public GameScreen gameScreen;
	public int tickTime;
	public int xBlock;
	
	public Level(GameScreen gameScreen) { this.gameScreen = gameScreen; }
	
	public void add(Entity e) {
		this.entities.add(e);
		e.init(this);
	}
	
	public void add(Particle p) {
		this.particles.add(p);
		p.init(this);
	}
	
	public void tick(int xScroll) {
		if((xScroll + GameComponent.WIDTH) / 32 != this.xBlock) {
			this.xBlock = (xScroll + GameComponent.WIDTH) / 32;
			updateLevel();
		}
		
		for(int i = 0; i < this.entities.size(); i++) {
			Entity e = this.entities.get(i);
			
			if(!e.removed) {
				e.tick();
				continue;
			}
			
			this.entities.remove(i--);
		}
		
		for(int i = 0; i < this.particles.size(); i++) {
			Particle p = this.particles.get(i);
			
			if(!p.removed) {
				p.tick();
				continue;
			}
			
			this.particles.remove(i--);
		}
		
		this.tickTime++;
		if(this.tickTime % 60 == 0) reduceTime(1);
	}
	
	private void renderLoop(Bitmap screen, int xScroll, int yPos, Bitmap img, int w) {
		int xPos = -xScroll;
		while(xPos > 0) {
			xPos -= w;
			screen.draw(img, xPos, yPos);
		}
		
		for(xPos = -xScroll; xPos < w; xPos += w) {
			screen.draw(img, xPos, yPos);
		}
	}
	
	private void renderBackground(Bitmap screen, int xScroll, Bitmap img) {
		renderLoop(screen, xScroll, 0, img, GameComponent.WIDTH);
	}
	
	public void render(Bitmap screen, double alpha, int xScroll) {
		renderBackground(screen, xScroll / 10, Art.bg2);
		renderBackground(screen, xScroll / 3, Art.bg1);
		renderBackground(screen, xScroll, Art.bg0);
		
		screen.setOffs(-xScroll + 106, 0);
		for(Entity e : this.entities) {
			e.renderShadow(screen, alpha);
		}
		
		for(Entity e : this.entities) {
			e.render(screen, alpha);
		}
		
		for(Particle p : this.particles) {
			p.render(screen, alpha);
		}
		
		screen.setOffs(0, 0);
	}
	
	private void updateLevel() {
		Object entity = null;
		Collections.shuffle(LevelGen.generated);
		for (LevelGen<? extends Object> element : LevelGen.generated) {
			int xBlockLast = element.getLastXBlock();
			int startBlock = element.getStartBlock();
			
			if (this.xBlock < startBlock) continue;
			
			int minInterval = element.getMinInterval();
			int maxInterval = element.getMaxInterval();
			
			int xDef = this.xBlock - xBlockLast;
			if (xDef > minInterval && random.nextFloat() < (float)minInterval / (float)maxInterval * 0.3f) {
				entity = element.createInstance(this.xBlock);
				break;
			}
			
			if (xDef <= maxInterval) continue;
			entity = element.createInstance(this.xBlock);
			break;
		}
		
		if (entity != null) this.add((Entity)entity);
	}
	
	public List<Entity> getEntities(Entity entity, float x0, float y0, float x1, float y1) {
		return getEntities(entity, x0, y0, x1, y1, null);
	}
	
	public List<Entity> getEntities(Entity entity, float x0, float y0, float x1, float y1, EntityFilter filter) {
		List<Entity> result = EntityListCache.getCache();
		for(Entity e : this.entities) {
			if(e == entity || e.removed || !e.intersects(x0, y0, x1, y1) || filter != null && !filter.accept(e)) continue;
			result.add(e);
		}
		
		return result;
	}
	
	public void reduceTime(int dmg) {
		this.distanceTime = Math.max(0, Math.min(10, this.distanceTime - dmg));
		if(this.distanceTime <= 0) this.gameScreen.gameOver();
	}
	
	public void resetLevel() {
		for(int i = 0; i < this.entities.size(); i++) {
			Entity e = this.entities.get(i);
			if(e instanceof Reaper || e instanceof Knight) continue;
			this.entities.remove(i--);
		}
		
		resetTime();
		Ability.resetAll();
		LevelGen.resetAll();
		this.xBlock = 0;
	}
	
	public void resetTime() {
		this.distanceTime = 10;
		this.tickTime = 0;
	}
	
}