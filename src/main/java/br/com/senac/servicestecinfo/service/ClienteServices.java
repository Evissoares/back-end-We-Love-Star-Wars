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

import br.com.senac.servicestecinfo.controller.ClienteController;
import br.com.senac.servicestecinfo.i18n.Message;
import br.com.senac.servicestecinfo.i18n.MessageProperties;
import br.com.senac.servicestecinfo.model.Cliente;
import br.com.senac.servicestecinfo.repository.ClienteRepository;
import br.com.senac.servicestecinfo.rest.response.ApiResponse;
import br.com.senac.servicestecinfo.rest.response.ResponseEntityUtil;
import br.com.senac.servicestecinfo.util.NullValidator;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping(value = "/clientes")
public class ClienteServices {

	@Autowired
	ClienteRepository clienteRepository;

	@Autowired
	NullValidator validator;

	@Autowired
	Message message;

	@Autowired
	ClienteController controller;
	
	@ApiOperation(value = "Este serviço é responsável por efetuar o cadastro "
			+ "de um cliente na base. Para isso, são obrigatórios Nome, Senha e EMail")
	@ApiResponses(value = {
			@io.swagger.annotations.ApiResponse(code = 200, message = "Usuário cadastrado com sucesso/ Usuário já cadastrado "),
			@io.swagger.annotations.ApiResponse(code = 201, message = "Usuário cadastrado com sucesso"),
			@io.swagger.annotations.ApiResponse(code = 403, message = "Você não tem permissão pra acessar este recurso"),
			@io.swagger.annotations.ApiResponse(code = 500, message = "Erro interno. Favor tente novamente."),
			@io.swagger.annotations.ApiResponse(code = 422, message = "Possíveis retornos: Nome do cliente obrigatório/ Erro nos campos enviados."),
	})
	@PostMapping(produces = "application/json", consumes = "application/json")
	@ResponseBody
	@CrossOrigin
	@Produces(MediaType.APPLICATION_JSON)
	public ResponseEntity<ApiResponse> addClient(@RequestBody Cliente cliente) {
		Cliente novoCliente = cliente;
		try {
			novoCliente.setNome(cliente.getNome().trim().toUpperCase());
			if (validator.isNotNull(novoCliente.getNome()) || !novoCliente.getNome().contentEquals("")) {
				novoCliente = controller.addClient(novoCliente);
				return ResponseEntityUtil.okResponseEntity(message.get(MessageProperties.USER_CREATED), novoCliente);
			} else {
				return ResponseEntityUtil.unprocessResponseEntity(message.get(MessageProperties.API_FIELDS_INVALIDS),
						cliente);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntityUtil.unprocessResponseEntity(message.get(MessageProperties.INTERNAL_SERVER_ERROR),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@ApiOperation(value = "Este serviço é responsável por localizar o cadastro "
			+ "de todos os clientes na base. Não é necessário passar nenhum parâmetro")
	@ApiResponses(value = {
			@io.swagger.annotations.ApiResponse(code = 200, message = "Usuário encontrado"),
			@io.swagger.annotations.ApiResponse(code = 403, message = "Você não tem permissão pra acessar este recurso"),
			@io.swagger.annotations.ApiResponse(code = 500, message = "Erro interno. Favor tente novamente."),
			@io.swagger.annotations.ApiResponse(code = 422, message = "Possíveis retornos:Erro nos campos enviados."),
	})
	@GetMapping(produces = "application/json")
	@ResponseBody
	@CrossOrigin
	@Produces(MediaType.APPLICATION_JSON)
	public ResponseEntity<ApiResponse> findAllClient() {
		List<Cliente> listaCliente = null;
		try {
			listaCliente = controller.findAllClient();
			if (listaCliente.isEmpty()) {
				return ResponseEntityUtil.okResponseEntity(message.get(MessageProperties.USERNAME_NOT_FOUND));
			} else {
				return ResponseEntityUtil.okResponseEntity(message.get(MessageProperties.USERNAME_FINDED),
						listaCliente);
			}
		} catch (Exception e) {
			return ResponseEntityUtil.unprocessResponseEntity(message.get(MessageProperties.INTERNAL_SERVER_ERROR),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@ApiOperation(value="Este serviço é responsável por efetuar filtrar o cliente "
			+ "por id. Para isso, é obrigatório o ID")
	@ApiResponses(value = {
			@io.swagger.annotations.ApiResponse(code = 200, message = "Usuário encontrando"),
			@io.swagger.annotations.ApiResponse(code = 403, message = "Você não tem permissão pra acessar este recurso"),
			@io.swagger.annotations.ApiResponse(code = 500, message = "Erro interno. Favor tente novamente."),
			@io.swagger.annotations.ApiResponse(code = 422, message = "Possíveis retornos: id do cliente obrigatório/ Erro nos campos enviados."),
			
	})
	@GetMapping(value = "/buscar-id", produces = "application/json")
	@ResponseBody
	@CrossOrigin
	@Produces(MediaType.APPLICATION_JSON)
	public ResponseEntity<ApiResponse> findClientByID(@RequestParam("id") Long id) {
		Cliente cliente = new Cliente();
		cliente.setId(id);
		try {
			if (validator.isNotNull(cliente.getId()) || !id.equals("")) {
				cliente = controller.findClientByID(cliente);
				if (cliente == null)
					return ResponseEntityUtil.okResponseEntity(message.get(MessageProperties.USERNAME_NOT_FOUND));
				else {
					return ResponseEntityUtil.okResponseEntity(message.get(MessageProperties.USERNAME_FINDED), cliente);
				}
			} else {
				return ResponseEntityUtil.unprocessResponseEntity(message.get(MessageProperties.API_FIELDS_INVALIDS),
						cliente);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntityUtil.unprocessResponseEntity(message.get(MessageProperties.INTERNAL_SERVER_ERROR),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@ApiOperation(value="Este serviço é responsável por efetuar filtrar o cliente "
			+ "por nome. Para isso, é obrigatório o nome do cliente")
	@ApiResponses(value = {
			@io.swagger.annotations.ApiResponse(code = 200, message = "Usuário encontrando"),
			@io.swagger.annotations.ApiResponse(code = 403, message = "Você não tem permissão pra acessar este recurso"),
			@io.swagger.annotations.ApiResponse(code = 500, message = "Erro interno. Favor tente novamente."),
			@io.swagger.annotations.ApiResponse(code = 422, message = "Possíveis retornos: id do cliente obrigatório/ Erro nos campos enviados."),
	})
	@GetMapping(value = "/buscar-nome", produces = "application/json")
	@ResponseBody
	@CrossOrigin
	@Produces(MediaType.APPLICATION_JSON)
	public ResponseEntity<ApiResponse> findClientByName(@RequestParam("nome") String nome) {
		List<Cliente> listaCliente = new ArrayList<>();
		Cliente cliente = new Cliente();
		cliente.setNome(nome);
		// TODO validação para nomes sem caractere especial.

		listaCliente = controller.findClientByName(cliente);
		return ResponseEntityUtil.okResponseEntity(message.get(MessageProperties.USER_CREATED), listaCliente);
	}

	@ApiOperation(value = "Este serviço é responsável por atualizar o cadastro "
			+ "de um cliente na base.  Para isso, são obrigatórios ID, Nome, Senha e EMail" 
			+ "caso contrário será criado um novo cliente na base")
	@ApiResponses(value = {
			@io.swagger.annotations.ApiResponse(code = 200, message = "Usuário atualizado com sucesso"),
			@io.swagger.annotations.ApiResponse(code = 201, message = "Usuário atualizado com sucesso"),
			@io.swagger.annotations.ApiResponse(code = 403, message = "Você não tem permissão pra acessar este recurso"),
			@io.swagger.annotations.ApiResponse(code = 500, message = "Erro interno. Favor tente novamente."),
			@io.swagger.annotations.ApiResponse(code = 422, message = "Possíveis retornos: Nome do cliente obrigatório/ Erro nos campos enviados."),
	})
	@PutMapping(value = "updateclient", produces = "application/json", consumes = "application/json")
	@ResponseBody
	@CrossOrigin
	@Produces(MediaType.APPLICATION_JSON)
	public ResponseEntity<ApiResponse> updateClient(@RequestBody @Validated Cliente cliente) {
		try {
			cliente.setNome(cliente.getNome().trim().toUpperCase());
			if (validator.isNotNull(cliente.getNome()) || !cliente.getNome().contentEquals("")) {
				Cliente updatedCliente = controller.addClient(cliente);
				return ResponseEntityUtil.okResponseEntity(message.get(MessageProperties.USER_CREATED), updatedCliente);
			} else {
				return ResponseEntityUtil.unprocessResponseEntity(message.get(MessageProperties.API_FIELDS_INVALIDS),
						cliente);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntityUtil.unprocessResponseEntity(message.get(MessageProperties.INTERNAL_SERVER_ERROR),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@ApiOperation(value = "Este serviço é responsável por deletar o cadastro "
			+ "de um cliente na base. É necessário passar o ID do cliente como parâmetro")
	@ApiResponses(value = {
			@io.swagger.annotations.ApiResponse(code = 200, message = "Cliente removido com sucesso"),
			@io.swagger.annotations.ApiResponse(code = 403, message = "Você não tem permissão pra acessar este recurso"),
			@io.swagger.annotations.ApiResponse(code = 500, message = "Erro interno. Favor tente novamente."),
			@io.swagger.annotations.ApiResponse(code = 422, message = "Erro nos campos enviados. Verifique os valores e nomes dos campos."),
	})
	@DeleteMapping(value = "/delete-id", produces = "application/json")
	@ResponseBody
	@CrossOrigin
	@Produces(MediaType.APPLICATION_JSON)
	public ResponseEntity<ApiResponse> deleteClientByID(@RequestParam("id") Long id) {
		Cliente cliente = new Cliente();
		cliente.setId(id);
		cliente = controller.deleteClientByID(cliente);
		return ResponseEntityUtil.okResponseEntity(message.get(MessageProperties.USER_CREATED), cliente);
	}
}
