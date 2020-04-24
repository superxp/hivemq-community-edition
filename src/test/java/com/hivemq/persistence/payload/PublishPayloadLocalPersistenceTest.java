package com.hivemq.persistence.payload;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class PublishPayloadLocalPersistenceTest {

    private PublishPayloadLocalPersistence persistence;

    @Before
    public void before() {
        persistence = new PublishPayloadMemoryLocalPersistence();
    }

    @Test
    public void test_add_get_payload() {

        final byte[] payload1 = "payload".getBytes();
        final byte[] payload2 = "payload".getBytes();

        persistence.put(0L, payload1);
        persistence.put(1L, payload2);

        final byte[] result1 = persistence.get(0L);
        final byte[] result2 = persistence.get(1L);

        assertArrayEquals(result1, payload1);
        assertArrayEquals(result2, payload2);
    }

    @Test
    public void test_add_remove_get_payload() {

        final byte[] payload1 = "payload".getBytes();
        final byte[] payload2 = "payload".getBytes();

        persistence.put(0L, payload1);
        persistence.put(1L, payload2);

        persistence.remove(1L);

        final byte[] result1 = persistence.get(0L);
        final byte[] result2 = persistence.get(1L);

        assertArrayEquals(result1, payload1);
        assertNull(result2);
    }

    @Test
    public void test_add_get_big_payload() {

        final byte[] payload1 = "payload".getBytes();
        final byte[] payload2 = RandomStringUtils.random(10 * 1024 * 1024 + 100, true, true).getBytes();

        persistence.put(0L, payload1);
        persistence.put(1L, payload2);

        final byte[] result1 = persistence.get(0L);
        final byte[] result2 = persistence.get(1L);

        assertArrayEquals(result1, payload1);
        assertArrayEquals(result2, payload2);
    }

    @Test
    public void test_add_remove_get_big_payload() {

        final byte[] payload1 = "payload".getBytes();
        final byte[] payload2 = RandomStringUtils.random(10 * 1024 * 1024 + 100, true, true).getBytes();

        persistence.put(0L, payload1);
        persistence.put(1L, payload2);

        persistence.remove(1L);

        final byte[] result1 = persistence.get(0L);
        final byte[] result2 = persistence.get(1L);

        assertArrayEquals(result1, payload1);
        assertNull(result2);
    }

    @Test
    public void test_get_all_ids() {

        final byte[] payload1 = "payload".getBytes();

        persistence.put(0L, payload1);
        persistence.put(1L, payload1);
        persistence.put(2L, payload1);

        persistence.remove(1L);

        final List<Long> allIds = persistence.getAllIds();
        assertEquals(2, allIds.size());
        assertFalse(allIds.contains(1L));
    }
}