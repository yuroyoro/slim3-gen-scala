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

import org.slim3.gen.AnnotationConstants;
import org.slim3.gen.ClassConstants;
import org.slim3.gen.Constants;
import org.slim3.gen.desc.ControllerDesc;
import org.slim3.gen.generator.Generator;
import org.slim3.gen.printer.Printer;
import org.slim3.gen.util.ClassUtil;

/**
 * Generates a controller test case scala file.
 * 
 * @author yuroyoro 
 * @since 3.0
 * 
 */
public class ScalaControllerTestCaseGenerator implements Generator {

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
        if (!ClassConstants.Object.equals(controllerDesc
            .getTestCaseSuperclassName())) {
            p.println("import %s", controllerDesc.getTestCaseSuperclassName());
        }
        p.println("import %s", AnnotationConstants.Test);
        p.println("import %s._", ClassConstants.Assert);
        p.println("import %s._", ClassConstants.CoreMatchers);
        p.println();
        p.print(
            "class %s%s",
            controllerDesc.getSimpleName(),
            Constants.TEST_SUFFIX);
        if (!ClassConstants.Object.equals(controllerDesc
            .getTestCaseSuperclassName())) {
            p.print(" extends %s", ClassUtil.getSimpleName(controllerDesc
                .getTestCaseSuperclassName()));
        }
        p.println(" {");
        p.println();
        p.println("    @Test");
        p.println("    def run() = {");
        p.println("        tester.start(\"%s\")", controllerDesc.getPath());
        p.println(
            "        val controller = tester.getController[%s]",
            controllerDesc.getSimpleName());
        p.println("        assertThat(controller, is(notNullValue[%s]))",
            controllerDesc.getSimpleName());
        if (controllerDesc.isUseView()) {
            p.println("        assertThat(tester.isRedirect, is(false))");
            p.println(
                "        assertThat(tester.getDestinationPath, is(\"%s\"))",
                controllerDesc.getViewName());
        } else {
            p.println("        assertThat(tester.isRedirect, is(false))");
            p
                .println("        assertThat(tester.getDestinationPath, is(nullValue))");
        }
        p.println("    }");
        p.println("}");
    }
}
