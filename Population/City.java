/**
 *	City data - the city name, state name, location designation,
 *				and population est. 2017
 *
 *	@author	Qi Huang
 *	@since	Dec 7, 2023
 */
public class City implements Comparable<City> {
	
	// fields
	private String name;
	private String designation;
	private String state;
	private int population;
	
	// constructor
	
	/**	Compare two cities populations
	 *	@param other		the other City to compare
	 *	@return				the following value:
	 *		If populations are different, then returns (this.population - other.population)
	 *		else if states are different, then returns (this.state - other.state)
	 *		else returns (this.name - other.name)
	 */
	public int compareTo(City other) { // implemented from Comparable<City>
		if (this.population != other.population) {
			return (this.population - other.population);
		} else if (this.state != other.state) {
			return (this.state.compareTo(other.state));
		} else {
			return (this.name.compareTo(other.name));
		}
	}
	/**	Equal city name and state name
	 *	@param other		the other City to compare
	 *	@return				true if city name and state name equal; false otherwise
	 */
	@Override 
	public boolean equals(City other) {
		if (this.name.equals(other.name))
			return true;
		return false;
	}
	/**	Accessor methods */
	
	/**	toString */
	@Override
	public String toString() {
		return String.format("%-22s %-22s %-12s %,12d", state, name, designation,
						population);
	}
}
