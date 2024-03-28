package com.airgear.scheduler;

import com.airgear.dto.CheckResult;
import com.airgear.model.GoodsVerificationStatus;
import com.airgear.model.SimpleGoods;
import com.airgear.service.ContentCheckerService;
import com.airgear.service.SimpleGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GoodsReviewScheduler {

    private static final int TIME_INTERVAL = 60000;
    @Autowired
    private SimpleGoodsService goodsService;

    @Autowired
    ContentCheckerService contentCheckerService;

    @Scheduled(fixedRate = TIME_INTERVAL)
    public void reviewGoods() {
        int page = 0;
        int size = 100;
        List<SimpleGoods> goodsList;

        do {
            goodsList = goodsService.getGoodsForReview(page, size);

            for (SimpleGoods goods : goodsList) {
                String contentToCheck = goods.getName() + " " + goods.getDescription();
                System.out.println("Checking content: " + contentToCheck); // TODO: remove this line
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
