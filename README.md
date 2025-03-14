# 🎯 Word Guess Game  

> 🔠 Adivinhe a palavra com base na definição!  

Você tem **três tentativas** e pode solicitar uma dica, mas isso custará uma tentativa!  

🔹 Escolha entre diferentes **temas** e **níveis de dificuldade** (*fácil, médio ou difícil*).  
🔹 Acompanhe o **histórico de palavras** já jogadas.  

---

## 🚀 Como funciona?  

1. O jogo exibe a **definição** da palavra.  
2. Você tem **três tentativas** para acertar.  
3. Se precisar, pode pedir uma **dica**, mas perderá uma tentativa.  
4. A resposta pode ser a palavra exata ou **dois sinônimos** dela.  


---

## 🔌 Arquitetura do Sistema  

O jogo é desenvolvido utilizando **Java RMI (Remote Method Invocation)** e **Threads** para otimizar o processamento.  

### 🖥️ Módulos do sistema:  

🔸 **Servidor de Banco de Dados (RMI)**  
   - Armazena as palavras no banco de dados.  
   - Utiliza **threads** para salvar os dados sem bloquear o jogo.  

🔸 **Servidor de Sorteio de Palavras (RMI)**  
   - Utiliza a **API do ChatGPT** para selecionar palavras aleatórias.  

🔸 **Servidor de Gerenciamento do Jogo (RMI)**  
   - Gerencia a lógica do jogo e controla as tentativas do jogador.  
   - Processa a **distância de Levenshtein** em **paralelo** para calcular as similaridades com a resposta correta e os sinônimos.  
   - Realiza cálculos estatísticos sobre as palavras sorteadas.

---

## ⚙️ Tecnologias Utilizadas  

- **Java** ☕  
- **RMI (Remote Method Invocation)** 🔗  
- **Threads & Paralelismo** 🚀  
- **Banco de Dados** 🗄️  
- **API ChatGPT** 🧠  
- **Docker para o Banco de dados postgres** 🐘
- **React para frontend** 🖌️


