package com.epam.esm.controller;

import com.epam.esm.service.StressTestService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/*
This is used at demo session to create all type of entity objects,
and it checks efficiency of service layer
 */

@RestController
@RequestMapping("stress-test")
public class StressTestController {

    private final StressTestService stressTestService;

    public StressTestController(StressTestService stressTestService) {
        this.stressTestService = stressTestService;
    }

    @GetMapping("/tag/{number}")
    public ResponseEntity createTag(@PathVariable Long number){
        return ResponseEntity.ok(stressTestService.createTag(number));
    }

    @GetMapping("/gift-certificate/{number}")
    public ResponseEntity createGiftCertificate(@PathVariable Long number){
        return ResponseEntity.ok(stressTestService.createGiftCertificate(number));
    }

    @GetMapping("/user/{number}")
    public ResponseEntity createUser(@PathVariable Long number){
        return ResponseEntity.ok(stressTestService.createUser(number));
    }

    @GetMapping("/order/{number}")
    public ResponseEntity createOrder(@PathVariable Long number){
        return ResponseEntity.ok(stressTestService.createOrder(number));
    }
}
