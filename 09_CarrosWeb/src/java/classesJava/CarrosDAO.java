/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classesJava;

import com.mysql.jdbc.PreparedStatement;
import com.sun.faces.config.DbfFactory;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

/**
 *
 * @author Fsociety
 */
public class CarrosDAO {
    String sql; // Varialvel responsável pelo comandos sql 
    Connection conexao;
    PreparedStatement ps; //  Comando com parametros 
    ResultSet rs; // Resultado do select 
    
    //Método para mostrar mensagens 
    public void mensagem(String msg){
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"",msg));    
    }
    
    //Cria um objeto da classe carro para que a gente possa salvar
    public void salvar (Carros carro){ 
        try {
            conexao = ConectaBD.getConexao();
            sql = "insert into tbCarros(Marca, Modelo, Cor, Ano) values (?,?,?,?)";
            ps = (PreparedStatement) conexao.prepareStatement(sql);
            ps.setString(1, carro.getMarca()); // Pega o primeiro campo e víncula com o campo Marca no BD.
            ps.setString(2, carro.getModelo());
            ps.setString(3, carro.getCor());
            ps.setInt(4, carro.getAno());
            ps.execute();
            ConectaBD.fechaConexao(); 
            mensagem("Carro cadastrado com sucesso!");
        } catch (SQLException ex) { 
            Logger.getLogger(CarrosDAO.class.getName()).log(Level.SEVERE, null, ex);
            mensagem("Erro ao cadastrar!"); 
        }
        
    }
    
        public void editar (Carros carro){ 
        try {
            conexao = ConectaBD.getConexao();
            sql = "update tbCarros set Marca = ?, Modelo = ?, Cor = ?, Ano = ? where idCarro = ?";
            ps = (PreparedStatement) conexao.prepareStatement(sql);
            ps.setString(1, carro.getMarca()); // Pega o primeiro campo e víncula com o campo Marca no BD.
            ps.setString(2, carro.getModelo());
            ps.setString(3, carro.getCor());
            ps.setInt(4, carro.getAno());
            ps.setInt(5, carro.getId());
            ps.execute();
            ConectaBD.fechaConexao(); 
            mensagem("Carro alterado com sucesso!");
        } catch (SQLException ex) { 
            Logger.getLogger(CarrosDAO.class.getName()).log(Level.SEVERE, null, ex);
            mensagem("Erro ao alterar!"); 
        }
        
    }
    
         public void excluir (int id){ 
        try {
            conexao = ConectaBD.getConexao();
            sql = "delete from tbCarros where idCarro = ?";
            ps = (PreparedStatement) conexao.prepareStatement(sql);
            ps.setInt(1, id);
            ps.execute();
            ConectaBD.fechaConexao(); 
            mensagem("Carro excluído com sucesso!");
        } catch (SQLException ex) { 
            Logger.getLogger(CarrosDAO.class.getName()).log(Level.SEVERE, null, ex);
            mensagem("Erro ao excluir!"); 
        }
        
    }
         public List<Carros> listar(){
        try {
            conexao = ConectaBD.getConexao();
            sql = "select * from tbCarros";
            ps = (PreparedStatement) conexao.prepareStatement(sql);
            rs = ps.executeQuery();
            
           List<Carros> listaCarros = new ArrayList<>();
           while(rs.next()){
               Carros car = new Carros(); 
               car.setId(rs.getInt("idCarro"));
               car.setMarca(rs.getString("Marca"));
               car.setMarca(rs.getString("Modelo"));
               car.setCor(rs.getString("Cor"));
                car.setAno(rs.getInt("Ano"));
                listaCarros.add(car);
           }
           return listaCarros;
        } catch (SQLException ex) {
            Logger.getLogger(CarrosDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
            
         }
}
