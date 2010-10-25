package com.amee.domain.data;

import org.apache.commons.collections.Predicate;

/**
 * Basic Predicate testing {@link com.amee.domain.data.ItemValue} instances for usable values.
 * {@see ItemValue#isUsableValue()}
 */
class LegacyUsableValuePredicate implements Predicate {
    public boolean evaluate(Object o) {
        return ((LegacyItemValue) o).isUsableValue();
    }
}