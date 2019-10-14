package com.example.twophasecommit.listener;

import com.example.twophasecommit.entity.Score;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component("scoreEventNotifier")
public class EventNotifier {
    @Autowired
    NotifierTemplate notifier;
    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    public void scoreCreatedEvent(Score score){
      notifier.notify(score);
    }

}
