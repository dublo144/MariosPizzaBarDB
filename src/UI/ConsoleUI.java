package UI;

import java.util.Scanner;

/**
 * @author Alexander 
 */
public class ConsoleUI implements UI{

    Scanner input = new Scanner(System.in);

    @Override
    public String getInput() {
        return input.nextLine();
    }

    @Override
    public void println(String words) {
        System.out.println(words);
    }

    @Override
    public void printf(String str, Object... format) {
        System.out.printf(str,format);
    }
    
    
}
