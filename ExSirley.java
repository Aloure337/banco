package banco;
import java.util.Arrays;
import java.util.Scanner;
import java.util.Random;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JOptionPane;
public class Banco {

    public static void main(String[] args) {
         
        ArrayList<conta>contas=new ArrayList<>();
conta c = new conta();
conta j = new conta();
conta usuario=new conta();
Scanner avoid = new Scanner(System.in);
double saque=0; 
int op=0;
c.saldo=15000;
c.nConta=1234;
c.titular="Carlos";
j.saldo=15000;
j.nConta=4321;
j.titular="Jonas";
contas.add(c);
contas.add(j);
        
String menu="\nBem vindo ao Sistema Bancario. \n1.Depositar \n2.Sacar \n3.Transferir \n4.Mostrar Dados \n5.Cadastrar Titular \n6.Sair";
   
    do{
        String opStr =JOptionPane.showInputDialog(menu+"\n Digite a opcao:");
        op=Integer.parseInt(opStr);
        switch(op){
                
        case 1 : {
           String ncontaStr=JOptionPane.showInputDialog("Digite o numero da sua conta: ");
                    int nconta = Integer.parseInt(ncontaStr);
                    usuario = encontrarConta(nconta, contas);
                    if (usuario != null) {
                        String deposStr=JOptionPane.showInputDialog("Digite o valor a ser depositado: ");
                        double depos = Double.parseDouble(deposStr);
                        if (usuario.depositar(depos)) {
                            JOptionPane.showMessageDialog(null,"\nDeposito efetuado com sucesso!"+"\nO seu saldo agora eh R$" + usuario.obtersaldo());
                            
                        } else {
                            JOptionPane.showMessageDialog(null,"\nOperacao nao realizada!");
                        }
                    } else {
                        JOptionPane.showMessageDialog(null,"Conta invalida!");
                    }
                }
                break;
        case 2 : {
            String ncontaStr=JOptionPane.showInputDialog("Digite o numero da sua conta: ");
            int nconta=Integer.parseInt(ncontaStr);
            usuario= encontrarConta(nconta, contas);
            if (usuario == null) {
                JOptionPane.showMessageDialog(null,"Conta invalida! Tente novamente.");
                break;
            }
            String saqueStr=JOptionPane.showInputDialog("Digite o valor a ser sacado: ");
            saque=Double.parseDouble(saqueStr);
                if(saque<0){
                    JOptionPane.showMessageDialog(null,"Valor invalido: nao eh possível sacar valores negativos.");
                }else if(saque<usuario.obtersaldo()){
                    if(usuario.sacar(saque)){
                        JOptionPane.showMessageDialog(null,"\nSaque efetuado com sucesso!"+"\nO seu saldo agora eh R$"+usuario.obtersaldo());
                    }else{
                        JOptionPane.showMessageDialog(null,"\nOperacao nao realizada!");
                    }
                }else if(saque==usuario.obtersaldo()){
                    String verStr=JOptionPane.showInputDialog("\nO saque a seguir vai zerar o saldo da conta. Deseja continuar? (sim/nao)");
                    String ver = verStr.trim().toLowerCase();                   
                    if  (verStr.equalsIgnoreCase("sim")) {
                        usuario.sacar(saque);
                        JOptionPane.showMessageDialog(null,"\nSaque efetuado com sucesso!");
                        break;
                    }else {
                        JOptionPane.showMessageDialog(null,"\nSaque cancelado.");   
                    }
                }else{
                    JOptionPane.showMessageDialog(null,"O valor do saque eh maior do que o saldo disponivel na conta. Tente novamente mais tarde!");
                }
            } 
            break;
                
        case 3 : {
            String noriStr=JOptionPane.showInputDialog("Digite o numero da conta de origem: ");
            int nori=Integer.parseInt(noriStr);
            conta ori=encontrarConta(nori, contas);
            if(ori==null){
                JOptionPane.showMessageDialog(null,"Conta invalida!");
                break;
            }
            String ndesStr=JOptionPane.showInputDialog("Digite o numero da conta de destino: ");
            int ndes=Integer.parseInt(ndesStr);
            conta des= encontrarConta(ndes, contas);
            if(des==null){
                JOptionPane.showMessageDialog(null,"Conta invalida!");
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
