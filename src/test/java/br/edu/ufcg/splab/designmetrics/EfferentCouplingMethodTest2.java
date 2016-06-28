package br.edu.ufcg.splab.designmetrics;

import org.designwizard.api.DesignWizard;
import org.designwizard.design.ClassNode;
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
public class EfferentCouplingMethodTest2 {

    private SoftAssert softAssert;
    private DesignWizard designWizard;
    private Coupling coupling;

    private ClassNode classEmpty;
    private ClassNode classOne;
    private ClassNode classTwo;
    private ClassNode classThree;
    private ClassNode classFour;
    private ClassNode classFive;
    private ClassNode classSix;
    private ClassNode classSeven;

    @BeforeClass
    public void setUp() throws Exception {
        // Design for all classes in the project.
        // Add here binary code or jar file of the project.
        designWizard = new DesignWizard("target/test-classes/br/edu/ufcg/splab/designmetrics/mocks/cbo2/");
        coupling = new Coupling(designWizard);

        // Classe Vazia
        classEmpty = designWizard.getClass("br.edu.ufcg.splab.designmetrics.mocks.cbo2.ClassEmpty");

        classOne = designWizard.getClass("br.edu.ufcg.splab.designmetrics.mocks.cbo2.ClassOne");

        classTwo = designWizard.getClass("br.edu.ufcg.splab.designmetrics.mocks.cbo2.ClassTwo");

        classThree = designWizard.getClass("br.edu.ufcg.splab.designmetrics.mocks.cbo2.ClassThree");

        classFour = designWizard.getClass("br.edu.ufcg.splab.designmetrics.mocks.cbo2.ClassFour");

        classFive = designWizard.getClass("br.edu.ufcg.splab.designmetrics.mocks.cbo2.ClassFive");

        classSix = designWizard.getClass("br.edu.ufcg.splab.designmetrics.mocks.cbo2.ClassSix");

        classSeven = designWizard.getClass("br.edu.ufcg.splab.designmetrics.mocks.cbo2.ClassSeven");
    }

    @BeforeMethod
    public void startTest() {
         softAssert = new SoftAssert();
    }

    @AfterClass
    public void tearDown() throws Exception {
    }

    /**
     */
    public void testCboZero() {

        softAssert.assertEquals(coupling.efferentCouplingMethodLevel(classEmpty, classEmpty), new Integer(0), "\n <empty, empty>: ");
        softAssert.assertEquals(coupling.efferentCouplingMethodLevel(classOne, classOne), new Integer(0), "\n <one, one>: ");
        softAssert.assertEquals(coupling.efferentCouplingMethodLevel(classTwo, classTwo), new Integer(0), "\n <two, two>: ");
        softAssert.assertEquals(coupling.efferentCouplingMethodLevel(classThree, classThree), new Integer(0), "\n <three, three>: ");
        softAssert.assertEquals(coupling.efferentCouplingMethodLevel(classFour, classFour), new Integer(0), "\n <four, four>: ");
        softAssert.assertEquals(coupling.efferentCouplingMethodLevel(classFive, classFive), new Integer(0), "\n <five, five>: ");
        softAssert.assertEquals(coupling.efferentCouplingMethodLevel(classSix, classSix), new Integer(0), "\n <six, six>: ");
        softAssert.assertEquals(coupling.efferentCouplingMethodLevel(classSeven, classSeven), new Integer(0), "\n <seven, seven>: ");

        softAssert.assertAll();
    }

    public void testCboOne() {
        softAssert.assertEquals(coupling.efferentCouplingMethodLevel(classOne, classTwo), new Integer(1), "\n <one, two>: ");
        softAssert.assertEquals(coupling.efferentCouplingMethodLevel(classOne, classThree), new Integer(0), "\n <one, three>: ");
        softAssert.assertEquals(coupling.efferentCouplingMethodLevel(classOne, classFour), new Integer(0), "\n <one, four>: ");
        softAssert.assertEquals(coupling.efferentCouplingMethodLevel(classOne, classFive), new Integer(0), "\n <one, five>: ");
        softAssert.assertEquals(coupling.efferentCouplingMethodLevel(classOne, classSix), new Integer(0), "\n <one, six>: ");
        softAssert.assertEquals(coupling.efferentCouplingMethodLevel(classOne, classSeven), new Integer(0), "\n <one, seven>: ");

        softAssert.assertAll();
    }

    public void testCboTwo() {
        softAssert.assertEquals(coupling.efferentCouplingMethodLevel(classTwo, classOne), new Integer(1), "\n <two, one>: ");
        softAssert.assertEquals(coupling.efferentCouplingMethodLevel(classTwo, classThree), new Integer(1), "\n <two, three>: ");
        softAssert.assertEquals(coupling.efferentCouplingMethodLevel(classTwo, classFour), new Integer(2), "\n <two, four>: ");
        softAssert.assertEquals(coupling.efferentCouplingMethodLevel(classTwo, classFive), new Integer(2), "\n <two, five>: ");
        softAssert.assertEquals(coupling.efferentCouplingMethodLevel(classTwo, classSix), new Integer(0), "\n <two, six>: ");
        softAssert.assertEquals(coupling.efferentCouplingMethodLevel(classTwo, classSeven), new Integer(0), "\n <two, seven>: ");

        softAssert.assertAll();
    }

    public void testCboThree() {
        softAssert.assertEquals(coupling.efferentCouplingMethodLevel(classThree, classOne), new Integer(0), "\n <three, one>: ");
        softAssert.assertEquals(coupling.efferentCouplingMethodLevel(classThree, classTwo), new Integer(1), "\n <three, two>: ");
        softAssert.assertEquals(coupling.efferentCouplingMethodLevel(classThree, classFour), new Integer(2), "\n <three, four>: ");
        softAssert.assertEquals(coupling.efferentCouplingMethodLevel(classThree, classFive), new Integer(2), "\n <three, five>: ");
        softAssert.assertEquals(coupling.efferentCouplingMethodLevel(classThree, classSix), new Integer(0), "\n <three, six>: ");
        softAssert.assertEquals(coupling.efferentCouplingMethodLevel(classThree, classSeven), new Integer(0), "\n <three, seven>: ");

        softAssert.assertAll();
    }

    public void testCboFour() {
        softAssert.assertEquals(coupling.efferentCouplingMethodLevel(classFour, classOne), new Integer(0), "\n <four, one>: ");
        softAssert.assertEquals(coupling.efferentCouplingMethodLevel(classFour, classTwo), new Integer(2), "\n <four, two>: ");
        softAssert.assertEquals(coupling.efferentCouplingMethodLevel(classFour, classThree), new Integer(2), "\n <four, three>: ");
        softAssert.assertEquals(coupling.efferentCouplingMethodLevel(classFour, classFive), new Integer(0), "\n <four, five>: ");
        softAssert.assertEquals(coupling.efferentCouplingMethodLevel(classFour, classSix), new Integer(0), "\n <four, six>: ");
        softAssert.assertEquals(coupling.efferentCouplingMethodLevel(classFour, classSeven), new Integer(0), "\n <four, seven>: ");

        softAssert.assertAll();
    }

    public void testCboFive() {
        softAssert.assertEquals(coupling.efferentCouplingMethodLevel(classFive, classOne), new Integer(0), "\n <five, one>: ");
        softAssert.assertEquals(coupling.efferentCouplingMethodLevel(classFive, classTwo), new Integer(2), "\n <five, two>: ");
        softAssert.assertEquals(coupling.efferentCouplingMethodLevel(classFive, classThree), new Integer(2), "\n <five, three>: ");
        softAssert.assertEquals(coupling.efferentCouplingMethodLevel(classFive, classFour), new Integer(0), "\n <five, four>: ");
        softAssert.assertEquals(coupling.efferentCouplingMethodLevel(classFive, classSix), new Integer(0), "\n <five, six>: ");
        softAssert.assertEquals(coupling.efferentCouplingMethodLevel(classFive, classSeven), new Integer(0), "\n <five, seven>: ");

        softAssert.assertAll();
    }

    public void testCboSix() {
        softAssert.assertEquals(coupling.efferentCouplingMethodLevel(classSix, classOne), new Integer(0), "\n <six, one>: ");
        softAssert.assertEquals(coupling.efferentCouplingMethodLevel(classSix, classTwo), new Integer(0), "\n <six, two>: ");
        softAssert.assertEquals(coupling.efferentCouplingMethodLevel(classSix, classThree), new Integer(0), "\n <six, three>: ");
        softAssert.assertEquals(coupling.efferentCouplingMethodLevel(classSix, classFour), new Integer(0), "\n <six, four>: ");
        softAssert.assertEquals(coupling.efferentCouplingMethodLevel(classSix, classFive), new Integer(0), "\n <six, five>: ");
        softAssert.assertEquals(coupling.efferentCouplingMethodLevel(classSix, classSeven), new Integer(4), "\n <six, seven>: ");

        softAssert.assertAll();
    }

    public void testCboSeven() {
        softAssert.assertEquals(coupling.efferentCouplingMethodLevel(classSeven, classOne), new Integer(0), "\n <seven, one>: ");
        softAssert.assertEquals(coupling.efferentCouplingMethodLevel(classSeven, classTwo), new Integer(0), "\n <seven, two>: ");
        softAssert.assertEquals(coupling.efferentCouplingMethodLevel(classSeven, classThree), new Integer(0), "\n <seven, three>: ");
        softAssert.assertEquals(coupling.efferentCouplingMethodLevel(classSeven, classFour), new Integer(0), "\n <seven, four>: ");
        softAssert.assertEquals(coupling.efferentCouplingMethodLevel(classSeven, classFive), new Integer(0), "\n <seven, five>: ");
        softAssert.assertEquals(coupling.efferentCouplingMethodLevel(classSeven, classSix), new Integer(4), "\n <seven, six>: ");

        softAssert.assertAll();
    }
}
