import java.time.Duration;
import java.time.LocalDateTime;

public class Pagamento {
    private double valorPorHora;
    private double valorTotal;
    private Veiculo veiculoPagou;
    private Duration tempoPermanencia;
    private LocalDateTime dataPagamento;
    private boolean pago;
    

    public Pagamento( Veiculo veiculoPagou) {
        this.valorPorHora = 100;
        this.valorTotal = 0;
        this.veiculoPagou = veiculoPagou;
        this.tempoPermanencia = null;
        this.dataPagamento = null;
        this.pago = false;
    }

    public double getValorPorHora() {
        return valorPorHora;
    }

    public double getValorTotal() {
        return valorTotal;
    }

    private void setValorTotal(double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public Veiculo getVeiculoPagou() {
        return veiculoPagou;
    }

    public Duration getTempoPermanencia() {
        return tempoPermanencia;
    }

    private void setTempoPermanencia(Duration tempoPermanencia) {
        this.tempoPermanencia = tempoPermanencia;
    }

    public boolean isPago() {
        return pago;
    }

    private void setPago(boolean pago) {
        this.pago = pago;
    }

    public LocalDateTime getDataPagamento() {
        return dataPagamento;
    }

    private void setDataPagamento(LocalDateTime dataPagamento) {
        this.dataPagamento = dataPagamento;
    } 

    public void calcularTempoPermanencia(){
        if (veiculoPagou == null) {
            System.out.println("Veiculo nao existe");
            return;
        }

        if (veiculoPagou.getHoraEntrada() == null) {
            System.out.println("Veiculo nao tem hora de entrada");
            return;
        }

        if (veiculoPagou.getHoraSaida() == null) {
            System.out.println("Hora de saida nao existe");
            return;
        }

        if (veiculoPagou.getHoraSaida().isBefore(veiculoPagou.getHoraEntrada())) {
            System.out.println("Hora de saida invalida");
            return;
        }

        Duration diferenca = Duration.between(veiculoPagou.getHoraEntrada(), veiculoPagou.getHoraSaida());
        this.setTempoPermanencia(diferenca);
    } 

    public void calcularValorTotal(){
        if (tempoPermanencia == null) {
            System.out.println("Nao é possivel calcular");
            return;
        }

        long horas= tempoPermanencia.toHours();
        long minutosAbsoluto= tempoPermanencia.toMinutes();

        long minutos = minutosAbsoluto % 60;
      
        if (minutos > 15) {
            horas = horas + 1;
        }

        if (horas == 0) {
            horas = horas + 1;
        }

        this.setValorTotal(horas*valorPorHora);
    }

    public void confirmarPagamento(){
        if (pago) {
            System.out.println("Ja esta pago ");
            return;
        }

        if (tempoPermanencia == null) {
            System.out.println("Nao tem tempo permanencia");
            return;
        }

        if (valorTotal <= 0) {
            System.out.println("Valor ainda nao calculado");
            return;
        }
        this.setPago(true);
        this.setDataPagamento(LocalDateTime.now()); 
        System.out.println("Foi pago em : " +dataPagamento);
    }

    public void exibirRecibo(){
        if (veiculoPagou == null) {
            System.out.println("Veiculo nao existe");
            return;
        } 

        if (tempoPermanencia == null) {
            System.out.println("Nao tem tempo permanencia");
            return;
        }

        String estado;
        if (pago) {
            estado = "Pago";
        }else {
            estado = "Pendente";
        }

        long horas= tempoPermanencia.toHours();
        long minutosAbsoluto= tempoPermanencia.toMinutes();
        long segundosAbsoluto=tempoPermanencia.getSeconds();

        long minutos = minutosAbsoluto % 60;
        long segundos = segundosAbsoluto % 60; 

        System.out.println("---------Recibo---------");
        System.out.println("Matricula : " +veiculoPagou.getMatricula());
        System.out.println("Entrada : " +veiculoPagou.getHoraEntrada());
        System.out.println("Saida : " +veiculoPagou.getHoraSaida());
        System.out.println("Permanencia : " + horas+"h"  + minutos+"m"+  segundos+"s");
        System.out.println("Valor : " +this.getValorTotal());
        System.out.println("Estado : " +estado);
        if (dataPagamento != null) {
          System.out.println("Data de pagamento : " +dataPagamento);   
        }else{
             System.out.println("Data de pagamento : " +"Sem data de pagamento");   
        } 
        System.out.println("------------------------");
    }    

    @Override
    public String toString() {
     String matricula = "Sem veiculo";

     if (veiculoPagou != null) {
    matricula = veiculoPagou.getMatricula();
     }

      return "Pagamento [matricula=" + matricula
        + ", valorTotal=" + valorTotal
        + ", pago=" + pago + "]";
    }
}
