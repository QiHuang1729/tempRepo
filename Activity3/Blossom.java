import info.gridworld.actor.Flower;
import java.awt.Color;

public class Blossom extends Flower {
	
	private int lifespan;
	private int steps;
	
	/** sets the color as green and the lifespan as 10 (the default) */
	public Blossom() {
		super(Color.GREEN);
		lifespan = 10;
		steps = 0;
	}
	
	/** sets the color as green and the lifespan as "lifespan" */
	public Blossom(int lifespan) {
		super(Color.GREEN);
		this.lifespan = lifespan;
		steps = 0;
	}
	
	/**
	 * Blossom acts the same as a Flower except it removes itself on 
	 * the turn when the number of steps matches the lifespan
	 */
	public void act() {
		if (steps == lifespan) {
			removeSelfFromGrid();
		}
	}
}
