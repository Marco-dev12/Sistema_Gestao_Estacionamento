public class Vaga {
    //Uma posição física onde um veículo pode estacionar.
    private int numeroVaga;//Identificar a vaga.(1,2,3)
    private boolean ocupada;//Controlar disponibilidade.
    private Veiculo veiculoAssociado;//VAGA1 --- VEICULO 3
    private boolean reservada;//indicar se a vaga esta reservada ou nao 
    
    public Vaga(int numeroVaga) {
        this.numeroVaga = numeroVaga;
        this.ocupada = false;
        this.veiculoAssociado = null;
        this.reservada = false;
    }

    public int getNumeroVaga() {
        return numeroVaga;
    }

    public void setNumeroVaga(int numeroVaga) {
        this.numeroVaga = numeroVaga;
    }

    public boolean isOcupada() {
        return ocupada;
    }

    public void setOcupada(boolean ocupada) {
        this.ocupada = ocupada;
    }

    public Veiculo getVeiculoAssociado() {
        return veiculoAssociado;
    }

    public void setVeiculoAssociado(Veiculo veiculoAssociado) {
        this.veiculoAssociado = veiculoAssociado;
    }

    public boolean isReservada() {
        return reservada;
    }

    public void setReservada(boolean reservada) {
        this.reservada = reservada;
    }

    @Override
    public String toString() {
        return "Vaga [numeroVaga=" + numeroVaga + ", ocupada=" + ocupada + ", veiculoAssociado=" + veiculoAssociado
                + ", reservada=" + reservada + "]";
    }
    
    public void ocuparVaga(Veiculo veiculo){//saber que veiculo esta na vaga 
        //validar se a vaga ja esta ocupada
        if (ocupada || reservada) {
            System.out.println("Vaga ja  ocupada nao é possivel ocupar de novo ");
            return;
        }

        //atualizar estado e associar o veiculo a vaga 
        this.setOcupada(true);
        this.setVeiculoAssociado(veiculo);
    }
    public void liberarVaga(){
        //verificar se esta livre 
        if (verificarDisponibilidade()) {
            System.out.println("Vaga livre nao ha necessidade de liberar");
            return;
        }

        //so atualizar o estado 
        this.setOcupada(false);
        this.setVeiculoAssociado(null);
    }
    public boolean verificarDisponibilidade(){
        if (!ocupada && !reservada) {
            System.out.println("Vaga esta livre");
            return true;
        }
        return false;
    }
    public void reservarVaga(){
        if (!verificarDisponibilidade()) {
            System.out.println("Vaga ja reservada ou ocupada");
            return;
        }

        this.setReservada(true);
    }
    public void cancelarReserva(){
        if (!reservada) {
            System.out.println("Nao tens vaga reservada ");
            return;
        }

        this.setReservada(false);
    }
}
