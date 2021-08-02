package com.tamanna.demo.model;

import com.tamanna.demo.exception.ValidationError;
import com.tamanna.demo.model.entity.Slot;

public class SlotUtil {

    private static final String INVALID_SLOT_DATE_MESSAGE = "INVALID SLOT DATE";

    public static void validateSlot(Slot slot) throws ValidationError {

        if (slot.getDate() == null) {
            throw new ValidationError(INVALID_SLOT_DATE_MESSAGE);
        }

        if (slot.getDate().getMinute() != 0) {
            throw new ValidationError(INVALID_SLOT_DATE_MESSAGE);
        }
    }
}
