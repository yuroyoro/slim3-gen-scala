/*
 * Copyright 2004-2009 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package org.slim3.gen.scala.generator;

import org.slim3.gen.ClassConstants;
import org.slim3.gen.desc.ServiceDesc;
import org.slim3.gen.generator.Generator;
import org.slim3.gen.printer.Printer;

/**
 * Generates a service scala file.
 * 
 * @author yuroyoro
 * 
 */
public class ScalaServiceGenerator implements Generator {

    /** the service description */
    protected final ServiceDesc serviceDesc;

    /**
     * Creates a new {@link ScalaServiceGenerator}.
     * 
     * @param serviceDesc
     *            the service description
     */
    public ScalaServiceGenerator(ServiceDesc serviceDesc) {
        if (serviceDesc == null) {
            throw new NullPointerException("The serviceDesc parameter is null.");
        }
        this.serviceDesc = serviceDesc;
    }

    public void generate(Printer p) {
        if (serviceDesc.getPackageName().length() != 0) {
            p.println("package %s", serviceDesc.getPackageName());
            p.println();
        }
        if (!ClassConstants.Object.equals(serviceDesc.getSuperclassName())) {
            p.println("import %s", serviceDesc.getSuperclassName());
        }
        p.println();
        p.print("class %s", serviceDesc.getSimpleName());
        if (!ClassConstants.Object.equals(serviceDesc.getSuperclassName())) {
            p.print(" extends %s", serviceDesc.getSuperclassName());
        }
        p.println(" {");
        p.println();
        p.println("}");
    }
}