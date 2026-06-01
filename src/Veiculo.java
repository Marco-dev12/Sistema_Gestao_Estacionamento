import java.time.Duration;
import java.time.LocalDateTime;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Veiculo {
    //Um carro/moto que entra no estacionamento.
    private String matricula;//identificador principal do veículo
    private LocalDateTime horaEntrada;//necessaria para controlar:tempo de permanencia e calculo de pagamento
    private LocalDateTime horaSaida;//usado LocalDateTime pois ela ja sabe :horas,minutos,segundos,difernça entre tempos
    private Vaga vagaAssociada;//mostra a relaçao veiculo ---vaga
    private boolean estacionado;//permite :saber estado de veiculo,evita duplicaçao e validr entrada/saida
    
    //construor so recebe  matricula pois o veículo é criado inicialmente sem estar estacionado.
    public Veiculo(String matricula) {
        this.setMatricula(matricula);
        this.horaEntrada = null;
        this.horaSaida = null;
        this.vagaAssociada = null;
        this.estacionado = false;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        if (validarMatricula(matricula)) {
          this.matricula = matricula;   
        }
    }

    public LocalDateTime getHoraEntrada() {
        return horaEntrada;
    }

    public void setHoraEntrada() {
         //vou tera hora de forma automatica ou seja quando for regisrada a  entrada nao sera preciso passar a hora sera dada automaticamnete com a hora do meu pc
         //como a nossa variavel e  do tipo LocalDateTime entao tem um metodo que permite isso
        this.horaEntrada = LocalDateTime.now();
    }

    public LocalDateTime getHoraSaida() {
        return horaSaida;
    }

    public void setHoraSaida() {
        this.horaSaida = LocalDateTime.now();
    }

    public Vaga getVagaAssociada() {
        return vagaAssociada;
    }

    public void setVagaAssociada(Vaga vagaAssociada) {
        this.vagaAssociada = vagaAssociada;
    }

    public boolean isEstacionado() {
        return estacionado;
    }

    public void setEstacionado(boolean estacionado) {
        this.estacionado = estacionado;
    }
    
    //criei um metodo so para ficar com o regex pois as matriculas tudo tem de ter mesma estrutura ST-2NUMEROS-2LETRAS
    private boolean estruturaMatricula(String matricula){
        if (matricula.matches("ST-\\d{2}-[A-Z]{2}")) {
            return true;
        }
        return false;
    } 
    private boolean validarMatricula(String matricula){
        //garantir que :matrícula existe,formato está correto,evitar dados inválidos no sistema

        if (matricula == null) {
             System.out.println("Matricula nao pode ser nula");
             return false;
        }

        //tirar os espaços eantes e depois
        String matriculaSemEspaco = matricula.trim();
        //verificar se esta vazia
        if (matriculaSemEspaco.isEmpty()) {
            System.out.println("Matricula nao pode estar vazia");
            return false;
        }

        //nao pode conter espaços
        if (matriculaSemEspaco.contains(" ")) {
            System.out.println("Matricula nao pode conter espaços");
            return false;
        }
        //verificar formato da matricula(ST-2NUMEROS-2LETRAS)
        if (!(estruturaMatricula(matriculaSemEspaco))) {
            System.out.println("Formato de matricula esta incorreto");
            return false;
        }
        return true;
    }
    public void registrarEntrada(Vaga vaga){
       //metodo foca apenas no estadodo veiculo ou seja apenas relacionada ao veiculo por isso a vaga nao foi mexida

        //verificar se veiculo ja esta estacionado
        if (estacionado) {
            System.out.println("Veiculo ja esta estacionado");
            return;
        }

        //verificar se a vaga é valida(se existe e se esta livre)
        if (vaga == null) {
            System.out.println("Vaga invalida");
            return;
        }
        if (vaga.isOcupada() || vaga.isReservada()) {
            System.out.println("Vaga nao esta livre ");
            return;
        }

        //atualizar o estado do veiculo ou sejaregistrar a entrada
        //como o setter ja recebe a hora de forma automatica nao é precisso passa-la
        this.setHoraEntrada();
        this.setVagaAssociada(vaga);
        this.setEstacionado(true);
        this.horaSaida = null;//caso voltar a estacionar novamente 
        System.out.println("Entrada registrada com sucesso");
    }
    public void registrarSaida(){
        //verificar se realmente o veiculo esta estacionado
        if (!estacionado) {
            System.out.println("Veiculo nao esta estacionado");
            return;
        }

        //mudar o estado
        this.setHoraSaida();
        this.setEstacionado(false);
        
        this.setVagaAssociada(null);
        System.out.println("Saida registrada com sucesso");
    }
    
    @Override
    public String toString() {
       String estado;
        if (estacionado) {
            estado ="Estacionado";
        }else{
            estado = "Nao esta estacionado";
        }
        //caso tenha um veiculo associado em vez de mostrar toda informaçao do veiculo mostra so a matricula
        if (vagaAssociada != null) {
           return "Veiculo [matricula=" + matricula
                   + ", estado=" + estado
                   + ", vaga=" + vagaAssociada.getNumeroVaga()
                   + ", entrada=" + horaEntrada
                   + ", saida=" + horaSaida + "]";
        }else{
             return "Veiculo [matricula=" + matricula
                   + ", estado=" + estado
                   + ", entrada=" + horaEntrada
                   + ", saida=" + horaSaida + "]";
        }
    } 
}
//{} 