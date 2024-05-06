package com.example.discussion.web;

import com.example.discussion.entities.Subscriber;
import com.example.discussion.repositories.SubscriberRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class SubcriberRestController {
    @Autowired
    private SubscriberRepository subscriberRepository;

    @GetMapping("/subscribers")
    public List<Subscriber> getAllSubscribers(){
        return subscriberRepository.findAll();
    }

    @GetMapping("/subscriber/{id}")
    public Subscriber getSubscriberById(@PathVariable Long id){
        return subscriberRepository.findById(id).orElse(null);
    }

    @PostMapping("/subscriber")
    public Subscriber createSubscriber(@RequestBody @Valid Subscriber subscriber){
        subscriberRepository.save(subscriber);
        return subscriber;
    }
}
