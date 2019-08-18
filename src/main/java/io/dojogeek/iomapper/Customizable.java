package io.dojogeek.iomapper;

/**
 * Customizable is the contract that define the custom mappings.
 *
 * @author norvek
 */
public interface Customizable {

    /**
     * Fills a custom mapper.
     *
     * @return the filled custom mapper.
     */
    CustomMappings fill(CustomMappings customMapping);

}
