package br.edu.ufcg.splab.designmetrics.metrics;

import java.util.HashSet;
import java.util.Set;

import org.designwizard.design.ClassNode;
import org.designwizard.design.FieldNode;
import org.designwizard.design.MethodNode;

public class EfferentCouplingMetric implements Metric {

    public EfferentCouplingMetric() {
        // TODO Auto-generated constructor stub
    }

    /**
     * Efferent Coupling (Ce) is the number of methods declared in one class use methods or
     * instance variables defined by the other class. The uses relationship can go either way:
     * both uses and used-by relationships are taken into account, but only once.
     *
     * {@link http://www.aivosto.com/project/help/pm-oo-ck.html}
     *
     * @param entity
     *            The entity that is desired efferent coupling.
     * @return The value of efferent coupling. If
     *         {@code Entity} is <code>null</code> returns <code>0</code>.
     */
    @Override
    public Integer calculate(ClassNode classNode) {
        if (classNode == null) {
            return 0;
        }
        return getDirectRelatedEntities(classNode).size();
    }

    /**
     * Return the direct related entities to the class.
     *
     * @param setClasses
     * @param name
     * @return
     */
    public Set<ClassNode> getDirectRelatedEntities(ClassNode classNode) {
        Set<ClassNode> directRelatedEntities = new HashSet<ClassNode>();

        // Classes directly calls
        Set<ClassNode> callees = classNode.getCalleeClasses();
        for (ClassNode callee : callees) {
            if (!callee.equals(classNode) && !callee.getClassName().equals("java.lang.Object")) {
                directRelatedEntities.add(callee);
            }
        }
        // Classes calls for methods
        Set<MethodNode> calleeMethods = classNode.getCalleeMethods();
        for (MethodNode method : calleeMethods) {
            ClassNode callee = method.getClassNode();
            if (!callee.equals(classNode) && !callee.getClassName().equals("java.lang.Object")) {
                directRelatedEntities.add(callee);
            }
        }

        // Fields Declared
        directRelatedEntities.addAll(getFieldDeclaredEntities(classNode));
        return directRelatedEntities;
    }

    private Set<ClassNode> getFieldDeclaredEntities(ClassNode classNode) {
        Set<ClassNode> feedBack = new HashSet<ClassNode>();
        Set<FieldNode> fieldsDeclared = classNode.getAllFields();

        for (FieldNode field : fieldsDeclared) {
            ClassNode type = field.getType();
            if (!type.equals(classNode)) {
                feedBack.add(type);
            }
        }

        return feedBack;
    }


}
