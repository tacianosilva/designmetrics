package br.edu.ufcg.splab.designmetrics.reports;

import java.util.Set;

import org.designwizard.api.DesignWizard;
import org.designwizard.design.PackageNode;

public class TopPackagesReport extends ReportTemplate {
    
    private Integer ce;
    
    /**
     * @param dw An instance of the DesignWizard.
     */
    public TopPackagesReport(DesignWizard dw) {
        super(dw);
    }
    
    /**
     * @return
     */
    public Set<PackageNode> getTopPackages() {
        return coupling.getTopPackagesCe(10);
    }
    
    /**
     * @param nodeA
     * @param nodeB
     */
    public void execute(PackageNode nodeA, PackageNode nodeB) {
        this.ce = coupling.efferentCoupling(nodeA, nodeB);
    }

    public Integer getCe() {
        return ce;
    }
}
