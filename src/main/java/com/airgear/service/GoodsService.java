package com.airgear.service;

import com.airgear.exception.GoodsExceptions;
import com.airgear.model.Goods;
import com.airgear.model.GoodsVerificationStatus;
import com.airgear.repository.GoodsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GoodsService {

    private GoodsRepository goodsRepository;

    public List<Goods> getGoodsForReview(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<Goods> goodsPage = goodsRepository.findByVerificationStatus(GoodsVerificationStatus.ON_REVIEW, pageRequest);
        return goodsPage.getContent();
    }

    @Transactional
    public void updateGoodsStatus(Long goodsId, GoodsVerificationStatus newStatus) {
        Goods goods = goodsRepository.findById(goodsId)
                .orElseThrow(() -> GoodsExceptions.goodsNotFound(goodsId));
        goods.setVerificationStatus(newStatus);
        goodsRepository.save(goods);
    }
}