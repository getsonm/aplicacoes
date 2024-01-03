package net.getson.apps.entidades;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Entity
@Table(name = "USUARIOS")
public class UsuarioEntidade implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	Integer Id;
	
	@Column(name = "NOME_USUARIO", nullable = false, unique = true, length = 128)
	String nomeUsuario;
	
	@Column(name = "SENHA", nullable = false, length = 256)
	String senha;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "ROLE", nullable = false, length = 32)
	Role role;
	
	@Column(name = "DATA_CRIACAO")
	LocalDateTime dataCriacao;
	
	@Column(name = "DATA_MODIFICACAO")
	LocalDateTime dataModificacao;
	
	@Column(name = "USUARIO_CRIACAO", nullable = false, length = 128)
	String usuarioCriacao;
	
	@Column(name = "USUARIO_MODIFICACAO", length = 128)
	String usuarioModificacao;
	
	@Column(name = "EMAIL", nullable = false, unique = true, length = 128)
	@Email(message = "Email inválido")
	/*@Email(message = "Email inválido", regexp = "^[a-z0-9.+-]+@[a-z0-9.-]+\\.[a-z]{2,}$")
	@Size(min = 6, max = 6)*/
	@NotBlank
	String email;
	
	public enum Role {
		ROLE_ADMIN, ROLE_CLIENTE
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UsuarioEntidade other = (UsuarioEntidade) obj;
		return Objects.equals(Id, other.Id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(Id);
	}

	@Override
	public String toString() {
		return "UsuarioEntidade [Id=" + Id + "]";
	}
	
}
