package com.yu.controller;

import com.yu.exception.InconsistencyDataException;
import com.yu.exception.RecordInsertionFailException;
import com.yu.exception.RecordModificationFailException;
import com.yu.model.dto.CountDto;
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
import javax.validation.ValidationException;
import java.time.Instant;
import java.util.List;

@RequestMapping("${property-service.api-base-url}/property")
@RestController
public class PropertyController {

    @Autowired
    protected PropertyMapper propertyMapper;

    @Autowired
    protected IdGenerationController idGenerationController;

    private static final long PAGE_SIZE_MIN = 1;
    private static final long PAGE_SIZE_SAFE_LIMIT = 1000;
    private static final long PAGE_OFFSET_SAFE_LIMIT = 1000;

    private static final Logger logger = LoggerFactory.getLogger(PropertyController.class);

    @GetMapping("/{id}")
    public Property findPropertyById(@PathVariable("id") String id){
        return MyBatisUtil.valueOrRecordNotFoundErr(this.propertyMapper.findPropertyById(id));
    }

    @GetMapping("")
    public List<Property> listAllProperty(
            @RequestParam(value = "offset", defaultValue = "0") long offset,
            @RequestParam(value = "size", defaultValue = "10") long size,
            @RequestParam(value = "idMin", required = false) String idMin,
            @RequestParam(value = "idMax", required = false) String idMax,
            @RequestParam(value = "creationDateMin", required = false)
                    Instant creationDateMin,
            @RequestParam(value = "creationDateMax", required = false)
                    Instant creationDateMax,
            @RequestParam(value = "lastUpdatedMin", required = false)
                    Instant lastUpdatedMin,
            @RequestParam(value = "lastUpdatedMax", required = false)
                    Instant lastUpdatedMax,
            @RequestParam(value = "isActive", defaultValue = "1") int isActive
    ){
        long safePageSize = Math.max(Math.min(size, PAGE_SIZE_SAFE_LIMIT), PAGE_SIZE_MIN);
        if (offset > PAGE_OFFSET_SAFE_LIMIT) {
            throw new ValidationException("offset too large");
        }
        return this.propertyMapper.listAllProperty(isActive, idMin, idMax,
                creationDateMin, creationDateMax,
                lastUpdatedMin, lastUpdatedMax,
                offset, safePageSize);
    }

    @GetMapping("/count")
    public CountDto countAllProperty(
            @RequestParam(value = "idMin", required = false) String idMin,
            @RequestParam(value = "idMax", required = false) String idMax,
            @RequestParam(value = "creationDateMin", required = false)
                    Instant creationDateMin,
            @RequestParam(value = "creationDateMax", required = false)
                    Instant creationDateMax,
            @RequestParam(value = "lastUpdatedMin", required = false)
                    Instant lastUpdatedMin,
            @RequestParam(value = "lastUpdatedMax", required = false)
                    Instant lastUpdatedMax,
            @RequestParam(value = "isActive", defaultValue = "1") int isActive
    ){
        return new CountDto(this.propertyMapper.countAllProperty(isActive, idMin, idMax,
                creationDateMin, creationDateMax,
                lastUpdatedMin, lastUpdatedMax));
    }

    @GetMapping("/search/byName")
    public List<Property> findAllBrandWithNamePattern(
            @RequestParam(value = "name") String name,
            @RequestParam(value = "offset", defaultValue = "0") long offset,
            @RequestParam(value = "size", defaultValue = "10") long size,
            @RequestParam(value = "idMin", required = false) String idMin,
            @RequestParam(value = "idMax", required = false) String idMax,
            @RequestParam(value = "creationDateMin", required = false)
                    Instant creationDateMin,
            @RequestParam(value = "creationDateMax", required = false)
                    Instant creationDateMax,
            @RequestParam(value = "lastUpdatedMin", required = false)
                    Instant lastUpdatedMin,
            @RequestParam(value = "lastUpdatedMax", required = false)
                    Instant lastUpdatedMax
    ){
        long safePageSize = Math.max(Math.min(size, PAGE_SIZE_SAFE_LIMIT), PAGE_SIZE_MIN);
        if (offset > PAGE_OFFSET_SAFE_LIMIT) {
            throw new ValidationException("offset too large");
        }
        return this.propertyMapper.findAllPropertyWithName(
                name, true,
                idMin, idMax,
                creationDateMin, creationDateMax,
                lastUpdatedMin, lastUpdatedMax,
                offset, safePageSize);
    }

    @Transactional
    @PostMapping("")
    public Property createProperty(@RequestBody @Valid Property property){
        Property newRecord = idGenerationController.fillWithGeneratedId(property);
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
