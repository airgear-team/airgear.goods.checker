package com.airgear.scheduler;

import com.airgear.dto.CheckResult;
import com.airgear.model.Goods;
import com.airgear.model.GoodsVerificationStatus;
import com.airgear.service.ContentCheckerService;
import com.airgear.service.GoodsService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@AllArgsConstructor
public class GoodsReviewScheduler {

    private static final int TIME_INTERVAL = 60000;

    private GoodsService goodsService;

    private ContentCheckerService contentCheckerService;

    @Scheduled(fixedRate = TIME_INTERVAL)
    public void reviewGoods() {
        int page = 0;
        int size = 100;
        List<Goods> goodsList;

        do {
            goodsList = goodsService.getGoodsForReview(page, size);

            for (Goods goods : goodsList) {
                String contentToCheck = goods.getName() + " " + goods.getDescription();
                CheckResult checkResult = contentCheckerService.checkContent(contentToCheck);

                if (checkResult.isClean()) {
                    goodsService.updateGoodsStatus(goods.getId(), GoodsVerificationStatus.APPROVED);
                } else {
                    goodsService.updateGoodsStatus(goods.getId(), GoodsVerificationStatus.REJECTED);
                }
            }
            page++;
        } while (!goodsList.isEmpty());
    }
}
