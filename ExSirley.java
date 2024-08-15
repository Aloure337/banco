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
                
            }
            String ndesStr=JOptionPane.showInputDialog("Digite o numero da conta de destino: ");
            int ndes=Integer.parseInt(ndesStr);
            conta des= encontrarConta(ndes, contas);
            if(des==null){
                JOptionPane.showMessageDialog(null,"Conta invalida!");
                break;
            }
            String valorStr=JOptionPane.showInputDialog("Digite o valor a ser transferido: ");
            double valor = Double.parseDouble(valorStr);
            if(valor<ori.obtersaldo()){
                ori.transferir(des, valor);
                JOptionPane.showMessageDialog(null,"\nTransferencia de R$" + valor + " para a conta de " + des.obtertitular() + " realizada com sucesso.");
            } else if (valor == ori.obtersaldo()) {
                String verStr=JOptionPane.showInputDialog("\nA tranferencia a seguir vai zerar o saldo da conta. Deseja continuar? (sim/nao)");
                    if  (verStr.equalsIgnoreCase("sim")) {
                        ori.transferir(des, valor);
                        JOptionPane.showMessageDialog(null,"\nTransferencia efetuada com sucesso!");
                        
                    }else {
                        JOptionPane.showMessageDialog(null,"\nTransferencia cancelada!");   
                    }
            } else {
                JOptionPane.showMessageDialog(null,"\nTransferencia falhou. Verifique o saldo e tente novamente.");
            }
        }
            break;    
        case 4 : {
            String ncontaStr=JOptionPane.showInputDialog("Digite o numero da sua conta: ");
            int nconta=Integer.parseInt(ncontaStr);
            usuario = encontrarConta(nconta, contas);
            if (usuario != null) {
                JOptionPane.showMessageDialog(null,usuario.mDados());
            } else {
                JOptionPane.showMessageDialog(null,"Conta invalida!");
            }
            break;  
            }
                
        case 5 : {
            conta novoUsuario = new conta();
            
            String ncomp = "";
            while (true) {
                String ncompStr=JOptionPane.showInputDialog("Digite seu nome COMPLETO (pelo menos duas palavras):");
                  
                String[] partes = ncompStr.split("\\s+"); 
                if (partes.length >= 2) {
                    novoUsuario.titular = partes[0];  
                    novoUsuario.sobrenome = String.join(" ", Arrays.copyOfRange(partes, 1, partes.length));
                    break;  
                } else {
                    JOptionPane.showMessageDialog(null,"Erro: O nome deve conter pelo menos duas palavras.");
                }
            }
            String[] ndiv=ncomp.split(" ", 2);
            novoUsuario.titular=ndiv[0];
            String sobre = ndiv.length>1 ? ndiv[1]:"";
            String doc="";
            String docs = null;
            while (true){
                String docsStr=JOptionPane.showInputDialog("Voce deseja informar o CPF ou RG? \nUtilize letras maiusculas");
                
                if (docsStr.equals("CPF")||docsStr.equals("RG")){
                    String ncompStr=JOptionPane.showInputDialog("Digite seu "+ docsStr+":");
                    
                    if((docsStr.equals("CPF")&&doc.length()==11)||(docsStr.equals("RG")&&doc.length()>=7&&doc.length()<=9)){
                        JOptionPane.showMessageDialog(null,"Erro: o "+docsStr+" deve ter "+ (docsStr.equals("CPF") ? "11" : "7 a 9") + " caracteres. Tente novamente.");
                        break;
                    
                    }
                }else{
                    JOptionPane.showMessageDialog(null,"Opcao invalida. Por favor, escolha CPF ou RG.");
                }
                break;
            }
            String teleStr=JOptionPane.showInputDialog("Digite seu telefone (apenas numeros, com DDD)");
            String tele="";
            while(true){
                tele=avoid.nextLine().trim();
                if(11!=tele.length()){
                    JOptionPane.showMessageDialog(null,"Numero invalido. Tente novamente");
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
            String dInitStr=JOptionPane.showInputDialog("Certo, estamos quase finalizando! Para finalizar o cadastro eh necessario fazer um deposito inicial, sem limite de valor\nQual sera o valor do seu deposito inicial?");
                    double dInit=Double.parseDouble(dInitStr);
                    if (dInit > 0) {
                        novoUsuario.saldo = dInit;
                        JOptionPane.showMessageDialog(null,"Pronto! O seu cadastro foi finalizado"+"\nAgora vamos acompanhar os dados da sua conta!");
                        
                        JOptionPane.showMessageDialog(null, "Nome: " + novoUsuario.titular + " " + novoUsuario.sobrenome + "\n" + docsStr + ": " + doc + "\nTelefone: " + teleStr + "\nNumero da conta: " + novoUsuario.nConta + "\nSaldo inicial R$" + novoUsuario.saldo);
                        
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
                            JOptionPane.showMessageDialog(null,"Ocorreu um erro ao salvar os dados. Tente novamente mais tarde");
                        }
                        contas.add(novoUsuario);
                        JOptionPane.showMessageDialog(null,novoUsuario.titular+", seja bem vindo ao seu novo banco!");
                       break;
                    } else {
                        JOptionPane.showMessageDialog(null,"O deposito inicial deve ser maior que 0. Tente novamente.");
                    }
            }
            break;
                }
                
        case 6 : {
                break;
                }
                
        default : JOptionPane.showMessageDialog(null,"Opcao invalida");   
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

