/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ui;
import logic.*;
import domain.Car;
import domain.CarCategory;
import domain.CarFuelPolicy;
import domain.CarSize;
import domain.CarSupplierRating;
import domain.User;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.time.*;



public class UserInterface {
    private static final String EMAIL_PATTERN = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
    private static final String uuidPattern="^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$";
    private ManageCars carManag;
    private ManageCustomers custManag;
    private ManageUsers userManag;
    private Scanner scanner;

    public UserInterface(Scanner scanner){
        this.scanner=scanner;
        this.carManag=new ManageCars();
        this.custManag=new ManageCustomers();
        this.userManag=new ManageUsers();
    }

    public void welcomePage(){
        System.out.println("Welcome to our website.\n");
        while(true){
            System.out.println("[0]: Register");
            System.out.println("[1]: Login");
            System.out.println("[2]: Browse cars");
            System.out.println("[3]: Quit!");
            int answer = Integer.valueOf(this.scanner.nextLine());
            if(answer==3){
                break;
            }
            if(answer==0){
                this.registerPage();
            }else if(answer==1){
                this.loginPage();
            }else{
                this.carManag.viewcars();
            }
        }
    }

    public void registerPage(){
        System.out.println("Enter username:");
        String username = this.scanner.nextLine();
        while(username.isBlank()){
            System.out.println("Please enter your username!");
            username = this.scanner.nextLine();
        }
        System.out.println("Enter password: ");
        String password = this.scanner.nextLine();
        while(password.isBlank()){
            System.out.println("Please enter your password!");
            password = this.scanner.nextLine();
        }
        System.out.println("Confirm password: ");
        String confirmedPassword = this.scanner.nextLine();
        while(!confirmedPassword.equals(password)){
            System.out.println("Wrong! Try again!");
            confirmedPassword = this.scanner.nextLine();
        }
        System.out.println("Enter your E-mail");
        String email = this.scanner.nextLine();
        while(!isValidEmail(email)){
            System.out.println("Your email is invalid, try again!");
            email = this.scanner.nextLine();
        }
        User user = new User(username, password, email);
        if(this.userManag.isRegistrated(username)){
            System.out.println("You're already registrated! You have to login!");
            System.out.println("[0]: Go to login page.");
            System.out.println("[1]: Return to welcome Page.");
            int answer = Integer.valueOf(this.scanner.nextLine());
            if(answer==0){
                loginPage();
            }else{
                welcomePage();
            }
        }else{
            System.out.println("Succcessfuly registrated!");
            if(user.getPassword().startsWith("admin@")){
                giveoOptionsForAdmin();
            }else{
                Customer customer = new Customer(username);
                giveOptionsForUser(customer);
            }

        }
        
    }

    public void loginPage(){
        System.out.println("Enter your username");
        String username = this.scanner.nextLine();
        if(this.userManag.isRegistrated(username)){
            System.out.println("Enter password (Wrong password in 3 tries, you're going to be directed to welcome Page!)");
            String password = this.scanner.nextLine();
            int count=1;
            while(!password.equals(this.userManag.getPasswordFor(username)) && count<4){
                count++;
                System.out.println("Try again!" + (4-count) + " more times");
            }
            if(password.equals(this.userManag.getPasswordFor(username)) && password.startsWith("admin@")){
                giveoOptionsForAdmin();
            }else if(password.equals(this.userManag.getPasswordFor(username)) && !password.startsWith("admin@")){
                giveOptionsForUser(new Customer(username));
            }else{
                welcomePage();
            }
        }
        
    }

    public void giveoOptionsForAdmin(){
        System.out.println("Welcome to the admin panel!");
        while(true){
            System.out.println("[0]:  Register new car");
            System.out.println("[1]:  View cars");
            System.out.println("[2]:  View Available cars");
            System.out.println("[3]:  View unavailable cars");
            System.out.println("[4]:  View rented cars");
            System.out.println("[5]:  View cars under maintenance");
            System.out.println("[6]:  View cars that should be removed");
            System.out.println("[7]:  List of Car Ids");
            System.out.println("[8]:  Maintain a car");
            System.out.println("[9]:  Return a car from repair");
            System.out.println("[10]: Sell a car");
            System.out.println("[11]: Retire a car");
            System.out.println("[12]: View Customers");
            System.out.println("[13]: Return to welcome page");
            int answer = Integer.valueOf(this.scanner.nextLine());
            if(answer==13){
                break;
            }
            if(answer==0){
                registerCarPage();
            }else if(answer==1){
                this.carManag.viewcars();
            }else if(answer==2){
                this.carManag.viewAvailableCars();
            }else if(answer==3){
                this.carManag.viewUnAvailableCars();
            }else if(answer==4){
                this.carManag.viewRentedCars();
            }else if(answer==5){
                this.carManag.viewUnderRepairCars();
            }else if(answer==6){
                this.shouldBeRemovedCarsPage();
            }else if(answer==7){
                this.carManag.listOfCarsAndId();
            }else if(answer==8){
                this.maintainCarPage();
            }else if(answer==9){
                this.returnCarPage();
            }else if(answer==10){
                this.sellCarPage();
            }else if(answer==11){
                this.retireCarPage();
            }else{
                this.custManag.sortCustomersByAmountSpent();
            }
        }

    }

    public void registerCarPage(){
        System.out.print("Enter car model: ");
        String model = this.scanner.nextLine();
        System.out.print("\tEnter car supplier: ");
        String supplier = this.scanner.nextLine();
        System.out.println("");
        Car car = new Car(model, supplier);
        this.carManag.registerNewCar(model, supplier);
        System.out.print("Enter car rent price per day: ");
        double price = Double.valueOf(this.scanner.nextLine());
        System.out.print("\tEnter car mileage in miles: ");
        double mileage = Double.valueOf(this.scanner.nextLine());
        this.carManag.setPriceMileage(car.getCarId(), price, mileage);
        System.out.println("Enter the car size: \t[S]: Small\t[M]: Medium\t[L]: Large");
        String answer = this.scanner.nextLine();
        if(answer.equalsIgnoreCase("M")){
            car.setCarSize(CarSize.MEDIUM);
        }else if(answer.equalsIgnoreCase("L")){
            car.setCarSize(CarSize.LARGE);
        }else{
            car.setCarSize(CarSize.SMALL);
        }
        System.out.println("");
        System.out.print("Enter the number of seats: ");
        car.setNumberOfSeats(Integer.valueOf(this.scanner.nextLine()));
        System.out.print("\tEnter the number of small bags: ");
        car.setNumberOfSmallBags(Integer.valueOf(this.scanner.nextLine()));
        System.out.print("\tEnter the number of large bags: ");
        car.setNumberOfLargeBags(Integer.valueOf(this.scanner.nextLine()));
        System.out.println("Enter the car category: \t[E]: Economy\t[S]: SUV\t[P]: People Carrier\t[D]: Standard\t[T]: Estate\t[C]: Convertible\t[L]: Luxury");
        String category = this.scanner.nextLine();
        if(category.equalsIgnoreCase("E")){
            car.setCategory(CarCategory.ECONOMY);
        }else if(category.equalsIgnoreCase("S")){
            car.setCategory(CarCategory.SUV);
        }else if(category.equalsIgnoreCase("P")){
            car.setCategory(CarCategory.CARRIER);
        }else if(category.equalsIgnoreCase("D")){
            car.setCategory(CarCategory.STANDARD);
        }else if(category.equalsIgnoreCase("T")){
            car.setCategory(CarCategory.ESTATE);
        }else if(category.equalsIgnoreCase("C")){
            car.setCategory(CarCategory.CONVERTIBLE);
        }else{
            car.setCategory(CarCategory.LUXURY);
        }
        System.out.println("Enter the car supplier rating: \t[E]: Excellent\t[G]: Good\t[S]: Satisfactory\t[P]: Poor");
        String rating = this.scanner.nextLine();
        if(rating.equalsIgnoreCase("E")){
            car.setRating(CarSupplierRating.EXCELLENT);
        }else if(rating.equalsIgnoreCase("G")){
            car.setRating(CarSupplierRating.GOOD);
        }else if(rating.equalsIgnoreCase("S")){
            car.setRating(CarSupplierRating.SATISFACTORY);
        }else{
            car.setRating(CarSupplierRating.POOR);
        }
        System.out.println("Enter the car fuel policy: \t[F]: Full/Full\t[E]: Full/Empty");
        String policy = this.scanner.nextLine();
        if(policy.equalsIgnoreCase("E")){
            car.setFuelPolicy(CarFuelPolicy.FULLEMPTY);
        }else{
            car.setFuelPolicy(CarFuelPolicy.FULLFULL);
        }
        System.out.println("Enter the car insurance details");
        car.setInsuranceDetails(this.scanner.nextLine());
    }

    public void maintainCarPage(){
        System.out.println("Enter the car Id: ");
        String input = this.scanner.nextLine();
        while(!input.matches(uuidPattern)){
            System.out.println("Invalid UUID format. please enter the id again. Here's the list of IDs");
            this.carManag.listOfCarsAndId();
            input=scanner.nextLine();
        }
        UUID id = UUID.fromString(input);
        this.carManag.maintain_a_Car(id);
    }

    public void sellCarPage(){
        System.out.println("Enter the car Id: ");
        String input = this.scanner.nextLine();
        while(!input.matches(uuidPattern)){
            System.out.println("Invalid UUID format. please enter the id again. Here's the list of IDs");
            this.carManag.listOfCarsAndId();
            input=scanner.nextLine();
        }
        UUID id = UUID.fromString(input);
        this.carManag.sellTheCar(id);
    }

    public void retireCarPage(){
        System.out.println("Enter the car Id: ");
        String input = this.scanner.nextLine();
        while(!input.matches(uuidPattern)){
            System.out.println("Invalid UUID format. please enter the id again. Here's the list of IDs");
            this.carManag.listOfCarsAndId();
            input=scanner.nextLine();
        }
        UUID id = UUID.fromString(input);
        this.carManag.retireTheCar(id);
    }

    public void shouldBeRemovedCarsPage(){
        this.carManag.viewCarsThatShouldBeRemoved();
        System.out.println("Do you want to remove a car?\t[Y]: Yes");
        String answer = scanner.nextLine();
        if(answer.equals("Y")){
            System.out.println("Enter the car id: ");
            String input = scanner.nextLine();
            while(!input.matches(uuidPattern)){
                System.out.println("Invalid id, Try again!");
                input=scanner.nextLine();
            }
            this.carManag.removeCar(UUID.fromString(input));
        }
    }

    public void returnCarPage(){
        System.out.println("Enter the car Id: ");
        String input = this.scanner.nextLine();
        while(!input.matches(uuidPattern)){
            System.out.println("Invalid UUID format. please enter the id again. Here's the list of IDs");
            this.carManag.listOfCarsAndId();
            input=scanner.nextLine();
        }
        UUID id = UUID.fromString(input);
        this.carManag.returnCarFromrepair(id);
    }

    public void giveOptionsForUser(Customer customer){
        System.out.println("Welcome to our car rental collection!");
        while(true){
            System.out.println("[0]: Set Contact Info");
            System.out.println("[1]: View Available Cars");
            System.out.println("[2]: Book a Car");
            System.out.println("[3]: Return a Car");
            System.out.println("[4]: Cancel car rent");
            System.out.println("[5]: View Booked Cars");
            System.out.println("[6]: View Rental History");
            System.out.println("[7]: Filter Cars");
            System.out.println("[8]: Sort Cars");
            System.out.println("[9]: Return to Home page");
            int answer = Integer.valueOf(this.scanner.nextLine());
            if(answer==9){
                break;
            }
            if(answer==0){
                this.setContactPage(customer);
            }else if(answer==1){
                this.carManag.viewAvailableCars();
            }else if(answer==2){
                this.bookCarPage(customer);
            }else if(answer==3){
                this.returnCarPage();
            }else if(answer==4){
                this.cancelRentPage(customer);
            }else if(answer==5){
                customer.bookedCars();
            }else if(answer==6){
                customer.viewRentalHistory();
            }else if(answer==7){
                this.filterCarsPage();
            }else{
                this.sortCarsPage();
            }
        }
    }

    public void setContactPage(Customer customer){
        System.out.println("Enter contact details here below:");
        String contact =this.scanner.nextLine();
        while(contact.isBlank()){
            System.out.println("You didn't enter anything. Contacts Informations are required please, try again!");
            contact = this.scanner.nextLine();
        }
        customer.setContactInfo(contact);
    }

    public void bookCarPage(Customer customer){
        System.out.print("Enter the car model name: ");
        String model = this.scanner.nextLine();
        while(this.carManag.getCarForModel(model)==null){
            System.out.println("The car model you entered is mispronouced, here's a list of car model names that you may find helpful");
            this.carManag.printCarModels();
            model = this.scanner.nextLine();
        }
        System.out.print("Enter the start date: \tYear: ");
        int year = Integer.valueOf(this.scanner.nextLine());
        System.out.print("\tMonth: ");
        int month = Integer.valueOf(this.scanner.nextLine());
        System.out.println("\tDay: ");
        int day = Integer.valueOf(this.scanner.nextLine());
        LocalDate startDate = LocalDate.of(year, month, day);
        System.out.println("");
        System.out.print("Enter the end date: \tYear: ");
        int y = Integer.valueOf(this.scanner.nextLine());
        System.out.print("\tMonth: ");
        int m = Integer.valueOf(this.scanner.nextLine());
        System.out.println("\tDay: ");
        int d = Integer.valueOf(this.scanner.nextLine());
        LocalDate endDate = LocalDate.of(y, m, d);
        customer.bookCar(model, startDate, endDate);
    }

    public void custReturnCarPage(Customer customer){
        System.out.println("Enter the car model name: ");
        String model = this.scanner.nextLine();
        while(this.carManag.getCarForModel(model)==null){
            System.out.println("The car model you entered is mispronouced, here's a list of car model names that you may find helpful");
            this.carManag.printCarModels();
            model = this.scanner.nextLine();
        }
        System.out.println("Enter how many miles you drove in the rent period: ");
        double mileage = Double.valueOf(this.scanner.nextLine());
        customer.returnCar(model, mileage);
    }

    public void cancelRentPage(Customer customer){
        System.out.println("Enter the car model name: ");
        String model = this.scanner.nextLine();
        while(this.carManag.getCarForModel(model)==null){
            System.out.println("The car model you entered is mispronouced, here's a list of car model names that you may find helpful");
            this.carManag.printCarModels();
            model = this.scanner.nextLine();
        }
        System.out.print("Enter the start date: \tYear: ");
        int year = Integer.valueOf(this.scanner.nextLine());
        System.out.print("\tMonth: ");
        int month = Integer.valueOf(this.scanner.nextLine());
        System.out.println("\tDay: ");
        int day = Integer.valueOf(this.scanner.nextLine());
        LocalDate startDate = LocalDate.of(year, month, day);
        customer.cancelRent(this.carManag.getCarForModel(model), startDate);
    }

    public void filterCarsPage(){
        while(true){
            System.out.println("[0]: Filter cars by price:");
            System.out.println("[1]: Filter cars by mileage");
            System.out.println("[2]: Filter cars by numbers of seats");
            System.out.println("[3]: Filter cars by number of small bags");
            System.out.println("[4]: Filter cars by number of large bags");
            System.out.println("[5]: Filter cars by category");
            System.out.println("[6]: Filter cars by fuel policy");
            System.out.println("[7]: Filter cars by supplier rating");
            System.out.println("[8]: Filter cars by size");
            System.out.println("[9]: Return ot menu");
            int answer = Integer.valueOf(this.scanner.nextLine());
            if(answer==9){
                break;
            }
            if(answer==0){
                this.filterByPricePage();
            }else if(answer==1){
                this.filterByMileagePage();
            }else if(answer==2){
                this.filterBySeatsPage();
            }else if(answer==3){
                this.filterBySmallPage();
            }else if(answer==4){
                this.filterByLargePage();
            }else if(answer==5){
                this.filterByCategoryPage();
            }else if(answer==6){
                this.filterByFuelPolicyPage();
            }else if(answer==7){
                this.filterByRatingPage();
            }else{
                this.filterBySizePage();
            }
        }

    }

    public void filterByPricePage(){
        System.out.print("Enter the minimum price in dollars: ");
        double minPrice = Double.valueOf(this.scanner.nextLine());
        System.out.println("\tEnter the maximum price in dollars: ");
        double maxPrice = Double.valueOf(this.scanner.nextLine());
        this.carManag.filterCarsByPrice(minPrice, maxPrice);
    }

    public void filterByMileagePage(){
        System.out.print("Enter the minimum mileage in miles: ");
        double minMiles = Double.valueOf(this.scanner.nextLine());
        System.out.println("\tEnter the maximum mileage in miles: ");
        double maxMiles = Double.valueOf(this.scanner.nextLine());
        this.carManag.filterCarsByMileage(minMiles, maxMiles);
    }

    public void filterBySeatsPage(){
        System.out.println("Enter the minimum number of seats: ");
        int seats = Integer.valueOf(this.scanner.nextLine());
        this.carManag.filterCarsBySeats(seats);
    }

    public void filterBySmallPage(){
        System.out.println("Enter the minimum number of small bags: ");
        int smallBags = Integer.valueOf(this.scanner.nextLine());
        this.carManag.filterCarsByNumberOfSmallBags(smallBags);
    }

    public void filterByLargePage(){
        System.out.println("Enter the minimum number of large bags: ");
        int largeBags = Integer.valueOf(this.scanner.nextLine());
        this.carManag.filterCarsByNumberOfLargeBags(largeBags);
    }

    public void filterByCategoryPage(){
        System.out.println("Enter the car category: \t[E]: Economy\t[S]: SUV\t[P]: People Carrier\t[D]: Standard\t[T]: Estate\t[C]: Convertible\t[L]: Luxury");
        String category = this.scanner.nextLine();
        if(category.equalsIgnoreCase("E")){
            this.carManag.filterCarsByCategory(CarCategory.ECONOMY);
        }else if(category.equalsIgnoreCase("S")){
            this.carManag.filterCarsByCategory(CarCategory.SUV);
        }else if(category.equalsIgnoreCase("P")){
            this.carManag.filterCarsByCategory(CarCategory.CARRIER);
        }else if(category.equalsIgnoreCase("D")){
            this.carManag.filterCarsByCategory(CarCategory.STANDARD);
        }else if(category.equalsIgnoreCase("T")){
            this.carManag.filterCarsByCategory(CarCategory.ESTATE);
        }else if(category.equalsIgnoreCase("C")){
            this.carManag.filterCarsByCategory(CarCategory.CONVERTIBLE);
        }else{
            this.carManag.filterCarsByCategory(CarCategory.LUXURY);
        }
    }

    public void filterBySizePage(){
        System.out.println("Enter the car size: \t[S]: Small\t[M]: Medium\t[L]: Large");
        String answer = this.scanner.nextLine();
        if(answer.equalsIgnoreCase("M")){
            this.carManag.filterCarsBySize(CarSize.MEDIUM);
        }else if(answer.equalsIgnoreCase("L")){
            this.carManag.filterCarsBySize(CarSize.LARGE);
        }else{
            this.carManag.filterCarsBySize(CarSize.SMALL);
        }
    }

    public void filterByRatingPage(){
        System.out.println("Enter the car supplier rating: \t[E]: Excellent\t[G]: Good\t[S]: Satisfactory\t[P]: Poor");
        String rating = this.scanner.nextLine();
        if(rating.equalsIgnoreCase("E")){
            this.carManag.filterCarsByRating(CarSupplierRating.EXCELLENT);
        }else if(rating.equalsIgnoreCase("G")){
            this.carManag.filterCarsByRating(CarSupplierRating.GOOD);
        }else if(rating.equalsIgnoreCase("S")){
            this.carManag.filterCarsByRating(CarSupplierRating.SATISFACTORY);
        }else{
            this.carManag.filterCarsByRating(CarSupplierRating.POOR);
        }
    }

    public void filterByFuelPolicyPage(){
        System.out.println("Enter the car fuel policy: \t[F]: Full/Full\t[E]: Full/Empty");
        String policy = this.scanner.nextLine();
        if(policy.equalsIgnoreCase("E")){
            this.carManag.filterCarsByFuelPolicy(CarFuelPolicy.FULLEMPTY);
        }else{
            this.carManag.filterCarsByFuelPolicy(CarFuelPolicy.FULLFULL);
        }
    }

    public void sortCarsPage(){
        while(true){
            System.out.println("[0]: Sort cars by ascending price");
            System.out.println("[1]: Sort cars by descending price");
            System.out.println("[2]: Sort cars by ascending mileage");
            System.out.println("[3]: Sort cars by descending mileage");
            System.out.println("[4]: Return to menu");
            int answer = Integer.valueOf(this.scanner.nextLine());
            if(answer==4){
                break;
            }
            if(answer==0){
                this.carManag.sortCarsByAscendingPrice();
            }else if(answer==1){
                this.carManag.sortCarsByDescendingPrice();
            }else if(answer==2){
                this.carManag.sortCarsByAscendingMileage();
            }else{
                this.carManag.sortCarsByDescendingMileage();
            }
        }
    }

    public boolean isValidEmail(String email){
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
