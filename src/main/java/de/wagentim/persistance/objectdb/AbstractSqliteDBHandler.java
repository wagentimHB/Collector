package de.wagentim.collector.persistance.objectdb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.wagentim.collector.utils.IConstants;

public abstract class AbstractSqliteDBHandler 
{
    protected String dbPath = IConstants.TXT_EMPTY_STRING;
    protected static final String DB_PRE_FIX = "jdbc:sqlite:";
    protected static final String DB_MEMORY = "memory:";
    protected Connection conn = null;
    protected static Logger logger = LoggerFactory.getLogger(AbstractSqliteDBHandler.class);

    public AbstractSqliteDBHandler(String dbPath)
    {
        this.dbPath = dbPath;
        initialConnection();
    }

    protected void initialConnection() 
    {
        String url = DB_PRE_FIX + this.dbPath;
        try {
            conn = DriverManager.getConnection(url);
            logger.info("connect to the db: {}", dbPath);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    protected void createTable()
    {
        try {
            conn.createStatement().executeQuery(getStatementCreateTable());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    protected abstract String getStatementCreateTable();
}
