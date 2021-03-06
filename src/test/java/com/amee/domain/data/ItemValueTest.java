package com.amee.domain.data;

import com.amee.domain.AMEEStatus;
import com.amee.domain.ValueDefinition;
import com.amee.domain.ValueType;
import com.amee.domain.item.BaseItemValue;
import com.amee.domain.item.data.DataItem;
import com.amee.domain.item.data.DataItemTextValue;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

public class ItemValueTest {

    private DataCategory mockDataCategory;
    private ValueDefinition mockValueDefinition;
    private ItemDefinition mockItemDefinition;
    private DataItem dataItem;
    private ItemValueDefinition mockItemValueDefinition;
    private BaseItemValue itemValue;

    @Before
    public void setUp() {
        mockDataCategory = mock(DataCategory.class);
        mockItemDefinition = mock(ItemDefinition.class);
        mockValueDefinition = mock(ValueDefinition.class);
        mockItemValueDefinition = mock(ItemValueDefinition.class);
        when(mockItemValueDefinition.getItemDefinition()).thenReturn(mockItemDefinition);
        when(mockItemValueDefinition.getValueDefinition()).thenReturn(mockValueDefinition);
        when(mockValueDefinition.getValueType()).thenReturn(ValueType.TEXT);
        dataItem = new DataItem(mockDataCategory, mockItemDefinition);
        itemValue = new DataItemTextValue(mockItemValueDefinition, dataItem);
    }

    @Test
    public void noneTrashed() {
        // An ItemValue should be considered trashed if:
        // itself is trashed or its Item is trashed or its ItemValueDefinition is trashed
        dataItem.setStatus(AMEEStatus.ACTIVE);
        when(mockItemValueDefinition.isTrash()).thenReturn(false);
        // ACTIVE IV + ACTIVE ITEM + ACTIVE IVD
        assertFalse("ItemValue should not be trashed", itemValue.isTrash());
        verify(mockItemValueDefinition).isTrash();
    }

    @Test
    public void itselfTrashed() {
        dataItem.setStatus(AMEEStatus.ACTIVE);
        itemValue.setStatus(AMEEStatus.TRASH);
        // TRASHED IV + ACTIVE ITEM + ACTIVE IVD
        assertTrue("Item value should be trashed", itemValue.isTrash());
    }

    @Test
    public void itemTrashed() {
        dataItem.setStatus(AMEEStatus.TRASH);
        // ACTIVE IV + TRASHED ITEM + ACTIVE IVD
        assertTrue("ItemValue should be trashed", itemValue.isTrash());
    }

    @Test
    public void itemValueDefinitionTrashed() {
        dataItem.setStatus(AMEEStatus.ACTIVE);
        when(mockItemValueDefinition.isTrash()).thenReturn(true);
        // ACTIVE IV + ACTIVE ITEM + TRASHED IVD
        assertTrue("ItemValue should be trashed", itemValue.isTrash());
        verify(mockItemValueDefinition).isTrash();
    }
}
