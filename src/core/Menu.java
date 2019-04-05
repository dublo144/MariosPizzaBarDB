package core;

/**
 * @author Alexander 
 */
import Storage.Storage;
import UI.UI;
import java.util.ArrayList;

public class Menu {

    private UI ui;
    private ArrayList<Pizza> menu;
    private final Storage storage;

    public Menu(UI ui, Storage storage) {
        this.ui = ui;
        this.storage = storage;
        this.menu = new ArrayList();
        menuFromFile();
    }

    public ArrayList<Pizza> getMenu() {
        return menu;
    }

    private void menuFromFile() {
        String[] splitLine;
        Pizza pizza;
        for (String pizzaFile : storage.readFromFile(Storage.MENU_FILE)) {
            splitLine = pizzaFile.split(" ");
            pizza = new Pizza(Integer.parseInt(splitLine[0]), splitLine[1], splitLine[2], Double.parseDouble(splitLine[3]));
            menu.add(pizza);
        }
        sortPizza();
    }
    
    private int nextPizzaNumber(){
        int number = 1;
        for(int i = 0; i < menu.size(); i++){
            if(menu.get(i).getNumber() != number){
                return number;
            }
            number++;
        }
        return number;
    }
    
    public void newPizza(String[] userInput) {
        //String[] userInput = userInputNewPizza();
        Pizza pizza = new Pizza(nextPizzaNumber(), userInput[0], userInput[1], Double.parseDouble(userInput[2]));
        String newPizza = pizza.toString();
        storage.writeToFile(newPizza, Storage.MENU_FILE);
        menu.add(pizza);
        sortPizza();
    }

    public void removePizza(int pizzaNumber) {
        storage.removePizza(pizzaNumber);
        for (int i = 0; i < menu.size(); i++) {
            if (menu.get(i).getNumber() == pizzaNumber) {
                menu.remove(i);
                i = menu.size();
            }
        }
    }
    
    public Pizza getPizza(int pizzaNumber){
        for(Pizza pizza : menu){
            if(pizza.getNumber() == pizzaNumber)
                return pizza;
        }
        return null;
    }

    private void sortPizza() {
        Pizza temp;
        for (int i = 0; i < menu.size(); i++) {
            for (int j = i; j < menu.size(); j++) {
                if (menu.get(j).getNumber() < menu.get(i).getNumber()) {
                    temp = menu.get(i);
                    menu.set(i, menu.get(j));
                    menu.set(j, temp);
                }
            }
        }
    }

    public void printMenu() {
        ui.println("---------------------- Mario's Pizzabar ----------------------");
        for (Pizza pizza : menu) {
            ui.println(pizza + " kr.");
        }
    }

}
