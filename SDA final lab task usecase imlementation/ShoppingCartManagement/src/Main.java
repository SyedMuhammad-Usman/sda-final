import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

// Item Class
class Item {
    private String itemId;
    private String name;
    private double price;
    private int quantity;

    public Item(String itemId, String name, double price, int quantity) {
        this.itemId = itemId;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public String getItemId() {
        return itemId;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void updateQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "Item{" + "name='" + name + '\'' + ", price=" + price + ", quantity=" + quantity + '}';
    }
}

// Cart Class
class Cart {
    private String cartId;
    private String userId;
    private List<Item> items;
    private double totalPrice;

    public Cart(String userId) {
        this.cartId = UUID.randomUUID().toString();
        this.userId = userId;
        this.items = new ArrayList<>();
        this.totalPrice = 0.0;
    }

    public void addItem(Item item) {
        items.add(item);
        calculateTotal();
    }

    public void removeItem(String itemId) {
        items.removeIf(item -> item.getItemId().equals(itemId));
        calculateTotal();
    }

    private void calculateTotal() {
        totalPrice = items.stream().mapToDouble(item -> item.getPrice() * item.getQuantity()).sum();
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public List<Item> getItems() {
        return items;
    }

    @Override
    public String toString() {
        return "Cart{" + "totalPrice=" + totalPrice + ", items=" + items + '}';
    }
}

// Order Class
class Order {
    private String orderId;
    private String cartId;
    private String userId;
    private Date orderDate;
    private double totalPrice;
    private String status;

    public Order(Cart cart, String userId) {
        this.orderId = UUID.randomUUID().toString();
        this.cartId = cart.getItems().isEmpty() ? null : cart.getItems().toString();
        this.userId = userId;
        this.orderDate = new Date();
        this.totalPrice = cart.getTotalPrice();
        this.status = "Pending";
    }

    public void updateStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Order{" + "orderId='" + orderId + '\'' + ", status='" + status + '\'' + ", totalPrice=" + totalPrice + '}';
    }
}

// Payment Class
class Payment {
    private String paymentId;
    private String orderId;
    private String userId;
    private Date paymentDate;
    private double amount;
    private String method;
    private String status;

    public Payment(Order order, String method) {
        this.paymentId = UUID.randomUUID().toString();
        this.orderId = order.toString();
        this.userId = order.toString();
        this.paymentDate = new Date();
        this.amount = order.toString().length();
        this.method = method;
        this.status = "Pending";
    }

    public void processPayment() {
        this.status = "Completed";
    }

    @Override
    public String toString() {
        return "Payment{" + "paymentId='" + paymentId + '\'' + ", method='" + method + '\'' + ", status='" + status + '\'' + ", amount=" + amount + '}';
    }
}

// User Class
class User {
    private String userId;
    private String name;
    private String email;
    private String address;
    private Cart cart;

    public User(String userId, String name, String email, String address) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.address = address;
        this.cart = new Cart(userId);
    }

    public void addToCart(Item item) {
        cart.addItem(item);
    }

    public void removeFromCart(String itemId) {
        cart.removeItem(itemId);
    }

    public Cart viewCart() {
        return cart;
    }

    public Order checkout() {
        return new Order(cart, userId);
    }

    public Payment makePayment(Order order, String paymentMethod) {
        Payment payment = new Payment(order, paymentMethod);
        payment.processPayment();
        return payment;
    }

    @Override
    public String toString() {
        return "User{" + "name='" + name + '\'' + ", email='" + email + '\'' + '}';
    }
}

// Main Class for Testing
public class Main {
    public static void main(String[] args) {
        // Create a user
        User user = new User("u1", "John Doe", "john@example.com", "123 Main Street");

        // Add items to the cart
        Item item1 = new Item("i1", "Laptop", 1000.0, 1);
        Item item2 = new Item("i2", "Mouse", 50.0, 2);

        user.addToCart(item1);
        user.addToCart(item2);

        // View cart details
        System.out.println("Cart Details:");
        System.out.println(user.viewCart());

        // Remove an item from the cart
        user.removeFromCart("i2");
        System.out.println("\nCart after removing an item:");
        System.out.println(user.viewCart());

        // Checkout
        Order order = user.checkout();
        System.out.println("\nOrder Details:");
        System.out.println(order);

        // Make payment
        Payment payment = user.makePayment(order, "Credit Card");
        System.out.println("\nPayment Details:");
        System.out.println(payment);
    }
}
