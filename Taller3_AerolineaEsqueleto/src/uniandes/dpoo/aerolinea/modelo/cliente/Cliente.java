package uniandes.dpoo.aerolinea.modelo.cliente;

import uniandes.dpoo.aerolinea.tiquetes.Tiquete;
import uniandes
import java.util.ArrayList;
import java.util.List;


public abstract class Cliente 
{
	
	private List<Tiquete> tiquetes;
	public Cliente() {
		this.tiquetes = new ArrayList<>();
	}
	public abstract String getTipoCliente();
	public abstract String getIdentificador();
	public void agregarTiquete(Tiquete tiquete) {
		tiquetes.add(tiquete);
	}
	public int calcularValorTotalTiquetes() {
	}
	public void usarTiquetes(Vuelo vuelo) {
    }
}
