package br.edu.ufcg.splab.designmetrics.reports;

import java.util.Set;

import org.designwizard.api.DesignWizard;
import org.designwizard.design.ClassNode;

public class TopClassesReport extends ReportTemplate {
    
    private Integer ce;
    
    public TopClassesReport(DesignWizard dw) {
        super(dw);
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
