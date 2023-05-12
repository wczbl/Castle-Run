package game.gfx;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Art {
	
	public static final Bitmap[] sprites = loadAndCut("/sprites.png", 32, 32);
	public static final Bitmap[] pickups = loadAndCut("/pickups.png", 10, 10);
	public static final Bitmap[] hourglass = loadAndCut("/hourglass.png", 16, 24);
	public static final Bitmap[] death = loadAndCut("/death.png", 86, 64);
	public static final Bitmap[] slime = loadAndCut("/slime.png", 24, 24);
	public static final Bitmap[] particles = loadAndCut("/particles.png", 8, 8);
	public static final Bitmap[] titleFont = loadAndCut("/title_font.png", 8, 8);
	public static final Bitmap[] gameOverFont = loadAndCut("/gameover_font.png", 8, 8);
	public static final Bitmap[] font = loadAndCut("/font.png", 8, 8);
	public static final Bitmap vendor = load("/vendor.png");
	public static final Bitmap bought_texture = load("/bought_texture.png");
	public static final Bitmap selectCursor = load("/selection_cursor.png");
	public static final Bitmap bg0 = load("/bg0.png");
	public static final Bitmap bg1 = load("/bg1.png");
	public static final Bitmap bg2 = load("/bg2.png");
	public static final Bitmap title = load("/title.png");
	public static final Bitmap gameOver = load("/gameover.png");
	public static final Bitmap icon = load("/hourglass_icon.png");
	
	public static Bitmap load(String path) {
		BufferedImage image;
		try {
			image = ImageIO.read(Art.class.getResourceAsStream(path));
		} catch (IOException e) {
			throw new RuntimeException("Failed to load " + path);
		}
		
		BufferedImage result = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);
		Graphics g = result.getGraphics();
		g.drawImage(image, 0, 0, null);
		g.dispose();
		return new Bitmap(result);
	}

	public static Bitmap[] loadAndCut(String path, int w, int h) {
		BufferedImage sheet;
		try {
			sheet = ImageIO.read(Art.class.getResourceAsStream(path));
		} catch (IOException e) {
			throw new RuntimeException("Failed to load " + path);
		}
		
		int sw = sheet.getWidth() / w;
		int sh = sheet.getHeight() / h;
		Bitmap[] result = new Bitmap[sw * sh];
		for(int y = 0; y < sh; y++) {
			for(int x = 0; x < sw; x++) {
				BufferedImage image = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
				Graphics g = image.getGraphics();
				g.drawImage(sheet, -x * w, -y * h, null);
				g.dispose();
				result[x + y * sw] = new Bitmap(image);
			}
		}
		
		return result;
	}
	
}