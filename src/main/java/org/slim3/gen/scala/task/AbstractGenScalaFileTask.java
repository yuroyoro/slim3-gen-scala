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
package org.slim3.gen.scala.task;

import java.io.File;
import java.io.IOException;

import org.slim3.gen.Constants;
import org.slim3.gen.desc.ClassDesc;
import org.slim3.gen.generator.Generator;
import org.slim3.gen.printer.Printer;
import org.slim3.gen.task.AbstractGenFileTask;

/**
 * Represents an abstract task to generate a scala file.
 * 
 * @author yuroyoro
 * @since 3.0
 */
public abstract class AbstractGenScalaFileTask extends AbstractGenFileTask {

    /** the source directory */
    protected File srcDir;

    /** the test source directory */
    protected File testDir;

    /**
     * Sets the srcDir.
     * 
     * @param srcDir
     *            the srcDir to set
     */
    public void setSrcDir(File srcDir) {
        this.srcDir = srcDir;
    }

    /**
     * Sets the testDir.
     * 
     * @param testDir
     *            the testDir to set
     */
    public void setTestDir(File testDir) {
        this.testDir = testDir;
    }

    @Override
    protected void doExecute() throws Exception {
        super.doExecute();
        if (srcDir == null) {
            throw new IllegalStateException("The srcDir parameter is null.");
        }
        if (testDir == null) {
            throw new IllegalStateException("The testDir parameter is null.");
        }
    }

    /**
     * Creates a scala file.
     * 
     * @param classDesc
     *            the class description
     * @return a sacla file.
     */
    protected ScalaFile createScalaFile(ClassDesc classDesc) {
        return new ScalaFile(srcDir, classDesc);
    }

    /**
     * Creates a scala file of test case.
     * 
     * @param classDesc
     *            the class description
     * @return a scala file.
     */
    protected ScalaFile createTestCaseScalaFile(ClassDesc classDesc) {
        return new ScalaFile(testDir, classDesc, Constants.TEST_SUFFIX);
    }

    /**
     * Generates a scala file.
     * 
     * @param generator
     *            the generator
     * @param scalaFile
     *            the scala file to be generated
     * @throws IOException
     */
    protected void generateScalaFile(Generator generator, ScalaFile scalaFile)
            throws IOException {
        File file = scalaFile.getFile();
        String className = scalaFile.getClassName();
        if (file.exists()) {
            log( String.format(
                "Already exists. Generation Skipped. ({0}.scala:0)",
                className));
            return;
        }
        Printer printer = null;
        try {
            printer = createPrinter(file);
            generator.generate(printer);
        } finally {
            if (printer != null) {
                printer.close();
            }
        }
        log( String.format(
            "Generated. ({0}.scala:0)",
            className));
    }

}
