package coffeemachine;

public class CoffeeType {

    private int id;
    private String name;
    private int water;          // ml
    private int milk;           // ml
    private int coffeeBeans;    // g
    private int price;          // $
    private final int cup;

    public CoffeeType(int id, String name, int water, int milk, int coffeeBeans, int price){
        this.id = id;
        this.name = name;
        this.water = water;
        this.milk = milk;
        this.coffeeBeans = coffeeBeans;
        this.price = price;
        this.cup = 1;
    }

    public String getIdAsString() {
        return String.valueOf(id);
    }

    public String getName() {
        return this.name;
    }

    public int getWater() {
        return this.water;
    }

    public int getMilk() {
        return this.milk;
    }

    public int getCoffeeBeans() {
        return this.coffeeBeans;
    }

    public int getPrice() {
        return this.price;
    }

    public int getCup() {
        return this.cup;
    }
}
