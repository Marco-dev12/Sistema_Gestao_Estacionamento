public class ListaVeiculos {
    private NoVeiculo inicio;
    private NoVeiculo fim;
    private int tamanho = 0;
    
    public ListaVeiculos() {
        this.inicio = null;
        this.fim = null;
    }

    
    public NoVeiculo getInicio() {
        return inicio;
    }

    public NoVeiculo getFim() {
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

    private NoVeiculo terNoAtravesPosicao(int posiçao){

        if (posiçao <0 || posiçao >= this.tamanho) {
          throw  new IllegalArgumentException("Posiçao nao existe");
        }
       
        NoVeiculo atual = inicio;
        int contador =0;
        while (contador < posiçao) {
        
        atual = atual.getProximo();
        
        contador++;
    }

     return atual;
    }

    public void inserirInicio(Veiculo info){
       
        NoVeiculo noo = new NoVeiculo(info);
       
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
    public void inserirFim( Veiculo info){
  
        NoVeiculo noo = new NoVeiculo(info);
       
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
    public void inserirMeio(int posiçao, Veiculo info){
       
        if (posiçao <0 || posiçao > this.tamanho) {
         throw new IllegalArgumentException("Posiçao nao existe");
        }

        if (posiçao==0) {
            this.inserirInicio(info);
            return;
        }

        if (posiçao==tamanho) {
            this.inserirFim(info);
            return;
        }
          
            NoVeiculo novoNo = new NoVeiculo(info);
           
            NoVeiculo noAnterior =this.terNoAtravesPosicao(posiçao - 1);
        
            NoVeiculo proximoNo = noAnterior.getProximo();
           
            novoNo.setAnterior(noAnterior);
            novoNo.setProximo(proximoNo);
           
            noAnterior.setProximo(novoNo);
      
            proximoNo.setAnterior(novoNo);   
        
        this.tamanho++;
    }
    public void listar(){
 
        if (isVazia()) {
            System.out.println("Lista vazia");
            return;
        }
        NoVeiculo atual = inicio;
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

        NoVeiculo novoInicio = inicio.getProximo();
        
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

        NoVeiculo novoFim = fim.getAnterior();

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

         NoVeiculo noRemover = terNoAtravesPosicao(posicao);
         
         NoVeiculo noAntes = noRemover.getAnterior();
         NoVeiculo noDepois = noRemover.getProximo();

         noAntes.setProximo(noDepois);

         noDepois.setAnterior(noAntes);

         this.tamanho--;
    }

    public Veiculo procurarPorMatricula(String matricula) {

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

    NoVeiculo atual = inicio;

    while (atual != null) {

        if (atual.getInfo().getMatricula().equalsIgnoreCase(matriculaProcurar)) {
            return atual.getInfo();
        }

        atual = atual.getProximo();
    }

    return null;
}

public boolean existe(String matricula) {

    if (procurarPorMatricula(matricula) != null) {
        return true;
    }
    return false;
}

public void removerPorMatricula(String matricula) {

    if (matricula == null) {
        System.out.println("Matricula invalida");
        return;
    }

    String matriculaRemover = matricula.trim();

    if (matriculaRemover.isEmpty()) {
        System.out.println("Matricula vazia");
        return;
    }

    if (isVazia()) {
        System.out.println("Lista vazia");
        return;
    }

    NoVeiculo atual = inicio;
    int posicao = 0;

    while (atual != null) {

        if (atual.getInfo().getMatricula().equalsIgnoreCase(matriculaRemover)) {

            if (posicao == 0) {
                removerInicio();
                return;
            }

            if (posicao == tamanho - 1) {
                removerFim();
                return;
            }

            removerMeio(posicao);
            return;
        }

        atual = atual.getProximo();
        posicao++;
    }

    System.out.println("Veiculo nao encontrado");
}

   public void listarVeiculosEstacionados(){
        
        if (isVazia()) {
            System.out.println("Lista vazia");
            return;
        }
        NoVeiculo atual = inicio;
        boolean encontrou = false;
        while (atual != null) {
        if (atual.getInfo().isEstacionado()) {
            System.out.println(atual.getInfo());
             encontrou = true;
        }
        atual = atual.getProximo();
    }
    if (!encontrou) {
            System.out.println("Nenhum veiculo estacionado");
            return;
        }
    }
}