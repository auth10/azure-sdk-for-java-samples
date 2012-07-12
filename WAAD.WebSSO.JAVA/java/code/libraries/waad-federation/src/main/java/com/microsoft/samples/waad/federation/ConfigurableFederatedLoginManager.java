//-----------------------------------------------------------------------
// <copyright file="ConfigurableFederatedLoginManager.java" company="Microsoft">
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

package com.microsoft.samples.waad.federation;

import java.net.URI;
import javax.servlet.http.HttpServletRequest;
import com.microsoft.samples.federation.FederatedAuthenticationListener;
import com.microsoft.samples.federation.FederatedLoginManager;
import com.microsoft.samples.federation.FederationException;
import com.microsoft.samples.federation.SamlTokenValidator;

public class ConfigurableFederatedLoginManager extends FederatedLoginManager {
	public static ConfigurableFederatedLoginManager fromRequest(HttpServletRequest request) {
		return fromRequest(request, null);
	}

	public static ConfigurableFederatedLoginManager fromRequest(HttpServletRequest request, FederatedAuthenticationListener listener) {
		return new ConfigurableFederatedLoginManager(request, listener);
	}
	
	private ConfigurableFederatedLoginManager(HttpServletRequest request, FederatedAuthenticationListener listener) {
		super(request, listener);
	}
	
	@Override
	protected void setAudienceUris(SamlTokenValidator validator) 
			throws FederationException {
		TrustedIssuersRepository repository = new TrustedIssuersRepository();
		try {
			for (TrustedIssuer trustedIssuer : repository.getTrustedIdentityProviderUrls()) {					
				validator.getAudienceUris().add(new URI(trustedIssuer.getSpn()));
			}
		} catch (Exception e) {
			throw new FederationException("Federated Login Configuration failure: Invalid Audience URI", e);
		}
	}
}
