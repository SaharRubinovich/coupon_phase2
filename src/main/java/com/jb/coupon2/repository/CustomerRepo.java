package com.jb.coupon2.repository;

import com.jb.coupon2.beans.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepo extends JpaRepository<Customer,Integer> {
    boolean existsCustomerByEmailAndPassword(String email, String password);
    Customer findCustomerByEmailAndPassword(String email, String password);
    boolean existsByEmail(String email);
    Optional<?> findByEmailAndPassword(String email, String password);

}
