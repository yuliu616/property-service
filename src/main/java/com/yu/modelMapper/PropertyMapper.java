package com.yu.modelMapper;

import com.yu.model.IntegerId;
import com.yu.model.property.Property;
import org.apache.ibatis.annotations.Param;

import java.time.Instant;
import java.util.List;

/**
 * model mapper interface for model Property
 */
public interface PropertyMapper {

    Property findPropertyById(@Param("id") String id);

    long generatePropertyId(@Param("o") IntegerId integerId);

    long insertPropertyWithModel(@Param("it") Property property);

    long updatePropertyWithModel(@Param("it") Property property);

    /**
     * @param isActive "0" for false,
     *                 "1" for true,
     *                 "-1" for anything.
     */
    List<Property> listAllProperty(
            @Param("isActive") int isActive,
            @Param("idMin") String idMin,
            @Param("idMax") String idMax,
            @Param("creationDateMin") Instant creationDateMin,
            @Param("creationDateMax") Instant creationDateMax,
            @Param("lastUpdatedMin") Instant lastUpdatedMin,
            @Param("lastUpdatedMax") Instant lastUpdatedMax,
            @Param("pageOffset") long pageOffset,
            @Param("pageSize") long pageSize
    );

    /**
     * @param isActive "0" for false,
     *                 "1" for true,
     *                 "-1" for anything.
     */
    long countAllProperty(
            @Param("isActive") int isActive,
            @Param("idMin") String idMin,
            @Param("idMax") String idMax,
            @Param("creationDateMin") Instant creationDateMin,
            @Param("creationDateMax") Instant creationDateMax,
            @Param("lastUpdatedMin") Instant lastUpdatedMin,
            @Param("lastUpdatedMax") Instant lastUpdatedMax
    );

    List<Property> findAllPropertyWithName(
            @Param("name") String name,
            @Param("isActive") boolean isActive,
            @Param("idMin") String idMin,
            @Param("idMax") String idMax,
            @Param("creationDateMin") Instant creationDateMin,
            @Param("creationDateMax") Instant creationDateMax,
            @Param("lastUpdatedMin") Instant lastUpdatedMin,
            @Param("lastUpdatedMax") Instant lastUpdatedMax,
            @Param("pageOffset") long pageOffset,
            @Param("pageSize") long pageSize
    );

}
