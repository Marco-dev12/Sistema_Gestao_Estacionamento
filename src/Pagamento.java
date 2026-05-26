import java.time.Duration;

public class Pagamento {
    private double valorPorHora;
    private double valorTotal;
    private Veiculo veiculoPagou;
    private Duration tempoPermanencia;
    private boolean pago;
    

    public Pagamento(double valorPorHora, Veiculo veiculoPagou) {
        this.valorPorHora = valorPorHora;
        this.valorTotal = valorTotal;
        this.veiculoPagou = veiculoPagou;
        this.tempoPermanencia = tempoPermanencia;
        this.pago = false;
    }

    public double getValorPorHora() {
        return valorPorHora;
    }

    public void setValorPorHora(double valorPorHora) {
        this.valorPorHora = valorPorHora;
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public Veiculo getVeiculoPagou() {
        return veiculoPagou;
    }

    public void setVeiculoPagou(Veiculo veiculoPagou) {
        this.veiculoPagou = veiculoPagou;
    }

    public Duration getTempoPermanencia() {
        return tempoPermanencia;
    }

    public void setTempoPermanencia(Duration tempoPermanencia) {
        this.tempoPermanencia = tempoPermanencia;
    }

    public boolean isPago() {
        return pago;
    }

    public void setPago(boolean pago) {
        this.pago = pago;
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

        Duration diferenca = Duration.between(veiculoPagou.getHoraEntrada(), veiculoPagou.getHoraSaida());
        this.setTempoPermanencia(diferenca);
    } 

    public void calcularValorTotal(){
        if (tempoPermanencia == null) {
            System.out.println("Nao é possivel calcular");
            return;
        }

        if (valorPorHora <= 0) {
            System.out.println("Valor por hora invalido");
            return;
        }

        long horas= tempoPermanencia.toHours();
        long minutosAbsoluto= tempoPermanencia.toMinutes();
        long segundosAbsoluto=tempoPermanencia.getSeconds();

        long minutos = minutosAbsoluto % 60;
        long segundos = segundosAbsoluto % 60; 

        if (minutos > 15) {
            horas = horas + 1;
        }

        this.setValorTotal(horas*valorPorHora);
    }
}
