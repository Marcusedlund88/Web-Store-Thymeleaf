package com.example.webstorethymeleaf.POJO;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue
    private Long id;
    private LocalDate date;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn
    private Customer customer;

    @OneToMany
    @JoinColumn
    private List<Item> items;

    public Order(LocalDate ld, Customer customer, List<Item> items){

        this.customer = customer;
        this.date = ld;
        this.items = items;
    }

}