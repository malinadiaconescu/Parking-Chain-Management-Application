import java.util.List;
import java.util.Scanner;
import java.io.*;
import java.util.*;
/**
 * contains the main of the project
 * @author Malina Diaconescu
 *
 */
public class Main {
	/**
	 * the main of my program that resolves all the requests
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			Scanner scanner=new Scanner(System.in);
			List<ParkingLot> ParkingChain=new ArrayList<ParkingLot>();
			System.out.println("Do you want to back up the data? \n 1 - Yes \n 2 - No");
			int raspuns=scanner.nextInt();
			File myObj=new File("C:\\Users\\malin\\eclipse-workspace\\p3-homework\\src\\parkinglot");
			Scanner myReader=new Scanner(myObj);
			int data=myReader.nextInt();
			
			while(data>0)
			{
				int nr=myReader.nextInt();
				ParkingLot P=new ParkingLot(nr);
				ParkingChain.add(P);
				data--;
			}
			if(raspuns==1)
			{
				try
			{
				FileInputStream filein=new FileInputStream("C:\\Users\\malin\\eclipse-workspace\\p3-homework\\src\\parkingdata.ser");
				ObjectInputStream in=new ObjectInputStream(filein);
				ParkingChain=(ArrayList<ParkingLot>)in.readObject();
				in.close();
				System.out.println("Data taken successfully!");
			}catch(IOException i)
			{
				System.out.println("An error occured");
				i.printStackTrace();
			}catch(ClassNotFoundException c)
				{
				System.out.println("Class not found");
				c.printStackTrace();
				}
			}
			
		
				
			
			
			boolean ok=true;
			
			do
			{
				System.out.println("\n What would you like to do? \n 1. Configure a parking lot \n 2. Show Parking Chain \n 3. Check availability \n 4. Park a car \n 5. Make a new subscription \n 6. Delete Subscription \n 7. See the money made in a day \n 8. Quit the program");
				boolean isNumber=false;
				int Value=0;
				do
				{
					if(scanner.hasNextInt())
					{
						Value=scanner.nextInt();
						isNumber=true;
					}
					else
					{
						System.out.println("Command not found! Choose another number");
						isNumber=false;
						scanner.next();
					}
				}while(isNumber==false);
				
				if(Value==1)
				{
				System.out.println("What parking lot do you want to configure? Parking lots available from 0 to "+ParkingChain.size());
				int Park=scanner.nextInt();
				if(Park>=0&&Park<ParkingChain.size())
				{
					System.out.println("Do you want to add or remove a parking zone? \n 1. Add \n 2. Remove");	
					int toDo=scanner.nextInt();
					if(toDo==1)
					{
						System.out.println("To add a parking zone, please fill in the following:");
						System.out.println("Number of spaces:");
						int number=scanner.nextInt();
						System.out.println("Type of cars the zone holds \n 1-SmallVehicle \n 2-MediumVehicle\n 3-BigVehicle ");
						int type=scanner.nextInt();
						System.out.println("The fee of the parking zone:");
						int fee=scanner.nextInt();
						if(type>=1&&type<=3)
						{ParkingZone p=new ParkingZone(number,Types.values()[type-1],fee);
						ParkingChain.get(Park).addParkingAreas(p);
						System.out.println("Parking Zone added successfully!");
						}
						else
						{System.out.println("Please try again! Type not available!");}
					}
					else if(toDo==2)
					{
						System.out.println("The index of the area you want to delete:");
						int index=scanner.nextInt();
						ParkingChain.get(Park).removeParkingAreas(index);
					}
				}
				else
				{
					System.out.println("Parking lot not available!");
				}
				}
				else if(Value==2)
				{
					for(ParkingLot P:ParkingChain)
						System.out.println(P);
				}
				else if(Value==3)
				{
					System.out.println("What parking lot you want to check the availability for? Parking Lots available from 0 to "+ParkingChain.size());
					int index=scanner.nextInt();
					System.out.println("Choose a date: ");
					System.out.println("Choose day:");
					int day=scanner.nextInt();
					System.out.println("Choose month:");
					int month=scanner.nextInt();
					System.out.println("Choose year:");
					int year=scanner.nextInt();
					Date date=new Date(day,month,year);
					System.out.println(ParkingChain.get(index).Availability(date)+" free spots");
					System.out.println("More information that might be useful:");
					for(ParkingLot park:ParkingChain)
						if(park.TotalNumberOfSpots()>0)
					{
				 		double x=(park.TotalNumberOfSpots()-park.Availability(date))*100/park.TotalNumberOfSpots();
						System.out.println("In Parking Lot "+ParkingChain.indexOf(park)+" there are "+park.Availability(date)+" free spots out of "+park.TotalNumberOfSpots()+" - a total occupancy of "+x+"%");
					}
						else 
						{
							System.out.println("In Parking Lot "+ParkingChain.indexOf(park)+" there are 0 parkings places");
						}
				}
				else if(Value==4)
				{
					System.out.println("Please fill in the following:");
					System.out.println("Type of your car: \n 1-SmallVehicle \n 2-MediumVehicle \n 3-BigVehicle \n Choose one: ");
					int type=scanner.nextInt();
					boolean ok1=false;
					scanner.nextLine();
					String plate;
					do
					{
						System.out.println("Type your license plate:");
						plate=scanner.nextLine();
						if(isPlate(plate)==true)
						{
							ok1=true;
						}
						else
						{
							System.out.println("Your plate is invalid! Try again");
						}
					}while(ok1==false);
					Car car=new Car(Types.values()[type-1],plate);
					System.out.println("Choose a parking lot - parking lots available from 0 to "+ParkingChain.size());
					int number=scanner.nextInt();
					System.out.println("You have parked here "+ParkingChain.get(number).TimesParked(car)+" times");
					if(ParkingChain.get(number).TimesParked(car)%10==0&&ParkingChain.get(number).TimesParked(car)>0)
					{
						System.out.println("A voucher is available!");
					}
					else System.out.println("No voucher available!");
					System.out.println("Choose a date: ");
					System.out.println("Choose day:");
					int day=scanner.nextInt();
					System.out.println("Choose month:");
					int month=scanner.nextInt();
					System.out.println("Choose year:");
					int year=scanner.nextInt();
					Date date=new Date(day,month,year);
					if(ParkingChain.get(number).Availability(date)>0)
					ParkingChain.get(number).NewCar(car,date);
					else System.out.println("No free spots");
				}
				else if(Value==5)
				{
					boolean ok1=false;
					scanner.nextLine();
					String plate;
					do
					{
						System.out.println("Type your license plate:");
						plate=scanner.nextLine();
						if(isPlate(plate)==true)
						{
							ok1=true;
						}
						else
						{
							System.out.println("Your plate is invalid! Try again");
						}
					}while(ok1==false);
					System.out.println("Choose the parking lot you want to make a subscription for: \n Parking lots available from 0 to "+ParkingChain.size());
					ok1=false;int park=0;
					do
					{
						park=scanner.nextInt();
						if(park<0 || park>ParkingChain.size())
							System.out.println("Parking Lot not Available!");
						else ok1=true;
					}while(ok1==false);
					ParkingChain.get(park).AddSubs(plate);
				}
				else if(Value==6)
				{
					boolean ok1=false;
					scanner.nextLine();
					String plate;
					do
					{
						System.out.println("Type your license plate:");
						plate=scanner.nextLine();
						if(isPlate(plate)==true)
						{
							ok1=true;
						}
						else
						{
							System.out.println("Your plate is invalid! Try again");
						}
					}while(ok1==false);
					System.out.println("Choose the parking lot you want to make a subscription for: \n Parking lots available from 0 to "+ParkingChain.size());
					ok1=false;int park=0;
					do
					{
						park=scanner.nextInt();
						if(park<0 || park>ParkingChain.size())
							System.out.println("Parking Lot not Available!");
						else ok1=true;
					}while(ok1==false);
					ParkingChain.get(park).DeleteSubs(plate);
				}
				else if(Value==7)
				{
					System.out.println("Choose the parking lot you want to see the gain for: \n Parking lots available from 0 to "+ParkingChain.size());
					boolean ok1=false;int park=0;
					do
					{
						park=scanner.nextInt();
						if(park<0 || park>ParkingChain.size())
							System.out.println("Parking Lot not Available!");
						else ok1=true;
					}while(ok1==false);
					System.out.println("Choose a date: ");
					System.out.println("Choose day:");
					int day=scanner.nextInt();
					System.out.println("Choose month:");
					int month=scanner.nextInt();
					System.out.println("Choose year:");
					int year=scanner.nextInt();
					Date date=new Date(day,month,year);
					System.out.println("Money made on "+day+"/"+month+"/"+year+": "+ParkingChain.get(park).MoneyADay(date));
					System.out.println("Money made on "+day+"/"+month+"/"+year+" from Small Vehicles: "+ParkingChain.get(park).MoneyFromSmallVehicle(date));
					System.out.println("Money made on "+day+"/"+month+"/"+year+" from Medium Vehicles: "+ParkingChain.get(park).MoneyFromMediumVehicle(date));
					System.out.println("Money made on "+day+"/"+month+"/"+year+" from Big Vehicles: "+ParkingChain.get(park).MoneyFromBigVehicle(date));
					System.out.println("Money made on "+day+"/"+month+"/"+year+" from Subscribers: "+ParkingChain.get(park).MoneyFromSubs(date));
					System.out.println("Money made on "+day+"/"+month+"/"+year+" from Sales: "+ParkingChain.get(park).MoneyFromSales(date));
				}
				else if(Value==8)
				{
					ok=false;
				}
				else
				{
					System.out.println("Sorry, command not found!");
				}
			}while(ok==true);
			try
			{
				FileOutputStream fileout=new FileOutputStream("C:\\Users\\malin\\eclipse-workspace\\p3-homework\\src\\parkingdata.ser");
				ObjectOutputStream out=new ObjectOutputStream(fileout);
				out.writeObject(ParkingChain);
				fileout.close();
				System.out.println("Data was saved!");
			}catch(IOException i)
			{
				System.out.println("An error occurred");
				i.printStackTrace();
			}
			
			
			
		}catch(FileNotFoundException e)
		{
			System.out.println("An error occured: file not found");
			e.printStackTrace();
		}
	}
	/**
	 * Method to check whether a string can be a license plate
	 * @param Plate the string to verify
	 * @return true if it can, false otherwise
	 */
	public static boolean isPlate(String Plate)
	{
		if(Plate.length()==7)
		{
			if(Plate.matches("[A-Z][A-Z][0-9][0-9][A-Z][A-Z][A-Z]")==true)
			{
			return true;
			}
			return false;
		}
		else if(Plate.length()==6)
		{
			if(Plate.matches("B[0-9][0-9][A-Z][A-Z][A-Z]")==true||Plate.matches("B[0-9][0-9][0-9][A-Z][A-Z][A-Z]")==true)
			{
				return true;
			}
			return false;
		}
		return false;
	}

}
