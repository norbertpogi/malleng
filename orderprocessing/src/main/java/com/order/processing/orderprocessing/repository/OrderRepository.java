package com.order.processing.orderprocessing.repository;

import com.order.processing.orderprocessing.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, String> {
//    @Override
//    default <S extends Order> List<S> saveAll(Iterable<S> entities) {
//        System.out.println("Saving Orders: " + ((List<Order>) entities).size());
//        return JpaRepository.super.saveAll(entities);
//    }
}
