public class ListaVaga {
    private NoVaga inicio;
    private NoVaga fim;
    private int tamanho=0;
    
    public ListaVaga() {
        this.inicio = null;
        this.fim = null;
    }

    public NoVaga getInicio() {
        return inicio;
    }

    public NoVaga getFim() {
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

    private NoVaga terNoAtravesPosicao(int posiçao){

        if (posiçao <0 || posiçao >= this.tamanho) {
          throw  new IllegalArgumentException("Posiçao nao existe");
        }
       
        NoVaga atual = inicio;
        int contador =0;
        while (contador < posiçao) {
        
        atual = atual.getProximo();
        
    
        contador++;
    }
 
     return atual;
    }

    public void inserirInicio(Vaga info){
       
        NoVaga noo = new  NoVaga(info);
       
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
    public void inserirFim( Vaga info){
      
        NoVaga noo = new  NoVaga(info);
      
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
        NoVaga atual = inicio;
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

        NoVaga novoInicio = inicio.getProximo();
        
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

        NoVaga novoFim = fim.getAnterior();

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

         NoVaga noRemover = terNoAtravesPosicao(posicao);
         
         NoVaga noAntes = noRemover.getAnterior();
         NoVaga noDepois = noRemover.getProximo();

         noAntes.setProximo(noDepois);

         noDepois.setAnterior(noAntes);

         this.tamanho--;
    }

    public Vaga procurarPorNumero(int numeroVaga) {

    if (isVazia()) {
        System.out.println("Lista vazia");
        return null;
    }

    NoVaga atual = inicio;

    while (atual != null) {

        if (atual.getInfo().getNumeroVaga() == numeroVaga) {
            System.out.println("Vaga encontrada");
            return atual.getInfo();
        }

        atual = atual.getProximo();
    }

    System.out.println("Vaga nao encontrada");
    return null;
}
}
