package Storage;

/**
 * @author Alexander 
 */
import UI.UI;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class FileStorage implements Storage {
    
    private UI ui;
    public FileStorage(UI ui) {
        this.ui = ui;
    }
    @Override
    public void completeOrder(int orderNumber){
        String line;
        String[] splitLine;
        ArrayList<String> orders = readFromFile(Storage.ORDERS_TO_MAKE_FILE);
        for(String order : orders){
            line = order;
            splitLine = line.split(" ");
            if (Integer.parseInt(splitLine[0]) == orderNumber)
                writeToFile(line, Storage.ALL_ORDERS_FILE);
            if (Integer.parseInt(splitLine[0]) != orderNumber)
                writeToFile(line, Storage.ORDERS_TO_MAKE_TEMP_FILE);
        }
        renameFile(Storage.ORDERS_TO_MAKE_TEMP_FILE, Storage.ORDERS_TO_MAKE_FILE);
    };
    
    @Override
    public ArrayList<Integer> getOrderNumbers(){
        ArrayList<Integer> orderNumbers = new ArrayList();
        String[] splitLine;
        ArrayList<String> readOrdersToMake = readFromFile(Storage.ORDERS_TO_MAKE_FILE);
        ArrayList<String> readAllOrders = readFromFile(Storage.ALL_ORDERS_FILE);
        for(String order : readOrdersToMake){
            splitLine = order.split(" ");
            if(!splitLine[0].equals("") || !splitLine[0].equals(" "))
                orderNumbers.add(Integer.parseInt(splitLine[0]));
        }
        for(String order : readAllOrders){
            splitLine = order.split(" ");
            if(!splitLine[0].equals("") || !splitLine[0].equals(" "))
                orderNumbers.add(Integer.parseInt(splitLine[0]));
        }      
        return orderNumbers;
    }

    @Override
    public void removePizza(int number) {
        String line;
        String[] splitLine;
        ArrayList<String> pizza = readFromFile(Storage.MENU_FILE);
        for(String piz : pizza){
            line = piz;
            splitLine = piz.split(" ");
            if (Integer.parseInt(splitLine[0]) != number) {
                    writeToFile(line, Storage.MENU_TEMP_FILE);
                }
        }
        renameFile(Storage.MENU_TEMP_FILE, Storage.MENU_FILE);
    }

    @Override
    public void writeToFile(String words, String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename, true))) {
            writer.write(words);
            writer.newLine();
        } catch (IOException e) {
            ui.println(e.getMessage());
        }
    }
    
    @Override
    public ArrayList<String> readFromFile(String filename){
        ArrayList<String> file = new ArrayList();
        String lineRead;
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) 
        {
            lineRead = reader.readLine();
            while (lineRead != null) {
                file.add(lineRead);
                lineRead = reader.readLine();
            }
        } catch (IOException e) {
            ui.println(e.getMessage());
        }
        return file;
    }

    private void renameFile(String filename, String newFilename) {
        try {
            File f = new File(newFilename);
            File tf = new File(filename);
            f.delete();
            tf.renameTo(f);
        } catch (Exception e) {
            ui.println(e.getMessage());
        }
    }
}
