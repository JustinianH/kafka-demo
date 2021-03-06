package com.kafkademo.controllers;

import com.kafkademo.client.FilterServiceClient;
import com.kafkademo.models.ChangesInMe;
import com.kafkademo.producers.KafkaProducer;
import com.kafkademo.services.ChangesInMeService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class WebController {

    @Autowired
    KafkaProducer producer;

    private final ChangesInMeService changesInMeService;
    private final FilterServiceClient filterServiceClient;

//    Submit simple String

    @PostMapping("/api/message")
    public void submitString(@RequestBody String message) {
        producer.send("new-topic", message);
    }

    @PostMapping("/api/key-message")
    public void submitKeyMessage(@RequestParam String key, @RequestParam String message) {
        producer.sendMessageWithKey("new-topic", key, message);
    }

//    Submit payload as model

    @PostMapping("/api/changes-in-me")
    public void submitChangesInMe(@RequestBody ChangesInMe changesInMe) {
        producer.sendChangesInMe("changes-in-me", changesInMe);
    }

//  Retrieve data while sending param

    @GetMapping("/api/changes-in-me")
    public List<ChangesInMe> getFilteredChanges(@RequestParam Integer bloodSugarLevel) {
        return changesInMeService.filterChangesInMe(bloodSugarLevel);
    }

    @GetMapping("/api/changes-in-me/filter")
    public List<ChangesInMe> getFilteredChangesThroughClient(@RequestParam Integer bloodSugarLevel) {
        List<ChangesInMe> allChanges = changesInMeService.getAllChangesInMe();
         return filterServiceClient.filterChangesInMe(allChanges, bloodSugarLevel);
    }

}
