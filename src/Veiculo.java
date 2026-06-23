import java.time.LocalDateTime;

public class Veiculo {
    private String matricula;
    private String marca;
    private String modelo;
    private LocalDateTime horaEntrada;
    private LocalDateTime horaSaida;
    private Vaga vagaAssociada;
    private boolean estacionado;

    public Veiculo(String matricula) {
        this.setMatricula(matricula);
        this.marca        = "Desconhecida";
        this.modelo       = "Desconhecido";
        this.horaEntrada  = null;
        this.horaSaida    = null;
        this.vagaAssociada = null;
        this.estacionado  = false;
    }

    public Veiculo(String matricula, String marca, String modelo) {
    this.setMatricula(matricula);

    if (marca == null || marca.trim().isEmpty()) {
        this.marca = "Desconhecida";
    } else {
        this.marca = marca.trim();
    }

    if (modelo == null || modelo.trim().isEmpty()) {
        this.modelo = "Desconhecido";
    } else {
        this.modelo = modelo.trim();
    }

    this.horaEntrada = null;
    this.horaSaida = null;
    this.vagaAssociada = null;
    this.estacionado = false;
}

    // --- getters e setters ---

    public String getMatricula() { return matricula; }

    public void setMatricula(String matricula) {
        if (validarMatricula(matricula)) {
            this.matricula = matricula;
        } else {
            throw new IllegalArgumentException("Matricula invalida: " + matricula);
        }
    }

    public String getMarca() { return marca; }
    public void setMarca(String marca) { this.marca = marca; }

    public String getModelo() { return modelo; }
    public void setModelo(String modelo) { this.modelo = modelo; }

    public LocalDateTime getHoraEntrada() { return horaEntrada; }

    // usado pelo sistema ao registar entrada (hora automatica)
    public void setHoraEntrada() {
        this.horaEntrada = LocalDateTime.now();
    }

    // usado pela base de dados ao carregar veiculos guardados
    public void setHoraEntradaManual(LocalDateTime horaEntrada) {
        this.horaEntrada = horaEntrada;
    }

    public LocalDateTime getHoraSaida() { return horaSaida; }

    public void setHoraSaida() {
        this.horaSaida = LocalDateTime.now();
    }

    public Vaga getVagaAssociada() { return vagaAssociada; }
    public void setVagaAssociada(Vaga vagaAssociada) { this.vagaAssociada = vagaAssociada; }

    public boolean isEstacionado() { return estacionado; }
    public void setEstacionado(boolean estacionado) { this.estacionado = estacionado; }

    // --- validacao da matricula ---

    private boolean estruturaMatricula(String matricula) {
        return matricula.matches("(ST|SA|SV|SN|SL|BV|MO|FG|FA|CD|PR|PAN|G)-\\d{2}-[A-Z]{2}");
    }

    private boolean validarMatricula(String matricula) {
        if (matricula == null) {
            return false;
        }
        String m = matricula.trim();
        if (m.isEmpty()) {
            return false;
        }
        if (m.contains(" ")) {
            return false;
        }
        if (!estruturaMatricula(m)) {
            return false;
        }
        return true;
    }

    // --- logica de negocio ---

    public void registrarEntrada(Vaga vaga) {
        if (estacionado) {
            System.out.println("Veiculo ja esta estacionado");
            return;
        }
        if (vaga == null) {
            System.out.println("Vaga invalida");
            return;
        }
        if (vaga.isOcupada() || vaga.isReservada()) {
            System.out.println("Vaga nao esta livre");
            return;
        }
        this.setHoraEntrada();
        this.setVagaAssociada(vaga);
        this.setEstacionado(true);
        this.horaSaida = null;
        System.out.println("Entrada registada com sucesso.");
    }

    public void registrarSaida() {
        if (!estacionado) {
            System.out.println("Veiculo nao esta estacionado");
            return;
        }
        this.setHoraSaida();
        this.setEstacionado(false);
        this.setVagaAssociada(null);
        System.out.println("Saida registada com sucesso.");
    }

    @Override
    public String toString() {
        String estado = estacionado ? "Estacionado" : "Nao estacionado";
        String vaga   = vagaAssociada != null ? String.valueOf(vagaAssociada.getNumeroVaga()) : "Nenhuma";
        java.time.format.DateTimeFormatter fmt = java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        String entrada = horaEntrada != null ? horaEntrada.format(fmt) : "null";
        String saida   = horaSaida  != null ? horaSaida.format(fmt)   : "null";
        return "Veiculo [matricula=" + matricula
             + ", marca=" + marca
             + ", modelo=" + modelo
             + ", estado=" + estado
             + ", vaga=" + vaga
             + ", entrada=" + entrada
             + ", saida=" + saida + "]";
    }
}