package view;

import entities.User;
import service.UserService;

import java.util.ArrayList;
import java.util.Scanner;

public class Menu {
    public void optionMenu(Scanner scanner, ArrayList<User> users, UserService userService){
        boolean isContinue = true;
        do {
            System.out.println("1-Đăng nhập");
            System.out.println("2-Dăng ký");
            System.out.println("Mời bạn lựa chọn: ");
            int select=Integer.parseInt(scanner.nextLine());
            switch (select){
                case 1:
                    isContinue = userService.inputLogin(scanner, users, userService);
                    break;
                case 2:
                    userService.inputRegister(scanner, users);
                    break;
            }
        }while (isContinue);
    }

}
