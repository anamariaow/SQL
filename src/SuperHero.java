import java.sql.*;

public class SuperHero {

    static final String DB_URL = "jdbc:mysql://localhost:3306/develhope";
    static final String USER = "root";
    static final String PASS = "admin";  // change here with the right password

    public void createTable() throws SQLException {

        Connection conn = null;

        conn= DriverManager.getConnection(DB_URL,USER,PASS);

        Statement statement = conn.createStatement();
        String createQuery = """
                    CREATE TABLE IF NOT EXISTS superheroes
                    ( hero_id INT(10) NOT NULL AUTO_INCREMENT,
                      name VARCHAR(30),
                      power VARCHAR(25),
                      team VARCHAR(25),
                      CONSTRAINT superheroes_pk PRIMARY KEY (hero_id)
                    );
                    """;

        statement.executeUpdate(createQuery);
        System.out.println("Tabella SuperHeroes Creata");

    }

    /**
     *
     * @param nome
     * @param teamsName
     * @throws SQLException
     * metodo per insierire il superHero
     */
    public void insertHero (String nome, TeamsName teamsName) throws SQLException {
        Connection conn = null;

        conn= DriverManager.getConnection(DB_URL,USER,PASS);

        Statement statement = conn.createStatement();

        String populateQuery = """
                    INSERT INTO superheroes (name, team)
                    VALUE ('Wolverine', 'XMEN');
                    """;
        statement.executeUpdate(populateQuery);
        System.out.println("SuperEroe Creato:"+nome +" "+ teamsName.getTeam());

    }

    public void printAllHeroes() throws SQLException {
        Connection conn = null;

        conn= DriverManager.getConnection(DB_URL,USER,PASS);

        Statement statement = conn.createStatement();

        String printQuery = """
                    select * from superheroes;
                    """;

        ResultSet resultSet = statement.executeQuery(printQuery);

        while(resultSet.next()){
            String name = resultSet.getString("name");
            String team = resultSet.getString("team");
            String power = resultSet.getString("power");

            System.out.print("name:" + name);
            System.out.print("team:" + team);
            System.out.print("power:" + power);
            System.out.println(" ");
        }
        conn.close();
    }

    public void updateHeroPower() throws SQLException {
        Connection conn = null;
        conn= DriverManager.getConnection(DB_URL,USER,PASS);
        Statement statement = conn.createStatement();

        String selectQuery = """
                    select * from superheroes;
                    """;
        ResultSet resultSet = statement.executeQuery(selectQuery);
        String findName = null;

        while(resultSet.next()){
            String name = resultSet.getString("name");
            String team = resultSet.getString("team");
            if (team.equals(TeamsName.XMEN.getTeam())){
                findName = name;
            }
        }
        if(findName != null) {
            String populateQuery = """
                    UPDATE superheroes
                    SET power = 'high'
                    where name = 'findName';
                    """;
            statement.executeUpdate(populateQuery);
            System.out.println("Table updated if find name diverso da null:" + findName);
        }


    }


    public enum TeamsName {

        XMEN("XMEN"),
        FANTASTICI4("FANTASTICI4"),
        FREELANCE("FREELANCE");

        private String team;

        public String getTeam() {
            return team;
        }
        private TeamsName(String team){
            this.team = team;
        }
    }


    public static void main(String[] args) throws SQLException {

        SuperHero superHero = new SuperHero();
        superHero.createTable();
        superHero.insertHero("wolverine", TeamsName.XMEN);
        superHero.printAllHeroes();
        superHero.updateHeroPower();
    }
}
