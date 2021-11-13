package emprestimo;

import aluno.Aluno;
import livro.Livro;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmprestimoDaoImpl implements EmprestimoDao{

    public static final String URL = "jdbc:mariadb://localhost:3306/bdbiblioteca";
    public static final String USER = "root";
    public static final String PASSWORD = "";


    public void adicionar(Emprestimo Al) {
        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD)){
            String sqlADD = "INSERT INTO tbEMPRESTIMO" +
                    "(CODIGO_EMPRESTIMO,RA_ALUNO,NOME_ALUNO,CELULAR_ALUNO,CODIGO_LIVRO,TITULO_LIVRO,DATA_EMPRESTIMO,DATA_ENTREGA,SITUACAO_EMPRESTIMO) VALUES" +
                    "(?, ?, ?, ?, ?, ?, ?,?,?)";
            PreparedStatement stmtADD = con.prepareStatement(sqlADD);
            stmtADD.setInt(1, Al.getCodigo());
            stmtADD.setLong(2, Al.getRA());
            stmtADD.setString(3, Al.getNome());
            stmtADD.setLong(4, Al.getCelular());
            stmtADD.setInt(5, Al.getISBN());
            stmtADD.setString(6, Al.getTitulo());
            stmtADD.setString(7, Al.getDataEmprestimo());
            stmtADD.setString(8, Al.getDataEntrega());
            stmtADD.setString(9, Al.getSituacao());
            stmtADD.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public Emprestimo pesquisarPorCodigo(long RA) {
        try (Connection conPQ = DriverManager.getConnection(URL, USER, PASSWORD)) {
            String sqlPQ = "SELECT * FROM tbaluno WHERE RA LIKE ?";
            PreparedStatement stmtPQ = conPQ.prepareStatement(sqlPQ);
            stmtPQ.setLong(1, RA);
            ResultSet rs = stmtPQ.executeQuery();
            while (rs.next()) {
                Emprestimo Al = new Emprestimo();
                Al.setRA(rs.getLong("RA"));
                Al.setNome(rs.getString("nome"));
                Al.setCelular(rs.getLong("celular"));
                Al.setSituacao(rs.getString("situacao"));
                return Al;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Emprestimo pesquisarPorCodigoEMPRESTIMO(long CODIGO) {
        try (Connection conPQ = DriverManager.getConnection(URL, USER, PASSWORD)) {
            String sqlPQ = "SELECT * FROM tbEMPRESTIMO WHERE CODIGO_EMPRESTIMO LIKE ?";
            PreparedStatement stmtPQ = conPQ.prepareStatement(sqlPQ);
            stmtPQ.setLong(1, CODIGO);
            ResultSet rs = stmtPQ.executeQuery();
            while (rs.next()) {
                Emprestimo Al = new Emprestimo();
                Al.setRA(rs.getLong("RA_ALUNO"));
                Al.setNome(rs.getString("nome_ALUNO"));
                Al.setCelular(rs.getLong("celular_ALUNO"));
                Al.setSituacao(rs.getString("situacao_emprestimo"));

                Al.setDataEmprestimo(rs.getString("data_emprestimo"));
                Al.setISBN(rs.getInt("CODIGO_LIVRO"));
                Al.setDataEntrega(rs.getString("data_entrega"));
                Al.setTitulo(rs.getString("titulo_LIVRO"));
                return Al;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
/*
    public List<Emprestimo> colocarValores(){
        List<Emprestimo> lista = new ArrayList<>();
        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD)) {
            String sql = "SELECT CODIGO_LIVRO,TITULO_LIVRO FROM tbEMPRESTIMO";
            PreparedStatement stmt = con.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Emprestimo Al = new Emprestimo();
                Al.setISBN(rs.getInt("CODIGO_LIVRO"));
                Al.setTitulo(rs.getString("titulo_LIVRO"));
                lista.add(Al);
            }
            return lista;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }
*/
    public Livro pesquisar(int cod) {
        try (Connection conPQ = DriverManager.getConnection(URL, USER, PASSWORD)) {
            String sqlPQ = "SELECT ISBN,TITULO FROM tbLIVRO WHERE ISBN LIKE ?";
            PreparedStatement stmtPQ = conPQ.prepareStatement(sqlPQ);
            stmtPQ.setLong(1, cod);
            ResultSet rs = stmtPQ.executeQuery();
            while (rs.next()) {
                Livro Lv = new Livro();
                Lv.setISBN(rs.getInt("ISBN"));
                Lv.setTITULO(rs.getString("TITULO"));
                return Lv;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}