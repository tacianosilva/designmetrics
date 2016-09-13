package br.edu.ufcg.splab.designmetrics;

import java.util.Set;

import org.designwizard.api.DesignWizard;
import org.designwizard.design.ClassNode;
import org.designwizard.design.PackageNode;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import br.edu.ufcg.splab.designmetrics.metrics.Coupling;

/**
 * Test class with checking the coupling calculation between classes.
 * @author Taciano Morais Silva - tacianosilva@gmail.com
 */
@Test
public class EfferentCouplingBetweenPackagesTest {

    private SoftAssert softAssert;
    private DesignWizard designWizard;
    private Coupling coupling;

    private PackageNode cbo1;
    private PackageNode cbo2;
    private PackageNode cbo3;
    private PackageNode cbo4;

    @BeforeClass
    public void setUp() throws Exception {
        // Design for all classes in the project.
        // Add here binary code or jar file of the project.
        designWizard = new DesignWizard("target/test-classes/br/edu/ufcg/splab/designmetrics/mocks/");
        coupling = new Coupling(designWizard);

        cbo1 = designWizard.getPackage("br.edu.ufcg.splab.designmetrics.mocks.cbo1");
        cbo2 = designWizard.getPackage("br.edu.ufcg.splab.designmetrics.mocks.cbo2");
        cbo3 = designWizard.getPackage("br.edu.ufcg.splab.designmetrics.mocks.cbo3");
        cbo4 = designWizard.getPackage("br.edu.ufcg.splab.designmetrics.mocks.cbo4");
    }

    @BeforeMethod
    public void startTest() {
         softAssert = new SoftAssert();
    }

    @AfterClass
    public void tearDown() throws Exception {
    }

    public void testNull() {
        PackageNode packageA = null;
        PackageNode packageB = null;

        softAssert.assertEquals(coupling.efferentCoupling(packageA, packageB), new Integer(0), "\n <null, null>: ");
        softAssert.assertEquals(coupling.efferentCoupling(cbo1, packageA), new Integer(0), "\n <object, null>: ");
        softAssert.assertEquals(coupling.efferentCoupling(packageA, cbo1), new Integer(0), "\n <null, object>: ");
        softAssert.assertAll();
    }

    public void testEntityNotExist() {
        PackageNode packageNotExist = new PackageNode("br.edu.ufcg.splab.designmetrics.mocks.packageNotExist");
        softAssert.assertEquals(coupling.efferentCoupling(packageNotExist, cbo1), new Integer(0), "\n <notexist, package>: ");
        softAssert.assertEquals(coupling.efferentCoupling(cbo1, packageNotExist), new Integer(0), "\n <package, notexist>: ");

        PackageNode packageNotInDesign = new PackageNode("java.util");
        softAssert.assertEquals(coupling.efferentCoupling(packageNotInDesign, cbo1), new Integer(0), "\n <notInDesign, package>: ");
        softAssert.assertEquals(coupling.efferentCoupling(cbo1, packageNotInDesign), new Integer(0), "\n <package, notInDesign>: ");

        softAssert.assertAll();
    }

    /**
     */
    public void testPackage() {
        Set<ClassNode> classes1 = cbo1.getAllClasses();
        Set<ClassNode> classes2 = cbo2.getAllClasses();
        Set<ClassNode> classes3 = cbo3.getAllClasses();
        Set<ClassNode> classes4 = cbo4.getAllClasses();

        softAssert.assertEquals(classes1.size(), new Integer(16), "\n CBO1 Qtd: ");
        softAssert.assertEquals(classes2.size(), new Integer(9), "\n CBO2 Qtd: ");
        softAssert.assertEquals(classes3.size(), new Integer(2), "\n CBO3 Qtd: ");
        softAssert.assertEquals(classes4.size(), new Integer(1), "\n CBO4 Qtd: ");

        softAssert.assertEquals(coupling.efferentCoupling(cbo1, cbo2), new Integer(0), "\n <cbo1, cbo2>: ");
        softAssert.assertEquals(coupling.efferentCoupling(cbo1, cbo3), new Integer(0), "\n <cbo1, cbo3>: ");
        softAssert.assertEquals(coupling.efferentCoupling(cbo1, cbo4), new Integer(0), "\n <cbo1, cbo4>: ");

        softAssert.assertEquals(coupling.efferentCoupling(cbo2, cbo1), new Integer(0), "\n <cbo2, cbo1>: ");
        softAssert.assertEquals(coupling.efferentCoupling(cbo2, cbo3), new Integer(2), "\n <cbo2, cbo3>: ");
        softAssert.assertEquals(coupling.efferentCoupling(cbo2, cbo4), new Integer(1), "\n <cbo2, cbo4>: ");

        softAssert.assertEquals(coupling.efferentCoupling(cbo3, cbo1), new Integer(0), "\n <cbo3, cbo1>: ");
        softAssert.assertEquals(coupling.efferentCoupling(cbo3, cbo2), new Integer(2), "\n <cbo3, cbo2>: ");
        softAssert.assertEquals(coupling.efferentCoupling(cbo3, cbo4), new Integer(2), "\n <cbo3, cbo4>: ");

        softAssert.assertEquals(coupling.efferentCoupling(cbo4, cbo1), new Integer(0), "\n <cbo4, cbo1>: ");
        softAssert.assertEquals(coupling.efferentCoupling(cbo4, cbo2), new Integer(2), "\n <cbo4, cbo2>: ");
        softAssert.assertEquals(coupling.efferentCoupling(cbo4, cbo3), new Integer(2), "\n <cbo4, cbo3>: ");

        softAssert.assertAll();
    }
}
