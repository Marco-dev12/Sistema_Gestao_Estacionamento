import java.util.ArrayList;
import java.util.List;

public class Estacionamento {
    //o “cérebro” do sistema ou seja coordena tudo

    private ArrayList<Veiculo> veiculos;
    private ArrayList<Vaga> vagas;
    
    public Estacionamento() {
        this.veiculos = new ArrayList<>();
        this.vagas = new ArrayList<>();
    }

    public ArrayList<Veiculo> getVeiculos() {
        return veiculos;
    }

    public void setVeiculos(ArrayList<Veiculo> veiculos) {
        this.veiculos = veiculos;
    }

    public ArrayList<Vaga> getVagas() {
        return vagas;
    }

    public void setVagas(ArrayList<Vaga> vagas) {
        this.vagas = vagas;
    }

    public void adicionarVaga(Vaga vagaAdicionar ){
        //Adicionar uma vaga à lista do estacionamento.
        //verificar se a vaga existe 
        if (vagaAdicionar == null) {
            System.out.println("vaga invalida");
            return;
        }

        //verificar se existe um vaga com o mesmo numero 
        for (Vaga vaga : vagas) {
            if (vaga.getNumeroVaga() == vagaAdicionar.getNumeroVaga()) {
                System.out.println("Vaga com esse numero ja existente");
                return;
            }
        }

        //adicionar vaga a lista
        vagas.add(vagaAdicionar);
        System.out.println("Vaga adicionada ao estacionamento com sucesso");
    } 
    public void registraEntrada(Veiculo veiculoEntrar){
        //verificar se veiculo existe
        if (veiculoEntrar == null) {
            System.out.println("Veiculo invalido");
            return;
        }

        //verificar se o veiculo ja esta estacionado
        if (veiculoEntrar.isEstacionado()) {
            System.out.println("Veiculo ja esta estacionado");
            return;
        }

        //verificar se existe vaga livre
        //vou criar uma variavel do tipo vaga para ficar com o valor da vaga livre 
        Vaga vagaLivre = this.procurarVagaLivre();
        if (vagaLivre == null) {
            System.out.println("Nao a vagas disponiveis ");
            return;
        }

        //vou ligar o veiculo a vaga e a vaga ao veiculo 
        veiculoEntrar.registrarEntrada(vagaLivre);
        vagaLivre.ocuparVaga(veiculoEntrar);
        veiculos.add(veiculoEntrar);
    }
    public void registraSaida(){}  
    public Vaga procurarVagaLivre(){
        //percorer a lista de vaga
        for (Vaga vaga : vagas) {
            //verificar se vaga esta disponivel
            if (vaga.verificarDisponibilidade()) {
                return vaga;
            }
        }
        return null;
    }
    public void listarVagas(){}
    public void listarVeiculosEstacionados(){}   
    public void atualizarVagaVeiculo(){}
    public void procurarVeiculoPorMatricula(){}  
}
//{} 