/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package logic;
import java.util.*;

import domain.User;

public class ManageUsers {
    private List<User> users;
    private Map<String, String> map;

    public ManageUsers(){
        this.users=new ArrayList<>();
        this.map=new HashMap<>();
    }

    public void nothingIfEmpty(){
        if(this.map.isEmpty() && this.users.isEmpty()){
            return;
        }
    }

    public void registerUser(String name, String code, String email){
        this.map.putIfAbsent(name, code);
        User user = new User(name, code, email);
        if(!this.users.contains(user)){
            this.users.add(user);
        }else{
            return;
        }
    }

    public String getPasswordFor(String name){
        nothingIfEmpty();
        return this.map.get(name);
    }

    public boolean isRegistrated(String name){
        return this.users.contains(new User(name, null, null));
    }
}
