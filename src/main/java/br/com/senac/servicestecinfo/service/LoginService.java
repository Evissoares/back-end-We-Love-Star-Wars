package br.com.senac.servicestecinfo.service;

import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.senac.servicestecinfo.controller.LoginController;
import br.com.senac.servicestecinfo.i18n.Message;
import br.com.senac.servicestecinfo.i18n.MessageProperties;
import br.com.senac.servicestecinfo.model.Cliente;
import br.com.senac.servicestecinfo.rest.response.ApiResponse;
import br.com.senac.servicestecinfo.rest.response.ResponseEntityUtil;
import br.com.senac.servicestecinfo.util.NullValidator;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponses;

@RestController
@CrossOrigin
public class LoginService {

	@Autowired
	NullValidator validator;

	@Autowired
	Message message;

	@Autowired
	LoginController controller;

	@ApiOperation(value = "Este serviço é responsável por efetuar o login"
			+ "de um cliente na base. Para isso, são obrigatórios Senha e EMail")
	@ApiResponses(value = {
			@io.swagger.annotations.ApiResponse(code = 200, message = "Usuário cadastrado com sucesso/ Usuário já cadastrado "),
			@io.swagger.annotations.ApiResponse(code = 403, message = "Você não tem permissão pra acessar este recurso"),
			@io.swagger.annotations.ApiResponse(code = 500, message = "Erro interno. Favor tente novamente."),
			@io.swagger.annotations.ApiResponse(code = 422, message = "Possíveis retornos: Nome do login obrigatório/ Erro nos campos enviados."),

	})
	@GetMapping(value = "/login", produces = "application/json")
	@ResponseBody
	@CrossOrigin
	@Produces(MediaType.APPLICATION_JSON)
	public ResponseEntity<ApiResponse> acessarConta(@RequestParam("email") String email,
			@RequestParam("senha") String senha) {
		Cliente cliente = new Cliente();
		cliente.setEmail(email);
		cliente.setSenha(senha);
		try {
			if (validator.isNotNull(email) || !(email).contentEquals("") || validator.isNotNull(senha)
					|| !(senha).contentEquals("")) {
				Cliente newCliente = controller.validarLogin(cliente);
				if (newCliente == null) {
					return ResponseEntityUtil.okResponseEntity(message.get(MessageProperties.USERNAME_NOT_FOUND),
							newCliente);
				}
				return ResponseEntityUtil.okResponseEntity(message.get(MessageProperties.USER_CREATED), newCliente);
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

	@ApiOperation(value = "Este serviço é responsável por efetuar a remoção do usuário "
			+ "da base. Para isso, é obrigatório o Cliente a ser removido")
	@ApiResponses(value = { @io.swagger.annotations.ApiResponse(code = 200, message = "Cliente removido com sucesso"),
			@io.swagger.annotations.ApiResponse(code = 403, message = "Você não tem permissão pra acessar este recurso"),
			@io.swagger.annotations.ApiResponse(code = 500, message = "Erro interno. Favor tente novamente."),
			@io.swagger.annotations.ApiResponse(code = 422, message = "Erro nos campos enviados. Verifique os valores e nomes dos campos."),

	})
	@DeleteMapping(value = "/deleteclient", produces = "application/json", consumes = "application/json")
	@ResponseBody
	@CrossOrigin
	@Produces(MediaType.APPLICATION_JSON)
	public ResponseEntity<ApiResponse> deleteClient(@RequestBody @Validated Cliente cliente) {
		Cliente clienteCompleto = null;
		try {
			cliente.setNome(cliente.getNome().trim().toUpperCase());
			if (validator.isNotNull(cliente.getNome()) || !cliente.getNome().contentEquals("")
					|| cliente.getId() != null || cliente.getEmail() != null) {

				clienteCompleto = controller.findById(cliente.getId());

				if (clienteCompleto != null) {
					if (controller.delete(clienteCompleto.getId()) == true) {

						return ResponseEntityUtil.okResponseEntity(message.get(MessageProperties.USER_REMOVED));

					} else {

						return ResponseEntityUtil
								.unprocessResponseEntity(message.get(MessageProperties.API_FIELDS_INVALIDS), cliente);
					}
				} else {
					return ResponseEntityUtil.okResponseEntity(message.get(MessageProperties.USERNAME_NOT_FOUND),
							cliente);
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

}
