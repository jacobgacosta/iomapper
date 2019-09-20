package dev.iomapper;

/**
 * Ignorable is the contract that defines the the fillign of ignorable fields.
 *
 * @author Jacob G. Acosta
 */
public interface Ignorable {

    /**
     * Adds the ignorable fields through the @see dev.iomapper.IgnorableFields object.
     *
     * @param ignorableFields an ignorable fields object
     * @return the filled list of ignorable fields.
     */
    IgnorableFields fill(IgnorableFields ignorableFields);

}
