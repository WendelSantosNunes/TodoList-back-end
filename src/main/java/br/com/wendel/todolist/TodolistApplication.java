package br.com.wendel.todolist;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// Decoreto que indicar que é a classe inicial do projeto
@SpringBootApplication
public class TodolistApplication {

	public static void main(String[] args) {
		// Roda um container por baixo da aplicação
		SpringApplication.run(TodolistApplication.class, args);
	}

}
