package model.dao;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;


/**
 * 共通DAOクラス
 *
 * @author aoki
 */
public abstract class CommonDao {

    // ---------------------------------------------------- Private Field Area

    /** データソース */
    private static DataSource ds = null;

    // ---------------------------------------------------- Static Method Area

    /** 静的初期化子 */
    static {
        try {
            Context ic = new InitialContext();
            Context ec = (Context) ic.lookup("java:comp/env/");
            ds = (DataSource) ec.lookup("jdbc/oracleDS");
        } catch (NamingException e) {
            throw new RuntimeException(e);
        }
    }

    // ---------------------------------------------------- Protected Method Area

    /**
     * コネクションを取得する
     *
     * @return コネクション
     */
    protected Connection getConnection() {
        try {
            Connection con = ds.getConnection();
            con.setAutoCommit(false);
            return con;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * コネクションを閉じる
     *
     * @param con コネクション
     */
    protected void closeConnection(Connection con) {
        try {
            if (con != null) {
                con.rollback();
                con.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
