<<<<<<< HEAD
# 🤝 DoaCerto - Sistema de Gestão de Doações

**DoaCerto** é um sistema desktop desenvolvido em Java para conectar pessoas que desejam doar itens (móveis, eletrodomésticos, roupas) a beneficiários que precisam desses itens. O sistema gerencia todo o ciclo de vida da doação, desde o cadastro do item até a aprovação e avaliação final.

---

## 🚀 Funcionalidades

O sistema possui três módulos principais acessíveis via Tela Inicial:

### 🟢 Módulo Doador
* **Cadastro de Itens:** Registro de objetos com descrição, categoria e estado de conservação.
* **Criação de Anúncios:** Agrupamento de itens em anúncios públicos com opções de frete e localização.
* **Gestão:** Visualização de itens e anúncios cadastrados.

### 🟠 Módulo Beneficiário
* **Mural de Doações:** Visualização de anúncios disponíveis em tempo real.
* **Solicitação:** Pedido de doação de itens de interesse.

### 🔵 Módulo Gestor (Administração)
* **Análise de Pedidos:** Aprovação ou Rejeição de solicitações de doação.
* **Agendamento:** Definição de datas de entrega para doações aprovadas.
* **Controle Total:** Acesso a todos os cadastros do sistema.

### ⭐ Sistema de Avaliação
* Após a conclusão da doação, o beneficiário avalia a experiência.
* Cálculo automático da reputação (nota média) do Doador baseado nas avaliações recebidas.

---

## 🛠️ Tecnologias Utilizadas

* **Linguagem:** Java (JDK 8 ou superior)
* **Interface Gráfica (GUI):** Java Swing (WindowBuilder)
* **Banco de Dados:** MySQL 8.0
* **Conexão:** JDBC (MySQL Connector)
* **IDE Recomendada:** Eclipse
* **Arquitetura:** MVC (Model - View - Controller/DAO)
* **Conceitos de OO:** Herança, Polimorfismo e Encapsulamento.

---

## 📂 Estrutura do Projeto

O projeto está organizado seguindo o padrão de camadas MVC:

```text
DoaCerto/
├── src/
│   └── br.com.doacao
│       ├── dao/          # Camada de acesso ao Banco de Dados (SQL)
│       ├── model/        # Classes POJO (Doador, Item, Doacao...)
│       ├── util/         # Configuração de Conexão (ConnectionFactory)
│       └── view/         # Telas (JFrames)
├── libs/                 # Driver JDBC (mysql-connector-j.jar)
├── database/             # Script SQL para criação do banco
└── README.md             # Documentação do projeto

=======
# 🤝 DoaCerto - Sistema de Gestão de Doações

**DoaCerto** é um sistema desktop desenvolvido em Java para conectar pessoas que desejam doar itens (móveis, eletrodomésticos, roupas) a beneficiários que precisam desses itens. O sistema gerencia todo o ciclo de vida da doação, desde o cadastro do item até a aprovação e avaliação final.

---

## 🚀 Funcionalidades

O sistema possui três módulos principais acessíveis via Tela Inicial:

### 🟢 Módulo Doador
* **Cadastro de Itens:** Registro de objetos com descrição, categoria e estado de conservação.
* **Criação de Anúncios:** Agrupamento de itens em anúncios públicos com opções de frete e localização.
* **Gestão:** Visualização de itens e anúncios cadastrados.

### 🟠 Módulo Beneficiário
* **Mural de Doações:** Visualização de anúncios disponíveis em tempo real.
* **Solicitação:** Pedido de doação de itens de interesse.

### 🔵 Módulo Gestor (Administração)
* **Análise de Pedidos:** Aprovação ou Rejeição de solicitações de doação.
* **Agendamento:** Definição de datas de entrega para doações aprovadas.
* **Controle Total:** Acesso a todos os cadastros do sistema.

### ⭐ Sistema de Avaliação
* Após a conclusão da doação, o beneficiário avalia a experiência.
* Cálculo automático da reputação (nota média) do Doador baseado nas avaliações recebidas.

---

## 🛠️ Tecnologias Utilizadas

* **Linguagem:** Java (JDK 8 ou superior)
* **Interface Gráfica (GUI):** Java Swing (WindowBuilder)
* **Banco de Dados:** MySQL 8.0
* **Conexão:** JDBC (MySQL Connector)
* **IDE Recomendada:** Eclipse
* **Arquitetura:** MVC (Model - View - Controller/DAO)
* **Conceitos de OO:** Herança, Polimorfismo e Encapsulamento.

---

## 📂 Estrutura do Projeto

O projeto está organizado seguindo o padrão de camadas MVC:

```text
DoaCerto/
├── src/
│   └── br.com.doacao
│       ├── dao/          # Camada de acesso ao Banco de Dados (SQL)
│       ├── model/        # Classes POJO (Doador, Item, Doacao...)
│       ├── util/         # Configuração de Conexão (ConnectionFactory)
│       └── view/         # Telas (JFrames)
├── libs/                 # Driver JDBC (mysql-connector-j.jar)
├── database/             # Script SQL para criação do banco
└── README.md             # Documentação do projeto
>>>>>>> 12483d0d825cad14bae206c525a8b33690a424b9
