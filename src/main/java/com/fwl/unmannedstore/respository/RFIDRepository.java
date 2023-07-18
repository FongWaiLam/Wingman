package com.fwl.unmannedstore.respository;

import com.fwl.unmannedstore.model.Product;
import com.fwl.unmannedstore.model.RFID;
import com.fwl.unmannedstore.model.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RFIDRepository extends JpaRepository<RFID, String> {

    public List<RFID> findByProduct(Product product);
    public List<RFID> findByProductAndStore(Product product, Store store);
}
