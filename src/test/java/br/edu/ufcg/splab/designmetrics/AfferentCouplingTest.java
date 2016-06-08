package br.edu.ufcg.splab.designmetrics;

import java.io.IOException;
import java.util.Set;

import org.designwizard.api.DesignWizard;
import org.designwizard.design.ClassNode;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import br.edu.ufcg.splab.designmetrics.metrics.AfferentCouplingMetric;
import br.edu.ufcg.splab.designmetrics.metrics.Coupling;

/**
 * Test class with the verification of design rules recommended by the hibernate for persistent classes.
 * This Unit Test Example use TestNG Framework.
 *
 *  The program ckjm calculates Chidamber and Kemerer object-oriented metrics by processing the
 *  bytecode of compiled Java files. The program calculates for each class the following six metrics,
 *  and displays them on its standard output, following the class's name:
 *
 *  WMC: Weighted methods per class
 *  DIT: Depth of Inheritance Tree
 *  NOC: Number of Children
 *  CBO: Coupling between object classes
 *  RFC: Response for a Class
 *  LCOM: Lack of cohesion in methods
 *  Ca: Afferent coupling (not a CK metric)
 *  NPM: Number of Public Methods for a class (not a CK metric)
 *
 * <code>java -jar ~/dev/ckjm-1.9/build/ckjm-1.9.jar *.class</code>
 *
 *                                               WMC DIT NOC CBO RFC LCOM Ca  NPM
 * br.edu.ufcg.splab.designmetrics.mocks.ClassA   1   1   1   0   2    0   3   1
 * br.edu.ufcg.splab.designmetrics.mocks.ClassB   1   1   0   1   2    0   2   1
 * br.edu.ufcg.splab.designmetrics.mocks.ClassC   1   1   0   2   2    0   2   1
 * br.edu.ufcg.splab.designmetrics.mocks.ClassD   1   1   0   1   2    0   6   1
 * br.edu.ufcg.splab.designmetrics.mocks.ClassE   3   1   0   1   4    1   3   3
 * br.edu.ufcg.splab.designmetrics.mocks.ClassF  13   1   0   2  15   62   7  13
 * br.edu.ufcg.splab.designmetrics.mocks.ClassG   2   1   0   1   4    1   0   2
 * br.edu.ufcg.splab.designmetrics.mocks.ClassH   3   1   0   2   6    1   0   3
 * br.edu.ufcg.splab.designmetrics.mocks.ClassI   4   1   0   3  10    0   0   4
 * br.edu.ufcg.splab.designmetrics.mocks.ClassJ   2   1   0   1   4    1   0   2
 * br.edu.ufcg.splab.designmetrics.mocks.ClassK   2   1   0   2   4    1   0   2
 * br.edu.ufcg.splab.designmetrics.mocks.ClassL   3   1   0   3   9    4   1   4
 * br.edu.ufcg.splab.designmetrics.mocks.ClassM   3   1   0   3   6    3   1   3
 * br.edu.ufcg.splab.designmetrics.mocks.ClassN   1   0   0   1   2    0   0   1
 * br.edu.ufcg.splab.designmetrics.mocks.ClassO   1   0   0   1   2    0   0   1
 * br.edu.ufcg.splab.designmetrics.mocks.ClassP   4   1   0   1  10    6   0   4
 *
 * @author Taciano Morais Silva - tacianosilva@gmail.com
 */
@Test
public class AfferentCouplingTest {

    private SoftAssert softAssert;
    private DesignWizard designWizard;
    private AfferentCouplingMetric caMetric;
    private Coupling coupling;

    private ClassNode classA;
    private ClassNode classB;
    private ClassNode classC;
    private ClassNode classD;
    private ClassNode classE;
    private ClassNode classF;
    private ClassNode classG;
    private ClassNode classH;
    private ClassNode classI;
    private ClassNode classJ;
    private ClassNode classK;
    private ClassNode classL;
    private ClassNode classM;
    private ClassNode classN;
    private ClassNode classO;
    private ClassNode classP;

    @BeforeClass
    public void setUp() throws Exception {
        // Design for all classes in the project.
        // Add here binary code or jar file of the project.
        designWizard = new DesignWizard("target/test-classes/br/edu/ufcg/splab/designmetrics/mocks/");
        caMetric = new AfferentCouplingMetric(designWizard);
        coupling = new Coupling(designWizard);

        // Classe Vazia
        classA = designWizard.getClass("br.edu.ufcg.splab.designmetrics.mocks.ClassA");

        // Classe com um atributo da Classe A
        classB = designWizard.getClass("br.edu.ufcg.splab.designmetrics.mocks.ClassB");

        // Classe com dois atributos da Classe A e Classe B
        classC = designWizard.getClass("br.edu.ufcg.splab.designmetrics.mocks.ClassC");

        // Classe com dois atributos da Classe C
        classD = designWizard.getClass("br.edu.ufcg.splab.designmetrics.mocks.ClassD");

        // Classe com um atributo da Classe C e métodos get e set para o atributo
        classE = designWizard.getClass("br.edu.ufcg.splab.designmetrics.mocks.ClassE");

        // Classe com vários atributos da Classe D e Classe E com gets e sets. Além de atributos de tipos primitivos.
        classF = designWizard.getClass("br.edu.ufcg.splab.designmetrics.mocks.ClassF");

        // Classe com um atributo da Classe E e faz chamada a um método de tipo primitivo
        classG = designWizard.getClass("br.edu.ufcg.splab.designmetrics.mocks.ClassG");

        // Classe com um atributo da Classe F e faz chamada a dois método do tipo Classe D
        classH = designWizard.getClass("br.edu.ufcg.splab.designmetrics.mocks.ClassH");

        // Classe com um atributo da Classe F e faz chamada a três método do tipo Classe D e Classe E
        classI = designWizard.getClass("br.edu.ufcg.splab.designmetrics.mocks.ClassI");

        // Classe com um atributo da Classe F e faz chamada a um método com parâmetros do tipo Classe D
        classJ = designWizard.getClass("br.edu.ufcg.splab.designmetrics.mocks.ClassJ");

        // Classe com um atributo da Classe F e faz chamada a um método com parâmetros do tipo Classe D (variável local)
        classK = designWizard.getClass("br.edu.ufcg.splab.designmetrics.mocks.ClassK");

        // TODO Testar se é o parâmetro ou a variável local que configura o acoplamento
        classL = designWizard.getClass("br.edu.ufcg.splab.designmetrics.mocks.ClassL");

        classM = designWizard.getClass("br.edu.ufcg.splab.designmetrics.mocks.ClassM");

        // Herança da classe M com métodos e atributos
        classN = designWizard.getClass("br.edu.ufcg.splab.designmetrics.mocks.ClassN");

        // Herança da classe A sem métodos e sem atributos.
        classO = designWizard.getClass("br.edu.ufcg.splab.designmetrics.mocks.ClassO");

        classP = designWizard.getClass("br.edu.ufcg.splab.designmetrics.mocks.ClassP");
    }

    @BeforeMethod
    public void startTest() {
         softAssert = new SoftAssert();
    }

    @AfterClass
    public void tearDown() throws Exception {
        this.designWizard = null;
    }

    public void testCaNull() {
        softAssert.assertEquals(coupling.afferentCoupling(null), new Integer(0), "\nCa: ");
        softAssert.assertAll();
    }

    public void testCaEntityNotExist() {
        ClassNode classNotExist = new ClassNode("br.edu.ufcg.splab.designmetrics.mocks.ClassNotExist");
        softAssert.assertEquals(coupling.efferentCoupling(classNotExist), new Integer(0), "\nCa: ");

        ClassNode classNotInDesign = new ClassNode("java.util.Scanner");
        softAssert.assertEquals(coupling.afferentCoupling(classNotInDesign), new Integer(0), "\nCa: ");

        softAssert.assertAll();
    }

    public void testCaMetric() throws IOException {
        AfferentCouplingMetric ca = new AfferentCouplingMetric(null);

        softAssert.assertEquals(ca.calculate(classA), new Integer(0), "\nCa: ");

        softAssert.assertAll();
    }

    /**
     */
    public void testCaClassA() {
        Set<ClassNode> directDependentsEntities = caMetric.getDependentsEntities(classA);

        softAssert.assertTrue(directDependentsEntities.contains(classB), "B call A? ");
        softAssert.assertTrue(directDependentsEntities.contains(classC), "C call A? ");
        softAssert.assertTrue(directDependentsEntities.contains(classO), "O call A? ");
        softAssert.assertTrue(directDependentsEntities.contains(classP), "P call A? ");

        softAssert.assertEquals(coupling.afferentCoupling(classA), new Integer(4), "\nCa: ");
        softAssert.assertEquals(directDependentsEntities.size(), 4);
        softAssert.assertAll();
    }

    public void testCaClassB() {
        Set<ClassNode> directDependentsEntities = caMetric.getDependentsEntities(classB);
        softAssert.assertTrue(directDependentsEntities.contains(classC), "C call B? ");
        softAssert.assertTrue(directDependentsEntities.contains(classP), "P call B? ");
        softAssert.assertEquals(coupling.afferentCoupling(classB), new Integer(2), "\nCa: ");
        softAssert.assertAll();
    }

    public void testCaClassC() {
        Set<ClassNode> directDependentsEntities = caMetric.getDependentsEntities(classC);
        softAssert.assertTrue(directDependentsEntities.contains(classD), "D call C? ");
        softAssert.assertTrue(directDependentsEntities.contains(classE), "E call C? ");
        softAssert.assertEquals(coupling.afferentCoupling(classC), new Integer(2), "\nCa: ");
        softAssert.assertAll();
    }

    public void testCaClassD() {
        Set<ClassNode> directDependentsEntities = caMetric.getDependentsEntities(classD);

        softAssert.assertTrue(directDependentsEntities.contains(classF), "F call D? ");
        softAssert.assertTrue(directDependentsEntities.contains(classH), "H call D? ");
        softAssert.assertTrue(directDependentsEntities.contains(classK), "K call D? ");
        softAssert.assertTrue(directDependentsEntities.contains(classI), "I call D? ");
        softAssert.assertTrue(directDependentsEntities.contains(classK), "K call D? ");
        softAssert.assertTrue(directDependentsEntities.contains(classL), "L call D? ");

        softAssert.assertEquals(coupling.afferentCoupling(classD), new Integer(6), "\nCa: ");
        softAssert.assertAll();
    }

    public void testCaClassE() {
        Set<ClassNode> directDependentsEntities = caMetric.getDependentsEntities(classE);

        softAssert.assertTrue(directDependentsEntities.contains(classF), "F call E? ");
        softAssert.assertTrue(directDependentsEntities.contains(classI), "I call E? ");
        softAssert.assertTrue(directDependentsEntities.contains(classL), "L call E? ");
        softAssert.assertEquals(coupling.afferentCoupling(classE), new Integer(3), "\nCa: ");
        softAssert.assertAll();
    }

    public void testCaClassF() {
        Set<ClassNode> directDependentsEntities = caMetric.getDependentsEntities(classF);

        softAssert.assertTrue(directDependentsEntities.contains(classG), "G call E? ");
        softAssert.assertTrue(directDependentsEntities.contains(classH), "H call E? ");
        softAssert.assertTrue(directDependentsEntities.contains(classI), "I call E? ");
        softAssert.assertTrue(directDependentsEntities.contains(classJ), "J call E? ");
        softAssert.assertTrue(directDependentsEntities.contains(classK), "K call E? ");
        softAssert.assertTrue(directDependentsEntities.contains(classL), "L call E? ");
        softAssert.assertTrue(directDependentsEntities.contains(classM), "M call E? ");

        softAssert.assertEquals(coupling.afferentCoupling(classF), new Integer(7), "\nCa: ");
        softAssert.assertAll();
    }

    public void testCaClassG() {
        //Set<ClassNode> directDependentsEntities = caMetric.getDependentsEntities(classG);
        softAssert.assertEquals(coupling.afferentCoupling(classG), new Integer(0), "\nCa: ");
        softAssert.assertAll();
    }

    public void testCaClassH() {
        //Set<ClassNode> directDependentsEntities = caMetric.getDependentsEntities(classH);
        softAssert.assertEquals(coupling.afferentCoupling(classH), new Integer(0), "\nCa: ");
        softAssert.assertAll();
    }

    public void testCaClassI() {
        //Set<ClassNode> directDependentsEntities = caMetric.getDependentsEntities(classI);
        softAssert.assertEquals(coupling.afferentCoupling(classI), new Integer(0), "\nCa: ");
        softAssert.assertAll();
    }

    public void testCaClassJ() {
        //Set<ClassNode> directDependentsEntities = caMetric.getDependentsEntities(classJ);
        softAssert.assertEquals(coupling.afferentCoupling(classJ), new Integer(0), "\nCa: ");
        softAssert.assertAll();
    }

    public void testCaClassK() {
        //Set<ClassNode> directDependentsEntities = caMetric.getDependentsEntities(classK);

        softAssert.assertEquals(coupling.afferentCoupling(classK), new Integer(0), "\nCa: ");
        softAssert.assertAll();
    }

    public void testCaClassL() {
        Set<ClassNode> directDependentsEntities = caMetric.getDependentsEntities(classL);

        softAssert.assertTrue(directDependentsEntities.contains(classM), "M call L?");
        softAssert.assertEquals(coupling.afferentCoupling(classL), new Integer(1), "\nCa: ");
        softAssert.assertAll();
    }

    public void testCaClassM() {
        Set<ClassNode> directDependentsEntities = caMetric.getDependentsEntities(classM);

        softAssert.assertTrue(directDependentsEntities.contains(classN), "N call M");
        softAssert.assertEquals(coupling.afferentCoupling(classM), new Integer(1), "\nCa: ");
        softAssert.assertAll();
    }

    public void testCaClassN() {
        //Set<ClassNode> directDependentsEntities = caMetric.getDependentsEntities(classN);

        softAssert.assertEquals(coupling.afferentCoupling(classN), new Integer(0), "\nCa: ");
        softAssert.assertAll();
    }

    public void testCaClassO() {
        //Set<ClassNode> directDependentsEntities = caMetric.getDependentsEntities(classO);

        softAssert.assertEquals(coupling.afferentCoupling(classO), new Integer(0), "\nCa: ");
        softAssert.assertAll();
    }

    public void testCaClassP() {
        //Set<ClassNode> directDependentsEntities = caMetric.getDependentsEntities(classP);

        softAssert.assertEquals(coupling.afferentCoupling(classP), new Integer(0), "\nCa: ");
        softAssert.assertAll();
    }
}
