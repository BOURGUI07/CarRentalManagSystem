/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package logic;
import domain.*;
import domain.CarCategory;
import domain.CarFuelPolicy;
import domain.CarSize;
import domain.CarSupplierRating;

import java.util.*;
import java.util.stream.Collectors;

public class ManageCars {
    private List<Car> cars;

    public ManageCars(){
        this.cars=new ArrayList<>();
    }

    public void registerNewCar(String model, String supplier){
        Car car = new Car(model, supplier);
        if(!this.cars.contains(car)){
            this.cars.add(car);
        }else{
            return;
        }
    }

    public void returnNothingIfEmpty(){
        if(this.cars.isEmpty()){
            return;
        }
    }

    public Car getCarForId(UUID id){
        for(Car car:this.cars){
            if(car.getCarId().equals(id)){
                return car;
            }
        }
        return null;
    }

    public Car getCarForModel(String model){
        for(Car car:this.cars){
            if(car.getModel().equals(model)){
                return car;
            }
        }
        return null;
    }

    public void setPriceMileage(UUID id, double price, double miles){
        this.getCarForId(id).setRentPrice(price);
        this.getCarForId(id).setMileage(miles);
    }


    public void setSeatsBags(UUID id, int seats, int smallbags, int largebags){
        this.getCarForId(id).setNumberOfSeats(seats);
        this.getCarForId(id).setNumberOfSmallBags(smallbags);
        this.getCarForId(id).setNumberOfLargeBags(largebags);
    }

    public void setDetails(UUID id, String details){
        this.getCarForId(id).setInsuranceDetails(details);
    }

    public void maintain_a_Car(UUID id){
        this.getCarForId(id).maintainCar();
    }

    public void returnCarFromrepair(UUID id){
        this.getCarForId(id).carNoLongerUnderRepair();
    }

    public void sellTheCar(UUID id){
        this.getCarForId(id).sellCar();
    }

    public void retireTheCar(UUID id){
        this.getCarForId(id).retireCar();
    }

    public void addMileageToCar(UUID id, double amount){
        this.getCarForId(id).addMileage(amount);
    }

    public void isCarAvailableById(UUID id){
        this.getCarForId(id).isAvailable();
    }

    public boolean isCarAvailableByModel(String model){
        return this.getCarForModel(model).isAvailable();
    }

    public boolean isCarAvailable(Car car){
        if(this.cars.contains(car)){
            return car.isAvailable();
        }
        return false;
    }

    public void removeCar(UUID id){
        if(this.getCarForId(id).canBeRemoved()){
            this.cars.remove(this.getCarForId(id));
        }else{
            System.out.println("To remove the car from fleet you have to either sell it or retire it!");
        }
    }

    public void viewcars(){
        this.returnNothingIfEmpty();
        this.cars.stream().forEach(car -> System.out.println(car));
    }

    public void viewAvailableCars(){
        this.returnNothingIfEmpty();
        this.cars.stream().filter(car -> car.isAvailable()==true).forEach(car -> System.out.println(car));
    }

    public void printCarModels(){
        this.returnNothingIfEmpty();
        this.cars.stream().filter(car -> car.isAvailable()==true).forEach(car -> System.out.println(car.getModel()));
    }

    public void viewUnAvailableCars(){
        this.returnNothingIfEmpty();
        this.cars.stream().filter(car -> car.isAvailable()==false).forEach(car -> System.out.println(car));
    }

    public void viewRentedCars(){
        this.returnNothingIfEmpty();
        this.cars.stream().filter(car -> car.isRented()==true).forEach(car -> System.out.println(car));
    }

    public void viewUnderRepairCars(){
        this.returnNothingIfEmpty();
        this.cars.stream().filter(car -> car.isUnderMaintenance()==true).forEach(car -> System.out.println(car));
    }

    public void viewCarsThatShouldBeRemoved(){
        this.returnNothingIfEmpty();
        this.cars.stream().filter(car -> car.canBeRemoved()==true).forEach(car -> System.out.println(car));
    }

    public void filterCarsByPrice(double minPrice, double maxPrice){
        returnNothingIfEmpty();
        if(maxPrice>minPrice){
            this.cars.stream().filter(car -> car.getRentPrice()>=minPrice && car.getRentPrice()<=maxPrice).forEach(car -> System.out.println(car));
        }else if(minPrice==maxPrice){
            this.cars.stream().filter(car -> car.getRentPrice()==minPrice).forEach(car -> System.out.println(car));
        }else{
            throw new IllegalArgumentException();
        }
    }

    public void filterCarsByMileage(double minMiles, double maxMiles){
        returnNothingIfEmpty();
        if(maxMiles>minMiles){
            this.cars.stream().filter(car -> car.getRentPrice()>=minMiles && car.getRentPrice()<=maxMiles).forEach(car -> System.out.println(car));
        }else if(minMiles==maxMiles){
            this.cars.stream().filter(car -> car.getRentPrice()==minMiles).forEach(car -> System.out.println(car));
        }else{
            throw new IllegalArgumentException();
        }
    }

    public void filterCarsBySeats(int minSeats){
        returnNothingIfEmpty();
        if(minSeats>=2){
            this.cars.stream().filter(car -> car.getNumberOfSeats()>=minSeats).forEach(c -> System.out.println(c));
        }else{
            throw new IllegalArgumentException();
        }
    }

    public void filterCarsByFuelPolicy(CarFuelPolicy policy){
        returnNothingIfEmpty();
        if(!(policy==null)){
            this.cars.stream().filter(car -> car.getFuelPolicy()==policy).forEach(car -> System.out.println(car));
        }else{
            throw new IllegalArgumentException();
        }
    }

    public void filterCarsByCategory(CarCategory category){
        returnNothingIfEmpty();
        if(!(category==null)){
            this.cars.stream().filter(car -> car.getCategory()==category).forEach(car -> System.out.println(car));
        }else{
            throw new IllegalArgumentException();
        }
    }

    public void filterCarsBySize(CarSize size){
        returnNothingIfEmpty();
        if(!(size==null)){
            this.cars.stream().filter(car -> car.getSize()==size).forEach(car -> System.out.println(car));
        }else{
            throw new IllegalArgumentException();
        }
    }

    public void filterCarsByRating(CarSupplierRating rating){
        returnNothingIfEmpty();
        if(!(rating==null)){
            this.cars.stream().filter(car -> car.getRating()==rating).forEach(car -> System.out.println(car));
        }else{
            throw new IllegalArgumentException();
        }
    }

    public void filterCarsByNumberOfSmallBags(int minSmallBags){
        returnNothingIfEmpty();
        if(minSmallBags>0){
            this.cars.stream().filter(car -> car.getNumberOfSmallBags()>=minSmallBags).forEach(car -> System.out.println(car));
        }else{
            throw new IllegalArgumentException();
        }
    }

    public void filterCarsByNumberOfLargeBags(int minLargeBags){
        returnNothingIfEmpty();
        if(minLargeBags>0){
            this.cars.stream().filter(car -> car.getNumberOfSmallBags()>=minLargeBags).forEach(car -> System.out.println(car));
        }else{
            throw new IllegalArgumentException();
        }
    }

    public void sortCarsByAscendingPrice(){
        returnNothingIfEmpty();
        List<Double> prices = this.cars.stream().map(car -> car.getRentPrice()).collect(Collectors.toCollection(ArrayList::new));
        Collections.sort(prices,(price1,price2) -> Double.compare(price1,price2));
        Iterator<Double> itr = prices.iterator();
        while(itr.hasNext()){
            for(Car car:this.cars){
                if(car.getRentPrice()==itr.next()){
                    System.out.println(car);
                }
            }
        }
    }

    public void sortCarsByAscendingMileage(){
        returnNothingIfEmpty();
        List<Double> mileage = this.cars.stream().map(car -> car.getMileage()).collect(Collectors.toCollection(ArrayList::new));
        Collections.sort(mileage,(mileage1,mileage2) -> Double.compare(mileage1,mileage2));
        Iterator<Double> itr = mileage.iterator();
        while(itr.hasNext()){
            for(Car car:this.cars){
                if(car.getRentPrice()==itr.next()){
                    System.out.println(car);
                }
            }
        }
    }

    public void sortCarsByDescendingPrice(){
        returnNothingIfEmpty();
        List<Double> prices = this.cars.stream().map(car -> car.getRentPrice()).collect(Collectors.toCollection(ArrayList::new));
        Collections.sort(prices,(price1,price2) -> Double.compare(price2,price1));
        Iterator<Double> itr = prices.iterator();
        while(itr.hasNext()){
            for(Car car:this.cars){
                if(car.getRentPrice()==itr.next()){
                    System.out.println(car);
                }
            }
        }
    }

    public void sortCarsByDescendingMileage(){
        returnNothingIfEmpty();
        List<Double> mileage = this.cars.stream().map(car -> car.getMileage()).collect(Collectors.toCollection(ArrayList::new));
        Collections.sort(mileage,(mileage1,mileage2) -> Double.compare(mileage2,mileage1));
        Iterator<Double> itr = mileage.iterator();
        while(itr.hasNext()){
            for(Car car:this.cars){
                if(car.getRentPrice()==itr.next()){
                    System.out.println(car);
                }
            }
        }
    }

    public boolean containsCar(Car car){
        return this.cars.contains(car);
    }


    public void listOfCarsAndId(){
        returnNothingIfEmpty();
        System.out.println("Car Model\tCar Id");
        for(Car car:this.cars){
            System.out.println(car.getModel() + "\t" + car.getCarId());
        }
    }
}
