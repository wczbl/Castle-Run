package game.level;

import game.entity.Entity;

public interface EntityFilter {

	boolean accept(Entity e);
	
}