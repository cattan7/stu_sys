/*
 *  Copyright 1999-2019 Seata.io Group.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package io.seata.rm.datasource.undo.mysql;

import io.seata.common.exception.ShouldNeverHappenException;
import io.seata.common.util.CollectionUtils;
import io.seata.rm.datasource.ColumnUtils;
import io.seata.rm.datasource.sql.struct.Field;
import io.seata.rm.datasource.sql.struct.Row;
import io.seata.rm.datasource.sql.struct.TableRecords;
import io.seata.rm.datasource.undo.AbstractUndoExecutor;
import io.seata.rm.datasource.undo.SQLUndoLog;
import io.seata.sqlparser.util.JdbcConstants;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * The type My sql undo insert executor.
 *
 * @author sharajava
 */
public class MySQLUndoInsertExecutor extends AbstractUndoExecutor {

    /**
     * DELETE FROM a WHERE pk = ?
     */
    private static final String DELETE_SQL_TEMPLATE = "DELETE FROM %s WHERE %s = ?";

    /**
     * Undo Inset.
     *
     * @return sql
     */
    @Override
    protected String buildUndoSQL() {
        TableRecords afterImage = sqlUndoLog.getAfterImage();
        List<Row> afterImageRows = afterImage.getRows();
        if (CollectionUtils.isEmpty(afterImageRows)) {
            throw new ShouldNeverHappenException("Invalid UNDO LOG");
        }
        Row row = afterImageRows.get(0);
        Field pkField = row.primaryKeys().get(0);
        // insert sql undo log after image all field come from table meta, need add escape.
        // see BaseTransactionalExecutor#buildTableRecords
        return String.format(DELETE_SQL_TEMPLATE, sqlUndoLog.getTableName(),
                ColumnUtils.addEscape(pkField.getName(), JdbcConstants.MYSQL));
    }

    @Override
    protected void undoPrepare(PreparedStatement undoPST, ArrayList<Field> undoValues, Field pkValue)
            throws SQLException {
        undoPST.setObject(1, pkValue.getValue(), pkValue.getType());
    }

    /**
     * Instantiates a new My sql undo insert executor.
     *
     * @param sqlUndoLog the sql undo log
     */
    public MySQLUndoInsertExecutor(SQLUndoLog sqlUndoLog) {
        super(sqlUndoLog);
    }

    @Override
    protected TableRecords getUndoRows() {
        return sqlUndoLog.getAfterImage();
    }
}