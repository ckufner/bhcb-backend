package com.example.bhcbbackend.security;

import lombok.NonNull;

public enum EServiceUserRole
{
    ADMIN,
    USER,
    SWAGGER;

    public static final class Strings
    {
        private Strings()
        {
        }

        /*
         * This is inner class has to be kept in sync with the outer enum.
         * This class is necessary to be used in annotations for spring security @Preauthorize
         * since values in there have to be compile time constant...
         */
        public static final String ADMIN = "ADMIN";
        public static final String USER = "USER";
    }

    public static String asString(@NonNull final EServiceUserRole role)
    {
        return role.toString();
    }
}
