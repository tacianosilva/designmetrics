package br.edu.ufcg.splab.designmetrics;

import java.util.Set;

import org.designwizard.api.DesignWizard;
import org.designwizard.design.ClassNode;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import br.edu.ufcg.splab.designmetrics.metrics.EfferentCouplingMetric;

/**
 * Test class with checking the coupling calculation between classes.
 * @author Taciano Morais Silva - tacianosilva@gmail.com
 */
@Test
public class EfferentCouplingBetweenClassTest {

    private SoftAssert softAssert;
    private DesignWizard designWizard;
    private EfferentCouplingMetric ceMetric;

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
        ceMetric = new EfferentCouplingMetric(designWizard);

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

    public void testNull() {
        softAssert.assertEquals(ceMetric.calculate(null, null), new Integer(0), "\n <null, null>: ");
        softAssert.assertEquals(ceMetric.calculate(classEmpty, null), new Integer(0), "\n <object, null>: ");
        softAssert.assertEquals(ceMetric.calculate(null, classEmpty), new Integer(0), "\n <null, object>: ");
        softAssert.assertAll();
    }

    public void testEntityNotExist() {
        ClassNode classNotExist = new ClassNode("br.edu.ufcg.splab.designmetrics.mocks.ClassNotExist");
        softAssert.assertEquals(ceMetric.calculate(classNotExist, classEmpty), new Integer(0), "\n <notexist, class>: ");
        softAssert.assertEquals(ceMetric.calculate(classEmpty, classNotExist), new Integer(0), "\n <class, notexist>: ");

        ClassNode classNotInDesign = new ClassNode("java.util.Scanner");
        softAssert.assertEquals(ceMetric.calculate(classNotInDesign, classEmpty), new Integer(0), "\n <notInDesign, class>: ");
        softAssert.assertEquals(ceMetric.calculate(classEmpty, classNotInDesign), new Integer(0), "\n <class, notInDesign>: ");

        softAssert.assertAll();
    }

    /**
     */
    public void testCboZero() {
        Set<ClassNode> relatedEmpty = ceMetric.getRelatedEntities(classEmpty);
        Set<ClassNode> relatedOne = ceMetric.getRelatedEntities(classOne);

        softAssert.assertEquals(ceMetric.calculate(classEmpty, classEmpty), new Integer(0), "\n <empty, empty>: ");
        softAssert.assertEquals(ceMetric.calculate(classOne, classOne), new Integer(0), "\n <one, one>: ");
        softAssert.assertEquals(ceMetric.calculate(classTwo, classTwo), new Integer(0), "\n <two, two>: ");
        softAssert.assertEquals(ceMetric.calculate(classThree, classThree), new Integer(0), "\n <three, three>: ");
        softAssert.assertEquals(ceMetric.calculate(classFour, classFour), new Integer(0), "\n <four, four>: ");
        softAssert.assertEquals(ceMetric.calculate(classFive, classFive), new Integer(0), "\n <five, five>: ");
        softAssert.assertEquals(ceMetric.calculate(classSix, classSix), new Integer(0), "\n <six, six>: ");
        softAssert.assertEquals(ceMetric.calculate(classSeven, classSeven), new Integer(0), "\n <one, seven>: ");

        softAssert.assertEquals(relatedEmpty.size(), 0);
        softAssert.assertEquals(relatedOne.size(), 0);
        softAssert.assertAll();
    }

    public void testCboWithIntersection() {
        Set<ClassNode> relatedOne = ceMetric.getRelatedEntities(classOne);
        Set<ClassNode> relatedTwo = ceMetric.getRelatedEntities(classTwo);
        Set<ClassNode> relatedThree = ceMetric.getRelatedEntities(classThree);
        Set<ClassNode> relatedFour = ceMetric.getRelatedEntities(classFour);
        Set<ClassNode> relatedFive = ceMetric.getRelatedEntities(classFive);

        softAssert.assertEquals(ceMetric.calculate(classOne, classTwo), new Integer(1), "\n <one, two>: ");
        softAssert.assertEquals(ceMetric.calculate(classOne, classThree), new Integer(0), "\n <one, three>: ");
        softAssert.assertEquals(ceMetric.calculate(classOne, classFour), new Integer(0), "\n <one, four>: ");
        softAssert.assertEquals(ceMetric.calculate(classOne, classFive), new Integer(0), "\n <one, five>: ");
        softAssert.assertEquals(ceMetric.calculate(classTwo, classOne), new Integer(1), "\n <two, one>: ");
        softAssert.assertEquals(ceMetric.calculate(classTwo, classThree), new Integer(1), "\n <two, three>: ");
        softAssert.assertEquals(ceMetric.calculate(classTwo, classFour), new Integer(1), "\n <two, four>: ");
        softAssert.assertEquals(ceMetric.calculate(classTwo, classFive), new Integer(1), "\n <two, five>: ");
        softAssert.assertEquals(ceMetric.calculate(classThree, classOne), new Integer(0), "\n <three, one>: ");
        softAssert.assertEquals(ceMetric.calculate(classThree, classTwo), new Integer(1), "\n <three, two>: ");
        softAssert.assertEquals(ceMetric.calculate(classThree, classFour), new Integer(2), "\n <three, four>: ");
        softAssert.assertEquals(ceMetric.calculate(classThree, classFive), new Integer(2), "\n <three, five>: ");
        softAssert.assertEquals(ceMetric.calculate(classFour, classOne), new Integer(0), "\n <four, one>: ");
        softAssert.assertEquals(ceMetric.calculate(classFour, classTwo), new Integer(1), "\n <four, two>: ");
        softAssert.assertEquals(ceMetric.calculate(classFour, classThree), new Integer(2), "\n <four, three>: ");
        softAssert.assertEquals(ceMetric.calculate(classFour, classFive), new Integer(2), "\n <four, five>: ");
        softAssert.assertEquals(ceMetric.calculate(classFive, classOne), new Integer(0), "\n <five, one>: ");
        softAssert.assertEquals(ceMetric.calculate(classFive, classTwo), new Integer(1), "\n <five, two>: ");
        softAssert.assertEquals(ceMetric.calculate(classFive, classThree), new Integer(2), "\n <five, three>: ");
        softAssert.assertEquals(ceMetric.calculate(classFive, classFour), new Integer(2), "\n <five, four>: ");

        softAssert.assertEquals(relatedOne.size(), 0, "relatedOne: ");
        softAssert.assertEquals(relatedTwo.size(), 1, "relatedTwo: ");
        softAssert.assertEquals(relatedThree.size(), 1, "relatedThree: ");
        softAssert.assertEquals(relatedFour.size(), 2, "relatedFour: ");
        softAssert.assertEquals(relatedFive.size(), 2, "relatedFive: ");
        softAssert.assertAll();
    }

    public void testCbo() {
        Set<ClassNode> relatedSix = ceMetric.getRelatedEntities(classSix);
        Set<ClassNode> relatedSeven = ceMetric.getRelatedEntities(classSeven);

        softAssert.assertEquals(ceMetric.calculate(classSix, classSeven), new Integer(2), "\n <six, seven>: ");
        softAssert.assertEquals(ceMetric.calculate(classSeven, classSix), new Integer(2), "\n <seven, six>: ");

        softAssert.assertEquals(relatedSix.size(), 1);
        softAssert.assertEquals(relatedSeven.size(), 1);
        softAssert.assertAll();
    }
}
