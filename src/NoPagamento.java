public class NoPagamento {
    private Pagamento info;
    private NoPagamento proximo;
    private NoPagamento anterior;
    
    public NoPagamento(Pagamento info) {
        this.info = info;
        this.proximo = null;
        this.anterior = null;
    }

    public Pagamento getInfo() {
        return info;
    }

    public void setInfo(Pagamento info) {
        this.info = info;
    }

    public NoPagamento getProximo() {
        return proximo;
    }

    public void setProximo(NoPagamento proximo) {
        this.proximo = proximo;
    }

    public NoPagamento getAnterior() {
        return anterior;
    }

    public void setAnterior(NoPagamento anterior) {
        this.anterior = anterior;
    }
    
}
