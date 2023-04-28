package com.example.webstorethymeleaf.POJO;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.Parent;

import java.time.LocalDate;
import java.util.ArrayList;
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


    @OneToOne
    @JoinColumn(name = "customer_id", foreignKey = @ForeignKey(name = "fk_customer", value = ConstraintMode.NO_CONSTRAINT))
    private Customer customer;


    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(
            name = "order_items",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "item_id"),
            foreignKey = @ForeignKey(name = "fk_order", value = ConstraintMode.NO_CONSTRAINT),
            inverseForeignKey = @ForeignKey(name = "fk_item", value = ConstraintMode.NO_CONSTRAINT)
    )
    private List<Item> items = new ArrayList<>();

    public Order(LocalDate ld, Customer customer, List<Item> items){
        this.customer = customer;
        this.date = ld;
        this.items = items;
    }

}