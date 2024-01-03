package net.getson.apps.controllers;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import net.getson.apps.entidades.UsuarioEntidade;
import net.getson.apps.servicos.UsuarioServico;

@RequiredArgsConstructor
@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

	final UsuarioServico usuarioServico;
	
	@PostMapping
	public ResponseEntity<UsuarioEntidade> create(@Valid @RequestBody UsuarioEntidade usuarioEntidade) {
		UsuarioEntidade novoUsuarioEntidade = usuarioServico.salvar(usuarioEntidade);
		return ResponseEntity.status(HttpStatus.CREATED).body(novoUsuarioEntidade);
	}
	
	@GetMapping()
	public ResponseEntity<Object> listarTodos() {
		return new ResponseEntity<Object>(usuarioServico.listarTodos(), HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Object> listarPorID(@PathVariable Integer id) {
		return new ResponseEntity<Object>(usuarioServico.listarPeloID(id), HttpStatus.OK);
	}
	
	@PatchMapping("/{id}")
	public ResponseEntity<UsuarioEntidade> atualizarSenha(@PathVariable Integer id, @RequestBody UsuarioEntidade usuarioEntidade) {
		LocalDateTime hoje = LocalDateTime.now();
		UsuarioEntidade usuarioSenhaNova = usuarioServico.usuarioAtualizar(id,
				usuarioEntidade.getSenha(),
				"Sistema-Atualiza",
				hoje,
				usuarioEntidade.getNomeUsuario(),
				usuarioEntidade.getEmail());
		return ResponseEntity.ok(usuarioSenhaNova);
	}
	
	@DeleteMapping("/{id}")
	public String excluir(@PathVariable Integer id) {
		return (usuarioServico.excluir(id));
	}
	
}
