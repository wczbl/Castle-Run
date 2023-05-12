package game.screen;

import game.GameComponent;
import game.InputHandler;
import game.entity.Knight;
import game.entity.Reaper;
import game.gfx.Art;
import game.gfx.Bitmap;
import game.gfx.Font;
import game.level.Level;

public class GameScreen extends Screen {
	
	private Level level;
	public Knight knight;
	private Reaper reaper;
	private int xScroll;
	private float xScrollA;
	
	public GameScreen() {
		this.level = new Level(this);
		this.knight = new Knight(0, GameComponent.HEIGHT - 70);
		this.reaper = new Reaper(this.knight.x - 1280, GameComponent.HEIGHT - 100, this.knight);
		this.level.add(this.knight);
		this.level.add(this.reaper);
	}
	
	public void tick(InputHandler input) {
		float scrollSpeed = 1.23f;
        int targetXScroll = (int)(this.knight.x + 53.0f + this.knight.hurtTime);
        this.xScrollA += (float)(targetXScroll - this.xScroll) / 320.0f * 3.0f * scrollSpeed;
        this.xScroll = (int)(this.xScroll + this.xScrollA);
        this.xScrollA = (float)(this.xScrollA * 0.7);
        this.level.tick(targetXScroll);
		this.knight.update(input);
	}

	public void render(Bitmap screen, double alpha) {
		this.level.render(screen, alpha, this.xScroll);

		screen.draw(Art.pickups[6], 10, 10);
		String text = "" + (int)this.knight.x;
		Font.draw(text, screen, 24, 10);
		
		screen.draw(Art.pickups[7], 100, 10);
		text = "" + this.knight.score;
		Font.draw(text, screen, 114, 10);
		
		screen.draw(Art.icon, 190, 5);
		text = "" + this.level.distanceTime;
		Font.draw(text, screen, 204, 10);
	}

	public void resetLevel() {
		this.level.resetLevel();
		this.knight.score /= 2;
		this.knight.setPos(0, GameComponent.HEIGHT - 70);
		this.reaper.setPos(this.knight.x - GameComponent.WIDTH / 2, 0);
	}
	
	public void gameOver() { setScreen(new GameOverScreen(this)); }
	
}