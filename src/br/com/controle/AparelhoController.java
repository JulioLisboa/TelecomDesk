/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.controle;

import br.com.Util.SessaoUtil;
import br.com.Util.ValidacaoUtil;
import br.com.dao.AparelhoDAO;
import br.com.dao.MarcaDAO;
import br.com.dao.ModeloDAO;
import br.com.dao.TipoAparelhoDAO;
import br.com.model.Aparelho;
import br.com.model.Sessao;
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
    
    public Aparelho buscar(String imeiApa){
        try {
            if (ValidacaoUtil.campoVazio(new String[]{imeiApa})) {
                throw new IllegalArgumentException("imeiApa não informado!");
            }
            return aparelhoDao.buscarPorImeiApa(imeiApa);
        } catch (IllegalArgumentException | SQLException e) {
            System.out.println("Erro ao buscar um Aparelho: " + e.getMessage());
            return null;
        }
    }
    
    public List<Aparelho> listar(Sessao sessao) {
        try {
            if (!SessaoUtil.sessaoValida(sessao)) {
                throw new IllegalArgumentException("Sessão inválida!");
            }

            if (!SessaoUtil.perfilValido(sessao, "TECNICO")) {
                throw new IllegalArgumentException("Sem permissão! Apenas técnicos podem listar usuários.");
            }

            return aparelhoDao.listar();

        } catch (IllegalArgumentException | SQLException e) {
            System.out.println("Erro ao listar os Aparelhos: " + e.getMessage());
            return null;
        }
    }
}
