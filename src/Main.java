import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.InputMismatchException;


public class Main {

    public static void main(String[] args) throws IOException, InvalidProductException, InvalidInventoryException, InvalidCustomerException, InvalidBillException, InvalidPurchaseException {

        Scanner sc = new Scanner(System.in);
        ProductFileHandler pf = new ProductFileHandler();
        Inventory i1 = pf.readProducts();
        BillFileHandler bf = new BillFileHandler();
        ArrayList<Bill> bills = bf.readBills();
        Customer cust = null;
        if(!bills.isEmpty()){
       cust = bills.get(0).customer;    
        }

        while(true)  {
            
            try{
                int choice;

                System.out.println("\n========================================");
                System.out.println("        SHOP MANAGEMENT SYSTEM");
                System.out.println("========================================");
                System.out.println();
                System.out.println("  1. Add Product");
                System.out.println("  2. View Products");
                System.out.println("  3. Search Product");
                System.out.println("  4. Update Product");
                System.out.println("  5. Remove Product");
                System.out.println("  6. Create Bill");
                System.out.println("  7. Customer History");
                System.out.println("  8. Save Data");
                System.out.println("  9. Exit");
                System.out.println("----------------------------------------");
                System.out.print("Enter your choice: ");

                choice = sc.nextInt();

                // ADD PRODUCT
                if(choice == 1){

                    System.out.println("\n------------ ADD PRODUCT ---------------");

                    System.out.print("Enter Product ID: ");
                    int pro_Id = sc.nextInt();

                    System.out.print("Enter Product Name: ");
                    sc.nextLine();
                    String pro_Name = sc.nextLine();

                    System.out.print("Enter Price of the Product: ");
                    double pro_price = sc.nextDouble();

                    System.out.print("Enter Quantity of the Product: ");
                    int pro_quantity = sc.nextInt();

                    Product pro = new Product(
                        pro_Id,
                        pro_Name,
                        pro_price,
                        pro_quantity,
                        null,
                        null,
                        null
                    );

                    i1.addProduct(pro);

                    System.out.println("----------------------------------------");
                    System.out.println("Product added successfully.");
                }

                // VIEW PRODUCTS
                else if(choice == 2){

                    System.out.println("\n------------ PRODUCT LIST --------------");

                    i1.displayProducts();

                    System.out.println("----------------------------------------");
                }

                // SEARCH PRODUCT
                else if(choice == 3){

                    System.out.println("\n------------ SEARCH PRODUCT ------------");

                    System.out.print("Enter the Product ID: ");
                    int product_ID = sc.nextInt();

                    Product found = i1.searchProduct(product_ID);

                    if(found == null){

                        System.out.println("----------------------------------------");
                        System.out.println("Product not found.");

                    }
                    else{

                        System.out.println("\nProduct Found!");
                        System.out.println("----------------------------------------");
                        System.out.println("Product ID: " + found.productId);
                        System.out.println("Product Name: " + found.name);
                        System.out.println("Product Price: " + found.price);
                        System.out.println("Product Quantity: " + found.quantity);
                        System.out.println("Product Category: " + found.category);
                        System.out.println("Expiry Date: " + found.expiryDate);
                        System.out.println("Manufacturing Date: " + found.manufacturingDate);
                        System.out.println("----------------------------------------");
                    }
                }

                // UPDATE PRODUCT
                else if(choice == 4){

                    System.out.println("\n------------ UPDATE PRODUCT ------------");

                    System.out.print("Enter the Product ID: ");
                    int productId = sc.nextInt();

                    System.out.print("Enter the New Price: ");
                    double newPrice = sc.nextDouble();

                    System.out.print("Enter the New Quantity: ");
                    int newQuantity = sc.nextInt();

                    i1.updateProduct(productId, newPrice, newQuantity);

                    System.out.println("----------------------------------------");
                    System.out.println("Product updated successfully.");
                }

                // REMOVE PRODUCT
                else if(choice == 5){

                    System.out.println("\n------------ REMOVE PRODUCT ------------");

                    System.out.print("Enter the Product ID: ");
                    int productId = sc.nextInt();

                    i1.removeProduct(productId);

                    System.out.println("----------------------------------------");
                    System.out.println("Product removed successfully.");
                }

                // CREATE BILL
                else if(choice == 6){

                    System.out.println("\n========================================");
                    System.out.println("              CREATE BILL");
                    System.out.println("========================================");

                    System.out.println("\n------------ CUSTOMER DETAILS ----------");

                    System.out.print("Enter Customer ID: ");
                    int cust_id = sc.nextInt();

                    System.out.print("Enter Customer Name: ");
                    sc.nextLine();
                    String cust_name = sc.nextLine();

                    System.out.print("Enter Customer Phone Number: ");
                    String cust_no = sc.nextLine();

                    System.out.print("Enter Customer Address: ");
                    String cust_address = sc.nextLine();

                    cust = new Customer(
                        cust_id,
                        cust_name,
                        cust_no,
                        cust_address
                    );

                    System.out.println("\n------------ INVOICE DETAILS -----------");

                    System.out.print("Enter the Invoice Number: ");
                    int invoice_no = sc.nextInt();
                    boolean invoiceExists = false;
                    for(Bill b  : bills){
                        if(b.invoiceNumber == invoice_no){
                            invoiceExists = true;
                            break;
                        }
                    }
                    if(invoiceExists){
                           throw new InvalidBillException("Invoice number already exists");
                    }

                    System.out.print("Enter the Invoice Date: ");
                    sc.nextLine();
                    String invoice_date = sc.nextLine();

                    System.out.print("Enter the Transaction ID: ");
                    String trans_id = sc.nextLine();

                    Bill bill = new Bill(
                        invoice_no,
                        cust,
                        invoice_date,
                        trans_id
                    );

                    System.out.println("\n------------ PURCHASE DETAILS ----------");

                    System.out.print("Enter Product ID: ");
                    int product_ID = sc.nextInt();

                    Product found = i1.searchProduct(product_ID);

                    if(found == null){

                        System.out.println("----------------------------------------");
                        System.out.println("Product not found.");

                    }
                    else{

                        System.out.print("Enter Quantity to Purchase: ");
                        int purc_quan = sc.nextInt();

                        Purchase purc = new Purchase(
                            purc_quan,
                            found
                        );

                    boolean purchaseAdded = bill.addPurchase(purc);

                    if(purchaseAdded){
                    cust.addPurchase(bill);
                     bill.calculateTotal();
                    bills.add(bill);

                        System.out.println("\n========================================");
                        System.out.println("               INVOICE");
                        System.out.println("========================================");
                        bill.displayBill();
                         System.out.println("========================================");
                        }
                        else{
                            System.out.println("----------------------------------------");
                            System.out.println("Insufficient stock.");
                        }
                    }
                }

                // CUSTOMER HISTORY
                else if(choice == 7){

                    System.out.println("\n------------ CUSTOMER HISTORY ----------");

                    if(cust == null){

                        System.out.println("No customer history found.");

                    }
                    else{

                        cust.purchaseHistory();
                    }

                    System.out.println("----------------------------------------");
                }

                // SAVE DATA
                    else if(choice == 8){
                         System.out.println("\n------------ SAVE DATA -----------------");
                            pf.saveProducts(i1);
                            bf.saveBills(bills);
                            System.out.println("Data saved successfully.");
                            System.out.println("----------------------------------------");    
}

                // EXIT
                else if(choice == 9){

                    System.out.println("\n========================================");
                    System.out.println("Thank you for using Shop Management System!");
                    System.out.println("========================================");

                    break;
                }

                // INVALID CHOICE
                else{

                    System.out.println("\nInvalid choice. Please enter a number between 1 and 9.");
                }
            }

        catch(InvalidProductException e){
            System.out.println("Product Error: " + e.getMessage());
        }

        catch(InvalidPurchaseException e){
            System.out.println("Purchase Error: " + e.getMessage());
        }

        catch(InvalidBillException e){
            System.out.println("Bill Error: " + e.getMessage());
        }

        catch(InvalidInventoryException e){
            System.out.println("Inventory Error: " + e.getMessage());
        }

        catch(InvalidCustomerException e){
            System.out.println("Customer Error: " + e.getMessage());
        }

        catch(IOException e){
            System.out.println("File Error: " + e.getMessage());
        }
        catch(InputMismatchException e){
            System.out.println("Invalid input. Please enter a number.");
            sc.nextLine();
        }
    }
    sc.close();
}
}
