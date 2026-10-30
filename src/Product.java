class InvalidProductException extends Exception{
        InvalidProductException(String message) {
        super(message);
    }
}
public class Product {
    int productId;
    String name;
    double price;
    int quantity;
    String category;
    String manufacturingDate;   
    String expiryDate;
    
    Product(int productId, String name, double price, int quantity, String category, String manufactingDate , String expiryDate) throws InvalidProductException{
        if(productId <=0 ) {
            throw new InvalidProductException("Product ID must be greater than 0");
        }
        if (name == null || name.isEmpty()) {
        throw new InvalidProductException("Product name cannot be empty");
        }
        if(quantity < 0 ){
            throw new InvalidProductException("Quantity cannot be negative");
        }
        if(price <= 0){
            throw new InvalidProductException("Price must be greater than 0");
        }

        this.productId = productId;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.category = category;
        this.manufacturingDate = manufactingDate;
        this.expiryDate = expiryDate;  

    }
    boolean checkStock(){
        if(quantity > 0){
            return true;
        } else{
            return false;
        }
    }
    void updateQuantity(int newQuantity) throws InvalidProductException {
         if(newQuantity < 0){
            throw new InvalidProductException("Quantity cannot be negative");
        }

    quantity = newQuantity;
    }
    void addStock(int newQuantity){
        quantity += newQuantity;
    }
    void updatePrice(double newPrice) throws InvalidProductException {
    if(newPrice <= 0){
        throw new InvalidProductException("Price must be greater than 0");
    }

    price = newPrice;
    }
    double getPrice(){
        return price;
    }
    boolean  hasEnoughStock(int purchasedQuantity){
        if(quantity >= purchasedQuantity){
            return true;
        }
        else{
            return false;
        }

    }
    void reduceStock(int purchasedQuantity){
        quantity = quantity - purchasedQuantity;
        System.out.println("In Stock: "+ quantity);
    
    }
}