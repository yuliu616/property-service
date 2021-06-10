package com.yu.controller;

import com.yu.exception.InconsistencyDataException;
import com.yu.exception.RecordInsertionFailException;
import com.yu.exception.RecordModificationFailException;
import com.yu.model.property.Property;
import com.yu.modelMapper.PropertyMapper;
import com.yu.util.MyBatisUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RequestMapping("${property-service.api-base-url}/property")
@RestController
public class PropertyController {

    @Autowired
    protected PropertyMapper propertyMapper;

    private static final long PAGE_SIZE_MIN = 1;
    private static final long PAGE_SIZE_SAFE_LIMIT = 100;

    private static final Logger logger = LoggerFactory.getLogger(PropertyController.class);

    @GetMapping("/{id}")
    public Property findPropertyById(@PathVariable("id") String id){
        return MyBatisUtil.valueOrRecordNotFoundErr(this.propertyMapper.findPropertyById(id));
    }

    @GetMapping("")
    public List<Property> listAllProperty(
            @RequestParam(value = "offset", defaultValue = "0") long offset,
            @RequestParam(value = "size", defaultValue = "10") long size
    ){
        long safePageSize = Math.max(Math.min(size, PAGE_SIZE_SAFE_LIMIT), PAGE_SIZE_MIN);
        return this.propertyMapper.listAllProperty(true, offset, safePageSize);
    }

    @Transactional
    @PostMapping("")
    public Property createProperty(@RequestBody @Valid Property property){
        Property newRecord = MyBatisUtil.generateIdForModel(property,
                o->this.propertyMapper.generatePropertyId(o));
        newRecord.setActive(true); // user is active by default
        long created = this.propertyMapper.insertPropertyWithModel(newRecord);
        if (created <= 0) {
            throw new RecordInsertionFailException();
        }
        return this.findPropertyById(newRecord.getId());
    }

    @Transactional
    @PutMapping("/{id}")
    public Property updatePropertyById(@PathVariable("id") String id,
                                     @RequestBody @Valid Property property)
    {
        if (!id.equals(property.getId())) {
            throw new InconsistencyDataException();
        }

        long affected = this.propertyMapper.updatePropertyWithModel(property);
        if (affected <= 0) {
            throw new RecordModificationFailException();
        }
        return this.propertyMapper.findPropertyById(id);
    }

}
