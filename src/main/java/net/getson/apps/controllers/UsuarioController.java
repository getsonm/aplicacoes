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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.media.ArraySchema;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import net.getson.apps.entidades.UsuarioEntidade;
import net.getson.apps.excecoes.ErroMessage;
import net.getson.apps.servicos.UsuarioServico;

@Tag(name = "Usuarios", description = "CRUD para operações usuário.")
@RequiredArgsConstructor
@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

	final UsuarioServico usuarioServico;
	
	@Operation(summary = "Criar novo usuário", description = "Recurso criar um novo usuário",
            responses = {
                @ApiResponse(responseCode = "201", description = "Recurso criado com sucesso",
                        content = @Content(mediaType = "application/json", schema = @Schema(implementation = UsuarioEntidade.class))),
                @ApiResponse(responseCode = "409", description = "Usuário e-mail já cadastrado no sistema",
                        content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErroMessage.class))),
                @ApiResponse(responseCode = "422", description = "Recurso não processado por dados de entrada invalidos",
                        content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErroMessage.class)))
            })
	@PostMapping
	public ResponseEntity<UsuarioEntidade> create(@Valid @RequestBody UsuarioEntidade usuarioEntidade) {
		UsuarioEntidade novoUsuarioEntidade = usuarioServico.salvar(usuarioEntidade);
		return ResponseEntity.status(HttpStatus.CREATED).body(novoUsuarioEntidade);
	}
	
	@Operation(summary = "Listar todos os usuários", description = "Listar todos os usuários cadastrados",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista com todos os usuários cadastrados",
                            content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = UsuarioEntidade.class))))
            })
	@GetMapping()
	public ResponseEntity<Object> listarTodos() {
		return new ResponseEntity<Object>(usuarioServico.listarTodos(), HttpStatus.OK);
	}
	
	@Operation(summary = "Usuário pelo id", description = "Um usuário pelo id",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Recurso recuperado com sucesso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = UsuarioEntidade.class))),
                    @ApiResponse(responseCode = "404", description = "Recurso não encontrado",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErroMessage.class)))
            })
	@GetMapping("/{id}")
	public ResponseEntity<Object> listarPorID(@PathVariable Integer id) {
		return new ResponseEntity<Object>(usuarioServico.listarPeloID(id), HttpStatus.OK);
	}
	
	@Operation(summary = "Atualizar dados", description = "Atualizar dados",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Dados atualizada com sucesso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Void.class))),
                    @ApiResponse(responseCode = "400", description = "Dados não confere",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErroMessage.class))),
                    @ApiResponse(responseCode = "404", description = "Dados não encontrado",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErroMessage.class)))
            })
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
	
	@Operation(summary = "Excluir usuário pelo id", description = "Excluir usuário pelo id",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Recurso excluído com sucesso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = UsuarioEntidade.class))),
                    @ApiResponse(responseCode = "404", description = "Recurso não excluído",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErroMessage.class)))
            })
	@DeleteMapping("/{id}")
	public String excluir(@PathVariable Integer id) {
		return (usuarioServico.excluir(id));
	}
	
}
