package org.javaculator.antlr4.handlers.interfaces;

import org.antlr.v4.runtime.ParserRuleContext;
import org.javaculator.antlr4.CalcParser;
import org.javaculator.antlr4.exceptions.impl.MissingOrNullIdentifierException;
import org.javaculator.antlr4.cache.RollbackCache;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.function.Function;

/**
 * A generic interface for handling expression nodes in a parse tree.
 * <p>
 * This interface provides several overloaded default methods to process a given parse tree context,
 * optionally using a snapshot of the current variable state and a visitor function to produce a result.
 * Additionally, it offers helper methods to interact with the snapshot based on an identifier extracted
 * from the parse tree context.
 * </p>
 *
 * @param <I_CTX> the type of the input context, which extends {@link ParserRuleContext}
 * @param <O_CTX> the type of the output context that may be produced from the input context
 */
public interface IExprHandler<I_CTX extends ParserRuleContext, O_CTX> {

    /**
     * Processes the given context and returns an output context wrapped in an {@code Optional}.
     *
     * @param ctx the parse tree context to process
     * @return an {@code Optional<OCTX>} containing the result of processing, or null if not applicable
     */
    default O_CTX handle(I_CTX ctx) {return null;}

    /**
     * Processes the given context using the provided snapshot and returns an output context wrapped in an {@code Optional}.
     *
     * @param ctx      the parse tree context to process
     * @param rollbackCache the snapshot representing the current variable state
     * @return an {@code Optional<OCTX>} containing the result of processing, or null if not applicable
     */
    default O_CTX handle(I_CTX ctx, RollbackCache rollbackCache)  {return null;}

    /**
     * Processes the given context by applying a visitor function to the output context.
     *
     * @param ctx     the parse tree context to process
     * @param visitor a function that converts an output context ({@code OCTX}) into a {@code BigDecimal} result
     * @return an {@link BigDecimal} containing the computed result, or null if not applicable
     */
    default BigDecimal handle(I_CTX ctx, Function<O_CTX, BigDecimal> visitor) {return null;}

    /**
     * Processes the given context using the provided snapshot and a visitor function to produce a {@code BigDecimal} result.
     *
     * @param ctx      the parse tree context to process
     * @param rollbackCache the snapshot representing the current variable state
     * @param visitor  a function that converts an output context ({@code OCTX}) into a {@code BigDecimal} result
     * @return an {@link BigDecimal} containing the computed result, or null if not applicable
     */
    default BigDecimal handle(I_CTX ctx, RollbackCache rollbackCache, Function<O_CTX, BigDecimal> visitor)  {return null;}

    /**
     * Retrieves a {@code BigDecimal} value from the snapshot corresponding to the identifier extracted from the context.
     *
     * @param ctx      the parse tree context containing the identifier token
     * @param rollbackCache the snapshot representing the current variable state
     * @return the {@code BigDecimal} value associated with the identifier
     * @throws MissingOrNullIdentifierException if the identifier is missing or its value is {@code null} in the snapshot
     */
    default BigDecimal getFromSnapshot(I_CTX ctx, RollbackCache rollbackCache) {
        String id = getId(ctx);

        return Optional.ofNullable(rollbackCache.get(id))
                .orElseThrow(() -> new MissingOrNullIdentifierException(id));
    }

    /**
     * Updates the snapshot with a new value for the identifier extracted from the context and returns the previous value.
     *
     * @param ctx      the parse tree context containing the identifier token
     * @param rollbackCache the snapshot representing the current variable state
     * @param value    the new value to associate with the identifier
     * @return the previous {@code BigDecimal} value associated with the identifier, or {@code null} if there was none
     */
    default BigDecimal putAndGetPrevious(I_CTX ctx, RollbackCache rollbackCache, BigDecimal value) {
        return rollbackCache.putAndGetPrevious(getId(ctx), value);
    }

    /**
     * Updates the snapshot with a new value for the identifier extracted from the context and returns the new value.
     *
     * @param ctx      the parse tree context containing the identifier token
     * @param rollbackCache the snapshot representing the current variable state
     * @param value    the new value to associate with the identifier
     * @return the new {@code BigDecimal} value associated with the identifier
     */
    default BigDecimal putAndGetCurrent(I_CTX ctx, RollbackCache rollbackCache, BigDecimal value) {
        return rollbackCache.putAndGetCurrent(getId(ctx), value);
    }

    /**
     * Extracts the identifier from the given parse tree context.
     *
     * @param ctx the parse tree context containing an identifier token
     * @return the text of the identifier token
     */
    private String getId(I_CTX ctx) {
        return ctx.getToken(CalcParser.ID, 0).getText();
    }
}
