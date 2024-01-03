package net.getson.apps.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;

import net.getson.apps.entidades.UsuarioEntidade;

public interface UsuarioRepositorio extends JpaRepository<UsuarioEntidade, Integer> {

}
