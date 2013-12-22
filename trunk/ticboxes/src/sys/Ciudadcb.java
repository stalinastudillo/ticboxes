package sys;
import java.util.List;

import javax.swing.AbstractListModel;
import javax.swing.ComboBoxModel;

import data.Ciudad;
import data.Embalaje;
import data.Unidad;


public class Ciudadcb extends AbstractListModel implements ComboBoxModel {

	private final List<Ciudad> lista;
	
	Object sel = null; 
	
	public Ciudadcb(List<Ciudad> alist) {
		lista = alist;
	}
	
	@Override
	public Object getElementAt(int lin) {
		Ciudad xx = lista.get(lin);
		return xx.getDescripcion();
	}
	
	public Ciudad getElemento(int lin) {
		Ciudad xx = lista.get(lin);
		return xx;
	}
	

	@Override
	public int getSize() {
		return lista.size();
	}

	@Override
	public Object getSelectedItem() {
		return sel;
	}

	@Override
	public void setSelectedItem(Object obj) {
		sel = obj;
		int selected = -1;
		for (int i = 0; i < lista.size(); i++)
			if (obj==lista.get(i))
			selected = i;
		fireContentsChanged(this,selected,selected);
	}

/*	public void setSeleccion(Object obj) {
		sel = obj;
		int select = -1;
		for (int i = 0; i < lista.size(); i++)
			if (obj == lista.get(i))
				select = i;
		fireContentsChanged(this,select,select);
	}
*/
	
}
