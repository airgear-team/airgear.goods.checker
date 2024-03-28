package com.airgear.service;

import com.airgear.model.SimpleGoods;
import com.airgear.model.GoodsVerificationStatus;
import com.airgear.repository.SimpleGoodsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SimpleGoodsService {

    @Autowired
    private SimpleGoodsRepository goodsRepository;

    public List<SimpleGoods> getGoodsForReview(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<SimpleGoods> goodsPage = goodsRepository.findByVerificationStatus(GoodsVerificationStatus.ON_REVIEW, pageRequest);
        return goodsPage.getContent();
    }

    @Transactional
    public void updateGoodsStatus(Long goodsId, GoodsVerificationStatus newStatus) {
        SimpleGoods goods = goodsRepository.findById(goodsId)
                .orElseThrow(() -> new RuntimeException("Goods not found: " + goodsId));
        goods.setVerificationStatus(newStatus);
        goodsRepository.save(goods);
    }
}