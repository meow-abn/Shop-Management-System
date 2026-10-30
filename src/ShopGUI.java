import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

public class ShopGUI extends Frame implements ActionListener {

    Button b1, b2, b3, b4, b5, b6, b7, b8;

    Inventory i1;
    ProductFileHandler pf;
    BillFileHandler bf;

    ArrayList<Bill> bills = new ArrayList<>();
    ArrayList<Customer> customers = new ArrayList<>();
    ArrayList<Purchase> cart = new ArrayList<>();

    Customer currentCustomer;
    int nextInvoiceNumber = 1;

    TextField productIdField, nameField, priceField, quantityField;
    TextField categoryField, manufacturingField, expiryField;

    TextField searchField, updateIdField;
    TextField updatePriceField, updateQuantityField;
    TextField removeIdField;

    TextField billCustomerIdField, billCustomerNameField;
    TextField billPhoneField, billAddressField;
    TextField billProductIdField, billQuantityField;

    TextArea searchResult, cartArea;

    Button searchButton, updateButton, removeButton;
    Button addProductButton, addToCartButton, generateBillButton;

    Frame billFrame;

ShopGUI() throws Exception {

    pf = new ProductFileHandler();
    bf = new BillFileHandler();

    i1 = pf.readProducts();
    bills = bf.readBills();

    loadCustomersAndInvoices();

    setTitle("Shop Management System");
    setLayout(new BorderLayout(0, 0));
    setBackground(new Color(245, 247, 250));

    // HEADER
    Panel header = new Panel(new GridLayout(2, 1));
    header.setBackground(new Color(31, 41, 55));

    Label title = new Label(
            "  SHOP MANAGEMENT SYSTEM",
            Label.LEFT
    );

    title.setFont(new Font("Arial", Font.BOLD, 23));
    title.setForeground(Color.WHITE);

    Label subtitle = new Label(
            "  Inventory Management | Billing | Customers",
            Label.LEFT
    );

    subtitle.setForeground(Color.LIGHT_GRAY);

    header.add(title);
    header.add(subtitle);

    // SIDEBAR
    Panel sidebar = new Panel(new GridLayout(9, 1, 5, 5));
    sidebar.setBackground(new Color(229, 234, 242));
    sidebar.setPreferredSize(new Dimension(200, 0));

    Label menuTitle = new Label("  MENU");
    menuTitle.setFont(new Font("Arial", Font.BOLD, 15));
    sidebar.add(menuTitle);

    b1 = new Button("Add Product");
    b2 = new Button("View Products");
    b3 = new Button("Search Product");
    b4 = new Button("Update Product");
    b5 = new Button("Remove Product");
    b6 = new Button("Create Bill");
    b7 = new Button("Customer History");
    b8 = new Button("Exit");

    Button[] buttons = {b1, b2, b3, b4, b5, b6, b7, b8};

    for (Button button : buttons) {
        button.setFont(new Font("Arial", Font.PLAIN, 14));
        button.addActionListener(this);
        sidebar.add(button);
    }

    // MAIN DASHBOARD
   Panel dashboard = new Panel(new BorderLayout(20, 25));
    dashboard.setBackground(new Color(248, 250, 252));

    Panel welcomePanel = new Panel(new GridLayout(3, 1, 5, 5));
    welcomePanel.setBackground(new Color(245, 247, 250));

    Label welcome = new Label("Welcome to Your Dashboard");
    welcome.setFont(new Font("Arial", Font.BOLD, 25));

    Label description = new Label(
            "Manage your shop from one place."
    );

    Label instruction = new Label(
            "Choose an option from the menu on the left."
    );

    welcomePanel.add(welcome);
    welcomePanel.add(description);
    welcomePanel.add(instruction);

    // DASHBOARD CARDS
    Panel cards = new Panel(new GridLayout(1, 3, 12, 12));
    cards.setBackground(new Color(245, 247, 250));

    cards.add(createCard(
            "TOTAL PRODUCTS",
            String.valueOf(i1.products.size())
    ));

    cards.add(createCard(
            "TOTAL BILLS",
            String.valueOf(bills.size())
    ));

    int lowStock = 0;

    for (Product product : i1.products) {
        if (product.quantity <= 5) {
            lowStock++;
        }
    }

    cards.add(createCard(
            "LOW STOCK ITEMS",
            String.valueOf(lowStock)
    ));

    dashboard.add(welcomePanel, BorderLayout.NORTH);
    dashboard.add(cards, BorderLayout.CENTER);

    // ADD COMPONENTS
    add(header, BorderLayout.NORTH);
    add(sidebar, BorderLayout.WEST);
    add(dashboard, BorderLayout.CENTER);

    setSize(950, 600);
    setLocationRelativeTo(null);

    addWindowListener(new WindowAdapter() {
        public void windowClosing(WindowEvent e) {
            dispose();
        }
    });

    setVisible(true);
}


Panel createCard(String heading, String value) {

    Panel card = new Panel(new GridLayout(3, 1, 5, 5));
    card.setBackground(Color.WHITE);

    Label title = new Label(heading, Label.CENTER);
    title.setFont(new Font("Arial", Font.BOLD, 13));
    title.setForeground(new Color(100, 116, 139));

    Label number = new Label(value, Label.CENTER);
    number.setFont(new Font("Arial", Font.BOLD, 28));
    number.setForeground(new Color(37, 99, 235));

    String footerText = "";

    if (heading.equals("TOTAL PRODUCTS")) {
        footerText = "Products in inventory";
    } 
    else if (heading.equals("TOTAL BILLS")) {
        footerText = "Bills generated";
    } 
    else if (heading.equals("LOW STOCK ITEMS")) {
        footerText = "Products with stock <= 5";
    }

    Label footer = new Label(footerText, Label.CENTER);
    footer.setFont(new Font("Arial", Font.PLAIN, 12));
    footer.setForeground(new Color(107, 114, 128));

    card.add(title);
    card.add(number);
    card.add(footer);

    return card;
}


    void loadCustomersAndInvoices() {

        for (Bill bill : bills) {

            if (bill.invoiceNumber >= nextInvoiceNumber) {
                nextInvoiceNumber = bill.invoiceNumber + 1;
            }

            boolean found = false;

            for (Customer customer : customers) {
                if (customer.customerId == bill.customer.customerId) {
                    found = true;
                    break;
                }
            }

            if (!found) {
                customers.add(bill.customer);
            }
        }
    }

    void showMessage(String message) {

        Dialog dialog = new Dialog(this, "Message", true);

        dialog.setLayout(new BorderLayout(10, 10));

        TextArea messageArea = new TextArea(message, 5, 40,
                TextArea.SCROLLBARS_VERTICAL_ONLY);

        messageArea.setEditable(false);

        Button okButton = new Button("OK");

        okButton.addActionListener(e -> dialog.dispose());

        Panel panel = new Panel();
        panel.add(okButton);

        dialog.add(messageArea, BorderLayout.CENTER);
        dialog.add(panel, BorderLayout.SOUTH);

        dialog.setSize(400, 200);
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    void closeFrame(Frame frame) {

        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                frame.dispose();
            }
        });
    }

    public void actionPerformed(ActionEvent e) {

        Object source = e.getSource();

        // OPTION 1: ADD PRODUCT

        if (source == b1) {

            Frame frame = new Frame("Add Product");
            frame.setLayout(new BorderLayout(10, 10));

            Panel form = new Panel(new GridLayout(7, 2, 8, 8));

            productIdField = new TextField();
            nameField = new TextField();
            priceField = new TextField();
            quantityField = new TextField();
            categoryField = new TextField();
            manufacturingField = new TextField();
            expiryField = new TextField();

            form.add(new Label("Product ID:"));
            form.add(productIdField);

            form.add(new Label("Product Name:"));
            form.add(nameField);

            form.add(new Label("Price:"));
            form.add(priceField);

            form.add(new Label("Quantity:"));
            form.add(quantityField);

            form.add(new Label("Category:"));
            form.add(categoryField);

            form.add(new Label("Manufacturing Date:"));
            form.add(manufacturingField);

            form.add(new Label("Expiry Date:"));
            form.add(expiryField);

            addProductButton = new Button("Save Product");

            addProductButton.addActionListener(this);

            frame.add(form, BorderLayout.CENTER);
            frame.add(addProductButton, BorderLayout.SOUTH);

            closeFrame(frame);

            frame.setSize(450, 350);
            frame.setLocationRelativeTo(this);
            frame.setVisible(true);
        }

        // SAVE PRODUCT

        else if (source == addProductButton) {

            try {

                int id = Integer.parseInt(productIdField.getText().trim());
                String name = nameField.getText().trim();
                double price = Double.parseDouble(priceField.getText().trim());
                int quantity = Integer.parseInt(quantityField.getText().trim());

                String category = categoryField.getText().trim();
                String manufacturingDate = manufacturingField.getText().trim();
                String expiryDate = expiryField.getText().trim();

                Product product = new Product(
                        id,
                        name,
                        price,
                        quantity,
                        category,
                        manufacturingDate,
                        expiryDate
                );

                i1.addProduct(product);
                pf.saveProducts(i1);

                showMessage("Product added successfully.");

            } catch (NumberFormatException ex) {
                showMessage("Enter valid numeric values for ID, price and quantity.");

            } catch (Exception ex) {
                showMessage("Error: " + ex.getMessage());
            }
        }

        // OPTION 2: VIEW PRODUCTS

        else if (source == b2) {

            Frame frame = new Frame("View Products");

            TextArea area = new TextArea("", 20, 60,
                    TextArea.SCROLLBARS_BOTH);

            area.setEditable(false);

            if (i1.products.isEmpty()) {

                area.setText("No products available.");

            } else {

                for (Product p : i1.products) {

                    area.append(
                            "Product ID: " + p.productId +
                            "\nName: " + p.name +
                            "\nPrice: " + p.price +
                            "\nQuantity: " + p.quantity +
                            "\nCategory: " + p.category +
                            "\nManufacturing Date: " + p.manufacturingDate +
                            "\nExpiry Date: " + p.expiryDate +
                            "\n--------------------------------\n"
                    );
                }
            }

            frame.add(area);

            closeFrame(frame);

            frame.setSize(600, 450);
            frame.setLocationRelativeTo(this);
            frame.setVisible(true);
        }

        // OPTION 3: SEARCH PRODUCT

        else if (source == b3) {

            Frame frame = new Frame("Search Product");
            frame.setLayout(new BorderLayout(10, 10));

            Panel top = new Panel();

            searchField = new TextField(15);
            searchButton = new Button("Search");

            top.add(new Label("Product ID:"));
            top.add(searchField);
            top.add(searchButton);

            searchResult = new TextArea("", 12, 50,
                    TextArea.SCROLLBARS_VERTICAL_ONLY);

            searchResult.setEditable(false);

            searchButton.addActionListener(this);

            frame.add(top, BorderLayout.NORTH);
            frame.add(searchResult, BorderLayout.CENTER);

            closeFrame(frame);

            frame.setSize(500, 350);
            frame.setLocationRelativeTo(this);
            frame.setVisible(true);
        }

        else if (source == searchButton) {

            try {

                int id = Integer.parseInt(searchField.getText().trim());

                Product p = i1.searchProduct(id);

                searchResult.setText("");

                if (p == null) {

                    searchResult.setText("Product not found.");

                } else {

                    searchResult.setText(
                            "Product ID: " + p.productId +
                            "\nName: " + p.name +
                            "\nPrice: " + p.price +
                            "\nQuantity: " + p.quantity +
                            "\nCategory: " + p.category +
                            "\nManufacturing Date: " + p.manufacturingDate +
                            "\nExpiry Date: " + p.expiryDate
                    );
                }

            } catch (NumberFormatException ex) {
                searchResult.setText("Enter a valid Product ID.");
            }
        }

        // OPTION 4: UPDATE PRODUCT

        else if (source == b4) {

            Frame frame = new Frame("Update Product");
            frame.setLayout(new BorderLayout(10, 10));

            Panel form = new Panel(new GridLayout(3, 2, 8, 8));

            updateIdField = new TextField();
            updatePriceField = new TextField();
            updateQuantityField = new TextField();

            form.add(new Label("Product ID:"));
            form.add(updateIdField);

            form.add(new Label("New Price:"));
            form.add(updatePriceField);

            form.add(new Label("New Quantity:"));
            form.add(updateQuantityField);

            updateButton = new Button("Update");
            updateButton.addActionListener(this);

            frame.add(form, BorderLayout.CENTER);
            frame.add(updateButton, BorderLayout.SOUTH);

            closeFrame(frame);

            frame.setSize(400, 220);
            frame.setLocationRelativeTo(this);
            frame.setVisible(true);
        }

        else if (source == updateButton) {

            try {

                int id = Integer.parseInt(updateIdField.getText().trim());
                double price = Double.parseDouble(
                        updatePriceField.getText().trim()
                );
                int quantity = Integer.parseInt(
                        updateQuantityField.getText().trim()
                );

                i1.updateProduct(id, price, quantity);
                pf.saveProducts(i1);

                showMessage("Product updated successfully.");

            } catch (NumberFormatException ex) {
                showMessage("Enter valid numeric values.");

            } catch (Exception ex) {
                showMessage("Error: " + ex.getMessage());
            }
        }

        // OPTION 5: REMOVE PRODUCT

        else if (source == b5) {

            Frame frame = new Frame("Remove Product");
            frame.setLayout(new BorderLayout(10, 10));

            Panel top = new Panel();

            removeIdField = new TextField(15);
            removeButton = new Button("Remove");

            top.add(new Label("Product ID:"));
            top.add(removeIdField);
            top.add(removeButton);

            removeButton.addActionListener(this);

            frame.add(top, BorderLayout.NORTH);

            closeFrame(frame);

            frame.setSize(400, 150);
            frame.setLocationRelativeTo(this);
            frame.setVisible(true);
        }

        else if (source == removeButton) {

            try {

                int id = Integer.parseInt(removeIdField.getText().trim());

                i1.removeProduct(id);
                pf.saveProducts(i1);

                showMessage("Product removed successfully.");

            } catch (NumberFormatException ex) {
                showMessage("Enter a valid Product ID.");

            } catch (Exception ex) {
                showMessage("Error: " + ex.getMessage());
            }
        }

        // OPTION 6: CREATE BILL

        else if (source == b6) {

            cart.clear();
            currentCustomer = null;

            billFrame = new Frame("Create Bill");
            billFrame.setLayout(new BorderLayout(10, 10));

            Label title = new Label("CREATE BILL", Label.CENTER);
            title.setFont(new Font("Arial", Font.BOLD, 20));

            Panel form = new Panel(new GridLayout(6, 2, 8, 8));

            billCustomerIdField = new TextField();
            billCustomerNameField = new TextField();
            billPhoneField = new TextField();
            billAddressField = new TextField();
            billProductIdField = new TextField();
            billQuantityField = new TextField();

            form.add(new Label("Customer ID:"));
            form.add(billCustomerIdField);

            form.add(new Label("Customer Name (new customer):"));
            form.add(billCustomerNameField);

            form.add(new Label("Phone (new customer):"));
            form.add(billPhoneField);

            form.add(new Label("Address (new customer):"));
            form.add(billAddressField);

            form.add(new Label("Product ID:"));
            form.add(billProductIdField);

            form.add(new Label("Quantity:"));
            form.add(billQuantityField);

            addToCartButton = new Button("Add to Cart");
            generateBillButton = new Button("Generate Bill");

            addToCartButton.addActionListener(this);
            generateBillButton.addActionListener(this);

            Panel buttons = new Panel(new FlowLayout());

            buttons.add(addToCartButton);
            buttons.add(generateBillButton);

            Panel top = new Panel(new BorderLayout(5, 5));

            top.add(title, BorderLayout.NORTH);
            top.add(form, BorderLayout.CENTER);
            top.add(buttons, BorderLayout.SOUTH);

            cartArea = new TextArea("Your cart is empty.", 12, 50,
                    TextArea.SCROLLBARS_VERTICAL_ONLY);

            cartArea.setEditable(false);

            billFrame.add(top, BorderLayout.NORTH);
            billFrame.add(cartArea, BorderLayout.CENTER);

            closeFrame(billFrame);

            billFrame.setSize(650, 550);
            billFrame.setLocationRelativeTo(this);
            billFrame.setVisible(true);
        }

        // ADD PRODUCT TO CART

        else if (source == addToCartButton) {

            try {

                int customerId = Integer.parseInt(
                        billCustomerIdField.getText().trim()
                );

                int productId = Integer.parseInt(
                        billProductIdField.getText().trim()
                );

                int quantity = Integer.parseInt(
                        billQuantityField.getText().trim()
                );

                if (quantity <= 0) {
                    showMessage("Quantity must be greater than zero.");
                    return;
                }

                Customer customer = findCustomer(customerId);

                if (customer == null) {

                    String name = billCustomerNameField.getText().trim();
                    String phone = billPhoneField.getText().trim();
                    String address = billAddressField.getText().trim();

                    customer = new Customer(
                            customerId, name, phone, address
                    );

                    customers.add(customer);
                }

                if (currentCustomer != null &&
                        currentCustomer.customerId != customer.customerId) {

                    showMessage("One bill can contain only one customer.");
                    return;
                }

                Product product = i1.searchProduct(productId);

                if (product == null) {
                    showMessage("Product not found.");
                    return;
                }

                int quantityAlreadyInCart = 0;

                for (Purchase purchase : cart) {
                    if (purchase.product.productId == productId) {
                        quantityAlreadyInCart += purchase.purchasedQuantity;
                    }
                }

                if (quantityAlreadyInCart + quantity > product.quantity) {
                    showMessage("Insufficient stock. Available quantity: "
                            + product.quantity);
                    return;
                }

                currentCustomer = customer;

                Purchase purchase = new Purchase(quantity, product);
                cart.add(purchase);

                updateCartDisplay();

                billProductIdField.setText("");
                billQuantityField.setText("");

            } catch (NumberFormatException ex) {
                showMessage("Enter valid numeric IDs and quantity.");

            } catch (Exception ex) {
                showMessage("Error: " + ex.getMessage());
            }
        }

        // GENERATE BILL

        else if (source == generateBillButton) {

            try {

                if (cart.isEmpty()) {
                    showMessage("Cart is empty. Add a product first.");
                    return;
                }

                // Validate all stock before reducing any quantity.

                for (Purchase purchase : cart) {

                    int required = 0;

                    for (Purchase item : cart) {
                        if (item.product.productId ==
                                purchase.product.productId) {
                            required += item.purchasedQuantity;
                        }
                    }

                    Product product = i1.searchProduct(
                            purchase.product.productId
                    );

                    if (product == null || required > product.quantity) {
                        showMessage("Insufficient stock for "
                                + purchase.product.name);
                        return;
                    }
                }

                String date = LocalDate.now().toString();

                String transactionId =
                        "TXN" + System.currentTimeMillis();

                Bill bill = new Bill(
                        nextInvoiceNumber,
                        currentCustomer,
                        date,
                        transactionId
                );

                // addPurchase() reduces stock through processPurchase().
                for (Purchase purchase : cart) {

                    if (!bill.addPurchase(purchase)) {
                        showMessage("Unable to process purchase.");
                        return;
                    }
                }

                bill.generateBill();

                currentCustomer.addPurchase(bill);
                bills.add(bill);

                pf.saveProducts(i1);
                bf.saveBills(bills);

                StringBuilder receipt = new StringBuilder();

                receipt.append("BILL GENERATED SUCCESSFULLY\n\n");
                receipt.append("Invoice Number: ")
                        .append(bill.invoiceNumber).append("\n");
                receipt.append("Date: ").append(bill.invoiceDate).append("\n");
                receipt.append("Transaction ID: ")
                        .append(bill.transactionId).append("\n");
                receipt.append("Customer: ")
                        .append(currentCustomer.customerName).append("\n\n");

                for (Purchase purchase : bill.purchases) {

                    receipt.append(purchase.product.name)
                            .append(" x ")
                            .append(purchase.purchasedQuantity)
                            .append(" = ")
                            .append(purchase.calculateSubTotal())
                            .append("\n");
                }

                receipt.append("\nTotal Amount: ")
                        .append(bill.totalAmount);

                cartArea.setText(receipt.toString());

                nextInvoiceNumber++;

                cart.clear();
                currentCustomer = null;

                billCustomerIdField.setText("");
                billCustomerNameField.setText("");
                billPhoneField.setText("");
                billAddressField.setText("");
                billProductIdField.setText("");
                billQuantityField.setText("");

            } catch (Exception ex) {
                showMessage("Could not generate bill: " + ex.getMessage());
            }
        }

        // OPTION 7: CUSTOMER HISTORY

        else if (source == b7) {

            Frame frame = new Frame("Customer Purchase History");
            frame.setLayout(new BorderLayout(10, 10));

            Panel top = new Panel();

            TextField customerIdField = new TextField(15);
            Button historyButton = new Button("Show History");

            top.add(new Label("Customer ID:"));
            top.add(customerIdField);
            top.add(historyButton);

            TextArea historyArea = new TextArea("", 20, 60,
                    TextArea.SCROLLBARS_VERTICAL_ONLY);

            historyArea.setEditable(false);

            historyButton.addActionListener(event -> {

                try {

                    int id = Integer.parseInt(
                            customerIdField.getText().trim()
                    );

                    Customer customer = findCustomer(id);

                    historyArea.setText("");

                    if (customer == null) {
                        historyArea.setText("Customer not found.");
                        return;
                    }

                    historyArea.append(
                            "Customer ID: " + customer.customerId + "\n"
                    );
                    historyArea.append(
                            "Name: " + customer.customerName + "\n"
                    );
                    historyArea.append(
                            "Phone: " + customer.customerPhoneNumber + "\n"
                    );
                    historyArea.append(
                            "Address: " + customer.customerAddress + "\n\n"
                    );

                    boolean found = false;

                    for (Bill bill : bills) {

                        if (bill.customer.customerId == id) {

                            found = true;

                            historyArea.append(
                                    "Invoice: " + bill.invoiceNumber + "\n"
                            );
                            historyArea.append(
                                    "Date: " + bill.invoiceDate + "\n"
                            );

                            for (Purchase purchase : bill.purchases) {

                                historyArea.append(
                                        purchase.product.name +
                                        " x " + purchase.purchasedQuantity +
                                        " = " + purchase.calculateSubTotal() +
                                        "\n"
                                );
                            }

                            historyArea.append(
                                    "Total: " + bill.totalAmount + "\n"
                            );
                            historyArea.append(
                                    "------------------------------\n"
                            );
                        }
                    }

                    if (!found) {
                        historyArea.append("No purchases found.");
                    }

                } catch (NumberFormatException ex) {
                    historyArea.setText("Enter a valid Customer ID.");
                }
            });

            frame.add(top, BorderLayout.NORTH);
            frame.add(historyArea, BorderLayout.CENTER);

            closeFrame(frame);

            frame.setSize(600, 450);
            frame.setLocationRelativeTo(this);
            frame.setVisible(true);
        }

        // OPTION 8: EXIT

        else if (source == b8) {
            dispose();
        }
    }

    Customer findCustomer(int customerId) {

        for (Customer customer : customers) {
            if (customer.customerId == customerId) {
                return customer;
            }
        }

        return null;
    }

    void updateCartDisplay() {

        cartArea.setText("");

        if (cart.isEmpty()) {
            cartArea.setText("Your cart is empty.");
            return;
        }

        double total = 0;

        cartArea.append("CURRENT CART\n");
        cartArea.append("------------------------------\n");

        for (Purchase purchase : cart) {

            double subtotal = purchase.calculateSubTotal();
            total += subtotal;

            cartArea.append(
                    purchase.product.name +
                    " | Quantity: " + purchase.purchasedQuantity +
                    " | Price: " + purchase.purchasePrice +
                    " | Subtotal: " + subtotal + "\n"
            );
        }

        cartArea.append("------------------------------\n");
        cartArea.append("Cart Total: " + total);
    }

    public static void main(String[] args) {

        try {
            new ShopGUI();

        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Unable to start application: "
                    + ex.getMessage());
        }
    }
}
