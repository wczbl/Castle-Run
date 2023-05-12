package game.screen;

import game.Ability;
import game.GameComponent;
import game.InputHandler;
import game.entity.Knight;
import game.gfx.Art;
import game.gfx.Bitmap;
import game.gfx.Font;

public class UpgradeScreen extends Screen {
	
	private GameScreen gameScreen;
	private Knight knight;
	private int tickTime;
	private int index;
	
	public UpgradeScreen(GameScreen gameScreen) { 
		this.gameScreen = gameScreen; 
		this.knight = gameScreen.knight;
	}

	public void tick(InputHandler input) {
		this.tickTime++;
		
		if(input.up.clicked) this.index--;
		if(input.down.clicked) this.index++;
		if(this.index < 0) this.index = 0;
		if(this.index >= Ability.abilities.size()) this.index = Ability.abilities.size() - 1;
		
		if(input.attack.clicked) Ability.abilities.get(this.index).upgrade(this.knight);
		if(input.escape.clicked) setScreen(this.gameScreen);
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
		
		screen.draw(Art.vendor, (320 - Art.vendor.w) / 2, (240 - Art.vendor.h) / 2);
		for(int i = 0; i < Ability.abilities.size(); i++) {
			Ability<?> ability = Ability.abilities.get(i);
			for(int j = 0; j < ability.getLevel(); j++) {
				screen.draw(Art.bought_texture, (GameComponent.WIDTH - 123) + j * 18, GameComponent.HEIGHT - 162 + i * 22);
			}
		}
		
		screen.draw(Art.selectCursor, GameComponent.WIDTH - 130 + (int)(Math.abs(Math.sin(this.tickTime * 0.1) * 5) + Ability.abilities.get(this.index).getLevel() * 16), GameComponent.HEIGHT - 154 + this.index * 22);
		String text = Ability.abilities.get(this.index).getNextCost() + "";
		Font.draw(text, screen, GameComponent.WIDTH - 100, GameComponent.HEIGHT - 24);
		text = this.knight.score + "";
		Font.draw(text, screen, GameComponent.WIDTH - 220, GameComponent.HEIGHT - 24);
	}

}