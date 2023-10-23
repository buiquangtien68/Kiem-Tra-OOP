package service;

import entities.User;
import view.Menu;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserService {
    public void login(Scanner scanner){

    }
    public void inputRegister(Scanner scanner, ArrayList<User> users){
        System.out.println("=======ĐĂNG KÝ=======");
        System.out.println("Mời bạn nhập username:");
        String username=scanner.nextLine();
        User user=new User();
        user.setUsername(username);
        if (checkUsername(username,users)){
            inputRegister(scanner,users);
        }else {
            System.out.println("Mời bạn nhập email:");
            String email = scanner.nextLine();
            if (checkEmail(email,users)){
                inputRegister(scanner, users);
            }else {
                user.setEmail(email);
                System.out.println("Mời bạn nhập password:");
                String password=scanner.nextLine();
                if(checkPassword(password)){
                    inputRegister(scanner,users);
                }else{
                    user.setPassword(password);
                    users.add(user);
                    System.out.println("Đăng ký thành công!!!");
                    System.out.println("Bạn có muốn nhập thêm không: ");
                    String select = scanner.nextLine();
                    if (select.equalsIgnoreCase("Y")) {
                        inputRegister(scanner, users);
                    }
                }
            }
        }
    }
    public boolean checkUsername(String userName, ArrayList<User> users){
        boolean isError =false;
        for (User userValue : users){
            if (userValue.getUsername().equals(userName)){
                isError=true;
                System.out.println("Username đã tồn tại");
                break;
            }
        }
        return isError;
    }
    public boolean checkEmail(String email, ArrayList<User> users){
        boolean isError =false;
        String regex = "^(.+)@(.+)$";
         if(email.matches(regex)){
            for (User userValue : users){
                if (userValue.getEmail().equals(email)){
                    isError = true;
                    System.out.println("Email này đã được sử dụng!");
                    break;
                }
            }
         }else{
             System.out.println("Email sai định dạng !");
             isError = true;
         }
        return isError;
    }

    public boolean checkPassword(String password){
        String regex = "^(?=.*[A-Z])(?=.*[.,-_;])[A-Za-z.,-_;]{7,15}$";
        boolean isError = false;
        if(!password.matches(regex)) {
            System.out.println("Mật khẩu sai định dạng");
            isError=true;
        }
        return isError;
    }
    public boolean inputLogin(Scanner scanner, ArrayList<User> users, UserService userService) {
        String selectLoop="";
        boolean isUserNameRight = false;
        boolean isContinue = true;
        do {
            System.out.println("=======ĐĂNG NHẬP=======");
            System.out.println("Mời bạn nhập username:");
            String username = scanner.nextLine();
            for (User userValue : users) {
                if (username.equals(userValue.getUsername())) {
                    System.out.println("Mời bạn nhập password:");
                    String password = scanner.nextLine();
                    isUserNameRight = true;
                    if (password.equals(userValue.getPassword())) {
                        System.out.println("Đăng nhập thành công!!!");
                        isContinue = subMenu(scanner,users,userValue,userService);
                        break;
                    } else {
                        System.out.println("Mật khẩu sai!");
                        System.out.println("Mời bạn chọn:");
                        System.out.println("1-Đăng nhập lại");
                        System.out.println("2-Quên mật khẩu");
                        System.out.println("Mời bạn chọn:");
                        int select = Integer.parseInt(scanner.nextLine());
                        switch (select) {
                            case 1:
                                isContinue = inputLogin(scanner, users,userService);
                                break;
                            case 2:
                                forgetPassword(scanner,users);
                                break;
                        }
                        break;
                    }
                }
            }
            if(!isUserNameRight){
                System.out.println("Kiểm tra lại username");
                System.out.println("Bạn có muốn nhập lại không(Y/N): ");
                selectLoop = scanner.nextLine();
            }
        }while (selectLoop.equalsIgnoreCase("Y"));
        return isContinue;
    }
    public void forgetPassword(Scanner scanner, ArrayList<User> users){
            boolean isEmailRight =false;
            System.out.println("Mời bạn nhập email:");
            String email= scanner.nextLine();
            for (User userValue : users){
                if (email.equals(userValue.getEmail())){
                    System.out.println("Mời bạn nhập lại password:");
                    String password= scanner.nextLine();
                    userValue.setPassword(password);
                    System.out.println("Đổi mật khẩu thành công!!!");
                    isEmailRight = true;
                    break;
                }
            }
            if (!isEmailRight){
                System.out.println("Kiểm tra lại email");
                System.out.println("Bạn có muốn nhập lại không (Y/N): ");
                String selectLoop = scanner.nextLine();
                if (selectLoop.equalsIgnoreCase("y")){
                    forgetPassword(scanner,users);
                }
            }
    }
    public boolean subMenu(Scanner scanner,ArrayList<User> users, User user, UserService userService){
        int select = 0;
        boolean isContinue = true;
        do {
            System.out.println("Chào mừng "+user.getUsername()+", bạn có thể thực hiện các công việc sau:");
            System.out.println("1 - Thay đổi username");
            System.out.println("2 - Thay đổi email");
            System.out.println("3 - Thay đổi mật khẩu");
            System.out.println("4 - Đăng xuất");
            System.out.println("0 - Thoát chương trình");
            System.out.println("Mời bạn lựa chọn:");
            select=Integer.parseInt(scanner.nextLine());
            switch (select){
                case 0:
                    isContinue=false;
                    return isContinue;
                case 1:
                    updateUsername(scanner,users,user);
                    break;
                case 2:
                    updateEmail(scanner,users,user);
                    break;
                case 3:
                    updatePassword(scanner,user);
                    break;
                case 4:
                    logOut(scanner,users,userService);
                    break;
            }
        }while (select!=0);
       return isContinue;
    }
    public void updateUsername(Scanner scanner,ArrayList<User> users, User user){
        System.out.println("Mời bạn nhập username mới:");
        String username = scanner.nextLine();
        boolean isError = checkUsername(username, users);
        if (!isError){
            user.setUsername(username);
        }
    }
    public void updateEmail(Scanner scanner,ArrayList<User> users, User user){
        System.out.println("Mời bạn nhập email mới:");
        String email = scanner.nextLine();
        boolean isError = checkEmail(email, users);
        if (!isError){
            user.setEmail(email);
        }
    }
    public void updatePassword(Scanner scanner, User user){
        System.out.println("Mời bạn nhập password mới:");
        String password= scanner.nextLine();
        boolean isError = checkPassword(password);
        if (!isError) {
            user.setPassword(password);
        }
    }
    public void logOut(Scanner scanner, ArrayList<User> users, UserService userService){
        Menu menu=new Menu();
        menu.optionMenu(scanner,users,userService);
    }
}
