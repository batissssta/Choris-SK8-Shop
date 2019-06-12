package com.ecommerce.negocio.cliente;


import com.ecommerce.dominio.Cliente;
import com.ecommerce.dominio.EntidadeDominio;
import com.ecommerce.negocio.IStrategy;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;


public class CriptografaSenha implements IStrategy {

    @Override
    public String processar(EntidadeDominio entidade) {

        Cliente cliente = (Cliente)entidade;

        MessageDigest algorithm = null;
        try {
            algorithm = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(CriptografaSenha.class.getName()).log(Level.SEVERE, null, ex);
        }
        byte messageDigest[] = null;
        try {
            messageDigest = algorithm.digest(cliente.getSenha().getBytes("UTF-8"));
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(CriptografaSenha.class.getName()).log(Level.SEVERE, null, ex);
        }

        cliente.setSenha(messageDigest.toString());

        return null;

    }



}
