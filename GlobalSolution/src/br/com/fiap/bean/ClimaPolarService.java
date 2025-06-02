package br.com.fiap.bean;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonArray;

public class ClimaPolarService {

    //atributos de classe

    private static final String API_BASE_URL = "https://api.open-meteo.com/v1/forecast?";
    private final HttpClient httpClient;
    private final Gson gson;

    //construtores
    public ClimaPolarService() {
        this.httpClient = HttpClient.newHttpClient();
        this.gson = new Gson();
    }

    public ClimaPolarService(HttpClient httpClient, Gson gson) {
        this.httpClient = httpClient;
        this.gson = gson;
    }

    //metodos de classe particulares

    public Double getTemperaturaAtual(Polo polo) throws IOException, InterruptedException {
        String url = String.format("%slatitude=%.2f&longitude=%.2f&current_weather=true&temperature_unit=celsius&forecast_days=1",
                API_BASE_URL, polo.getLatitude(), polo.getLongitude());

        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).GET().build();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            String responseBody = response.body();
            System.out.println("Resposta bruta da API para " + polo.getNome() + ": " + responseBody); // Para depuração

            JsonElement jsonElement = gson.fromJson(responseBody, JsonElement.class);

            JsonObject jsonResponse = null;

            if (jsonElement.isJsonObject()) {
                jsonResponse = jsonElement.getAsJsonObject();
            } else if (jsonElement.isJsonArray()) {
                JsonArray jsonArray = jsonElement.getAsJsonArray();
                if (jsonArray.size() > 0 && jsonArray.get(0).isJsonObject()) {
                    jsonResponse = jsonArray.get(0).getAsJsonObject(); // Pega o primeiro objeto do array
                }
            }

            if (jsonResponse != null && jsonResponse.has("current_weather")) {
                JsonObject currentWeather = jsonResponse.getAsJsonObject("current_weather");
                if (currentWeather.has("temperature")) {
                    return currentWeather.get("temperature").getAsDouble();
                }
            }
        }
        return null;
    }

    public boolean verificarAlertaDegelo(Polo polo) throws IOException, InterruptedException {
        Double temperatura = getTemperaturaAtual(polo);
        return temperatura != null && temperatura > 0.0;
    }

    public boolean verificarAlertaDegelo(Polo polo, double limiarDegelo) throws IOException, InterruptedException {
        Double temperatura = getTemperaturaAtual(polo);
        return temperatura != null && temperatura > limiarDegelo;
    }

    @Override
    public String toString() {
        return "Serviço de Clima Polar";
    }
}