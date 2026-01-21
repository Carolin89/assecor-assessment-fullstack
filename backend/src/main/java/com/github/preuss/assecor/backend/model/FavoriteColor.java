package com.github.preuss.assecor.backend.model;

import java.util.Map;

public enum FavoriteColor {
    BLAU(1, "blau"),
    GRUEN(2, "grün"),
    VIOLETT(3, "violett"),
    ROT(4, "rot"),
    GELB(5, "gelb"),
    TUERKIS(6, "türkis"),
    WEISS(7, "weiß");

    private static final Map<Integer, FavoriteColor> BY_ID =
            Map.of(
                    1, BLAU,
                    2, GRUEN,
                    3, VIOLETT,
                    4, ROT,
                    5, GELB,
                    6, TUERKIS,
                    7, WEISS
            );

    private final int id;
    private final String label;

    FavoriteColor(int id, String label) {
        this.id = id;
        this.label = label;
    }
    public String getLabel() {
        return label;
    }

    public static String fromId(Integer id) {
        if (id == null) {
            return null;
        }
        FavoriteColor color = BY_ID.get(id);
        return color != null ? color.getLabel() : null;
    }
}
