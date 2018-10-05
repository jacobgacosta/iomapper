package io.dojogeek.sayamapper.utils;

import io.dojogeek.sayamapper.Determiner;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class DeterminerTest {

    @Test
    public void shouldMapToAPathNested() {
        assertTrue(Determiner.isNested("user.address.state"));

        assertFalse(Determiner.isNested("*"));
        assertFalse(Determiner.isNested(".user"));
        assertFalse(Determiner.isNested("user."));
        assertFalse(Determiner.isNested("user.*"));
        assertFalse(Determiner.isNested(".user."));
        assertFalse(Determiner.isNested("*.user"));
        assertFalse(Determiner.isNested("*.user.*"));
        assertFalse(Determiner.isNested("user.address.state*"));
        assertFalse(Determiner.isNested("*user.address.state*"));
        assertFalse(Determiner.isNested("user address state"));
    }

}
