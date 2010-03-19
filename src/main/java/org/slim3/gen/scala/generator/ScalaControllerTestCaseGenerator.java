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

import org.slim3.gen.desc.ControllerDesc;
import org.slim3.gen.generator.Generator;
import org.slim3.gen.printer.Printer;

/**
 * Generates a controller test case scala file.
 *
 * @author yuroyoro
 * @since 3.0
 *
 */
public class ScalaControllerTestCaseGenerator implements Generator {

    static final String SpecsSpecification = "org.specs.Specification";
    static final String SpecsRunner = "org.specs.runner";
    static final String TesterPackage = "org.slim3.tester.";
    static final String ControllerTester = "ControllerTester";

    /** the controller description */
    protected final ControllerDesc controllerDesc;

    /**
     * Creates a new {@link ScalaControllerTestCaseGenerator}.
     *
     * @param controllerDesc
     *            the controller description
     */
    public ScalaControllerTestCaseGenerator(ControllerDesc controllerDesc) {
        if (controllerDesc == null) {
            throw new NullPointerException(
                "The controllerDesc parameter is null.");
        }
        this.controllerDesc = controllerDesc;
    }

    public void generate(Printer p) {
        if (controllerDesc.getPackageName().length() != 0) {
            p.println("package %s", controllerDesc.getPackageName());
            p.println();
        }

        p.println("import %s", SpecsSpecification );
        p.println("import %s._", SpecsRunner );
        p.println("import %s%s", TesterPackage, ControllerTester );
        p.println();
        p.print(
            "object %sSpec extends %s",
            controllerDesc.getSimpleName(),
            SpecsSpecification );
        p.println(" {");
        p.println();
        p.println("  val tester = new %s( classOf[%s] )",
            ControllerTester,
            controllerDesc.getSimpleName() );
        p.println();
        p.println("  \"%s\" should {",controllerDesc.getSimpleName() );
        p.println("    doBefore{ tester.setUp;tester.start(\"%s\")}", controllerDesc.getPath());
        p.println();
        p.println("    \"not null\" >> {");
        p.println("      val controller = tester.getController[%s]" , controllerDesc.getSimpleName());
        p.println("      controller must notBeNull");
        p.println("    }");
        p.println("    \"not redirect\" >> {");
        p.println("      tester.isRedirect must beFalse");
        p.println("    }");

        if (controllerDesc.isUseView()) {
            p.println("    \"get destination path is %s\" >> {",controllerDesc.getViewName());
            p.println("      tester.getDestinationPath must_==/ \"%s\"", controllerDesc.getViewName() );
            p.println("    }");
        } else {
            p.println("    \"get destination path is null\" >> {" );
            p.println("      tester.getDestinationPath must beNull" );
            p.println("    }");
        }
        p.println();
        p.println("    doAfter{ tester.tearDown}");
        p.println("  }");
        p.println("}");

        p.println("class %sSpecTest extends JUnit4( %sSpec )", controllerDesc.getSimpleName() , controllerDesc.getSimpleName());
    }
}
