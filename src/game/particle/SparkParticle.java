package game.particle;

public class SparkParticle extends Particle {
	
	public SparkParticle(float x, float y) {
		super(x, y);
		
	    do {
			this.xa = random.nextGaussian() * 2.0 - 1.0;
			this.ya = random.nextGaussian() - 1.0;
	    } while (Math.sqrt(this.xa * this.xa + this.ya * this.ya) > 1.0);
	    
	    double dd = Math.sqrt(this.xa * this.xa + this.ya * this.ya);
	    this.xa /= dd;
	    this.ya /= dd;
	    
	    double speed = random.nextDouble() * 1.5;
	    double pow = speed * speed * speed;
	    
	    this.xa *= pow * 1;
	    this.ya *= speed * 0.1;
	    this.maxTime = random.nextInt(15) + 3;
	    this.sprite = 0;
	}
		
	public void tick() {
		super.tick();
		
		this.x += this.xa;
		this.y += this.ya;
		this.xa *= 0.92f;
		this.ya *= 0.92f;
		this.rot = this.time / (float)this.maxTime * (float)Math.PI * 2.0f;
	}
	
}