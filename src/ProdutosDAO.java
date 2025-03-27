
import java.sql.PreparedStatement;
import java.sql.Connection;
import javax.swing.JOptionPane;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.sql.SQLException;

public class ProdutosDAO {
    
    Connection conn;
    PreparedStatement prep;
    ResultSet resultset;
    ArrayList<ProdutosDTO> listagem = new ArrayList<>();
    
    public void cadastrarProduto(ProdutosDTO produto) {
    // Conectar ao banco de dados
    try (Connection conn = new conectaDAO().getConnection()) {
        // Query SQL para inserir o produto
        String sql = "INSERT INTO produtos (nome, valor, status) VALUES (?, ?, ?)";
        
        // Preparando o statement
        try (PreparedStatement prep = conn.prepareStatement(sql)) {
            // Setando os valores no PreparedStatement
            prep.setString(1, produto.getNome());
            prep.setInt(2, produto.getValor());
            prep.setString(3, produto.getStatus());
            
            // Executando o comando de inserção
            prep.executeUpdate();
            
            // Informando ao usuário
            JOptionPane.showMessageDialog(null, "Produto cadastrado com sucesso!");
        }
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(null, "Erro ao cadastrar o produto: " + e.getMessage());
    }
}
    
    public ArrayList<ProdutosDTO> listarProdutos(){
        
        return listagem;
    }
    
    
    
        
}

