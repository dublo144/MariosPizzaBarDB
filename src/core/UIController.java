package core;

import Storage.FileStorage;
import Storage.Storage;
import UI.UI;
import java.time.LocalTime;
import java.util.ArrayList;
import toolbox.SortedQueue;

/**
 * @author Mads
 */
public class UIController {

    private final UI ui;
    private final OrderHandler orderHandler;
    private final Menu menuHandler;
    private final Storage storage;
    private final Statistics statistics;

    public UIController(UI ui) {
        this.ui = ui;
        storage = new FileStorage(ui);
        statistics = new Statistics(storage);
        menuHandler = new Menu(ui, storage);
        orderHandler = new OrderHandler(ui,storage,menuHandler);
    }

    public void startProgram() {
        int choice = 0;
        while (choice != 6) {
            ui.println("---------------------- Mario's Pizzabar ----------------------");
            ui.println("1) Bestilling");
            ui.println("2) Se menukort");
            ui.println("3) Adm. Pizza");
            ui.println("4) Omsætning");
            ui.println("5) Statistik");
            ui.println("6) Exit");
            try {
                choice = Integer.parseInt(ui.getInput());
                if (choice < 1 || choice > 6) {
                    throw new NumberFormatException();
                }
                switch (choice) {
                    case 1:
                        orders();
                        break;
                    case 2:
                        menuCard();
                        break;
                    case 3:
                        admPizza();
                        break;
                    case 4:
                        turnover();
                        break;
                    case 5:
                        statistics();
                        break;
                }

            } catch (NumberFormatException e) {
                ui.println("Ikke et valid input. Vælg mellem 1 - 6");
            }
        }
    }

    public void menuCard() {
        int choice = 0;
        while (choice != -1) {
            try {
                menuHandler.printMenu();
                ui.println("\nIndtast -1 for at gå tilbage");
                choice = Integer.parseInt(ui.getInput());
            } catch (NumberFormatException e) {
                ui.println("Ikke et valid input. Vælg -1");
            }
        }
    }

    private void orders() {
        int choice = 0;
        while (choice != 4) {
            ui.println("------------------ ADM. Bestillinger ------------------");
            ui.println("1) Se bestilling");
            ui.println("2) Opret bestilling");
            ui.println("3) Bestilling færdig");
            ui.println("4) Tilbage til hovedmenu");
            try {
                choice = Integer.parseInt(ui.getInput());
                if (choice < 1 || choice > 4) {
                    throw new NumberFormatException();
                }
                switch (choice) {
                    case 1:
                        for (Order order : orderHandler.loadOrdersFromFile()) {
                            ui.println(order.toString());
                        }
                        break;
                    case 2:
                        newOrder();
                        break;
                    case 3:
                        completeOrder();
                        break;
                }

            } catch (NumberFormatException e) {
                ui.println("Ikke et valid input. Vælg mellem 1 - 4");
            }
        }
    }

    public void newOrder() {
        Order order; 
        ArrayList<Pizza> pizza = selectPizzas();
        ui.println("Indtast tid for afhenting: (time:minutter)");
        String[] splits = ui.getInput().split(":");
        LocalTime time = LocalTime.of(Integer.parseInt(splits[0]),Integer.parseInt(splits[1]));
        order = new Order(orderHandler.nextOrderNumber(),pizza,time);
        orderHandler.writeOrderToFile(order);
        String[] printOrder = order.returnOrder();
        for(String foodItem : printOrder){
            ui.println(foodItem);
        }
    }
    
    public ArrayList<Pizza> selectPizzas() {
        int choice = 0;
        int menuSize = menuHandler.getMenu().size();
        ArrayList<Pizza> pizzas = new ArrayList();
        while (choice != -1) {
            menuHandler.printMenu();
            ui.println("\nVælg pizza eller -1 for at afslutte");
            try {
                choice = Integer.parseInt(ui.getInput());
                if (choice != -1) {
                    if (choice < menuSize + 1 && choice > 0) {
                        Pizza p = menuHandler.getPizza(choice);
                        pizzas.add(p);
                    } else {
                        throw new NumberFormatException();
                    }
                }
            } catch (NumberFormatException e) {
                ui.printf("Ikke et valid input. Vælg mellem 1 - %d%n", menuSize);
            }
        }
        return pizzas;
    }

    private void completeOrder() {
        int choice = 0;
        int[] orderNumbers = orderHandler.getOrderNumbersToMake();
        SortedQueue<Order> orders = orderHandler.loadOrdersFromFile(); 
        while (choice != -1) {
            ui.println("Vælg ordrenummer");
            ui.println("Indtast -1 for at afslutte ordre");
            try {
                for (Order order : orders) {
                    ui.println(order.toString());
                }
                choice = Integer.parseInt(ui.getInput());
                if (choice != -1) {
                    boolean orderExist = false;
                    for (int i = 0; i < orderNumbers.length; i++) {
                        if (choice == orderNumbers[i]) {
                            orderExist = true;
                        }
                    }
                    if (!orderExist) {
                        throw new NumberFormatException();
                    }
                    orderHandler.completeOrder(choice);
                    int index = 0;
                    for (int i = 0; i < orders.size(); i++) {
                        if (orders.get(i).getOrderNumber() == choice) {
                          index = i;  
                        }
                    }
                    orders.remove(index);
                }
            } catch (NumberFormatException e) {
                ui.println("Ikke et valid input. vælg et ordrenummer eller -1");
            }
        }
    }

    private void admPizza() {
        int choice = 0;
        while (choice != 3) {
            ui.println("---------------------- ADM. Pizza ----------------------");
            ui.println("1) Opret Pizza");
            ui.println("2) Fjern Pizza");
            ui.println("3) Tilbage til hovedmenu");
            try {
                choice = Integer.parseInt(ui.getInput());
                if (choice < 1 || choice > 3) {
                    throw new IllegalArgumentException();
                }
                switch (choice) {
                    case 1:
                        menuHandler.newPizza(newPizza());
                        break;
                    case 2:
                        removePizza();
                        break;
                    case 3:
                        break;
                }

            } catch (NumberFormatException e) {
                ui.println("Ikke et valid input i ADM. Pizza menuen. Vælg mellem 1 - 5");
            }
        }
    }

    private void removePizza() {
        int choice = 0;
        int menuSize = menuHandler.getMenu().size();
        while (choice != -1) {
            menuHandler.printMenu();
            ui.println("\nVælg pizza eller -1 for at afslutte");
            try {
                choice = Integer.parseInt(ui.getInput());
                if (choice != -1) {
                    if (choice < menuSize + 1 && choice > 0) {
                        menuHandler.removePizza(choice);
                    } else {
                        throw new NumberFormatException();
                    }
                }
            } catch (NumberFormatException e) {
                System.out.printf("Ikke et valid input. Vælg mellem 1 - %d%n", menuSize);
            }
        }
    }
    
    private String[] newPizza(){
        String[] userInput = new String[3];
        ui.println("Indtast navn på pizza'en");
        userInput[0] = ui.getInput();
        ui.println("Indtast ingredienser separeret med komma");
        userInput[1] = ui.getInput();
        //Removes whitespaces
        userInput[1] = userInput[1].replaceAll("\\s", "");
        ui.println("Indtast pris på pizza'en. f.eks: 57");
        userInput[2] = ui.getInput();
        return userInput;
    }

    private void turnover() {
        int choice = 0;
        while (choice != 6) {
            ui.println("---------------------- Omsætning ----------------------");
            ui.println("1) Sidste 7 dage");
            ui.println("2) Sidste 4 uger");
            ui.println("3) Sidste 6 måneder");
            ui.println("4) Sidste 12 måneder");
            ui.println("5) Hele omsætningen");
            ui.println("6) Tilbage til hovedmenu");
            try {
                choice = Integer.parseInt(ui.getInput());
                if (choice < 1 || choice > 6) {
                    throw new NumberFormatException();
                }
                switch (choice) {
                    case 1:
                        ui.println(statistics.turnoverForDuration(0, 0, 1));
                        break;
                    case 2:
                        ui.println(statistics.turnoverForDuration(0, 0, 4));
                        break;
                    case 3:
                        ui.println(statistics.turnoverForDuration(0, 6, 0));
                        break;
                    case 4:
                        ui.println(statistics.turnoverForDuration(0, 12, 0));
                        break;
                    case 5:
                        ui.println(statistics.turnoverAllTime());
                        break;
                }

            } catch (NumberFormatException e) {
                ui.println("Ikke et valid input. Vælg mellem 1 - 6");
            }
        }
    }

    private void statistics() {
        int choice = 0;
        while (choice != 6) {
            ui.println("---------------------- Statistics ----------------------");
            ui.println("1) Mest populær pizza sidste 7 dage");
            ui.println("2) Mest populær pizza sidste 4 uger");
            ui.println("3) Mest populær pizza sidste 6 måneder");
            ui.println("4) Mest populær pizza sidste 12 måneder");
            ui.println("5) Mest populær pizza nogensinde");
            ui.println("6) Tilbage til hovedmenu");
            try {
                choice = Integer.parseInt(ui.getInput());
                if (choice < 1 || choice > 6) {
                    throw new NumberFormatException();
                }
                switch (choice) {
                    case 1:
                        ui.println(statistics.popularPizzaForDuration(0, 0, 1));
                        break;
                    case 2:
                        ui.println(statistics.popularPizzaForDuration(0, 0, 4));
                        break;
                    case 3:
                        ui.println(statistics.popularPizzaForDuration(0, 6, 0));
                        break;
                    case 4:
                        ui.println(statistics.popularPizzaForDuration(0, 12, 0));
                        break;
                    case 5:
                        statistics.popularPizzaAllTime();
                        break;
                }

            } catch (NumberFormatException e) {
                ui.println("Ikke et valid input. Vælg mellem 1 - 6");
            }
        }
    }

}
