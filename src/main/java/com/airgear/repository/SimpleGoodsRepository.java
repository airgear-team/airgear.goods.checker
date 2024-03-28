package com.airgear.repository;

import com.airgear.model.SimpleGoods;
import com.airgear.model.GoodsVerificationStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SimpleGoodsRepository extends JpaRepository<SimpleGoods, Long> {
    Page<SimpleGoods> findByVerificationStatus(GoodsVerificationStatus status, Pageable pageable);
}