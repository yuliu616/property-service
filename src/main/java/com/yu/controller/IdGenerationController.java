package com.yu.controller;

import com.yu.exception.RecordInsertionFailException;
import com.yu.exception.UnhandledException;
import com.yu.model.IntegerId;
import com.yu.model.config.IdGenerationStrategy;
import com.yu.model.property.Property;
import com.yu.modelMapper.PropertyMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class IdGenerationController {

    @Autowired
    protected PropertyMapper propertyMapper;

    @Value("${property-service.options.model.property.id-generation-strategy}")
    protected IdGenerationStrategy propertyIdGenerationStrategy;

    private static final Logger logger = LoggerFactory.getLogger(IdGenerationController.class);

    @Transactional
    public Property fillWithGeneratedId(Property model){
        if (propertyIdGenerationStrategy == IdGenerationStrategy.ID_TABLE) {
            return this.fillWithGeneratedIdWithIdTable(model);
        } else {
            throw new UnhandledException("unknown IdGenerationStrategy: "+this.propertyIdGenerationStrategy);
        }
    }

    private Property fillWithGeneratedIdWithIdTable(Property model){
        IntegerId integerId = new IntegerId();
        long affected = propertyMapper.generatePropertyId(integerId);
        if (affected <= 0) {
            throw new RecordInsertionFailException();
        }
        String idInString = String.valueOf(integerId.getId());
        model.setId(idInString);
        return model;
    }

}
