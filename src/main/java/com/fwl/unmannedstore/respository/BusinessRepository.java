package com.fwl.unmannedstore.respository;

import com.fwl.unmannedstore.model.Business;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BusinessRepository extends JpaRepository<Business, Integer> {
}
