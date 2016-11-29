package br.edu.ufcg.splab.designmetrics;

import org.designwizard.api.DesignWizard;

public abstract class ReportTemplate {
    
    protected DesignWizard dw;
    
    public ReportTemplate(DesignWizard dw) {
        this.dw = dw;
    }
}
