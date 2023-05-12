package game;

import java.awt.Canvas;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

public class InputHandler implements KeyListener {

	private List<Key> keys = new ArrayList<Key>();
	public Key jump = new Key();
	public Key attack = new Key();
	public Key up = new Key();
	public Key down = new Key();
	public Key escape = new Key();
	
	public InputHandler(Canvas canvas) { canvas.addKeyListener(this); }
	
	public void tick() {
		for(Key key : this.keys) {
			key.tick();
		}
	}
	
	public void keyTyped(KeyEvent e) {}
	public void keyPressed(KeyEvent e) { toggle(e, true); }
	public void keyReleased(KeyEvent e) { toggle(e, false); }
	
	public void toggle(KeyEvent e, boolean pressed) {
		if(e.getKeyCode() == KeyEvent.VK_W || e.getKeyCode() == KeyEvent.VK_NUMPAD8 || e.getKeyCode() == KeyEvent.VK_UP) this.up.toggle(pressed);
		if(e.getKeyCode() == KeyEvent.VK_S || e.getKeyCode() == KeyEvent.VK_NUMPAD2 || e.getKeyCode() == KeyEvent.VK_DOWN) this.down.toggle(pressed);
		if(e.getKeyCode() == KeyEvent.VK_SPACE || e.getKeyCode() == KeyEvent.VK_Z) this.jump.toggle(pressed);
		if(e.getKeyCode() == KeyEvent.VK_X || e.getKeyCode() == KeyEvent.VK_NUMPAD5) this.attack.toggle(pressed);
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE) this.escape.toggle(pressed);
	}
	
	public class Key {
		private int absorbs;
		private int presses;
		
		public boolean down;
		public boolean clicked;
		
		public Key() { InputHandler.this.keys.add(this); }
		
		public void tick() {
			if(this.presses > this.absorbs) {
				this.absorbs++;
				this.clicked = true;
			} else this.clicked = false;
		}
		
		public void toggle(boolean pressed) {
			if(pressed) this.presses++;
			if(pressed != this.down) this.down = pressed;
		}
	}

}
