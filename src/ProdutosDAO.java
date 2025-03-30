
import java.sql.PreparedStatement;
import java.sql.Connection;
import javax.swing.JOptionPane;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.sql.SQLException;
import java.util.List;

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
    
    public ArrayList<ProdutosDTO> listarProdutos() {
        ArrayList<ProdutosDTO> lista = new ArrayList<>();
        
        String sql = "SELECT * FROM produtos";  // SQL para buscar todos os produtos
        
        try (Connection conn = conectaDAO.getConnection(); 
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                ProdutosDTO produto = new ProdutosDTO();
                produto.setId(rs.getInt("id"));
                produto.setNome(rs.getString("nome"));
                produto.setValor(rs.getInt("valor"));
                produto.setStatus(rs.getString("status"));
                lista.add(produto);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return lista;
    }
    
   public void venderProduto(int id) {
    String sql = "UPDATE produtos SET status = 'Vendido' WHERE id = ?";
    
    try (Connection conn = conectaDAO.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {
        
        stmt.setInt(1, id);
        stmt.executeUpdate();
        
        System.out.println("Produto vendido com sucesso!");
        
    } catch (SQLException e) {
        System.out.println("Erro ao vender o produto: " + e.getMessage());
    }
}

   public List<ProdutosDTO> listarProdutosVendidos() {
    List<ProdutosDTO> listaProdutos = new ArrayList<>();
    String sql = "SELECT * FROM produtos WHERE status = 'Vendido'";
    
    try (Connection conn = conectaDAO.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql);
         ResultSet rs = stmt.executeQuery()) {
        
        while (rs.next()) {
            ProdutosDTO produto = new ProdutosDTO();
            produto.setId(rs.getInt("id"));
            produto.setNome(rs.getString("nome"));
            produto.setValor(rs.getInt("valor"));
            produto.setStatus(rs.getString("status"));
            
            listaProdutos.add(produto);
        }
        
    } catch (SQLException e) {
        System.out.println("Erro ao listar produtos vendidos: " + e.getMessage());
    }
    
    return listaProdutos;
}

}

