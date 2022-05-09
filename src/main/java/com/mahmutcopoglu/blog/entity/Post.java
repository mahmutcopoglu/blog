package com.mahmutcopoglu.blog.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "posts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Post extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name="title", length = 100)
    private String title;

    @Column(name="meta_title", length = 100)
    private String metaTitle;

    @Column(name="slug", length = 50)
    private String slug;

    @Column(name="published")
    private Boolean published;

    @Column(name="published_at")
    private Date publishedAt;

    @Column(name="content",length = 10000)
    private String content;

    @OneToOne
    @JoinColumn(name="parent_id")
    private Post parent;

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "parent",cascade = CascadeType.ALL)
    private Set<Post> children;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "post",cascade = CascadeType.ALL)
    private Set<PostComment> postComments = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinTable(
            name = "post_tags",
            joinColumns = @JoinColumn(name = "post_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private Set<Tag> tags = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinTable(
            name = "post_category",
            joinColumns = @JoinColumn(name = "post_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id"))
    private Set<Category> category = new HashSet<>();

}
