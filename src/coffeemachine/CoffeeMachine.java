package coffeemachine;

// CM initial state:
// 400 ml of water,
// 540 ml of milk,
// 120 g of coffee beans,
// 9 disposable cups,
// $550 in cash

// 1 - espresso - 250 ml of water, 16 g of coffee beans, costs $4
// 2 - latte - 350 ml of water, 75 ml of milk, 20 g of coffee beans, costs $7
// 3 - cappuccino - 200 ml of water, 100 ml of milk, 12 g of coffee beans, costs $6

import java.io.IOException;

public class CoffeeMachine {

    private final String ADMIN_USERNAME = "admin";
    private final String ADMIN_PASSWORD = "admin123";
    private final String ROLE_USER = "USER";
    private final String ROLE_ADMIN = "ADMIN";

    private int water;
    private int milk;
    private int coffeeBeans;
    private int disposableCups;
    private int amountOfMoney;

    private CoffeeType[] coffeeTypes;
    private String currentUser;
    private CoffeeMachinePersistentData persistentData;

    public CoffeeMachine() throws IOException {
        persistentData = new CoffeeMachinePersistentData();
        if (persistentData != null) {
            initMachineStateFromFile();
        } else {
            initMachineState();
        }
        coffeeTypes = new CoffeeType[]{
                new CoffeeType(1, "espresso", 250, 0, 16, 4),
                new CoffeeType(2, "latte", 350, 75, 20, 7),
                new CoffeeType(3, "cappuccino", 200, 100, 12, 6)
        };
        currentUser = ROLE_USER;
    }

    private void initMachineState() {
        this.water = 400;
        this.milk = 540;
        this.coffeeBeans = 120;
        this.disposableCups = 9;
        this.amountOfMoney = 550;
    }

    private void initMachineStateFromFile() throws IOException {
        int[] loadMachineState = persistentData.loadData();
        this.water = loadMachineState[0];
        this.milk = loadMachineState[1];
        this.coffeeBeans = loadMachineState[2];
        this.disposableCups = loadMachineState[3];
        this.amountOfMoney = loadMachineState[4];
    }

    private boolean canMakeCoffee(CoffeeType coffeeType) {
        return water >= coffeeType.getWater() && milk >= coffeeType.getMilk() &&
                coffeeBeans >= coffeeType.getCoffeeBeans() && disposableCups >= coffeeType.getCup();
    }

    private String canMakeCoffeeMessage(CoffeeType coffeeType) {
        if (this.water < coffeeType.getWater()) return "water";
        if (this.milk < coffeeType.getMilk()) return "milk";
        if (this.coffeeBeans < coffeeType.getCoffeeBeans()) return "coffee beans";
        if (this.disposableCups < coffeeType.getCup()) return "disposable cups";
        return "";
    }

    private void makeCoffee(CoffeeType coffeeType) {
        this.amountOfMoney += coffeeType.getPrice();
        this.water -= coffeeType.getWater();
        this.milk -= coffeeType.getMilk();
        this.coffeeBeans -= coffeeType.getCoffeeBeans();
        this.disposableCups -= coffeeType.getCup();
    }

    private CoffeeType coffeeSelection(String selection) {
        CoffeeType coffeeType = null;
        for (CoffeeType ct : coffeeTypes) {
            if (ct.getName().equals(selection) || ct.getIdAsString().equals(selection)) {
                coffeeType = ct;
            }
        }
        return coffeeType;
    }

    public String buy(String action) {
        CoffeeType coffeeType = coffeeSelection(action);
        String message = "";
        if (canMakeCoffee(coffeeType)) {
            message = "\nI have enough resources, making you a coffee!";
            makeCoffee(coffeeType);
        } else {
            message = "\nSorry, not enough " + canMakeCoffeeMessage(coffeeType) + "!";
        }
        return message;
    }

    public boolean login(String username, String password) {
        if (username.equals(ADMIN_USERNAME) && password.equals(ADMIN_PASSWORD)) {
            currentUser = ROLE_ADMIN;
            return true;
        }
        return false;
    }

    public void logout() {
        if (currentUser.equals(ROLE_ADMIN))
            currentUser = ROLE_USER;
    }

    public int take() {
        if (currentUser.equals(ROLE_ADMIN)) {
            int money = this.amountOfMoney;
            this.amountOfMoney = 0;
            return money;
        }
        return 0;
    }

    public void fill(int water, int milk, int coffeeBeans, int disposableCups) {
        if (currentUser.equals(ROLE_ADMIN)) {
            this.water += water;
            this.milk += milk;
            this.coffeeBeans += coffeeBeans;
            this.disposableCups += disposableCups;
        }
    }

    public int[] remaining() {
        if (currentUser.equals(ROLE_ADMIN)) {
            int[] coffeeMachineStatus = { this.water, this.milk, this.coffeeBeans, this.disposableCups, this.amountOfMoney };
            return coffeeMachineStatus;
        }
        return null;
    }

    public void exit() throws IOException {
        int[] machineState = {this.water, this.milk, this.coffeeBeans, this.disposableCups, this.amountOfMoney};
        String[] credentials = {this.ADMIN_USERNAME, this.ADMIN_PASSWORD};
        CoffeeType[] machineCoffeeTypes = coffeeTypes;
        persistentData.saveData(machineState, credentials, machineCoffeeTypes);
    }
}
