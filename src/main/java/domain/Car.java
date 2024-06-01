/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package domain;

import java.util.UUID;

public class Car{


    private UUID carId;
    private String insuranceDetails;

    private double rentPrice;
    private String model;
    private int numberOfSeats;
    private CarSize size;
    private CarSupplierRating rating;
    private String supplier;
    private CarCategory category;
    private int numberOfSmallBags;
    private int numberOfLargeBags;
    private double mileage;
    private boolean isUnderMaintenance;
    private boolean isRented;
    private boolean toBeRemoved;
    private CarFuelPolicy fuelPolicy;
    

    public Car(String model, String supplier){
        this.rentPrice = 0.0;
        this.carId= UUID.randomUUID();
        this.mileage = 0.0;
        this.isUnderMaintenance=false;
        this.isRented=false;
        this.model = model;
        this.numberOfSeats=0;
        this.size=null;
        this.rating=null;
        this.numberOfLargeBags=0;
        this.numberOfSmallBags=0;
        this.supplier=supplier;
        this.category=null;
        this.insuranceDetails=null;
        this.toBeRemoved = false;
        this.fuelPolicy=null;
    }

    public CarFuelPolicy getFuelPolicy(){
        return this.fuelPolicy;
    }

    public void setFuelPolicy(CarFuelPolicy newPolicy){
        this.fuelPolicy=newPolicy;
    }

    public void retireCar(){
        this.toBeRemoved=true;
    }

    public void sellCar(){
        this.toBeRemoved=true;
    }

    public boolean canBeRemoved(){
        return this.toBeRemoved;
    }

    public double getRentPrice(){
        return this.rentPrice;
    }

    public void setRentPrice(double rentPrice){
        if(rentPrice<=0){
            throw new IllegalArgumentException();
        }
        this.rentPrice=rentPrice;
    }

    public double getMileage(){
        return this.mileage;
    }

    public void setMileage(double miles){
        if(miles<=0){
            throw new IllegalArgumentException();
        }
        this.mileage=miles;
    }

    public void addMileage(double amount){
        if(amount<0){
            throw new IllegalArgumentException();
        }
        this.mileage+=amount;
    }

    public String getModel(){
        return this.model;
    }

    public String getSupplier(){
        return this.supplier;
    }

    public int getNumberOfSeats(){
        return this.numberOfSeats;
    }

    public void setNumberOfSeats(int number){
        if(number<2){
            throw new IllegalArgumentException();
        }
        this.numberOfSeats=number;
    }

    public CarCategory getCategory(){
        return this.category;
    }

    public void setCategory(CarCategory newCategory){
        if(newCategory==null){
            throw new IllegalArgumentException();
        }
        this.category=newCategory;
    }

    public int getNumberOfLargeBags(){
        return this.numberOfLargeBags;
    }

    public int getNumberOfSmallBags(){
        return this.numberOfSmallBags;
    }

    public void setNumberOfLargeBags(int number){
        if(number<0){
            throw new IllegalArgumentException();
        }
        this.numberOfLargeBags=number;
    }

    public void setNumberOfSmallBags(int number){
        if(number<0){
            throw new IllegalArgumentException();
        }
        this.numberOfSmallBags=number;
    }

    public boolean isUnderMaintenance(){
        return this.isUnderMaintenance;
    }

    public void maintainCar(){
        this.isUnderMaintenance=true;
    }

    public boolean isRented(){
        return this.isRented;
    }

    public void carNoLongerUnderRepair(){
        this.isUnderMaintenance=false;
    }

    public void carNoLongerRented(){
        this.isRented=false;
    }

    public void rentCar(){
        this.isRented=true;
    }

    public boolean isAvailable(){
        return !this.isUnderMaintenance && !this.isRented && !this.canBeRemoved();
    }

    public CarSize getSize(){
        return this.size;
    }

    public void setCarSize(CarSize newSize){
        if(newSize==null){
            throw new IllegalArgumentException();
        }
        this.size=newSize;
    }

    public CarSupplierRating getRating(){
        return this.rating;
    }

    public UUID getCarId(){
        return this.carId;
    }

    public void setRating(CarSupplierRating newRating){
        if(newRating==null){
            throw new IllegalArgumentException();
        }
        this.rating=newRating;
    }

    public String getInsuranceDetails(){
        return this.insuranceDetails;
    }

    public void setInsuranceDetails(String details){
        if(details==null){
            throw new IllegalArgumentException();
        }
        this.insuranceDetails=details;
    }

    public boolean equals(Object car){
        if(this==car){
            return true;
        }

        if(!(car instanceof Car)){
            return false;
        }

        Car castedCar = (Car) car;
        if(this.carId.equals(castedCar.getCarId())){
            return true;
        }

        return false;
    }

    public String toString(){
        String x = this.model + "\n";
        String y = "Registration Number: " + this.carId + "\n";
        if(this.isAvailable()){
            y+= "Is the car available? Yes!" + "\n";
        }else{
            y+= "Is the car available? No!" + "\n";
        }
        String a = "Size: " + this.size + "\t" + "Category: " + this.category + "\tSupplier Rating: " + this.rating + "\n";
        String b = "Number of seats: " + this.numberOfSeats + "\tNumber of Large Bags: "+ this.numberOfLargeBags + "\tNumber of small bags: " + this.numberOfSmallBags + "\n";
        String c = "Mileage: " + this.mileage;
        return x+y+a+b+c;
    }
}
