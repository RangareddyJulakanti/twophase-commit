package com.example.twophasecommit;

import com.example.twophasecommit.entity.Score;
import com.example.twophasecommit.service.ScoreService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class TwoPhaseCommitApplication {
	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(TwoPhaseCommitApplication.class, args);
		ScoreService service = context.getBean(ScoreService.class);
		Score score = Score.builder().sname("bot")
				.course("software development")
				.score(700).build();
		service.save(score);
	}



}
