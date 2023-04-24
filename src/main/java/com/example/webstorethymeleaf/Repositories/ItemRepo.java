package com.example.webstorethymeleaf.Repositories;

import com.example.webstorethymeleaf.POJO.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepo extends JpaRepository<Item,Long> {
}
