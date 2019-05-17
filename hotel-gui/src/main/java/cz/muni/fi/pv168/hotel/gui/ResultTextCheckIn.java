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
public enum ResultTextCheckIn {
    EMPTY_FIELD(String.class),
    ADD_GUEST(String.class),
    ALL_ROOMS_FULL(String.class),
    GUEST_NOT_CREATE(String.class);

    private final Class<?> type;

    private ResultTextCheckIn(Class<?> type) {
        this.type = type;
    }

    public Class<?> getType() {
        return type;
    }

}
