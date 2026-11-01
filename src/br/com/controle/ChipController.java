/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.controle;

import br.com.Util.SessaoUtil;
import br.com.Util.ValidacaoUtil;
import br.com.dao.ChipDAO;
import br.com.dao.OperadoraDAO;
import br.com.model.Chip;
import br.com.model.Sessao;
import java.sql.SQLException;
import java.util.UUID;


/**
 *
 * @author dev
 */
public class ChipController {
    private final ChipDAO chipDao;
    private final OperadoraDAO operadoraDao;

    public ChipController() {
        this.chipDao = new ChipDAO();
        this.operadoraDao = new OperadoraDAO();
    }
    
    public boolean cadastrar(Sessao sessao, String iccidChip, String numeroChip, String idOpe){
        try {
            if(!SessaoUtil.sessaoValida(sessao)){
                throw new IllegalArgumentException("Sem permissão!");
            }
            if(!SessaoUtil.perfilValido(sessao, "TECNICO")){
                throw new IllegalArgumentException("Sem permissão 2!");
            }
            if (ValidacaoUtil.campoVazio(new String[]{iccidChip, numeroChip, idOpe})) {
                throw new IllegalArgumentException("Informe os campos obrigatorios!");
            }
            Chip chip = new Chip(iccidChip, numeroChip, idOpe);
            if (!ValidacaoUtil.tamanhoValido(chip.getIccidChip(), 30)) {
                throw new IllegalArgumentException("O IccidChip deve ter no máximo 30 caracteres!");
            }
            if (!ValidacaoUtil.tamanhoValido(chip.getNumeroChip(), 15)) {
                throw new IllegalArgumentException("O numeroChip deve ter no máximo 15 caracteres!");
            }
            if (!ValidacaoUtil.tamanhoValido(chip.getIdOpe(), 36)) {
                throw new IllegalArgumentException("O idOpe deve ter no máximo 36 caracteres!");
            }
            if (operadoraDao.buscar(idOpe) == null){
                throw new IllegalArgumentException("Operadora não encontrada!");
            }
            chip.setIdChip(UUID.randomUUID().toString());
            chipDao.inserir(chip.getIdChip(), chip.getIccidChip(), chip.getNumeroChip(), chip.getIdOpe());
            return true;
        } catch (IllegalArgumentException | SQLException e) {
            System.out.println("Erro ao cadastrar um chip: " + e.getMessage());
            return false;
        }
    }
    
    public Chip buscar(String numeroChip){
        try {
            if (ValidacaoUtil.campoVazio(new String[]{numeroChip})) {
                throw new IllegalArgumentException("IdChip não informado!");
            }
            return chipDao.buscarPorNumeroChip(numeroChip);
        } catch (IllegalArgumentException | SQLException e) {
            System.out.println("Erro ao buscar um chip: " + e.getMessage());
            return null;
        }
    }
}
