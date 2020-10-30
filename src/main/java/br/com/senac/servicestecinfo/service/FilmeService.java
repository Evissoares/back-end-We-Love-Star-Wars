package br.com.senac.servicestecinfo.service;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.senac.servicestecinfo.controller.FilmeController;
import br.com.senac.servicestecinfo.i18n.Message;
import br.com.senac.servicestecinfo.i18n.MessageProperties;
import br.com.senac.servicestecinfo.model.Filme;
import br.com.senac.servicestecinfo.repository.FilmeRepository;
import br.com.senac.servicestecinfo.rest.response.ApiResponse;
import br.com.senac.servicestecinfo.rest.response.ResponseEntityUtil;
import br.com.senac.servicestecinfo.util.NullValidator;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/filmes")
public class FilmeService {
	
	@Autowired
	FilmeRepository filmeRepository;

	@Autowired
	NullValidator validator;

	@Autowired
	Message message;

	@Autowired
	FilmeController controller;
	
	@ApiOperation(value = "Este serviço é responsável por efetuar o cadastro "
			+ "de um filme na base. Para isso, são obrigatórios Titulo, Genero e Descrição")
	@ApiResponses(value = {
			@io.swagger.annotations.ApiResponse(code = 200, message = "Filme cadastrado com sucesso/ Filme já cadastrado "),
			@io.swagger.annotations.ApiResponse(code = 201, message = "Filme cadastrado com sucesso"),
			@io.swagger.annotations.ApiResponse(code = 403, message = "Você não tem permissão pra acessar este recurso"),
			@io.swagger.annotations.ApiResponse(code = 500, message = "Erro interno. Favor tente novamente."),
			@io.swagger.annotations.ApiResponse(code = 422, message = "Possíveis retornos: Titulo do Filme obrigatório/ Erro nos campos enviados."),
	})
	@PostMapping(produces = "application/json", consumes = "application/json")
	@ResponseBody
	@CrossOrigin
	@Produces(MediaType.APPLICATION_JSON)
	public ResponseEntity<ApiResponse> addFilme(@RequestBody Filme filme) {
		Filme novoFilme= filme;
		try {
			novoFilme.setTitulo(filme.getTitulo().trim().toUpperCase());
			if (validator.isNotNull(novoFilme.getTitulo()) || !novoFilme.getTitulo().contentEquals("")) {
				novoFilme = controller.addFilme(novoFilme);
				return ResponseEntityUtil.okResponseEntity(message.get(MessageProperties.USER_CREATED), novoFilme);
			} else {
				return ResponseEntityUtil.unprocessResponseEntity(message.get(MessageProperties.API_FIELDS_INVALIDS),
						filme);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntityUtil.unprocessResponseEntity(message.get(MessageProperties.INTERNAL_SERVER_ERROR),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@ApiOperation(value = "Este serviço é responsável por localizar o cadastro "
			+ "de todos os filmes na base. Não é necessário passar nenhum parâmetro")
	@ApiResponses(value = {
			@io.swagger.annotations.ApiResponse(code = 200, message = "filme encontrado"),
			@io.swagger.annotations.ApiResponse(code = 403, message = "Você não tem permissão pra acessar este recurso"),
			@io.swagger.annotations.ApiResponse(code = 500, message = "Erro interno. Favor tente novamente."),
			@io.swagger.annotations.ApiResponse(code = 422, message = "Possíveis retornos:Erro nos campos enviados."),
	})
	@GetMapping(produces = "application/json")
	@ResponseBody
	@CrossOrigin
	@Produces(MediaType.APPLICATION_JSON)
	public ResponseEntity<ApiResponse> findAllFilme() {
		List<Filme> listaFilme = null;
		try {
			listaFilme = controller.findAllfilme();
			if (listaFilme.isEmpty()) {
				return ResponseEntityUtil.okResponseEntity(message.get(MessageProperties.USERNAME_NOT_FOUND));
			} else {
				return ResponseEntityUtil.okResponseEntity(message.get(MessageProperties.USERNAME_FINDED),
						listaFilme);
			}
		} catch (Exception e) {
			return ResponseEntityUtil.unprocessResponseEntity(message.get(MessageProperties.INTERNAL_SERVER_ERROR),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@ApiOperation(value="Este serviço é responsável por filtrar o filme "
			+ "por id. Para isso, é obrigatório o ID do filme")
	@ApiResponses(value = {
			@io.swagger.annotations.ApiResponse(code = 200, message = "filme encontrando"),
			@io.swagger.annotations.ApiResponse(code = 403, message = "Você não tem permissão pra acessar este recurso"),
			@io.swagger.annotations.ApiResponse(code = 500, message = "Erro interno. Favor tente novamente."),
			@io.swagger.annotations.ApiResponse(code = 422, message = "Possíveis retornos: id do Filme obrigatório/ Erro nos campos enviados."),
			
	})
	@GetMapping(value = "/buscar-filme-id", produces = "application/json")
	@ResponseBody
	@CrossOrigin
	@Produces(MediaType.APPLICATION_JSON)
	public ResponseEntity<ApiResponse> findFilmeByID(@RequestParam("id") Long id) {
		Filme filme = new Filme();
		filme.setId(id);
		try {
			if (validator.isNotNull(filme.getId()) || !id.equals("")) {
				filme = controller.findfilmeById(filme);
				if (filme == null)
					return ResponseEntityUtil.okResponseEntity(message.get(MessageProperties.USERNAME_NOT_FOUND));
				else {
					return ResponseEntityUtil.okResponseEntity(message.get(MessageProperties.USERNAME_FINDED), filme);
				}
			} else {
				return ResponseEntityUtil.unprocessResponseEntity(message.get(MessageProperties.API_FIELDS_INVALIDS),
						filme);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntityUtil.unprocessResponseEntity(message.get(MessageProperties.INTERNAL_SERVER_ERROR),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@ApiOperation(value="Este serviço é responsável por filtrar o filme "
			+ "por Titulo. Para isso, é obrigatório o Titulo do filme")
	@ApiResponses(value = {
			@io.swagger.annotations.ApiResponse(code = 200, message = "filme encontrando"),
			@io.swagger.annotations.ApiResponse(code = 403, message = "Você não tem permissão pra acessar este recurso"),
			@io.swagger.annotations.ApiResponse(code = 500, message = "Erro interno. Favor tente novamente."),
			@io.swagger.annotations.ApiResponse(code = 422, message = "Possíveis retornos: id do filme obrigatório/ Erro nos campos enviados."),
	})
	@GetMapping(value = "/buscar-Titulo", produces = "application/json")
	@ResponseBody
	@CrossOrigin
	@Produces(MediaType.APPLICATION_JSON)
	public ResponseEntity<ApiResponse> findFilmeByName(@RequestParam("Titulo") String Titulo) {
		List<Filme> listaFilme = new ArrayList<>();
		Filme filme = new Filme();
		filme.setTitulo(Titulo);
		// TODO validação para Titulos sem caractere especial.

		listaFilme = controller.findfilmeByTitulo(filme);
		return ResponseEntityUtil.okResponseEntity(message.get(MessageProperties.USER_CREATED), listaFilme);
	}

	@ApiOperation(value = "Este serviço é responsável por atualizar o cadastro "
			+ "de um filme na base.  Para isso, são obrigatórios ID, Titulo, Genero e Descrição" 
			+ "caso contrário será criado um novo filme na base")
	@ApiResponses(value = {
			@io.swagger.annotations.ApiResponse(code = 200, message = "filme atualizado com sucesso"),
			@io.swagger.annotations.ApiResponse(code = 201, message = "filme atualizado com sucesso"),
			@io.swagger.annotations.ApiResponse(code = 403, message = "Você não tem permissão pra acessar este recurso"),
			@io.swagger.annotations.ApiResponse(code = 500, message = "Erro interno. Favor tente novamente."),
			@io.swagger.annotations.ApiResponse(code = 422, message = "Possíveis retornos: Titulo do filme obrigatório/ Erro nos campos enviados."),
	})
	@PutMapping(value = "update-filme", produces = "application/json", consumes = "application/json")
	@ResponseBody
	@CrossOrigin
	@Produces(MediaType.APPLICATION_JSON)
	public ResponseEntity<ApiResponse> updateFilme(@RequestBody @Validated Filme filme) {
		try {
			filme.setTitulo(filme.getTitulo().trim().toUpperCase());
			if (validator.isNotNull(filme.getTitulo()) || !filme.getTitulo().contentEquals("")) {
				Filme updatedFilme = controller.addFilme(filme);
				return ResponseEntityUtil.okResponseEntity(message.get(MessageProperties.USER_CREATED), updatedFilme);
			} else {
				return ResponseEntityUtil.unprocessResponseEntity(message.get(MessageProperties.API_FIELDS_INVALIDS),
						filme);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntityUtil.unprocessResponseEntity(message.get(MessageProperties.INTERNAL_SERVER_ERROR),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@ApiOperation(value = "Este serviço é responsável por deletar o cadastro "
			+ "de um filme na base. É necessário passar o ID do filme como parâmetro")
	@ApiResponses(value = {
			@io.swagger.annotations.ApiResponse(code = 200, message = "Filme removido com sucesso"),
			@io.swagger.annotations.ApiResponse(code = 403, message = "Você não tem permissão pra acessar este recurso"),
			@io.swagger.annotations.ApiResponse(code = 500, message = "Erro interno. Favor tente novamente."),
			@io.swagger.annotations.ApiResponse(code = 422, message = "Erro nos campos enviados. Verifique os valores e Titulos dos campos."),
	})
	@DeleteMapping(value = "/delete-id", produces = "application/json")
	@ResponseBody
	@CrossOrigin
	@Produces(MediaType.APPLICATION_JSON)
	public ResponseEntity<ApiResponse> deleteFilmeByID(@RequestParam("id") Long id) {
		Filme filme = new Filme();
		filme.setId(id);
		filme = controller.deletefilmeById(filme);
		return ResponseEntityUtil.okResponseEntity(message.get(MessageProperties.USER_CREATED), filme);
	}

}
