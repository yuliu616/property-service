package com.yu.model;

public enum Permission {

    /**
     * permission to use the system/service
     */
    BASIC,

    /**
     * if one have this permission, he can do anything
     */
    ANYTHING,

    /**
     * permission to load/check existence of
     * a record of model `Property` with explicit ID
     */
    PROPERTY_GET,

    /**
     * permission to load/search for
     * record of model `Property` (using condition instead of ID)
     */
    PROPERTY_SEARCH,

    /**
     * permission to create new record of model `Property`
     */
    PROPERTY_ADD,

    /**
     * permission to update a particular record of model `Property`
     */
    PROPERTY_UPDATE,

    /**
     * permission to delete record of model `Property`
     */
    PROPERTY_DELETE,

}
