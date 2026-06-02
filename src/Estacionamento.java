import java.util.ArrayList;
import java.util.List;

public class Estacionamento {
    //o “cérebro” do sistema ou seja coordena tudo
    //vai representar os estcionados atuais

    private ListaVeiculos veiculos;
    private ListaVaga vagas;
    private ListaPagamento pagamentos;
    
    public Estacionamento() {
        this.veiculos = new ListaVeiculos();
        this.vagas = new ListaVaga();
        this.pagamentos = new ListaPagamento();
    }

   

    public ListaVeiculos getVeiculos() {
        return veiculos;
    }



    public void setVeiculos(ListaVeiculos veiculos) {
        this.veiculos = veiculos;
    }



    public ListaVaga getVagas() {
        return vagas;
    }



    public void setVagas(ListaVaga vagas) {
        this.vagas = vagas;
    }



    public ListaPagamento getPagamentos() {
        return pagamentos;
    }



    public void setPagamentos(ListaPagamento pagamentos) {
        this.pagamentos = pagamentos;
    }



    public void adicionarVaga(Vaga vagaAdicionar ){
        //Adicionar uma vaga à lista do estacionamento.
        //verificar se a vaga existe 
        if (vagaAdicionar == null) {
            System.out.println("vaga invalida");
            return;
        }

        if (vagas.procurarPorNumero(vagaAdicionar.getNumeroVaga()) != null) {
            System.out.println("Vaga com esse numero ja existente");
            return;
        }

        vagas.inserirFim(vagaAdicionar);
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
       if (veiculos.procurarPorMatricula(veiculoEntrar.getMatricula()) != null) {
           System.out.println("Veiculo ja adicionado a lista");
           return;
       }
       
        //vou ligar o veiculo a vaga e a vaga ao veiculo e adicionar veiculo a lista 
        veiculoEntrar.registrarEntrada(vagaLivre);
        vagaLivre.ocuparVaga(veiculoEntrar);
        veiculos.inserirFim(veiculoEntrar);
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
        veiculos.removerPorMatricula(veiculoSair.getMatricula());
    }  
    
    public Vaga procurarVagaLivre(){
       NoVaga atual = vagas.getInicio();

       while (atual != null) {
         if (atual.getInfo().verificarDisponibilidade()) {
            return atual.getInfo();
         }
         atual = atual.getProximo();
       }

       return null;
    }
    
    public void listarVagas(){
        //Mostrar todas as vagas cadastradas no estacionamento.
        vagas.listar();
    }

    public void listarVeiculosEstacionados(){
      veiculos.listarVeiculosEstacionados();
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
        return veiculos.procurarPorMatricula(matricula);
    }  

    public void registrarPagamento(Pagamento pagamentoAdicionar){
        if (pagamentoAdicionar == null) {
            System.out.println("Pagamento nao existe");
            return;
        }

        if (pagamentos.procurarPorMatricula(pagamentoAdicionar.getVeiculoPagou().getMatricula() ) != null) {
            System.out.println("Pagamento ja existe");
            return;
        }

        if (!pagamentoAdicionar.isPago()) {
            System.out.println("Pagamento nao foi confirmado");
            return;
        }

        pagamentos.inserirFim(pagamentoAdicionar);
        System.out.println("Pagamento adicionado a lista com sucesso");
    } 
}
//{} 