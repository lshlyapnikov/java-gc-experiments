package com.github.lshlyapnikov.unsafe;

import jdk.internal.misc.Unsafe;

import java.util.Arrays;

public class UnsafeArrayMain {
    private static final Unsafe U = Unsafe.getUnsafe();

    private static final int ABASE = U.arrayBaseOffset(Long[].class);
    private static final int ASHIFT = calculateAshift(Long[].class);

    private static int calculateAshift(Class<?> arrayClass) {
        int scale = U.arrayIndexScale(arrayClass);
        if ((scale & (scale - 1)) != 0) {
            throw new ExceptionInInitializerError("array index scale not a power of two");
        }
        return 31 - Integer.numberOfLeadingZeros(scale);
    }

    public static void main(String[] args) {
        System.out.printf("ABASE: %s\n", ABASE);
        System.out.printf("ASHIFT: %s\n", ASHIFT);

        final Long[] array = new Long[32];
        Arrays.fill(array, 0L);
        System.out.printf("array: %s\n", Arrays.toString(array));
        for (int i = 0; i < array.length; i++) {
//            System.out.printf("offset: %s\n", ((long) i << ASHIFT));
            U.compareAndSetReference(array, ((long) i << ASHIFT) + ABASE, 0L, (long) i);
        }
        System.out.printf("array: %s\n", Arrays.toString(array));
    }
}
