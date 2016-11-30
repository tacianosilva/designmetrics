package br.edu.ufcg.splab.designmetrics;

import org.designwizard.api.DesignWizard;
import org.designwizard.design.PackageNode;

import br.edu.ufcg.splab.designmetrics.metrics.Coupling;

public class PackageReport extends ReportTemplate {
    
    private Coupling coupling;
    
    private Integer efferent;
    private Integer afferent;
    private Integer effMl;
    private Integer affMl;

    public PackageReport(DesignWizard dw) {
        super(dw);
        this.coupling = new Coupling(dw);
    }

    public void execute(PackageNode entity) {
        this.efferent = coupling.efferentCoupling(entity);
        this.afferent = coupling.afferentCoupling(entity);
        
        this.effMl = coupling.efferentCouplingMethodLevel(entity);
        this.affMl = coupling.afferentCouplingMethodLevel(entity);
    }

    public Integer getCe() {
        return efferent;
    }

    public Integer getCa() {
        return afferent;
    }

    public Integer getCeMl() {
        return effMl;
    }

    public Integer getCaMl() {
        return affMl;
    }
}
