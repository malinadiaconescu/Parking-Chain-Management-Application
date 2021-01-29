import java.io.Serializable;
import java.util.*;
/**
 * defines a parking zone of the parking lot
 * @author Malina Diaconescu
 *
 */
public class ParkingZone implements Serializable  {
	/**
	 * total number of spaces of the parking zone
	 */
	private int NumberOfSpaces;
	/**
	 * type of the cars that the parking zone can hold
	 */
	private Types Type;
	/**
	 * the fee the car has to pay
	 */
	private int Fee;
	/**
	 * for each day, we count how many cars parked in the parking zone through this map
	 */
	private Map<Date,Integer> OccupiedSpaces=new HashMap<Date,Integer>();
	/**
	 * for each day, we remember the money we make through this map
	 */
	private Map<Date,Double> Gain=new HashMap<Date,Double>();
	/**
	 * for each day, we remember the money we make from subscribers through this map
	 */
	private Map<Date,Double> GainSubs=new HashMap<Date,Double>();
	/**
	 * for each day, we remember the money we make from sales through this map
	 */
	private Map<Date,Double> GainSales=new HashMap<Date,Double>();
	/**
	 * getter for NumberOfSpaces
	 * @return number of spaces
	 */
	public int getNumberOfSpaces() {
		return NumberOfSpaces;
	}
	/**
	 * setter for the number of spaces
	 * @param numberOfSpaces
	 */
	public void setNumberOfSpaces(int numberOfSpaces) {
		NumberOfSpaces = numberOfSpaces;
	}
	/**
	 * getter for the type of the cars that parking zone can hold
	 * @return
	 */
	public Types getType() {
		return Type;
	}
	/**
	 * setter for the type of the cars that parking zone can hold
	 * @param type
	 */
	public void setType(Types type) {
		Type = type;
	}
	/**
	 * Construcotr for an Parking Zone object
	 * @param numberOfSpaces the number of cars the parking zone can hold
	 * @param type the type of the cars that the parking zone can hold
	 * @param fee the fee of the parking zone
	 */
	public ParkingZone(int numberOfSpaces, Types type, int fee) {
		super();
		this.NumberOfSpaces = numberOfSpaces;
		this.Type = type;
		this.Fee=fee;
	}
	@Override
	/**
	 * toString override
	 */
	public String toString() {
		return "ParkingZone [NumberOfSpaces=" + NumberOfSpaces + ", Type=" + Type + ", Fee="+Fee+"]";
	}
	/**
	 * This function is called in ParkingLot class when we park a new car. The car is put to the occupied spaces and there are proccessed the money got from it
	 * 
	 * @param date when the car parks in the parking zone
	 * @param Situation in which situation the car is in: it is a normal customer, it parked ten times or it has a subscription
	 */
	public void NewCar(Date date,int Situation) //1 - normal / 2 - parked ten times / 3 - subscription
	{
		double SumToAdd=0;
		if(Situation==1) 
		{
			System.out.println("No Subscription Available or Any Other ");
			SumToAdd=this.Fee;
		}
		else if(Situation==3)
		{
			System.out.println("Subscription Available!");
			SumToAdd=this.Fee*0.8;
			if(GainSubs.containsKey(date))
			{
				double aux=GainSubs.get(date);
				GainSubs.put(date, aux+SumToAdd);
			}
			else GainSubs.put(date, SumToAdd);
		}
		else
		{
			System.out.println("Voucher available! You have to pay half of the actual fee!");
			SumToAdd=this.Fee/2;
			if(GainSales.containsKey(date))
			{
				double aux=GainSales.get(date);
				GainSales.put(date, aux+SumToAdd);
			}
			else GainSales.put(date, SumToAdd);
		}
		boolean ok=false;
		for(Date d:OccupiedSpaces.keySet())
		{
			if(date.equals(d))
			{
				ok=true;
				int aux=OccupiedSpaces.get(date);
				OccupiedSpaces.put(date, aux+1);
				double aux1=Gain.get(date);
				Gain.put(date, aux1+SumToAdd);
			}
		}
		if(ok==false)
		{
			OccupiedSpaces.put(date, 1);
			Gain.put(date, SumToAdd);
		}
		
	}
	/**
	 * The function returns whether there is space for a new car to be parked
	 * @param date the date we want to check for
	 * @return true or false; true for is space; false if there is no space
	 */
	public boolean isSpace(Date date)
	{
		for(Date d:OccupiedSpaces.keySet())
		{
			if(date.equals(d))
			{
				int aux=OccupiedSpaces.get(date);
				if(NumberOfSpaces-aux>0) return true;
				else return false;
			}
		}
		return true;
	}
	/**
	 * It returns the sum of the money made in a specific day
	 * @param date the date for which we want to know how money were made
	 * @return
	 */
	public double returnGain(Date date)
	{
		if(Gain.containsKey(date))
		return Gain.get(date);
		else return 0;
	}
	/**
	 * It returns the sum of the money made in a specific day only from the customers with a subscription
	 * @param date the date for which we want to know how money were made
	 * @return the sum of the money made in a specific day only from the customers with a subscription
	 */
	public double returnMoneyFromSubs(Date date)
	{
		if(GainSubs.containsKey(date))
		return GainSubs.get(date);
		else return 0;
	}
	/**
	 * It returns the sum of the money made in a specific day only from the sales
	 * @param date the date for which we want to know how money were made
	 * @return the sum of the money made in a specific day only from the sales
	 */
	public double returnMoneyFromSales(Date date)
	{
		if(GainSales.containsKey(date))
		return GainSales.get(date);
		else return 0;
	}
}
