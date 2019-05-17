/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pv168.hotel.gui;

/**
 *
 * @author Lydie
 */
public enum ResultText {
    PRICE_INVALID(String.class),
        PRICE_NEGATIVE(String.class),
        PRICE_EMPTY(String.class),
        NUMBER_INVALID(String.class),
        NUMBER_NEGATIVE(String.class),
        NUMBER_EMPTY(String.class),
        CAPACITY_INVALID(String.class),
        CAPACITY_NEGATIVE(String.class),
        CAPACITY_EMPTY(String.class),
        ROOM_WITH_SAME_NUMBER(String.class),
        ROOM_NOT_CREATE(String.class),
        ROOM_ADD(String.class),
        ROOM_IS_FULL(String.class);

        private final Class<?> type;

        private ResultText(Class<?> type) {
            this.type = type;
        }
        public Class<?> getType() {
        return type;
    }
}
