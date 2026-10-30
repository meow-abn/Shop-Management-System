class InvalidPurchaseException extends Exception{
    InvalidPurchaseException(String message){
        super(message);
    }
}


public class Purchase {
    Product product;
    int purchasedQuantity;
    double purchasePrice;
    double subtotal;

    Purchase(int purchasedQuantity,Product product) throws InvalidPurchaseException{
        if(purchasedQuantity <=0){
            throw new InvalidPurchaseException("Purchased quantity must be greater than zero");
        }
        if(product == null){
            throw new InvalidPurchaseException("Product cannot be null");        }
        this.purchasedQuantity  = purchasedQuantity;
        this.product = product;
        purchasePrice = product.getPrice();
        subtotal = purchasePrice * purchasedQuantity;
    }
    double calculateSubTotal(){
        return purchasePrice * purchasedQuantity;
    }
    boolean processPurchase(){
        if(product.hasEnoughStock(purchasedQuantity)){
            product.reduceStock(purchasedQuantity);
            return true;
        }
        else{
            return false;
        }
    }
}