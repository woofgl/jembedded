/* Adolfo Estevez Jimenez: aestevezjimenez@gmail.com
 * Copyright 2007-2009
 * http://code.google.com/p/jcontenedor,
 * http://code.google.com/p/jembedded,
 * http://code.google.com/p/jservicerules
 * http://semanticj2ee.blogspot.com/ 
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.jsemantic.services.examples.energy.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jsemantic.services.examples.energy.MockDataGenerator;
import org.jsemantic.services.examples.energy.invoice.InvoiceService;
import org.jsemantic.services.examples.energy.model.Customer;
import org.jsemantic.services.examples.energy.model.Invoice;
import org.jsemantic.services.examples.energy.model.MeterReading;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

public class InvoiceController extends AbstractController {

	private InvoiceService invoiceService = null;

	private MockDataGenerator generator = null;

	public void setGenerator(MockDataGenerator generator) {
		this.generator = generator;
	}

	public void setInvoiceService(InvoiceService invoiceService) {
		this.invoiceService = invoiceService;
	}

	protected ModelAndView handleRequestInternal(HttpServletRequest arg0,
			HttpServletResponse arg1) throws Exception {

		Customer customer = generator.getCustomer();
		MeterReading reading = generator.getMeterReading();

		Invoice invoice = invoiceService.generateInvoice(customer, reading);

		ModelAndView mv = new ModelAndView("invoice");
		mv.addObject(invoice);
		return mv;
	}

}
