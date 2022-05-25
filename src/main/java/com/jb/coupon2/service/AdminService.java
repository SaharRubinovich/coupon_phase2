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

    /**
     *add company to db method
     * @param company - receive company instance to add into the db.
     * @throws AdminServiceException - if the email and name of the company we want to add
     * already exist within the db, throw custom exception to inform the user.
     */
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

    /**
     * update company method
     * @param company - company instance
     * @throws AdminServiceException - if the company isn't found in the db or there was an ateempt
     * to change the company id or name.
     */
    public void updateCompany(Company company) throws AdminServiceException {
        Optional<Company> dbCompany = companyRepo.findByName(company.getName());
        if (dbCompany.isEmpty()){
            throw new AdminServiceException("Company doesn't exist in database.");
        } else  if (dbCompany.get().getId() != company.getId() || !dbCompany.get().getName().equals(company.getName())){
            throw new AdminServiceException("Cannot change company Id and name.");
        }
        companyRepo.saveAndFlush(company);
    }

    /**
     * delete company from db method
     * @param companyId - integer of company id
     * uses repo to delete safely company instance from the db.
     * first delete the history of the company coupon purchases, then delete all the coupons that contains the company
     * id from the db and lastly delete the company itself so the connection between all of them won't interfere the
     * deletion process.
     */
    public void deleteCompany(int companyId) {
        couponRepo.deleteCouponPurchasedHistory(companyId);
        couponRepo.deleteCouponByCompanyId(companyId);
        companyRepo.deleteById(companyId);
        System.out.println("Company was deleted.");
    }

    /**
     * get list of all the companies in the db method
     * @return list of companies inside the db.
     */
    public List<Company> getAllCompanies() {
        return companyRepo.findAll();
    }

    /**
     * get 1 company instance method
     * @param companyId - integer of the wanted company id.
     * @return company instance if existed.
     * @throws AdminServiceException - if company wasn't found with the wanted id will inform the user on it.
     */
    public Company getOneCompany(int companyId) throws AdminServiceException {
        Optional<Company> companyOptional = companyRepo.findById(companyId);
        if (companyOptional.isPresent()) {
            return companyOptional.get();
        } else {
            throw new AdminServiceException("Company was not found.");
        }
    }

    /**
     * add customer to db method
     * @param customer - customer instance.
     * @throws AdminServiceException - if customer with the same email already exist in the db will inform the user
     * about it.
     */
    public void addCustomer(Customer customer) throws AdminServiceException {
        if (!customerRepo.existsByEmail(customer.getEmail())) {
            customerRepo.save(customer);
            System.out.println("Customer was added.");
        } else {
            throw new AdminServiceException("Customer with the same email already exist.");
        }
    }

    /**
     * updateing customer method
     * @param customer - customer instance we want to use to update
     * @throws AdminServiceException - if the customer doesn't exist in the db or the new update trying to change its
     * id in the db will trow exception to inform the user accordingly.
     */
    public void updateCustomer(Customer customer) throws AdminServiceException {
        if (!customerRepo.existsByEmail(customer.getEmail())){
            throw new AdminServiceException("Customer was not found.");
        }
        if (customerRepo.getById(customer.getId()).getId() != customer.getId()){
            throw new AdminServiceException("Cannot update customer id.");
        }
        customerRepo.saveAndFlush(customer);
        System.out.println("Customer was updated!");
    }

    /**
     * delete customer from db method.
     * @param customerId - integer if the wanted customer to delete.
     */
    public void deleteCustomer(int customerId) {
        customerRepo.deleteById(customerId);
        System.out.println("Customer was deleted");
    }

    /**
     * get a list of all customer from db method.
     * @return list of customers.
     */
    public List<Customer> getAllCustomers() {
        return customerRepo.findAll();
    }

    /**
     * get customer instance from db.
     * @param customerId - integer of the wanted customer id.
     * @return - customer instance if found in the db.
     * @throws AdminServiceException - if customer wasn't found matching the wanted id will throw exception and inform
     * the user.
     */
    public Customer getOneCustomer(int customerId) throws AdminServiceException {
        Optional<Customer> customerOptional = customerRepo.findById(customerId);
        if (customerOptional.isPresent()) {
            return customerOptional.get();
        } else {
            throw new AdminServiceException("Customer was not found.");
        }
    }

    /**
     * login method
     * @param email - the admin email.
     * @param password - the admin password
     * @return - true or false
     */
    @Override
    public boolean login(String email, String password){
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
