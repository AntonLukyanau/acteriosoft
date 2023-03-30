package com.asteriosoft.lukyanau.testingtask.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "banner")
@NoArgsConstructor
@Setter
@Getter
@Builder
@EqualsAndHashCode
@SQLDelete(sql = "UPDATE banner SET deleted = true WHERE id=?")
@Where(clause = "deleted=false")
public class Banner {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "banner_id_seq")
    @SequenceGenerator(name = "banner_id_seq", sequenceName = "banner_id_seq", allocationSize = 1)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "body", nullable = false)
    private String body;

    @Column(name = "price", nullable = false)
    private BigDecimal price;

    @EqualsAndHashCode.Exclude
    @Column(name = "deleted")
    private boolean deleted = Boolean.FALSE;

    @EqualsAndHashCode.Exclude
    @ManyToMany
    @JoinTable(name = "banner_category",
            joinColumns = @JoinColumn(name = "banner_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id"))
    private List<Category> categories;

    private Banner(Long id, String name, String body, BigDecimal price, boolean deleted, List<Category> categories) {
        this.id = id;
        this.name = name;
        this.body = body;
        this.price = price;
        this.deleted = deleted;
        this.categories = new ArrayList<>(categories);
    }

}
