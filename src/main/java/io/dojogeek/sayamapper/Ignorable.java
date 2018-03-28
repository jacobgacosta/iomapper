package io.dojogeek.sayamapper;

/**
 * Ignorable is the contract that define the ignorable list.
 *
 * @author norvek
 */
public interface Ignorable {

    /**
     * Fills a list of ignorable.
     *
     * @return the filled list of ignorable.
     */
    UnwantedTargetList fill(UnwantedTargetList unwantedTargetList);

}
