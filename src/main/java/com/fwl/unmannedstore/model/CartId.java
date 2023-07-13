package com.fwl.unmannedstore.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartId implements Serializable {
    private int cart_id; // primary key
    private int rfid; // primary key

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CartId)) {
            return false;
        }
        CartId that = (CartId) o;
        return Objects.equals(getCart_id(), that.getCart_id()) &&
                Objects.equals(getRfid(), that.getRfid());
    }
    @Override
    public int hashCode() {
        return Objects.hash(getCart_id(), getRfid());
    }
}
