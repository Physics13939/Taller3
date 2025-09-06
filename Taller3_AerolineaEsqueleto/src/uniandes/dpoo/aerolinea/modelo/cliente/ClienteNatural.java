package uniandes.dpoo.aerolinea.modelo.cliente;

public class ClienteNatural extends Cliente{
	public ClienteNatural(String nombre) {
		super();
		this.nombre = nombre;
	}
	public String NATURAL = "Natural";
	private String nombre;
	
	public String getIdentificador() {
		return nombre;
	}
	public String getTipoCliente() {
		return NATURAL;
	}
}
