import java.io.Serializable;
import java.util.ArrayList;

public class Consulta implements Serializable{
	private static final long serialVersionUID = 1L;
	private TipoAccion tipoAccion;
	private ArrayList<TipoEntrada> entradas;
	
	public Consulta(TipoAccion accion, ArrayList<TipoEntrada> entradas) {
		this.entradas = entradas;
		this.tipoAccion = accion;
	}

	public TipoAccion getTipoAccion() {
		return tipoAccion;
	}

	public void setTipoAccion(TipoAccion tipoAccion) {
		this.tipoAccion = tipoAccion;
	}

	public ArrayList<TipoEntrada> getEntradas() {
		return entradas;
	}

	public void setEntradas(ArrayList<TipoEntrada> entradas) {
		this.entradas = entradas;
	}	
	
	
	
}
