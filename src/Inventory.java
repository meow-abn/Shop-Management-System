import java.util.ArrayList;

class InvalidInventoryException extends Exception{
    InvalidInventoryException(String message){
        super(message);
    }
}

public class Inventory {
    ArrayList<Product> products;


    Inventory(){
        products = new ArrayList<>();
    }

    void addProduct(Product product) throws InvalidInventoryException{
        if(product == null){
            throw new InvalidInventoryException("Product cannot be null");
        }
 
        if(searchProduct(product.productId) != null){
             throw new InvalidInventoryException("Product with this ID " + product.productId + " already exists");

        }
        products.add(product);

    }

    void removeProduct(int productId)  throws InvalidInventoryException{
        boolean found = false;
        for(int i = 0; i < products.size(); i++) 

            {
                if(products.get(i).productId == productId) 
                {
                    products.remove(i);
                    found = true;
                    break;
                }
            }
        if(!found){
            throw new InvalidInventoryException("Product with this ID does not exist");
        }
    }
    Product searchProduct(int productId){
        for(int i = 0; i < products.size(); i++) 
            {
                if(products.get(i).productId == productId) 
                {
                return products.get(i);
                }
            }
        return null;
    }

    void updateProduct(int productId , double newPrice, int newQuantity) throws InvalidInventoryException , InvalidProductException{
        Product product = searchProduct(productId);
        if(product == null){
            throw new InvalidInventoryException("Product with this ID does not exist");
        }

        product.updatePrice(newPrice);
        product.updateQuantity(newQuantity);
        
    }
    void displayProducts(){
       for(Product product : products){
        System.out.println("Product ID: "+ product.productId);
        System.out.println("Product Name: "+ product.name);
        System.out.println("Product Category: "+ product.category);
        System.out.println("Product Price: "+ product.getPrice());
        System.out.println("Product Quantity: "+ product.quantity);
        System.out.println("Product's Expiry Date: "+ product.expiryDate);
        System.out.println("Product's Manufacturing Date: "+ product.manufacturingDate);
       }
    }

    
    
}
