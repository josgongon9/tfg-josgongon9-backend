package com.josgongon9.tfgwebbackend.repository;

import java.util.List;
import java.util.Optional;

import com.josgongon9.tfgwebbackend.model.ERole;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import com.josgongon9.tfgwebbackend.model.User;
import org.springframework.data.mongodb.repository.Query;


public interface UserRepository extends MongoRepository<User, String> {
  Optional<User> findByUsername(String username);
  
  List<User> findAllByUsernameIn(List<String> username);

  Boolean existsByUsername(String username);

  Boolean existsByEmail(String email);

  @Query("{'roles' :{'$ref' : 'roles' , '$id' : ?0}}")
  List<User> findAllByRole(ObjectId id);

  List<User> findUserByOrganizations(ObjectId id);

  @Query("{'vacations' :{'$ref' : 'vacations' , '$id' : ?0}}")
  List<User> findUserByVacation(ObjectId id);

  List<User> findAllByUsernameLike(String username);

  /*@Query(value = "{'organizations' :{'$ref' : 'organizations' , '$id' : ?0}}", delete = true)
  void deleteOrganizationById(Object id);*/

  
}
