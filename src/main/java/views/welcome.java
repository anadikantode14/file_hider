package views;

import dao.userDAO;
import model.user;
import service.generateOTP;
import service.sendOTPService;
import service.userService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.Scanner;

public class welcome {
    public static void welcomeScreen(){
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("-----WELCOME TO THE APP-----");
        System.out.println("Press 1 to login");
        System.out.println("Press 2 to signup");
        System.out.println("Press 0 to exit");
        int choice = 0;
        try{
            choice = Integer.parseInt(br.readLine());
        }
        catch (IOException e){
            e.printStackTrace();
        }
        switch (choice){
            case 1 -> login();
            case 2 -> signup();
            case 0 -> System.exit(0);
        }
    }

    private static void login() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter Email: ");
        String email = sc.nextLine();
        try{
            if(userDAO.isExists(email)) {
                String genOTP = generateOTP.getOTP();
                sendOTPService.sendOTP(email, genOTP);
                System.out.print("Enter the OTP: ");
                String otp = sc.nextLine();
                if(otp.equals(genOTP)){
                    new userView(email).home();
                }else{
                    System.out.println("Wrong OTP");
                }
            }else{
                System.out.println("User Not Found!");
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    private static void signup(){
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter Name: ");
        String name = sc.nextLine();
        System.out.print("Enter Email: ");
        String email = sc.nextLine();
        String genOTP = generateOTP.getOTP();
        sendOTPService.sendOTP(email, genOTP);
        System.out.print("Enter the OTP: ");
        String otp = sc.nextLine();
        if(otp.equals(genOTP)){
            user user = new user(name, email);
            Integer response = userService.saveUser(user);
            if (response == null) {
                System.out.println("Error occurred while saving user.");
            } else {
                switch (response) {
                    case 0 -> System.out.println("User Already Exists.");
                    case 1 -> System.out.println("User Registered.");
                    default -> System.out.println("Unexpected response.");
                }
            }
        }else{
            System.out.println("Wrong OTP");
        }
    }
}
