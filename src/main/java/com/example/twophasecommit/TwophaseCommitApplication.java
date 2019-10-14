package com.example.twophasecommit;

import com.example.twophasecommit.entity.Score;
import com.example.twophasecommit.service.ScoreService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.Random;

@SpringBootApplication
public class TwophaseCommitApplication {


	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(TwophaseCommitApplication.class, args);

		ScoreService service = context.getBean(ScoreService.class);

		Score score = new Score();
		//score.setId(new Random().nextLong());
		score.setSname("bot");
		score.setCourse("Software Development");
		score.setScore(700);

		service.save(score);

		// context.stop();
		// context.close();
	}



}
