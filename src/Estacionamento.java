import java.util.ArrayList;
import java.util.List;

public class Estacionamento {
    //o “cérebro” do sistema ou seja coordena tudo
    //vai representar os estcionados atuais

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
        //vou criar uma variavel do tipo vaga para ficar com o valor da vaga livre pois caso existir vou ter de liga-la com o veiculo
        Vaga vagaLivre = this.procurarVagaLivre();
        if (vagaLivre == null) {
            System.out.println("Nao a vagas disponiveis ");
            return;
        }

       

        //verificar se o veiculo ja nao esta na lista percorendo e comparando matricula
        for (Veiculo veiculo : veiculos) {
            if (veiculo.getMatricula().equals(veiculoEntrar.getMatricula())) {
            System.out.println("Veiculo ja adicionado a lista");
            return;
        }     
        }
       
        //vou ligar o veiculo a vaga e a vaga ao veiculo e adicionar veiculo a lista 
        veiculoEntrar.registrarEntrada(vagaLivre);
        vagaLivre.ocuparVaga(veiculoEntrar);
        veiculos.add(veiculoEntrar);
    }

    public void registraSaida(Veiculo veiculoSair){
        //verificar se veiculo existe 
        if (veiculoSair == null) {
            System.out.println("Veiculo nao existe");
            return;
        }

        //verificar se veiculo esta estacionado
        if (!veiculoSair.isEstacionado()) {
            System.out.println("Veiculo nao esta estacionado");
            return;
        }

        //verificar se veiculo possui vaga associada
        Vaga vagaAssociada = veiculoSair.getVagaAssociada();

    
        if (vagaAssociada == null) {
            System.out.println("Nao ha vaga existente");
            return;
        }

        //liberar vaga,registrar saida e remover da lista
        vagaAssociada.liberarVaga();
        veiculoSair.registrarSaida();
        veiculos.remove(veiculoSair);
    }  
    
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
    
    public void listarVagas(){
        //Mostrar todas as vagas cadastradas no estacionamento.
        //veririfcar se existem vagas cadastradas oou seja se a lista esta vazia ou nao 
        if (vagas.isEmpty()) {
            System.out.println("Lista esta vazia");
            return;
        }

        for (Vaga vaga : vagas) {
            System.out.println(vaga);
        }
    }

    public void listarVeiculosEstacionados(){
        if (veiculos.isEmpty()) {
            System.out.println("Lista esta vazia");
            return;
        }

        boolean encontrou = false;
        for (Veiculo veiculo : veiculos) {
            if (veiculo.isEstacionado()) {
                System.out.println(veiculo);
                encontrou = true;
            }
        }

        if (!encontrou) {
            System.out.println("Nenhum veiculo estacionado");
            return;
        }
    }   
    public void atualizarVagaVeiculo(Veiculo veiculo , Vaga novaVaga){
        //mover um veiculo de uma vaga paraa outra 

        if (veiculo == null) {
            System.out.println("Veiculo nao existe");
            return;
        }

        if (!veiculo.isEstacionado()) {
            System.out.println("Veiculo nao esta estaconado ");
            return;
        }

        if (novaVaga == null) {
             System.out.println("Vaga nao existe");
            return;
        }

        if (!novaVaga.verificarDisponibilidade()) {
            System.out.println("Vaga nao esta disponivel");
            return;
        }

        if (veiculo.getVagaAssociada().getNumeroVaga() == novaVaga.getNumeroVaga()) {
            System.out.println("Vagas sao iguais ");
            return;
        }

        Vaga vagaAntiga = veiculo.getVagaAssociada();
        vagaAntiga.liberarVaga();
        novaVaga.ocuparVaga(veiculo);
        veiculo.setVagaAssociada(novaVaga);
    }
    
    public Veiculo procurarVeiculoPorMatricula(String matricula){

        if (matricula == null) {
            System.out.println("Matricula nao existe");
            return null;
        }
        String matriculaProcurar = matricula.trim();

        if (matriculaProcurar.isEmpty()) {
            System.out.println("Matricula esta vazia");
            return null;
        }

        for (Veiculo veiculo : veiculos) {
            if ((veiculo.getMatricula().equalsIgnoreCase(matriculaProcurar))) {
                System.out.println("Veiculo encontrado");
                return veiculo;
            }
        }
        System.out.println("Veiculo nao encontrado");
        return null;
    }  
}
//{} 