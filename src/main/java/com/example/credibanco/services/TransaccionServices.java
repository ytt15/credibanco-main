package com.example.credibanco.services;

import com.example.credibanco.models.TransaccionModel;
import com.example.credibanco.repositories.TransaccionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class TransaccionServices {
	private static Logger LOG = LoggerFactory.getLogger(TransaccionServices.class);
	@Autowired
	TransaccionRepository rep;

	public String realizarTransaccion(TransaccionModel transaccion) {
		try {
			TransaccionModel resp = rep.realizarCompra2(transaccion.getTarjetaModel().getIdTarjeta(),
					new BigDecimal(transaccion.getPrecioTran()));
			return "Transaccion generada: " + resp.getIdTransaccion();
		}
		catch (Exception e) {
			LOG.error("ex: {}", e);
			return extraerUltimoMensajeError(e);
		}
	}

	public static String extraerUltimoMensajeError(Exception e) {
		// Definir la expresión regular para encontrar todos los mensajes de error
		String regex = "\\[(.*?)\\]";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(e.getMessage());

		String ultimoMensajeError = "";
		String mensajeAnterior = "";
		boolean primeraCoincidencia = true;

		while (matcher.find()) {

			if (!primeraCoincidencia) {
				ultimoMensajeError = mensajeAnterior;
			}

			mensajeAnterior = matcher.group(1);
			primeraCoincidencia = false;
		}

		return ultimoMensajeError;
	}

	public List<TransaccionModel> obtenerTransaccionesPorTarjeta(Integer tarjetaId) {
		return rep.findByTarjetaId(tarjetaId);
	}

	public TransaccionModel getTransactionDetail(Integer transactionId) {
		return rep.findById(transactionId).get();
	}

	public String disabledTransaction(TransaccionModel transaccion) {
		try {
			return rep.anularTransaccion(transaccion.getIdTransaccion());
		}
		catch (Exception e) {
			LOG.error("ex: {}", e);
			return extraerUltimoMensajeError(e);
		}
	}

	public List<TransaccionModel> obtenerTransaccionesPorAnulaciones(String estado) {
		return rep.findByEstado(estado);
	}

}
