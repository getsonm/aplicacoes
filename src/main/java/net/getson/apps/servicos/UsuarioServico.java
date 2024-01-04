package net.getson.apps.servicos;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import net.getson.apps.entidades.UsuarioEntidade;
import net.getson.apps.excecoes.NotFoundException;
import net.getson.apps.excecoes.UniqueViolationException;
import net.getson.apps.repositorios.UsuarioRepositorio;

@RequiredArgsConstructor
@Service
public class UsuarioServico {

	final UsuarioRepositorio usuarioRepositorio;

	@Transactional
	public UsuarioEntidade salvar(UsuarioEntidade usuarioEntidade) {
		try {
			return usuarioRepositorio.save(usuarioEntidade); 
		} catch(DataIntegrityViolationException ex) {
			throw new UniqueViolationException("Já cadastrado!");
		}
	}

	@Transactional(readOnly = true)
	public List<UsuarioEntidade> listarTodos() {
		return(usuarioRepositorio.findAll());
	}
	
	@Transactional(readOnly = true)
	public UsuarioEntidade listarPeloID(Integer id) {
		return(usuarioRepositorio.findById(id).orElseThrow(
					() -> new NotFoundException("Usuário não encontrado")
				));
	}
	
	@Transactional
	public UsuarioEntidade usuarioAtualizar(Integer id, String senha, String usuario, LocalDateTime hoje, String nome, String email) {
		UsuarioEntidade usuarioAtualizar = listarPeloID(id);
		usuarioAtualizar.setSenha(senha);
		usuarioAtualizar.setUsuarioModificacao(usuario);
		usuarioAtualizar.setDataModificacao(hoje);
		usuarioAtualizar.setNomeUsuario(nome);
		usuarioAtualizar.setEmail(email);
		return usuarioAtualizar;
	}
	
	@Transactional
	public String excluir(Integer id) {
		UsuarioEntidade usuarioExcluir = listarPeloID(id);
		usuarioRepositorio.deleteById(id);
		return "Usuário excluído " + usuarioExcluir.getNomeUsuario();
	}

}
