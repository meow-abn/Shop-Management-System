import java.util.ArrayList;

class InvalidCustomerException extends Exception {
    InvalidCustomerException(String message){
        super(message);
    }
}


public class Customer {
    int customerId;
    String customerName;
    String customerPhoneNumber;
    String customerAddress;
    ArrayList<Bill> purchaseHistory = new ArrayList<>();
    
    Customer(int customerId, String customerName, String phoneNumber, String customerAddress) throws InvalidCustomerException {
        if(customerId <= 0){
            throw new InvalidCustomerException("Customer ID must be greater than zero");
        }
        if(customerName == null || customerName.isEmpty()){
            throw new InvalidCustomerException("Customer name cannot be null");
        }
        if(phoneNumber == null || phoneNumber.isEmpty()){
            throw new InvalidCustomerException("Phone number cannot be null");
        }
        if(customerAddress == null || customerAddress.isEmpty()){
           throw new InvalidCustomerException("Customer address cannot be empty");
        }
        
        this.customerId = customerId;
     this.customerName = customerName;
        this.customerPhoneNumber = phoneNumber;
        this.customerAddress = customerAddress;
    }
    void updateDetails(String newName, String newPhoneNumber, String newAddress)
        throws InvalidCustomerException {

    if(newName == null || newName.isEmpty()){
        throw new InvalidCustomerException("Customer name cannot be empty");
    }

    if(newPhoneNumber == null || newPhoneNumber.isEmpty()){
        throw new InvalidCustomerException("Customer phone number cannot be empty");
    }

    if(newAddress == null || newAddress.isEmpty()){
        throw new InvalidCustomerException("Customer address cannot be empty");
    }

    customerName = newName;
    customerPhoneNumber = newPhoneNumber;
    customerAddress = newAddress;
    }
    
    void addPurchase(Bill bill){
        purchaseHistory.add(bill);
    }
    void displayCustomer(){
        System.out.println("---------------Customer Details--------------- ");
        System.out.println("Customer ID: "+ customerId);
        System.out.println("Customer Name: "+ customerName);
        System.out.println("Customer's Phone number: "+ customerPhoneNumber);
        System.out.println("Customer Address:  " +  customerAddress);
    }
    void purchaseHistory(){
        for(Bill bill : purchaseHistory) {
            bill.displayBill();
        }
    }
}
