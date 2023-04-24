package com.example.webstorethymeleaf.POJO;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Customer {

    @Id
    @GeneratedValue
    private Long id;

    protected String name;
    protected String ssn;

    public Customer(String name, String ssn){
        this.name = name;
        this.ssn = ssn;
    }

    public void addOrder(Order order) {
    }
}
