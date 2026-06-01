public class Relatorio {
    private Estacionamento estacionamento;

    public Relatorio(Estacionamento estacionamento) {
        this.estacionamento = estacionamento;
    }

    public Estacionamento getEstacionamento() {
        return estacionamento;
    }

    public void setEstacionamento(Estacionamento estacionamento) {
        this.estacionamento = estacionamento;
    }

    public void relatorioVagas(Estacionamento estacionamento){
        if (estacionamento == null) {
            System.out.println("Estacionamento invalido");
            return;
        }

        System.out.println("===== RELATORIO DE VAGAS =====");
        estacionamento.listarVagas();
    }

    public void relatorioVeiculos(Estacionamento estacionamento){
        if (estacionamento == null) {
            System.out.println("Estacionamento invalido");
            return;
        }

        System.out.println("===== RELATORIO DE Veiculos =====");
        estacionamento.listarVeiculosEstacionados();
    }

    public void relatorioPagamentos(Estacionamento estacionamento){
        if (estacionamento == null) {
            System.out.println("Estacionamento invalido");
            return;
        }

        System.out.println("===== RELATORIO DE Pagamentos =====");
        estacionamento.getPagamentos().listar();
    }

    public void relatorioGeral(Estacionamento estacionamento) {
        if (estacionamento == null) {
            System.out.println("Estacionamento invalido");
            return;
        }

        System.out.println("===== RELATORIO GERAL =====");

        System.out.println("\n--- VAGAS ---");
        estacionamento.listarVagas();

        System.out.println("\n--- VEICULOS ESTACIONADOS ---");
        estacionamento.listarVeiculosEstacionados();

        System.out.println("\n--- PAGAMENTOS ---");
        estacionamento.getPagamentos().listar();
    }
}
