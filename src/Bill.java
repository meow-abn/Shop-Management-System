import java.util.ArrayList;

class InvalidBillException extends Exception{
    InvalidBillException(String message){
        super(message);
    }
}

public class Bill {
    int invoiceNumber;
    Customer customer;
    ArrayList<Purchase> purchases = new ArrayList<>();
    String invoiceDate;
    String transactionId;
    double totalAmount;

    Bill(int invoiceNumber, Customer customer,String invoiceDate, String transactionId) throws InvalidBillException{

        if(invoiceNumber <=0){
            throw new InvalidBillException("Invoice Number must be greater than zero");
        }
        if(customer==null){
            throw new InvalidBillException("Customer cannot be null");
        }

        this.invoiceNumber = invoiceNumber;
        this.customer = customer;
        this.invoiceDate = invoiceDate;
        this.transactionId = transactionId;
    }
    boolean addPurchase(Purchase purchase){
        if(purchase.processPurchase()){
        purchases.add(purchase);
        return true;
        }
        return false;
    }
    void calculateTotal(){
        totalAmount = 0;
        for(Purchase purchase  : purchases){
            totalAmount += purchase.calculateSubTotal();
        }
    }
    void generateBill(){
        calculateTotal();
    }
    void displayBill(){
        System.out.println("Invoice Number " + invoiceNumber);
        System.out.println("Invoice Date : " + invoiceDate);
        System.out.println("Transaction ID: " + transactionId);
        customer.displayCustomer();
        for(Purchase purchase : purchases){
            System.out.println("Product Name: "+ purchase.product.name);
            System.out.println("Purchased Quantities: "+ purchase.purchasedQuantity);
            System.out.println("Purchased Price: "+ purchase.purchasePrice);
            System.out.println("Purchased Subtotal: "+ purchase.subtotal);
        }
        System.out.println("Total Amount: " + totalAmount);
    }
}
