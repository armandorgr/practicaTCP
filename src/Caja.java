import java.util.concurrent.locks.ReentrantLock;

public class Caja {
	private static float total = 0f;
	private float precio = 0f;
	private ReentrantLock lock = new ReentrantLock();
	private String nombreEntrada;
	private int entradasDisponibles;
	private int entradasReservadas = 0;

	public Caja(float precio, String nombre, int entradasDisponibles) {
		this.precio = precio;
		this.nombreEntrada = nombre;
		this.entradasDisponibles = entradasDisponibles;
	}

	public static float getTotal() {
		return total;
	}

	public static void setTotal(float total) {
		Caja.total = total;
	}

	public float getPrecio() {
		return precio;
	}

	public void setPrecio(float precio) {
		this.precio = precio;
	}

	public ReentrantLock getLock() {
		return lock;
	}

	public void setLock(ReentrantLock lock) {
		this.lock = lock;
	}

	public String getNombreEntrada() {
		return nombreEntrada;
	}

	public void setNombreEntrada(String nombreEntrada) {
		this.nombreEntrada = nombreEntrada;
	}
	

	public int getEntradasDisponibles() {
		return entradasDisponibles;
	}

	public void setEntradasDisponibles(int entradasDisponibles) {
		this.entradasDisponibles = entradasDisponibles;
	}

	public int getEntradasReservadas() {
		return entradasReservadas;
	}

	public void setEntradasReservadas(int entradasReservadas) {
		this.entradasReservadas = entradasReservadas;
	}

	public boolean reservarEntradas(int cantidad) {
		boolean resultado = false;
		try {
			lock.lock();
			if(entradasDisponibles>=cantidad) {
				entradasDisponibles-=cantidad;
				entradasReservadas+=cantidad;
				resultado = true;
			}
			lock.unlock();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return resultado;
	}

	public void cancelarCompra() {
		if(entradasReservadas>0) {
			entradasDisponibles+=entradasReservadas;
			entradasReservadas=0;
		}
	}

	public void comprarEntrada() {
		lock.lock();
		if(entradasReservadas>0) {
			total+=entradasReservadas*precio;
			entradasReservadas=0;
		}
		lock.unlock();
	}
	
	public void actualizarEntradasDisponibles(boolean accion) {
		lock.lock();
		if(accion) {
			entradasDisponibles++;
		}else {
			if(entradasDisponibles>0) {
				entradasDisponibles--;
			}
		}
		lock.unlock();
	}

}
