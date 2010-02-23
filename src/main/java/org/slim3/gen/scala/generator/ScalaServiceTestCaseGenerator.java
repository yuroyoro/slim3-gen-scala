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

import org.slim3.gen.desc.ServiceDesc;
import org.slim3.gen.generator.Generator;
import org.slim3.gen.printer.Printer;

/**
 * Generates a service test case scala file.
 * 
 * @author yuroyoro
 * 
 */
public class ScalaServiceTestCaseGenerator implements Generator {
    
    static final String SpecsSpecification = "org.specs.Specification";
    static final String SpecsRunner = "org.specs.runner";
    static final String TesterPackage = "org.slim3.tester.";
    static final String AppEngineTester = "AppEngineTester";

    /** the service description */
    protected final ServiceDesc serviceDesc;

    /**
     * Creates a new {@link ScalaServiceTestCaseGenerator}.
     * 
     * @param serviceDesc
     *            the service description
     */
    public ScalaServiceTestCaseGenerator(ServiceDesc serviceDesc) {
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
        p.println("import %s", SpecsSpecification );
        p.println("import %s._", SpecsRunner );
        p.println("import %s%s", TesterPackage, AppEngineTester );
        p.println();
        p.print(
            "object %sSpec extends %s",
            serviceDesc.getSimpleName(),
            SpecsSpecification );
        p.println(" {");
        p.println("  val tester = new %s", AppEngineTester );
        p.println("  val service = new %s", serviceDesc.getSimpleName() );
        p.println();
        p.println("  \"%s\" should {",serviceDesc.getSimpleName() );
        p.println("    doBefore{ tester.setUp}");
        p.println();
        p.println("    \"not null\" >> {");
        p.println("      service must notBeNull");
        p.println("    }");
        p.println();
        p.println("    doAfter{ tester.tearDown}");
        p.println("  }");
        p.println("}");
        
        p.println("class %sSpecTest extends JUnit4( %sSpec )", serviceDesc.getSimpleName() , serviceDesc.getSimpleName());
    }
}