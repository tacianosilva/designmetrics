package br.edu.ufcg.splab.designmetrics;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Set;

import org.designwizard.api.DesignWizard;
import org.designwizard.design.ClassNode;
import org.designwizard.design.MethodNode;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import br.edu.ufcg.splab.designmetrics.metrics.Coupling;
import br.edu.ufcg.splab.designmetrics.metrics.EfferentCouplingMetric;

@Test
public class EfferentCouplingDesignWizardTest {

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
    private ClassNode designManager;

    @BeforeClass
    public void setUp() throws Exception {

        downloadProject();
        
        // Project to analyze
    	String project = "src/test/resources/designwizard-1.5-SNAPSHOT.jar";
        
        // Design for all classes in the project.
        // Add here binary code or jar file of the project.
        designWizard = new DesignWizard(project);
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
        
        designManager = designWizard.getClass("org.designwizard.design.manager.DesignManager");
    }

	private void downloadProject() throws MalformedURLException, IOException {
		String fileName = "designwizard-1.5-SNAPSHOT.jar";
		String targetDirectory = "src/test/resources/";
		
		Path path = Paths.get(targetDirectory + fileName);
		
		if (Files.notExists(path)) {
			String sourceUrl = "https://oss.sonatype.org/content/repositories/snapshots/org/designwizard/designwizard/1.5-SNAPSHOT/designwizard-1.5-20160601.200935-2.jar";
        
			URL url = new URL(sourceUrl);

        	Path targetPath = new File(targetDirectory + fileName).toPath();

        	Files.copy(url.openStream(), targetPath,
                    StandardCopyOption.REPLACE_EXISTING);
		}
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
    
    public void testCeDesignManager() {
        Set<ClassNode> directRelatedEntities = ceMetric.getRelatedEntities(designManager);
        for (ClassNode classNode : directRelatedEntities) {
            System.out.println(classNode.getName());
        }
        System.out.println();
        Set<MethodNode> directRelatedMethods = ceMetric.getRelatedMethods(designManager);
        
        for (MethodNode methodNode : directRelatedMethods) {
            System.out.println(methodNode.getName());
        }

        softAssert.assertTrue(directRelatedEntities.contains(packageNode));
        softAssert.assertTrue(directRelatedEntities.contains(classNode));
        softAssert.assertTrue(directRelatedEntities.contains(fieldNode));
        softAssert.assertTrue(directRelatedEntities.contains(methodNode));
        softAssert.assertEquals(coupling.efferentCoupling(designManager), new Integer(18), "\n designManager Ce: ");
        softAssert.assertEquals(coupling.efferentCouplingMethodLevel(designManager), new Integer(46), "\n designManager Ce ml: ");
        softAssert.assertAll();
    }
}
