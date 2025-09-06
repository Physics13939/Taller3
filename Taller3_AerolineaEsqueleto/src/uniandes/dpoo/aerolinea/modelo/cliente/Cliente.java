package uniandes.dpoo.aerolinea.modelo.cliente;

import uniandes.dpoo.aerolinea.tiquetes.Tiquete;
import java.util.ArrayList;
import java.util.List;
import uniandes.dpoo.aerolinea.modelo.Vuelo;


public abstract class Cliente 
{
	protected List<Tiquete> tiquetesSinUsar = new ArrayList<>();
	protected List<Tiquete> tiquetesUsados = new ArrayList<>();
	public Cliente() {
	}

	public abstract String getTipoCliente();
	public abstract String getIdentificador();
	
	public void agregarTiquete(Tiquete tiquete) {
		tiquetesSinUsar.add(tiquete);
	}
	public int calcularValorTotalTiquetes() {
		int total = 0;
		for(Tiquete t : tiquetesUsados) {
			total += t.getTarifa();
		}
		return total;
	}
	public void usarTiquetes(Vuelo vuelo) {
		List<Tiquete> usadosAhora = new ArrayList<>();
		for (Tiquete t : tiquetesSinUsar) {
            if (t.getVuelo().equals(vuelo)) {
                usadosAhora.add(t);
            }
        }
        tiquetesSinUsar.removeAll(usadosAhora);
        tiquetesUsados.addAll(usadosAhora);
	}
	
}
