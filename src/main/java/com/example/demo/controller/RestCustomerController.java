package com.example.demo.controller;

import com.example.demo.model.Customer;
import com.example.demo.service.ICustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    public String handleError(){
        return "error";
    }
    @GetMapping
    public  ResponseEntity<Iterable<Customer>> showAll(){
        List<Customer> customerList= (List<Customer>) customerService.findAll();
        if(customerList.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        }
        return new ResponseEntity<>(customerList,HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Customer> customer(@PathVariable Long id){
        Optional<Customer> customerOptional= customerService.findById(id);
        if(!customerOptional.isPresent()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(customerOptional.get(), HttpStatus.OK);
    }
@PostMapping("/search")
public ResponseEntity<Page<Customer>> findByCustomer(@RequestParam Optional<String> search, Pageable pageable){
    Page<Customer> page = customerService.findAll(pageable);
    if (page.isEmpty()){
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    if (search.isPresent()){
        return new ResponseEntity<>(customerService.findAllByNameContaining(search.get(),pageable),HttpStatus.OK);
    }
    return new ResponseEntity<>(page,HttpStatus.OK);
}
    @PostMapping
    public ResponseEntity<Customer> createCustomer(@RequestBody Optional<Customer> customer){
        if(customer.get().getFirstName().isEmpty()|| customer.get().getLastName().isEmpty()){
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
