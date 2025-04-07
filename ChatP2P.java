import java.io.*;
import java.net.*;
import java.util.*;

public class ChatP2P {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Digite a porta para escutar mensagens: ");
        int portaReceber = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Digite o endereço do outro cliente: ");
        String enderecoOutroCliente = scanner.nextLine();
        System.out.print("Digite a porta para enviar mensagens: ");
        int portaEnviar = scanner.nextInt();
        new Thread(new ReceberMensagens(portaReceber)).start();
        new Thread(new EnviarMensagens(enderecoOutroCliente, portaEnviar)).start();
    }
}
