package br.com.wendel.todolist.user;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity(name = "tb_users") // Nomeado a tabela no banco de dados
public class UserModel {
  @Id // Estamos informando para banco que essa é a chave primária
  @GeneratedValue(generator = "UUID") // Gerar o valor automanticamente
  private UUID id; // Chave primária

  @Column(unique = true)
  private String username;
  private String name;
  private String password;

  @CreationTimestamp // Data de quando foi criando o dado no banco
  private LocalDateTime createdAt;
}
