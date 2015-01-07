package hmo.project.datastruct;

public final class Consumer {
	
	final public int x;
	final public int y;
	
	private int resourcesNeeded;
	
	public Consumer(final int x, final int y, final int resourcesNeeded) {
		this.x = x;
		this.y = y;
		this.resourcesNeeded = resourcesNeeded;
	}
	
	public Consumer(final int x, final int y) {
		this.x = x;
		this.y = y;
	}
	
	public int getResourcesNeeded() {
		return this.resourcesNeeded;
	}
	
	public void SetResourcesNeeded(final int resorucesNeeded) {
		this.resourcesNeeded = resorucesNeeded;
	}
	
	@Override
	public String toString() {
		return "Consumer((" + x + ", " + y + "), " + resourcesNeeded + ")";
	}
}
