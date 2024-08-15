package banco;
import java.util.Arrays;
import java.util.Scanner;
import java.util.Random;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
public class Banco {

    public static void main(String[] args) {
         
        ArrayList<conta>contas=new ArrayList<>();
conta c = new conta();
conta j = new conta();
conta usuario=new conta();
Scanner avoid = new Scanner(System.in);
double saque=0; 
int op=0;
c.saldo=0;
c.nConta=1234;
c.titular="Carlos";
j.saldo=0;
j.nConta=4321;
j.titular="Jonas";
contas.add(c);
contas.add(j);
String menu="\nBem vindo ao Sistema Bancario. \n1.Depositar \n2.Sacar \n3.Transferir \n4.Mostrar Dados \n5.Cadastrar Titular \n6.Sair \n Digite a opcao:";
    do{
        System.out.println(menu);
        op=avoid.nextInt();
        switch(op){
        case 1 -> {
           System.out.println("Digite o numero da sua conta: ");
                    int nconta = avoid.nextInt();
                    avoid.nextLine();
                    usuario = encontrarConta(nconta, contas);
                    if (usuario != null) {
                        System.out.println("Digite o valor a ser depositado: ");
                        double depos = avoid.nextDouble();
                        if (usuario.depositar(depos)) {
                            System.out.println("\nDeposito efetuado com sucesso!");
                            System.out.println("O seu saldo agora eh R$" + usuario.obtersaldo());
                        } else {
                            System.out.println("\nOperacao nao realizada!");
                        }
                    } else {
                        System.out.println("Conta invalida!");
                    }
                }
        case 2 -> {
            System.out.println("Digite o numero da sua conta: ");
            int nconta=avoid.nextInt();
            avoid.nextLine();
            usuario= encontrarConta(nconta, contas);
            if (usuario == null) {
                System.out.println("Conta invalida! Tente novamente.");
                break;
            }
            System.out.println("Digite o valor a ser sacado: ");
            saque=avoid.nextDouble();
            avoid.nextLine();
            while (true) {
                if(saque<0){
                    System.out.println("Valor invalido: nao eh possível sacar valores negativos.");
                }else if(saque<usuario.obtersaldo()){
                    if(usuario.sacar(saque)){
                        System.out.println("\nSaque efetuado com sucesso!");
                        System.out.println("\nO seu saldo agora eh R$"+c.obtersaldo());
                    }else{
                        System.out.println("\nOperacao nao realizada!");
                    }
                }else if(saque==usuario.obtersaldo()){
                    System.out.println("\nO saque a seguir vai zerar o saldo da conta. Deseja continuar? (sim/nao)");
                    String ver = avoid.next();                    
                    if  (ver.equalsIgnoreCase("sim")) {
                        usuario.sacar(saque);
                        System.out.println("\nSaque efetuado com sucesso!");
                        break;
                    }else {
                        System.out.println("\nSaque cancelado.");   
                    }
                }else{
                    System.out.println("O valor do saque eh maior do que o saldo disponivel na conta. Tente novamente mais tarde!");
                }
            } 
        }
        case 3 -> {
            System.out.println("Digite o numero da conta de origem: ");
            int nori=avoid.nextInt();
            conta ori=encontrarConta(nori, contas);
            if(ori==null){
                System.out.println("Conta invalida!");
                break;
            }
            System.out.println("Digite o numero da conta de destino: ");
            int ndes=avoid.nextInt();
            conta des= encontrarConta(ndes, contas);
            if(des==null){
                System.out.println("Conta invalida!");
                break;
            }
            System.out.println("Digite o valor a ser transferido: ");
            double valor = avoid.nextInt();
            if(valor<ori.obtersaldo()){
                ori.transferir(des, valor);
                System.out.println("\nTransferencia de R$" + valor + " para a conta de " + des.obtertitular() + " realizada com sucesso.");
            } else if (valor == ori.obtersaldo()) {
                System.out.println("\nA transferencia a seguir vai zerar o saldo da conta. Deseja continuar? (sim/não)");
                String ver = avoid.next();
                if (ver.equalsIgnoreCase("sim")) {
                    ori.transferir(des, valor);
                    System.out.println("\nTransferencia de R$" + valor + " para a conta de " + des.obtertitular() + " realizada com sucesso.");
                } else {
                System.out.println("\nTransferencia cancelada.");
                }
            } else {
                System.out.println("\nTransferencia falhou. Verifique o saldo e tente novamente.");
            }
        }
        case 4 -> {
            System.out.println("Digite o numero da sua conta: ");
            int nconta = avoid.nextInt();
            usuario = encontrarConta(nconta, contas);
            if (usuario != null) {
                System.out.print(usuario.mDados());
            } else {
                System.out.println("Conta invalida!");
            }
            break;  
            }
        case 5 -> {
            conta novoUsuario = new conta();
            avoid.nextLine();
            String ncomp = "";
            while (true) {
                System.out.println("Digite seu nome COMPLETO (pelo menos duas palavras):");
                ncomp = avoid.nextLine().trim();  
                String[] partes = ncomp.split("\\s+"); 
                if (partes.length >= 2) {
                    novoUsuario.titular = partes[0];  
                    novoUsuario.sobrenome = String.join(" ", Arrays.copyOfRange(partes, 1, partes.length));
                    break;  
                } else {
                    System.out.println("Erro: O nome deve conter pelo menos duas palavras.");
                }
            }
            String[] ndiv=ncomp.split(" ", 2);
            novoUsuario.titular=ndiv[0];
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
            novoUsuario.nConta = maluco.nextInt(9000) + 1000;
            while(true){
            System.out.println("Certo, estamos quase finalizando! Para finalizar o cadastro eh necessario fazer um deposito inicial, sem limite de valor\nQual sera o valor do seu deposito inicial?");
                    double dInit=avoid.nextDouble(); 
                    if (dInit > 0) {
                        novoUsuario.saldo = dInit;
                        System.out.println("Pronto! O seu cadastro foi finalizado");
                        System.out.println("Agora vamos acompanhar os dados da sua conta!\n");
                        System.out.println("Nome: "+novoUsuario.titular+" "+novoUsuario.sobrenome+"\n"+docs+": "+doc+"\nTelefone: "+tele);
                        System.out.println("Numero da conta: "+novoUsuario.nConta+"\nSaldo inicial R$"+novoUsuario.saldo);
                        try (BufferedWriter dadinhos = new BufferedWriter(new FileWriter("cadastrar.txt", true))){
                            dadinhos.write("\nNome: "+novoUsuario.titular+" "+sobre);
                            dadinhos.newLine();
                            dadinhos.write(docs+": "+doc);
                            dadinhos.newLine();
                            dadinhos.write("Telefone: "+tele);
                            dadinhos.newLine();
                            dadinhos.write("Numero da conta: "+novoUsuario.nConta);
                            dadinhos.newLine();
                            dadinhos.write("Saldo inicial R$"+novoUsuario.saldo);
                        }catch (IOException e){
                            System.out.println("Ocorreu um erro ao salvar os dados. Tente novamente mais tarde");
                        }
                        contas.add(novoUsuario);
                        System.out.println(novoUsuario.titular+", seja bem vindo ao seu novo banco!");
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
    public static conta encontrarConta(int nconta, ArrayList<conta> contas) {
        for (conta conta : contas) {
            if (conta.obternConta() == nconta) {
                return conta;
            }
        }
        return null;
    }
    public static conta cadastrarUsuario(Scanner avoid) {
        conta usuario = new conta();
        return usuario;
    }
}

