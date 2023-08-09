package com.fwl.unmannedstore.respository;

import com.fwl.unmannedstore.controller.requestResponse.InventoryDisplay;
import com.fwl.unmannedstore.controller.requestResponse.SalesDisplay;
import com.fwl.unmannedstore.model.SalesRecord;
import com.fwl.unmannedstore.model.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface SalesRecordRepository extends JpaRepository<SalesRecord, Integer> {
    public List<SalesRecord> findByStore(Store store);

    @Query(
            value = "Select * from sales_record s where s.transaction_date_time >= :start And s.transaction_date_time <= :end And s.store_id = :storeId",
            nativeQuery = true
    )
    public List<SalesRecord> findByPeriod(@Param("start") Date start, @Param("end") Date end, @Param("store") int storeId);


    @Query("SELECT new com.fwl.unmannedstore.controller.requestResponse.SalesDisplay(YEAR(s.transactionDateTime), MONTH(s.transactionDateTime), SUM(s.amountInPence)/100) " +
            "FROM SalesRecord s " +
            "GROUP BY YEAR(s.transactionDateTime), MONTH(s.transactionDateTime)")
    List<SalesDisplay> findSumSalesByMonthAndYear();

}
