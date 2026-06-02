public class ListaPagamento {
    private NoPagamento inicio;
    private NoPagamento fim;
    private int tamanho=0;
    
    public ListaPagamento() {
        this.inicio = null;
        this.fim = null;
    }

    public NoPagamento getInicio() {
        return inicio;
    }

    public NoPagamento getFim() {
        return fim;
    }

    public int getTamanho() {
        return tamanho;
    }

     private boolean isVazia(){
        if (inicio == null || fim == null) {
            return true;
        }
        return false;
    }

    private NoPagamento terNoAtravesPosicao(int posiçao){

        //verificar se a posiçao existe
        if (posiçao <0 || posiçao >= this.tamanho) {
          throw  new IllegalArgumentException("Posiçao nao existe");
        }
       
        NoPagamento atual = inicio;
        int contador =0;
        while (contador < posiçao) {
        
        atual = atual.getProximo();
        
    
        contador++;
    }
 
     return atual;
    }

    public void inserirInicio(Pagamento info){
       
        NoPagamento noo = new  NoPagamento(info);
       
        if (isVazia()) {
            this.inicio = noo;
            this.fim=noo;
        }else{
            noo.setProximo(inicio);;
          
            inicio.setAnterior(noo);
          
            this.inicio = noo;
        }
        this.tamanho++;
    }
    public void inserirFim( Pagamento info){
      
        NoPagamento noo = new NoPagamento(info);
      
        if (isVazia()) {
            this.inicio = noo;
            this.fim=noo;
        }else{
           
            fim.setProximo(noo);
          
            noo.setAnterior(fim);;
           
            this.fim = noo;
           
            noo.setProximo(null);;
        }
        this.tamanho++;
    }

    public void listar(){
        
        if (isVazia()) {
            System.out.println("Lista vazia");
            return;
        }
        NoPagamento atual = inicio;
        while (atual != null) {
        System.out.println(atual.getInfo());
        atual = atual.getProximo();
    }
    }

    public void removerInicio(){
        
        if (isVazia()) {
            System.out.println("Lista fazia");
            return;
        }

        if (inicio == fim) {
            this.inicio = null;
            this.fim = null;
            this.tamanho--;
            return;
        }

        NoPagamento novoInicio = inicio.getProximo();
        
        novoInicio.setAnterior(null);    
        
        inicio =novoInicio;

        this.tamanho--;
    }
    public void removerFim(){
    
        if (isVazia()) {
            System.out.println("Lista fazia");
            return;
        }
  
        if (inicio == fim) {
            this.inicio = null;
            this.fim = null;
            this.tamanho--;
            return;
        }

        NoPagamento novoFim = fim.getAnterior();

        novoFim.setProximo(null);

        fim = novoFim;

        this.tamanho--;
    }

    public void removerMeio(int posicao){
        
        if (posicao <0 || posicao > this.tamanho) {
         throw new IllegalArgumentException("Posiçao nao existe");
        }

        if (posicao == 0) {
            this.removerInicio();
            return;
        }

        if (posicao == tamanho - 1) {
            this.removerFim();
            return;
        }

         NoPagamento noRemover = terNoAtravesPosicao(posicao);
         
         NoPagamento noAntes = noRemover.getAnterior();
         NoPagamento noDepois = noRemover.getProximo();

         noAntes.setProximo(noDepois);

         noDepois.setAnterior(noAntes);

         this.tamanho--;
    }

    public Pagamento procurarPorMatricula(String matricula) {

    if (matricula == null) {
        System.out.println("Matricula invalida");
        return null;
    }

    String matriculaProcurar = matricula.trim();

    if (matriculaProcurar.isEmpty()) {
        System.out.println("Matricula vazia");
        return null;
    }

    if (isVazia()) {
        return null;
    }

    NoPagamento atual = inicio;

    while (atual != null) {

        if (atual.getInfo()
                 .getVeiculoPagou()
                 .getMatricula()
                 .equalsIgnoreCase(matriculaProcurar)) {

            return atual.getInfo();
        }

        atual = atual.getProximo();
    }

    return null;
}
}