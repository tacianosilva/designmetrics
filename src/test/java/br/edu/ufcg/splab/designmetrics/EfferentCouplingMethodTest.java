package br.edu.ufcg.splab.designmetrics;

import java.io.IOException;

import org.designwizard.api.DesignWizard;
import org.designwizard.design.ClassNode;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

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
public class EfferentCouplingMethodTest {

    private SoftAssert softAssert;
    private DesignWizard designWizard;
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
        designWizard = new DesignWizard("target/test-classes/br/edu/ufcg/splab/designmetrics/mocks/cbo1/");
        coupling = new Coupling(designWizard);

        // Classe Vazia
        classA = designWizard.getClass("br.edu.ufcg.splab.designmetrics.mocks.cbo1.ClassA");

        // Classe com um atributo da Classe A
        classB = designWizard.getClass("br.edu.ufcg.splab.designmetrics.mocks.cbo1.ClassB");

        // Classe com dois atributos da Classe A e Classe B
        classC = designWizard.getClass("br.edu.ufcg.splab.designmetrics.mocks.cbo1.ClassC");

        // Classe com dois atributos da Classe C
        classD = designWizard.getClass("br.edu.ufcg.splab.designmetrics.mocks.cbo1.ClassD");

        // Classe com um atributo da Classe C e métodos get e set para o atributo
        classE = designWizard.getClass("br.edu.ufcg.splab.designmetrics.mocks.cbo1.ClassE");

        // Classe com vários atributos da Classe D e Classe E com gets e sets. Além de atributos de tipos primitivos.
        classF = designWizard.getClass("br.edu.ufcg.splab.designmetrics.mocks.cbo1.ClassF");

        // Classe com um atributo da Classe E e faz chamada a um método de tipo primitivo
        classG = designWizard.getClass("br.edu.ufcg.splab.designmetrics.mocks.cbo1.ClassG");

        // Classe com um atributo da Classe F e faz chamada a dois método do tipo Classe D
        classH = designWizard.getClass("br.edu.ufcg.splab.designmetrics.mocks.cbo1.ClassH");

        // Classe com um atributo da Classe F e faz chamada a três método do tipo Classe D e Classe E
        classI = designWizard.getClass("br.edu.ufcg.splab.designmetrics.mocks.cbo1.ClassI");

        // Classe com um atributo da Classe F e faz chamada a um método com parâmetros do tipo Classe D
        classJ = designWizard.getClass("br.edu.ufcg.splab.designmetrics.mocks.cbo1.ClassJ");

        // Classe com um atributo da Classe F e faz chamada a um método com parâmetros do tipo Classe D (variável local)
        classK = designWizard.getClass("br.edu.ufcg.splab.designmetrics.mocks.cbo1.ClassK");

        classL = designWizard.getClass("br.edu.ufcg.splab.designmetrics.mocks.cbo1.ClassL");

        classM = designWizard.getClass("br.edu.ufcg.splab.designmetrics.mocks.cbo1.ClassM");

        // Herança da classe M com métodos e atributos
        classN = designWizard.getClass("br.edu.ufcg.splab.designmetrics.mocks.cbo1.ClassN");

        // Herança da classe A sem métodos e sem atributos.
        classO = designWizard.getClass("br.edu.ufcg.splab.designmetrics.mocks.cbo1.ClassO");

        classP = designWizard.getClass("br.edu.ufcg.splab.designmetrics.mocks.cbo1.ClassP");
    }

    @BeforeMethod
    public void startTest() {
         softAssert = new SoftAssert();
    }

    @AfterClass
    public void tearDown() throws Exception {
    }

    public void testCeNull() {
        softAssert.assertEquals(coupling.efferentCouplingMethodLevel(null, null), new Integer(0), "\nCe: ");
        softAssert.assertEquals(coupling.efferentCouplingMethodLevel(classA, null), new Integer(0), "\nCe: ");
        softAssert.assertEquals(coupling.efferentCouplingMethodLevel(null, classA), new Integer(0), "\nCe: ");
        softAssert.assertAll();
    }

    public void testCeMetric() throws IOException {
        softAssert.assertEquals(coupling.efferentCouplingMethodLevel(classA, classA), new Integer(0), "\nCe: ");

        softAssert.assertAll();
    }

    /**
     */
    public void testCeClassA() {
        softAssert.assertEquals(coupling.efferentCouplingMethodLevel(classA, classB), new Integer(1), "\nCe: A <--> B?");
        softAssert.assertEquals(coupling.efferentCouplingMethodLevel(classA, classC), new Integer(1), "\nCe: A <--> C?");
        softAssert.assertEquals(coupling.efferentCouplingMethodLevel(classA, classD), new Integer(0), "\nCe: A <--> D?");
        softAssert.assertEquals(coupling.efferentCouplingMethodLevel(classA, classE), new Integer(0), "\nCe: A <--> E?");
        softAssert.assertEquals(coupling.efferentCouplingMethodLevel(classA, classF), new Integer(0), "\nCe: A <--> F?");
        softAssert.assertEquals(coupling.efferentCouplingMethodLevel(classA, classG), new Integer(0), "\nCe: A <--> G?");
        softAssert.assertEquals(coupling.efferentCouplingMethodLevel(classA, classH), new Integer(0), "\nCe: A <--> H?");
        softAssert.assertEquals(coupling.efferentCouplingMethodLevel(classA, classI), new Integer(0), "\nCe: A <--> I?");
        softAssert.assertEquals(coupling.efferentCouplingMethodLevel(classA, classJ), new Integer(0), "\nCe: A <--> J?");
        softAssert.assertEquals(coupling.efferentCouplingMethodLevel(classA, classK), new Integer(0), "\nCe: A <--> K?");
        softAssert.assertEquals(coupling.efferentCouplingMethodLevel(classA, classL), new Integer(0), "\nCe: A <--> L?");
        softAssert.assertEquals(coupling.efferentCouplingMethodLevel(classA, classM), new Integer(0), "\nCe: A <--> M?");
        softAssert.assertEquals(coupling.efferentCouplingMethodLevel(classA, classN), new Integer(0), "\nCe: A <--> N?");
        // ClassO extends ClassA (call method ClassA.init()
        softAssert.assertEquals(coupling.efferentCouplingMethodLevel(classA, classO), new Integer(1), "\nCe: A <--> O?");
        softAssert.assertEquals(coupling.efferentCouplingMethodLevel(classA, classP), new Integer(0), "\nCe: A <--> P?");

        softAssert.assertAll();
    }

    public void testCeClassB() {
        softAssert.assertEquals(coupling.efferentCouplingMethodLevel(classB, classA), new Integer(1), "\nCe: B <--> A?");
        softAssert.assertEquals(coupling.efferentCouplingMethodLevel(classB, classC), new Integer(1), "\nCe: B <--> C?");
        softAssert.assertEquals(coupling.efferentCouplingMethodLevel(classB, classD), new Integer(0), "\nCe: B <--> D?");
        softAssert.assertEquals(coupling.efferentCouplingMethodLevel(classB, classE), new Integer(0), "\nCe: B <--> E?");
        softAssert.assertEquals(coupling.efferentCouplingMethodLevel(classB, classF), new Integer(0), "\nCe: B <--> F?");
        softAssert.assertEquals(coupling.efferentCouplingMethodLevel(classB, classG), new Integer(0), "\nCe: B <--> G?");
        softAssert.assertEquals(coupling.efferentCouplingMethodLevel(classB, classH), new Integer(0), "\nCe: B <--> H?");
        softAssert.assertEquals(coupling.efferentCouplingMethodLevel(classB, classI), new Integer(0), "\nCe: B <--> I?");
        softAssert.assertEquals(coupling.efferentCouplingMethodLevel(classB, classJ), new Integer(0), "\nCe: B <--> J?");
        softAssert.assertEquals(coupling.efferentCouplingMethodLevel(classB, classK), new Integer(0), "\nCe: B <--> K?");
        softAssert.assertEquals(coupling.efferentCouplingMethodLevel(classB, classL), new Integer(0), "\nCe: B <--> L?");
        softAssert.assertEquals(coupling.efferentCouplingMethodLevel(classB, classM), new Integer(0), "\nCe: B <--> M?");
        softAssert.assertEquals(coupling.efferentCouplingMethodLevel(classB, classN), new Integer(0), "\nCe: B <--> N?");
        softAssert.assertEquals(coupling.efferentCouplingMethodLevel(classB, classO), new Integer(0), "\nCe: B <--> O?");
        softAssert.assertEquals(coupling.efferentCouplingMethodLevel(classB, classP), new Integer(0), "\nCe: B <--> P?");

        softAssert.assertAll();
    }

    public void testCeClassC() {
        softAssert.assertEquals(coupling.efferentCouplingMethodLevel(classC, classA), new Integer(1), "\nCe: C <--> A?");
        softAssert.assertEquals(coupling.efferentCouplingMethodLevel(classC, classB), new Integer(1), "\nCe: C <--> B?");
        softAssert.assertEquals(coupling.efferentCouplingMethodLevel(classC, classD), new Integer(1), "\nCe: C <--> D?");
        softAssert.assertEquals(coupling.efferentCouplingMethodLevel(classC, classE), new Integer(1), "\nCe: C <--> E?");
        softAssert.assertEquals(coupling.efferentCouplingMethodLevel(classC, classF), new Integer(0), "\nCe: C <--> F?");
        softAssert.assertEquals(coupling.efferentCouplingMethodLevel(classC, classG), new Integer(0), "\nCe: C <--> G?");
        softAssert.assertEquals(coupling.efferentCouplingMethodLevel(classC, classH), new Integer(0), "\nCe: C <--> H?");
        softAssert.assertEquals(coupling.efferentCouplingMethodLevel(classC, classI), new Integer(0), "\nCe: C <--> I?");
        softAssert.assertEquals(coupling.efferentCouplingMethodLevel(classC, classJ), new Integer(0), "\nCe: C <--> J?");
        softAssert.assertEquals(coupling.efferentCouplingMethodLevel(classC, classK), new Integer(0), "\nCe: C <--> K?");
        softAssert.assertEquals(coupling.efferentCouplingMethodLevel(classC, classL), new Integer(0), "\nCe: C <--> L?");
        softAssert.assertEquals(coupling.efferentCouplingMethodLevel(classC, classM), new Integer(0), "\nCe: C <--> M?");
        softAssert.assertEquals(coupling.efferentCouplingMethodLevel(classC, classN), new Integer(0), "\nCe: C <--> N?");
        softAssert.assertEquals(coupling.efferentCouplingMethodLevel(classC, classO), new Integer(0), "\nCe: C <--> O?");
        softAssert.assertEquals(coupling.efferentCouplingMethodLevel(classC, classP), new Integer(0), "\nCe: C <--> P?");

        softAssert.assertAll();
    }

    public void testCeClassD() {
        softAssert.assertEquals(coupling.efferentCouplingMethodLevel(classD, classA), new Integer(0), "\nCe: D <--> A?");
        softAssert.assertEquals(coupling.efferentCouplingMethodLevel(classD, classB), new Integer(0), "\nCe: D <--> B?");
        softAssert.assertEquals(coupling.efferentCouplingMethodLevel(classD, classC), new Integer(1), "\nCe: D <--> C?");
        softAssert.assertEquals(coupling.efferentCouplingMethodLevel(classD, classE), new Integer(0), "\nCe: D <--> E?");
        softAssert.assertEquals(coupling.efferentCouplingMethodLevel(classD, classF), new Integer(1), "\nCe: D <--> F?");
        softAssert.assertEquals(coupling.efferentCouplingMethodLevel(classD, classG), new Integer(0), "\nCe: D <--> G?");
        softAssert.assertEquals(coupling.efferentCouplingMethodLevel(classD, classH), new Integer(0), "\nCe: D <--> H?");
        softAssert.assertEquals(coupling.efferentCouplingMethodLevel(classD, classI), new Integer(0), "\nCe: D <--> I?");
        softAssert.assertEquals(coupling.efferentCouplingMethodLevel(classD, classJ), new Integer(0), "\nCe: D <--> J?");
        softAssert.assertEquals(coupling.efferentCouplingMethodLevel(classD, classK), new Integer(0), "\nCe: D <--> K?");
        softAssert.assertEquals(coupling.efferentCouplingMethodLevel(classD, classL), new Integer(1), "\nCe: D <--> L?");
        softAssert.assertEquals(coupling.efferentCouplingMethodLevel(classD, classM), new Integer(0), "\nCe: D <--> M?");
        softAssert.assertEquals(coupling.efferentCouplingMethodLevel(classD, classN), new Integer(0), "\nCe: D <--> N?");
        softAssert.assertEquals(coupling.efferentCouplingMethodLevel(classD, classO), new Integer(0), "\nCe: D <--> O?");
        softAssert.assertEquals(coupling.efferentCouplingMethodLevel(classD, classP), new Integer(0), "\nCe: D <--> P?");

        softAssert.assertAll();
    }

    public void testCeClassE() {
        softAssert.assertEquals(coupling.efferentCouplingMethodLevel(classE, classA), new Integer(0), "\nCe: E <--> A?");
        softAssert.assertEquals(coupling.efferentCouplingMethodLevel(classE, classB), new Integer(0), "\nCe: E <--> B?");
        softAssert.assertEquals(coupling.efferentCouplingMethodLevel(classE, classC), new Integer(1), "\nCe: E <--> C?");
        softAssert.assertEquals(coupling.efferentCouplingMethodLevel(classE, classD), new Integer(0), "\nCe: E <--> D?");
        softAssert.assertEquals(coupling.efferentCouplingMethodLevel(classE, classF), new Integer(1), "\nCe: E <--> F?");
        softAssert.assertEquals(coupling.efferentCouplingMethodLevel(classE, classG), new Integer(0), "\nCe: E <--> G?");
        softAssert.assertEquals(coupling.efferentCouplingMethodLevel(classE, classH), new Integer(0), "\nCe: E <--> H?");
        softAssert.assertEquals(coupling.efferentCouplingMethodLevel(classE, classI), new Integer(0), "\nCe: E <--> I?");
        softAssert.assertEquals(coupling.efferentCouplingMethodLevel(classE, classJ), new Integer(0), "\nCe: E <--> J?");
        softAssert.assertEquals(coupling.efferentCouplingMethodLevel(classE, classK), new Integer(0), "\nCe: E <--> K?");
        softAssert.assertEquals(coupling.efferentCouplingMethodLevel(classE, classL), new Integer(1), "\nCe: E <--> L?");
        softAssert.assertEquals(coupling.efferentCouplingMethodLevel(classE, classM), new Integer(0), "\nCe: E <--> M?");
        softAssert.assertEquals(coupling.efferentCouplingMethodLevel(classE, classN), new Integer(0), "\nCe: E <--> N?");
        softAssert.assertEquals(coupling.efferentCouplingMethodLevel(classE, classO), new Integer(0), "\nCe: E <--> O?");
        softAssert.assertEquals(coupling.efferentCouplingMethodLevel(classE, classP), new Integer(0), "\nCe: E <--> P?");

        softAssert.assertAll();
    }

    public void testCeClassF() {
        softAssert.assertEquals(coupling.efferentCouplingMethodLevel(classF, classA), new Integer(0), "\nCe: F <--> A?");
        softAssert.assertEquals(coupling.efferentCouplingMethodLevel(classF, classB), new Integer(0), "\nCe: F <--> B?");
        softAssert.assertEquals(coupling.efferentCouplingMethodLevel(classF, classC), new Integer(0), "\nCe: F <--> C?");
        softAssert.assertEquals(coupling.efferentCouplingMethodLevel(classF, classD), new Integer(1), "\nCe: F <--> D?");
        softAssert.assertEquals(coupling.efferentCouplingMethodLevel(classF, classE), new Integer(1), "\nCe: F <--> E?");
        softAssert.assertEquals(coupling.efferentCouplingMethodLevel(classF, classG), new Integer(2), "\nCe: F <--> G?");
        softAssert.assertEquals(coupling.efferentCouplingMethodLevel(classF, classH), new Integer(3), "\nCe: F <--> H?");
        softAssert.assertEquals(coupling.efferentCouplingMethodLevel(classF, classI), new Integer(5), "\nCe: F <--> I?");
        softAssert.assertEquals(coupling.efferentCouplingMethodLevel(classF, classJ), new Integer(2), "\nCe: F <--> J?");
        softAssert.assertEquals(coupling.efferentCouplingMethodLevel(classF, classK), new Integer(2), "\nCe: F <--> K?");
        softAssert.assertEquals(coupling.efferentCouplingMethodLevel(classF, classL), new Integer(3), "\nCe: F <--> L?");
        softAssert.assertEquals(coupling.efferentCouplingMethodLevel(classF, classM), new Integer(2), "\nCe: F <--> M?");
        softAssert.assertEquals(coupling.efferentCouplingMethodLevel(classF, classN), new Integer(2), "\nCe: F <--> N?");
        softAssert.assertEquals(coupling.efferentCouplingMethodLevel(classF, classO), new Integer(0), "\nCe: F <--> O?");
        softAssert.assertEquals(coupling.efferentCouplingMethodLevel(classF, classP), new Integer(0), "\nCe: F <--> P?");

        softAssert.assertAll();
    }

    public void testCeClassG() {
        softAssert.assertEquals(coupling.efferentCouplingMethodLevel(classG, classA), new Integer(0), "\nCe: G <--> A?");
        softAssert.assertEquals(coupling.efferentCouplingMethodLevel(classG, classB), new Integer(0), "\nCe: G <--> B?");
        softAssert.assertEquals(coupling.efferentCouplingMethodLevel(classG, classC), new Integer(0), "\nCe: G <--> C?");
        softAssert.assertEquals(coupling.efferentCouplingMethodLevel(classG, classD), new Integer(0), "\nCe: G <--> D?");
        softAssert.assertEquals(coupling.efferentCouplingMethodLevel(classG, classE), new Integer(0), "\nCe: G <--> E?");
        softAssert.assertEquals(coupling.efferentCouplingMethodLevel(classG, classF), new Integer(2), "\nCe: G <--> F?");
        softAssert.assertEquals(coupling.efferentCouplingMethodLevel(classG, classH), new Integer(0), "\nCe: G <--> H?");
        softAssert.assertEquals(coupling.efferentCouplingMethodLevel(classG, classI), new Integer(0), "\nCe: G <--> I?");
        softAssert.assertEquals(coupling.efferentCouplingMethodLevel(classG, classJ), new Integer(0), "\nCe: G <--> J?");
        softAssert.assertEquals(coupling.efferentCouplingMethodLevel(classG, classK), new Integer(0), "\nCe: G <--> K?");
        softAssert.assertEquals(coupling.efferentCouplingMethodLevel(classG, classL), new Integer(0), "\nCe: G <--> L?");
        softAssert.assertEquals(coupling.efferentCouplingMethodLevel(classG, classM), new Integer(0), "\nCe: G <--> M?");
        softAssert.assertEquals(coupling.efferentCouplingMethodLevel(classG, classN), new Integer(0), "\nCe: G <--> N?");
        softAssert.assertEquals(coupling.efferentCouplingMethodLevel(classG, classO), new Integer(0), "\nCe: G <--> O?");
        softAssert.assertEquals(coupling.efferentCouplingMethodLevel(classG, classP), new Integer(0), "\nCe: G <--> P?");

        softAssert.assertAll();
    }

    public void testCeClassH() {
        softAssert.assertEquals(coupling.efferentCouplingMethodLevel(classH, classA), new Integer(0), "\nCe: H <--> A?");
        softAssert.assertEquals(coupling.efferentCouplingMethodLevel(classH, classB), new Integer(0), "\nCe: H <--> B?");
        softAssert.assertEquals(coupling.efferentCouplingMethodLevel(classH, classC), new Integer(0), "\nCe: H <--> C?");
        softAssert.assertEquals(coupling.efferentCouplingMethodLevel(classH, classD), new Integer(0), "\nCe: H <--> D?");
        softAssert.assertEquals(coupling.efferentCouplingMethodLevel(classH, classE), new Integer(0), "\nCe: H <--> E?");
        softAssert.assertEquals(coupling.efferentCouplingMethodLevel(classH, classF), new Integer(3), "\nCe: H <--> F?");
        softAssert.assertEquals(coupling.efferentCouplingMethodLevel(classH, classG), new Integer(0), "\nCe: H <--> G?");
        softAssert.assertEquals(coupling.efferentCouplingMethodLevel(classH, classI), new Integer(0), "\nCe: H <--> I?");
        softAssert.assertEquals(coupling.efferentCouplingMethodLevel(classH, classJ), new Integer(0), "\nCe: H <--> J?");
        softAssert.assertEquals(coupling.efferentCouplingMethodLevel(classH, classK), new Integer(0), "\nCe: H <--> K?");
        softAssert.assertEquals(coupling.efferentCouplingMethodLevel(classH, classL), new Integer(0), "\nCe: H <--> L?");
        softAssert.assertEquals(coupling.efferentCouplingMethodLevel(classH, classM), new Integer(0), "\nCe: H <--> M?");
        softAssert.assertEquals(coupling.efferentCouplingMethodLevel(classH, classN), new Integer(0), "\nCe: H <--> N?");
        softAssert.assertEquals(coupling.efferentCouplingMethodLevel(classH, classO), new Integer(0), "\nCe: H <--> O?");
        softAssert.assertEquals(coupling.efferentCouplingMethodLevel(classH, classP), new Integer(0), "\nCe: H <--> P?");

        softAssert.assertAll();
    }

    public void testCeClassI() {
        softAssert.assertEquals(coupling.efferentCouplingMethodLevel(classI, classA), new Integer(0), "\nCe: I <--> A?");
        softAssert.assertEquals(coupling.efferentCouplingMethodLevel(classI, classB), new Integer(0), "\nCe: I <--> B?");
        softAssert.assertEquals(coupling.efferentCouplingMethodLevel(classI, classC), new Integer(0), "\nCe: I <--> C?");
        softAssert.assertEquals(coupling.efferentCouplingMethodLevel(classI, classD), new Integer(0), "\nCe: I <--> D?");
        softAssert.assertEquals(coupling.efferentCouplingMethodLevel(classI, classE), new Integer(0), "\nCe: I <--> E?");
        softAssert.assertEquals(coupling.efferentCouplingMethodLevel(classI, classF), new Integer(5), "\nCe: I <--> F?");
        softAssert.assertEquals(coupling.efferentCouplingMethodLevel(classI, classG), new Integer(0), "\nCe: I <--> G?");
        softAssert.assertEquals(coupling.efferentCouplingMethodLevel(classI, classH), new Integer(0), "\nCe: I <--> H?");
        softAssert.assertEquals(coupling.efferentCouplingMethodLevel(classI, classJ), new Integer(0), "\nCe: I <--> J?");
        softAssert.assertEquals(coupling.efferentCouplingMethodLevel(classI, classK), new Integer(0), "\nCe: I <--> K?");
        softAssert.assertEquals(coupling.efferentCouplingMethodLevel(classI, classL), new Integer(0), "\nCe: I <--> L?");
        softAssert.assertEquals(coupling.efferentCouplingMethodLevel(classI, classM), new Integer(0), "\nCe: I <--> M?");
        softAssert.assertEquals(coupling.efferentCouplingMethodLevel(classI, classN), new Integer(0), "\nCe: I <--> N?");
        softAssert.assertEquals(coupling.efferentCouplingMethodLevel(classI, classO), new Integer(0), "\nCe: I <--> O?");
        softAssert.assertEquals(coupling.efferentCouplingMethodLevel(classI, classP), new Integer(0), "\nCe: I <--> P?");

        softAssert.assertAll();
    }
}
