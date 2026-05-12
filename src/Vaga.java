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
    
    public void ocuparVaga(){
        //validar se a vaga ja esta ocupada
    }
    public void liberarVaga(){}
    public void verificarDisponibilidade(){}
    public void reservarVaga(){}
    public void cancelarReserva(){}
}
