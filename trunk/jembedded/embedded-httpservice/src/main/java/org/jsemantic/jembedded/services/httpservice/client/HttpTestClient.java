package org.jsemantic.jembedded.services.httpservice.client;

import java.util.Map;

import org.apache.http.HttpResponse;
import org.jsemantic.jembedded.services.httpservice.exception.HttpTestClientException;
import org.jsemantic.jservice.core.component.Component;

public interface HttpTestClient extends Component {

	/**
	 * 
	 * @param uri
	 * @return
	 */
	public HttpResponse executeHttpGetRequest(String uri)
			throws HttpTestClientException;

	/**
	 * 
	 * @param uri
	 * @param parameters
	 * @return
	 */
	public HttpResponse executeHttpGetRequest(String uri,
			Map<String, Object> parameters) throws HttpTestClientException;

	/**
	 * 
	 * @param httppost
	 * @return
	 */
	public HttpResponse executeHttpPostRequest(String uri)
			throws HttpTestClientException;

	/**
	 * 
	 * @param uri
	 * @param parameters
	 * @return
	 */
	public HttpResponse executeHttpPostRequest(String uri,
			Map<String, Object> parameters) throws HttpTestClientException;

	
	
	public void consumeContent(HttpResponse response);


}
