/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Main;
import ui.*;
import java.util.*;

public class MainProgram {
    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        UserInterface userIn = new UserInterface(scanner);
        userIn.welcomePage();
    }
}
