import info.gridworld.actor.*;
import info.gridworld.grid.*;

import java.awt.Color;

// Tomorrow:
// Add comments throughout the code
// Create a separate test file 

public class Jumper extends Bug {
	
	private int distanceBound;
	private int distance;
	
	public Jumper() {
		setColor(Color.BLUE);
		distanceBound = 10;
		distance = 0;
	}
	
	public Jumper(int distanceBound) {
		setColor(Color.BLUE);
		this.distanceBound = distanceBound;
		distance = 0;
	}
	
	/** 
	 *  When it can jump, it jumps, when it can't, it keeps turning until a
	 *  possible location is found. 
	 *  If all locations two cells away (in the compass directions) are not possible, 
	 *  it moves one cell forward if it can. 
	 *  If it cannot, then it turns until it finds a possible cell.
	 *  If all cells one away are also not possible, then the Jumper does nothing.
	 */
	public void act() {
		int startDirection = getDirection();
		startDirection = roundTo45(startDirection);
		setDirection(startDirection);
		
		if (distance < distanceBound) {
			if (!canJump()) {
				turn();				
			}
			while (getDirection() != startDirection && !canJump()) {
				turn();
			}
			
			if (canJump() && getDirection() != startDirection) {
				// if the direction is different, distance should reset to 0
				distance = 0;
				jump();
				distance += 2;
			} else if (canJump() && getDirection() == startDirection) {
				jump();
				distance += 2;
			} else {
				// here, the direction is the same and all cells two away are 
				// occupied
				if (!canMove()) {
					turn();
				}
				while (getDirection() != startDirection && !canMove()) {
					turn();
				}
				if (canMove() && getDirection() != startDirection) {
					// if the direction is changed, the distance should reset to 0
					distance = 0;
					move();
					distance++;
				} else if (canJump() && getDirection() == startDirection) {
					jump();
					distance++;
				}
			}
		} else {
			turn();
			turn();
			distance = 0;
		}
	}
	
	/** 
	 *  Moves the jumper two cells forward (in the direction it is facing)
	 */
	public void move() {
		Grid<Actor> gr = getGrid();
        if (gr == null)
            return;
        Location loc = getLocation();
        Location next = loc.getAdjacentLocation(getDirection());
        if (gr.isValid(next))
            moveTo(next);
        else
            removeSelfFromGrid();
        int lifespan = (int)(7 * Math.random() + 4);
        Blossom blossom = new Blossom(lifespan);
        blossom.putSelfInGrid(gr, loc);
	}
	
	/** 
	 *  Moves the jumper two cells forward (in the direction it is facing)
	 */
	public void jump() {
		Grid<Actor> grid = getGrid();
		
		Location location = getLocation();
		Location target = location.getAdjacentLocation(getDirection());
		target = target.getAdjacentLocation(getDirection());
		
		if (grid.isValid(target)) 
			moveTo(target);
		else 
			removeSelfFromGrid();
		
		int lifespan = (int)(7 * Math.random() + 4);
		Blossom blossom = new Blossom(lifespan);
		blossom.putSelfInGrid(grid, location);
	}
	
	/** 
	 *  Tests if there are any objects two cells away in the direction
	 *  the Jumper is facing.
	 * 	@return true if this Jumper can jump
	 */
	public boolean canJump() {
		Grid<Actor> grid = getGrid();
		if (grid == null)
			return false;
		
		Location location = getLocation();
		Location newLocation = location.getAdjacentLocation(getDirection());
		newLocation = newLocation.getAdjacentLocation(getDirection());
		
		if (!grid.isValid(newLocation)) return false;
		
		Actor actorAtNew = grid.get(newLocation);
		
		return (actorAtNew == null);
	}
	
	/** 
	 *  Overrides the canMove() method in Bug so the Jumper can't move onto a 
	 *  Flower.
	 * 	@return true if this Jumper can move
	 */
	public boolean canMove() {
		Grid<Actor> grid = getGrid();
		if (grid == null)
			return false;
		
		Location location = getLocation();
		Location newLocation = location.getAdjacentLocation(getDirection());
		
		if (!grid.isValid(newLocation)) return false;
		
		Actor actor = grid.get(newLocation);
		return (actor == null);
	}
	
	/** Rounds the direction to the nearest multiple of 45, and all numbers 
	 *  rounded to 360 goes to 0. 
	 *  @param direction Direction an actor is facing
	 * 	@return The rounded direction
	 */
	private int roundTo45(int direction) {
		direction += Location.HALF_RIGHT / 2;
		direction = ( direction / Location.HALF_RIGHT ) * Location.HALF_RIGHT;
		direction = direction % Location.FULL_CIRCLE;
		return direction;
	}
}
