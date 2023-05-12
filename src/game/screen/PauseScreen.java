package game.screen;

import game.GameComponent;
import game.InputHandler;
import game.gfx.Bitmap;
import game.gfx.Font;

public class PauseScreen extends Screen {

	private GameScreen gameScreen;
	private int tickTime;
	
	public PauseScreen(GameScreen gameScreen) { 
		this.gameScreen = gameScreen; 
	}

	public void tick(InputHandler input) {
		this.tickTime++;
		if(input.escape.clicked || input.attack.clicked) setScreen(this.gameScreen);
	}

	public void render(Bitmap screen, double alpha) {
		this.gameScreen.render(screen, alpha);
		for(int i = 0; i < screen.pixels.length; i++) {
			int c = screen.pixels[i];
			int a = (c >> 24) & 0xFF;
			int r = (c >> 16) & 0xFF;
			int g = (c >> 8) & 0xFF;
			int b = (c >> 0) & 0xFF;
			float transparency = 0.6f;
			r = (int)(r * transparency);
			g = (int)(g * transparency);
			b = (int)(b * transparency);
			screen.pixels[i] = a << 24 | r << 16 | g << 8 | b << 0;
		}
		
		String text = "PAUSED";
		Font.draw(text, screen, (GameComponent.WIDTH - text.length() * 7) / 2, GameComponent.HEIGHT / 4);

		if(this.tickTime / 30 % 2 == 0) return;
		text = "Press 'Z', 'X', or 'ESC' to unpause";
		Font.draw(text, screen, (GameComponent.WIDTH - text.length() * 7) / 2, GameComponent.HEIGHT / 2 + 50);
	}

}