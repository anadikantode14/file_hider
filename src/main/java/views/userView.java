package views;

import dao.dataDAO;
import model.data;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class userView {
    private String email;
    userView(String email){
        this.email=email;
    }
    public void home(){
        do{
            System.out.println();
            System.out.println("---Welcome "+ this.email+" ---");
            System.out.println("Press 1 to show hidden files");
            System.out.println("Press 2 to hide new file");
            System.out.println("Press 3 to unhide a file");
            System.out.println("Press 0 to exit");
            Scanner sc = new Scanner(System.in);
            int ch = Integer.parseInt(sc.nextLine());
            switch(ch){
                case 1 -> {
                    try {
                        List<data> files = dataDAO.getAllFiles(this.email);
                        System.out.println("ID - File Name");
                        for(data file: files){
                            System.out.println(file.getId()+" - "+file.getFilename());
                        }
                    }catch (SQLException e){
                        e.printStackTrace();
                    }
                }
                case 2 -> {
                    System.out.print("Enter the file Path: ");
                    String path = sc.nextLine();
                    File f = new File(path);
                    data file = new data(0, f.getName(), path, this.email);
                    try{

                        dataDAO.hideFile(file);
                    }catch (SQLException e){
                        e.printStackTrace();
                    }catch (IOException e){
                        e.printStackTrace();
                    }
                }
                case 3 -> {
                    List<data> files = null;
                    try {
                        files = dataDAO.getAllFiles(this.email);

                    System.out.println("ID - File Name");
                    for(data file: files){
                        System.out.println(file.getId()+" - "+file.getFilename());
                    }
                    System.out.print("Enter the id of file to unhide: ");
                    int id = Integer.parseInt(sc.nextLine());
                    boolean isValidID = false;
                    for(data file: files){
                        if(file.getId() == id){
                            isValidID=true;
                            break;
                        }
                    }
                    if(isValidID){
                        dataDAO.unhide(id);
                    }else{
                        System.out.println("Wrong ID");
                    }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    catch (IOException e){
                        e.printStackTrace();
                    }
                }
                case 0 -> {
                    System.exit(0);
                }
            }
        }while(true);
    }
}
