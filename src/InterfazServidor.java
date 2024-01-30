import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;

import org.w3c.dom.ls.LSOutput;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.KeyStore.PrivateKeyEntry;
import java.sql.ClientInfoStatus;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.awt.event.ActionEvent;

public class InterfazServidor {

	private JFrame frame;
	private int entradasNormales = 34;
	private int entradasVIP = 10;
	private int entradasPremium = 5;
	private Caja cajaNormal = new Caja(5.0f, "Normal", entradasNormales);
	private Caja cajaVip = new Caja(30.0f, "VIP", entradasVIP);
	private Caja cajaPremium = new Caja(50.0f, "Premium", entradasPremium);

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					InterfazServidor window = new InterfazServidor();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public InterfazServidor() {
		initialize();
		Thread hiloServidor = new Thread(()->{
			try {
				startServer();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		});
		hiloServidor.start();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 514, 409);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JLabel lblTitle = new JLabel("Servidor");
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setFont(new Font("Tahoma", Font.PLAIN, 22));
		lblTitle.setBounds(198, 11, 103, 28);
		frame.getContentPane().add(lblTitle);

		JLabel lblCapacidad = new JLabel("Capacidad total del festival");
		lblCapacidad.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblCapacidad.setHorizontalAlignment(SwingConstants.CENTER);
		lblCapacidad.setBounds(145, 61, 192, 28);
		frame.getContentPane().add(lblCapacidad);

		JLabel lblNumCapacidad = new JLabel("0");
		lblNumCapacidad.setHorizontalAlignment(SwingConstants.CENTER);
		lblNumCapacidad.setBounds(221, 100, 46, 14);
		frame.getContentPane().add(lblNumCapacidad);

		JLabel lblEntradasDisponibles = new JLabel("Entradas disponibles");
		lblEntradasDisponibles.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblEntradasDisponibles.setHorizontalAlignment(SwingConstants.CENTER);
		lblEntradasDisponibles.setBounds(172, 126, 143, 28);
		frame.getContentPane().add(lblEntradasDisponibles);

		JLabel lblNumDisponibles_normal = new JLabel("0");
		lblNumDisponibles_normal.setHorizontalAlignment(SwingConstants.CENTER);
		lblNumDisponibles_normal.setBounds(38, 216, 31, 14);
		frame.getContentPane().add(lblNumDisponibles_normal);

		JLabel lblNumDisponibles_vip = new JLabel("0");
		lblNumDisponibles_vip.setHorizontalAlignment(SwingConstants.CENTER);
		lblNumDisponibles_vip.setBounds(206, 216, 31, 14);
		frame.getContentPane().add(lblNumDisponibles_vip);

		JLabel lblNumDisponibles_premium = new JLabel("0");
		lblNumDisponibles_premium.setHorizontalAlignment(SwingConstants.CENTER);
		lblNumDisponibles_premium.setBounds(347, 216, 22, 14);
		frame.getContentPane().add(lblNumDisponibles_premium);

		JLabel lblNormal = new JLabel("Normal");
		lblNormal.setHorizontalAlignment(SwingConstants.CENTER);
		lblNormal.setBounds(63, 179, 46, 14);
		frame.getContentPane().add(lblNormal);

		JLabel lblVip = new JLabel("VIP");
		lblVip.setHorizontalAlignment(SwingConstants.CENTER);
		lblVip.setBounds(206, 179, 46, 14);
		frame.getContentPane().add(lblVip);

		JLabel lblPremium = new JLabel("Premium");
		lblPremium.setHorizontalAlignment(SwingConstants.CENTER);
		lblPremium.setBounds(347, 179, 56, 14);
		frame.getContentPane().add(lblPremium);

		JButton btnSumNormal = new JButton("+");

		btnSumNormal.setBounds(84, 200, 46, 23);
		frame.getContentPane().add(btnSumNormal);

		JButton btnResNormal = new JButton("-");

		btnResNormal.setBounds(84, 229, 46, 23);
		frame.getContentPane().add(btnResNormal);

		JButton btnSumVIP = new JButton("+");

		btnSumVIP.setBounds(242, 204, 46, 23);
		frame.getContentPane().add(btnSumVIP);

		JButton btnResVip = new JButton("-");

		btnResVip.setBounds(242, 233, 46, 23);
		frame.getContentPane().add(btnResVip);

		JButton btnSumPremium = new JButton("+");

		btnSumPremium.setBounds(379, 204, 46, 23);
		frame.getContentPane().add(btnSumPremium);

		JButton btnResPremium = new JButton("-");

		btnResPremium.setBounds(379, 233, 46, 23);
		frame.getContentPane().add(btnResPremium);

		JLabel lblFacturacion = new JLabel("Facturacion Total");
		lblFacturacion.setHorizontalAlignment(SwingConstants.CENTER);
		lblFacturacion.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblFacturacion.setBounds(145, 267, 143, 28);
		frame.getContentPane().add(lblFacturacion);

		JLabel lblNumFacturacionTotal = new JLabel("0");
		lblNumFacturacionTotal.setHorizontalAlignment(SwingConstants.CENTER);
		lblNumFacturacionTotal.setBounds(206, 306, 31, 14);
		frame.getContentPane().add(lblNumFacturacionTotal);



		

		Demonio daemon = new Demonio(lblNumFacturacionTotal, lblNumDisponibles_normal, lblNumDisponibles_vip,
				lblNumDisponibles_premium);
		daemon.setCajaNormal(cajaNormal);
		daemon.setCajaVIP(cajaVip);
		daemon.setCajaPremium(cajaPremium);
		daemon.setCapacidadJ(lblNumCapacidad);
		
		Thread hiloDemonio = new Thread(daemon);
		hiloDemonio.setDaemon(true);
		hiloDemonio.start();

		btnSumNormal.addActionListener((e) -> {
			cajaNormal.actualizarEntradasDisponibles(true);
		});

		btnResNormal.addActionListener((e) -> {
			cajaNormal.actualizarEntradasDisponibles(false);
		});

		btnSumVIP.addActionListener((e) -> {
			cajaVip.actualizarEntradasDisponibles(true);
		});

		btnResVip.addActionListener((e) -> {
			cajaVip.actualizarEntradasDisponibles(false);
		});

		btnSumPremium.addActionListener((e) -> {
			cajaPremium.actualizarEntradasDisponibles(true);
		});

		btnResPremium.addActionListener((e) -> {
			cajaPremium.actualizarEntradasDisponibles(false);
		});
	}
	
	private void comprar() {
		cajaNormal.comprarEntrada();
		cajaPremium.comprarEntrada();
		cajaVip.comprarEntrada();
	}
	
	private void cancelar() {
		cajaNormal.cancelarCompra();
		cajaPremium.cancelarCompra();
		cajaVip.cancelarCompra();
	}
	
	private boolean reservaPosible(Map<String, Integer> entradas) {
		boolean[] resultado = {true};
		entradas.forEach((k,v) -> {
			int entradasReservar = v;
			if(entradasReservar>0) {
				switch(k) {
				  case "NORMAL":
					  resultado[0] = (!(cajaNormal.getEntradasDisponibles()>=entradasReservar)) ? false : resultado[0];
					  break;
				  case "VIP":
					  resultado[0] = (!(cajaVip.getEntradasDisponibles()>=entradasReservar)) ? false : resultado[0];
					  break;
				  case "PREMIUM":
					  resultado[0] = (!(cajaPremium.getEntradasDisponibles()>=entradasReservar)) ? false : resultado[0];
					  break;
				}
			}
		});
		return resultado[0];
	}
	
	private void reservar(Map<String, Integer> entradas) {
		entradas.forEach((k,v) -> {
			int entradasReservar = v;
			if(entradasReservar>0) {
				switch(k) {
				  case "NORMAL":
					  cajaNormal.reservarEntradas(entradasReservar);
					  break;
				  case "VIP":
					  cajaVip.reservarEntradas(entradasReservar);
					  break;
				  case "PREMIUM":
					  cajaPremium.reservarEntradas(entradasReservar);
					  break;
				}
			}
		});
	}
	
	private void startServer() throws IOException, InterruptedException {
		try(ServerSocket conn = new ServerSocket(1234)){
			while(true) {
				Socket cliente = conn.accept();
				(new Thread(new Cliente(cliente))).start();
			}

		}
	}
	class Cliente implements Runnable {
		private Socket cliente;
		

		public Cliente(Socket cliente) {
			this.cliente = cliente;
		}
		
		

		@Override
		public void run() {
			boolean[] compraRealizada = {false};
			try {
			OutputStream clienteOutputStream = cliente.getOutputStream(); 
	        ObjectOutputStream pepe = new ObjectOutputStream(clienteOutputStream);
	        pepe.flush();
	        InputStream clienteInputStream = cliente.getInputStream();
	        ObjectInputStream clienteObjectInputStream = new ObjectInputStream(clienteInputStream);
			while (true) {
				try {
					//buffer aqui funciona
					BufferedReader lectorCliente = new BufferedReader(new InputStreamReader(clienteInputStream));
					String consulta = lectorCliente.readLine();
					switch(consulta) {
					case "CONSULTA" :
						Map<String, Integer> entradasDisponibles = new HashMap<>();
						entradasDisponibles.put("normal", cajaNormal.getEntradasDisponibles());
						entradasDisponibles.put("vip", cajaVip.getEntradasDisponibles());
						entradasDisponibles.put("premium", cajaPremium.getEntradasDisponibles());
						pepe.writeObject(entradasDisponibles);
						pepe.flush();
						break;
					case "COMPRA":
						compraRealizada[0]=true;
						comprar();
						clienteOutputStream.write(("compra"+"\n").getBytes());
						clienteOutputStream.flush();
						break;
					case "TIEMPO":
						new Thread(()->{
							try {
								
								for(int i = 30;i>=0;i--) {
									if(compraRealizada[0]) {
										System.out.print("compra servidor");
										break;
									}
									clienteOutputStream.write((i+"\n").getBytes());
									Thread.sleep(100);
								}
								if(!compraRealizada[0]) {
									System.out.println("cancelada");
									clienteOutputStream.write(("cancelada"+"\n").getBytes());
									clienteOutputStream.flush();
								}
								compraRealizada[0] = false;
								cancelar();
							}catch (Exception e) {
								e.printStackTrace();
							}
						}).start();
						break;
					case "RESERVA":				
						Map<String, Integer> mapa = (Map<String, Integer>) (clienteObjectInputStream.readObject());
						Boolean reservar = reservaPosible(mapa);
						clienteOutputStream.write((String.valueOf( reservar + "\n").getBytes()));
						clienteOutputStream.flush();
						System.out.print(reservar);
						if(reservar) reservar(mapa);
						System.out.print(mapa.toString());
						break;
					}					
				} catch (IOException  e) {
					e.printStackTrace();
					try {
						cliente.close();
						break;
					} catch (IOException e1) {
						// TODO Bloque catch generado automáticamente
						e1.printStackTrace();
					}
					e.printStackTrace();
				}

			}
			}catch (Exception e) {
				e.printStackTrace();
			}

		}

	}

}


