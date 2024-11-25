package pos.pro;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class db {
    public static Connection mycon() {
        Connection con = null;
        try {
            // Carregar o driver JDBC para MySQL
            Class.forName("com.mysql.cj.jdbc.Driver");

            // URL de conexão com o servidor MySQL
            String url = "jdbc:mysql://localhost:3306/";
            String user = "root"; // Altere para seu usuário
            String password = "12345"; // Altere para sua senha

            // Estabelecer a conexão
            con = DriverManager.getConnection(url, user, password);
            System.out.println("Conexão estabelecida com sucesso!");

            // Tentar criar o banco de dados se não existir
            Statement stmt = con.createStatement();
            stmt.executeUpdate("CREATE DATABASE IF NOT EXISTS pos");
            System.out.println("Banco de dados 'pos' verificado/criado com sucesso!");

            // Mudar o catálogo para o banco de dados 'pos'
            con.setCatalog("pos");

            // Criar tabelas necessárias
            createTables(con);

        } catch (ClassNotFoundException e) {
            System.out.println("Driver não encontrado: " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("Erro ao conectar ao banco de dados: " + e.getMessage());
        }
        return con;
    }

    private static void createTables(Connection con) {
        try {
            Statement stmt = con.createStatement();

            // Criar tabela de clientes
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS customer (" +
                    "cid INT AUTO_INCREMENT PRIMARY KEY, " +
                    "customer_name VARCHAR(255), " +
                    "Tp_Number VARCHAR(255), " +
                    "billing_address TEXT, " +
                    "shipping_address TEXT, " +
                    "bank VARCHAR(255), " +
                    "city VARCHAR(255), " +
                    "person_name VARCHAR(255), " +
                    "contact_person VARCHAR(255), " +
                    "person_tp VARCHAR(255), " +
                    "email VARCHAR(255), " +
                    "online VARCHAR(255)" +
                    ");");

            // Criar tabela de fornecedores
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS supplier (" +
                    "sid INT AUTO_INCREMENT PRIMARY KEY, " +
                    "supplier_name VARCHAR(255), " +
                    "Tp_Number VARCHAR(255), " +
                    "billing_address TEXT, " +
                    "shipping_address TEXT, " +
                    "bank VARCHAR(255), " +
                    "city VARCHAR(255), " +
                    "person_name VARCHAR(255), " +
                    "contact_person VARCHAR(255), " +
                    "person_tp VARCHAR(255), " +
                    "email VARCHAR(255), " +
                    "online VARCHAR(255)" +
                    ");");

            // Criar tabela de produtos
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS product (" +
                    "pid INT AUTO_INCREMENT PRIMARY KEY, " +
                    "Product_Name VARCHAR(255), " +
                    "Bar_code VARCHAR(255), " +
                    "Price DECIMAL(10, 2), " +
                    "Qty INT, " +
                    "Sid INT, " +
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
                    "Payment_Date TIMESTAMP DEFAULT CURRENT_TIMESTAMP, " +
                    "FOREIGN KEY (SaleID) REFERENCES sales(saleid)" +
                    ");");

            System.out.println("Tabelas criadas com sucesso!");

        } catch (SQLException e) {
            System.out.println("Erro ao criar tabelas: " + e.getMessage());
        }
    }
}