package hmo.project.datastruct;

public class Consumer {
	
	private static final long serialVersionUID = 1669934855238885989L;
	
	final public int x;
	final public int y;
	final private int name;
	
	private int resourcesNeeded;
	
	public Consumer(final int name, final int x, final int y, final int resourcesNeeded) {
		this.name = name;
		this.x = x;
		this.y = y;
		this.resourcesNeeded = resourcesNeeded;
	}
	
	public Consumer(final int name, final int x, final int y) {
		this.name = name;
		this.x = x;
		this.y = y;
	}
	
	public int getResourcesNeeded() {
		return this.resourcesNeeded;
	}
	
	public void SetResourcesNeeded(final int resorucesNeeded) {
		this.resourcesNeeded = resorucesNeeded;
	}
	
	public int GetName() {
		return this.name;
	}
	
	@Override
	public String toString() {
		return "Consumer((" + x + ", " + y + "), " + resourcesNeeded + ")";
	}
}
