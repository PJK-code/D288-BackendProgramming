package com.example.demo.services;

import com.example.demo.dao.CartRespository;
import com.example.demo.dao.CustomerRepository;
import com.example.demo.entities.Cart;
import com.example.demo.entities.CartItem;
import com.example.demo.entities.Customer;
import com.example.demo.entities.StatusType;
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
    public PurchaseResponse placeOrder(Purchase purchase) {

        // retrieve the order info from dto
        Cart cart = purchase.getCart();
        Customer customer = purchase.getCustomer();

        //set cart status to 'ordered'
        cart.setStatus(StatusType.ordered);

        //generate tracking number
        String orderTrackingNumber = generateOrderTrackingNumber();
        cart.setOrderTrackingNumber(orderTrackingNumber);

        //populate order with orderItems
        Set<CartItem> cartItems = purchase.getCartItems();
        cartItems.forEach(item -> cart.add(item));

        // populate customer with order
        customer.add(cart);

        //save to the database
        customerRepository.save(customer);

        //return a response

        return new PurchaseResponse(orderTrackingNumber);

    }

    private String generateOrderTrackingNumber() {
        //generate a random UUID number
        //UUID is Universally Unique Identifier
        return UUID.randomUUID().toString();

    }
}
