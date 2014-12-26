package hmo.project.datastruct;

public class Vehicle {
	
	private Consumer[] destinations;
	private int numberOfDestinations;
	private final Producer origin;
	private int capacity;
	
	public Vehicle(final Producer origin) {
		this.destinations = new Consumer[11];
		this.numberOfDestinations = 0;
		this.origin = origin;
	}
	
	public void AddDestination(Consumer destination) {
		this.destinations[numberOfDestinations] = destination;
		this.numberOfDestinations++;
	}
	
	public void SetCapacity(final int capacity) {
		this.capacity = capacity;
	}
	
	public double GetCost() {
		if (numberOfDestinations == 0)
			return 0;
		else {
			// TODO
			return 0.4;
		}
	}
	
	public Producer GetOrigin() {
		return this.origin;
	}
	
	public int GetNumberOfDestinations() {
		return this.numberOfDestinations;
	}
	
	public Consumer[] GetDestinations() {
		return this.destinations;
	}
	
	public String toString() {
		return "DeliveryVehicle(" + origin + ", " + numberOfDestinations + ")";
	}
}
