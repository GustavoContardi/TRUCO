package observer;

import ar.edu.unlu.rmimvc.observer.IObservableRemoto;
import ar.edu.unlu.rmimvc.observer.IObservadorRemoto;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public abstract class ObservableRemoto implements Remote, IObservableRemoto {

    private ArrayList<IObservadorRemoto> observadores;

	public ObservableRemoto() {
		this.observadores = new ArrayList<>();
	}

	/* (non-Javadoc)
	 * @see ar.edu.unlu.mvcrmi.IObservableRemoto#agregarObservador(ar.edu.unlu.mvcrmi.ObservadorRemoto)
	 */
	@Override
	public void agregarObservador(IObservadorRemoto o) throws RemoteException {
		this.observadores.add(o);
	}

	/* (non-Javadoc)
	 * @see ar.edu.unlu.mvcrmi.IObservableRemoto#removerObservador(ar.edu.unlu.mvcrmi.ObservadorRemoto)
	 */
	@Override
	public void removerObservador(IObservadorRemoto o) throws RemoteException {
		this.observadores.remove(o);
	}

	/* (non-Javadoc)
	 * @see ar.edu.unlu.mvcrmi.IObservableRemoto#notificarObservadores(java.lang.Object)
	 */
	@Override
	public void notificarObservadores(Object obj) throws RemoteException {
		for (IObservadorRemoto o: this.observadores) {
			o.actualizar(this, obj);
			/*new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						o.actualizar(obj);
					} catch (RemoteException e) {
						System.out.println("ERROR: notificando al observador.");
						e.printStackTrace();
					}
				}
			}).start();*/
		}
	}

	/* (non-Javadoc)
	 * @see ar.edu.unlu.mvcrmi.IObservableRemoto#notificarObservadores()
	 */
	@Override
	public void notificarObservadores() throws RemoteException {
		this.notificarObservadores(null);
	}
}
