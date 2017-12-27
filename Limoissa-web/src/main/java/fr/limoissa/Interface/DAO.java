/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.limoissa.Interface;

import java.util.Collection;

/**
 *
 * @author Adrien
 * @param <T>
 */
public interface DAO<T> {
    
    Boolean add(T Item); // Ajout d'un item, renvoie true si l'opération s'est bien passée
    Boolean edit(T Item); // Modification d'un item, renvoie true si l'opération s'est bien passée
    T get(int Id); // Récupération d'un item, renvoie l'objet demandé ou null en cas de problème
    Collection<T> getAll(); // Récupération de tous les items depuis la base
}
