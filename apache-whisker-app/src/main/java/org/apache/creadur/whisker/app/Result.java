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
package org.apache.creadur.whisker.app;

import java.io.File;

/**
 * 
 */
public enum Result {
    LICENSE("LICENSE"), NOTICE("NOTICE"), 
    MISSING_LICENSE_REPORT("missing-licenses.txt"), 
    XML_TEMPLATE("manifest-template.xml"), 
    DIRECTORIES_REPORT("directories.txt");
    
    /** Conventional name for the result */
    private final String name;

    private Result(String name) {
        this.name = name;
    }

    /**
     * Gets the conventional name for this result.
     * @return not null
     */
    public String getName() {
        return name;
    }

    /**
     * Creates the conventional file for storage of this result.
     * @param directory not null
     * @return conventional file within directory
     */
    public File within(File directory) {
        return new File(directory, getName());
    }
}