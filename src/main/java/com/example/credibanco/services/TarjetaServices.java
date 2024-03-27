package com.example.credibanco.services;

import com.example.credibanco.models.TarjetaModel;
import com.example.credibanco.repositories.TarjetaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Random;

@Service
public class TarjetaServices {

    @Autowired
    private TarjetaRepository tarjetaRepository;

    public ArrayList<TarjetaModel> obtenerTarjetas() {
        return (ArrayList<TarjetaModel>) tarjetaRepository.findAll();
    }

    public String crearTarjeta(TarjetaModel tarjeta) {
        TarjetaModel tarjetaGuardada = tarjetaRepository.save(tarjeta);

        Random random = new Random();
        StringBuilder numeroTarjetaBuilder = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            numeroTarjetaBuilder.append(random.nextInt(10));
        }
        String tipoTarjeta = tarjeta.getTipo();
        String seisPrimerosDigitos;
        if ("credito".equalsIgnoreCase(tipoTarjeta)) {
            seisPrimerosDigitos = "787878";
        } else if ("debito".equalsIgnoreCase(tipoTarjeta)) {
            seisPrimerosDigitos = "121212";
        } else {
            throw new IllegalArgumentException("Tipo de tarjeta inválido.");
        }
        String numeroCompletoTarjeta = seisPrimerosDigitos + numeroTarjetaBuilder.toString();

        tarjetaGuardada.asignarNumeroTarjeta(Long.parseLong(numeroCompletoTarjeta));

        tarjetaRepository.save(tarjetaGuardada);
        return "Tarjeta creada con éxito. Por favor, active la tarjeta.";
    }

    public String activarTarjeta(Long id) {
        Optional<TarjetaModel> optionalTarjeta = tarjetaRepository.findById(id);
        if (optionalTarjeta.isPresent()) {
            TarjetaModel tarjeta = optionalTarjeta.get();
            if (tarjeta.getEstado().equals("INACTIVA")) {
                tarjeta.setEstado("ACTIVA");
                tarjetaRepository.save(tarjeta);
                return "Tarjeta activada correctamente";
            } else {
                return "La tarjeta ya está activada";
            }
        } else {
            return "No se encontró la tarjeta con el ID proporcionado";
        }
    }

    public String asignarSaldoDolares(Long id, double saldoDolares) {
        Optional<TarjetaModel> optionalTarjeta = tarjetaRepository.findById(id);
        if (optionalTarjeta.isPresent()) {
            TarjetaModel tarjeta = optionalTarjeta.get();
            if (tarjeta.getEstado().equals("ACTIVA")) {
                double saldoActual =tarjeta.getSaldo().doubleValue();
                double nuevoSaldo = saldoActual + saldoDolares;
                tarjeta.setSaldo(new BigDecimal( nuevoSaldo));
                tarjetaRepository.save(tarjeta);
                return "Saldo asignado correctamente a la tarjeta con numero " +tarjeta.getNumero()+ " Nuevo saldo: " + String.format("%.2f USD", nuevoSaldo);
            } else {
                return "No se puede asignar saldo a una tarjeta inactiva.";
            }
        } else {
            return "No se encontró la tarjeta con el ID proporcionado.";
        }
    }

    public TarjetaModel getBalance(Long cardId){
        return tarjetaRepository.findById(cardId).get();
    }

}
