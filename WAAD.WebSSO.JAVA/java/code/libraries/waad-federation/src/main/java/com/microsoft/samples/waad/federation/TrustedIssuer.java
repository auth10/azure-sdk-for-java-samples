//-----------------------------------------------------------------------
// <copyright file="TrustedIssuer.java" company="Microsoft">
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

import com.microsoft.samples.federation.FederatedLoginManager;

public class TrustedIssuer {
	private String name;
	private String displayName;
	private String spn;
	private String replyURL = null;

	public TrustedIssuer(String name, String displayName, String spn) {
		this.name = name;
		this.displayName = displayName;
		this.spn = spn;
	}

	public TrustedIssuer(String name, String displayName, String spn, String replyURL) {
		this.name = name;
		this.displayName = displayName;
		this.spn = spn;
		this.replyURL = replyURL;
	}
	
	public String getName() {
		return this.name;
	}

	public String getDisplayName() {
		return this.displayName;
	}

	public String getSpn() {
		return this.spn;
	}

	public String getLoginURL(String returnUrl) {
		return FederatedLoginManager.getFederatedLoginUrl(this.spn, this.replyURL, returnUrl);
	}
}
