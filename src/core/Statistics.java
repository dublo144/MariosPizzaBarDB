package core;

/**
 * @author Mads 
 */
import Storage.Storage;
import java.time.LocalDate;
import java.util.ArrayList;

public class Statistics {

    private final Storage storage;

    public Statistics(Storage storage) {
        this.storage = storage;
    }
    
    public String calculateTurnover(int startDate, int endDate) {
        ArrayList<String> numbers = storage.readFromFile(Storage.TURNOVER_FILE);
        String[] splitLine;
        double turnover = 0;
        for (String number : numbers) {
            splitLine = number.split(" ");
            if (Integer.parseInt(splitLine[2].replace("-", "")) >= startDate && Integer.parseInt(splitLine[2].replace("-", "")) <= endDate) {
                turnover += Double.parseDouble(splitLine[1]);
            }
        }
        return String.format("Turnover from %d to %d = %.2f kr%n", startDate, endDate, turnover);
    }

    public String turnoverForDuration(int years,int months, int weeks) {
        LocalDate endDate = LocalDate.now();
        LocalDate startDate = endDate.minusYears(years)
                .minusMonths(months)
                .minusWeeks(weeks);
        int start = Integer.parseInt(startDate.toString().replaceAll("-", ""));
        int end = Integer.parseInt(endDate.toString().replaceAll("-", ""));
        return calculateTurnover(start, end);
    }
    
    public String turnoverAllTime() {
        LocalDate endDate = LocalDate.now();
        LocalDate startDate = endDate.withYear(2017);
        int start = Integer.parseInt(startDate.toString().replaceAll("-", ""));
        int end = Integer.parseInt(endDate.toString().replaceAll("-", ""));
        return calculateTurnover(start, end);
    }
    
    public String calculatePopularPizza(int startDate, int endDate) {
        ArrayList<String> numbers = storage.readFromFile(Storage.POPULAR_PIZZA_FILE);
        int[] pizzaCount = new int[100];
        String[] splitLine;
        for (String number : numbers) {
            splitLine = number.split(" ");
            if (Integer.parseInt(splitLine[1].replace("-", "")) >= startDate && Integer.parseInt(splitLine[1].replace("-", "")) <= endDate) {
                pizzaCount[Integer.parseInt(splitLine[0]) - 1]++;
            }
        }
        int max = 0;
        int pizzaNumber = 0;
        
        for(int i = 0; i < pizzaCount.length; i++){
            if(pizzaCount[i] > max){
                max = pizzaCount[i];
                pizzaNumber = i + 1;
            }
                
        }
        return String.format("Most popular pizza from %d to %d = number %d%n", startDate, endDate, pizzaNumber);
    }
    
    public String popularPizzaForDuration(int years, int months, int weeks) {
        LocalDate endDate = LocalDate.now();
        LocalDate startDate = endDate.minusYears(years)
                .minusMonths(months)
                .minusWeeks(weeks);
        int start = Integer.parseInt(startDate.toString().replaceAll("-", ""));
        int end = Integer.parseInt(endDate.toString().replaceAll("-", ""));
        return calculatePopularPizza(start, end);
    }
    
    public String popularPizzaAllTime() {
        LocalDate endDate = LocalDate.now();
        LocalDate startDate = endDate.withYear(2017);
        int start = Integer.parseInt(startDate.toString().replaceAll("-", ""));
        int end = Integer.parseInt(endDate.toString().replaceAll("-", ""));
        return calculatePopularPizza(start, end);
    }    
}
