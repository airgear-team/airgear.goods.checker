package com.airgear.controller;

import com.airgear.dto.CheckResult;
import com.airgear.service.ContentCheckerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class TextCheckerController {

    @Autowired
    private ContentCheckerService contentCheckerService;

    @PostMapping("/check-text")
    public ResponseEntity<CheckResult> checkText(@RequestBody String text) {
        CheckResult checkResult = contentCheckerService.checkContent(text);
        return ResponseEntity.ok(checkResult);
    }
}
