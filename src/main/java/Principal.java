import com.mysql.cj.x.protobuf.MysqlxDatatypes;
import com.mysql.cj.xdevapi.SqlUpdateResult;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.security.spec.RSAOtherPrimeInfo;
import java.sql.*;
import javax.swing.JOptionPane;


public class Principal extends JFrame {

    public Connection conn = null;
    public Statement stmt;
    public ResultSet rs;
    private JTabbedPane abas;
    private JPanel panel1;
    private JTextField textLogin;
    private JPasswordField textPassword;
    private JButton textEntre;
    private JTextField textSigla;
    private JTextField textNome;
    private JTextArea textDescr;
    private JButton inserirButton;
    private JButton deletarButton;
    private JButton atualizarButton;
    private JButton limparCamposButton;
    private JButton primeiroButton;
    private JButton anteriorButton;
    private JButton proximoButton;
    private JButton ultimoButton;

    public Principal() {
        Connect();
        this.setTitle("My Data Base");
        this.setVisible(true);
        this.setContentPane(panel1);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
       // initComponentes();
        abas.setEnabledAt(1, false); //para desabilitar uma aba (como começa com 0, e eu quero desabilitar a segunda aba, colocar 1)


        textEntre.addActionListener((ActionEvent e) -> {
            String password = new String(textPassword.getPassword()); //o campo senha é preciso ser convertido para string
            if (textLogin.getText().equals("adm") && password.equals("123")) {
                abas.setEnabledAt(1, true);
                abas.setSelectedIndex(1);
            } else {
                JOptionPane.showMessageDialog(null, "Dados de login incorretos");
            }
        });

        inserirButton.addActionListener((ActionEvent e) -> {

            try {
//atribuindo uma instrução SQL a uma variável String;
                String sql = "INSERT INTO curso VALUES('"
                        + textSigla.getText() + "','"
                        + textNome.getText() + "','"
                        + textDescr.getText() + "')";
                JOptionPane.showMessageDialog(null, sql); //exibe o conteúdo da variável string (sql) em um JOptionPane;
                int i = 0;
                i = stmt.executeUpdate(sql); //executando comando sql
                stmt.close();

//se i for maior que zero, significa que a instrução SQL foi executada corretamente, assim é exibida uma mensagem de sucesso;
                if (i > 0) {
                    JOptionPane.showMessageDialog(null, "Curso cadastrado com sucesso");
                    abreTabela();
                }

            } catch (SQLException ActionEvent){
                System.out.println(e);
            }
        });

        deletarButton.addActionListener((ActionEvent e) -> {

            String query1 = "DELETE FROM curso WHERE (sigla=";
            String a = textSigla.getText();
            try{
                String sql = query1 + "'" + textSigla.getText() + "')";
                JOptionPane.showMessageDialog(null, sql);
                int i = 0;
                i = stmt.executeUpdate(sql);
                if (i >0) {
                    JOptionPane.showMessageDialog(null, "Curso deletado com sucesso!");

                    //limpando os campos
                textSigla.setText("");
                textNome.setText("");
                textDescr.setText("");
                abreTabela();
                }

            }catch (SQLException ActionEvent){
                System.out.println(e);
            }
        });

        atualizarButton.addActionListener((ActionEvent e) -> {
            String query1 = "UPDATE curso SET sigla="; //atribui uma instrução SQL (seleção) a uma variável string;
            String a = textSigla.getText(); //atribui valor do campo Sigla para uma variável string;

            try{
//Implementando instrução SQL e atribuindo o resultado a uma variável string;
                String sql = query1 + "'"
                        + textSigla.getText() + "',"
                        + "nome='"
                        + textNome.getText()
                        + "'," + "descricao='"
                        + textDescr.getText()
                        + "' where sigla="
                        + "'"
                        + textSigla.getText()
                        +"'";
                JOptionPane.showMessageDialog(null, sql); //Exibibe o conteúdo da variável string (sql) em um JOptionPane;
                int i = 0;
                i = stmt.executeUpdate(sql); //executando comando sql e atribuindo resultado a uma variável int;
                int y = 0;
                stmt.close();
                y = stmt.CLOSE_ALL_RESULTS;
                if (i > 0) {
                    JOptionPane.showMessageDialog(null, "Curso alterado com sucesso!");
                    abreTabela();
                }
            }catch (SQLException ActionEvent){
                System.out.println(e);
            }

        });

        limparCamposButton.addActionListener((ActionEvent e) -> {
 //atribuindo uma string vazia para a propriedade setText dos campos textos da aplicação
            textSigla.setText("");
            textNome.setText("");
            textDescr.setText("");
        });

        primeiroButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    rs.first(); // Executa o método first do objeto rs;
                    atualiza_campos(); //chama o método atualiza campos;
                }catch (SQLException ActionEvent){ //caso ocorra alguma exceção, o sistema exibe o erro.
                    System.out.println(e);
                }
            }
        });

        anteriorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    rs.previous(); //Executa o método previous do objeto rs;
                    atualiza_campos(); //chama o método altualiza_campos;
                }catch (SQLException ActionEvent){
                    System.out.println(e);
                }
            }
        });

        proximoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    rs.next(); //executa o método next do objeto rs.
                    atualiza_campos();
                }catch (SQLException ActionEvent){
                    System.out.println(e);
                }
            }
        });

        ultimoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    rs.last();
                    atualiza_campos();
                }catch (SQLException ActionEvent){
                    System.out.println(e);
                }
            }
        });

    }

    private void abreTabela() {
        try {
            String quiery1 = "select * from curso";
            rs = stmt.executeQuery(quiery1); //executando o comando sql
            rs.first(); //estamos movendo o cursor para o primeiro registro pesquisado
            atualiza_campos(); //conteúdo da coluna sigla

        } catch (SQLException e){
            System.out.println(e);
        }
    }

    private void atualiza_campos() {
        try {

/*Atribuindo aos camops os valores contidos na variável ResultSEt.
Observe ser passado como parâmetro do método getString o nome da coluna que
se deseja objetr o valor*/
            textSigla.setText("" + rs.getString("Sigla"));
            textNome.setText("" + rs.getString("Nome"));
            textDescr.setText("" + rs.getString("Descrição"));
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public void Connect(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver"); //registra a Classe do Driver JDBC
            conn = DriverManager.getConnection("jdbc:mysql://localhost/dbaula4", "root", "123");
//cria um objeto de conexção passando como parâmetros o enderçõ, usuario e senha do banco de dados;
            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
//cria um objeto Statement e é por meio desse objeto que iremos executar operações no banco de dados;

        } catch (ClassNotFoundException e) {
            System.out.println(e);
        } catch (SQLException e) {
            System.out.println(e);
        }
    }


    public static void main(String[] args) {
       JFrame frame = new Principal();
    }

}
