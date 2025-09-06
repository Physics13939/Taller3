package uniandes.dpoo.aerolinea.modelo;

import uniandes.dpoo.aerolinea.tiquetes.Tiquete;
import uniandes.dpoo.aerolinea.exceptions.VueloSobrevendidoException;
import uniandes.dpoo.aerolinea.modelo.cliente.Cliente;
import uniandes.dpoo.aerolinea.modelo.tarifas.CalculadoraTarifas;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class Vuelo {
	private String fecha;
	private Ruta ruta;
	private Avion avion;
	
	private Map<String, Tiquete> tiquetes;
	
	public Vuelo(Ruta ruta, String fecha, Avion avion) {
		this.ruta = ruta;
		this.fecha = fecha;
		this.avion = avion;
		this.tiquetes = new HashMap<>();
	}

	public String getFecha() {
		return fecha;
	}

	public Ruta getRuta() {
		return ruta;
	}

	public Avion getAvion() {
		return avion;
	}

	public Collection<Tiquete> getTiquetes() {
		return tiquetes.values();
	}
	
	public int venderTiquetes(Cliente cliente, CalculadoraTarifas calculadora, int cantidad) throws VueloSobrevendidoException
	{
		int total = 0;
	    for (int i = 0; i < cantidad; i++) {
	        int tarifa = calculadora.calcularTarifa(this, cliente);
	        
	        String codigo = fecha + "-" + cliente.getTipoCliente() + "-" + i;
	
	        Tiquete tiquete = new Tiquete(codigo, this, cliente, tarifa);
	
	        tiquetes.put(codigo, tiquete);
	
	        total += tarifa;
	    }
	    return total;
	}
	public boolean equals(Object obj) {
		if (this == obj) {
	        return true; 
	    }
	    if (obj == null || getClass() != obj.getClass()) {
	        return false; 
	    }
	    Vuelo other = (Vuelo) obj;
	    return this.fecha.equals(other.fecha) && this.ruta.equals(other.ruta);
	}
}
