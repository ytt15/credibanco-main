package com.example.credibanco.controllers;

import com.example.credibanco.models.TarjetaModel;
import com.example.credibanco.repositories.TarjetaRepository;
import com.example.credibanco.services.TarjetaServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/card")
public class TarjetaController {
	@Autowired
	private TarjetaServices tarjetaService;

	@Autowired
	private TarjetaRepository tarjetaRepository;

	@GetMapping("/listar")
	public ArrayList<TarjetaModel> obtenerTarjetas() {
		return tarjetaService.obtenerTarjetas();
	}

	@PostMapping
	public String crearTarjeta(@RequestBody TarjetaModel tarjeta) {
		tarjeta.setEstado("INACTIVA");
		return this.tarjetaService.crearTarjeta(tarjeta);
	}

	@PutMapping("/{id}/activate")
	public String activarTarjeta(@PathVariable Long id) {
		return tarjetaService.activarTarjeta(id);
	}

	@PutMapping("/{id}/assign-balance")
	public String asignarSaldoDolares(@PathVariable Long id, @RequestParam double saldoDolares) {
		return tarjetaService.asignarSaldoDolares(id, saldoDolares);
	}

	@GetMapping("/balance/{cardId}")
	public ResponseEntity<Object> getBalance(@PathVariable Long cardId) {
		try {
			TarjetaModel tarjeta = tarjetaService.getBalance(cardId);
			return ResponseEntity.ok(tarjeta);
		}
		catch (Exception ex) {
			Map<String, Object> errorBody = new HashMap<>();
			errorBody.put("error", "Ocurrió un error interno del servidor");
			errorBody.put("mensaje", ex.getMessage()); // Agregar el mensaje de error
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorBody);
		}
	}

}
