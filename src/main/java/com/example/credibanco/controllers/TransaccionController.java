package com.example.credibanco.controllers;

import com.example.credibanco.models.TransaccionModel;
import com.example.credibanco.services.TransaccionServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transaction")
public class TransaccionController {

	private final TransaccionServices transaccionService;

	@Autowired
	public TransaccionController(TransaccionServices transaccionService) {
		this.transaccionService = transaccionService;
	}

	@PostMapping("/realizar")
	public ResponseEntity<String> realizarTransaccion(@RequestBody TransaccionModel transaccion) {

		try {
			String resultado = transaccionService.realizarTransaccion(transaccion);
			return ResponseEntity.ok(resultado);
		}
		catch (DataAccessException e) {

			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}

	@GetMapping("/target/{tarjetaId}")
	public List<TransaccionModel> obtenerTransaccionesPorTarjeta(@PathVariable Integer tarjetaId) {
		return transaccionService.obtenerTransaccionesPorTarjeta(tarjetaId);
	}

	@GetMapping("/{transactionId}")
	public TransaccionModel getTransactionDetails(@PathVariable Integer transactionId) {
		return transaccionService.getTransactionDetail(transactionId);
	}

	@PostMapping("/anulation")
	public ResponseEntity<String> disabledTransaction(@RequestBody TransaccionModel transaccion) {
		try {
			String resultado = transaccionService.disabledTransaction(transaccion);
			return ResponseEntity.ok(resultado);
		}
		catch (DataAccessException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}

	@GetMapping("/{estado}")
	public List<TransaccionModel> obtenerTransaccionesPorAnulaciones(@PathVariable String estado) {
		return transaccionService.obtenerTransaccionesPorAnulaciones(estado);
	}


}
