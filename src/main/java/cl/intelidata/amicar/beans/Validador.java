package cl.intelidata.amicar.beans;

import cl.intelidata.amicar.bd.Proceso;
import cl.intelidata.amicar.db.ConsultasDB;

public class Validador {
	/*******************************************************************************************************/
	/********************************************** Atributos **********************************************/
	/**
	 * ***************************************************************************************************
	 */

	private boolean procesoValido    = false;
	private boolean cotizacionLeida  = false;
	private Proceso procesoPrincipal = new Proceso();

	/*******************************************************************************************************/
	/******************************************** Constructores ********************************************/
	/**
	 * ***************************************************************************************************
	 */
	public Validador(String iProcesoID) {
		try {
			MCrypt mcrypt = new MCrypt();
			String idProc = new String(mcrypt.decrypt(iProcesoID));
			if (idProc != null) {
				this.asignarProceso(Integer.parseInt(idProc));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/*******************************************************************************************************/
	/*********************************************** Metodos ***********************************************/
	/**
	 * ***************************************************************************************************
	 */

	private void asignarProceso(int iProcesoID) {
		ConsultasDB consultasDB = new ConsultasDB();
		Proceso proceso = consultasDB.procesoActivo(iProcesoID);
		this.setProcesoPrincipal(proceso);

		if ((this.getProcesoPrincipal() != null)) {
			this.setProcesoValido(true);
			if (proceso.getFechaClickLink() != null) {
				this.setCotizacionLeida(true);
			}
		}
	}

	/*******************************************************************************************************/
	/****************************************** Getters y Setters ******************************************/

	public boolean isProcesoValido() {
		return procesoValido;
	}

	/**
	 * ***************************************************************************************************
	 */

	public void setProcesoValido(boolean procesoValido) {
		this.procesoValido = procesoValido;
	}

	public boolean isCotizacionLeida() {
		return cotizacionLeida;
	}

	public void setCotizacionLeida(boolean cotizacionLeida) {
		this.cotizacionLeida = cotizacionLeida;
	}

	public Proceso getProcesoPrincipal() {
		return procesoPrincipal;
	}

	public void setProcesoPrincipal(Proceso procesoPrincipal) {
		this.procesoPrincipal = procesoPrincipal;
	}

}
