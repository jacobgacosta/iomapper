package dev.iomapper;

/**
 * <b>Customizable</b> is the contract that define the custom mappings.
 *
 * @author Jacob G. Acosta
 */
public interface Customizable {

    /**
     * Fills a custom mapper through the @see dev.iomapper.CustomMappings object.
     *
     * @return the filled custom mapper.
     */
    CustomMappings fill(CustomMappings customMapping);

}
