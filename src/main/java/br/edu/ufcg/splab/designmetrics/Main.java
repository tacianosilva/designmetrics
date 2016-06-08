package br.edu.ufcg.splab.designmetrics;

import java.io.IOException;
import java.util.Set;

import org.designwizard.api.DesignWizard;
import org.designwizard.design.ClassNode;
import org.designwizard.exception.InexistentEntityException;

import br.edu.ufcg.splab.designmetrics.metrics.Coupling;
import br.edu.ufcg.splab.designmetrics.metrics.EfferentCouplingMetric;

public class Main {

    public static void main(String[] args) throws IOException, InexistentEntityException {

        DesignWizard designwizard = new DesignWizard("target/classes");

        Coupling coupling = new Coupling(designwizard);

        EfferentCouplingMetric ceMetric = new EfferentCouplingMetric(designwizard);

        ClassNode couplingNode = designwizard.getClass("br.edu.ufcg.splab.designmetrics.metrics.Coupling");
        ClassNode afferentNode = designwizard.getClass("br.edu.ufcg.splab.designmetrics.metrics.AfferentCouplingMetric");
        ClassNode efferentNode = designwizard.getClass("br.edu.ufcg.splab.designmetrics.metrics.EfferentCouplingMetric");

        Set<ClassNode> callees = afferentNode.getCalleeClasses();

        for (ClassNode callee : callees) {
            System.out.println(afferentNode.getName() + " call --> " + callee.getName());
        }

        callees = efferentNode.getCalleeClasses();

        for (ClassNode callee : callees) {
            System.out.println(efferentNode.getName() + " call --> " + callee.getName());
        }

        System.out.println("\nAcoplamento Coupling: " + coupling.efferentCoupling(couplingNode));
        System.out.println("\nAcoplamento AfferentNode: " + coupling.efferentCoupling(afferentNode));
        System.out.println("\nAcoplamento EfferentNode: " + coupling.efferentCoupling(efferentNode));

        Set<ClassNode> inter = ceMetric.intersectionRelatedEntities(afferentNode, efferentNode);
        System.out.println("Inter: ");
        for (ClassNode classNode : inter) {
            System.out.print(classNode.getName() + ", ");
        }
        System.out.println("\nAcoplamento <AfferentNode, EfferentNode>: " + ceMetric.calculate(afferentNode, efferentNode));

        System.out.println("\nAcoplamento Eferente Total: " + coupling.efferentCoupling());
    }
}
