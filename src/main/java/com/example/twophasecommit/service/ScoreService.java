package com.example.twophasecommit.service;

import com.example.twophasecommit.entity.Score;
import com.example.twophasecommit.repository.DbRepository;
import com.example.twophasecommit.repository.JmsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


@Service
public class ScoreService {

    @Autowired
    private DbRepository dbRepository;

    @Autowired
    private JmsRepository jmsRepository;
    @Autowired
    protected ApplicationEventPublisher publisher;

    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = DataAccessException.class)
    public void save(Score score) {
        score=dbRepository.save(score);
        publisher.publishEvent(score);
    }


}
