package com.vinay.jenkins.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.vinay.jenkins.model.Entity;

public interface EntityRepo extends MongoRepository<Entity,String> {

}
