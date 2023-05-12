package game.screen;

import game.GameComponent;
import game.InputHandler;
import game.gfx.Art;
import game.gfx.Bitmap;
import game.gfx.Font;

public class TitleScreen extends Screen {

	private Screen target;
	private int tickTime;
	
	public TitleScreen(Screen target) { this.target = target; }
	
	public void tick(InputHandler input) {
		this.tickTime++;
		if(input.attack.clicked) setScreen(this.target);
	}

	public void render(Bitmap screen, double alpha) {
		screen.draw(Art.title, 0, 0);
		String text = "Press 'X' to start";
		if(this.tickTime / 20 % 2 == 0) return;
		Font.draw(text, screen, (GameComponent.WIDTH - text.length() * 7) / 2, GameComponent.HEIGHT - 24);
	}
	

}