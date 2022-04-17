package com.jb.coupon2.service;

import com.jb.coupon2.beans.Company;
import com.jb.coupon2.beans.Customer;
import com.jb.coupon2.exception.AdminServiceException;
import com.jb.coupon2.exception.LoginException;
import lombok.Getter;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Getter
public class AdminService extends ClientService {
    private final String USER_EMAIL = "admin@admin.com";
    private final String USER_PASS = "admin";
    private final int ID = 0;

    public void addCompany(Company company) throws AdminServiceException {
        Optional<Company> checkName = companyRepo.findByName(company.getName());
        Optional<Company> checkEmail = companyRepo.findByEmail(company.getEmail());
        if (checkEmail.isPresent() || checkName.isPresent()) {
            throw new AdminServiceException("The is already a company with the same name or email.");
        } else {
            companyRepo.save(company);
            System.out.println("Company was added!");
        }
    }

    public void updateCompany(Company company) {
        try {
            companyRepo.saveAndFlush(company);
        } catch (Exception err) {
            System.out.println(err.getMessage());
        }
    }

    public void deleteCompany(int companyId) {
        couponRepo.deleteCouponPurchasedHistory(companyId);
        couponRepo.deleteCouponByCompanyId(companyId);
        companyRepo.deleteById(companyId);
        System.out.println("Company was deleted.");
    }

    public List<Company> getAllCompanies() {
        return companyRepo.findAll();
    }

    public Company getOneCompany(int companyId) throws AdminServiceException {
        Optional<Company> companyOptional = companyRepo.findById(companyId);
        if (companyOptional.isPresent()) {
            return companyOptional.get();
        } else {
            throw new AdminServiceException("Company was not found.");
        }
    }

    public void addCustomer(Customer customer) throws AdminServiceException {
        if (!customerRepo.existsByEmail(customer.getEmail())) {
            customerRepo.save(customer);
            System.out.println("Customer was added.");
        } else {
            throw new AdminServiceException("Customer with the same email already exist.");
        }
    }

    public void updateCustomer(Customer customer) {
        try {
            customerRepo.saveAndFlush(customer);
            System.out.println("Customer was updated!");
        } catch (Exception err) {
            System.out.println(err.getMessage());
        }
    }

    public void deleteCustomer(int customerId) {
        customerRepo.deleteById(customerId);
        System.out.println("Customer was deleted");
    }

    public List<Customer> getAllCustomers() {
        return customerRepo.findAll();
    }

    public Customer getOneCustomer(int customerId) throws AdminServiceException {
        Optional<Customer> customerOptional = customerRepo.findById(customerId);
        if (customerOptional.isPresent()) {
            return customerOptional.get();
        } else {
            throw new AdminServiceException("Customer was not found.");
        }
    }

    @Override
    public boolean login(String email, String password) throws LoginException {
        if (email.equals(this.USER_EMAIL) && password.equals(this.USER_PASS)) {
            System.out.println("Logged in successfully.");
            return true;
        } else {
            return false;
        }
    }

    @Override
    public int getId() {
        return this.ID;
    }
}
