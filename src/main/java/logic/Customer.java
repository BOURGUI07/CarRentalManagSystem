/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package logic;
import domain.*;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class Customer implements Comparable<Customer>{
    private String name;
    private UUID licenceNumber;
    private String contactInfo;
    private List<Car> bookings;
    private ManageCars manag;
    private double amountSpent;
    private int numberOfTimesCustomerBookedCar;
    private ManageCustomers manageCustomers;
    private List<String> transactions;

    public Customer(String name){
        if(name==null){
            throw new IllegalArgumentException();
        }
        this.name=name;
        this.licenceNumber=UUID.randomUUID();
        this.contactInfo=null;
        this.bookings = new ArrayList<>();
        this.manag = new ManageCars();
        this.amountSpent=0.0;
        this.numberOfTimesCustomerBookedCar=0;
        this.manageCustomers= new ManageCustomers();
        this.transactions=new ArrayList<>();
    }

    public String getCustomerName(){
        return this.name;
    }

    public UUID getLicenseNumber(){
        return this.licenceNumber;
    }

    public String getContact(){
        return this.contactInfo;
    }

    public void setContactInfo(String contactInfo){
        this.contactInfo=contactInfo;
    }

    public List<Car> getBookedCars(){
        return this.bookings;
    }

    public double getAmountSpent(){
        return this.amountSpent;
    }

    public int getCount(){
        return this.numberOfTimesCustomerBookedCar;
    }

    public void increaseCount(){
        this.numberOfTimesCustomerBookedCar++;
    }

    public void bookCar(String carModel, LocalDate startDate, LocalDate endDate){
        LocalDate now =LocalDate.now();
        Car car = this.manag.getCarForModel(carModel);
        if(this.isTheDesiredCarAvailable(carModel)){
            this.bookings.add(car);
            this.accumulateAmountSpentForThisCar(car, startDate, endDate);
            this.increaseCount();
            car.rentCar();
            this.manageCustomers.addCustomer(this);
            String transaction = this.transaction(now, carModel, totalRentPrice(car, startDate, endDate), startDate, endDate);
            this.transactions.add(transaction);
        }else{
            System.out.println("The car is not available right now!");
            System.out.println("Browse available cars:");
            this.manag.viewAvailableCars();
        }
        
        
    }

    public boolean isTheDesiredCarAvailable(String carModel){
        if(carModel==null){
            throw new IllegalArgumentException();
        }
        this.manag.returnNothingIfEmpty();
        Car car = this.manag.getCarForModel(carModel);
        return this.manag.isCarAvailable(car);
    }

    public void returnCar(String carModel, double mileageAmount){
        this.manag.returnNothingIfEmpty();
        Car car = this.manag.getCarForModel(carModel);
        if(this.manag.containsCar(car)){
            car.carNoLongerRented();
        }
        this.manag.addMileageToCar(car.getCarId(), mileageAmount);
    }

    public double totalRentPrice(Car car, LocalDate startDate, LocalDate endDate){
        return car.getRentPrice()*totalRentDuration(startDate, endDate);
    }

    public int totalRentDuration(LocalDate startDate, LocalDate endDate){
        return (int) ChronoUnit.DAYS.between(startDate, endDate);
    }

    public boolean bookCanBeCancelled(LocalDate startDate){
        int dayDifference = (int) ChronoUnit.DAYS.between(startDate, LocalDate.now());
        return dayDifference >=1;
    }

    public void cancelRent(Car car, LocalDate startDate){
        if(car==null || startDate==null){
            throw new IllegalArgumentException();
        }
        if(this.bookings.contains(car) && bookCanBeCancelled(startDate)){
            this.bookings.remove(car);
            car.carNoLongerRented();
        }else{
            System.out.println("You can't cancel the rent! The cancel must be at least 24 hours before the start date.");
        }
    }

    public void returnNothingIfBookingsIsEmpty(){
        if(this.bookings.isEmpty()){
            return;
        }
    }

    public void  accumulateAmountSpent(double amount){
        if(amount<=0){
            throw new IllegalArgumentException();
        }
        this.amountSpent+=amount;
    }

    public void accumulateAmountSpentForThisCar(Car car, LocalDate startDate, LocalDate endDate){
        this.accumulateAmountSpent(this.totalRentPrice(car, startDate, endDate));
    }

    public double averageAmountSpentPerTransaction(){
        if(this.getCount()==0){
            return 0.0;
        }
        return this.amountSpent/getCount();
    }

    public boolean equals(Object cust){
        if(this==cust){
            return true;
        }
        if(!(cust instanceof Customer)){
            return false;
        }
        Customer customer =(Customer) cust;
        if(this.name.equals(customer.getCustomerName())){
            return true;
        }
        return false;
    }

    public String bookedCars(){
        if(this.bookings.isEmpty()){
            return "No booked cars yet!";
        }

        String a= "";
        for(Car car:this.bookings){
            a+=car.getModel() + "\n";
        }
        return a + "\n";
    }

    public String toString(){
        String a = this.name + ", ID = " + this.licenceNumber + "\n";
        String b = "List of booked cars: \n" + bookedCars();
        String c = "Total amount spent by this customer: " + this.amountSpent + "$\n";
        String d = "Average amount spent per booking: " + this.averageAmountSpentPerTransaction() + "$";
        return a+b+c+d;
    }

    public String transaction(LocalDate now, String carModel ,double amountPaid, LocalDate starDate, LocalDate endDate){
        return "Date: " + now + "\tRented Car: " + carModel + "\tAmount Paid: " + amountPaid + "$\tFrom " + starDate + " to " + endDate;
    }

    public void viewRentalHistory(){
        this.returnNothingIfBookingsIsEmpty();
        for(String transaction:this.transactions){
            System.out.println(transaction);
        }
    }

    public int compareTo(Customer cust){
        if(this.amountSpent>cust.getAmountSpent()){
            return -1;
        }else if(this.amountSpent<cust.getAmountSpent()){
            return 1;
        }else{
            return 0;
        }
    }

}
