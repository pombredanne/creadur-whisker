/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 *  to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.creadur.whisker.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;

public class TestDescriptorPrimaryOnly extends TestCase {

    License primaryLicense = new License(false, "This is the license text", Collections.<String> emptyList(), "example.org", "http://example.org", "Example License");
    Organisation primaryOrg = new Organisation("primary", "primary.org", "http://primary.org");
    Organisation thirdPartyOrg = new Organisation("third-party", "thirdparty.org", "http://thirdparty.org");
    String primaryNotice = "The primary notice.";
    Collection<WithinDirectory> contents = new ArrayList<WithinDirectory>();
    Map<String, License> licenses = new HashMap<String, License>();
    Map<String, String> notices = new HashMap<String, String>();
    Map<String, Organisation> organisations = new HashMap<String, Organisation>();
    
    Descriptor subject;
    
    protected void setUp() throws Exception {
        super.setUp();
        primaryLicense.storeIn(licenses);
        primaryOrg.storeIn(organisations);
        thirdPartyOrg.storeIn(organisations);
                       
        addDirectory(primaryLicense, primaryOrg, ".");
    }

    private void addDirectory(License license, final Organisation org,
            final String directoryName) {
        final WithinDirectory withinDirectory = buildDirectory(license, org,
                directoryName);
        contents.add(withinDirectory);
    }

    private WithinDirectory buildDirectory(License license,
            final Organisation org, final String directoryName) {
        Collection<ByOrganisation> byOrgs = new ArrayList<ByOrganisation>();
        Collection<Resource> resources = buildResources();
        byOrgs.add(new ByOrganisation(org, resources));
        
        Collection<WithLicense> withLicenses = new ArrayList<WithLicense>();
        String copyright = "Copyright Blah";
        Map<String, String> params = Collections.emptyMap();
        withLicenses.add(new WithLicense(license, copyright, params, byOrgs));
        
        Collection<ByOrganisation> publicDomain = Collections.emptyList();
        
        final WithinDirectory withinDirectory = new WithinDirectory(directoryName, withLicenses, publicDomain);
        return withinDirectory;
    }

    private Collection<Resource> buildResources() {
        String noticeId = "notice:id";
        notices.put(noticeId, "Some notice text");
        Collection<Resource> resources = new ArrayList<Resource>();
        String source = "";
        String name = "resource";
        resources.add(new Resource(name, noticeId, source));
        return resources;
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void testIsPrimaryOnlyWithThirdPartyResources() throws Exception {
        subject = 
                new Descriptor(primaryLicense, primaryOrg.getId(),  primaryNotice, 
                        licenses, notices, organisations, contents);
        addDirectory(primaryLicense, thirdPartyOrg, "lib");
        assertFalse("Work is not primary only when third party resources exist.", subject.isPrimaryOnly());        
    }

    public void testIsPrimaryOnlyWithoutThirdPartyResources() throws Exception {
        subject = 
                new Descriptor(primaryLicense, primaryOrg.getId(),  null, 
                        licenses, notices, organisations, contents);
        
        assertTrue("Work is primary only when no third party resources exist.", 
                subject.isPrimaryOnly());        
    }
    
    public void testIsPrimaryOnlyWithoutResources() throws Exception {
        subject = 
                new Descriptor(primaryLicense, primaryOrg.getId(),  null, 
                        licenses, notices, organisations, contents);
        
        contents.clear();
        
        assertTrue("Work is primary only when no third party resources exist.", 
                subject.isPrimaryOnly());        
    }

}