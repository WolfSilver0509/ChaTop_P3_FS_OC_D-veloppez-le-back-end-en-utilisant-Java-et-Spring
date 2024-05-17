package com.p3_oc_fs.chatop.models;

/* Importation des annotations JPA pour la gestion des entités et des tables dans la base de données */

import jakarta.persistence.Table;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority; // Importation de l'interface GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails; // Importation de l'interface UserDetails

import java.util.Collection;
import java.util.Date;
import java.util.List;

/* Définition de la classe User comme entité JPA et mapping à la table 'users' */
@Table(name = "users")
@Entity
public class User implements UserDetails { // Définition de la classe User comme implémentant UserDetails

    /* Définition de la clé primaire */
    @Id
    /* Stratégie de génération automatique de la clé primaire */
    @GeneratedValue(strategy = GenerationType.AUTO)
    /* Définition des propriétés de la colonne 'id' dans la base de données */
    @Column(nullable = false)
    private Integer id;

    /* Définition des propriétés de la colonne 'name' dans la base de données */
    @Column(nullable = false)
    private String name;

    /* Définition des propriétés de la colonne 'email' dans la base de données */
    @Column(unique = true, length = 100, nullable = false)
    private String email;

    /* Définition des propriétés de la colonne 'password' dans la base de données */
    @Column(nullable = false)
    private String password;

    /* Annotation pour générer automatiquement la date de création */
    @CreationTimestamp
    /* Définition des propriétés de la colonne 'created_at' dans la base de données */
    @Column(updatable = false, name = "created_at")
    @Temporal(TemporalType.DATE)
    private Date createdAt;

    /* Annotation pour mettre à jour automatiquement la date de modification */
    @UpdateTimestamp
    /* Définition des propriétés de la colonne 'updated_at' dans la base de données */
    @Column(name = "updated_at")
    @Temporal(TemporalType.DATE)
    private Date updatedAt;

    /* Implémentation de la méthode pour obtenir les autorités de l'utilisateur */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    /* Méthode pour obtenir le mot de passe de l'utilisateur */
    public String getPassword() {
        return password;
    }

    /* Implémentation de la méthode pour obtenir le nom d'utilisateur de l'utilisateur */
    @Override
    public String getUsername() {
        return email;
    }

    /* Implémentation de la méthode pour vérifier si le compte de l'utilisateur n'a pas expiré */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /* Implémentation de la méthode pour vérifier si le compte de l'utilisateur n'est pas verrouillé */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /* Implémentation de la méthode pour vérifier si les informations d'identification de l'utilisateur n'ont pas expiré */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /* Implémentation de la méthode pour vérifier si le compte de l'utilisateur est activé */
    @Override
    public boolean isEnabled() {
        return true;
    }

    /* Méthode pour obtenir l'ID de l'utilisateur */
    public Integer getId() {
        return id;
    }

    /* Méthode pour définir l'ID de l'utilisateur */
    public User setId(Integer id) {
        this.id = id;
        return this;
    }

    /* Méthode pour obtenir le nom de l'utilisateur */
    public String getName() {
        return name;
    }

    /* Méthode pour définir le nom de l'utilisateur */
    public User setName(String name) {
        this.name = name;
        return this;
    }

    /* Méthode pour obtenir l'email de l'utilisateur */
    public String getEmail() {
        return email;
    }

    /* Méthode pour définir l'email de l'utilisateur */
    public User setEmail(String email) {
        this.email = email;
        return this;
    }

    /* Méthode pour définir le mot de passe de l'utilisateur */
    public User setPassword(String password) {
        this.password = password;
        return this;
    }

    /* Méthode pour obtenir la date de création de l'utilisateur */
    public Date getCreatedAt() {
        return createdAt;
    }

    /* Méthode pour définir la date de création de l'utilisateur */
    public User setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    /* Méthode pour obtenir la date de mise à jour de l'utilisateur */
    public Date getUpdatedAt() {
        return updatedAt;
    }

    /* Méthode pour définir la date de mise à jour de l'utilisateur */
    public User setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    /* Redéfinition de la méthode toString pour afficher les informations de l'utilisateur */
    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
