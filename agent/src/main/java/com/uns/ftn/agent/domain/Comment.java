package com.uns.ftn.agent.domain;
/***********************************************************************
 * Module:  Comment.java
 * Author:  Vunic
 * Purpose: Defines the Class Comment
 ***********************************************************************/

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
@Entity
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "allowed")
    private Boolean allowed = false;

    @Column(name = "userId")
    private Long userId;

    @Column(name = "remoteId")
    private Long remoteId;

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Advertisement advertisement;

}