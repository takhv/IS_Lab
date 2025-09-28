package ru.itmo.humanbeingsystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ru.itmo.humanbeingsystem.repository.HumanBeingRepository;

@Service
@Transactional
public class HumanBeingServiceImpl {
    @Autowired
    private HumanBeingRepository repository;

    public HumanBeingServiceImpl(HumanBeingRepository repository){
        this.repository = repository;
    }


}
