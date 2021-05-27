package connection;


import com.revature.ATeamORM.util.annotations.JDBCConnection;

@JDBCConnection(url ="java-react-enterprise-210426.c875m6bzujpq.us-west-2.rds.amazonaws.com",
                        password = "revature",username = "mendozajua",schema = "public")
public class PortalConnection {
    
    public PortalConnection() {
    
    }
}
