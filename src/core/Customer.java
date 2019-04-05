package core;

/**
 * @author Benjamin
 */
public class Customer {

    private String firstName;
    private String lastName;
    private String address;
    
    public Customer(){};

    public Customer(String firstName) {
        this.firstName = firstName;
    }
    
    public Customer(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }
    
    public Customer(String firstName, String lastName, String Address) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = Address;
    }

    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getAddress() { return address; }    
    
    @Override
    public String toString() {
        return firstName + " " + lastName + " " + address;
    }
    
    
    
}
