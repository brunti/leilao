import java.sql.*;
import java.util.ArrayList;

public class ProdutosDAO {

    public ArrayList<ProdutosDTO> listarProdutos() {
        ArrayList<ProdutosDTO> produtos = new ArrayList<>();
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            // Estabeleça a conexão com o banco de dados
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/seubanco", "usuario", "senha");

            // Criação de um Statement
            stmt = conn.createStatement();

            // Consulta SQL para listar os produtos
            String sql = "SELECT * FROM produtos";
            rs = stmt.executeQuery(sql);

            // Processar o ResultSet
            while (rs.next()) {
                ProdutosDTO produto = new ProdutosDTO();
                produto.setId(rs.getInt("id"));           // Ajuste conforme o nome da coluna
                produto.setNome(rs.getString("nome"));   // Ajuste conforme o nome da coluna
                produto.setValor(rs.getInt("valor")); // Ajuste conforme o nome da coluna
                produto.setStatus(rs.getString("status")); // Ajuste conforme o nome da coluna

                produtos.add(produto);  // Adiciona o produto à lista
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Caso ocorra algum erro, imprime a stack trace
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return produtos; // Retorna a lista de produtos
    }
    public void cadastrarProduto(ProdutosDTO produto) {
        String sql = "INSERT INTO produtos (nome, valor, status) VALUES (?, ?, ?)";
        
        try (Connection conn = ConexaoDAO.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, produto.getNome());
            stmt.setInt(2, produto.getValor());
            stmt.setString(3, produto.getStatus());

            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas > 0) {
                System.out.println("Produto cadastrado com sucesso!");
            } else {
                System.out.println("Erro ao cadastrar produto!");
            }

        } catch (SQLException e) {
            System.out.println("Erro SQL ao cadastrar produto: " + e.getMessage());
        }
    }
}
