package org.javaculator.shuntified.models.tkn.impl;

import lombok.Getter;
import org.javaculator.shuntified.models.Assignment;
import org.javaculator.shuntified.models.tkn.Tkn;

@Getter
public class AssignmentTkn extends Tkn {
    private final Assignment assignment;

    public AssignmentTkn(Assignment assignment) {
        super(assignment.origin());

        this.assignment = assignment;
    }
}
