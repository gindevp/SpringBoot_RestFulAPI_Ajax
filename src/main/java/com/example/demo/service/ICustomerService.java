package com.example.demo.service;

import com.example.demo.model.Customer;
import com.example.demo.repository.ICustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

public interface ICustomerService extends IGeneralService<Customer> {
    Page<Customer> findAll(Pageable pageable);
    Page<Customer> findAllByNameContaining(String name, Pageable pageable);
}
