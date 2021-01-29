import java.io.Serializable;
import java.util.*;
/**
 * defines a parking lot
 * @author Malina Diaconescu
 */
public class ParkingLot implements Serializable {
	/**
	 * number of entrances the parking lot has
	 */
	private int NumberOfEntrances;
	/**
	 * list of parking zones that the parking lot contains
	 */
	private List<ParkingZone> ParkingAreas=new ArrayList<ParkingZone>();
	/**
	 * list of cars that parked in the parking lot
	 */
	private List<Car> CarsParked=new ArrayList<Car>();
	/**
	 * list of integers that denotes how many times each car has parked
	 */
	private List<Integer> HowManyTimes=new ArrayList<Integer>();
	/**
	 * map of strings and dates; strings denotes the license plates that parked in the parking lot and the list contains dates of when the license plate parked in the parking lot
	 */
	private Map<String, List<Date> > When=new HashMap<String,List<Date>>();
	/**
	 * a list of license plates that has subscription
	 */
	private List<String> Subscribers=new ArrayList<String>();
	/**
	 * getter for the number of entrances
	 * @return number of entrances
	 */
	public int getNumberOfEntrances() {
		return NumberOfEntrances;
	}
	/**
	 * setter for the number of entrances
	 * @param numberOfEntrances the number of entrances you want to set for the parking lot
	 */
	public void setNumberOfEntrances(int numberOfEntrances) {
		NumberOfEntrances = numberOfEntrances;
	}
	/**
	 * getter for the list of parking zones
	 * @return list of parking zones
	 */
	public List<ParkingZone> getParkingAreas() {
		return ParkingAreas;
	}
	/**
	 * setter for the list of parking zones
	 * @param parkingAreas
	 */
	public void setParkingAreas(List<ParkingZone> parkingAreas) {
		ParkingAreas = parkingAreas;
	}
	/**
	 * Constructor for Parking Lot object
	 * @param NumberOfEntrances number of entrances of the parking lot
	 */
	public ParkingLot(int NumberOfEntrances) {
		this.NumberOfEntrances=NumberOfEntrances;
	}
	/**
	 * Method to add a new parking zone to the parking lot
	 * @param Area a new parking zone
	 */
	public void addParkingAreas(ParkingZone Area) {
		this.ParkingAreas.add(Area);
	}
	/**
	 * Method to remove a parking zone of the parking lot
	 * @param index
	 */
	public void removeParkingAreas(int index)
	{
		if(index>=0&&index<ParkingAreas.size())
		{
			this.ParkingAreas.remove(index);
			System.out.println("Parking zone removed successfully!");
		}
		else System.out.println("Parking zone does not exist!");
		
	}
	/**
	 * Method that counts the number of cars that the parking lot can holds
	 * @return an integer denoting the number of spaces in the parking lot
	 */
	public int TotalNumberOfSpots()
	{
		int sum=0;
		for(ParkingZone P:ParkingAreas)
		{
			sum+=P.getNumberOfSpaces();
		}
		return sum;
	}
	/**
	 * Method that counts the number of available spaces
	 * @param date the date you want to know availability for
	 * @return an integer denoting the number of available spaces in the parking lot
	 */
	public int Availability(Date date)
	{
		int sum=0;
		for(ParkingZone P:ParkingAreas)
		{
			sum+=P.getNumberOfSpaces();
		}
		int sum1=0;
		for(Car aux:CarsParked)
		{
			for(Date d:When.get(aux.getNumber()))
			{
				if(d.equals(date)) sum1++;
			}
		}
		return sum-sum1;
	}
	/**
	 * Method that counts how many times a car has parked
	 * @param car the car we want to know the information about
	 * @return integer denoting how many times a car has parked
	 */
	public int ParkingsNumber(Car car)
	{
		int k=0;
		for(int i=0;i<CarsParked.size();i++)
		{
			if(car.getNumber().equals(CarsParked.get(i).getNumber())&&car.getType().equals(CarsParked.get(i).getType()))
				k= HowManyTimes.get(i).intValue();
		}
		return k;
	}
	/**
	 * A method that is called when we want to park a car. It searches if the car has where to park and how much it has to pay
	 * @param car the car to be parked
	 * @param date when the car has to be parked
	 */
	public void NewCar(Car car,Date date)
	{
		int pos=0;
		int Situation=1;
		if(Subscribers.contains(car.getNumber())) Situation=3;
		else
			if(CarsParked.contains(car))
			{
				if(HowManyTimes.get(CarsParked.indexOf(car))%10==0&&HowManyTimes.get(CarsParked.indexOf(car))>0) Situation=2;
			}
		boolean gasit=false;
		for(int i=0;i<CarsParked.size();i++)
			if(car.getNumber().equals(CarsParked.get(i).getNumber())&& car.getType().equals(CarsParked.get(i).getType()))
				gasit=true;
		boolean parked=false;
		for(int i=0;i<ParkingAreas.size();i++)
			{if(ParkingAreas.get(i).getType().equals(car.getType())&&ParkingAreas.get(i).isSpace(date)==true)
			{
				ParkingAreas.get(i).NewCar(date,Situation);
				if(gasit==false)
				{
					CarsParked.add(car);
					When.putIfAbsent(car.getNumber(), new ArrayList<Date>());
					When.get(car.getNumber()).add(date);
					HowManyTimes.add(1);
					parked=true;
					System.out.println("Car parked succesfully!");
				}
				else
				{
					When.get(car.getNumber()).add(date);
					int aux=HowManyTimes.get(pos).intValue();
					HowManyTimes.set(pos, aux+1);
					parked=true;
					System.out.println("Car parked succesfully!");
				}
			}
			}
		if(parked==false) System.out.println("Could not execute the command! Did not found a free spot for you!");
	}
	/**
	 *  Method that counts how many times a car has parked
	 * @param car the car in discussion
	 * @return an integer denoting how many times the car has parked
	 */
	public int TimesParked(Car car)
	{
		if(When.containsKey(car.getNumber()))
			return When.get(car.getNumber()).size();
		else return 0;
	}
	/**
	 * Method that adds a new customer with subscription
	 * @param S the license plate
	 */
	public void AddSubs(String S)
	{
		if(Subscribers.contains(S))
			System.out.println("You already have one!");
		else
			{this.Subscribers.add(S);
			System.out.println("Subscription created Succesfully!");
			}
	}
	/**
	 * Method that checks whether the car has a subscription
	 * @param car the car in discussion
	 * @return true if it has, false otherwise
	 */
	public boolean hasSub(Car car)
	{
		return Subscribers.contains(car.getNumber());
	}
	/**
	 * Method to delete a subscription
	 * @param S the license plate of the car's subscription we want to delete
	 */
	public void DeleteSubs(String S)
	{
		if(Subscribers.contains(S))
		{
			Subscribers.remove(S);
		}
		else System.out.println("There is no subscription");
	}
	/**
	 * Method that counts the total money made in a day
	 * @param date the day we want to know the gain for
	 * @return integer denoting the total money made in a day
	 */
	public double MoneyADay(Date date)
	{
	double sum=0;
	for(int i=0;i<ParkingAreas.size();i++)
		sum+=ParkingAreas.get(i).returnGain(date);
	return sum;
	}
	/**
	 * Method that counts the money made from small vehicles
	 * @param date the day we want to know the gain for
	 * @return integer denoting the total money made in a day from small vehicles
	 */
	public double MoneyFromSmallVehicle(Date date)
	{
		double sum=0;
		for(int i=0;i<ParkingAreas.size();i++)
			if(ParkingAreas.get(i).getType().equals(Types.SmallVehicle))
			sum+=ParkingAreas.get(i).returnGain(date);
		return sum;
	}
	/**
	 * Method that counts the money made from medium vehicles
	 * @param date the day we want to know the gain for
	 * @return integer denoting the total money made in a day from medium vehicles
	 */
	public double MoneyFromMediumVehicle(Date date)
	{
		double sum=0;
		for(int i=0;i<ParkingAreas.size();i++)
			if(ParkingAreas.get(i).getType().equals(Types.MediumVehicle))
			sum+=ParkingAreas.get(i).returnGain(date);
		return sum;
	}
	/**
	 * Method that counts the money made from big vehicles
	 * @param date the day we want to know the gain for
	 * @return integer denoting the total money made in a day from big vehicles
	 */
	public double MoneyFromBigVehicle(Date date)
	{
		double sum=0;
		for(int i=0;i<ParkingAreas.size();i++)
			if(ParkingAreas.get(i).getType().equals(Types.BigVehicle))
			sum+=ParkingAreas.get(i).returnGain(date);
		return sum;
	}
	/**
	 * Method that counts the money made from subscribers
	 * @param date the day we want to know the gain for
	 * @return integer denoting the total money made in a day from subscribers
	 */
	public double MoneyFromSubs(Date date)
	{
		double sum=0;
		for(int i=0;i<ParkingAreas.size();i++)
			sum+=ParkingAreas.get(i).returnMoneyFromSubs(date);
		return sum;
	}
	/**
	 * Method that counts the money made from sales
	 * @param date the day we want to know the gain for
	 * @return integer denoting the total money made in a day from sales
	 */
	public double MoneyFromSales(Date date)
	{
		double sum=0;
		for(int i=0;i<ParkingAreas.size();i++)
			sum+=ParkingAreas.get(i).returnMoneyFromSales(date);
		return sum;
	}
	@Override
	/**
	 * toString override
	 */
	public String toString() {
		return "ParkingLot [NumberOfEntrances=" + NumberOfEntrances + ", ParkingAreas=" + ParkingAreas + "]";
	}
	
}
