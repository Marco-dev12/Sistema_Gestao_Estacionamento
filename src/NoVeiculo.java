public class NoVeiculo {
    private Veiculo info;
    private NoVeiculo proximo;
    private NoVeiculo anterior;
    
    public NoVeiculo(Veiculo info) {
        this.info = info;
        this.proximo = null;
        this.anterior = null;
    }

    public Veiculo getInfo() {
        return info;
    }

    public void setInfo(Veiculo info) {
        this.info = info;
    }

    public NoVeiculo getProximo() {
        return proximo;
    }

    public void setProximo(NoVeiculo proximo) {
        this.proximo = proximo;
    }

    public NoVeiculo getAnterior() {
        return anterior;
    }

    public void setAnterior(NoVeiculo anterior) {
        this.anterior = anterior;
    }

    
}
