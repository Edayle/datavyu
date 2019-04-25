/**
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.datavyu.models.db;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

/**
 * Tests for the TextCellValue Interface
 */
public class TextCellValueTest {

    /**
     * The parent DataStore for the TextCellValue we are testing.
     */
    private DataStore ds;

    /**
     * The parent variable for the TextCellValue we are testing.
     */
    private Variable var;

    /**
     * The parent cell for the TextCellValue we are testing.
     */
    private Cell cell;

    /**
     * The value that we are testing.
     */
    private CellValue model;

    @BeforeMethod
    public void setUp() throws UserWarningException {
        ds = DataStoreFactory.newDataStore();
        var = ds.createVariable("test", Argument.Type.TEXT);
        cell = var.createCell();
        model = cell.getCellValue();
    }

    @AfterMethod
    public void tearDown() {
        model = null;
        cell = null;
        var = null;
        ds = null;
    }

    @Test
    public void testClear() {
        assertTrue(model.isEmpty());

        model.set("test");
        assertFalse(model.isEmpty());

        model.clear();
        assertTrue(model.isEmpty());
    }


}
