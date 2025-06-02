package br.com.fiap.bean;

import java.io.IOException;
import java.util.Random;

public class SensorHidrico {

    //atributos

    private static final double NIVEL_LIMITE_ALERTA = 2.0; // Limite em metros para alerta
    private final ClimaPolarService climaPolarService;
    private final Random random;

    //construtorees
    public SensorHidrico() {
        this.climaPolarService = new ClimaPolarService();
        this.random = new Random();
    }

    public SensorHidrico(ClimaPolarService climaPolarService, Random random) {
        this.climaPolarService = climaPolarService;
        this.random = random;
    }

    //metodos getters/setters

    public ClimaPolarService getClimaPolarService() {
        return climaPolarService;
    }

    public Random getRandom() {
        return random;
    }




    //metodos de classe particulares

    public double obterNivelAgua(Polo polo) throws IOException, InterruptedException {
        Double temperatura = climaPolarService.getTemperaturaAtual(polo);

        if (temperatura == null) {
            System.out.println("Temperatura indisponível para " + polo.getNome());
            return -1.0; // Erro de leitura
        }

        double baseLatitude = polo.getLatitude() < 0 ? 1.0 : 0.5;


        double variacaoAleatoria = random.nextDouble() * 2.5;


        double impactoTemperatura = Math.max(0, temperatura / 10.0);

        // Forçar aleatoriamente um pico de alerta com 20% de chance
        if (random.nextDouble() < 0.2) { // 20% chance
            variacaoAleatoria += 1.0; // adiciona um metro a mais
        }

        double nivelAgua = baseLatitude + variacaoAleatoria + impactoTemperatura;

        return Math.round(nivelAgua * 100.0) / 100.0; // arredonda para 2 casas decimais
    }


    public boolean verificarAlertaNivelAlto(Polo polo) throws IOException, InterruptedException {
        double nivel = obterNivelAgua(polo);
        return nivel > NIVEL_LIMITE_ALERTA;
    }

    @Override
    public String toString() {
        return "Sensor Hídrico Simulado - Monitoramento do Nível da Água";
    }
}

