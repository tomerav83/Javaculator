package org.javaculator.shuntified.models;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.javaculator.shuntified.models.tkn.Tkn;
import org.javaculator.shuntified.models.tkn.impl.AssignmentTkn;

import java.util.List;

public record AssignmentStatement(AssignmentTkn assignment, List<Tkn> equation) {
}
