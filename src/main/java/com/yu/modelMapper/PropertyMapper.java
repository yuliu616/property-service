package com.yu.modelMapper;

import com.yu.model.IntegerId;
import com.yu.model.property.Property;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * model mapper interface for model Property
 */
public interface PropertyMapper {

    Property findPropertyById(@Param("id") String id);

    long generatePropertyId(@Param("o") IntegerId integerId);

    long insertPropertyWithModel(@Param("p") Property property);

    long updatePropertyWithModel(@Param("p") Property property);

    List<Property> listAllProperty(
            @Param("isActive") boolean isActive,
            @Param("pageOffset") long pageOffset,
            @Param("pageSize") long pageSize);

    List<Property> findAllPropertyWithName(
            @Param("name") String name,
            @Param("isActive") boolean isActive,
            @Param("pageOffset") long pageOffset,
            @Param("pageSize") long pageSize);

}
