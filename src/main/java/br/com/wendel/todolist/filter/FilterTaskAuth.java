package br.com.wendel.todolist.filter;

import java.io.IOException;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import at.favre.lib.crypto.bcrypt.BCrypt;
import br.com.wendel.todolist.user.IUserRepository;
// servlet = Base qualquer framework do java para Web
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

// Filter parece com middleware
@Component // Notação que permite o spring boot gerência a classe
public class FilterTaskAuth extends OncePerRequestFilter {
  // Estamos modificando o código por causa do http. O filter é necessário converter os http e Once.. já da completo.
  @Autowired 
  private IUserRepository userRepository; // Vamos utilizar esse método do user para verificar se o usuário já existe no banco de dados

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
    throws ServletException, IOException {
      
    var servletPath = request.getServletPath();
    // startWith -> Começar com path
    if(servletPath.startsWith("/tasks/")){
      // Pegar a autenticação (usuário e senha)
      var authorization = request.getHeader("Authorization");

      /* Conveter o dado recebido pela authorization 
      * - 1 - Remover o nome Basic
      * - 2 - Utilizar o decode para decodificar o código
      * - 3 - Conveter o código decodificado para string
      * - 4 - Por fim, separa em username e password
      */
      var authEncodec = authorization.substring("Basic".length()).trim();  

      byte[] authDecode = Base64.getDecoder().decode(authEncodec);

      var authString = new String(authDecode);

      String[] credentials = authString.split(":");

      String username = credentials[0];
      String password = credentials[1];

      // Validar usuário
      var user = this.userRepository.findByUsername(username);

      if(user == null) {
        response.sendError(401);
      } else {
        // Validar senha
        // o password tem que ser um array de caracteres
        var passwordVerify = BCrypt.verifyer().verify(password.toCharArray(), user.getPassword());
        
        if(passwordVerify.verified) {
          // Adicionar o id na requisição a partir do user encontrado
          request.setAttribute("idUser", user.getId());
          // doFilter permite que a requisição continue para controller.
          filterChain.doFilter(request, response);
        }
      }
    }else {
      filterChain.doFilter(request, response);
    } 
  }
}
