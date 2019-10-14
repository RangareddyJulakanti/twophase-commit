package com.example.twophasecommit.repository;

import com.example.twophasecommit.entity.Score;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public interface DbRepository extends CrudRepository<Score,Long> {

}
