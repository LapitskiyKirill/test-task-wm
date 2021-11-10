package com.gmail.kirilllapitsky.consumer.repository;

import com.gmail.kirilllapitsky.consumer.entity.Contract;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContractRepository extends JpaRepository<Contract, Long> {
}
