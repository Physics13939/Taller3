package uniandes.dpoo.aerolinea.modelo.tarifas;

import uniandes.dpoo.aerolinea.modelo.Vuelo;
import uniandes.dpoo.aerolinea.modelo.cliente.Cliente;
import uniandes.dpoo.aerolinea.modelo.cliente.ClienteCorporativo;
import uniandes.dpoo.aerolinea.modelo.Ruta;

public class CalculadoraTarifasTemporadaBaja extends CalculadoraTarifas{
	protected int COSTO_POR_KM_NATURAL = 600;
	protected int COSTO_POR_KM_CORPORATIVO = 900;
	protected double DESCUENTO_PEQ = 0.02;
	protected double DESCUENTO_MEDIANAS = 0.1;
	protected double DESCUENTO_GRANDES = 0.2;
	
	@Override
	public int calcularCostoBase(Vuelo vuelo, Cliente cliente) {
		Ruta ruta = vuelo.getRuta();
		int distancia = calcularDistanciaVuelo(ruta);

		if (cliente.getTipoCliente().equals("Natural")) {
			return distancia * COSTO_POR_KM_NATURAL;
		}
		else if (cliente.getTipoCliente().equals("Corporativo")) {
			return distancia * COSTO_POR_KM_CORPORATIVO;
		}
		else {
			return 0;
		}
	}
	@Override
	public double calcularPorcentajeDescuento(Cliente cliente) {
		  String tipoCliente = cliente.getTipoCliente();
		  if (tipoCliente.equals("Natural")) {
			  return 0.0;
		  }
		  else if (tipoCliente.equals("Corporativo")) {
			  ClienteCorporativo corp = (ClienteCorporativo) cliente;
			  int tamano = corp.getTamanoEmpresa();
			  if (tamano == 1) {
				  return DESCUENTO_PEQ;
			  }
			  else if (tamano == 2) {
				  return DESCUENTO_MEDIANAS;
			  }
			  else if (tamano == 3) {
				  return DESCUENTO_GRANDES;
			  }
		  }
		  return 0.0;
	}

}
