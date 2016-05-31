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

@Test
public class EfferentCouplingMetricTest {

    private SoftAssert softAssert;
    private DesignWizard designWizard;
    private EfferentCouplingMetric ceMetric;
    private Coupling coupling;

    private ClassNode designIF;
    private ClassNode design;
    private ClassNode abstractEntity;
    private ClassNode packageNode;
    private ClassNode classNode;
    private ClassNode methodNode;
    private ClassNode fieldNode;
    private ClassNode relation;

    @BeforeClass
    public void setUp() throws Exception {

        String home = System.getProperty("user.home");
        // Design for all classes in the project.
        // Add here binary code or jar file of the project.
        designWizard = new DesignWizard(home + "/.m2/repository/org/designwizard/designwizard/1.5-SNAPSHOT/"
                + "designwizard-1.5-SNAPSHOT.jar");
        ceMetric = new EfferentCouplingMetric(designWizard);
        coupling = new Coupling(designWizard);

        designIF = designWizard.getClass("org.designwizard.design.DesignIF");
        design = designWizard.getClass("org.designwizard.design.Design");
        abstractEntity = designWizard.getClass("org.designwizard.design.AbstractEntity");
        packageNode = designWizard.getClass("org.designwizard.design.PackageNode");
        classNode = designWizard.getClass("org.designwizard.design.ClassNode");
        methodNode = designWizard.getClass("org.designwizard.design.MethodNode");
        fieldNode = designWizard.getClass("org.designwizard.design.FieldNode");
        relation = designWizard.getClass("org.designwizard.design.relation.Relation");
    }

    @BeforeMethod
    public void startTest() {
         softAssert = new SoftAssert();
    }

    @AfterClass
    public void tearDown() throws Exception {
    }

    public void testCeDesignIF() {
        Set<ClassNode> directRelatedEntities = ceMetric.getRelatedEntities(designIF);
        softAssert.assertTrue(directRelatedEntities.contains(packageNode), "package: ");
        softAssert.assertTrue(directRelatedEntities.contains(classNode), "class: ");
        softAssert.assertTrue(directRelatedEntities.contains(fieldNode), "field: ");
        softAssert.assertTrue(directRelatedEntities.contains(methodNode), "method: ");
        softAssert.assertEquals(coupling.efferentCoupling(designIF), new Integer(8), "\n designIF Ce: ");
        softAssert.assertAll();
    }

    public void testCeDesign() {
        Set<ClassNode> directRelatedEntities = ceMetric.getRelatedEntities(design);
        softAssert.assertTrue(directRelatedEntities.contains(packageNode));
        softAssert.assertTrue(directRelatedEntities.contains(classNode));
        softAssert.assertTrue(directRelatedEntities.contains(fieldNode));
        softAssert.assertTrue(directRelatedEntities.contains(methodNode));
        softAssert.assertTrue(directRelatedEntities.contains(relation));
        softAssert.assertEquals(coupling.efferentCoupling(design), new Integer(14), "\n design Ce: ");
        softAssert.assertAll();
    }

    public void testCePackageNode() {
        Set<ClassNode> directRelatedEntities = ceMetric.getRelatedEntities(packageNode);
        for (ClassNode node : directRelatedEntities) {
            System.out.println("PackageNode: " +  node.getName());
        }
        softAssert.assertTrue(directRelatedEntities.contains(classNode));
        softAssert.assertTrue(directRelatedEntities.contains(methodNode));

        //TODO A herança de AbstractEntity não foi contata pela ferramenta ckjm
        softAssert.assertTrue(directRelatedEntities.contains(abstractEntity));
        //softAssert.assertEquals(coupling.efferentCoupling(packageNode), 7, "\nCe: ");
        softAssert.assertEquals(coupling.efferentCoupling(packageNode), new Integer(8), "\n packageNode Ce: ");
        softAssert.assertAll();
    }

    public void testCeClassNode() {
        Set<ClassNode> directRelatedEntities = ceMetric.getRelatedEntities(classNode);
        softAssert.assertTrue(directRelatedEntities.contains(fieldNode), "field: ");
        softAssert.assertTrue(directRelatedEntities.contains(methodNode), "method: ");
        softAssert.assertEquals(coupling.efferentCoupling(classNode), new Integer(11), "\n classNode Ce: ");
        softAssert.assertAll();
    }

    public void testCeMethodNode() {
        Set<ClassNode> directRelatedEntities = ceMetric.getRelatedEntities(methodNode);
        for (ClassNode node : directRelatedEntities) {
            System.out.println("MethodNode: " +  node.getName());
        }
        softAssert.assertTrue(directRelatedEntities.contains(classNode), "class: ");

        //TODO Não encontra o FieldNode, pois o único método que acessa é o MethodNode.getAccessedFields()
        // Este método retorna um Set<FieldNode> e aparentemente o DesignWizard não extrai esse tipo de informação
        softAssert.assertTrue(directRelatedEntities.contains(fieldNode), "field: ");
        softAssert.assertTrue(directRelatedEntities.contains(relation), "relation: ");
        softAssert.assertEquals(coupling.efferentCoupling(methodNode), new Integer(11), "\n methodNode Ce: ");
        softAssert.assertAll();
    }

    public void testCeFieldNode() {
        Set<ClassNode> directRelatedEntities = ceMetric.getRelatedEntities(fieldNode);
        softAssert.assertTrue(directRelatedEntities.contains(classNode));
        softAssert.assertTrue(directRelatedEntities.contains(relation));
        softAssert.assertEquals(coupling.efferentCoupling(fieldNode), new Integer(9), "\n fieldNode Ce: ");
        softAssert.assertAll();
    }
}
