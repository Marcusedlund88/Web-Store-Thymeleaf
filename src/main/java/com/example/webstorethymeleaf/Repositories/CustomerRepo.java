package com.example.webstorethymeleaf.Repositories;

import com.example.webstorethymeleaf.POJO.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepo extends JpaRepository<Customer,Long> {
}
