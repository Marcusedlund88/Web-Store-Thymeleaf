package com.example.webstorethymeleaf.Repositories;

import com.example.webstorethymeleaf.POJO.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepo extends JpaRepository<Order,Long> {
}
