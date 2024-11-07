package coffeemachine;

import java.io.*;
import java.util.Scanner;

public class CoffeeMachinePersistentData {

    private final String FILE_NAME = "docs/coffee_machine_status.txt";

    public void SaveData(int[] state, String[] credentials, CoffeeType[] coffeeTypes) throws IOException {
        FileWriter fileWriter = new FileWriter(FILE_NAME);

        fileWriter.write(state[0] + "; " + state[1] + "; " + state[2] + "; " + state[3] + "; " + state[4] + "\n");
        fileWriter.write(credentials[0] + "; " + credentials[1] + "\n\n");

        for (CoffeeType coffeeType : coffeeTypes) {
            fileWriter.write(coffeeType.getName() + "; " + coffeeType.getWater() + "; " + coffeeType.getMilk() + "; "
                    + coffeeType.getCoffeeBeans() + "; " + coffeeType.getPrice() + "\n");
        }
        fileWriter.close();
    }

    public int[] LoadData() throws IOException {

        Scanner scanner = new Scanner(new File(FILE_NAME));
        scanner.useDelimiter("; |\n");

        int counter = 0;
        int[] machineState = new int[5];
        while (scanner.hasNext() && counter < 5) {
            machineState[counter] = scanner.nextInt();
            counter++;
        }

        return machineState;
    }

}
