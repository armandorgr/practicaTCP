import java.awt.EventQueue;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.ListModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;

public class InterfazCliente {

	private JFrame frame;
	private JLabel lblNumPremium;
	private JLabel lblNumNormales;
	private JLabel lblNumVIP;
	private JButton btnConfirmarCompra ;
	private JButton btnTiempo;
	private JLabel lblTiempo;
	private JButton btnConsultar;
	private TipoAccion accionActual = TipoAccion.ESPERA;
	private Map<String, Integer> entradas = new HashMap<>();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					InterfazCliente window = new InterfazCliente();
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
	public InterfazCliente() {
		entradas.put(TipoEntrada.NORMAL.name(), 0);
		entradas.put(TipoEntrada.VIP.name(), 0);
		entradas.put(TipoEntrada.PREMIUM.name(), 0);
		initialize();
		Thread hiloCliente = new Thread(() -> {
			try {
				connectServer();
			} catch (IOException | ClassNotFoundException e) {
				e.printStackTrace();
			}
		});
		hiloCliente.start();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 1100, 444);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JLabel lblTitle = new JLabel("Cliente");
		lblTitle.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setBounds(471, 22, 78, 25);
		frame.getContentPane().add(lblTitle);

		JLabel lblEntradasDisponibles = new JLabel("Entradas Disponibles");
		lblEntradasDisponibles.setHorizontalAlignment(SwingConstants.CENTER);
		lblEntradasDisponibles.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblEntradasDisponibles.setBounds(435, 80, 148, 25);
		frame.getContentPane().add(lblEntradasDisponibles);

		JLabel lblDisponiblesNormal = new JLabel("Entradas normales Disponibles");
		lblDisponiblesNormal.setHorizontalAlignment(SwingConstants.CENTER);
		lblDisponiblesNormal.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblDisponiblesNormal.setBounds(55, 136, 191, 36);
		frame.getContentPane().add(lblDisponiblesNormal);

		lblNumNormales = new JLabel("0");
		lblNumNormales.setHorizontalAlignment(SwingConstants.CENTER);
		lblNumNormales.setBounds(123, 192, 46, 14);
		frame.getContentPane().add(lblNumNormales);

		lblNumVIP = new JLabel("0");
		lblNumVIP.setHorizontalAlignment(SwingConstants.CENTER);
		lblNumVIP.setBounds(494, 192, 46, 14);
		frame.getContentPane().add(lblNumVIP);

		JLabel lblDisponiblesVip = new JLabel("Entradas VIP Disponibles");
		lblDisponiblesVip.setHorizontalAlignment(SwingConstants.CENTER);
		lblDisponiblesVip.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblDisponiblesVip.setBounds(426, 136, 191, 36);
		frame.getContentPane().add(lblDisponiblesVip);

		lblNumPremium = new JLabel("0");
		lblNumPremium.setHorizontalAlignment(SwingConstants.CENTER);
		lblNumPremium.setBounds(857, 192, 46, 14);
		frame.getContentPane().add(lblNumPremium);

		JLabel lblDisponiblesPremium = new JLabel("Entradas Premium Disponibles");
		lblDisponiblesPremium.setHorizontalAlignment(SwingConstants.CENTER);
		lblDisponiblesPremium.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblDisponiblesPremium.setBounds(788, 136, 191, 36);
		frame.getContentPane().add(lblDisponiblesPremium);

		JList list = new JList();
		list.setModel(new DefaultListModel<>());
		list.setBounds(68, 264, 101, 99);
		frame.getContentPane().add(list);

		JLabel lblEntradasComprar = new JLabel("Entradas a reservar");
		lblEntradasComprar.setHorizontalAlignment(SwingConstants.CENTER);
		lblEntradasComprar.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblEntradasComprar.setBounds(41, 234, 139, 14);
		frame.getContentPane().add(lblEntradasComprar);

		JButton btnSumNormal = new JButton("+");

		btnSumNormal.setBounds(240, 261, 41, 23);
		frame.getContentPane().add(btnSumNormal);

		JLabel lblSumarNormal = new JLabel("Normal");
		lblSumarNormal.setHorizontalAlignment(SwingConstants.CENTER);
		lblSumarNormal.setBounds(184, 265, 46, 14);
		frame.getContentPane().add(lblSumarNormal);

		JButton btnResNormal = new JButton("-");

		btnResNormal.setBounds(55, 374, 41, 23);
		frame.getContentPane().add(btnResNormal);

		JLabel lblSumarVip = new JLabel("VIP");
		lblSumarVip.setHorizontalAlignment(SwingConstants.CENTER);
		lblSumarVip.setBounds(184, 299, 46, 14);
		frame.getContentPane().add(lblSumarVip);

		JButton btnSumVIP = new JButton("+");

		btnSumVIP.setBounds(240, 295, 41, 23);
		frame.getContentPane().add(btnSumVIP);

		JLabel lblSumarPremium = new JLabel("Premium");
		lblSumarPremium.setHorizontalAlignment(SwingConstants.CENTER);
		lblSumarPremium.setBounds(184, 337, 46, 14);
		frame.getContentPane().add(lblSumarPremium);

		JButton btnSumPremium = new JButton("+");

		btnSumPremium.setBounds(240, 333, 41, 23);
		frame.getContentPane().add(btnSumPremium);

		JButton btnReservar = new JButton("Reservar");

		btnReservar.setBounds(116, 374, 89, 23);
		frame.getContentPane().add(btnReservar);
		
		btnConsultar = new JButton("Consultar");

		btnConsultar.setBounds(601, 83, 89, 23);
		frame.getContentPane().add(btnConsultar);
		
		btnTiempo = new JButton("");
		btnTiempo.setEnabled(false);
		btnTiempo.setForeground(new Color(0, 255, 0));
		btnTiempo.setBackground(new Color(0, 255, 64));
		btnTiempo.setBounds(561, 276, 126, 23);
		frame.getContentPane().add(btnTiempo);
		
		lblTiempo = new JLabel("Tiempo restante para confirmar compra");
		lblTiempo.setHorizontalAlignment(SwingConstants.CENTER);
		lblTiempo.setBounds(662, 264, 277, 49);
		frame.getContentPane().add(lblTiempo);
		
		btnConfirmarCompra = new JButton("Confirmar");

		btnConfirmarCompra.setBounds(672, 328, 89, 23);
		frame.getContentPane().add(btnConfirmarCompra);

		btnSumPremium.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addEntrada(TipoEntrada.PREMIUM, list);
			}
		});

		btnReservar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				accionActual = TipoAccion.RESERVA;
			}
		});

		btnSumNormal.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				addEntrada(TipoEntrada.NORMAL, list);
			}
		});

		btnResNormal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				removeEntrada(list);
			}
		});

		btnSumVIP.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addEntrada(TipoEntrada.VIP, list);
			}
		});
		
		btnConsultar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				accionActual = TipoAccion.CONSULTA;
			}
		});
		
		btnConfirmarCompra.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				accionActual = TipoAccion.COMPRA;
			}
		});
		
		confirmarNotVisible();
	}

	private void connectServer() throws UnknownHostException, IOException, ClassNotFoundException {
		try (Socket servidor = new Socket("localhost", 1234)) {
			OutputStream serverOutputStream = servidor.getOutputStream();
			InputStream servidorInputStream = servidor.getInputStream();
			ObjectInputStream pepe = new ObjectInputStream(servidorInputStream);
			ObjectOutputStream serverObjectOutputStream = new ObjectOutputStream(servidor.getOutputStream());
			serverObjectOutputStream.flush();
			BufferedReader lector = new BufferedReader(new InputStreamReader(servidorInputStream));
			while (true) {
				switch(accionActual) {
					case CONSULTA:
						
						System.out.println("consulta");
						serverOutputStream.write((accionActual.name()+"\n").getBytes());
						serverOutputStream.flush();
						
						Map<String, Integer> mapa = (Map<String, Integer>) pepe.readObject();
						lblNumNormales.setText(String.valueOf(mapa.get("normal")));
						lblNumPremium.setText(String.valueOf(mapa.get("premium")));
						lblNumVIP.setText(String.valueOf(mapa.get("vip")));
						
						accionActual = TipoAccion.ESPERA;
						break;
						
					case RESERVA:
						serverOutputStream.write((accionActual.name()+"\n").getBytes());
						serverOutputStream.flush();
						Thread.sleep(100);
												
						System.out.println("cliente"+entradas.toString());
						Map<String, Integer> nuevoMapa = new HashMap<>();
						nuevoMapa.put(TipoEntrada.NORMAL.name(), entradas.get("NORMAL"));
						nuevoMapa.put(TipoEntrada.VIP.name(), entradas.get("VIP"));
						nuevoMapa.put(TipoEntrada.PREMIUM.name(), entradas.get("PREMIUM"));
						serverObjectOutputStream.writeObject(nuevoMapa);
						serverObjectOutputStream.flush();
						
						if(lector.readLine().equals("false")) {
							JOptionPane.showConfirmDialog(null, "Reserva fallida, no hay suficientes entradas", "Custom Input", JOptionPane.OK_CANCEL_OPTION);
							accionActual = TipoAccion.ESPERA;
							break;
						}
						
						accionActual = TipoAccion.TIEMPO;
						break;
					case COMPRA:
						System.out.println("compra");
						serverOutputStream.write((accionActual.name()+"\n").getBytes());
						serverOutputStream.flush();						
						accionActual = TipoAccion.ESPERA;
						break;
					case TIEMPO:
						serverOutputStream.write((accionActual.name()+"\n").getBytes());
						serverOutputStream.flush();
						confirmarVisible();
						accionActual = TipoAccion.ESPERA;
						new Thread(()->{
							btnConsultar.setEnabled(false);
							String tiempo;
							try {
								while(!(tiempo = lector.readLine()).equals("")) {
									if(tiempo.equals("compra")) {
										System.out.println("pepe");
										accionActual = TipoAccion.CONSULTA;
										break;
									}
									if(tiempo.equals("cancelada")) {
										accionActual = TipoAccion.CONSULTA;
										int option = JOptionPane.showConfirmDialog(null, "Compra cancelada", "Custom Input", JOptionPane.OK_CANCEL_OPTION);
										break;
									}
									btnTiempo.setText(tiempo);
								}
							} catch (IOException e) {
								e.printStackTrace();
							}finally {
								btnConsultar.setEnabled(true);
							}
							confirmarNotVisible();
						}).start();
						break;
					default:
			
						break;						
				}

			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	private void removeEntrada(JList list) {
		DefaultListModel<String> model = (DefaultListModel<String>) list.getModel();
		if(model.getSize()>0){
			String element = model.get(model.getSize()-1);
			model.removeElement(element);
			entradas.put(element, entradas.get(element)-1);
			System.out.println(entradas.toString());
		}
	}
	
	private void confirmarVisible() {
		btnConfirmarCompra.setVisible(true);
		lblTiempo.setVisible(true);
		btnTiempo.setVisible(true);
	}

	private void confirmarNotVisible() {
		btnConfirmarCompra.setVisible(false);
		lblTiempo.setVisible(false);
		btnTiempo.setVisible(false);
	}
	
	private void addEntrada(TipoEntrada tipo, JList list) {
		DefaultListModel<String> model = (DefaultListModel<String>) list.getModel();
		switch (tipo) {
		case NORMAL:
			if (model.getSize() < 3) {
				model.addElement(tipo.name());
				entradas.put(tipo.name(), entradas.get(tipo.name())+1);
				System.out.print(entradas.get(tipo.name()));
			}
			break;
		case PREMIUM:
			if (model.getSize() < 3) {
				model.addElement(tipo.name());
				entradas.put(tipo.name(), entradas.get(tipo.name())+1);
				System.out.print(entradas.get(tipo.name()));
			}
			break;
		case VIP:
			if (model.getSize() < 3) {
				model.addElement(tipo.name());
				entradas.put(tipo.name(), entradas.get(tipo.name())+1);
				System.out.print(entradas.get(tipo.name()));
			}
			break;
		default:
			break;
		}
	}
}
