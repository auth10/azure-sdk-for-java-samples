//-----------------------------------------------------------------------
// <copyright file="TrustedIssuersRepository.java" company="Microsoft">
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

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class TrustedIssuersRepository {
	private String repositoryFileName;

	public TrustedIssuersRepository() {
		this.repositoryFileName = Constants.REPOSITORY_FILENAME;
	}

	public TrustedIssuersRepository(String repositoryFileName) {
		this.repositoryFileName = repositoryFileName;
	}

	public Iterable<TrustedIssuer> getTrustedIdentityProviderUrls()
			throws ParserConfigurationException, SAXException, IOException {
		List<TrustedIssuer> trustedIssuers = new ArrayList<TrustedIssuer>();

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder documentBuilder = factory.newDocumentBuilder();
		Document document = documentBuilder.parse(getRepositoryStream());

		NodeList xmlTrustedIssuers = document.getFirstChild().getChildNodes();
		for (int i = 0; i < xmlTrustedIssuers.getLength(); i++) {
			Node xmlTrustedIssuer = xmlTrustedIssuers.item(i);
			if (xmlTrustedIssuer instanceof Element) {
				trustedIssuers.add(new TrustedIssuer(
						xmlTrustedIssuer.getAttributes().getNamedItem(Constants.REPOSITORY_NAME_ATTRIBUTE).getTextContent(), 
						xmlTrustedIssuer.getAttributes().getNamedItem(Constants.REPOSITORY_DISPLAY_NAME_ATTRIBUTE).getTextContent(), 
						xmlTrustedIssuer.getAttributes().getNamedItem(Constants.REPOSITORY_REALM_ATTRIBUTE).getTextContent()));
			}
		}

		return trustedIssuers;
	}
	
	public TrustedIssuer getTrustedIdentityProviderUrl(String name, String replyURL) 
			 throws ParserConfigurationException, SAXException, IOException { 
		TrustedIssuer result = null;
		
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder documentBuilder = factory.newDocumentBuilder();
		Document document = documentBuilder.parse(getRepositoryStream());

		NodeList xmlTrustedIssuers = document.getFirstChild().getChildNodes();
		for (int i = 0; i < xmlTrustedIssuers.getLength(); i++) {
			Node xmlTrustedIssuer = xmlTrustedIssuers.item(i);
			if ((xmlTrustedIssuer instanceof Element) &&
					(name.equals(xmlTrustedIssuer.getAttributes().getNamedItem(Constants.REPOSITORY_NAME_ATTRIBUTE).getTextContent())))
				result = new TrustedIssuer(
						xmlTrustedIssuer.getAttributes().getNamedItem(Constants.REPOSITORY_NAME_ATTRIBUTE).getTextContent(), 
						xmlTrustedIssuer.getAttributes().getNamedItem(Constants.REPOSITORY_DISPLAY_NAME_ATTRIBUTE).getTextContent(), 
						xmlTrustedIssuer.getAttributes().getNamedItem(Constants.REPOSITORY_REALM_ATTRIBUTE).getTextContent(),
						replyURL);
		}
	 
		return result; 
	 }

	protected InputStream getRepositoryStream() {
		return TrustedIssuersRepository.class.getResourceAsStream(this.repositoryFileName);
	}
}
