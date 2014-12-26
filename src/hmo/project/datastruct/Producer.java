package hmo.project.datastruct;

public class Producer {
	
	private static final long serialVersionUID = 349426006838552905L;
	
	final private int x;
	final private int y;
	final private int name;
	
	private int capacity;
	private int costOfOpening;

	public Producer(final int name, final int x, final int y, final int capacity) {
		this.name = name;
		this.x = x;
		this.y = y;
		this.capacity = capacity;
	}
	
	public Producer(final int name, final int x, final int y) {
		this.name = name;
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
	
	public int GetName() {
		return this.name;
	}
	
	public int GetCapacity() {
		return this.capacity;
	}
	
	@Override
	public String toString() {
		return "Producer((" + x + ", " + y + "), " + capacity + ", " + costOfOpening + ")"; 
	}
}
