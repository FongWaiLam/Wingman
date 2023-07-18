package com.fwl.unmannedstore.respository;

import com.fwl.unmannedstore.model.SalesRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SalesRecordRepository extends JpaRepository<SalesRecord, Integer> {
}
