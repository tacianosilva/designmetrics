package br.edu.ufcg.splab.designmetrics.reports;

import org.designwizard.api.DesignWizard;

import br.edu.ufcg.splab.designmetrics.metrics.Coupling;

public abstract class ReportTemplate {
    
    protected DesignWizard dw;
    protected Coupling coupling;
    
    protected Integer numberOfClasses = null;
    protected Integer numberOfMethods = null;
    
    /**
     * @param dw
     */
    public ReportTemplate(DesignWizard dw) {
        this.dw = dw;
        this.coupling = new Coupling(dw);
        this.numberOfClasses = getNumberOfClasses(); 
        this.numberOfMethods = getNumberOfMethods();
    }
    
    /**
     * @return
     */
    public Integer getNumberOfClasses() {
        if (this.dw != null && numberOfClasses == null) {
            return dw.getAllClasses().size();
        }
        return numberOfClasses;
    }
    
    /**
     * @return
     */
    public Integer getNumberOfMethods() {
        if (this.dw != null && numberOfMethods == null) {
            return dw.getAllMethods().size();
        }
        return numberOfMethods;
    }
}