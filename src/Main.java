import coffeemachine.CoffeeMachine;

import java.io.IOException;
import java.util.Scanner;

public class Main {

    private static final String STATE_MAIN_MENU = "MAIN_MENU";
    private static final String STATE_BUY = "BUY";
    private static final String STATE_LOGIN = "LOGIN";
    private static final String STATE_EXIT = "EXIT";

    private static final String STATE_ADMIN_MENU = "ADMIN_MENU";
    private static final String STATE_FILL = "FILL";
    private static final String STATE_TAKE = "TAKE";
    private static final String STATE_REMAINING = "REMAINING";
    private static final String STATE_LOGOUT = "LOGOUT";

    private static final String STATE_EXIT_APP = "EXIT_APP";

    private static Scanner scanner;
    private static String action;
    private static CoffeeMachine coffeeMachine;
    private static String state;

    private static int loginAttempts = 0;

    public static void main(String[] args) throws IOException {

        coffeeMachine = new CoffeeMachine();
        scanner = new Scanner(System.in);
        action = "";
        state = STATE_MAIN_MENU;

        while (!state.equals(STATE_EXIT_APP)) {
            switch (state) {
                case STATE_MAIN_MENU -> mainMenu();
                case STATE_BUY -> buyMenu();
                case STATE_LOGIN -> loginMenu();
                case STATE_ADMIN_MENU -> adminMenu();
                case STATE_FILL -> fillMenu();
                case STATE_TAKE -> takeMenu();
                case STATE_REMAINING -> remainingMenu();
                case STATE_LOGOUT -> logoutMenu();
                case STATE_EXIT -> exitMenu();
                default -> System.out.println("State not found");
            }
        }
    }

    private static void mainMenu() {
        action = "";
        // main menu
        System.out.println("\nMain Menu");
        System.out.println(" 1. - buy");
        System.out.println(" 2. - login");
        System.out.println(" 0. - exit");
        System.out.print("Enter action: ");
        // selection
        action = scanner.nextLine();
        switch (action) {
            case "buy", "1":
                state = STATE_BUY;
                break;
            case "login", "2":
                state = STATE_LOGIN;
                break;
            case "exit", "0":
                state = STATE_EXIT;
                break;
            default:
                System.out.println("Action not found");
        }
    }

    private static void buyMenu() {
        action = "";
        // buy menu
        System.out.println("\nWhat do you want to buy?");
        System.out.println(" 1. - espresso");
        System.out.println(" 2. - latte");
        System.out.println(" 3. - cappuccino");
        System.out.println(" 0. - back - to main menu");
        System.out.print("Enter action: ");
        // selection
        action = scanner.nextLine().trim().toLowerCase();
        switch (action) {
            case "espresso", "1", "latte", "2", "cappuccino", "3":
                System.out.println(coffeeMachine.buy(action));
                state = STATE_MAIN_MENU;
                break;
            case "back":
                state = STATE_MAIN_MENU;
                break;
            default:
                System.out.println("Action not found");
        }
    }

    private static void loginMenu() {

        System.out.println("\nLogin Menu");
        System.out.print(" Enter username: ");
        String username = scanner.nextLine();
        System.out.print(" Enter password: ");
        String password = scanner.nextLine();
        if (coffeeMachine.login(username, password)) {
            state = STATE_ADMIN_MENU;
        } else {
            System.out.println("Wrong username or password");
            loginAttempts++;
            if (loginAttempts == 3) {
                state = STATE_MAIN_MENU;
                loginAttempts = 0;
            }
        }
    }

    private static void adminMenu() {
        action = "";
        // admin menu
        System.out.println("\n*** Admin menu ***");
        System.out.println(" 1. - fill");
        System.out.println(" 2. - take");
        System.out.println(" 3. - remaining");
        System.out.println(" 0. - logout");
        System.out.print("Enter action: ");
        // selection
        action = scanner.nextLine().trim().toLowerCase();
        switch (action) {
            case "fill", "1":
                state = STATE_FILL;
                break;
            case "take", "2":
                state = STATE_TAKE;
                break;
            case "remaining", "3":
                state = STATE_REMAINING;
                break;
            case "logout", "0":
                state = STATE_LOGOUT;
                break;
            default:
                System.out.println("\nAction not found");
        }
    }


    private static void fillMenu() {
        System.out.println("\nWrite how many ml of water you want to add:");
        int water = scanner.nextInt();
        System.out.println("Write how many ml of milk you want to add:");
        int milk = scanner.nextInt();
        System.out.println("Write how many grams of coffee beans you want to add:");
        int coffeeBeans = scanner.nextInt();
        System.out.println("Write how many disposable cups you want to add:");
        int disposableCups = scanner.nextInt();
        scanner.nextLine();
        coffeeMachine.fill(water, milk, coffeeBeans, disposableCups);
        state = STATE_ADMIN_MENU;
    }

    private static void takeMenu() {
        System.out.println("\nI gave you $" + coffeeMachine.take());
        state = STATE_ADMIN_MENU;
    }

    private static void remainingMenu() {
        int[] coffeeMachineStatus = coffeeMachine.remaining();
        System.out.println("\nThe coffee machine has:");
        System.out.println(coffeeMachineStatus[0] + " ml of water");
        System.out.println(coffeeMachineStatus[1] + " ml of milk");
        System.out.println(coffeeMachineStatus[2] + " g of coffee beans");
        System.out.println(coffeeMachineStatus[3] + " disposable cups");
        System.out.println("$" + coffeeMachineStatus[4] + " of money");
        state = STATE_ADMIN_MENU;
    }

    private static void logoutMenu() {
        coffeeMachine.logout();
        System.out.println("\nYou are logout");
        state = STATE_MAIN_MENU;
    }

    private static void exitMenu() throws IOException {
        // save resources to db
        coffeeMachine.exit();
        System.out.println("\nBye!");
        state = STATE_EXIT_APP;
    }
}
