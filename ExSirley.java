package banco;
import java.util.Arrays;
import java.util.Scanner;
import java.util.Random;
public class Banco {

    public static void main(String[] args) {
        conta c = new conta();
conta j = new conta();
Scanner avoid = new Scanner(System.in);
double saque=0;
int op=0;
c.saldo=15000;
c.nConta=21;
c.titular="Carlos";
j.saldo=15000;
j.nConta=12;
j.titular="Jonas";
String menu="\nBem vindo ao Sistema Bancario. \n1.Depositar \n2.Sacar \n3.Transferir \n4.Mostrar Dados \n5.Cadastrar Titular \n6.Sair \n Digite a opcao:";

    do{
        System.out.println(menu);
        op=avoid.nextInt();
        switch(op){
        case 1 -> {
            System.out.println("Digite o numero da sua conta: ");
            int nconta;
            nconta=avoid.nextInt();
            if(nconta==c.obternConta()){
                System.out.println("Digite o valor a ser depositado: ");
                int depos=avoid.nextInt();
                if(c.depositar(depos)){
                    System.out.println("\nDeposito efetuado com sucesso!");
                    System.out.println("O seu saldo agora eh R$"+c.obtersaldo());
                }else{
                    System.out.println("\nOperacao nao realizada!");
                     }           
            }else if(nconta==j.obternConta()){
                System.out.println("Digite o valor a ser depositado: ");
                int depos=avoid.nextInt();
                if(j.depositar(depos)){
                    System.out.println("\nDeposito efetuado com sucesso!");
                    System.out.println("\nO seu saldo agora eh R$"+j.obtersaldo());
                }else{
                    System.out.println("\nOperacao nao realizada!");
                     }
            }else{
                System.out.println("Conta invalida!");
                return;
                  }
                }
        case 2 -> {
            System.out.println(c.mDados());
            System.out.println(j.mDados());
            System.out.println("Digite o numero da sua conta: ");
            int nconta=avoid.nextInt();
            avoid.nextLine();
            if(c.obternConta()==nconta){
                while (true) {
                System.out.println("Digite o valor a ser sacado: ");
                saque=avoid.nextDouble();
                avoid.nextLine();
                if(saque<0){
                    System.out.println("Valor inválido: não é possível sacar valores negativos.");
                }else if(saque<c.obtersaldo()){
                    if(c.sacar(saque)){
                        System.out.println("\nSaque efetuado com sucesso!");
                        System.out.println("\nO seu saldo agora eh R$"+c.obtersaldo());
                    }else{
                        System.out.println("\nOperacao nao realizada!");
                    }
                }else if(saque==c.obtersaldo()){
                    System.out.println("\nO saque a seguir vai zerar o saldo da conta. Deseja continuar? (sim/nao)");
                    String ver = avoid.next();                    
                    if  (ver.equalsIgnoreCase("sim")) {
                        c.sacar(saque);
                        System.out.println("\nSaque efetuado com sucesso!");
                        break;
                    }else {
                        System.out.println("\nSaque cancelado.");   
                    }
                }else{
                    System.out.println("O valor do saque é maior do que o saldo disponivel na conta. Tente novamente mais tarde!");
                }
                }
        }else if (nconta==j.obternConta()){
            
                System.out.println("Digite o valor a ser sacado: ");
                saque=avoid.nextInt();
                avoid.nextLine();
                if(saque<0){
                    System.out.println("Valor invalido: nao eh possivel sacar valores negativos.");
                }else if(saque<j.obtersaldo()){
                    if(j.sacar(saque)){
                    System.out.println("\nSaque efetuado com sucesso!");
                    System.out.println("\nO seu saldo agora eh R$"+j.obtersaldo());
                    }
                }else if(saque==j.obtersaldo()){
                    System.out.println("\nO saque a seguir vai zerar o saldo da conta. Deseja continuar? (sim/nao)");
                    String ver = avoid.next();
                    if (ver.equalsIgnoreCase("sim")) {
                        j.sacar(saque);
                        System.out.println("\nSaque efetuado com sucesso!");
                    }else {
                        System.out.println("\nSaque cancelado."); 
                    }
                }else{
                    System.out.println("O valor do saque eh maior do que o saldo disponivel na conta. Tente novamente mais tarde!");
                    }
            }else{
                System.out.println("\nNumero de conta invalido! Tente novamente \n");
            }
        }
        case 3 -> {
            System.out.println(c.mDados());
            System.out.println(j.mDados());
            System.out.println("Digite o numero da conta de origem: ");
            int nori=avoid.nextInt();
            conta des = null;
            conta ori = null;
            if(nori==c.obternConta()){
                 ori=c;             
            }else if(nori==j.obternConta()){
                ori=j;
            }else{
                System.out.println("Conta invalida!");
            }
            System.out.println("Digite o numero da conta destino: ");
            int ndes=avoid.nextInt();
            if(ndes==c.obternConta()){
                des=c;             
            }else if(ndes==j.obternConta()){
                des=j;
            }else{
                System.out.println("Conta invalida!");
            }
            System.out.println("Digite o valor a ser transferido: R$");
            double valor = avoid.nextDouble();
            
            if(valor<ori.saldo){
                ori.transferir(des, valor);
                System.out.println("\n Transferencia de R$" + valor + " para a conta de " + des.titular + " realizada com sucesso.");
            }else if(valor==ori.saldo){
                System.out.println("\nA transferencia a seguir vai zerar o saldo da conta. Deseja continuar? (sim/nao)");
                String ver = avoid.next();
                if (ver.equalsIgnoreCase("sim")) {
                    ori.transferir(des, valor);
                    System.out.println("\nTransferencia de R$" + valor + " para a conta de " + des.titular + " realizada com sucesso.");
                } else {
                    System.out.println("\nTransferencia cancelada.");
                }
            }else{
                    System.out.println("\n Transferencia falhou. Verifique o saldo e tente novamente.");
                    }
                    break;
        }
        case 4 -> {
                        System.out.println("Digite o numero da sua conta: ");
                        int mconta;
                        mconta=avoid.nextInt();
                        if(mconta==c.nConta){
                            System.out.print(c.mDados());
                        }else if(mconta==j.nConta){
                            System.out.print(j.mDados());
                        }
                      break;  
                }
        case 5 -> {
            conta usuario=new conta();
            avoid.nextLine();
            String ncomp = "";
            while (true) {
                System.out.println("Digite seu nome COMPLETO (pelo menos duas palavras):");
                ncomp = avoid.nextLine().trim();  
                String[] partes = ncomp.split("\\s+"); 
                if (partes.length >= 2) {
                    usuario.titular = partes[0];  
                    usuario.sobrenome = String.join(" ", Arrays.copyOfRange(partes, 1, partes.length));  // Armazena o sobrenome
                    break;  
        } else {
            System.out.println("Erro: O nome deve conter pelo menos duas palavras.");
        }
    }
            String[] ndiv=ncomp.split(" ", 2);
            usuario.titular=ndiv[0];
            String sobre = ndiv.length>1 ? ndiv[1]:"";
            String doc="";
            String docs;
            while (true){
                System.out.println("Voce deseja informar o CPF ou RG? \nUtilize letras maiusculas");
                docs=avoid.nextLine().trim();
                if (docs.equals("CPF")||docs.equals("RG")){
                    System.out.println("Digite seu "+ docs+":");
                    doc=avoid.nextLine();
                    if((docs.equals("CPF")&&doc.length()==11)||(docs.equals("RG")&&doc.length()>=7&&doc.length()<=9)){
                    break;
                    }else{
                        System.out.println("Erro: o "+docs+" deve ter "+ (docs.equals("CPF") ? "11" : "7 a 9") + " caracteres. Tente novamente.");
                            }
                    
                }else{
                    System.out.println("Opcao invalida. Por favor, escolha CPF ou RG.");
                }
            }
            System.out.println("Digite seu telefone (apenas numeros, com DDD)");
            String tele="";
            while(true){
                tele=avoid.nextLine().trim();
                if(11!=tele.length()){
                    System.out.println("Numero invalido. Tente novamente");
                } else {
                    break;
                }
            }
            if (docs.equals("CPF")){
                doc=doc.replaceAll("(\\d{3})(\\d{3})(\\d{3})(\\d{2})", "$1.$2.$3-$4");
            }else if(docs.equals("RG")){
                if(doc.length()==7){
                    doc=doc.replaceAll("(\\d{1})(\\d{3})(\\d{3})", "$1.$2.$3");
                }else if(doc.length()==8){
                    doc=doc.replaceAll("(\\d{2})(\\d{3})(\\d{3})", "$1.$2.$3");
                }else if(doc.length()==9){
                    doc=doc.replaceAll("(\\d{2})(\\d{3})(\\d{3})(\\d{1})", "$1.$2.$3-$4");
                }
            }
            tele=tele.replaceAll("(\\d{2})(\\d{5})(\\d{4})","($1)$2-$3");
            Random maluco = new Random();
            usuario.nConta = maluco.nextInt(90) + 10;
            while(true){
            System.out.println("Certo, estamos quase finalizando! Para finalizar o cadastro eh necessario fazer um deposito inicial, sem limite de valor\nQual sera o valor do seu deposito inicial?");
                    double dInit=avoid.nextDouble();
                    
                    if (dInit > 0) {
                        usuario.saldo = dInit;
                        System.out.println("Pronto! O seu cadastro foi finalizado");
                        System.out.println("Agora vamos acompanhar os dados da sua conta!\n");
                        System.out.println("Nome: "+usuario.titular+" "+sobre+"\n"+docs+": "+doc+"\nTelefone: "+tele);
                        System.out.println("Numero da conta: "+usuario.nConta+"\nSaldo inicial R$"+usuario.saldo);
                        System.out.println("Numero da conta: "+usuario.nConta+"\nSaldo inicial R$"+usuario.saldo);
                        try (BufferedWriter dadinhos = new BufferedWriter(new FileWriter("cadastar.txt", true))){
                            dadinhos.write("Nome: "+usuario.titular+" "+sobre);
                            dadinhos.newLine();
                        }catch (IOException e){
                            System.out.println("Ocorreu um erro ao salvar os dados. Tente novamente mais tarde");
                        }
                        System.out.println(usuario.titular+", seja bem vindo ao seu novo banco!");
                       break;
                    } else {
                        System.out.println("O deposito inicial deve ser maior que 0. Tente novamente.");
                    }
            }
            break;
                }
        case 6 -> {
                break;
                }
        default -> System.out.println("Opcao invalida");   
            }
        }while(op!=6);  
}
}
   
