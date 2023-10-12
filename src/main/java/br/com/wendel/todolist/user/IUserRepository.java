package br.com.wendel.todolist.user;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;


public interface IUserRepository extends JpaRepository<UserModel, UUID>{
  // Verificando se já existe esse username
  UserModel findByUsername(String username);
  
}