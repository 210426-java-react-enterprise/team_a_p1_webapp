package com.revature.ATeamWebApp.util.datasource;


import com.revature.ATeamORM.annotations.ConnectionConfig;
import com.revature.ATeamORM.annotations.JDBCConnection;

@ConnectionConfig(filepath = "application.properties")
@JDBCConnection(url = "${url}", username = "${username}", password = "${password}", schema = "${schema}")
public class ConnectionSQL {

    public ConnectionSQL() {}
}
