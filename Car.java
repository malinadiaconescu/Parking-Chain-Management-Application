import java.io.Serializable;
/**
 * defines a car
 * @author Malina Diaconescu
 */
public class Car implements Serializable{
	/**
	 * the type of the care. it can be small, medium or big
	 */
	private Types Type;
	/**
	 * it is the license plate of the car. It takes the form of the ones in Romania
	 */
	private String Number;
	/**
	 * Constructor for a Car object
	 * @param type the type of the car
	 * @param number the licence plate of the car
	 */
	public Car(Types type, String number) {
		super();
		Type = type;
		Number = number;
	}
	@Override
	/**
	 * toString override 
	 */
	public String toString() {
		return "Car [Type=" + Type + ", Number=" + Number+ "]";
	}
	/**
	 * getter for type
	 * @return type
	 */
	public Types getType() {
		return Type;
	}
	/**
	 * setter for type
	 * @param type - the type you want to set for the car
	 */
	public void setType(Types type) {
		Type = type;
	}
	/**
	 * getter for the license plate
	 * @return license plate
	 */
	public String getNumber() {
		return Number;
	}
	/**
	 * setter for license plate
	 * @param number - the license plate you want to set for the car
	 */
	public void setNumber(String number) {
		Number = number;
	}
}
