package com.josgongon9.tfgwebbackend.repository;

import java.util.List;

import com.josgongon9.tfgwebbackend.model.Organization;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.josgongon9.tfgwebbackend.model.Vacation;
import org.springframework.stereotype.Repository;

@Repository
public interface VacationRepository extends MongoRepository<Vacation, String> {
    List<Vacation> findByPublished(boolean published);

    List<Vacation> findByTitleContaining(String title);

    List<Vacation> findAllByTitleLike(String title);
}