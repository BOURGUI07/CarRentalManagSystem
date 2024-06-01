/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package domain;

public class User {
    private String username;
    private String password;
    private String email;

    public User(String name, String password, String email){
        if(name==null){
            throw new IllegalArgumentException();
        }
        this.username=name;
        this.password=password;
        this.email=email;
    }

    public String getUserName(){
        return this.username;
    }

    public String getPassword(){
        return this.password;
    }

    public String getEmail(){
        return this.email;
    }

    public boolean equals(Object user){
        if(this==user){
            return true;
        }
        if(!(user instanceof User)){
            return false;
        }
        User castedUser = (User) user;
        if(this.username.equals(castedUser.getUserName())){
            return true;
        }
        return false;
    }
    
    public int hashCode(){
        return this.username.hashCode();
    }
}
