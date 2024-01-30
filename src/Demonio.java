import javax.swing.JLabel;

public class Demonio implements Runnable {
	private JLabel facturacionTotal;
	private JLabel entradasNormal;
	private JLabel entradasVIP;
	private JLabel entradasPremium;
	private JLabel capacidadJ;

	private Caja cajaNormal;
	private Caja cajaVIP;
	private Caja cajaPremium;

	public Caja getCajaNormal() {
		return cajaNormal;
	}

	public void setCajaNormal(Caja cajaNormal) {
		this.cajaNormal = cajaNormal;
	}

	public Caja getCajaVIP() {
		return cajaVIP;
	}

	public void setCajaVIP(Caja cajaVIP) {
		this.cajaVIP = cajaVIP;
	}

	public Caja getCajaPremium() {
		return cajaPremium;
	}

	public void setCajaPremium(Caja cajaPremium) {
		this.cajaPremium = cajaPremium;
	}

	public Demonio(JLabel facturacion, JLabel normal, JLabel VIP, JLabel premium) {
		this.facturacionTotal = facturacion;
		this.entradasNormal = normal;
		this.entradasVIP = VIP;
		this.entradasPremium = premium;
	}

	public JLabel getCapacidadJ() {
		return capacidadJ;
	}

	public void setCapacidadJ(JLabel capacidadJ) {
		this.capacidadJ = capacidadJ;
	}

	@Override
	public void run() {
		while (true) {
			facturacionTotal.setText(String.valueOf(Caja.getTotal()));
			entradasNormal.setText(String.valueOf(cajaNormal.getEntradasDisponibles()));
			entradasVIP.setText(String.valueOf(cajaVIP.getEntradasDisponibles()));
			entradasPremium.setText(String.valueOf(cajaPremium.getEntradasDisponibles()));
			capacidadJ.setText(String.valueOf(cajaNormal.getEntradasDisponibles()
					+ cajaPremium.getEntradasDisponibles() + cajaVIP.getEntradasDisponibles()));
		}

	}
}
