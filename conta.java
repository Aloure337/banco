package exsirley;

public class conta {
    public int nConta;
    public String titular;
    public double saldo;
    public String sobrenome;
    public boolean sacar(double valor){
        if(this.saldo>=valor){
            this.saldo-=valor;
            return true;
        }else{
            return false;
        }        
    }
    public boolean depositar(double valor){
        if(valor>0){
            this.saldo+=valor;
            return true;
    }else{
            return false;
        }
    }
    public String mDados(){
        String texto;
        texto="\nNome: "+ this.titular+"\nNumero da conta: "+ this.nConta+"\nSaldo: R$"+this.saldo;
        return texto;
    }
    public boolean transferir(conta destino, double valor) {
        if (this.sacar(valor)) {
            destino.depositar(valor);
            
            return true;
        } else {
            return false; 		        } 		    
    }
    public int obternConta() {
		        return nConta;
		    }
    public String obtertitular() {
		        return titular;
		    }
    public double obtersaldo() {
		        return saldo;
		    }
}


