package com.example.demo.controller;

import com.example.demo.model.Customer;
import com.example.demo.service.ICustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/api/customers")
public class RestCustomerController {
    @Autowired
    private ICustomerService customerService;
    @GetMapping
    public ResponseEntity<Iterable<Customer>> listCustomer(){
        List<Customer> customerIterable= (List<Customer>) customerService.findAll();
        if(customerIterable.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(customerIterable, HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Customer> customer(@PathVariable Long id){
        Optional<Customer> customerOptional= customerService.findById(id);
        if(!customerOptional.isPresent()){

            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(customerOptional.get(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Customer> createCustomer(@RequestBody Optional<Customer> customer){
        if(!customer.isPresent()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(customerService.save(customer.get()),HttpStatus.CREATED);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Customer> deleteCustomer(@PathVariable Long id ){
        Optional<Customer> customerOptional= customerService.findById(id);
        if(!customerOptional.isPresent()){

            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        customerService.remove(id);
        return new ResponseEntity<>(customerOptional.get(),HttpStatus.NO_CONTENT);
    }
    @PutMapping("/edit/{id}")
    public ResponseEntity<Customer> createCustomer(@PathVariable Long id, @RequestBody Customer customer){
        Optional<Customer> customerOptional= customerService.findById(id);
        if (!customerOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        customer.setId(customerOptional.get().getId());
        return new ResponseEntity<>(customerService.save(customer),HttpStatus.OK);
    }
}
