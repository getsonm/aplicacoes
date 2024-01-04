package net.getson.apps.excecoes;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class ErroMessage {

	String path;
	String metodo;
	Integer status;
	String statusText;
	String mensagem;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	Map<String, String> erros;
	
	public ErroMessage(HttpServletRequest request, HttpStatus status, String mensagem) {
		super();
		this.path = request.getRequestURI();
		this.metodo = request.getMethod();
		this.status = status.value();
		this.statusText = status.getReasonPhrase();
		this.mensagem = mensagem;
	}
	
	public ErroMessage(HttpServletRequest request, HttpStatus status, String mensagem, BindingResult result) {
		super();
		this.path = request.getRequestURI();
		this.metodo = request.getMethod();
		this.status = status.value();
		this.statusText = status.getReasonPhrase();
		this.mensagem = mensagem;
		addErros(result);
	}

	private void addErros(BindingResult result) {
		this.erros =  new HashMap<>();
		for(FieldError fielderror : result.getFieldErrors())
			this.erros.put(fielderror.getField(), fielderror.getDefaultMessage());
	}

	public ErroMessage() {
		super();
	}
	
}
