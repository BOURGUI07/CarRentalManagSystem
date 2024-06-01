/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package logic;
import java.util.*;

public class ManageCustomers {
    private List<Customer> customers;

    public ManageCustomers(){
        this.customers=new ArrayList<>();
    }

    public void addCustomer(Customer customer){
        this.customers.add(customer);
    }

    public void returnNothingIfCustomersEmpty(){
        if(this.customers.isEmpty()){
            return;
        }
    }

    public void sortCustomersByAmountSpent(){
        returnNothingIfCustomersEmpty();
        this.customers.stream().sorted().forEach(cust -> System.out.println(cust));
    }


}
