/*******************************************************************************
 * Copyright (c) 2014 Institute for Pervasive Computing, ETH Zurich and others.
 * <p/>
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * and Eclipse Distribution License v1.0 which accompany this distribution.
 * <p/>
 * The Eclipse Public License is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * and the Eclipse Distribution License is available at
 * http://www.eclipse.org/org/documents/edl-v10.html.
 * <p/>
 * Contributors:
 * Matthias Kovatsch - creator and main architect
 ******************************************************************************/
package com.ericsson.hack.knowyourlimit;

import org.eclipse.californium.core.CoapClient;
import org.eclipse.californium.core.CoapResponse;
import org.eclipse.californium.core.Utils;

import java.net.URI;
import java.net.URISyntaxException;


public class COAPClient {

    /*
     * Application entry point.
     *
     */
    public static String getSpeedLimit(String locationUri) {

        URI uri = null;

        try {
            uri = new URI(locationUri);
        } catch (URISyntaxException e) {
            System.err.println("Invalid URI: " + e.getMessage());
            System.exit(-1);
        }

        CoapClient client = new CoapClient(uri);

        CoapResponse response = client.get();

        if (response != null) {

            System.out.println(response.getCode());
            System.out.println(response.getOptions());
            System.out.println(response.getResponseText());

            System.out.println("\nADVANCED\n");
            // access advanced API with access to more details through .advanced()
            System.out.println(Utils.prettyPrint(response));

            return response.getResponseText();
        } else {
            System.out.println("No response received.");
        }
        return "";
    }
}
