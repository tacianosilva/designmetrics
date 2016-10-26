package br.edu.ufcg.splab.designmetrics;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.designwizard.api.DesignWizard;
import org.designwizard.design.ClassNode;

import br.edu.ufcg.splab.designmetrics.metrics.Coupling;

public class ReportGenerator {
	
	private static Logger logger = LogManager.getLogger(ReportGenerator.class);

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
            FileReader arq = new FileReader(fileProjects);
            BufferedReader lerArq = new BufferedReader(arq);

            FileWriter fw = criarArquivo(fileResults);
            PrintWriter resultsWriter = new PrintWriter(fw);
            resultsWriter.printf("%s,%s,%s,%s%n", "project", "class", "ce", "ca");

            String linha = lerArq.readLine(); // lê a primeira linha
            // a variável "linha" recebe o valor "null" quando o processo
            // de repetição atingir o final do arquivo texto
            while (linha != null) {
                logger.info("Processando projeto: " + linha);

                processarProjeto(linha, resultsWriter);

                linha = lerArq.readLine(); // lê da segunda até a última linha
            }

            arq.close();
            fw.close();
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

            // All Classes from Project
            Set<ClassNode> classes = designWizard.getAllClasses();

            for (ClassNode classNode : classes) {

                Coupling coupling = new Coupling(designWizard);
                
                Integer efferent = coupling.efferentCoupling(classNode);
                Integer afferent = coupling.afferentCoupling(classNode);
                
                Integer effMl = coupling.efferentCouplingMethodLevel(classNode);
                Integer affMl = coupling.afferentCouplingMethodLevel(classNode);

                logger.debug(">>>>>" + projeto + ", " + classNode.getClassName() + ", " + efferent + ", " + effMl + ", " + afferent + ", " + affMl);

                gravarLinha(resultsWriter, projeto, classNode.getClassName(), efferent, afferent);
            }

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }
    
    private static void gravarLinha(PrintWriter gravar, String projeto, String className, int efferent, int afferent) {
        gravar.printf("%s,%s,%d,%d%n", projeto, className, efferent, afferent);
    }

    public static FileWriter criarArquivo(String fileResults) throws IOException {
        FileWriter arq = new FileWriter(fileResults);
        return arq;
    }
    
    private static String getClassesDirectory(String projeto) {
        if ("designwizard".equals(projeto)) {
            return "/classes";
        }
        return "/target/classes";
    }
}