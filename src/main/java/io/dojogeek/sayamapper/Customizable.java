package io.dojogeek.sayamapper;

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
    CustomMapper fill(CustomMapper customMapping);

}
