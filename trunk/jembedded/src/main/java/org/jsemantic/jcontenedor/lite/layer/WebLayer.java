package org.jsemantic.jcontenedor.lite.layer;

import javax.servlet.ServletContext;

import org.jsemantic.services.core.context.Context;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.context.support.XmlWebApplicationContext;

public class WebLayer extends PathLayer implements Layer {

	private Context contenedorContext = null;

	public WebLayer(ConfigurableApplicationContext appContext) {
		super(appContext);
		init();
	}

	public WebLayer(String id, String file, Context contenedorContext, int order) {
		super(id, file, order);
		this.contenedorContext = contenedorContext;
		init();
	}

	public void setContext(Context context) {
		this.contenedorContext = context;
	}

	public void init() {
		this.applicationContext = new XmlWebApplicationContext();
		((XmlWebApplicationContext) this.applicationContext).setId(super.getId());
		((XmlWebApplicationContext) this.applicationContext).setConfigLocation(this.file);
		((XmlWebApplicationContext) this.applicationContext)
				.setServletContext((ServletContext) this.contenedorContext
						.getExternal());
	}

	public void dispose() {
		this.applicationContext.close();
	}

	public void refresh(String file) {
		this.file = file;
		((XmlWebApplicationContext) this.applicationContext).setConfigLocation(this.file);
		this.refresh();
	}
}
