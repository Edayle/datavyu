/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.openshapa.db;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 */
public class ODBCDatabaseTest extends DatabaseTest {

    public ODBCDatabase db;

    @Override
    public Database getInstance() {
        return db;
    }

    public ODBCDatabaseTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() throws SystemErrorException {
        db = new ODBCDatabase();
    }

    @After
    public void tearDown() {
        db = null;
    }

    /**
     * Test of getType method, of class ODBCDatabase.
     */
    @Test
    @Override
    public void testGetType() {
        assertTrue(db.getType().length() != 0);
    }

    /**
     * Test of getVersion method, of class ODBCDatabase.
     */
    @Test
    public void testGetVersion() {
        assertTrue(db.getVersion() > 0.0);
    }


}