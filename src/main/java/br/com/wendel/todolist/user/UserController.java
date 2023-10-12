package br.com.wendel.todolist.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import at.favre.lib.crypto.bcrypt.BCrypt;

@RestController
@RequestMapping("/users")
public class UserController {
  @Autowired // Spring pode gerenciar todo o ciclo de vinda (Faça o que for possível para eu utilizar)
  private IUserRepository userRepository;

  @PostMapping("/")
  public ResponseEntity create(@RequestBody UserModel userModel){
    var user = this.userRepository.findByUsername(userModel.getUsername());
    
    if (user != null){
      // Mensagem de erro
      // Status code
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Usuário já existe");
    }

    // withDefaults = Configurações padrões
    // hashToString = E o tipo da hask
    // Os parâmentro da hashToString
      // 1 - Força da senha
      // 2 - Senha (A senha tem ser um array de char)
    var passordHashred = BCrypt.withDefaults().hashToString(12, userModel.getPassword().toCharArray());
    
    // Passando a senha criptografada
    userModel.setPassword(passordHashred);

    var userCREATED = this.userRepository.save(userModel); // Criado um usuário no banco de dados e retornado para navegador 
    
    return ResponseEntity.status(HttpStatus.CREATED).body(userCREATED);
  }
}
