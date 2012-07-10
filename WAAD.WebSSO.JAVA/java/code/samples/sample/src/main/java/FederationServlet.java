//-----------------------------------------------------------------------
// <copyright file="FederationServlet.java" company="Microsoft">
//     Copyright (c) Microsoft Corporation.  All rights reserved.
//
// 
//    Copyright 2012 Microsoft Corporation
//    All rights reserved.
//
//    Licensed under the Apache License, Version 2.0 (the "License");
//    you may not use this file except in compliance with the License.
//    You may obtain a copy of the License at
//      http://www.apache.org/licenses/LICENSE-2.0
//
// THIS CODE IS PROVIDED *AS IS* BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, 
// EITHER EXPRESS OR IMPLIED, INCLUDING WITHOUT LIMITATION ANY IMPLIED WARRANTIES OR 
// CONDITIONS OF TITLE, FITNESS FOR A PARTICULAR PURPOSE, MERCHANTABLITY OR NON-INFRINGEMENT.
//
// See the Apache Version 2.0 License for specific language governing 
// permissions and limitations under the License.
// </copyright>
//
// <summary>
//     
//
// </summary>
//----------------------------------------------------------------------------------------------

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.microsoft.samples.federation.FederatedAuthenticationListener;
import com.microsoft.samples.federation.FederatedPrincipal;
import com.microsoft.samples.federation.FederationException;
import com.microsoft.samples.waad.federation.ConfigurableFederatedLoginManager;

public class FederationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
		
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String token = request.getParameter("wresult").toString();
		
		if (token == null) {
			response.sendError(400, "You were supposed to send a wresult parameter with a token");
		}
		
		ConfigurableFederatedLoginManager loginManager = ConfigurableFederatedLoginManager.fromRequest(request, new SampleAuthenticationListener());

		try {
			loginManager.authenticate(token, response);
		} catch (FederationException e) {
			response.sendError(500, "Oops! and error occurred.");
		}
	}
	
	private class SampleAuthenticationListener implements FederatedAuthenticationListener {
		@Override
		public void OnAuthenticationSucceed(FederatedPrincipal principal) {
			// ***
			// do whatever you want with the principal object that contains the token's claims
			// ***
		}		
	}
}
