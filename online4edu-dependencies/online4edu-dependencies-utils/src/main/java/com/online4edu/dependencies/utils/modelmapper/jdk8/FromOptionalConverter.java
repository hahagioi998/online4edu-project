package com.online4edu.dependencies.utils.modelmapper.jdk8;

import org.modelmapper.spi.ConditionalConverter;
import org.modelmapper.spi.MappingContext;

import java.util.Optional;

/**
 * Converts  {@link Optional} to {@link Object}
 *
 * @author Chun Han Hsiao
 */
public class FromOptionalConverter implements ConditionalConverter<Optional<Object>, Object> {

    @Override
    public MatchResult match(Class<?> sourceType, Class<?> destinationType) {
        return (Optional.class.equals(sourceType) && !Optional.class.equals(destinationType))
                ? MatchResult.FULL
                : MatchResult.NONE;
    }

    @Override
    public Object convert(MappingContext<Optional<Object>, Object> mappingContext) {
        Optional<Object> sourceOpt = mappingContext.getSource();
        if (mappingContext.getSource() == Optional.empty() || !sourceOpt.isPresent()) {
            return null;
        }

        MappingContext<Object, Object> propertyContext = mappingContext
                .create(sourceOpt.get(), mappingContext.getDestinationType());

        return mappingContext.getMappingEngine().map(propertyContext);
    }
}
