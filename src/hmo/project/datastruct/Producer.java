package hmo.project.datastruct;

public final class Producer {	
	final public int x;
	final public int y;
	
	private int capacity;
	private int costOfOpening;

	public Producer(final int x, final int y, final int capacity) {
		this.x = x;
		this.y = y;
		this.capacity = capacity;
	}
	
	public Producer(final int x, final int y) {
		this.x = x;
		this.y = y;
	}
	
	public void SetCapacity(final int capacity) {
		this.capacity = capacity;
	}
	
	public void SetCostOfOpening(final int cost) {
		this.costOfOpening = cost;
	}
	
	public int GetCostOfOpening() {
		return this.costOfOpening;
	}
	
	public int GetCapacity() {
		return this.capacity;
	}
	
	@Override
	public String toString() {
		return "Producer((" + x + ", " + y + "), " + capacity + ", " + costOfOpening + ")"; 
	}
}
