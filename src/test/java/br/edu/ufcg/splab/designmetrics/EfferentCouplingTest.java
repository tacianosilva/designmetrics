package br.edu.ufcg.splab.designmetrics;

import java.util.Set;

import org.designwizard.api.DesignWizard;
import org.designwizard.design.ClassNode;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import br.edu.ufcg.splab.designmetrics.metrics.Coupling;
import br.edu.ufcg.splab.designmetrics.metrics.EfferentCouplingMetric;

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
 * br.edu.ufcg.splab.designmetrics.mocks.ClassA   1   1   0   0   2    0   2   1
 * br.edu.ufcg.splab.designmetrics.mocks.ClassB   1   1   0   1   2    0   1   1
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
 * br.edu.ufcg.splab.designmetrics.mocks.ClassM   3   1   0   3   6    3   0   3
 * br.edu.ufcg.splab.designmetrics.mocks.ClassN   1   0   0   1   2    0   0   1
 * br.edu.ufcg.splab.designmetrics.mocks.ClassO   1   0   0   1   2    0   0   1
 *
 * @author Taciano Morais Silva - tacianosilva@gmail.com
 */
@Test
public class EfferentCouplingTest {

    private SoftAssert softAssert;
    private DesignWizard designWizard;
    private EfferentCouplingMetric ceMetric;
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
        ceMetric = new EfferentCouplingMetric(designWizard);
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
    }

    /**
     */
    public void testCeClassA() {
        Set<ClassNode> directRelatedEntities = ceMetric.getRelatedEntities(classA);

        softAssert.assertEquals(coupling.efferentCoupling(classA), new Integer(0), "\nCe: ");
        softAssert.assertEquals(directRelatedEntities.size(), 0);
        softAssert.assertAll();
    }

    public void testCeClassB() {
        Set<ClassNode> directRelatedEntities = ceMetric.getRelatedEntities(classB);
        softAssert.assertTrue(directRelatedEntities.contains(classA));
        softAssert.assertEquals(coupling.efferentCoupling(classB), new Integer(1), "\nCe: ");
        softAssert.assertAll();
    }

    public void testCeClassC() {
        Set<ClassNode> directRelatedEntities = ceMetric.getRelatedEntities(classC);
        softAssert.assertTrue(directRelatedEntities.contains(classA));
        softAssert.assertTrue(directRelatedEntities.contains(classB));
        softAssert.assertEquals(coupling.efferentCoupling(classC), new Integer(2), "\nCe: ");
        softAssert.assertAll();
    }

    public void testCeClassD() {
        Set<ClassNode> directRelatedEntities = ceMetric.getRelatedEntities(classD);
        softAssert.assertTrue(directRelatedEntities.contains(classC));
        softAssert.assertEquals(coupling.efferentCoupling(classD), new Integer(1), "\nCe: ");
        softAssert.assertAll();
    }

    public void testCeClassE() {
        Set<ClassNode> directRelatedEntities = ceMetric.getRelatedEntities(classE);
        softAssert.assertTrue(directRelatedEntities.contains(classC));
        softAssert.assertEquals(coupling.efferentCoupling(classE), new Integer(1), "\nCe: ");
        softAssert.assertAll();
    }

    public void testCeClassF() {
        Set<ClassNode> directRelatedEntities = ceMetric.getRelatedEntities(classF);
        softAssert.assertTrue(directRelatedEntities.contains(classD));
        softAssert.assertTrue(directRelatedEntities.contains(classE));
        softAssert.assertEquals(coupling.efferentCoupling(classF), new Integer(2), "\nCe: ");
        softAssert.assertAll();
    }

    public void testCeClassG() {
        Set<ClassNode> directRelatedEntities = ceMetric.getRelatedEntities(classG);
        softAssert.assertTrue(directRelatedEntities.contains(classF));
        softAssert.assertEquals(coupling.efferentCoupling(classG), new Integer(1), "\nCe: ");
        softAssert.assertAll();
    }

    public void testCeClassH() {
        Set<ClassNode> directRelatedEntities = ceMetric.getRelatedEntities(classH);
        softAssert.assertTrue(directRelatedEntities.contains(classF), "\nClassF");
        softAssert.assertTrue(directRelatedEntities.contains(classD), "\nClassD");
        softAssert.assertEquals(coupling.efferentCoupling(classH), new Integer(2), "\nCe: ");
        softAssert.assertAll();
    }

    public void testCeClassI() {
        Set<ClassNode> directRelatedEntities = ceMetric.getRelatedEntities(classI);
        softAssert.assertTrue(directRelatedEntities.contains(classF), "\nClassF");
        softAssert.assertTrue(directRelatedEntities.contains(classD), "\nClassD");
        softAssert.assertTrue(directRelatedEntities.contains(classE), "\nClassE");
        softAssert.assertEquals(coupling.efferentCoupling(classI), new Integer(3), "\nCe: ");
        softAssert.assertAll();
    }

    public void testCeClassJ() {
        Set<ClassNode> directRelatedEntities = ceMetric.getRelatedEntities(classJ);
        softAssert.assertTrue(directRelatedEntities.contains(classF), "\nClassF");
        softAssert.assertEquals(coupling.efferentCoupling(classJ), new Integer(1), "\nCe: ");
        softAssert.assertAll();
    }

    public void testCeClassK() {
        Set<ClassNode> directRelatedEntities = ceMetric.getRelatedEntities(classK);
        softAssert.assertTrue(directRelatedEntities.contains(classF), "\nClassF");
        softAssert.assertEquals(coupling.efferentCoupling(classK), new Integer(2), "\nCe: ");
        softAssert.assertAll();
    }

    public void testCeClassL() {
        Set<ClassNode> directRelatedEntities = ceMetric.getRelatedEntities(classL);
        softAssert.assertTrue(directRelatedEntities.contains(classF), "\nClassF");
        softAssert.assertTrue(directRelatedEntities.contains(classD), "\nClassD");
        softAssert.assertTrue(directRelatedEntities.contains(classE), "\nClassE");
        softAssert.assertEquals(coupling.efferentCoupling(classL), new Integer(3), "\nCe: ");
        softAssert.assertAll();
    }

    public void testCeClassM() {
        Set<ClassNode> directRelatedEntities = ceMetric.getRelatedEntities(classM);
        softAssert.assertTrue(directRelatedEntities.contains(classF), "\nClassF");
        softAssert.assertTrue(directRelatedEntities.contains(classD), "\nClassD");
        softAssert.assertTrue(directRelatedEntities.contains(classL), "\nClassL");
        softAssert.assertEquals(coupling.efferentCoupling(classM), new Integer(3), "\nCe: ");
        softAssert.assertAll();
    }

    public void testCeClassN() {
        Set<ClassNode> directRelatedEntities = ceMetric.getRelatedEntities(classN);
        softAssert.assertTrue(directRelatedEntities.contains(classM), "\nClassM");
        softAssert.assertTrue(directRelatedEntities.contains(classF), "\nClassF");
        softAssert.assertTrue(directRelatedEntities.contains(classD), "\nClassD");
        softAssert.assertTrue(directRelatedEntities.contains(classL), "\nClassL");
        //TODO o ckjm retorns CBO = 1
        softAssert.assertEquals(coupling.efferentCoupling(classN), new Integer(4), "\nCe: ");
        softAssert.assertAll();
    }

    public void testCeClassO() {
        Set<ClassNode> directRelatedEntities = ceMetric.getRelatedEntities(classO);
        softAssert.assertTrue(directRelatedEntities.contains(classA), "\nClassA");
        softAssert.assertEquals(coupling.efferentCoupling(classO), new Integer(1), "\nCe: ");
        softAssert.assertAll();
    }

    @Test(enabled=false)
    public void testCeClassP() {
        Set<ClassNode> directRelatedEntities = ceMetric.getRelatedEntities(classP);
        softAssert.assertTrue(directRelatedEntities.contains(classA), "\nClassA");
        softAssert.assertTrue(directRelatedEntities.contains(classB), "\nClassB");
        softAssert.assertEquals(coupling.efferentCoupling(classP), new Integer(2), "\nCe: ");
        softAssert.assertAll();
    }
}
