import java.io.*;

class ProductFileHandler {
    Inventory readProducts() throws IOException, InvalidProductException, InvalidInventoryException{

        File file = new File("products.txt");
        if(!file.exists()){
        System.out.println("Product file does not exist.");
        return new Inventory();
        }

        Inventory inventory = new Inventory();
        FileReader fr = new FileReader("products.txt");
        BufferedReader br = new BufferedReader(fr);
        String line;
        boolean foundProduct = false;

        while((line = br.readLine()) != null){
            foundProduct = true;
            String[] data = line.split("\\|");
            int productId = Integer.parseInt(data[0]);
            double price = Double.parseDouble(data[2]);
            int quantity = Integer.parseInt(data[3]);
            String name = data[1];
            String category = data[4];
            String manufacturingDate = data[5];
            String expiryDate = data[6];
            Product product = new Product(
            productId,
            name,
            price,
            quantity,
            category,
            manufacturingDate,
            expiryDate
        );
        inventory.addProduct(product);
    }
    if(!foundProduct){
    System.out.println("No products found in file.");
    }
    br.close();
    fr.close();
    return inventory;
    }
    void saveProducts(Inventory inventory) throws IOException{
        File file = new File("products.txt");
        FileWriter fw = new FileWriter(file);
        for(Product product : inventory.products){
                fw.write(product.productId + "|" + product.name + "|" + product.price + "|" + product.quantity + "|" + product.category + "|" + product.manufacturingDate + "|" + product.expiryDate);
                fw.write("\n");
        }

        fw.close();
    }
}