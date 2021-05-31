package com.revature.ATeamWebApp.util.datasource;


import com.revature.ATeamORM.annotations.JDBCConnection;

@JDBCConnection(url ="java-react-enterprise-210426.c875m6bzujpq.us-west-2.rds.amazonaws.com",
        password = "revature",username = "mendozajua",schema = "public")
public class ConnectionSQL {

    public ConnectionSQL() {}
}
