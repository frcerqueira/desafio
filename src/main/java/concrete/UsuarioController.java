package concrete;

import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UsuarioController {
	
	private static final int UM_MINUTO = 60000; // em milisegundos
	
	private final UsuarioRepository usuarioRepository;

	@Autowired
	UsuarioController(UsuarioRepository usuarioRepository) {
		this.usuarioRepository = usuarioRepository;
	}

	@RequestMapping("/cadastro")
	public ResponseEntity<?> adicionaUsuario(@RequestBody Usuario usuarioReq) {
		
		Usuario usuario;
		
		usuario = usuarioRepository.findByEmail(usuarioReq.getEmail());
		
		if (usuario != null) {
			return new ResponseEntity<>(new Mensagem("E-mail já existente"), HttpStatus.OK);
		}
		
		usuario = new Usuario(usuarioReq.getEmail(), usuarioReq.getNome(), usuarioReq.getPassword());
		usuario = usuarioRepository.save(usuario);
		return new ResponseEntity<>(usuario, HttpStatus.OK);
	}

	@RequestMapping("/login")
	public ResponseEntity<?> loginUsuario(@RequestBody Usuario usuarioReq) {
		
		Usuario usuario;
		
		usuario = usuarioRepository.findByEmail(usuarioReq.getEmail());
				
		if (usuario != null && 
				usuario.getPassword().equals(usuarioReq.getPassword())) {
			
			usuario.setLastLogin(new Date());
			usuario = usuarioRepository.save(usuario);
			return new ResponseEntity<>(usuario, HttpStatus.OK);
		}
		else {
			return new ResponseEntity<>(new Mensagem("Usuário e/ou senha inválidos"), HttpStatus.UNAUTHORIZED);
		}
	}
	
	@RequestMapping(value="/{id}/perfil", method=RequestMethod.GET)
	public ResponseEntity<?> perfilUsuario(@PathVariable Long id, @RequestHeader Map<String, String> reqHeaders) {
		
		String token = reqHeaders.get("token");
		
		if (token == null){
			return new ResponseEntity<>(new Mensagem("Não autorizado"), HttpStatus.UNAUTHORIZED);
		}
		
		Usuario usuario = usuarioRepository.findOne(id);
		
		if (usuario == null) {
			return new ResponseEntity<>(new Mensagem("Sessão inválida"), HttpStatus.UNAUTHORIZED);
		}
		else {
			if (!usuario.getToken().equals(token)) {
				return new ResponseEntity<>(new Mensagem("Não autorizado"), HttpStatus.UNAUTHORIZED);
			}
			else if (minutosDoUltimoLogin(usuario.getLastLogin()) >= 30) {
				return new ResponseEntity<>(new Mensagem("Sessão inválida"), HttpStatus.OK);				
			}
			return new ResponseEntity<>(usuario, HttpStatus.OK);
		}
		
	}

	protected long minutosDoUltimoLogin(Date lastLogin) {
		Date now = new Date();
		long dif = (now.getTime() - lastLogin.getTime()) / UM_MINUTO;
		System.out.println(">>>> Dif minutos = " + dif);
		return dif;
	}
	
	@RequestMapping("/hello")
	public Usuario hello(@RequestParam(value="nome", defaultValue="") String nome) {
		return new Usuario("you@mail.com", nome, null);
	}
}
