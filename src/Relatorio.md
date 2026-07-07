# Relatório do Projeto
# Sistema de Gestão de Estacionamento

## Curso
Engenharia Informática

## Disciplina
Programação Orientada a Objetos

## Autores
- Marco Soares
- Elizeu Gonçalves
- David dos Santos

---

# Índice

1. Introdução
2. Objetivos
3. Tecnologias Utilizadas
4. Arquitetura do Sistema
5. Diagrama Geral
6. Descrição das Classes
7. Estruturas de Dados
8. Base de Dados
9. Funcionalidades Implementadas
10. Validações Implementadas
11. Fluxo de Funcionamento
12. Testes Realizados
13. Conclusão

---

# 1. Introdução

O presente projeto consiste no desenvolvimento de um Sistema de Gestão de Estacionamento utilizando a linguagem Java.

O sistema foi desenvolvido aplicando os princípios da Programação Orientada a Objetos, permitindo controlar a entrada e saída de veículos, gerir vagas, calcular pagamentos e manter as informações armazenadas numa base de dados MySQL.

O objetivo principal foi desenvolver uma aplicação organizada, reutilizável e de fácil manutenção, utilizando listas ligadas desenvolvidas manualmente para armazenamento em memória e MySQL para persistência dos dados.

---

# 2. Objetivos

## Objetivo Geral

Desenvolver um sistema capaz de gerir o funcionamento de um estacionamento de forma automática.

## Objetivos Específicos

- Registar entrada de veículos.
- Registar saída de veículos.
- Controlar vagas livres, ocupadas e reservadas.
- Calcular o valor do estacionamento.
- Gerar recibo de pagamento.
- Pesquisar veículos.
- Atualizar dados dos veículos.
- Armazenar informações numa base de dados MySQL.

---

# 3. Tecnologias Utilizadas

- Java
- Visual Studio Code
- MySQL
- WampServer
- JDBC (MySQL Connector)
- Git/GitHub

---

# 4. Arquitetura do Sistema

O sistema foi dividido em várias classes, cada uma responsável por uma tarefa específica.

Essa divisão segue o princípio da responsabilidade única (Single Responsibility Principle), facilitando a organização e manutenção do código.

As principais classes são:

- Veiculo
- Vaga
- Pagamento
- Estacionamento
- Database
- ListaVeiculos
- ListaVaga
- ListaPagamento
- NoVeiculo
- NoVaga
- NoPagamento
- App

---

# 5. Diagrama Geral

```
                App
                 │
                 │
        Estacionamento
        ├───────────────┐
        │               │
     Veiculo          Vaga
        │               │
        └──────┬────────┘
               │
          Pagamento
               │
          Database
               │
            MySQL
```

---

# 6. Descrição das Classes

## 6.1 Classe Veiculo

Representa um veículo que entra no estacionamento.

### Atributos

- matricula
- marca
- modelo
- horaEntrada
- horaSaida
- vagaAssociada
- estacionado

### Principais métodos

- registrarEntrada()
- registrarSaida()
- validarMatricula()
- estruturaMatricula()

A matrícula é validada utilizando Expressões Regulares (Regex), garantindo que apenas formatos válidos sejam aceites.

---

## 6.2 Classe Vaga

Representa uma vaga física do estacionamento.

### Atributos

- numeroVaga
- ocupada
- reservada
- veiculoAssociado

### Métodos

- ocuparVaga()
- liberarVaga()
- reservarVaga()
- cancelarReserva()
- verificarDisponibilidade()

Esta classe controla o estado atual de cada vaga.

---

## 6.3 Classe Pagamento

Responsável pelo cálculo dos pagamentos.

### Atributos

- valorPorHora
- valorTotal
- tempoPermanencia
- dataPagamento
- pago

### Métodos

- calcularTempoPermanencia()
- calcularValorTotal()
- confirmarPagamento()
- exibirRecibo()

O cálculo é realizado utilizando Duration.

Regra implementada:

- Até 15 minutos adicionais mantém a hora anterior.
- Acima de 15 minutos cobra uma nova hora.

---

## 6.4 Classe Estacionamento

É considerada o cérebro do sistema.

Coordena todas as operações entre veículos, vagas e pagamentos.

### Métodos

- adicionarVaga()
- registraEntrada()
- registraSaida()
- procurarVagaLivre()
- listarVagas()
- listarVeiculosEstacionados()
- atualizarVagaVeiculo()
- procurarVeiculoPorMatricula()
- registrarPagamento()

---

## 6.5 Classe Database

Responsável pela comunicação entre o sistema e a base de dados.

### Funcionalidades

- Testar ligação
- Criar tabelas
- Guardar veículos
- Atualizar veículos
- Guardar vagas
- Atualizar vagas
- Guardar pagamentos
- Carregar veículos
- Carregar vagas
- Pesquisar por marca
- Relatórios

---

## 6.6 Classe App

Classe principal do sistema.

Contém o menu principal e controla toda a interação com o utilizador.

---

# 7. Estruturas de Dados

Para cumprir os objetivos da disciplina, foram implementadas listas ligadas manualmente.

## ListaVeiculos

Armazena todos os veículos estacionados.

Cada elemento é um objeto NoVeiculo.

---

## ListaVaga

Armazena todas as vagas existentes.

Cada elemento é um objeto NoVaga.

---

## ListaPagamento

Armazena todos os pagamentos efetuados.

Cada elemento é um objeto NoPagamento.

---

## Nós

Cada lista é composta por nós contendo:

- informação
- referência para o próximo nó

Esta implementação substitui o uso de ArrayList.

---

# 8. Base de Dados

Foi utilizada a base de dados MySQL.

As tabelas criadas foram:

## vagas

Campos:

- numero_vaga
- ocupada
- reservada

---

## veiculos

Campos:

- matricula
- marca
- modelo
- hora_entrada
- hora_saida
- estacionado
- numero_vaga

---

## pagamentos

Campos:

- id
- matricula
- valor_total
- tempo_horas
- data_pagamento
- pago

---

# 9. Funcionalidades Implementadas

O sistema permite:

- Registar entrada de veículos.
- Registar saída.
- Reservar vagas.
- Cancelar reservas.
- Atualizar dados.
- Pesquisar veículos.
- Pesquisar por marca/modelo.
- Listar veículos.
- Listar vagas.
- Calcular pagamentos.
- Gerar recibos.
- Gerar relatório.
- Guardar informações automaticamente na base de dados.

---

# 10. Validações Implementadas

Durante o desenvolvimento foram implementadas diversas validações.

## Matrícula

- Não pode ser nula.
- Não pode estar vazia.
- Não pode conter espaços.
- Deve seguir o formato definido.

---

## Entrada

Não permite:

- veículo já estacionado;
- vaga inexistente;
- vaga ocupada;
- vaga reservada.

---

## Saída

Verifica:

- existência do veículo;
- veículo estacionado;
- vaga associada.

---

## Pagamento

Verifica:

- tempo calculado;
- valor calculado;
- pagamento duplicado.

---

## Reserva

Verifica:

- vaga livre;
- vaga ocupada;
- vaga reservada.

---

# 11. Fluxo de Funcionamento

```
Entrada

↓

Escolher vaga livre

↓

Associar veículo

↓

Guardar na base de dados

↓

Estacionamento

↓

Saída

↓

Calcular permanência

↓

Calcular pagamento

↓

Gerar recibo

↓

Guardar pagamento

↓

Libertar vaga
```

---

# 12. Testes Realizados

Durante o desenvolvimento foram realizados diversos testes.

## Entrada

✔ Funcionou corretamente.

---

## Saída

✔ Funcionou corretamente.

---

## Pagamento

✔ Valor calculado corretamente.

---

## Reserva

✔ Funcionou corretamente.

---

## Cancelamento

✔ Funcionou corretamente.

---

## Atualização

✔ Marca e modelo atualizados corretamente.

---

## Pesquisa

✔ Pesquisa por matrícula.

✔ Pesquisa por marca/modelo.

---

## Persistência

Todos os dados permaneceram armazenados na base de dados mesmo após reiniciar o sistema.

---

# 13. Conclusão

O projeto atingiu todos os objetivos inicialmente definidos.

Foi possível desenvolver um sistema completo de gestão de estacionamento utilizando Programação Orientada a Objetos, estruturas de dados e integração com uma base de dados MySQL.

Durante o desenvolvimento foram aplicados conceitos como encapsulamento, associação entre classes, listas ligadas, persistência de dados e validações, tornando o sistema organizado, reutilizável e de fácil manutenção.

A integração com a base de dados permitiu garantir que todas as informações permanecessem armazenadas mesmo após o encerramento da aplicação, aumentando a fiabilidade do sistema.

O projeto permitiu consolidar os conhecimentos adquiridos ao longo da disciplina, demonstrando a aplicação prática dos conceitos estudados.