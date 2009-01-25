package org.jsemantic.jembedded.common.spring;

import java.util.Map;
import java.util.WeakHashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jsemantic.jembedded.common.BusinessService;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

public class Controller extends AbstractController {

	private BusinessService service = null;

	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String result = service.doSomething();
		Map<String, Object> model = new WeakHashMap<String, Object>();
		model.put("result", result);
		ModelAndView mv = new ModelAndView("index");
		mv.addAllObjects(model);
		return null;
	}

	public void setService(BusinessService service) {
		this.service = service;
	}

}
