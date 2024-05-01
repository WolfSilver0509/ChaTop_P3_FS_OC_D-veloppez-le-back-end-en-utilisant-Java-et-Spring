package com.p3_oc_fs.chatop.repositorys;

import com.p3_oc_fs.chatop.models.Rental;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RentalRepository extends JpaRepository<Rental, Integer> {
}