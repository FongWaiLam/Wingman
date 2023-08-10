package com.fwl.unmannedstore.respository;

import com.fwl.unmannedstore.controller.requestResponse.InventoryDisplay;
import com.fwl.unmannedstore.model.Product;
import com.fwl.unmannedstore.model.RFID;
import com.fwl.unmannedstore.model.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RFIDRepository extends JpaRepository<RFID, String> {

    public List<RFID> findByProduct(Product product);
    public List<RFID> findByProductAndStore(Product product, Store store);

    @Query("SELECT new com.fwl.unmannedstore.controller.requestResponse.InventoryDisplay(r.product.prodId, r.product.name, r.product.category, COUNT(r.epc), SUM(r.product.price), r.store.storeId, r.store.name) " +
            "FROM RFID r " +
            "WHERE r.isSold = false " +
            "GROUP BY r.product.prodId, r.store.storeId")
    List<InventoryDisplay> findAllUnsoldProductTotalValueAndQuantity();

    @Query("SELECT new com.fwl.unmannedstore.controller.requestResponse.InventoryDisplay(r.product.prodId, r.product.name, r.product.category, COUNT(r.epc), SUM(r.product.price)) " +
            "FROM RFID r " +
            "WHERE r.isSold = false " +
            "GROUP BY r.product.prodId")
    List<InventoryDisplay> findAllUnsoldProductTotalValueAndQuantityByCatIgnoreStore();

    @Query("SELECT new com.fwl.unmannedstore.controller.requestResponse.InventoryDisplay(r.product.prodId, r.product.name, r.product.category, COUNT(r.epc), SUM(r.product.price), r.store.storeId, r.store.name) " +
            "FROM RFID r " +
            "WHERE r.isSold = false AND r.product.category = :category " +
            "GROUP BY r.product.prodId, r.store.storeId")
    List<InventoryDisplay> findAllUnsoldProductTotalValueAndQuantityByCat(@Param("category") String category);

//    @Query("SELECT new com.fwl.unmannedstore.controller.requestResponse.InventoryDisplay(COUNT(r.epc), SUM(r.product.price), r.store.storeId, r.store.name) " +
//            "FROM RFID r " +
//            "WHERE r.isSold = false " +
//            "GROUP BY r.store.storeId, r.store.name " +
//            "ORDER BY r.store.storeId")

    @Query("SELECT " +
            "new com.fwl.unmannedstore.controller.requestResponse.InventoryDisplay(COUNT(r.epc), " +
            "SUM(p.price) AS totalValue, " +
            "s.storeId, " +
            "s.name) " +
            "FROM RFID r " +
            "JOIN r.product p " +
            "JOIN r.store s " +
            "WHERE r.isSold = false " +
            "GROUP BY s.storeId, s.name " +
            "ORDER BY s.storeId")
    List<InventoryDisplay> findAllUnsoldProductTotalValueAndQuantityByStore();

//    @Query("SELECT " +
//            "new com.fwl.unmannedstore.controller.requestResponse.InventoryDisplay(COUNT(r.epc), " +
//            "SUM(p.price) AS totalValue, " +
//            "s.storeId, " +
//            "s.name) " +
//            "FROM RFID r " +
//            "JOIN r.product p " +
//            "JOIN r.store s " +
//            "WHERE r.isSold = false AND s.storeId = :storeId " +
//            "GROUP BY s.storeId, s.name " +
//            "ORDER BY s.storeId")
//    List<InventoryDisplay> findAllUnsoldProductTotalValueAndQuantityByStore(@Param("storeId") int storeId);

    @Query("SELECT new com.fwl.unmannedstore.controller.requestResponse.InventoryDisplay(r.product.prodId, r.product.name, r.product.category, COUNT(r.epc), SUM(r.product.price), r.store.storeId, r.store.name) " +
            "FROM RFID r " +
            "WHERE r.isSold = false AND r.store.storeId = :storeId " +
            "GROUP BY r.product.prodId, r.store.storeId")
    List<InventoryDisplay> findAllUnsoldProductTotalValueAndQuantityInAStore(@Param("storeId") int storeId);

}
