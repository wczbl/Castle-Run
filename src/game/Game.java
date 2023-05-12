package game;

import game.gfx.Bitmap;
import game.screen.FadeScreen;
import game.screen.GameScreen;
import game.screen.Screen;
import game.screen.TitleScreen;

public class Game {

	public Screen screen;
	
	public Game() { setScreen(new TitleScreen(new FadeScreen(new GameScreen()))); }
	
	public void setScreen(Screen screen) {
		this.screen = screen;
		this.screen.init(this);
	}
	
	public void tick(InputHandler input) { this.screen.tick(input); }
	public void render(Bitmap screen, double alpha) { this.screen.render(screen, alpha); }
	
}