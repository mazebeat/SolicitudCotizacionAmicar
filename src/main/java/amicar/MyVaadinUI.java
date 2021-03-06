package amicar;

import cl.intelidata.amicar.beans.Procesar;
import cl.intelidata.amicar.beans.Validador;
import cl.intelidata.amicar.componentes.Body;
import cl.intelidata.amicar.referencias.Texto;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

import javax.servlet.annotation.WebServlet;

@Theme("amicartheme")
@SuppressWarnings("serial")
public class MyVaadinUI extends UI {

	@Override
	protected void init(VaadinRequest request) {
		final VerticalLayout layout = new VerticalLayout();
		layout.setMargin(true);
		setContent(layout);

		/**************************************************************/
		try {
			Body body = new Body();

			// Request obtiene los parametros pasados por url
			if ((request.getParameter(Texto.COTIZACION) != null)) {
				// Valida que
				Validador validador = new Validador(request.getParameter(Texto.COTIZACION));
				// Si el proceso es valido y la cotizacion no ha sido leida
				if (validador.isProcesoValido() && !validador.isCotizacionLeida()) {
					body.setMensaje(Texto.MENSAJE_CLIENTE, null);
					Procesar procesar = new Procesar(validador.getProcesoPrincipal());
					procesar.guardarCotizacion();
				} else if (validador.isProcesoValido() && validador.isCotizacionLeida()) {
					body.setMensaje(Texto.MENSAJE_PROCESO_OK, 10);
				} else {
					body.setMensaje(Texto.MENSAJE_PROCESO_INVALIDO, 10);
				}
				layout.addComponent(body);
			} else {
				System.out.println(Texto.MENSAJE_PROCESO_INVALIDO);
				body.setMensaje(Texto.MENSAJE_PROCESO_INVALIDO, 10);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@WebServlet(value = "/*", asyncSupported = true)
	@VaadinServletConfiguration(productionMode = false, ui = MyVaadinUI.class, widgetset = "amicar.AppWidgetSet")
	public static class Servlet extends VaadinServlet {}
}
