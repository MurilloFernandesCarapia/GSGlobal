package br.com.fiap.main;

import br.com.fiap.bean.Polo;
import br.com.fiap.bean.ClimaPolarService;
import br.com.fiap.bean.SensorHidrico;

import javax.swing.JOptionPane;
import java.io.IOException;

public class Main {

    private static ClimaPolarService climaService = new ClimaPolarService();
    private static SensorHidrico sensorHidrico = new SensorHidrico();

    private static final double GLOBAL_TEMP_ALERT_THRESHOLD = 2.0;

    public static void main(String[] args) {
        Polo poloNorte = new Polo("Polo Norte", 89.9, 0.0);
        Polo poloSul = new Polo("Polo Sul", -89.9, 0.0);

        String menu = "🌍 Plataforma de Monitoramento de Degelo Polar 🧊\n\n" +
                "Escolha uma opção:\n\n" +
                "1. 🌡️ Ver Temperatura do Polo Norte\n" +
                "2. 🌡️ Ver Temperatura do Polo Sul\n" +
                "3. 💧 Ver Nível da Água no Polo Norte\n" +
                "4. 💧 Ver Nível da Água no Polo Sul\n" +
                "5. 📋 Gerar Relatório Completo de Alertas\n" +
                "6. 👥 Ver Integrantes do Grupo\n" +
                "7. 💡 Nossa Solução\n" +
                "8. ❌ Sair da Plataforma\n";

        int opcao = 0;
        do {
            try {
                String input = JOptionPane.showInputDialog(null, menu, "Monitoramento Polar - Grupo Eco Ambiental 🌎", JOptionPane.INFORMATION_MESSAGE);
                if (input == null) {
                    opcao = 8;
                    continue;
                }
                opcao = Integer.parseInt(input);

                switch (opcao) {
                    case 1:
                        verTemperaturaPolo(poloNorte);
                        break;
                    case 2:
                        verTemperaturaPolo(poloSul);
                        break;
                    case 3:
                        verNivelAgua(poloNorte);
                        break;
                    case 4:
                        verNivelAgua(poloSul);
                        break;
                    case 5:
                        gerarRelatorioCompleto(poloNorte, poloSul);
                        break;
                    case 6:
                        String integrantes = "👥 Integrantes:\n" +
                                "- Murillo Fernandes Carapia, RM: 564969, Turma: 1TDSPH (Matutino)\n" +
                                "- Leonardo Zerbinatti de Sales, RM: 562992, Turma: 1TDSPH (Matutino)\n" +
                                "- Breno da Fonseca Eleutério, RM: 564948, Turma: 1TDSPY (Noturno)";
                        JOptionPane.showMessageDialog(null, integrantes, "Nosso Grupo", JOptionPane.INFORMATION_MESSAGE);
                        break;
                    case 7:
                        JOptionPane.showMessageDialog(null, "💡 Nossa Solução: \n\n" +
                                        "Atualmente, o derretimento de gelo nos polos terrestres é um dos eventos extremos que mais geram problemas para a humanidade. " +
                                        "Portanto, esses eventos ocorrem devido ao aquecimento global, de maneira agressiva e prejudicial ao ambiente onde vivemos. " +
                                        "Isso resulta no aquecimento da terra e no deslizamento de grandes blocos de gelo para o oceano, o que pode elevar o nível da água e provocar grandes danos. " +
                                        "Tais eventos podem causar impactos ambientais e globais, incluindo tsunamis, alterações climáticas, elevação do nível da água devido ao derretimento, " +
                                        "resultando em inundações em cidades litorâneas, e no aumento da velocidade das correntes marítimas, além de intensificar a frequência e a intensidade de eventos climáticos extremos.\n\n" +
                                        "❄️ Plataforma de Monitoramento e Prevenção de Degelo Polar com Dados Climáticos em Tempo Real\n" +
                                        "Nosso objetivo é monitorar o degelo nas regiões polares e em cidades costeiras para medir o nível da água em tempo real usando sensores, " +
                                        "e fornecer alertas e ações sugeridas para empresas globais, a fim de diminuir o aquecimento global e conscientizar os problemas causados pelo derretimento. " +
                                        "Assim, informando as providências necessárias para diminuir o aquecimento global, consequentemente diminuir os deslizamentos e derretimentos de gelo.\n",
                                "Nossa Solução", JOptionPane.INFORMATION_MESSAGE);
                        break;
                    case 8:
                        JOptionPane.showMessageDialog(null, "Saindo da plataforma. Até logo! 👋", "Até mais!", JOptionPane.INFORMATION_MESSAGE);
                        break;
                    default:
                        JOptionPane.showMessageDialog(null, "Opção inválida. Por favor, escolha um número de 1 a 8.", "Erro de Opção", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Entrada inválida. Por favor, digite um número inteiro.", "Erro de Entrada", JOptionPane.ERROR_MESSAGE);
            } catch (IOException | InterruptedException e) {
                JOptionPane.showMessageDialog(null, "Erro de conexão ou leitura da API: " + e.getMessage(), "Erro de Serviço", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Ocorreu um erro inesperado: " + e.getMessage(), "Erro Inesperado", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        } while (opcao != 8);
        if (JOptionPane.showConfirmDialog(null, "Deseja usar o Programa da Eco Ambiental novamente?", "Atenção", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == 0) {
            main(args);
        } else {
            JOptionPane.showMessageDialog(null, "Fim de Programa. Volte Sempre!", "Adeus", JOptionPane.WARNING_MESSAGE);
        }
    }

    private static void verTemperaturaPolo(Polo polo) throws IOException, InterruptedException {
        Double temperatura = climaService.getTemperaturaAtual(polo);
        if (temperatura != null) {
            JOptionPane.showMessageDialog(null, "🌡️ Temperatura atual no " + polo.getNome() + ": " +
                    String.format("%.2f", temperatura) + " °C", "Temperatura Atual", JOptionPane.INFORMATION_MESSAGE);

            if (temperatura > GLOBAL_TEMP_ALERT_THRESHOLD) {
                exibirAlertaGlobalTemperatura(polo, temperatura);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Não foi possível obter a temperatura para o " + polo.getNome() + ".", "Erro de Temperatura", JOptionPane.WARNING_MESSAGE);
        }
    }

    private static void verNivelAgua(Polo polo) throws IOException, InterruptedException {
        double nivelAgua = sensorHidrico.obterNivelAgua(polo);
        if (nivelAgua >= 0) {
            JOptionPane.showMessageDialog(null, "💧 Nível da água no " + polo.getNome() + ": " + String.format("%.2f", nivelAgua) + " metros", "Nível da Água", JOptionPane.INFORMATION_MESSAGE);

            if (sensorHidrico.verificarAlertaNivelAlto(polo)) {
                exibirAlertaGlobalNivelAgua(polo, nivelAgua);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Não foi possível obter o nível da água no " + polo.getNome() + ".", "Erro de Nível da Água", JOptionPane.WARNING_MESSAGE);
        }
    }

    private static void gerarRelatorioCompleto(Polo poloNorte, Polo poloSul) throws IOException, InterruptedException {
        StringBuilder relatorio = new StringBuilder();
        relatorio.append("📋 Relatório Completo de Alertas de Degelo ⚠️\n\n");

        boolean alertaGlobalAcionado = false;

        Double tempNorte = climaService.getTemperaturaAtual(poloNorte);
        double nivelNorte = sensorHidrico.obterNivelAgua(poloNorte);
        boolean alertaTempNorte = (tempNorte != null && climaService.verificarAlertaDegelo(poloNorte, 0.0));
        boolean alertaNivelNorte = sensorHidrico.verificarAlertaNivelAlto(poloNorte);

        relatorio.append("❄️ ").append(poloNorte.getNome()).append(":\n");
        relatorio.append("  Temperatura: ").append(tempNorte != null ? String.format("%.2f", tempNorte) + " °C" : "N/A").append("\n");
        relatorio.append("  Nível da Água: ").append(nivelNorte >= 0 ? String.format("%.2f", nivelNorte) + " metros" : "N/A").append("\n");

        if (alertaTempNorte) {
            relatorio.append("  ⚠️ ALERTA DE TEMPERATURA: Acima de 0°C!\n");
            alertaGlobalAcionado = true;
        } else {
            relatorio.append("  ✅ Temperatura: OK.\n");
        }
        if (alertaNivelNorte) {
            relatorio.append("  ⚠️ ALERTA DE NÍVEL DA ÁGUA: Acima do limite crítico!\n");
            alertaGlobalAcionado = true;
        } else {
            relatorio.append("  ✅ Nível da Água: OK.\n");
        }
        relatorio.append("\n");

        Double tempSul = climaService.getTemperaturaAtual(poloSul);
        double nivelSul = sensorHidrico.obterNivelAgua(poloSul);
        boolean alertaTempSul = (tempSul != null && climaService.verificarAlertaDegelo(poloSul));
        boolean alertaNivelSul = sensorHidrico.verificarAlertaNivelAlto(poloSul);

        relatorio.append("🐧 ").append(poloSul.getNome()).append(":\n");
        relatorio.append("  Temperatura: ").append(tempSul != null ? String.format("%.2f", tempSul) + " °C" : "N/A").append("\n");
        relatorio.append("  Nível da Água: ").append(nivelSul >= 0 ? String.format("%.2f", nivelSul) + " metros" : "N/A").append("\n");

        if (alertaTempSul) {
            relatorio.append("  ⚠️ ALERTA DE TEMPERATURA: Acima do limiar crítico!\n");
            alertaGlobalAcionado = true;
        } else {
            relatorio.append("  ✅ Temperatura: OK.\n");
        }
        if (alertaNivelSul) {
            relatorio.append("  ⚠️ ALERTA DE NÍVEL DA ÁGUA: Acima do limite crítico!\n");
            alertaGlobalAcionado = true;
        } else {
            relatorio.append("  ✅ Nível da Água: OK.\n");
        }
        relatorio.append("\n");

        if (!alertaGlobalAcionado) {
            relatorio.append("👍 Nenhum alerta crítico de degelo acionado para os polos no momento. Continue monitorando!\n");
        } else {
            relatorio.append("---\n");
            relatorio.append("🚨 ALERTA GLOBAL: Ação Urgente Necessária! 🚨\n");
            relatorio.append("As condições atuais indicam um risco elevado de degelo e elevação do nível do mar. É crucial que grandes empresas e indústrias intensifiquem seus esforços para combater as mudanças climáticas.\n\n");
            relatorio.append("Recomendações para Grandes Empresas:\n");
            relatorio.append(" - 📉 Reduzam o uso de combustíveis fósseis (carvão, petróleo, gás) e invistam em energia limpa.\n");
            relatorio.append(" - 💡 Usem energia de forma mais eficiente em suas fábricas e escritórios.\n");
            relatorio.append(" - ♻️ Reutilizem e reciclem mais materiais, produzindo menos lixo.\n");
            relatorio.append(" - 🌳 Plantem árvores e protejam florestas para ajudar a absorver CO2.\n");
            relatorio.append(" - 💧 Gerenciem a água de forma inteligente para evitar desperdício e poluição.\n");
            relatorio.append(" - 🗣️ Conversem com seus fornecedores e clientes sobre a importância de serem mais sustentáveis.\n\n");
            relatorio.append("O futuro do planeta depende das decisões tomadas hoje. Seja parte da solução!\n");
        }

        JOptionPane.showMessageDialog(null, relatorio.toString(), "Relatório de Monitoramento Polar", JOptionPane.INFORMATION_MESSAGE);
    }

    private static void exibirAlertaGlobalTemperatura(Polo polo, Double temperatura) {
        StringBuilder alertaMensagem = new StringBuilder();
        alertaMensagem.append("🚨 ALERTA DE TEMPERATURA CRÍTICA NO ").append(polo.getNome().toUpperCase()).append("! 🚨\n\n");
        alertaMensagem.append("Temperatura atual: ").append(String.format("%.2f", temperatura)).append(" °C\n");
        alertaMensagem.append("O valor está acima do limiar crítico de ").append(GLOBAL_TEMP_ALERT_THRESHOLD).append("°C, indicando aceleração do degelo.\n\n");
        alertaMensagem.append("--- Mensagem Urgente para Grandes Empresas e Indústrias ---\n");
        alertaMensagem.append("A elevação da temperatura polar exige ação imediata. Contribuam para a redução do aquecimento global através de:\n");
        alertaMensagem.append(" - 📉 Reduzam o uso de combustíveis fósseis (carvão, petróleo, gás) e invistam em energia limpa.\n");
        alertaMensagem.append(" - 💡 Usem energia de forma mais eficiente em suas fábricas e escritórios.\n");
        alertaMensagem.append(" - ♻️ Reutilizem e reciclem mais materiais, produzindo menos lixo.\n");
        alertaMensagem.append(" - 🌳 Plantem árvores e protejam florestas para ajudar a absorver CO2.\n");
        alertaMensagem.append(" - 💧 Gerenciem a água de forma inteligente para evitar desperdício e poluição.\n");
        alertaMensagem.append(" - 🗣️ Conversem com seus fornecedores e clientes sobre a importância de serem mais sustentáveis.\n\n");
        alertaMensagem.append("Sejam a mudança que o planeta precisa!\n");

        JOptionPane.showMessageDialog(null, alertaMensagem.toString(), "Alerta Crítico de Temperatura!", JOptionPane.ERROR_MESSAGE);
    }

    private static void exibirAlertaGlobalNivelAgua(Polo polo, double nivelAgua) {
        StringBuilder alertaMensagem = new StringBuilder();
        alertaMensagem.append("🚨 ALERTA DE NÍVEL DE ÁGUA CRÍTICO NO ").append(polo.getNome().toUpperCase()).append("! 🚨\n\n");
        alertaMensagem.append("Nível da água: ").append(String.format("%.2f", nivelAgua)).append(" metros\n");
        alertaMensagem.append("O valor está acima do limite crítico, indicando elevação do nível do mar devido ao degelo.\n\n");
        alertaMensagem.append("--- Mensagem Urgente para Grandes Empresas e Indústrias ---\n");
        alertaMensagem.append("A elevação do nível da água polar exige ação imediata. Contribuam para a redução do aquecimento global através de:\n");
        alertaMensagem.append(" - 📉 Reduzam o uso de combustíveis fósseis (carvão, petróleo, gás) e invistam em energia limpa.\n");
        alertaMensagem.append(" - 💡 Usem energia de forma mais eficiente em suas fábricas e escritórios.\n");
        alertaMensagem.append(" - ♻️ Reutilizem e reciclem mais materiais, produzindo menos lixo.\n");
        alertaMensagem.append(" - 🌳 Plantem árvores e protejam florestas para ajudar a absorver CO2.\n");
        alertaMensagem.append(" - 💧 Gerenciem a água de forma inteligente para evitar desperdício e poluição.\n");
        alertaMensagem.append(" - 🗣️ Conversem com seus fornecedores e clientes sobre a importância de serem mais sustentáveis.\n\n");
        alertaMensagem.append("Sejam a mudança que o planeta precisa!\n");

        JOptionPane.showMessageDialog(null, alertaMensagem.toString(), "Alerta Crítico de Nível da Água!", JOptionPane.ERROR_MESSAGE);
    }
}