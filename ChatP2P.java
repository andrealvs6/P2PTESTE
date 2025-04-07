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
class EnviarMensagens implements Runnable {
    private final String endereco;
    private final int porta;

    public EnviarMensagens(String endereco, int porta) {
        this.endereco = endereco;
        this.porta = porta;
    }

    @Override
    public void run() {
        try {
            Socket socket = null;
            PrintWriter out = null;
            BufferedReader teclado = new BufferedReader(new InputStreamReader(System.in));

            while (socket == null || socket.isClosed()) {
                try {
                    socket = new Socket(endereco, porta);
                    out = new PrintWriter(socket.getOutputStream(), true);
                    System.out.println("Conectado ao cliente para envio.");
                } catch (IOException e) {
                    System.out.println("Aguardando cliente estar disponível para conexão...");
                    Thread.sleep(1000);
                }
            }
            String mensagem;
            while ((mensagem = teclado.readLine()) != null) {
                out.println(mensagem);
                if (mensagem.equalsIgnoreCase("sair")) {
                    System.out.println("Encerrando envio de mensagens...");
                    break;
                }
            }
            socket.close();
        } catch (IOException | InterruptedException e) {
            System.err.println("Erro ao enviar mensagens: " + e.getMessage());
        }
    }
}
