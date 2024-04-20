package com.airgear.repository;

import com.airgear.model.Goods;
import com.airgear.model.GoodsVerificationStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GoodsRepository extends JpaRepository<Goods, Long> {
    Page<Goods> findByVerificationStatus(GoodsVerificationStatus status, Pageable pageable);
}