package com.example.demo.config;

import com.example.demo.dao.CustomerRepository;
import com.example.demo.dao.DivisionRepository;
import com.example.demo.entities.Customer;
import com.example.demo.entities.Division;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BootStrapData implements CommandLineRunner {

    private final CustomerRepository customerRepository;
    private final DivisionRepository divisionRepository;

    public BootStrapData(CustomerRepository customerRepository, DivisionRepository divisionRepository) {
        this.customerRepository = customerRepository;
        this.divisionRepository = divisionRepository;
    }

    @Override
    public void run(String... args) {

        if (customerRepository.count() > 1) {
            return;
        }

        // existing Division to assign to each Customer
        List<Division> divisions = divisionRepository.findAll();
        if (divisions.isEmpty()) {
            // No divisions available, so we can't create valid customers
            return;
        }

        Division d1 = divisions.get(0);
        Division d2 = divisions.size() > 1 ? divisions.get(1) : d1;
        Division d3 = divisions.size() > 2 ? divisions.get(2) : d1;

        // 5 sample customers
        Customer c1 = new Customer();
        c1.setFirstName("Alex");
        c1.setLastName("Example");
        c1.setAddress("100 Main St");
        c1.setPostal_code("12345");
        c1.setPhone("555-0001");
        c1.setDivision(d1);

        Customer c2 = new Customer();
        c2.setFirstName("Jamie");
        c2.setLastName("Example");
        c2.setAddress("200 Main St");
        c2.setPostal_code("23456");
        c2.setPhone("555-0002");
        c2.setDivision(d2);

        Customer c3 = new Customer();
        c3.setFirstName("Taylor");
        c3.setLastName("Example");
        c3.setAddress("300 Main St");
        c3.setPostal_code("34567");
        c3.setPhone("555-0003");
        c3.setDivision(d3);

        Customer c4 = new Customer();
        c4.setFirstName("Casey");
        c4.setLastName("Example");
        c4.setAddress("400 Main St");
        c4.setPostal_code("45678");
        c4.setPhone("555-0004");
        c4.setDivision(d1);

        Customer c5 = new Customer();
        c5.setFirstName("Jordan");
        c5.setLastName("Example");
        c5.setAddress("500 Main St");
        c5.setPostal_code("56789");
        c5.setPhone("555-0005");
        c5.setDivision(d2);

        customerRepository.saveAll(List.of(c1, c2, c3, c4, c5));
    }
}