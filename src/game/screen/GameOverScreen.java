package game.screen;

import game.GameComponent;
import game.InputHandler;
import game.gfx.Art;
import game.gfx.Bitmap;
import game.gfx.Font;

public class GameOverScreen extends Screen {

	private GameScreen gameScreen;
	private int tickTime;
	
	public GameOverScreen(GameScreen gameScreen) { this.gameScreen = gameScreen; }
	
	public void tick(InputHandler input) {		
		if(this.tickTime++ > 240 && input.attack.clicked) {
			this.gameScreen.resetLevel();
			setScreen(new FadeScreen(this.gameScreen));
		}
	}

	public void render(Bitmap screen, double alpha) {		
		if(this.tickTime <= 180) {
			this.gameScreen.render(screen, alpha);
			
			for(int i = 0; i < screen.pixels.length; i++) {
				int c = screen.pixels[i];
				int a = (c >> 24) & 0xFF;
				int r = (c >> 16) & 0xFF;
				int g = (c >> 8) & 0xFF;
				int b = (c >> 0) & 0xFF;
				
				float duration = (180 - this.tickTime) / 180.0f;
				r = (int)(r * duration);
				g = (int)(g * duration);
				b = (int)(b * duration);
				
				screen.pixels[i] = a << 24 | r << 16 | g << 8 | b << 0;
			}
		} 
		
		if(this.tickTime > 180) {
			screen.draw(Art.gameOver, 0, 0);
			if(this.tickTime > 360) {
				String text = "Press 'X' to restart";
				Font.drawGameOver(text, screen, (GameComponent.WIDTH - text.length() * 7) / 2, GameComponent.HEIGHT - 16 - (int)(Math.abs(Math.sin(this.tickTime * 0.1) * 10)));
			}
		}
	}

}