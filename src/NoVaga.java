public class NoVaga {
    private Vaga info;
    private NoVaga proximo;
    private NoVaga anterior;
    
    public NoVaga(Vaga info) {
        this.info = info;
        this.proximo = null;
        this.anterior = null;
    }

    public Vaga getInfo() {
        return info;
    }

    public void setInfo(Vaga info) {
        this.info = info;
    }

    public NoVaga getProximo() {
        return proximo;
    }

    public void setProximo(NoVaga proximo) {
        this.proximo = proximo;
    }

    public NoVaga getAnterior() {
        return anterior;
    }

    public void setAnterior(NoVaga anterior) {
        this.anterior = anterior;
    }

    
}
