import random
import os

# Listas globais
temperatura_polonorte = []
temperatura_polosul = []
nivelagua_polonorte = []
nivelagua_polosul = []

def limpar_tela():
    os.system('cls' if os.name == 'nt' else 'clear')

def calcular_estatisticas(lista):
    if lista:
        media = round(sum(lista) / len(lista), 2)
        maxima = max(lista)
        minima = min(lista)
        return media, maxima, minima
    return 0, 0, 0

def menu_ecoambiental():
    while True:
        limpar_tela()
        print('❄️  ==== Menu Principal - Grupo EcoAmbiental ====❄️')
        print("🌡️  1 - Ver temperatura Polo Norte")
        print("🌡️  2 - Ver temperatura Polo Sul")
        print("💧 3 - Ver nível da água Polo Norte")
        print("💧 4 - Ver nível da água Polo Sul")
        print("📋 5 - Gerar Relatório Completo de Alertas")
        print("👥 6 - Ver integrantes do Grupo EcoAmbiental")
        print("🌎 7 - Nossa solução")
        print("❌ 8 - Sair")
        
        try:
            opcao = int(input("Qual opção você deseja? "))
        except ValueError:
            print("Entrada inválida. Por favor, digite um número.")
            input("\nPressione Enter para continuar...")
            continue

        if opcao == 1:
            ver_temperatura_polonorte()
        elif opcao == 2:
            ver_temperatura_polosul()
        elif opcao == 3:
            ver_nivel_polonorte()
        elif opcao == 4:
            ver_nivel_polosul()
        elif opcao == 5:
            relatorio()
        elif opcao == 6:
            integrantes()
        elif opcao == 7:
            solucao()
        elif opcao == 8:
            print("Saindo... Obrigado por usar o Programa EcoAmbiental!")
            break
        else:
            print("Não existe essa opção! Por favor, escolha de 1 a 8.")
            input("\nPressione Enter para continuar...")

def ver_temperatura_polonorte():
    limpar_tela()
    temp = round(random.uniform(-40.0, 5.0), 2)
    temperatura_polonorte.append(temp)
    print(f"Temperatura atual do Polo Norte: {temp}°C ❄️")
    input("\nPressione Enter para voltar ao menu...")

def ver_temperatura_polosul():
    limpar_tela()
    temp = round(random.uniform(-60.0, 0.0), 2)
    temperatura_polosul.append(temp)
    print(f"Temperatura atual do Polo Sul: {temp}°C ❄️")
    input("\nPressione Enter para voltar ao menu...")

def ver_nivel_polonorte():
    limpar_tela()
    nivel = round(random.uniform(0.0, 1.5), 2)
    nivelagua_polonorte.append(nivel)
    print(f"Nível da água no Polo Norte: {nivel}m")
    input("\nPressione Enter para voltar ao menu...")

def ver_nivel_polosul():
    limpar_tela()
    nivel = round(random.uniform(0.0, 1.0), 2)
    nivelagua_polosul.append(nivel)
    print(f"Nível da água no Polo Sul: {nivel}m")
    input("\nPressione Enter para voltar ao menu...")

def relatorio():
    limpar_tela()

    if not (temperatura_polonorte and temperatura_polosul and nivelagua_polonorte and nivelagua_polosul):
        print("⚠️ Atenção: é necessário preencher os 4 módulos antes de gerar o relatório!")
        print("- Temperatura Polo Norte")
        print("- Temperatura Polo Sul")
        print("- Nível da água Polo Norte")
        print("- Nível da água Polo Sul")
        input("\nPressione Enter para voltar ao menu...")
        return

    print("📋 ==== RELATÓRIO COMPLETO ====\n")

    # Temperatura Polo Norte
    print("🌡️ Temperatura Polo Norte:")
    print(f"Última registrada: {temperatura_polonorte[-1]}°C")
    media, maxima, minima = calcular_estatisticas(temperatura_polonorte)
    print(f"🔎 Média: {media}°C\n🔺 Máxima: {maxima}°C\n🔻 Mínima: {minima}°C\n")

    # Temperatura Polo Sul
    print("🌡️ Temperatura Polo Sul:")
    print(f"Última registrada: {temperatura_polosul[-1]}°C")
    media, maxima, minima = calcular_estatisticas(temperatura_polosul)
    print(f"🔎 Média: {media}°C\n🔺 Máxima: {maxima}°C\n🔻 Mínima: {minima}°C\n")

    # Nível da água Polo Norte
    print("💧 Nível da água Polo Norte:")
    print(f"Último registrado: {nivelagua_polonorte[-1]}m")
    media, maxima, minima = calcular_estatisticas(nivelagua_polonorte)
    print(f"🔎 Média: {media}m\n🔺 Máximo: {maxima}m\n🔻 Mínimo: {minima}m\n")

    # Nível da água Polo Sul
    print("💧 Nível da água Polo Sul:")
    print(f"Último registrado: {nivelagua_polosul[-1]}m")
    media, maxima, minima = calcular_estatisticas(nivelagua_polosul)
    print(f"🔎 Média: {media}m\n🔺 Máximo: {maxima}m\n🔻 Mínimo: {minima}m\n")

    print("📢 RECOMENDAÇÕES:")
    print("- Reduzir emissões de carbono")
    print("- Investir em energia limpa")
    print("- Reflorestamento e conservação ambiental")

    input("\nPressione Enter para voltar ao menu...")

def integrantes():
    limpar_tela()
    print("👥 Integrantes do Grupo:")
    print("Murillo Fernandes Carapia, RM: 564969")
    print("Leonardo Zerbinatti de Sales, RM: 562992")
    print("Breno da Fonseca Eleutério, RM: 564948")
    input("\nPressione Enter para voltar ao menu...")

def solucao():
    limpar_tela()
    print("🌎 Solução proposta para o degelo polar\n")
    print("🔍 Plataforma de monitoramento em tempo real com sensores nos polos.")
    print("⚠️ Alertas para empresas e recomendações automáticas.")
    print("❄️ Foco em reduzir o aquecimento global e evitar desastres.")
    input("\nPressione Enter para voltar ao menu...")

# Executar o programa
menu_ecoambiental()
