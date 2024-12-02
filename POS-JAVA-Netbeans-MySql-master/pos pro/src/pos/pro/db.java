package pos.pro;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class db {
    private static final String URL = "jdbc:mysql://127.0.0.1:3306/?user=root&password=12345";
    private static final String DATABASE_NAME = "pos";

    // Método para estabelecer a conexão com o banco de dados
    public static Connection mycon() {
        Connection con = null;
        try {
            // Carregar o driver JDBC para MySQL
            Class.forName("com.mysql.cj.jdbc.Driver");
            // Estabelecer a conexão
            con = DriverManager.getConnection(URL);
            con.setCatalog(DATABASE_NAME);
            System.out.println("Conexão estabelecida com sucesso!");
        } catch (ClassNotFoundException e) {
            System.out.println("Driver não encontrado: " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("Erro ao conectar ao banco de dados: " + e.getMessage());
        }
        return con;
    }

    // Método para inicializar o banco de dados e criar as tabelas
    public static void initializeDatabase() {
        try (Connection con = mycon()) {
            if (con != null) {
                // Cria o banco de dados se não existir
                Statement stmt = con.createStatement();
                stmt.executeUpdate("CREATE DATABASE IF NOT EXISTS " + DATABASE_NAME);
                System.out.println("Banco de dados '" + DATABASE_NAME + "' verificado/criado com sucesso!");

                // Muda o catálogo para o banco de dados 'pos'
                con.setCatalog(DATABASE_NAME);

                // Cria as tabelas necessárias
                createTables(con);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao inicializar o banco de dados: " + e.getMessage());
        }
    }

    // Método para criar as tabelas no banco de dados
    private static void createTables(Connection con) {
        try (Statement stmt = con.createStatement()) {
            // Criar tabela de clientes
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS customer (" +
                    "cid INT AUTO_INCREMENT PRIMARY KEY, " +
                    "customer_name VARCHAR(255), " +
                    "Tp_Number VARCHAR(255), " +
                    "city VARCHAR(255)" +
                    ");");

            // Criar tabela de vendedores
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS employee (" +
                    "cid INT AUTO_INCREMENT PRIMARY KEY, " +
                    "employee_name VARCHAR(255), " +
                    "Tp_Number VARCHAR(255), " +
                    "person_name VARCHAR(255), " +
                    "contact_person VARCHAR(255)" +
                    ");");

            // Criar tabela de fornecedores
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS supplier (" +
                    "sid INT AUTO_INCREMENT PRIMARY KEY, " +
                    "supplier_name VARCHAR(255), " +
                    "Tp_Number VARCHAR(255), " +
                    "product_type VARCHAR(255), " +
                    "person_name VARCHAR(255), " +
                    "contact_person VARCHAR(255)" +
                    ");");

            // Criar tabela de produtos
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS product (" +
                    "pid INT AUTO_INCREMENT PRIMARY KEY, " +
                    "Product_Name VARCHAR(255), " +
                    "Bar_code VARCHAR(255), " +
                    "Price DECIMAL(10, 2), " +
                    "Qty INT, " +
                    "Sid INT, " +
                    "Product_Type VARCHAR(255), " +
                    "FOREIGN KEY (Sid) REFERENCES supplier(sid)" +
                    ");");

            // Criar tabela de vendas
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS sales (" +
                    "saleid INT AUTO_INCREMENT PRIMARY KEY, " +
                    "INID VARCHAR(255), " +
                    "Cid INT, " +
                    "Customer_Name VARCHAR(255), " +
                    "Total_Qty INT, " +
                    "Total_Bill DECIMAL(10, 2), " +
                    "Status VARCHAR(255), " +
                    "Balance DECIMAL(10, 2), " +
                    "Payment_Method VARCHAR(50), " +
                    "FOREIGN KEY (Cid) REFERENCES customer(cid)" +
                    ");");

            // Criar tabela de carrinho
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS cart (" +
                    "cartid INT AUTO_INCREMENT PRIMARY KEY, " +
                    "INID VARCHAR(255), " +
                    "Product_Name VARCHAR(255), " +
                    "Bar_code VARCHAR(255), " +
                    "qty INT, " +
                    "Unit_Price DECIMAL(10, 2), " +
                    "Total_Price DECIMAL(10, 2)" +
                    ");");

            // Criar tabela de pagamentos
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS payment (" +
                    "paymentid INT AUTO_INCREMENT PRIMARY KEY, " +
                    "SaleID INT, " +
                    "Amount DECIMAL(10, 2), " +
                    "Payment_Method VARCHAR(255), " +
                    "Payment_Date DATETIME, " +
                    "FOREIGN KEY (SaleID) REFERENCES sales(saleid)" +
                    ");");

            System.out.println("Todas as tabelas foram criadas ou já existem.");
        } catch (SQLException e) {
            System.out.println("Erro ao criar tabelas: " + e.getMessage());
        }
    }
}