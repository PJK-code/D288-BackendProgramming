package com.example.demo.services;

import com.example.demo.dao.CartRespository;
import com.example.demo.dao.CustomerRepository;
import com.example.demo.entities.Cart;
import com.example.demo.entities.CartItem;
import com.example.demo.entities.Customer;
import com.example.demo.entities.StatusType;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.UUID;

@Service
public class CheckoutServiceImpl implements CheckoutService{

    @Autowired
    private CustomerRepository customerRepository;
    private CartRespository cartRespository;

    public CheckoutServiceImpl(CustomerRepository customerRepository, CartRespository cartRespository) {
        this.customerRepository = customerRepository;
        this.cartRespository = cartRespository;
    }

    @Override
    @Transactional
    public PurchaseResponse placeOrder(Purchase purchase) {

        // retrieve the order info from dto
        Cart cart = purchase.getCart();
        Customer customer = purchase.getCustomer();

        // prevent placing an order with an empty cart
        if (cart == null) {
            return new PurchaseResponse("Error: cart can't be empty");
        }

        //populate cart with cart items
        Set<CartItem> cartItems = purchase.getCartItems();
        cartItems.forEach(item -> cart.add(item));

        // cart must have items after population
        if (cart.getCartItems() == null || cart.getCartItems().isEmpty()) {
            return new PurchaseResponse("Error: cart can't be empty");
        }

        //generate tracking number
        String orderTrackingNumber = generateOrderTrackingNumber();
        cart.setOrderTrackingNumber(orderTrackingNumber);

        // populate customer with order
        customer.add(cart);

        //set cart status to 'ordered'
        cart.setStatus(StatusType.ordered);

        //save to the database
        customerRepository.save(customer);

        //return success response with tracking number
        if (cart == null || cart.getCartItems() == null || cart.getCartItems().isEmpty()) {
            return new PurchaseResponse("Error: cart can't be empty");
        } else {
            return new PurchaseResponse(orderTrackingNumber);
        }
    }

    private String generateOrderTrackingNumber() {
        //generate a random UUID number
        //UUID is Universally Unique Identifier
        return UUID.randomUUID().toString();

    }
}

//validation code that I couldn't get to work and I might come back to
// DEBUG
        /*System.out.println("Cart: " + cart);
        System.out.println("Cart items from cart: " + (cart != null ? cart.getCartItems() : "cart is null"));
        if (cart != null && cart.getCartItems() != null) {
            System.out.println("Number of cart items: " + cart.getCartItems().size());
            for (CartItem item : cart.getCartItems()) {
                System.out.println("  Cart item: " + item);
                System.out.println("  Has vacation: " + (item.getVacation() != null));
            }
        }

        // Check if cart or cartItems is empty
        if (cart == null || cart.getCartItems() == null || cart.getCartItems().isEmpty()) {
            return new PurchaseResponse("Error: Cart is empty");
        }

        // Check if the first cart item has a vacation
        CartItem firstItem = cart.getCartItems().iterator().next();
        if (firstItem.getVacation() == null) {
            return new PurchaseResponse("Error: Cart is empty");
        }*/


