package servidor;

import ar.edu.unlu.rmimvc.Util;
import ar.edu.unlu.rmimvc.servidor.Servidor;
import ar.edu.unlu.rmimvc.RMIMVCException;
import interfaces.IModelo;
import modelo.Partida;

import javax.swing.*;
import java.rmi.RemoteException;
import java.util.ArrayList;

public class AppServidor {
	private IModelo modelo;

	public AppServidor(IModelo modelo) {
		this.modelo = modelo;
	}

	public static void main(String[] args) {
		ArrayList<String> ips = Util.getIpDisponibles();
		String ip = (String) JOptionPane.showInputDialog(
				null,
				"Seleccione la IP en la que escuchará peticiones el servidor", "IP del servidor",
				JOptionPane.QUESTION_MESSAGE,
				null,
				ips.toArray(),
				null
		);
		String port = (String) JOptionPane.showInputDialog(
				null,
				"Seleccione el puerto en el que escuchará peticiones el servidor", "Puerto del servidor",
				JOptionPane.QUESTION_MESSAGE,
				null,
				null,
				8888
		);
		Servidor servidor = new Servidor(ip, Integer.parseInt(port));
		try {
            Partida modelo = new Partida();
			servidor.iniciar(modelo);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RMIMVCException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void setModelo(IModelo modelo) {
		this.modelo = modelo;
	}
}
