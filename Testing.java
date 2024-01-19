public class Testing {
	
	public static void main(String[] args) {
		ExtendedObject obj = new ExtendedString("Finals Week!");
		if (obj.compareTo("Finals Week!") == 0) {
			System.out.println("Terrific!");
		}
	}
}

class ExtendedObject extends Object {
	/*public int compareTo(String str) {
		return 1;
	}*/
}

class ExtendedString extends ExtendedObject {
	private String str;
	
	public ExtendedString(String str) {
		this.str = new String(str);
	}
	
	public int compareTo(String str) {
		return this.str.compareTo(str);
	}
}
