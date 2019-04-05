package core;

import UI.ConsoleUI;
import UI.UI;

public class Main {

    public static void main(String[] args) {
        UI ui = new ConsoleUI();
        UIController uiController = new UIController(ui);
        uiController.startProgram();
    }

}
