package uk.co.nbrown.photoalbum.interceptors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import uk.co.nbrown.photoalbum.services.interfaces.KidService;

@Component
public class ClientInterceptor implements HandlerInterceptor {
	
	@Autowired private KidService kidService;
	
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
			Object handler) throws Exception {
		if (request.getMethod().equals("OPTIONS") || request.getMethod().equals("DELETE")) {
			return true;
		}
		String clientId = request.getHeader("client-id");
		if (clientId == null || !kidService.kidExists(clientId)) {
			response.sendError(401, "Unauthorised: Invalid Client ID");
			return false;
		}
		return true;
	}
}