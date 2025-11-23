package com.lp2.bibliotecaapi.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ArmazenamentoJson<T> {

    //responsavel por ler e escrever o JSON
    private final ObjectMapper mapper = new ObjectMapper();

    private final File arquivo;
    private final TypeReference<List<T>> typeReference;

    public ArmazenamentoJson(String caminho, TypeReference<List<T>> tr){
        this.arquivo = new File(caminho);
        this.typeReference = tr;

        if(!arquivo.exists()){
            try{
                //cria a pasta de dados se ela nao existir
                arquivo.getParentFile().mkdirs();

                //vai escrever uma lista vazia no arquivo
                mapper.writeValue(arquivo, new ArrayList<>());

            } catch (IOException e) {
                throw new RuntimeException("Erro ao criar arquivo JSON: " + caminho);
            }
        }
    }

    public List<T> load(){
        try{
            //le o json e converte para uma List de forma automatica
            return mapper.readValue(arquivo, typeReference);
        } catch(IOException e) {
                return new ArrayList<>(); //retorna lista vazia
            }
    }
    public void save(List<T> dados){
        try{
            //escreve o json de forma identada
            mapper.writerWithDefaultPrettyPrinter().writeValue(arquivo, dados);
        } catch(IOException e){
            throw new RuntimeException("Erro ao salvar o JSON: " + arquivo.getPath());
        }
    }


}
