package br.com.fiap.bean;

public class Polo {
    private String nome;
    private double latitude;
    private double longitude;

    //construtores


    public Polo() {
    }

    public Polo(String nome, double latitude, double longitude) {
        this.nome = nome;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    // Getters/Setters

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    // Sobrescrita do toString para melhor representação
    @Override
    public String toString() {
        return "Polo: " + nome + " (Lat: " + latitude + ", Long: " + longitude + ")";
    }
}
