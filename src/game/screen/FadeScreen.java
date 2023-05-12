package game.screen;

import game.InputHandler;
import game.gfx.Bitmap;

public class FadeScreen extends Screen {

	private Screen target;
	private int tickTime;
	private float maxDuration = 60.0f;

	public FadeScreen(Screen target) { this.target = target; }
	
	public void tick(InputHandler input) {
		this.tickTime++;
		if(this.tickTime > this.maxDuration) setScreen(this.target);
	}

	public void render(Bitmap screen, double alpha) {
		this.target.render(screen, alpha);
		
		for(int i = 0; i < screen.pixels.length; i++) {
			int c = screen.pixels[i];
			int a = (c >> 24) & 0xFF;
			int r = (c >> 16) & 0xFF;
			int g = (c >> 8) & 0xFF;
			int b = (c >> 0) & 0xFF;
		
			float duration = (float)this.tickTime / this.maxDuration;
			r = (int)(r * duration);
			g = (int)(g * duration);
			b = (int)(b * duration);	
			screen.pixels[i] = a << 24 | r << 16 | g << 8 | b << 0;
		}
	}

}