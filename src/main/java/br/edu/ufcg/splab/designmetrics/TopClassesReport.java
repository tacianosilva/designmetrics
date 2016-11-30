package br.edu.ufcg.splab.designmetrics;

import java.util.Set;

import org.designwizard.api.DesignWizard;
import org.designwizard.design.ClassNode;

import br.edu.ufcg.splab.designmetrics.metrics.Coupling;

public class TopClassesReport extends ReportTemplate {
    
    private Coupling coupling;
    private Integer ce;
    
    public TopClassesReport(DesignWizard dw) {
        super(dw);
        this.coupling = new Coupling(dw);
    }
    
    public Set<ClassNode> getTopClasses() {
        return coupling.getTopClassesCe(40);
    }
    
    public void execute(ClassNode nodeA, ClassNode nodeB) {
        this.ce = coupling.efferentCoupling(nodeA, nodeB);
    }

    public Integer getCe() {
        return ce;
    }
}
