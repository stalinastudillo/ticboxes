package sys;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.MaskFormatter;

import org.apache.xmlbeans.impl.xb.xsdschema.RestrictionDocument.Restriction;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import data.Factura;
import data.Persona;

public class BuscarFacturaFrame extends JInternalFrame {

	JPanel p0,p1,p2;
	JFormattedTextField jtfecha1, jtfecha2;
	JLabel tffecha1, tffecha2;
	JTextField tfnumfac,tfnombre,tfrazon;
	JLabel jlnumfac, jlnombre, jlrazon;
//	JButton btactualizar;
	List<Factura> fact;
	DefaultTableCellRenderer centrar = new DefaultTableCellRenderer();
	DefaultTableCellRenderer derecha = new DefaultTableCellRenderer();
	DefaultTableModel datos = new DefaultTableModel() {
	    @Override
	    public boolean isCellEditable(int row, int column) {
	       //all cells false
	       return false;
	    }
	};
	JTable jtbusqueda;
	JScrollPane scroll;
	SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
	MaskFormatter mffecha ;

	
	public BuscarFacturaFrame(final AdminFactura adminFactura) {
		// TODO Auto-generated constructor stub
		super("Búsqueda de Facturas",true,true,true,true);
		fact = new ArrayList();
		setSize(600,300);
		setLayout(new FlowLayout());
		p0 = new JPanel();
//		p0.setSize(600,10);
		p0.setLayout(new FlowLayout());
		p1 = new JPanel();
//		p1.setSize(600,30);
//		p1.setLayout(new FlowLayout());
		p2 = new JPanel();
//		p2.setSize(600,250);
		
		tffecha1 = new JLabel("Fecha Desde");
		tffecha2 = new JLabel("Fecha Hasta");
		jlrazon = new JLabel("Razón");
		jlnombre = new JLabel("Nombre");
		jlnumfac = new JLabel("Nro. Fact.");
		jtfecha1 = new JFormattedTextField();
		jtfecha2 = new JFormattedTextField();
		
		try {
			mffecha = new MaskFormatter("**/**/****");
			jtfecha1 = new JFormattedTextField(mffecha);
			jtfecha1.setColumns(8);
			jtfecha2 = new JFormattedTextField(mffecha);
			jtfecha2.setColumns(8);
		} catch (ParseException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}

		jtfecha1.setValue(formato.format(adminFactura.actual.getFecha()));
		
		Calendar fech = Calendar.getInstance();
		
		fech.setTime(adminFactura.actual.getFecha());
		fech.add(Calendar.DATE, 1);
		
		jtfecha2.setValue(formato.format(fech.getTime()));
		
		tfnumfac = new JTextField(5);
		tfnombre = new JTextField(15);
		tfrazon = new JTextField(15);
//		btactualizar = new JButton("Actualizar");
		scroll = new JScrollPane();
		Object[] newIdentifiers = {"Nro","Nombre","Razón Social","Monto"};		
		datos.setColumnIdentifiers(newIdentifiers);		
		JTable jtbusqueda = new JTable(datos);
		jtbusqueda.getColumnModel().getColumn(0).setPreferredWidth(70);
		jtbusqueda.getColumnModel().getColumn(0).setCellRenderer(centrar);
		jtbusqueda.getColumnModel().getColumn(1).setPreferredWidth(230);
		jtbusqueda.getColumnModel().getColumn(2).setPreferredWidth(230);
		jtbusqueda.getColumnModel().getColumn(3).setPreferredWidth(150);
		jtbusqueda.getColumnModel().getColumn(3).setCellRenderer(derecha);
		jtbusqueda.setFillsViewportHeight(true);

		p0.add(tffecha1);
		p0.add(jtfecha1);
		p0.add(tffecha2);
		p0.add(jtfecha2);
//		p0.add(jlnumfac);
//		p0.add(tfnumfac);
//		p0.add(jlnombre);
//		p0.add(tfnombre);
//		p0.add(jlrazon);
//		p0.add(tfrazon);
		
//		p0.add(btactualizar);
		
		scroll = new JScrollPane(jtbusqueda);
		scroll.setPreferredSize(new Dimension(550,170));
		
		JPanel p11 = new JPanel();
		GroupLayout layer = new GroupLayout(p11);
		layer.setAutoCreateGaps(true);
		layer.setAutoCreateContainerGaps(true);
		layer.setHorizontalGroup(
				layer.createSequentialGroup()
				.addGroup(layer.createParallelGroup(GroupLayout.Alignment.CENTER)
						.addComponent(jlnumfac)
						.addComponent(tfnumfac))
				.addGroup(layer.createParallelGroup(GroupLayout.Alignment.CENTER)
						.addComponent(jlnombre)
						.addComponent(tfnombre))
				.addGroup(layer.createParallelGroup(GroupLayout.Alignment.CENTER)
						.addComponent(jlrazon)
						.addComponent(tfrazon))
		);
		layer.setVerticalGroup(
				layer.createSequentialGroup()
				.addGroup(layer.createParallelGroup()
						.addComponent(jlnumfac)
						.addComponent(jlnombre)
						.addComponent(jlrazon))
				.addGroup(layer.createParallelGroup()
						.addComponent(tfnumfac,GroupLayout.PREFERRED_SIZE,GroupLayout.DEFAULT_SIZE,GroupLayout.PREFERRED_SIZE)
						.addComponent(tfnombre,GroupLayout.PREFERRED_SIZE,GroupLayout.DEFAULT_SIZE,GroupLayout.PREFERRED_SIZE)
						.addComponent(tfrazon,GroupLayout.PREFERRED_SIZE,GroupLayout.DEFAULT_SIZE,GroupLayout.PREFERRED_SIZE))				
		);
		
		p11.setLayout(layer);
		
		p1.add(p11);
		
//		p1.add(tfnumfac);
//		p1.add(tfnombre);
//		p1.add(tfrazon);

		p2.add(scroll);

		jtfecha1.addKeyListener(new KeyAdapter() 
		{
			@Override
			public void keyTyped(KeyEvent e)
			{
				char car = e.getKeyChar();
				// Verificación de Teclas pulsadas...
				if((car < '0' || car > '9') && car!='\b') 
					{
					getToolkit().beep();
					e.consume();
					}
			}
		});
		jtfecha1.addFocusListener(new FocusListener(){

			@Override
			public void focusGained(FocusEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void focusLost(FocusEvent arg0) {
				// TODO Auto-generated method stub
				try {
					busqueda(formato.parse(jtfecha1.getText()), formato.parse(jtfecha2.getText()), tfnombre.getText(), tfrazon.getText(), Integer.parseInt(tfnumfac.getText().equals("")?"0":tfnumfac.getText()));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		});

		jtfecha2.addKeyListener(new KeyAdapter() 
		{
			@Override
			public void keyTyped(KeyEvent e)
			{
				char car = e.getKeyChar();
				// Verificación de Teclas pulsadas...
				if((car < '0' || car > '9') && car!='\b') 
					{
					getToolkit().beep();
					e.consume();
					}
			}
		});
		jtfecha2.addFocusListener(new FocusListener(){

			@Override
			public void focusGained(FocusEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void focusLost(FocusEvent arg0) {
				// TODO Auto-generated method stub
				try {
					busqueda(formato.parse(jtfecha1.getText()), formato.parse(jtfecha2.getText()), tfnombre.getText(), tfrazon.getText(),  Integer.parseInt(tfnumfac.getText().equals("")?"0":tfnumfac.getText()));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}				
			}
			
		});

		tfnombre.addKeyListener(new KeyAdapter() 
		{
			public void keyTyped(KeyEvent e)
			{
				char car = e.getKeyChar();
				try {
					busqueda(formato.parse(jtfecha1.getText()), formato.parse(jtfecha2.getText()), (tfnombre.getText()+car).trim(), tfrazon.getText().trim(),  Integer.parseInt(tfnumfac.getText().equals("")?"0":tfnumfac.getText()));
				} catch (ParseException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
			}
		});
		tfrazon.addKeyListener(new KeyAdapter() 
		{
			public void keyTyped(KeyEvent e)
			{
				char car = e.getKeyChar();
				try {
					busqueda(formato.parse(jtfecha1.getText()), formato.parse(jtfecha2.getText()), tfnombre.getText().trim(), (tfrazon.getText()+car).trim(),  Integer.parseInt(tfnumfac.getText().equals("")?"0":tfnumfac.getText()));
				} catch (ParseException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
			}
		});
		tfnumfac.addKeyListener(new KeyAdapter() 
		{
			public void keyTyped(KeyEvent e)
			{
				char car = e.getKeyChar();
				// Verificación de Teclas pulsadas...
				if((car < '0' || car > '9') && car!='\b') 
					{
					getToolkit().beep();
					e.consume();
					car = ' ';
					}
				try {
					busqueda(formato.parse(jtfecha1.getText()), formato.parse(jtfecha2.getText()), tfnombre.getText(), tfrazon.getText(),  Integer.parseInt(tfnumfac.getText().trim().equals("")?"0":tfnumfac.getText()));
				} catch (ParseException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
			}
		});

		
		jtbusqueda.addKeyListener(new KeyListener() 
		{
			public void keyPressed(KeyEvent ee){

				if (ee.getKeyCode()== KeyEvent.VK_ENTER)
				{
					adminFactura.actual = (Factura) fact.get(((JTable) ee.getSource()).getSelectedRow());
					adminFactura.refreshFactura();
					close();
				}
			}

			@Override
			public void keyReleased(KeyEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void keyTyped(KeyEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});

		try {
			busqueda(formato.parse(jtfecha1.getText()), formato.parse(jtfecha2.getText()), "", "", 0);
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		add(p0);
		add(p1);
		add(p2);
	}

	private void busqueda(Date fecha1, Date fecha2, String nombre, String razon, int nrofac)
	{
		fact.clear();
		try {
			Session sesion = principal.fabrica.getCurrentSession();		
			sesion.beginTransaction();
			Criteria crit = sesion.createCriteria(Factura.class);
			Criterion cr1 = Restrictions.between("fecha", fecha1, fecha2);
			Disjunction disjun = Restrictions.disjunction();
			Conjunction conjun = Restrictions.conjunction();
			System.out.println("Fecha 1:"+fecha1.toString());
			System.out.println("Fecha 2:"+fecha2.toString());
			System.out.println("Nombre:"+nombre);
			System.out.println("Razón:"+razon);
			System.out.println("NroFact:"+nrofac);
			Criterion cr0 = null;
			Criterion cr2 = null;
			Criterion cr3 = null;
			Criterion cr4 = null;
			Criterion cr5 = null;
//			Criterion crt = Restrictions.disjunction();
			conjun.add(cr1);
			crit.createAlias("facOficina", "facOficina");
			cr0 = Restrictions.eq("facOficina.codigo", principal.ofic.getCodigo());
			conjun.add(cr0);
			if (!nombre.trim().equals(""))
			{
				crit.createAlias("remitente", "remitente");
				cr2 = Restrictions.like("remitente.nombre", "%"+nombre.trim()+"%");
				cr3 = Restrictions.like("remitente.apellido", "%"+nombre.trim()+"%");
				disjun.add(cr2);
				disjun.add(cr3);
			}
			if (!razon.trim().equals(""))
			{
				crit.createAlias("facturacion1", "facturacion1");
				cr4 = Restrictions.like("facturacion1.razon", "%"+razon.trim()+"%");
				disjun.add(cr4);
			}
			conjun.add(disjun);
			if (nrofac!=0) 
			{
				cr5 = Restrictions.like("control",nrofac);
				conjun.add(cr5);
			}
			crit.add(conjun);
			crit.addOrder(Order.asc("control"));
			crit.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
            fact = (List<Factura>) crit.list();
			sesion.getTransaction().commit();
		} catch (HibernateException e2) {
			JOptionPane.showMessageDialog(new JFrame(), "AQUI!!!Errores de Base de Datos!!!\n"+e2,"Database Error!",JOptionPane.ERROR_MESSAGE);
			e2.printStackTrace();
		}
		datos.getDataVector().clear();
		if (fact.size()==0)
		{
			System.out.println("NO Encontró Registros!!!!");
			// NO HACER NADA... DEJAR QUE SE ACTUALICE EN BLANCO!!!
		}else
		{
			for(Factura iter : fact)
			{
				System.out.println("Encontró Registros!!!!");
//				Object[] newIdentifiers = {"Nro","Nombre","Razón Social","Monto"};		
				String rr = "";
				try 
				{
					rr = iter.getFacturacion1().getRazon();					
				}catch (NullPointerException ee)
				{
					ee.printStackTrace();
				}
				
				Object[] record = {
						""+iter.getControl(),
						""+iter.getRemitente().getApellido()+", "+iter.getRemitente().getNombre(),
						""+rr,
						""+iter.getTotal()
						};
				datos.addRow(record);
			}
		}
		jtbusqueda = new JTable(datos);
		scroll.updateUI();
	}

	private void close()
	{
		super.dispose();
	}

}
