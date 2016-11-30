package br.edu.ufcg.splab.designmetrics;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.designwizard.api.DesignWizard;
import org.designwizard.design.ClassNode;
import org.designwizard.design.PackageNode;

import br.edu.ufcg.splab.designmetrics.metrics.Coupling;

public class ReportGenerator {
	
	private static Logger logger = LogManager.getLogger(ReportGenerator.class);
	
	private ReportGenerator() {
	    super();
	}

	public static void main(String[] args) {
		logger.info("Start Main - Gerando Relatórios de Acoplamento");
		
		generateReport();
	}
	
    public static void generateReport() {
        String fileName = "datasets/input/projects.txt";
        String fileResults = "datasets/results/results_coupling.txt";

        processarArquivo(fileName, fileResults);
    }
    
    public static void processarArquivo(String fileProjects, String fileResults) {
        try {
            InputStreamReader arq = new InputStreamReader(new FileInputStream(fileProjects), StandardCharsets.UTF_8);
            
            BufferedReader lerArq = new BufferedReader(arq);

            PrintWriter resultsWriter = new PrintWriter(criarArquivo(fileResults));
            resultsWriter.printf("%s,%s,%s,%s,%s,%s%n", "project", "class", "ce", "ce ml", "ca", "ca ml");

            String linha = lerArq.readLine(); // lê a primeira linha
            // a variável "linha" recebe o valor "null" quando o processo
            // de repetição atingir o final do arquivo texto
            while (linha != null) {
                logger.info("Processando projeto: " + linha);

                processarProjeto(linha, resultsWriter);

                linha = lerArq.readLine(); // lê da segunda até a última linha
            }

            resultsWriter.close();
            lerArq.close();
        } catch (IOException e) {
            logger.error("Erro na abertura do arquivo: %s.%n", e);
        }
    }
	
    public static void processarProjeto(String projeto, PrintWriter resultsWriter) {
        
        String reposDir = "/local_home/tacianosilva/workspace/";
        String classDir = getClassesDirectory(projeto);

        String projectDir = reposDir + projeto + classDir;

        try {
            logger.info("Project Directory: " + projectDir);
            
            DesignWizard designWizard = new DesignWizard(projectDir);

            //classReport(projeto, resultsWriter, designWizard);
            //packageReport(projeto, designWizard);
            TopClassesReport(projeto, designWizard);

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        

    }

    private static void classReport(String projeto, PrintWriter resultsWriter, DesignWizard designWizard) {
        // All Classes from Project
        Set<ClassNode> classes = designWizard.getAllClasses();

        for (ClassNode classNode : classes) {

            Coupling coupling = new Coupling(designWizard);
            
            Integer efferent = coupling.efferentCoupling(classNode);
            Integer afferent = coupling.afferentCoupling(classNode);
            
            Integer effMl = coupling.efferentCouplingMethodLevel(classNode);
            Integer affMl = coupling.afferentCouplingMethodLevel(classNode);

            logger.debug(">>>>>" + projeto + ", " + classNode.getClassName() + ", " + efferent + ", " + effMl + ", " + afferent + ", " + affMl);

            gravarLinha(resultsWriter, projeto, classNode.getClassName(), efferent, effMl, afferent, affMl);
        }
    }

    private static void packageReport(String project, DesignWizard designWizard) {
        logger.info("Generating Couple Package Report ..." + project);
        
        // All packages from Project
        Set<PackageNode> packages = designWizard.getAllPackages();
        PackageReport pr = new PackageReport(designWizard);
        
        for (PackageNode packageNode : packages) {

            pr.execute(packageNode);
            logger.debug(project + ", " + packageNode.getName() 
                        + ", " + pr.getCe() + ", " + pr.getCeMl() 
                        + ", " + pr.getCa() + ", " + pr.getCaMl());

            //gravarLinha(resultsWriter, projeto, classNode.getClassName(), efferent, effMl, afferent, affMl);
        }
    }
    
    private static void TopClassesReport(String project, DesignWizard designWizard) {
        logger.info("Generating Couple Top Classes Report ..." + project);
        
        // All packages from Project
        TopClassesReport tc = new TopClassesReport(designWizard);
        Set<ClassNode> classes = tc.getTopClasses();
        
        for (ClassNode classNodeA : classes) {
            for (ClassNode classNodeB : classes) {
                tc.execute(classNodeA, classNodeB);
                logger.debug(project + ", " + classNodeA.getName() 
                        + ", " + classNodeB.getClassName() 
                        + ", " + tc.getCe());

                //gravarLinha(resultsWriter, projeto, classNode.getClassName(), efferent, effMl, afferent, affMl);
            }
        }
    }
    
    private static void gravarLinha(PrintWriter gravar, String projeto, String className, int efferent, int effMl, int afferent, int affMl) {
        gravar.printf("%s,%s,%d,%d,%d,%d%n", projeto, className, efferent, effMl, afferent, affMl);
    }

    public static OutputStreamWriter criarArquivo(String fileResults) throws IOException {
        OutputStreamWriter arq = new OutputStreamWriter(new FileOutputStream(fileResults), StandardCharsets.UTF_8);
        return arq;
    }
    
    private static String getClassesDirectory(String projeto) {
        if ("designwizard".equals(projeto)) {
            return "/classes";
        }
        return "/target/classes";
    }
}