package uniandes.dpoo.aerolinea.persistencia;

import java.io.FileWriter;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;


import uniandes.dpoo.aerolinea.modelo.Aerolinea;
import uniandes.dpoo.aerolinea.modelo.Aeropuerto;
import uniandes.dpoo.aerolinea.modelo.Avion;
import uniandes.dpoo.aerolinea.modelo.Ruta;
import uniandes.dpoo.aerolinea.modelo.Vuelo;

public class PersistenciaAerolineaJson implements IPersistenciaAerolinea{
	@Override
	public void cargarAerolinea(String archivo, Aerolinea aerolinea) {
	    try {
	        BufferedReader reader = new BufferedReader(new FileReader(archivo));
	        StringBuilder sb = new StringBuilder();
	        String line;
	        while ((line = reader.readLine()) != null) {
	            sb.append(line);
	        }
	        reader.close();

	        JSONObject json = new JSONObject(sb.toString());
	        JSONArray aeropuertos = json.getJSONArray("aeropuertos");
	        Map<String, Aeropuerto> mapaAeropuertos = new HashMap<>();
	        for (int i = 0; i < aeropuertos.length(); i++) {
	            JSONObject ap = aeropuertos.getJSONObject(i);
	            String nombre = ap.getString("nombre");
	            String codigo = ap.getString("codigo");
	            String ciudad = ap.getString("ciudad");
	            double latitud = ap.getDouble("latitud");
	            double longitud = ap.getDouble("longitud");

	            Aeropuerto aeropuerto = new Aeropuerto(nombre, codigo, ciudad, latitud, longitud);
	            mapaAeropuertos.put(codigo, aeropuerto);
	        }
	        JSONArray rutas = json.getJSONArray("rutas");
	        Map<String, Ruta> mapaRutas = new HashMap<>();
	        for (int i = 0; i < rutas.length(); i++) {
	            JSONObject r = rutas.getJSONObject(i);
	            String codigoRuta = r.getString("codigoRuta");
	            String origen = r.getString("origen");
	            String destino = r.getString("destino");
	            String horaSalida = r.getString("horaSalida");
	            String horaLlegada = r.getString("horaLlegada");

	            Aeropuerto apOrigen = mapaAeropuertos.get(origen);
	            Aeropuerto apDestino = mapaAeropuertos.get(destino);

	            Ruta ruta = new Ruta(apOrigen, apDestino, horaSalida, horaLlegada, codigoRuta);
	            mapaRutas.put(codigoRuta, ruta);
	        }
	        JSONArray aviones = json.getJSONArray("aviones");
	        Map<String, Avion> mapaAviones = new HashMap<>();
	        for (int i = 0; i < aviones.length(); i++) {
	            JSONObject a = aviones.getJSONObject(i);
	            String nombre = a.getString("nombre");
	            int capacidad = a.getInt("capacidad");

	            Avion avion = new Avion(nombre, capacidad);
	            aerolinea.agregarAvion(avion);
	            mapaAviones.put(nombre, avion);
	        }

	        JSONArray vuelos = json.getJSONArray("vuelos");
	        for (int i = 0; i < vuelos.length(); i++) {
	            JSONObject v = vuelos.getJSONObject(i);
	            String fecha = v.getString("fecha");
	            String codigoRuta = v.getString("ruta");

	            aerolinea.registrarVueloRealizado(fecha, codigoRuta);
	        }

	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
	
	@Override
	public void salvarAerolinea(String archivo, Aerolinea aerolinea) {
	    try {
	        JSONObject json = new JSONObject();

	        JSONArray aeropuertos = new JSONArray();
	        for (Ruta ruta : aerolinea.getRutas()) {
	            Aeropuerto origen = ruta.getOrigen();
	            Aeropuerto destino = ruta.getDestino();

	            JSONObject apOrigen = new JSONObject();
	            apOrigen.put("codigo", origen.getCodigo());
	            apOrigen.put("nombre", origen.getNombre());
	            apOrigen.put("ciudad", origen.getNombreCiudad());
	            apOrigen.put("latitud", origen.getLatitud());
	            apOrigen.put("longitud", origen.getLongitud());
	            aeropuertos.put(apOrigen);

	            JSONObject apDestino = new JSONObject();
	            apDestino.put("codigo", destino.getCodigo());
	            apDestino.put("nombre", destino.getNombre());
	            apDestino.put("ciudad", destino.getNombreCiudad());
	            apDestino.put("latitud", destino.getLatitud());
	            apDestino.put("longitud", destino.getLongitud());
	            aeropuertos.put(apDestino);
	        }
	        json.put("aeropuertos", aeropuertos);


	        JSONArray rutas = new JSONArray();
	        for (Ruta ruta : aerolinea.getRutas()) {
	            JSONObject r = new JSONObject();
	            r.put("codigoRuta", ruta.getCodigoRuta());
	            r.put("origen", ruta.getOrigen().getCodigo());
	            r.put("destino", ruta.getDestino().getCodigo());
	            r.put("horaSalida", ruta.getHoraSalida());
	            r.put("horaLlegada", ruta.getHoraLlegada());
	            rutas.put(r);
	        }
	        json.put("rutas", rutas);
	        
	        JSONArray aviones = new JSONArray();
	        for (Avion avion : aerolinea.getAviones()) {
	            JSONObject a = new JSONObject();
	            a.put("nombre", avion.getNombre());
	            a.put("capacidad", avion.getCapacidad());
	            aviones.put(a);
	        }
	        json.put("aviones", aviones);

	        JSONArray vuelos = new JSONArray();
	        for (Vuelo vuelo : aerolinea.getVuelos()) {
	            JSONObject v = new JSONObject();
	            v.put("fecha", vuelo.getFecha());
	            v.put("ruta", vuelo.getRuta().getCodigoRuta());
	            vuelos.put(v);
	        }
	        json.put("vuelos", vuelos);

	        FileWriter file = new FileWriter(archivo);
	        file.write(json.toString(4)); 
	        file.close();

	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}

}

