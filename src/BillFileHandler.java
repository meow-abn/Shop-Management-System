import java.io.*;
import java.util.ArrayList;

class BillFileHandler {

    void saveBills(ArrayList<Bill> bills) throws IOException {

        File file = new File("bills.txt");

        FileWriter fw = new FileWriter(file);

        for(Bill bill : bills){

            for(Purchase purchase : bill.purchases){

                fw.write(
                    bill.invoiceNumber + "|" +
                    bill.customer.customerId + "|" +
                    bill.customer.customerName + "|" +
                    bill.customer.customerPhoneNumber + "|" +
                    bill.customer.customerAddress + "|" +
                    bill.invoiceDate + "|" +
                    bill.transactionId + "|" +
                    purchase.product.productId + "|" +
                    purchase.product.name + "|" +
                    purchase.purchasedQuantity + "|" +
                    purchase.purchasePrice + "\n"
                );
            }
        }

        fw.close();
    }


    ArrayList<Bill> readBills()
            throws IOException, InvalidCustomerException,
                   InvalidBillException, InvalidProductException,
                   InvalidPurchaseException {

        File file = new File("bills.txt");

        if(!file.exists()){
            System.out.println("Bill file does not exist.");
            return new ArrayList<>();
        }

        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);

        ArrayList<Bill> bills = new ArrayList<>();
        ArrayList<Customer> customers = new ArrayList<>();

        String line;

        while((line = br.readLine()) != null){

            String[] data = line.split("\\|");

            int invoiceNumber = Integer.parseInt(data[0]);

            int customerId = Integer.parseInt(data[1]);
            String customerName = data[2];
            String customerPhoneNumber = data[3];
            String customerAddress = data[4];

            String invoiceDate = data[5];
            String transactionId = data[6];

            int productId = Integer.parseInt(data[7]);
            String productName = data[8];

            int purchasedQuantity = Integer.parseInt(data[9]);
            double purchasePrice = Double.parseDouble(data[10]);


            Bill existingBill = null;

            for(Bill bill : bills){

                if(bill.invoiceNumber == invoiceNumber){
                    existingBill = bill;
                    break;
                }
            }


            Bill bill;

            if(existingBill == null){

Customer customer = null;

for(Customer c : customers){
    if(c.customerId == customerId){
        customer = c;
        break;
        }
    }

        if(customer == null){
            customer = new Customer(
            customerId,
            customerName,
            customerPhoneNumber,
            customerAddress
        );

    customers.add(customer);
}

                bill = new Bill(
                    invoiceNumber,
                    customer,
                    invoiceDate,
                    transactionId
                );
                customer.addPurchase(bill);
                bills.add(bill);

            }
            else{

                bill = existingBill;

            }


            Product product = new Product(
                productId,
                productName,
                purchasePrice,
                0,
                null,
                null,
                null
            );


            Purchase purchase = new Purchase(
                purchasedQuantity,
                product
            );


            purchase.purchasePrice = purchasePrice;
            
            bill.purchases.add(purchase);
        }


        for(Bill bill : bills){
            bill.calculateTotal();
        }


        br.close();
        fr.close();

        return bills;
    }
}