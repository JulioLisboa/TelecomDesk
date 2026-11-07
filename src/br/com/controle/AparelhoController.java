/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.controle;

import br.com.Util.SessaoUtil;
import br.com.Util.ValidacaoUtil;
import br.com.dao.AparelhoDAO;
import br.com.dao.ModeloDAO;
import br.com.dao.TipoAparelhoDAO;
import br.com.model.Aparelho;
import br.com.model.Modelo;
import br.com.model.Sessao;
import br.com.model.Tipo_Aparelho;
import java.util.UUID;
import java.sql.SQLException;
import java.util.List;


/**
 *
 * @author dev
 */
public class AparelhoController {
    private final AparelhoDAO aparelhoDao;
    private final ModeloDAO modeloDao;
    private final TipoAparelhoDAO tipoAparelhoDAO;

    public AparelhoController() {
        this.aparelhoDao = new AparelhoDAO();
        this.modeloDao = new ModeloDAO();
        this.tipoAparelhoDAO = new TipoAparelhoDAO();
    }

    public boolean cadastrar(Sessao sessao, String imeiApa, String idTip, String idMod){
        try {
            if(!SessaoUtil.sessaoValida(sessao)){
                throw new IllegalArgumentException("Sem permissão!");
            }
            if(!SessaoUtil.perfilValido(sessao, "TECNICO")){
                throw new IllegalArgumentException("Sem permissão 2!");
            }
            if (ValidacaoUtil.campoVazio(new String[]{imeiApa, idTip, idMod})) {
                throw new IllegalArgumentException("Informe os campos obrigatorios!");
            }
            if (!ValidacaoUtil.tamanhoValido(imeiApa, 20)) {
                throw new IllegalArgumentException("O imeiApa deve ter no máximo 20 caracteres!");
            }
            if (!ValidacaoUtil.tamanhoValido(idTip, 36)) {
                throw new IllegalArgumentException("O idTip deve ter no máximo 36 caracteres!");
            }
            if (!ValidacaoUtil.tamanhoValido(idMod, 36)) {
                throw new IllegalArgumentException("O idMod deve ter no máximo 36 caracteres!");
            }
            if (modeloDao.buscar(idMod) == null) {
                throw new IllegalArgumentException("Modelo não encontrado!");
            }
            if (tipoAparelhoDAO.buscar(idTip) == null) {
                throw new IllegalArgumentException("Tipo não encontrado!");
            }
            
            String idApa = UUID.randomUUID().toString();
            Aparelho apa = new Aparelho(idApa, imeiApa, idApa , idTip, idMod);
            aparelhoDao.inserir(apa.getIdApa(), apa.getImeiApa(), apa.getIdTip(), apa.getIdMod());
            return true;
        } catch (IllegalArgumentException | SQLException e) {
            System.out.println("Erro ao cadastrar um aparelho: " + e.getMessage());
            return false;
        }
    }
    
    public java.util.List<Aparelho> listar(Sessao sessao) {
        try {
            return new AparelhoDAO().listar();
        } catch (Exception e) {
            System.out.println("Erro ao listar aparelhos: " + e.getMessage());
            return null;
        }
    }

  
    public java.util.List<String> listarTiposFormatados() {
        java.util.List<String> lista = new java.util.ArrayList<>();
        try {
            java.util.List<Tipo_Aparelho> tipos = new TipoAparelhoDAO().listar();
            if (tipos != null) for (Tipo_Aparelho t : tipos) lista.add(t.getIdTip() + " - " + t.getNomeTip());
        } catch (Exception e) {}
        return lista;
    }

   
    public java.util.List<String> listarModelosFormatados() {
        java.util.List<String> lista = new java.util.ArrayList<>();
        try {
            java.util.List<br.com.model.Modelo> modelos = new ModeloDAO().listar();
            if (modelos != null) for (Modelo m : modelos) lista.add(m.getIdMod() + " - " + m.getNomeMod());
        } catch (Exception e) {}
        return lista;
    }


    // Busca um aparelho pelo IMEI usando o DAO existente.
    public Aparelho buscar(String imeiApa) {
        try {
            if (ValidacaoUtil.campoVazio(new String[]{imeiApa})) {
                throw new IllegalArgumentException("Informe o IMEI!");
            }
            return aparelhoDao.buscarPorImeiApa(imeiApa);
        } catch (IllegalArgumentException | SQLException erro) {
            System.err.println("Erro ao buscar aparelho: " + erro.getMessage());
            return null;
        }
    }

    // Edita sem criar classe nova: usa AparelhoDAO.atualizar() que já existe.
    public boolean editar(Sessao sessao, String idApa, String imeiApa, String idTip, String idMod) {
        try {
            if (!SessaoUtil.sessaoValida(sessao) || !SessaoUtil.perfilValido(sessao, "TECNICO")) {
                throw new IllegalArgumentException("Sem permissão para editar aparelhos!");
            }
            if (ValidacaoUtil.campoVazio(new String[]{idApa, imeiApa, idTip, idMod})) {
                throw new IllegalArgumentException("Preencha todos os campos!");
            }
            if (!ValidacaoUtil.tamanhoValido(idApa, 36)
                    || !ValidacaoUtil.tamanhoValido(imeiApa, 20)
                    || !ValidacaoUtil.tamanhoValido(idTip, 36)
                    || !ValidacaoUtil.tamanhoValido(idMod, 36)) {
                throw new IllegalArgumentException("Um dos campos ultrapassa o tamanho permitido!");
            }
            if (aparelhoDao.buscarPorIdApa(idApa) == null) {
                throw new IllegalArgumentException("Aparelho não encontrado ou excluído!");
            }
            if (tipoAparelhoDAO.buscar(idTip) == null || modeloDao.buscar(idMod) == null) {
                throw new IllegalArgumentException("Tipo ou modelo não encontrado!");
            }
            return aparelhoDao.atualizar(idApa, imeiApa, idTip, idMod);
        } catch (IllegalArgumentException | SQLException erro) {
            System.err.println("Erro ao editar aparelho: " + erro.getMessage());
            return false;
        }
    }
}
