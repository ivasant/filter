/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.parts.filter;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author antonina
 */
public enum SortField {
    PART_NAME(0) {
        @Override
        public String getFieldName() {
            return "part_name";
        }
    },
    PART_NUMBER(1) {
        @Override
        public String getFieldName() {
            return "part_number";
        }
    },
    VENDOR(2) {
        @Override
        public String getFieldName() {
            return "vendor";
        }
    },
    QTY(3) {
        @Override
        public String getFieldName() {
            return "qty";
        }
    },
    SHIPPED(4) {
        @Override
        public String getFieldName() {
            return "shipped";
        }
    },
    RECEIVE(5) {
        @Override
        public String getFieldName() {
            return "receive";
        }
    };

    private final int fieldNum;

    private SortField(int inFieldNum) {
        fieldNum = inFieldNum;
    }
    
    // Mapping SortField to field_num
    private static final HashMap<Integer, SortField> _map = new HashMap<Integer, SortField>();
    static
    {
        for (SortField sf : SortField.values())
            _map.put(sf.fieldNum, sf);
    }

    public static SortField from(int inFieldNum)
    {
        return _map.get(inFieldNum);
    }

    public abstract String getFieldName();

}
