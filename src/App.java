import java.util.Scanner;

public class App {
    public static void main(String[] args) {

        // 1. testar ligacao antes de tudo
        if (!Database.testarLigacao()) {
            System.out.println("Nao foi possivel ligar ao MySQL. Verifica se o servidor esta ligado.");
            return;
        }

        //2. criar tabelas se nao existirem 
        //Database.criarTabelas(); - ja temos o estacionamento.sql

        // 3. criar o estacionamento e carregar dados guardados
        Estacionamento estacionamento = new Estacionamento();
        Database.carregarVagas(estacionamento);
        Database.carregarVeiculosEstacionados(estacionamento);

        // 4. adicionar vagas iniciais se o estacionamento estiver vazio
        if (estacionamento.getVagas().getTamanho() == 0) {
            System.out.println("A criar vagas iniciais...");
            for (int i = 1; i <= 10; i++) {
                Vaga v = new Vaga(i);
                estacionamento.adicionarVaga(v);
                Database.guardarVaga(v);
            }
        }

        Scanner scanner = new Scanner(System.in);
        int opcao = -1;

        System.out.println("\n=== Sistema de Gestao de Estacionamento ===");

        while (opcao != 0) {
            System.out.println("\n--- MENU ---");
            System.out.println("1. Registar entrada de veiculo");
            System.out.println("2. Registar saida de veiculo");
            System.out.println("3. Listar veiculos estacionados");
            System.out.println("4. Pesquisar veiculo por matricula");
            System.out.println("5. Pesquisar por marca ou modelo");
            System.out.println("6. Listar todas as vagas");
            System.out.println("7. Reservar vaga");
            System.out.println("8. Cancelar reserva");
            System.out.println("9. Actualizar dados do veiculo");
            System.out.println("10. Relatorio do estacionamento");
            System.out.println("0. Sair");
            System.out.print("Opcao: ");

            try {
                opcao = Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Opcao invalida.");
                continue;
            }

            switch (opcao) {

                case 1: {
                    // registar entrada
                    System.out.print("Matricula (ex: ST-01-AB): ");
                    String matricula = scanner.nextLine().trim().toUpperCase();

                    System.out.print("Marca: ");
                    String marca = scanner.nextLine().trim();

                    System.out.print("Modelo: ");
                    String modelo = scanner.nextLine().trim();

                    try {
                        Veiculo veiculo = new Veiculo(matricula, marca, modelo);
                        estacionamento.registraEntrada(veiculo);

                        // se entrou com sucesso guarda na BD
                        if (veiculo.isEstacionado()) {
                            Database.guardarVeiculo(veiculo);
                            Database.actualizarVaga(veiculo.getVagaAssociada());
                        }
                    } catch (IllegalArgumentException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                }

                case 2: {
                    // registar saida
                    System.out.print("Matricula do veiculo a sair: ");
                    String matricula = scanner.nextLine().trim().toUpperCase();

                    Veiculo veiculo = estacionamento.procurarVeiculoPorMatricula(matricula);
                    if (veiculo == null) {
                        System.out.println("Veiculo nao encontrado.");
                        break;
                    }

                    Vaga vagaAntes = veiculo.getVagaAssociada();
                    estacionamento.registraSaida(veiculo);

                    // actualizar BD
                    Database.actualizarVeiculoSaida(veiculo);
                    if (vagaAntes != null) {
                        Database.actualizarVaga(vagaAntes);
                    }

                    // calcular e guardar pagamento
                    Pagamento pagamento = new Pagamento(veiculo);
                    pagamento.calcularTempoPermanencia();
                    pagamento.calcularValorTotal();
                    pagamento.confirmarPagamento();
                    pagamento.exibirRecibo();

                    estacionamento.registrarPagamento(pagamento);
                    Database.guardarPagamento(pagamento);
                    break;
                }

                case 3: {
                    // listar veiculos estacionados
                    System.out.println("\n--- Veiculos estacionados ---");
                    estacionamento.listarVeiculosEstacionados();
                    break;
                }

                case 4: {
                    // pesquisar por matricula
                    System.out.print("Matricula a pesquisar: ");
                    String matricula = scanner.nextLine().trim().toUpperCase();
                    Veiculo encontrado = estacionamento.procurarVeiculoPorMatricula(matricula);
                    if (encontrado != null) {
                        System.out.println(encontrado);
                    }
                    break;
                }

                case 5: {
                    // pesquisar por marca ou modelo (vai a BD)
                    System.out.print("Marca ou modelo a pesquisar: ");
                    String termo = scanner.nextLine().trim();
                    Database.pesquisarPorMarcaOuModelo(termo);
                    break;
                }

                case 6: {
                    // listar vagas
                    System.out.println("\n--- Vagas ---");
                    estacionamento.listarVagas();
                    break;
                }

                case 7: {
                    // reservar vaga
                    System.out.print("Numero da vaga a reservar: ");
                    try {
                        int num = Integer.parseInt(scanner.nextLine().trim());
                        Vaga vaga = estacionamento.getVagas().procurarPorNumero(num);
                        if (vaga == null) {
                            System.out.println("Vaga nao encontrada.");
                        } else {
                            vaga.reservarVaga();
                            Database.actualizarVaga(vaga);
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Numero invalido.");
                    }
                    break;
                }

                case 8: {
                    // cancelar reserva
                    System.out.print("Numero da vaga para cancelar reserva: ");
                    try {
                        int num = Integer.parseInt(scanner.nextLine().trim());
                        Vaga vaga = estacionamento.getVagas().procurarPorNumero(num);
                        if (vaga == null) {
                            System.out.println("Vaga nao encontrada.");
                        } else {
                            vaga.cancelarReserva();
                            Database.actualizarVaga(vaga);
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Numero invalido.");
                    }
                    break;
                }

                case 9: {
                    // actualizar dados do veiculo
                    System.out.print("Matricula do veiculo a actualizar: ");
                    String matricula = scanner.nextLine().trim().toUpperCase();

                    Veiculo veiculo = estacionamento.procurarVeiculoPorMatricula(matricula);
                    if (veiculo == null) {
                        System.out.println("Veiculo nao encontrado.");
                        break;
                    }

                    System.out.print("Nova marca (actual: " + veiculo.getMarca() + "): ");
                    String novaMarca = scanner.nextLine().trim();

                    System.out.print("Novo modelo (actual: " + veiculo.getModelo() + "): ");
                    String novoModelo = scanner.nextLine().trim();

                    if (!novaMarca.isEmpty()) veiculo.setMarca(novaMarca);
                    if (!novoModelo.isEmpty()) veiculo.setModelo(novoModelo);

                    Database.actualizarDadosVeiculo(matricula,
                        novaMarca.isEmpty()  ? veiculo.getMarca()   : novaMarca,
                        novoModelo.isEmpty() ? veiculo.getModelo()  : novoModelo);
                    break;
                }

                case 10: {
                    // relatorio
                    System.out.println("\n======= RELATORIO =======");
                    System.out.println("Vagas livres      : " + Database.totalVagasLivres());
                    System.out.println("Total arrecadado  : " + Database.totalArrecadado() + " CVE");
                    System.out.println("Veiculos actuais  : " + estacionamento.getVeiculos().getTamanho());
                    System.out.println("-------------------------");
                    Database.listarPagamentos();
                    break;
                }

                case 0:
                    System.out.println("A sair... Ate logo!");
                    break;

                default:
                    System.out.println("Opcao invalida. Tenta novamente.");
            }
        }

        scanner.close();
    }
}