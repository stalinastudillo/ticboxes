package sys;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import data.Factura;
import data.Pago;



public class FormadePago extends JInternalFrame implements ActionListener, KeyListener, FocusListener {
	JPanel p1,p2,p3,p4;
	JPanel p11,p12;
	static int pxx = 0;
	static Double mxx = 0.00;
	Factura factura;
	List<Pago> pagos;
	JButton btagrega, btborrar, btgrabar, btcancel;
	JRadioButton btefecti, btcheque, bttdebit, bttcredi;
	JLabel lbmonto, lbrefere, lbbanco, lbtipota, lbapagar, lbpagado, lbresta, lbmapaga, lbmpagdo, lbmresta, Ttipo;
	JTextField tfrefere;
	JFormattedTextField tfmonto;
	JComboBox cbbanco, cbtipota;
	ButtonGroup tipo;
	NumberFormat formateador = new DecimalFormat("###,##0.00");
	DefaultTableCellRenderer centrar = new DefaultTableCellRenderer();
	DefaultTableCellRenderer derecha = new DefaultTableCellRenderer();
	DefaultTableModel datos = new DefaultTableModel() {
	    @Override
	    public boolean isCellEditable(int row, int column) {
	       //all cells false
	       return false;
	    }
	};
	JTable jtpagos;
	JScrollPane scroll;
	
/*	public static void main(String[] args) {
		// TODO Auto-generated method stub
		JFrame frame = new FormadePago();
		frame.setVisible(true);
	}*/
	
	public FormadePago(Factura fact) {  
		super("Forma de Pago",false,true,false,false);
		setSize(410,280);
		factura = fact;
		setLayout(new GridLayout(2,2,5,5));
		pagos = new ArrayList<Pago>();
		Ttipo = new JLabel();
		centrar.setHorizontalAlignment(SwingConstants.CENTER);
		derecha.setHorizontalAlignment(SwingConstants.RIGHT);
		p1 = new JPanel();
		p11 = new JPanel();
		p12 = new JPanel();
		p2 = new JPanel();
		p3 = new JPanel();
		p4 = new JPanel();
		btefecti = new JRadioButton("Efectivo");
//		btefecti.setPreferredSize(new Dimension(110,30));
		btcheque = new JRadioButton("Cheque");
		bttdebit = new JRadioButton("T.Débito");
		bttcredi = new JRadioButton("T.Crédito");
		btagrega = new JButton(new ImageIcon("images/Plus24.gif"));
		btagrega.setEnabled(false);
		btborrar = new JButton(new ImageIcon("images/Minus24.gif"));
		btborrar.setEnabled(false);
		btgrabar = new JButton(new ImageIcon("images/Save24.gif"));
		btgrabar.setEnabled(false);
		btcancel = new JButton(new ImageIcon("images/Cancel24.gif"));
		btcancel.setEnabled(false);
		btagrega.setHorizontalAlignment(SwingConstants.CENTER);
		tipo = new ButtonGroup();
		btefecti.setMnemonic('E');
		btcheque.setMnemonic('H');
		bttdebit.setMnemonic('D');
		bttcredi.setMnemonic('C');
		tipo.add(btefecti);
		tipo.add(btcheque);
		tipo.add(bttcredi);
		tipo.add(bttdebit);
		lbmonto = new JLabel("Monto");
		lbrefere = new JLabel("Nro./Ref.");
		lbbanco = new JLabel("Banco");
		lbtipota = new JLabel("Tipo Tarj.");
		lbapagar = new JLabel("A Pagar", SwingConstants.CENTER);
		lbpagado = new JLabel("Pagado", SwingConstants.CENTER);
		lbresta = new JLabel("Resta", SwingConstants.CENTER);
		tfmonto = new JFormattedTextField(formateador);
		tfmonto.setColumns(8);
		cbbanco = new JComboBox();
		cbbanco.addItem("Banesco");
		cbbanco.addItem("Bicentenario");
		cbbanco.addItem("Caribe");
		cbbanco.addItem("Exterior");
		cbbanco.addItem("Fondo Común");
		cbbanco.addItem("Mercantil");
		cbbanco.addItem("Provincial");
		cbbanco.addItem("Sofitasa");	
		cbbanco.setSelectedIndex(-1);
		cbtipota = new JComboBox();
		tfmonto.setHorizontalAlignment(SwingConstants.RIGHT);
		tfrefere = new JTextField(8);
		datos.addColumn("Código");
		datos.addColumn("Monto");
		
		tfrefere.setEnabled(false);
		cbtipota.setEnabled(false);
		cbbanco.setEnabled(false);
		
		jtpagos = new JTable(datos);

		jtpagos.getColumnModel().getColumn(0).setPreferredWidth(70);
		jtpagos.getColumnModel().getColumn(0).setCellRenderer(centrar);
		jtpagos.getColumnModel().getColumn(1).setPreferredWidth(100);
		jtpagos.getColumnModel().getColumn(1).setCellRenderer(derecha);
		jtpagos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);


		try 
		{
			Session sesion = principal.fabrica.getCurrentSession();
			sesion.beginTransaction();
			double tot = 0;
			List<Pago> pps = new ArrayList();
			Query quer = sesion.createQuery("from Pago where CodFactura = :tex");
			quer.setInteger("tex", factura.getCodigo());
			pps = quer.list();
			sesion.getTransaction().commit();
			pxx = 0;
			mxx = 0.00;
			if (pps.size()>0)
			{
				for (Pago px : pps) 
				{
					Object[] aux = {px.getForma(), formateador.format(px.getMonto())};
					datos.addRow(aux);
					pagos.add(px);
					pxx++;
					mxx+=px.getMonto();
				}	
			}		
		} catch (HibernateException e2) {
			JOptionPane.showMessageDialog(new JFrame(), "Error Al Grabar Pagos!!!!"+e2,"Database Error!",JOptionPane.ERROR_MESSAGE);
		}		
		
		lbmapaga = new JLabel(formateador.format(fact.getTotal()), SwingConstants.CENTER);
		lbmapaga.setForeground(Color.BLUE);
		lbmapaga.setFont(new Font("Arial",Font.BOLD, 20));
		lbmpagdo = new JLabel(formateador.format(0+mxx), SwingConstants.CENTER);
		lbmpagdo.setForeground(Color.GREEN);
		lbmpagdo.setFont(new Font("Arial",Font.BOLD, 20));
		lbmresta = new JLabel(formateador.format(0-fact.getTotal()+mxx), SwingConstants.CENTER);
		lbmresta.setForeground(Color.RED);
		lbmresta.setFont(new Font("Arial",Font.BOLD, 20));

		
		p1.setLayout(new GridLayout(0,2,0,0));
		p11.setLayout(new GridLayout(4,0,0,0));
		p12.setLayout(new GridLayout(4,0,5,5));
		p11.add(btefecti);
		p11.add(btcheque);
		p11.add(bttdebit);
		p11.add(bttcredi);
		p12.add(btagrega);
		p12.add(btborrar);
		p12.add(btgrabar);
		p12.add(btcancel);
		p1.add(p11);
		p1.add(p12);

		GroupLayout layer = new GroupLayout(p2);
		layer.setAutoCreateGaps(true);
		layer.setAutoCreateContainerGaps(true);
		layer.setHorizontalGroup(layer.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(layer.createSequentialGroup()
						.addGroup(layer.createSequentialGroup()
						.addComponent(lbmonto)
						.addComponent(tfmonto,GroupLayout.PREFERRED_SIZE,GroupLayout.DEFAULT_SIZE,GroupLayout.PREFERRED_SIZE)
						.addComponent(Ttipo)))
				.addGroup(layer.createSequentialGroup()
						.addGroup(layer.createParallelGroup(GroupLayout.Alignment.LEADING)
								.addComponent(lbbanco)
								.addComponent(lbtipota)
								.addComponent(lbrefere))
						.addGroup(layer.createParallelGroup(GroupLayout.Alignment.LEADING)
								.addComponent(cbbanco,GroupLayout.PREFERRED_SIZE,GroupLayout.DEFAULT_SIZE,GroupLayout.PREFERRED_SIZE)
								.addComponent(cbtipota,GroupLayout.PREFERRED_SIZE,GroupLayout.DEFAULT_SIZE,GroupLayout.PREFERRED_SIZE)
								.addComponent(tfrefere,GroupLayout.PREFERRED_SIZE,GroupLayout.DEFAULT_SIZE,GroupLayout.PREFERRED_SIZE))));
		layer.setVerticalGroup(layer.createSequentialGroup()
				.addGroup(layer.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(lbmonto)
						.addComponent(tfmonto)
						.addComponent(Ttipo))
				.addGroup(layer.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(lbbanco)
						.addComponent(cbbanco))
				.addGroup(layer.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(lbtipota)
						.addComponent(cbtipota))
				.addGroup(layer.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(lbrefere)
						.addComponent(tfrefere)));
		p2.setLayout(layer);
			
		scroll = new JScrollPane(jtpagos);
		scroll.setPreferredSize(new Dimension(190,100));
		jtpagos.setFillsViewportHeight(true);
		
		p3.add(scroll);
		
		p4.setLayout(new GridLayout(6,0,0,0));
		p4.add(lbapagar);
		p4.add(lbmapaga);
		p4.add(lbpagado);
		p4.add(lbmpagdo);
		p4.add(lbresta);
		p4.add(lbmresta);
	
		add(p2);
		add(p1);
		add(p3);
		add(p4);
		
		btefecti.addItemListener(new ManejadorSeleccion("EFE"));
		btcheque.addItemListener(new ManejadorSeleccion("CHE"));
		bttdebit.addItemListener(new ManejadorSeleccion("TDB"));
		bttcredi.addItemListener(new ManejadorSeleccion("TCR"));
		
		ListSelectionModel lsm = jtpagos.getSelectionModel();
		lsm.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent ls){
				if (jtpagos.getSelectedRow()>-1+pxx)
					btborrar.setEnabled(true);
				else btborrar.setEnabled(false);
			}
		});
		
		btagrega.addActionListener(this);
		btborrar.addActionListener(this);
		btgrabar.addActionListener(this);
		btcancel.addActionListener(this);
		
		cbbanco.addActionListener(this);
		cbtipota.addActionListener(this);
		
		tfmonto.addKeyListener(new KeyAdapter()
		{
			@Override
			public void keyTyped(KeyEvent ex) 
			{
				char car = ex.getKeyChar();
				int largo = tfmonto.getText().length();
				if((car < '0' || car > '9') && car!='\b' && car!=',' && car!='\n') {
					getToolkit().beep();
					ex.consume();
				}else
				{
					try 
					{
						Double F = formateador.parse(tfmonto.getText().trim()).doubleValue();
						Double R = -formateador.parse(lbmresta.getText().trim()).doubleValue();
						System.out.println("tfmonto:"+formateador.format(F));
						System.out.println("lbmresta:"+formateador.format(R));
						if (F>R)
						{
							getToolkit().beep();
							tfmonto.setValue(0);
							tfmonto.setText("");
							btagrega.setEnabled(false);
							tipo.clearSelection();
//							ex.consume();
						}					
					} catch (ParseException e) {
						// TODO Auto-generated catch block
//						e.printStackTrace();
					}
				
				}
				if (car=='\n') {
					System.out.println("Presionó Enter!!!");
				}
				if (largo>11) {
					getToolkit().beep();
					ex.consume();
				}
				if (tfmonto.getText().contains(",") && car==','){
					getToolkit().beep();
					ex.consume();
				}
				if (!tfmonto.getText().trim().equals("")) 
					{
					System.out.println("No es Blanco tfmonto!!!");
					}
				else 
				{
					System.out.println("Es Blanco tfmonto!!!");
					tfmonto.setValue(0);
					tfmonto.setText("");
				}
				checkear();
			}
		});
		
		tfrefere.addKeyListener(new KeyAdapter()
		{
			public void keyTyped(KeyEvent ex) 
			{
				if (!tfrefere.getText().trim().equals("")) 
				{
					System.out.println("No es Blanco refere!!!"); 
				}
				else 
				{
					System.out.println("Es Blanco refere!!!"); 
				}
				checkear();
			}
		});
	}

	

	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource()==btagrega) {
			Pago nuevo = new Pago();
			Object[] record = null;
			nuevo.setPagFactura(factura);
			switch (Ttipo.getText()=="EFE"?1:Ttipo.getText()=="CHE"?2:Ttipo.getText()=="TDB"?3:Ttipo.getText()=="TCR"?4:0) {
			case (1) : // EFE
				nuevo.setForma("EFE");
				try {
					nuevo.setMonto(formateador.parse(tfmonto.getText()).doubleValue());
				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				nuevo.setFecha(new Date());
				Object[] aux = {"EFE", tfmonto.getText()};
				record = aux;
				break;
			case (2) : // CHE
				nuevo.setForma("CHE");
				try {
					nuevo.setMonto(formateador.parse(tfmonto.getText()).doubleValue());
				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				nuevo.setFecha(new Date());
				nuevo.setRefere(tfrefere.getText());
				Object[] aux1 = {"CHE", tfmonto.getText()};
				record = aux1;
				break;
			case (3) : // TDB
				nuevo.setForma("TDB");
				try {
					nuevo.setMonto(formateador.parse(tfmonto.getText()).doubleValue());
				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				nuevo.setFecha(new Date());
				nuevo.setRefere(tfrefere.getText());
				nuevo.setTTarje(cbtipota.getSelectedItem().toString());
				Object[] aux2 = {"TDB", tfmonto.getText()};
				record = aux2;
				break;
			case (4) : // TDC
				nuevo.setForma("TDC");
				try {
					nuevo.setMonto(formateador.parse(tfmonto.getText()).doubleValue());
				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				nuevo.setFecha(new Date());
				nuevo.setRefere(tfrefere.getText());
				nuevo.setTTarje(cbtipota.getSelectedItem().toString());
				Object[] aux3 = {"TDC", tfmonto.getText()};
				record = aux3;
				break;
			default :
				
			}
			try 
			{
				Double F = formateador.parse(tfmonto.getText().trim()).doubleValue();
				Double R = -formateador.parse(lbmresta.getText().trim()).doubleValue();
				if (F>R)
				{
					getToolkit().beep();
					tfmonto.setValue(0);
					tfmonto.setText("");
					btagrega.setEnabled(false);
//					ex.consume();
				}else
				{
					pagos.add(nuevo);
					datos.addRow(record);
					try {
						lbmpagdo.setText(formateador.format(formateador.parse(lbmpagdo.getText()).doubleValue()+nuevo.getMonto()));
						lbmresta.setText(formateador.format(formateador.parse(lbmresta.getText()).doubleValue()+nuevo.getMonto()));
					} catch (ParseException e1) {
						// TODO Auto-generated catch block
					//	e1.printStackTrace();
					}
				}
			} catch (ParseException el) {
				// TODO Auto-generated catch block
//				e.printStackTrace();
			}
			cbbanco.setSelectedIndex(-1);
			cbtipota.setSelectedIndex(-1);
			tfrefere.setText("");
			tfmonto.setValue(0);
			tfmonto.setText("");
			tipo.clearSelection();
			Ttipo.setText("");
			cbbanco.setEnabled(false);
			cbtipota.setEnabled(false);
		}
		
		if (e.getSource()==cbbanco || e.getSource()==cbtipota) {
			checkear();
		}
		
		if (e.getSource()==btborrar) 
		{
			try {
				lbmpagdo.setText(formateador.format(formateador.parse(lbmpagdo.getText()).
						doubleValue()-formateador.parse((String) datos.
						getValueAt(jtpagos.getSelectedRow(), 1)).doubleValue()));
				lbmresta.setText(formateador.format(formateador.parse(lbmresta.getText()).
						doubleValue()-formateador.parse((String) datos.
						getValueAt(jtpagos.getSelectedRow(), 1)).
						doubleValue()));
			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			pagos.remove(jtpagos.getSelectedRow());
			datos.removeRow(jtpagos.getSelectedRow());
			checkear();
		}

		if (e.getSource()==btgrabar)  // se supone que todo deba cuadrar!!! 
		{
			try 
			{
				Session sesion = principal.fabrica.getCurrentSession();
				sesion.beginTransaction();
				double tot = 0;
				int iff = 0;
				for (Pago pp : pagos)
				{
					tot+=pp.getMonto();
					if (iff>=pxx)
					{
						sesion.save(pp);							
					}
					iff++;
				}
				if (tot==factura.getTotal()) 
				{
					factura.setEstado("P");
				}else
				{
					factura.setEstado("A");	
				}				
				sesion.update(factura);
/*				String upd = "update Resultado set blockResult = false where ordenResult = :tex";
				Query quer = sesion.createQuery(upd);
				quer.setString("tex", factura.getOrdenFactura());				
				int rowC = quer.executeUpdate();
				System.out.println("Rows affected: " + rowC);
*/
				sesion.getTransaction().commit();				
			} catch (HibernateException e2) {
				JOptionPane.showMessageDialog(new JFrame(), "Error Al Grabar Pagos!!!!","Database Error!",JOptionPane.ERROR_MESSAGE);
			}
//			AdminFactura.refreshFactura();
			this.dispose();
		}
	
	
	}


	@Override
	public void focusGained(FocusEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void focusLost(FocusEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	
	
	
	
	private class ManejadorSeleccion implements ItemListener {
		private String ttipo;
		
		public ManejadorSeleccion (String tipo) {
			ttipo = tipo;
		}

		@Override
		public void itemStateChanged(ItemEvent arg0) {
			// TODO Auto-generated method stub
			Ttipo.setText(ttipo);
			cbtipota.removeAllItems();
			tfmonto.setEnabled(true);
			tfmonto.grabFocus();
			switch (ttipo=="EFE"?1:ttipo=="CHE"?2:ttipo=="TDB"?3:ttipo=="TCR"?4:0) {
			case (1) : // EFE
				tfrefere.setEnabled(false);
				cbtipota.setEnabled(false);
				cbbanco.setEnabled(false);
				break;
			case (2) : // CHE
				cbbanco.setSelectedIndex(-1);
				tfrefere.setEnabled(true);
				cbtipota.setEnabled(false);
				cbbanco.setEnabled(true);
				break;
			case (3) : // TDB
				tfrefere.setEnabled(true);
				cbtipota.addItem("Maestro");
				cbtipota.addItem("VisaElectron");
				cbtipota.setSelectedIndex(-1);
				cbtipota.setEnabled(true);
				cbbanco.setEnabled(true);
				break;
			case (4) : // TDC
				tfrefere.setEnabled(true);
				cbtipota.addItem("MasterCard");
				cbtipota.addItem("Visa");
				cbtipota.setSelectedIndex(-1);
				cbtipota.setEnabled(true);
				cbbanco.setEnabled(true);
				break;
			default :
				tfrefere.setEnabled(false);
				cbtipota.setEnabled(false);
				cbbanco.setEnabled(false);				
			}
			checkear();
		}
	}
	
	public void checkear(){
		String cheq = Ttipo.getText();
		switch (cheq=="EFE"?1:cheq=="CHE"?2:cheq=="TDB"?3:cheq=="TCR"?4:0) {
		case (1) : // EFE
			try {
				if (tfmonto.getText().trim().equals("")?false:formateador.parse(tfmonto.getText()).doubleValue()>0.00) 
				{
					btagrega.setEnabled(true); 
				}
				else
				{
					btagrega.setEnabled(false);
				}
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case (2) : // CHE
			try {
				if (tfmonto.getText().trim().equals("")?false:formateador.parse(tfmonto.getText()).doubleValue()>0.00 && !tfrefere.getText().trim().equals("") && cbbanco.getSelectedIndex()>-1) 
				{
					btagrega.setEnabled(true); 
				}
				else 
				{
					btagrega.setEnabled(false);
				}
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case (3) : // TDB
			try {
				if (tfmonto.getText().trim().equals("")?false:formateador.parse(tfmonto.getText()).doubleValue()>0.00 && !tfrefere.getText().trim().equals("") && cbbanco.getSelectedIndex()>-1 && cbtipota.getSelectedIndex()>-1) 
				{
					btagrega.setEnabled(true); 
				}
				else 
				{
					btagrega.setEnabled(false);
				}
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case (4) : // TDC
			try {
				if (tfmonto.getText().trim().equals("")?false:formateador.parse(tfmonto.getText()).doubleValue()>0.00 && !tfrefere.getText().trim().equals("") && cbbanco.getSelectedIndex()>-1 && cbtipota.getSelectedIndex()>-1) 
				{
					btagrega.setEnabled(true); 
				}
				else 
				{
					btagrega.setEnabled(false);
				}
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
//		default :
		}
//		try {
			if (pagos.size()>0) 
			{
				btgrabar.setEnabled(true);
//				btagrega.setEnabled(false);
			}else
				btgrabar.setEnabled(false);
/*		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
*/		
			try 
			{
				Double F = formateador.parse(tfmonto.getText().trim()).doubleValue();
				Double R = -formateador.parse(lbmresta.getText().trim()).doubleValue();
				System.out.println("tfmonto:"+formateador.format(F));
				System.out.println("lbmresta:"+formateador.format(R));
				
				if (F>R)
				{
					getToolkit().beep();
					tfmonto.setValue(0);
					tfmonto.setText("");
					btagrega.setEnabled(false);
//					ex.consume();
				}					
			} catch (ParseException e) {
				// TODO Auto-generated catch block
//				e.printStackTrace();
			}

	}
	
}
