import java.sql.*;

public class Database {

    private static final String URL      = "jdbc:mysql://localhost:3306/estacionamento?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
    private static final String USUARIO  = "root";
    private static final String PASSWORD = "";


    // LIGACAO

    public static Connection conectar() throws SQLException {
        return DriverManager.getConnection(URL, USUARIO, PASSWORD);
    }

    // testa a ligacao e mostra erro detalhado se falhar
    public static boolean testarLigacao() {
        try (Connection conn = conectar()) {
            System.out.println("Ligacao ao MySQL bem sucedida!");
            return true;
        } catch (SQLException e) {
            System.out.println("ERRO ao ligar ao MySQL: " + e.getMessage());
            System.out.println("Codigo do erro: " + e.getErrorCode());
            return false;
        }
    }


    // CRIAR TABELAS

    public static void criarTabelas() {

        String criarVagas = """
            CREATE TABLE IF NOT EXISTS vagas (
                numero_vaga  INT PRIMARY KEY,
                ocupada      TINYINT(1) DEFAULT 0,
                reservada    TINYINT(1) DEFAULT 0
            )
        """;

        String criarVeiculos = """
            CREATE TABLE IF NOT EXISTS veiculos (
                matricula    VARCHAR(20) PRIMARY KEY,
                marca        VARCHAR(50),
                modelo       VARCHAR(50),
                hora_entrada DATETIME,
                hora_saida   DATETIME,
                estacionado  TINYINT(1) DEFAULT 0,
                numero_vaga  INT,
                FOREIGN KEY (numero_vaga) REFERENCES vagas(numero_vaga)
            )
        """;

        String criarPagamentos = """
            CREATE TABLE IF NOT EXISTS pagamentos (
                id             INT AUTO_INCREMENT PRIMARY KEY,
                matricula      VARCHAR(20),
                valor_total    DOUBLE,
                tempo_horas    BIGINT,
                data_pagamento DATETIME,
                pago           TINYINT(1) DEFAULT 0,
                FOREIGN KEY (matricula) REFERENCES veiculos(matricula)
            )
        """;

        try (Connection conn = conectar();
             Statement stmt = conn.createStatement()) {

            stmt.execute(criarVagas);
            stmt.execute(criarVeiculos);
            stmt.execute(criarPagamentos);
            System.out.println("Tabelas criadas com sucesso.");

        } catch (SQLException e) {
            System.out.println("ERRO ao criar tabelas: " + e.getMessage());
        }
    }

    // VAGAS

    public static void guardarVaga(Vaga vaga) {
        String query = "INSERT IGNORE INTO vagas (numero_vaga, ocupada, reservada) VALUES (?, ?, ?)";

        try (Connection conn = conectar();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, vaga.getNumeroVaga());
            ps.setBoolean(2, vaga.isOcupada());
            ps.setBoolean(3, vaga.isReservada());
            ps.executeUpdate();

        } catch (SQLException e) {
            System.out.println("ERRO ao guardar vaga: " + e.getMessage());
        }
    }

    public static void actualizarVaga(Vaga vaga) {
        String query = "UPDATE vagas SET ocupada = ?, reservada = ? WHERE numero_vaga = ?";

        try (Connection conn = conectar();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setBoolean(1, vaga.isOcupada());
            ps.setBoolean(2, vaga.isReservada());
            ps.setInt(3, vaga.getNumeroVaga());
            ps.executeUpdate();

        } catch (SQLException e) {
            System.out.println("ERRO ao actualizar vaga: " + e.getMessage());
        }
    }

    public static void carregarVagas(Estacionamento estacionamento) {
        String query = "SELECT * FROM vagas";

        try (Connection conn = conectar();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Vaga vaga = new Vaga(rs.getInt("numero_vaga"));
                vaga.setOcupada(rs.getBoolean("ocupada"));
                vaga.setReservada(rs.getBoolean("reservada"));
                estacionamento.getVagas().inserirFim(vaga);
            }
            System.out.println("Vagas carregadas.");

        } catch (SQLException e) {
            System.out.println("ERRO ao carregar vagas: " + e.getMessage());
        }
    }


    // VEICULOS


    public static void guardarVeiculo(Veiculo veiculo) {
        String query = """
            INSERT INTO veiculos (matricula, marca, modelo, hora_entrada, hora_saida, estacionado, numero_vaga)
            VALUES (?, ?, ?, ?, ?, ?, ?)
            ON DUPLICATE KEY UPDATE
                hora_entrada = VALUES(hora_entrada),
                hora_saida   = VALUES(hora_saida),
                estacionado  = VALUES(estacionado),
                numero_vaga  = VALUES(numero_vaga)
        """;

        try (Connection conn = conectar();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, veiculo.getMatricula());
            ps.setString(2, veiculo.getMarca());
            ps.setString(3, veiculo.getModelo());

            if (veiculo.getHoraEntrada() != null) {
                ps.setTimestamp(4, Timestamp.valueOf(veiculo.getHoraEntrada()));
            } else {
                ps.setNull(4, Types.TIMESTAMP);
            }

            if (veiculo.getHoraSaida() != null) {
                ps.setTimestamp(5, Timestamp.valueOf(veiculo.getHoraSaida()));
            } else {
                ps.setNull(5, Types.TIMESTAMP);
            }

            ps.setBoolean(6, veiculo.isEstacionado());

            if (veiculo.getVagaAssociada() != null) {
                ps.setInt(7, veiculo.getVagaAssociada().getNumeroVaga());
            } else {
                ps.setNull(7, Types.INTEGER);
            }

            ps.executeUpdate();
            System.out.println("Veiculo guardado na base de dados.");

        } catch (SQLException e) {
            System.out.println("ERRO ao guardar veiculo: " + e.getMessage());
        }
    }

    public static void actualizarVeiculoSaida(Veiculo veiculo) {
        String query = "UPDATE veiculos SET hora_saida = ?, estacionado = 0, numero_vaga = NULL WHERE matricula = ?";

        try (Connection conn = conectar();
             PreparedStatement ps = conn.prepareStatement(query)) {

            if (veiculo.getHoraSaida() != null) {
                ps.setTimestamp(1, Timestamp.valueOf(veiculo.getHoraSaida()));
            } else {
                ps.setNull(1, Types.TIMESTAMP);
            }

            ps.setString(2, veiculo.getMatricula());
            ps.executeUpdate();

        } catch (SQLException e) {
            System.out.println("ERRO ao actualizar saida: " + e.getMessage());
        }
    }

    public static void actualizarDadosVeiculo(String matricula, String novaMarca, String novoModelo) {
        String query = "UPDATE veiculos SET marca = ?, modelo = ? WHERE matricula = ?";

        try (Connection conn = conectar();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, novaMarca);
            ps.setString(2, novoModelo);
            ps.setString(3, matricula);
            ps.executeUpdate();
            System.out.println("Dados do veiculo actualizados.");

        } catch (SQLException e) {
            System.out.println("ERRO ao actualizar dados: " + e.getMessage());
        }
    }

    public static void carregarVeiculosEstacionados(Estacionamento estacionamento) {
        String query = "SELECT * FROM veiculos WHERE estacionado = 1";

        try (Connection conn = conectar();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Veiculo veiculo = new Veiculo(rs.getString("matricula"));
                veiculo.setMarca(rs.getString("marca"));
                veiculo.setModelo(rs.getString("modelo"));
                veiculo.setEstacionado(true);

                Timestamp entrada = rs.getTimestamp("hora_entrada");
                if (entrada != null) {
                    veiculo.setHoraEntradaManual(entrada.toLocalDateTime());
                }

                int numeroVaga = rs.getInt("numero_vaga");
                if (!rs.wasNull()) {
                    Vaga vaga = estacionamento.getVagas().procurarPorNumero(numeroVaga);
                    if (vaga != null) {
                        veiculo.setVagaAssociada(vaga);
                        vaga.setVeiculoAssociado(veiculo);
                        vaga.setOcupada(true);
                    }
                }

                estacionamento.getVeiculos().inserirFim(veiculo);
            }
            System.out.println("Veiculos estacionados carregados.");

        } catch (SQLException e) {
            System.out.println("ERRO ao carregar veiculos: " + e.getMessage());
        }
    }

    public static void pesquisarPorMarcaOuModelo(String termo) {
        String query = "SELECT * FROM veiculos WHERE marca LIKE ? OR modelo LIKE ?";

        try (Connection conn = conectar();
             PreparedStatement ps = conn.prepareStatement(query)) {

            String t = "%" + termo + "%";
            ps.setString(1, t);
            ps.setString(2, t);

            ResultSet rs = ps.executeQuery();
            boolean encontrou = false;

            while (rs.next()) {
                System.out.println("----------------------------");
                System.out.println("Matricula : " + rs.getString("matricula"));
                System.out.println("Marca     : " + rs.getString("marca"));
                System.out.println("Modelo    : " + rs.getString("modelo"));
                System.out.println("Estado    : " + (rs.getBoolean("estacionado") ? "Estacionado" : "Nao estacionado"));
                encontrou = true;
            }

            if (!encontrou) System.out.println("Nenhum veiculo encontrado com: " + termo);

        } catch (SQLException e) {
            System.out.println("ERRO na pesquisa: " + e.getMessage());
        }
    }


    // PAGAMENTOS

    public static void guardarPagamento(Pagamento pagamento) {
        String query = """
            INSERT INTO pagamentos (matricula, valor_total, tempo_horas, data_pagamento, pago)
            VALUES (?, ?, ?, ?, ?)
        """;

        try (Connection conn = conectar();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, pagamento.getVeiculoPagou().getMatricula());
            ps.setDouble(2, pagamento.getValorTotal());
            ps.setLong(3, pagamento.getTempoPermanencia() != null
                          ? pagamento.getTempoPermanencia().toHours() : 0);

            if (pagamento.getDataPagamento() != null) {
                ps.setTimestamp(4, Timestamp.valueOf(pagamento.getDataPagamento()));
            } else {
                ps.setNull(4, Types.TIMESTAMP);
            }

            ps.setBoolean(5, pagamento.isPago());
            ps.executeUpdate();
            System.out.println("Pagamento guardado na base de dados.");

        } catch (SQLException e) {
            System.out.println("ERRO ao guardar pagamento: " + e.getMessage());
        }
    }


    // RELATORIO

    public static int totalVagasLivres() {
        String query = "SELECT COUNT(*) FROM vagas WHERE ocupada = 0 AND reservada = 0";
        try (Connection conn = conectar();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            if (rs.next()) return rs.getInt(1);
        } catch (SQLException e) {
            System.out.println("ERRO no relatorio: " + e.getMessage());
        }
        return 0;
    }

    public static double totalArrecadado() {
        String query = "SELECT SUM(valor_total) FROM pagamentos WHERE pago = 1";
        try (Connection conn = conectar();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            if (rs.next()) return rs.getDouble(1);
        } catch (SQLException e) {
            System.out.println("ERRO no relatorio: " + e.getMessage());
        }
        return 0.0;
    }

    public static void listarPagamentos() {
        String query = "SELECT * FROM pagamentos ORDER BY data_pagamento DESC";
        try (Connection conn = conectar();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            System.out.println("======= HISTORICO DE PAGAMENTOS =======");
            boolean encontrou = false;
            while (rs.next()) {
                System.out.println("Matricula      : " + rs.getString("matricula"));
                System.out.println("Valor total    : " + rs.getDouble("valor_total") + " CVE");
                System.out.println("Horas          : " + rs.getLong("tempo_horas") + "h");
                java.time.format.DateTimeFormatter fmt = java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
                java.sql.Timestamp ts = rs.getTimestamp("data_pagamento");
                String dataFmt = ts != null ? ts.toLocalDateTime().format(fmt) : "Sem data";
                System.out.println("Data pagamento : " + dataFmt);
                System.out.println("Estado         : " + (rs.getBoolean("pago") ? "Pago" : "Pendente"));
                System.out.println("---------------------------------------");
                encontrou = true;
            }
            if (!encontrou) System.out.println("Nenhum pagamento registado.");

        } catch (SQLException e) {
            System.out.println("ERRO ao listar pagamentos: " + e.getMessage());
        }
    }
}