//-----------------------------------------------------------------------
// <copyright file="FederationFilter.java" company="Microsoft">
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
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.regex.*;

import com.microsoft.samples.federation.URLUTF8Encoder;
import com.microsoft.samples.waad.federation.ConfigurableFederatedLoginManager;

public class FederationFilter implements Filter {
	private String loginPage;
	private String allowedRegex;

	@Override
	public void init(FilterConfig config) throws ServletException {
		this.loginPage = config.getInitParameter("login-page-url");
		this.allowedRegex = config.getInitParameter("allowed-regex");
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {

		if (request instanceof HttpServletRequest) {
			HttpServletRequest httpRequest = (HttpServletRequest) request;

			if (!httpRequest.getRequestURL().toString().contains(this.loginPage)) {
				ConfigurableFederatedLoginManager loginManager = ConfigurableFederatedLoginManager.fromRequest(httpRequest);

				boolean allowedUrl = Pattern.compile(this.allowedRegex).matcher(httpRequest.getRequestURL().toString()).find();

				if (!allowedUrl && !loginManager.isAuthenticated()) {
					HttpServletResponse httpResponse = (HttpServletResponse) response;
					String encodedReturnUrl = URLUTF8Encoder.encode(httpRequest.getRequestURL().toString());
					httpResponse.setHeader("Location", this.loginPage + "?returnUrl=" + encodedReturnUrl);
					httpResponse.setStatus(302);
					return;
				}
			}
		}

		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {
	}
}
