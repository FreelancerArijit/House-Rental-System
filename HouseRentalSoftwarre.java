package Projects.HouseRentalSystem;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.InputMismatchException;

//house
 class House{
    private String  house_no;
    private String house_info;
    private String house_location;
    private double base_price;
    private boolean isAvailable;

    //parametrized constructer used for set values of private data members
    House(String house_no, String house_info, String house_location, double base_price, boolean isAvailable){

        this.house_no = house_no;
        this.house_info  = house_info;
        this.house_location = house_location;
        this.base_price = base_price;
        this.isAvailable = true;
    }
    //getter
     public String getHouse_no(){
        return house_no;
     }
     public String getHouse_info(){
        return house_info;
     }
     public String getHouse_location(){
        return house_location;
     }
     public double getBase_price(int rental_days){
        return base_price * rental_days;
     }
     public boolean getIsAvailable(){
        return isAvailable;
     }

     public void rent(){
        isAvailable = false;
     }
     public void returnHome(){
        isAvailable = true;
     }


        }


     //Customers
    class Customer{
    private String name;
    private String mobile_no;
    private String id_no;

    Customer(String name, String mobile_no, String id_no){
        this.name = name;
        this.mobile_no  = mobile_no;
        this.id_no = id_no;

        }

        //getter
            public String getName(){
        return name;
        }
        public String getMobile_no(){
        return mobile_no;
        }

        public String getId_no(){
        return id_no;
        }

         }

         //Rental Process
     class RentalProcess{
     private House house_no;
     private Customer customer_name;

     int days;

     RentalProcess(House house_no, Customer customer_name, int days){
         this.house_no = house_no;
         this.customer_name = customer_name;
         this.days = days;
     }
             //getter
             public House getHouse_no(){
                 return house_no;
             }
             public Customer getCustomer_name(){
                return customer_name;
             }
             public int getDays(){
                return days;
             }
         }

         //Main home rental system
      class HomeRentalSystem{

     private ArrayList<House> houseList;
     private ArrayList<Customer>customerList;
     private ArrayList<RentalProcess> rentalInfo;

     //allocate memory
    public HomeRentalSystem(){
      houseList = new ArrayList<>();
      customerList = new ArrayList<>();
      rentalInfo = new ArrayList<>();
    }

    //features to add data
    public void addHouseList(House house){
        houseList.add(house);
    }
    public void addCustomerList(Customer customer){
        customerList.add(customer);
    }


    public void rentHouse(House house, Customer customer, int days ){
        if(house.getIsAvailable()) {
            house.rent();
            rentalInfo.add(new RentalProcess(house, customer,days));
        }else{
            System.out.println("Home is booked now");
        }
    }

    public void returnHouse(House house){
        house.returnHome();
        RentalProcess rentalToRemove = null;
        for(RentalProcess rental : rentalInfo ){
            if(rental.getHouse_no() == house){
                rentalToRemove = rental;
                break;
            }
        }
        if(rentalToRemove != null){
            rentalInfo.remove(rentalToRemove);
            System.out.println("House is available");
        }else{
            System.out.println("House is not available");
        }
    }

    public void menue(){
        Scanner sc = new Scanner(System.in);

        try {

        while(true){

                System.out.println("--Rental House System--");
                System.out.println("1. Book a house");
                System.out.println("2. Checkout a house");
                System.out.println("3. Exit from  the system");
                System.out.println("Enter your choice");

                int choice = sc.nextInt();
                sc.nextLine();


//

            if(choice == 1){
                System.out.println("Process of house booking is started");
                System.out.println("Enter your name: ");
                String name = sc.nextLine();
                System.out.println("Enter mobile no: ");
                String phnNo = sc.nextLine();
                System.out.println("Enter ID no: ");
                String custId = sc.nextLine();
//

                System.out.println("List of available house");
                //displaying information of available houses
                for(House house : houseList){
                    if(house.getIsAvailable()){
                        System.out.println(house.getHouse_no() + "--" + house.getHouse_location() + "--" +
                                house.getHouse_info());
                    }

                }

                System.out.println("Enter house no which you want to book");
               String bookedHouseNo = sc.nextLine();
               System.out.println("Enter no of booking days");
               int bookingDays = sc.nextInt();
               sc.nextLine();//consumes new line




                Customer newCustomer = new Customer(name, phnNo,custId );//passed the enter info as argument
                addCustomerList(newCustomer);

                House rentedHouse = null;
                //check if the house is available or not
                for(House house : houseList) {
                    if (house.getHouse_no().equals(bookedHouseNo) && house.getIsAvailable()) {
                        rentedHouse = house;
                        break;
                    }else{
                        System.out.println("House no is not valid");
                        break;
                    }
                }


                    if(rentedHouse != null) {
                        double totalPrice = rentedHouse.getBase_price(bookingDays);

                        System.out.println("--Rental information--");
                        //displaying customer info who booked a house
                        System.out.println(newCustomer.getId_no() + "--" + newCustomer.getName());
                        //displaying the house info which is booked
                        System.out.println(rentedHouse.getHouse_no() + "--" + rentedHouse.getHouse_info() +
                                "--" + rentedHouse.getHouse_location());
                        System.out.println(bookingDays);
                        System.out.println(totalPrice);


                        System.out.println("Confirm: Y/n");




                        String confirm = sc.nextLine();
                        if(confirm.equalsIgnoreCase("Y")){
                            rentHouse(rentedHouse, newCustomer,bookingDays);
                            System.out.println("--You booked this house, your house no is: "+ rentedHouse.getHouse_no());
                        }else{
                            System.out.println("you booking is cancelled");
                        }

                    }




            }else if(choice == 2){
                System.out.println("Checkout  process is started");
                System.out.println("Enter house no");
                String houseNo = sc.nextLine();
                House houseToReturn = null;
                for(House house : houseList) {
                    if (house.getHouse_no().equals(houseNo) && !house.getIsAvailable()) {
                        houseToReturn = house;
                        break;
                    }
                }

                    if(houseToReturn != null){
                        Customer customer = null;
                        for(RentalProcess rentalProcess : rentalInfo){
                           if(rentalProcess.getHouse_no() == houseToReturn){
                               customer = rentalProcess.getCustomer_name();
                               break;
                           }
                       }

                        if(houseToReturn != null){
                            System.out.println("--Checkout house information--");
                            System.out.println(houseToReturn.getHouse_no() + "--" + houseToReturn.getHouse_info()
                                    + "--" + houseToReturn.getHouse_location());
                            System.out.println("--Customer information--");
                            System.out.println(customer.getName()  + "--" + customer.getId_no() + "--" +
                                    customer.getMobile_no());

                            houseToReturn.returnHome();//available the house again for further booking
                        }
//
                    }else{
                        System.out.println("Invalid house no");
                    }

            }else if(choice == 3){
                break;
            }else{
                System.out.println("Invalid choice choose a correcct option");
            }


        }
            //new
        }catch(InputMismatchException e){
            System.out.println("choose  the correct option: "+ e);

        }

        System.out.println("Thanks for visiting House rantal system, visit again :)");
    }


         }


public class HouseRentalSoftwarre {
    public static void main(String args[]){

        HomeRentalSystem hrs = new HomeRentalSystem();

        //Listed houses that customer can book
        House h1 = new House("123","2BHK","Saltlake",450.25,true);
        House h2 = new House("125","3BHK","Saltlake",850.35,true);
        House h3 = new House("326","1BHK","Tollyganj",250.35,true);
        House h4 = new House("455","2BHK","Howrah",355.25,true);

        hrs.addHouseList(h1);
        hrs.addHouseList(h2);
        hrs.addHouseList(h3);
        hrs.addHouseList(h4);

        hrs.menue();



    }
}
