package com.jb.coupon2.repository;

import com.jb.coupon2.beans.Company;
import com.jb.coupon2.beans.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CompanyRepo extends JpaRepository<Company,Integer> {
    Optional<Company> findByEmailAndPassword(String email, String password);
    Company findCompanyByEmailAndPassword(String email, String password);
    Optional<Company> findByName(String name);
    Optional<Company> findByEmail(String email);
    boolean existsByEmailAndPassword(String email, String password);
}
