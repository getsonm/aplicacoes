package net.getson.apps.excecoes;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class ApiExceptionHandler {

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErroMessage> methodArgumentNotValidException(MethodArgumentNotValidException ex,
				HttpServletRequest request,
				BindingResult result) {
		log.error("API ERRO: ", ex);
		return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
				.contentType(MediaType.APPLICATION_JSON)
				.body(new ErroMessage(request, HttpStatus.UNPROCESSABLE_ENTITY, "Campo(s) inválido(s)!", result));
	}
	
	@ExceptionHandler(UniqueViolationException.class)
	public ResponseEntity<ErroMessage> methodArgumentNotValidException(RuntimeException ex,
				HttpServletRequest request) {
		log.error("API ERRO: ", ex);
		return ResponseEntity.status(HttpStatus.CONFLICT)
				.contentType(MediaType.APPLICATION_JSON)
				.body(new ErroMessage(request, HttpStatus.CONFLICT, ex.getMessage()));
	}
	
	@ExceptionHandler(NotFoundException.class)
	public ResponseEntity<ErroMessage> notFoundException(RuntimeException ex,
				HttpServletRequest request) {
		log.error("API ERRO: ", ex);
		return ResponseEntity.status(HttpStatus.NOT_FOUND)
				.contentType(MediaType.APPLICATION_JSON)
				.body(new ErroMessage(request, HttpStatus.NOT_FOUND, ex.getMessage()));
	}
	
}
