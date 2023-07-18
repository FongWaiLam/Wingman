package com.fwl.unmannedstore.respository;

import com.fwl.unmannedstore.model.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StoreRepository extends JpaRepository<Store, Integer> {
}
