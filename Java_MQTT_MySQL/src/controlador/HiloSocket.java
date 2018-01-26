package controlador;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.sql.SQLException;

import modelo.InterfaceMySQL;

public class HiloSocket extends Thread {
	private boolean fin = false;

	@Override
	public void run() {

		ServerSocket servidor;
		Socket socket;
		InputStreamReader datosCliente;
		BufferedReader b;
		int puerto = 4455;
		try {
			servidor = new ServerSocket(puerto);
			servidor.setSoTimeout(10000);

			while (fin != true) {
				try {
					// System.out.println("Esperando al cliente...");
					socket = servidor.accept();
					// System.out.println("Cliente conectado");
					// En 2 l�neas
					datosCliente = new InputStreamReader(socket.getInputStream());
					b = new BufferedReader(datosCliente);
					// EL CLIENTE ENVIA UN MENSAJE
					String textoRec = b.readLine();
					// System.out.println("Recibiendo del cliente:\n\t" + textoRec);
					if (textoRec != null) {
						if (!textoRec.equals("null")) {
							String[] textos = textoRec.split("\\?");
							String[] datos = textos[1].split(",");
							String[] reglas = datos[1].split(" ");// limpio
							String[] presencia = datos[2].split(" ");// limpio
//							System.out.println("T� termostato: " + datos[0]);
//							System.out.println("Reglas activadas: " + reglas[0]);
//							System.out.println("Presencia: " + presencia[0]);

							boolean reglasAct;
							int reglasActBin;
							if (reglas[0].equals("true")) {
								reglasAct = true;
								reglasActBin = 1;
							} else {
								reglasAct = false;
								reglasActBin = 0;
							}
							boolean reglasPresencia;
							int reglasPresenciaBin;
							if (presencia[0].equals("true")) {
								reglasPresencia = true;
								reglasPresenciaBin = 1;
							} else {
								reglasPresencia = false;
								reglasPresenciaBin = 0;
							}

							Termostato.setTemperaturaTermostato(Float.parseFloat(datos[0]));
							Termostato.setActivado(reglasAct);
							Termostato.setPresencia(reglasPresencia);
							InterfaceMySQL.insertTermostato(Float.parseFloat(datos[0]), reglasActBin, reglasPresenciaBin);

						}
					}

				} catch (SocketTimeoutException s) {
					// System.out.println("Socket timed out!");
				} catch (NumberFormatException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Fin hilo socket");
	}

	public void finHilo() {
		fin = true;
	}

}